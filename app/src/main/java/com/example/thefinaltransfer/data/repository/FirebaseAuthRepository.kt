package com.example.thefinaltransfer.data.repository

import android.app.Activity
import com.example.thefinaltransfer.data.model.UserModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

sealed class AuthResult {
    data class Success(val message: String = "Operation successful") : AuthResult()
    data class Error(val message: String) : AuthResult()
    data object Loading : AuthResult()
}

class FirebaseAuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")

    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    // --- Current User State ---

    val currentUserId: String?
        get() = auth.currentUser?.uid

    val isUserLoggedIn: Boolean
        get() = auth.currentUser != null

    //PHONE OTP AUTHENTICATION

    /**
     * Sends a 6-digit OTP to the specified phone number via Firebase Phone Auth.
     *
     * @param phoneNumber Full international phone number (e.g., "+919876543210")
     * @param activity    Required by Firebase for reCAPTCHA verification
     * @return Flow emitting AuthResult states (Loading → Success/Error)
     */
    fun sendPhoneOtp(phoneNumber: String, activity: Activity): Flow<AuthResult> = callbackFlow {
        trySend(AuthResult.Loading)

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Auto-verification on some devices (Google Play Services auto-reads SMS)
                trySend(AuthResult.Success("Auto-verified successfully"))
                channel.close()
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                trySend(AuthResult.Error(exception.message ?: "Phone verification failed"))
                channel.close()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // Store the verification ID for later use when user enters OTP
                storedVerificationId = verificationId
                resendToken = token
                trySend(AuthResult.Success("OTP sent successfully"))
                // Don't close channel — we still need it for verification
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        awaitClose { /* Cleanup if needed */ }
    }

    /**
     * Verifies the user-entered OTP against the stored verification ID.
     *
     * @param otp The 6-digit OTP entered by the user
     * @return AuthResult indicating success or failure
     */
    suspend fun verifyPhoneOtp(otp: String): AuthResult {
        return try {
            val verificationId = storedVerificationId
                ?: return AuthResult.Error("No verification ID found. Please request OTP again.")

            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            auth.signInWithCredential(credential).await()
            AuthResult.Success("Phone verified successfully")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "OTP verification failed")
        }
    }

    // ========================================================================
    // SECTION 2: EMAIL + PASSWORD AUTHENTICATION
    // ========================================================================

    /**
     * Creates a new user account with email and password.
     * Firebase automatically sends a verification email.
     *
     * @param email    User's email address
     * @param password User's chosen password
     * @return AuthResult indicating success or failure
     */
    suspend fun signUpWithEmail(email: String, password: String): AuthResult {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            // Send email verification
            auth.currentUser?.sendEmailVerification()?.await()
            AuthResult.Success("Account created. Verification email sent.")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Sign up failed")
        }
    }

    /**
     * Signs in an existing user with email and password.
     *
     * @param email    User's registered email address
     * @param password User's vault password
     * @return AuthResult indicating success or failure
     */
    suspend fun signInWithEmail(email: String, password: String): AuthResult {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success("Login successful")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Login failed. Check your credentials.")
        }
    }

    /**
     * Sends a password reset email to the user.
     */
    suspend fun sendPasswordResetEmail(email: String): AuthResult {
        return try {
            auth.sendPasswordResetEmail(email).await()
            AuthResult.Success("Password reset email sent")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to send reset email")
        }
    }

    /**
     * Signs out the current user from Firebase.
     */
    fun signOut() {
        auth.signOut()
    }

    // ========================================================================
    // SECTION 3: REALTIME DATABASE — USER PROFILE CRUD
    // ========================================================================

    /**
     * Saves a complete user profile to Firebase Realtime Database.
     * Path: /users/{uid}/
     *
     * @param user The UserModel containing all user details
     * @return AuthResult indicating success or failure
     */
    suspend fun saveUserToDatabase(user: UserModel): AuthResult {
        return try {
            val uid = user.uid.ifEmpty { auth.currentUser?.uid ?: return AuthResult.Error("No authenticated user") }
            usersRef.child(uid).setValue(user.copy(uid = uid)).await()
            AuthResult.Success("User profile saved successfully")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to save user data")
        }
    }

    /**
     * Retrieves a user profile from Firebase Realtime Database.
     *
     * @param uid The Firebase UID of the user
     * @return Flow emitting the UserModel or null
     */
    fun getUserFromDatabase(uid: String): Flow<UserModel?> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                trySend(user)
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(null)
            }
        }

        usersRef.child(uid).addValueEventListener(listener)

        awaitClose {
            usersRef.child(uid).removeEventListener(listener)
        }
    }

    /**
     * Updates specific fields in a user's profile without overwriting the entire node.
     *
     * @param uid     The Firebase UID
     * @param updates A map of field names to new values
     */
    suspend fun updateUserFields(uid: String, updates: Map<String, Any>): AuthResult {
        return try {
            usersRef.child(uid).updateChildren(updates).await()
            AuthResult.Success("Profile updated")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Update failed")
        }
    }

    /**
     * Checks if a user profile already exists in the database.
     */
    suspend fun doesUserExist(uid: String): Boolean {
        return try {
            val snapshot = usersRef.child(uid).get().await()
            snapshot.exists()
        } catch (e: Exception) {
            false
        }
    }

    // ========================================================================
    // SECTION 4: UTILITY FUNCTIONS
    // ========================================================================

    /**
     * Hashes a password using SHA-256 for safe storage.
     * NEVER store plaintext passwords, even in Firebase.
     */
    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
