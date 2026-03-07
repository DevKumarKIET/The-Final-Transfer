package com.example.thefinaltransfer.presentation.signupscreen

import android.util.Patterns
import androidx.annotation.DrawableRes
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
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navigation.Routes

@Composable
fun RegisterPersonalDetailsScreen(navHostController: NavHostController) {
    // --- State Management Architecture ---
    // Utilizing remember blocks to hoist text state across recompositions
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }

    // Core Focus Manager retrieved from the CompositionLocal provider
    // Critical for orchestrating sequential traversal and keyboard dismissal
    val focusManager = LocalFocusManager.current

    // --- Dynamic Validation Heuristics ---
    // Continuously evaluating input to determine the CTA button state.
    // Name requires a minimum length to be considered valid natively.
    val isNameValid = fullName.trim().length >= 3
    // Email utilizes the Android OS standard regex pattern matcher.
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    // Mobile enforces a strict 10-digit numeric requirement.
    val isMobileValid = mobile.length == 10 && mobile.all { it.isDigit() }

    // The master validation flag governing the Continue button.
    val isFormValid = isNameValid && isEmailValid && isMobileValid

    // --- UI Asset Generation ---
    val bgBrush = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.pastel_bg_top),
            colorResource(id = R.color.pastel_bg_mid),
            colorResource(id = R.color.pastel_bg_bot)
        )
    )

    // The root layout container establishes the global touch interceptor.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
            // Gesture interception layer: Tapping anywhere outside a specific
            // active UI component will trigger this block, clearing all focus
            // and commanding the OS to retract the software keyboard.
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                // Ensures the layout remains accessible if the keyboard obscures data
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- 1. Top Navigation Bar ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Navigate Back",
                    tint = colorResource(id = R.color.gradient_start),
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable { navHostController.navigate(Routes.LoginScreen) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Back",
                    color = colorResource(id = R.color.gradient_start),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { navHostController.navigate(Routes.LoginScreen) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- 2. Branding and Header Section ---
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFFFF4E3))
                    .border(1.dp, Color(0xFFFFDCC1), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile Icon",
                    tint = colorResource(id = R.color.brand_orange),
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Personal Details",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            )

            Text(
                text = "Complete your profile information",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Form Progression Indicator
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                color = colorResource(id = R.color.brand_orange).copy(alpha = 0.1f),
                shape = CircleShape
            ) {
                Text(
                    text = "Step 1 of 4",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.brand_orange),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- 3. Sequential Form Fields ---
            // Field 1: Full Name. Implements ImeAction.Next to push focus downwards.
            CustomInputField(
                label = "Full Name",
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = "Enter your full name",
                iconVector = Icons.Outlined.Person, // Utilizing standard Material Vector
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next,
                focusManager = focusManager,
                isValid = isNameValid || fullName.isEmpty() // Suppress initial empty error
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Field 2: Email Address. Integrates custom drawable request.
            // Notice the use of iconDrawable instead of iconVector.
            CustomInputField(
                label = "Email Address",
                value = email,
                onValueChange = { email = it },
                placeholder = "name@example.com",
                iconDrawable = R.drawable.emailicon_tft, // Integration of custom asset
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next, // Pushes focus down to Mobile field
                focusManager = focusManager,
                isValid = isEmailValid || email.isEmpty()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Field 3: Mobile Number. The terminal field in the sequence.
            CustomInputField(
                label = "Mobile Number",
                value = mobile,
                // Input sanitizer to block non-numeric characters at the view layer
                onValueChange = { if (it.length <= 10 && it.all { char -> char.isDigit() }) mobile = it },
                placeholder = "9876543210",
                iconVector = Icons.Outlined.Phone,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done, // Renders the checkmark/done button on IME
                focusManager = focusManager,
                isValid = isMobileValid || mobile.isEmpty()
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(40.dp))

            // --- 4. Dynamic Call-To-Action Button ---
            ValidationGradientButton(
                text = "Continue",
                isEnabled = isFormValid,
                onClick = {
                    // Absolute guarantee that keyboard collapses prior to route change
                    focusManager.clearFocus()
                    navHostController.navigate(Routes.SignUpOTPScreen)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// --- Architecturally Enhanced Reusable Components ---

@Composable
fun CustomInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    iconVector: ImageVector? = null,
    @DrawableRes iconDrawable: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    imeAction: ImeAction,
    focusManager: FocusManager,
    isValid: Boolean = true
) {
    // Localized state to track if this specific component holds the OS focus
    var isFocused by remember { mutableStateOf(false) }

    // Smooth, tween-based color transitions for borders to replicate high-end UX
    val brandOrange = colorResource(id = R.color.brand_orange)
    val borderColor by animateColorAsState(
        targetValue = when {
            isFocused -> brandOrange // Active state
            !isValid && value.isNotEmpty() -> Color.Red.copy(alpha = 0.6f) // Error state
            else -> Color(0xFFE0E0E0) // Neutral/Idle state
        },
        animationSpec = tween(durationMillis = 300)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        // Label construction with required asterisk indicator
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
                // Conditional logic to support both standard vectors and custom drawables
                val tintColor = if (isFocused) brandOrange else Color.Gray.copy(alpha = 0.6f)
                if (iconVector!= null) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = "Input Icon",
                        tint = tintColor
                    )
                } else if (iconDrawable!= null) {
                    Icon(
                        painter = painterResource(id = iconDrawable),
                        contentDescription = "Custom Input Icon",
                        tint = tintColor,
                        modifier = Modifier.size(24.dp) // Standardized scaling for drawables
                    )
                }
            },
            trailingIcon = {
                // Renders a positive reinforcement checkmark when data is valid
                // Requires the field to have lost focus to prevent premature validation
                if (isValid && value.isNotEmpty() &&!isFocused) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = "Validation Success",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                // Focus state observer crucial for driving the visual validation logic
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = brandOrange,
                errorBorderColor = Color.Red.copy(alpha = 0.6f)
            ),
            isError =!isValid && value.isNotEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                capitalization = capitalization,
                imeAction = imeAction
            ),
            // The central mechanism for sequential traversal orchestration
            keyboardActions = KeyboardActions(
                onNext = {
                    // Instructs the OS to find the next focusable node in the layout tree
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    // Instructs the OS to collapse the keyboard upon final field completion
                    focusManager.clearFocus()
                }
            ),
            singleLine = true
        )

        // Contextual error messaging displayed only upon focus loss (blur)
        if (!isValid && value.isNotEmpty() &&!isFocused) {
            Text(
                text = "Please enter a valid $label format.",
                color = Color.Red.copy(alpha = 0.8f),
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}

@Composable
private fun ValidationGradientButton(
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val startColor = colorResource(id = R.color.gradient_start)
    val endColor = colorResource(id = R.color.gradient_end)

    // Dynamically calculating the brush gradient based on external validation state
    val buttonBrush = if (isEnabled) {
        Brush.horizontalGradient(listOf(startColor, endColor))
    } else {
        Brush.horizontalGradient(
            listOf(
                startColor.copy(alpha = 0.4f),
                endColor.copy(alpha = 0.4f)
            )
        )
    }

    Button(
        onClick = { if (isEnabled) onClick() },
        enabled = true, // Visual state is handled manually to bypass default gray-out
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = if (isEnabled) 8.dp else 0.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = startColor
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