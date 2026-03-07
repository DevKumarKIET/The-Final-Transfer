package com.example.thefinaltransfer.presentation.createpasswordscreen


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navigation.Routes

// --- Defined Colors for consistency without XML dependency ---
private val PastelTop = Color(0xFFFFF7ED)
private val PastelMid = Color(0xFFFFFBEB)
private val PastelBot = Color(0xFFFFEDD4)
private val BrandOrange = Color(0xFFE26A2C)
private val GradientStart = Color(0xFFFF9F45)
private val GradientEnd = Color(0xFFFFB703)
private val SuccessGreen = Color(0xFF4CAF50)

@Composable
fun CreatePasswordScreen(navHostController: NavHostController?) {
    // --- State Management ---
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Visibility Toggles
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    // --- Dynamic Security Validation Rules ---
    val hasMinLength = password.length >= 8
    val hasUppercase = password.any { it.isUpperCase() }
    val hasNumber = password.any { it.isDigit() }
    val hasSpecialChar = password.any {!it.isLetterOrDigit() }
    val passwordsMatch = password == confirmPassword && password.isNotEmpty()

    // The button is only enabled when ALL security conditions and matching conditions are met
    val isFormValid = hasMinLength && hasUppercase && hasNumber && hasSpecialChar && passwordsMatch

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PastelTop, PastelMid, PastelBot)))
            // Smart Gesture: Tapping anywhere outside the inputs collapses the keyboard
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- 1. Top Bar ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = GradientStart,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable { navHostController?.popBackStack() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Back",
                    color = GradientStart,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { navHostController?.popBackStack() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- 2. Header Section ---
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFFFF4E3))
                    .border(1.dp, Color(0xFFFFDCC1), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.lock),
                    contentDescription = "Security",
                    tint = BrandOrange,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Secure Your Vault",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            )

            Text(
                text = "Create a strong password to protect your legacy",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Step Indicator
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                color = BrandOrange.copy(alpha = 0.1f),
                shape = CircleShape
            ) {
                Text(
                    text = "Step 3 of 4",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BrandOrange,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. Form Fields ---

            // Create Password Field
            PasswordInputField(
                label = "Create Password",
                value = password,
                onValueChange = { password = it },
                placeholder = "Enter a strong password",
                isVisible = isPasswordVisible,
                onVisibilityToggle = { isPasswordVisible =!isPasswordVisible },
                imeAction = ImeAction.Next,
                focusManager = focusManager,
                isValid = password.isEmpty() || (hasMinLength && hasUppercase && hasNumber && hasSpecialChar)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Animated Password Strength Checklist ---
            // This builds user trust and provides gamified, instant feedback
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                PasswordRequirementRow(text = "At least 8 characters", isMet = hasMinLength)
                PasswordRequirementRow(text = "Contains an uppercase letter", isMet = hasUppercase)
                PasswordRequirementRow(text = "Contains a number", isMet = hasNumber)
                PasswordRequirementRow(text = "Contains a special character (!@#\$%)", isMet = hasSpecialChar)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Confirm Password Field
            PasswordInputField(
                label = "Confirm Password",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Re-enter your password",
                isVisible = isConfirmPasswordVisible,
                onVisibilityToggle = { isConfirmPasswordVisible =!isConfirmPasswordVisible },
                imeAction = ImeAction.Done, // Shows the "Done/Check" button on keyboard
                focusManager = focusManager,
                isMatching = passwordsMatch, // Renders a green check when they match perfectly
                isValid = confirmPassword.isEmpty() || confirmPassword == password
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(40.dp))

            // --- 4. Dynamic Gradient Button ---
            ValidationGradientButton(
                text = "Continue",
                isEnabled = isFormValid,
                onClick = {
                    focusManager.clearFocus()
                    navHostController?.navigate(Routes.BiometricAuthenticationScreen)
                }
            )
        }
    }
}

// --- Reusable Advanced UI Components ---

@Composable
fun PasswordInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isVisible: Boolean,
    onVisibilityToggle: () -> Unit,
    imeAction: ImeAction,
    focusManager: FocusManager,
    isValid: Boolean,
    isMatching: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    // Smooth color transitions for borders
    val borderColor by animateColorAsState(
        targetValue = when {
            isMatching -> SuccessGreen // Priority: Match success
            isFocused -> BrandOrange
            !isValid && value.isNotEmpty() -> Color.Red.copy(alpha = 0.6f)
            else -> Color(0xFFE0E0E0)
        },
        animationSpec = tween(300), label = "border_color"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            buildAnnotatedString {
                append(label)
                withStyle(SpanStyle(color = Color.Red)) { append(" *") }
            },
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray.copy(alpha = 0.5f)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.lock),
                    contentDescription = null,
                    tint = if (isFocused) BrandOrange else Color.Gray.copy(alpha = 0.6f)
                )
            },
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Show a success checkmark if the confirmation password matches
                    if (isMatching) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = "Matched",
                            tint = SuccessGreen,
                            modifier = Modifier.padding(end = 8.dp).size(20.dp)
                        )
                    }

                    // The Eye Icon for visibility toggle
                    IconButton(onClick = onVisibilityToggle) {
                        Icon(
                            imageVector = if (isVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = if (isVisible) "Hide password" else "Show password",
                            tint = Color.Gray
                        )
                    }
                }
            },
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = BrandOrange
            ),
            isError =!isValid && value.isNotEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true
        )

        // Error text (Only shown when field loses focus and is invalid)
        if (!isValid && value.isNotEmpty() &&!isFocused) {
            Text(
                text = "Passwords do not match.",
                color = Color.Red.copy(alpha = 0.8f),
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun PasswordRequirementRow(text: String, isMet: Boolean) {
    // Animate color transition when requirement is met
    val tint by animateColorAsState(
        targetValue = if (isMet) SuccessGreen else Color.Gray.copy(alpha = 0.5f),
        animationSpec = tween(400), label = "icon_color"
    )

    val textColor by animateColorAsState(
        targetValue = if (isMet) Color(0xFF1A1A1A) else Color.Gray,
        animationSpec = tween(400), label = "text_color"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = if (isMet) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 13.sp,
            color = textColor
        )
    }
}

@Composable
private fun ValidationGradientButton(
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val buttonBrush = if (isEnabled) {
        Brush.horizontalGradient(listOf(GradientStart, GradientEnd))
    } else {
        Brush.horizontalGradient(
            listOf(GradientStart.copy(alpha = 0.4f), GradientEnd.copy(alpha = 0.4f))
        )
    }

    Button(
        onClick = { if (isEnabled) onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = if (isEnabled) 8.dp else 0.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = GradientStart
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(buttonBrush),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isEnabled) Color.White else Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = null,
                    tint = if (isEnabled) Color.White else Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}
