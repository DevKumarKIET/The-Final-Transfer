package com.example.thefinaltransfer.data.repository

import com.example.thefinaltransfer.data.model.TrustedUserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TrustedUserRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val trustedRef = database.getReference("trusted_nominees")

    val currentUserId: String?
        get() = auth.currentUser?.uid

    /**
     * Retrieve all trusted users for the current owner, sorted by priority.
     */
    fun getTrustedUsers(): Flow<List<TrustedUserModel>> = callbackFlow {
        val uid = currentUserId
        if (uid == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = mutableListOf<TrustedUserModel>()
                for (child in snapshot.children) {
                    val user = child.getValue(TrustedUserModel::class.java)
                    if (user != null) {
                        users.add(user)
                    }
                }
                // Always sort by priority_order
                users.sortBy { it.priorityOrder }
                trySend(users)
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(emptyList())
            }
        }

        trustedRef.child(uid).addValueEventListener(listener)

        awaitClose {
            trustedRef.child(uid).removeEventListener(listener)
        }
    }

    suspend fun addTrustedUser(nominee: TrustedUserModel): AuthResult {
        return try {
            val uid = currentUserId ?: return AuthResult.Error("Not Authenticated")
            val newRef = trustedRef.child(uid).push()
            val nomineeId = newRef.key ?: return AuthResult.Error("Failed to generate ID")

            val newNominee = nominee.copy(id = nomineeId)
            newRef.setValue(newNominee).await()
            AuthResult.Success("Trusted User Added")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to add trusted user")
        }
    }

    suspend fun removeTrustedUser(nomineeId: String): AuthResult {
        return try {
            val uid = currentUserId ?: return AuthResult.Error("Not Authenticated")
            trustedRef.child(uid).child(nomineeId).removeValue().await()
            AuthResult.Success("User removed")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to remove trusted user")
        }
    }

    suspend fun updateTrustedUser(nominee: TrustedUserModel): AuthResult {
        return try {
            val uid = currentUserId ?: return AuthResult.Error("Not Authenticated")
            trustedRef.child(uid).child(nominee.id).setValue(nominee).await()
            AuthResult.Success("User updated")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to update trusted user")
        }
    }
}
