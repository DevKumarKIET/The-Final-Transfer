package com.example.thefinaltransfer.presentation.logins.loginotpscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private object OtpScreenTokens {
    const val OTP_LENGTH = 6
    val BRAND_ORANGE = Color(0xFFFF9F45)
    val BRAND_YELLOW = Color(0xFFFFB703)
    val TEXT_PRIMARY = Color(0xFF1C1C1E)
    val TEXT_SECONDARY = Color(0xFF757575)
    val BG_SURFACE = Color.White
    val ERROR_RED = Color(0xFFD32F2F)
    val ERROR_BG = Color(0xFFFFF0F0)
    val BORDER_DEFAULT = Color(0xFFE0E0E0)

    val ACTIVE_BRUSH = Brush.horizontalGradient(listOf(BRAND_ORANGE, BRAND_YELLOW))
    val DISABLED_BRUSH = Brush.horizontalGradient(
        listOf(BRAND_ORANGE.copy(alpha = 0.5f), BRAND_YELLOW.copy(alpha = 0.5f))
    )
    val BACKGROUND_BRUSH = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFF7ED), Color(0xFFFFFBEB), Color(0xFFFFEDD4))
    )
}

@Composable
fun LoginOtpScreen(navHostController: NavHostController?) {
    var otpState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = "", selection = TextRange(0)))
    }

    var isVerifying by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val isOtpComplete = otpState.text.length == OtpScreenTokens.OTP_LENGTH

    // Scroll mechanics cleanly isolated from UI generation
    LaunchedEffect(showError) {
        if (showError) {
            delay(100)
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OtpScreenTokens.BACKGROUND_BRUSH)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .systemBarsPadding()
                .verticalScroll(scrollState)
                .imePadding()
                .animateContentSize(animationSpec = tween(400, easing = FastOutSlowInEasing)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OtpTopAppBar(onBackClick = { navHostController?.popBackStack() })

            Spacer(modifier = Modifier.height(34.dp))

            SecurityHeaderIcon()

            Spacer(modifier = Modifier.height(28.dp))

            HeaderTypography()

            Spacer(modifier = Modifier.height(48.dp))

            // Problem 1 Fixed: Responsive geometric constraints applied internally.
            OtpInputGrid(
                otpState = otpState,
                showError = showError,
                onOtpChange = { newValue ->
                    // Restrict input matrix length to prevent buffer overflow and visual breaking
                    if (newValue.text.length <= OtpScreenTokens.OTP_LENGTH && newValue.text.all { it.isDigit() }) {
                        // Mathematically force the cursor strictly to the end of the text string
                        // to prevent mid-string insertion bugs and maintain IME parity.
                        val safeValue = newValue.copy(selection = TextRange(newValue.text.length))
                        otpState = safeValue
                        showError = false

                        // Close keyboard smoothly on completion
                        if (safeValue.text.length == OtpScreenTokens.OTP_LENGTH) {
                            focusManager.clearFocus()
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            ResendActionLink(enabled =!isVerifying, onResendClick = { /* Network logic */ })

            Spacer(modifier = Modifier.height(24.dp))

            ErrorManifestation(showError = showError)

            Spacer(modifier = Modifier.weight(1f, fill = false))
            Spacer(modifier = Modifier.height(24.dp))

            // Problem 3 Fixed: Action component integrates dynamic shadow mitigation.
            ActionGradientButton(
                isEnabled = isOtpComplete,
                isLoading = isVerifying,
                onClick = {
                    focusManager.clearFocus()
                    if (isOtpComplete) {
                        isVerifying = true
                        showError = false

                        coroutineScope.launch {
                            delay(2000) // Simulated backend network handshake
                            isVerifying = false
                            navHostController?.navigate(Routes.HomeScreen)
                        }
                    } else {
                        showError = true
                    }
                }
            )
        }
    }
}

/**
 * Encapsulated Top Navigation Component
 */
@Composable
private fun OtpTopAppBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = "Navigate Back",
            tint = OtpScreenTokens.BRAND_ORANGE,
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(50))
                .clickable(onClick = onBackClick)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Back",
            color = OtpScreenTokens.BRAND_ORANGE,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable(onClick = onBackClick)
        )
    }
}

/**
 * Encapsulated Security Icon Design Element
 * Contains complex shadow casting geometries isolated from the main flow.
 */
@Composable
private fun SecurityHeaderIcon() {
    Box(
        modifier = Modifier
            .size(88.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = OtpScreenTokens.BRAND_ORANGE,
                spotColor = OtpScreenTokens.BRAND_ORANGE.copy(alpha = 0.5f),
                clip = false
            )
            .background(OtpScreenTokens.BG_SURFACE, RoundedCornerShape(28.dp))
            .border(
                width = 1.5.dp,
                color = OtpScreenTokens.BRAND_ORANGE.copy(alpha = 0.4f),
                shape = RoundedCornerShape(28.dp)
            )
            .clip(RoundedCornerShape(28.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.VpnKey,
            contentDescription = "Security Authentication Key",
            tint = OtpScreenTokens.BRAND_ORANGE,
            modifier = Modifier.size(42.dp)
        )
    }
}

/**
 * Encapsulated Typography Matrix
 */
@Composable
private fun HeaderTypography() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Verification Code",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = OtpScreenTokens.TEXT_PRIMARY,
                lineHeight = 40.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please enter the 6-digit verification code sent to your registered contact.",
            fontSize = 15.sp,
            color = OtpScreenTokens.TEXT_SECONDARY,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

/**
 * Responsive OTP Input Field Grid
 * Solves absolute geometry issues via dynamic weight and aspect ratio calculations.
 */
@Composable
private fun OtpInputGrid(
    otpState: TextFieldValue,
    showError: Boolean,
    onOtpChange: (TextFieldValue) -> Unit
) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = otpState,
        onValueChange = onOtpChange,
        keyboardOptions = KeyboardOptions(
            // Explicitly requesting standard Number type resolves deeply embedded backspace bugs
            // present on older OEM software keyboards when using Password flags.
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(OtpScreenTokens.OTP_LENGTH) { index ->
                    val char = when {
                        index < otpState.text.length -> otpState.text[index].toString()
                        else -> ""
                    }

                    val isFocused = otpState.text.length == index
                    val hasValue = char.isNotEmpty()

                    val borderColor by animateColorAsState(
                        targetValue = when {
                            showError -> OtpScreenTokens.ERROR_RED.copy(alpha = 0.6f)
                            isFocused || hasValue -> OtpScreenTokens.BRAND_ORANGE
                            else -> OtpScreenTokens.BORDER_DEFAULT
                        },
                        animationSpec = tween(300),
                        label = "otp_border_color_anim"
                    )

                    // Adaptive Constraints: Weight determines variable width,
                    // aspectRatio forces the height to match identically for perfect squares.
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(OtpScreenTokens.BG_SURFACE, RoundedCornerShape(14.dp))
                            .border(1.5.dp, borderColor, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = OtpScreenTokens.TEXT_PRIMARY
                        )
                    }
                }
            }
        }
    )
}

/**
 * Interactive Resend Link Component
 */
@Composable
private fun ResendActionLink(enabled: Boolean, onResendClick: () -> Unit) {
    Text(
        text = "Didn't receive the code? Resend",
        fontSize = 14.sp,
        color = if (enabled) OtpScreenTokens.BRAND_ORANGE else OtpScreenTokens.TEXT_SECONDARY,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.clickable(
            enabled = enabled,
            onClick = onResendClick
        )
    )
}

/**
 * Animated Error Banner Topology
 */
@Composable
private fun ErrorManifestation(showError: Boolean) {
    AnimatedVisibility(
        visible = showError,
        enter = expandVertically() + fadeIn(animationSpec = tween(300)),
        exit = shrinkVertically() + fadeOut(animationSpec = tween(300))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(OtpScreenTokens.ERROR_BG)
                .border(1.dp, Color(0xFFFFCDCD), RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Error Information",
                tint = OtpScreenTokens.ERROR_RED,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Invalid verification code. Please check and try again.",
                fontSize = 14.sp,
                color = Color(0xFFB71C1C),
                lineHeight = 18.sp
            )
        }
    }
}

/**
 * Primary Call To Action Component
 * Contains the logic to strip hardware shadow geometries when the button is inactive,
 * preventing dark artifacts from bleeding through the transparent bounding boxes.
 */
@Composable
private fun ActionGradientButton(
    isEnabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    // Dynamic calculation prevents rendering anomalies on the RenderNode canvas.
    val dynamicElevation = if (isEnabled &&!isLoading) 12.dp else 0.dp

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(
                elevation = dynamicElevation,
                shape = RoundedCornerShape(16.dp),
                spotColor = OtpScreenTokens.BRAND_ORANGE
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        enabled =!isLoading
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isEnabled &&!isLoading) OtpScreenTokens.ACTIVE_BRUSH else OtpScreenTokens.DISABLED_BRUSH),
            contentAlignment = Alignment.Center
        ) {
            Crossfade(
                targetState = isLoading,
                animationSpec = tween(durationMillis = 300),
                label = "VerifyButtonCrossfadeState"
            ) { verifying ->
                if (verifying) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Verify & Continue",
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

@Preview(showBackground = true)
@Composable
fun PreviewLoginOtpScreen() {
    LoginOtpScreen(navHostController = null)
}