package com.example.thefinaltransfer.presentation.logins.emaillogin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EmailLoginScreen(navHostController: NavHostController) {
    // --- State Encapsulation ---
    var emailAddress by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isAuthenticating by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }

    // --- Focus & Scroll Management ---
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll to bottom when error appears so the user can see it
    LaunchedEffect(showError) {
        if (showError) {
            delay(100) // Slight delay to allow UI to render the box first
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    val bgBrush = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.pastel_bg_top),
            colorResource(id = R.color.pastel_bg_mid),
            colorResource(id = R.color.pastel_bg_bot)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
            // Tap outside to dismiss keyboard
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                // SCROLL FIX: Allows the screen to be scrollable
                .verticalScroll(scrollState)
                // IME PADDING FIX: Pushes content up when keyboard opens
                .imePadding()
                .animateContentSize(
                    animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // --- Top Navigation ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = colorResource(id = R.color.gradient_start),
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable { navHostController.popBackStack() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Back",
                    color = colorResource(id = R.color.gradient_start),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { navHostController.popBackStack() }
                )
            }

            Spacer(modifier = Modifier.height(34.dp))

            // --- Header Icon ---
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .border(
                        width = 1.5.dp,
                        color = colorResource(id = R.color.brand_orange).copy(alpha = 0.4f),
                        shape = RoundedCornerShape(28.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Email, // Standard icon to prevent preview crashes
                    contentDescription = "Email",
                    tint = colorResource(id = R.color.brand_orange),
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- Title ---
            Text(
                text = "Secure System\nAccess",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1E),
                    lineHeight = 40.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Provide your credentials to establish a secure session.",
                fontSize = 16.sp,
                color = colorResource(id = R.color.gray_text),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.dp))

            // --- Email Input ---
            InputFieldLabel(text = "Registered Email Address", isMandatory = true)
            var isEmailFocused by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = emailAddress,
                onValueChange = {
                    emailAddress = it
                    showError = false
                },
                placeholder = { Text("contact@thefinaltransfer.com", color = Color.Gray.copy(alpha = 0.5f)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Email",
                        // Retain color if focused OR filled
                        tint = if (isEmailFocused || emailAddress.isNotEmpty()) colorResource(id = R.color.brand_orange) else Color.Gray.copy(alpha = 0.6f),
                    modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(emailFocusRequester)
                    .onFocusChanged { isEmailFocused = it.isFocused },
                shape = RoundedCornerShape(16.dp),
                colors = CustomTextFieldColors(isFilled = emailAddress.isNotEmpty()),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { passwordFocusRequester.requestFocus() }
                ),
                singleLine = true,
                enabled =!isAuthenticating
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Password Input ---
            InputFieldLabel(text = "Vault Password", isMandatory = true)
            var isPasswordFocused by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    showError = false
                },
                placeholder = { Text("Enter your password", color = Color.Gray.copy(alpha = 0.5f)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Password",
                        tint = if (isPasswordFocused || password.isNotEmpty()) colorResource(id = R.color.brand_orange)
                        else Color.Gray.copy(alpha = 0.6f)
                    )
                },
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
                    IconButton(
                        onClick = { passwordVisible =!passwordVisible },
                        modifier = Modifier.semantics {
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        }
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.Gray.copy(alpha = 0.8f)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester)
                    .onFocusChanged { isPasswordFocused = it.isFocused },
                shape = RoundedCornerShape(16.dp),
                colors = CustomTextFieldColors(isFilled = password.isNotEmpty()),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        if (emailAddress.isBlank() || password.isBlank()) {
                        showError = true
                    }
                    }
                ),
                singleLine = true,
                enabled =!isAuthenticating
            )

            // Forgot Password
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Forgot Password?",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.brand_orange),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .clickable(enabled =!isAuthenticating) { /* Recovery */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Error Manifestation ---
            // Because this is inside a ColumnScope, AnimatedVisibility works perfectly here.
            AnimatedVisibility(
                visible = showError,
                enter = expandVertically() + fadeIn(animationSpec = tween(300)),
                exit = shrinkVertically() + fadeOut(animationSpec = tween(300))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFFF0F0))
                        .border(1.dp, Color(0xFFFFCDCD), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Error",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Invalid credentials. Please ensure your registered email and vault password are correct.",
                        fontSize = 14.sp,
                        color = Color(0xFFB71C1C),
                        lineHeight = 18.sp
                    )
                }
            }

            if(showError) Spacer(modifier = Modifier.height(24.dp))

            // Spacer to push button to the bottom area smoothly
            Spacer(modifier = Modifier.weight(1f, fill = false))

            // --- Crossfade Gradient Button ---
            val buttonBrush = Brush.horizontalGradient(
                colors = listOf(
                    colorResource(id = R.color.gradient_start),
                    colorResource(id = R.color.gradient_end)
                )
            )

            Button(
                onClick = {
                    focusManager.clearFocus()
                    if (emailAddress.isBlank() || password.isBlank()) {
                    showError = true
                } else {
                    isAuthenticating = true
                    showError = false

                    coroutineScope.launch {
                        delay(2000) // Simulate Auth
                        isAuthenticating = false

                        navHostController.navigate(Routes.LoginOTPScreen)
                    }
                }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = if (isAuthenticating) 2.dp else 12.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = colorResource(id = R.color.gradient_start)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                enabled =!isAuthenticating
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(if (isAuthenticating) SolidColor(Color.Gray.copy(alpha = 0.5f)) else buttonBrush),
                    contentAlignment = Alignment.Center
                ) {
                    // FIX: Replaced AnimatedVisibility with Crossfade for a flawless button transition
                    Crossfade(
                        targetState = isAuthenticating,
                        animationSpec = tween(300),
                        label = "AuthButtonState"
                    ) { loading ->
                        if (loading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 3.dp,
                                modifier = Modifier.size(28.dp)
                            )
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Proceed",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputFieldLabel(text: String, isMandatory: Boolean) {
    Text(
        text = buildAnnotatedString {
            append("$text ")
            if (isMandatory) {
                withStyle(style = SpanStyle(color = Color(0xFFD32F2F))) { append("*") }
            }
        },
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF424242),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, bottom = 8.dp),
        textAlign = TextAlign.Start
    )
}

// --- Field Color Resolution Logic ---
@Composable
fun CustomTextFieldColors(isFilled: Boolean): TextFieldColors {
    val brandOrange = colorResource(id = R.color.brand_orange)
    val defaultUnfocusedBorder = Color(0xFFE0E0E0)

    // If the field is filled but not focused, keep it orange. Otherwise, gray.
    val resolvedUnfocusedBorder = if (isFilled) brandOrange else defaultUnfocusedBorder

    return OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
        disabledContainerColor = Color.White.copy(alpha = 0.5f),
        focusedBorderColor = brandOrange,
        unfocusedBorderColor = resolvedUnfocusedBorder,
        errorBorderColor = Color(0xFFD32F2F),
        cursorColor = brandOrange,
        focusedTextColor = Color(0xFF1C1C1E),
        unfocusedTextColor = Color(0xFF1C1C1E)
    )
}