package com.example.thefinaltransfer.presentation.logins.mobilelogin

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
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
fun MobileLoginScreen(navHostController: NavHostController) {
    // Unidirectional State Hoisting Matrix ensuring a single source of truth
    var primaryIdentifier by remember { mutableStateOf("") }
    var securePassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isProcessing by remember { mutableStateOf(false) }

    // System Service Providers for asynchronous execution and input management
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    // Memory-optimized Skia Render Brushes allocated once per composition
    val backgroundBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFFF9F2),
                Color(0xFFFFF0E0),
                Color(0xFFFFE4C4)
            )
        )
    }

    val activeActionBrush = remember {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFFF7A00),
                Color(0xFFFF5000)
            )
        )
    }

    val disabledActionBrush = remember {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFB0BEC5),
                Color(0xFF90A4AE)
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusManager.clearFocus()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Navigate back to previous screen",
                        tint = Color(0xFFFF7A00),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Back",
                    color = Color(0xFFFF7A00),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { navHostController.popBackStack() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                    imageVector = Icons.Default.Call,
                    contentDescription = "Brand Authentication Icon",
                    tint = Color(0xFFFF7A00),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Secure System\nAccess",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.85f),
                    lineHeight = 34.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Provide your credentials to establish a secure session.",
                fontSize = 15.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Primary Identifier Input Module with customized drawable integration
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = buildAnnotatedString {
                        append("Registered Identifier ")
                        withStyle(style = SpanStyle(color = Color.Red)) { append("*") }
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )

                OutlinedTextField(
                    value = primaryIdentifier,
                    onValueChange = { input ->
                        if (input.length <= 50) primaryIdentifier = input
                    },
                    placeholder = {
                        Text("0123456789", color = Color.Gray.copy(alpha = 0.5f))
                    },
                    leadingIcon = {
                        // Explicit implementation of the requested custom drawable replacement
                        Icon(
                            painter = painterResource(id = R.drawable.mobileicon),
                            contentDescription = "Mobile Icon",
                            tint = Color(0xFFFF7A00).copy(alpha = 0.6f),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFFFF7A00),
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        cursorColor = Color(0xFFFF7A00)
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    enabled =!isProcessing
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Secure Cryptographic Password Input Module mapping password visual transforms
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = buildAnnotatedString {
                        append("Secure Password ")
                        withStyle(style = SpanStyle(color = Color.Red)) { append("*") }
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )

                OutlinedTextField(
                    value = securePassword,
                    onValueChange = { securePassword = it },
                    placeholder = {
                        Text("Enter your password", color = Color.Gray.copy(alpha = 0.5f))
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.lock),
                            contentDescription = "Lock Icon",
                            tint = Color(0xFFFF7A00).copy(alpha = 0.6f),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        val visibilityIcon = if (isPasswordVisible) {
                            Icons.Outlined.Visibility
                        } else {
                            Icons.Outlined.VisibilityOff
                        }

                        IconButton(onClick = { isPasswordVisible =!isPasswordVisible }) {
                            Icon(
                                imageVector = visibilityIcon,
                                contentDescription = if (isPasswordVisible) "Mask password" else "Reveal password",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFFFF7A00),
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        cursorColor = Color(0xFFFF7A00)
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        autoCorrect = false
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    singleLine = true,
                    enabled =!isProcessing
                )
            }

            Spacer(modifier = Modifier.height(40.dp))


            // Multi-State Asynchronous Execution Button routing logical transitions
            val isFormValid = primaryIdentifier.isNotBlank() && securePassword.isNotBlank()

            Button(
                onClick = {
                    if (isFormValid) {
                        focusManager.clearFocus()

                        coroutineScope.launch {
                            isProcessing = true
                            delay(600)
                            delay(800)
                            delay(400)
                            isProcessing = false
                            navHostController.navigate(Routes.LoginOTPScreen)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    //.padding(bottom = 32.dp)
                    .shadow(
                        elevation = if (isFormValid &&!isProcessing) 8.dp else 0.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = Color(0xFFFF7A00)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                enabled =!isProcessing && isFormValid
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(if (isFormValid &&!isProcessing) activeActionBrush else disabledActionBrush),
                    contentAlignment = Alignment.Center
                ) {
                    // RESOLUTION: Explicitly qualifying the package path to bypass implicit receiver ambiguity

                    // State 1: Active Processing Display
                    androidx.compose.animation.AnimatedVisibility(
                        visible = isProcessing,
                        enter = fadeIn(animationSpec = tween(300)),
                        exit = fadeOut(animationSpec = tween(300))
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // State 2: Idle Action Display
                    androidx.compose.animation.AnimatedVisibility(
                        visible =!isProcessing,
                        enter = fadeIn(animationSpec = tween(300)),
                        exit = fadeOut(animationSpec = tween(300))
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Proceed",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                contentDescription = "Proceed to Next Phase",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}