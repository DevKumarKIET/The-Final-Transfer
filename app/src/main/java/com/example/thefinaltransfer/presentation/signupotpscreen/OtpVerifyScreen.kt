package com.example.thefinaltransfer.presentation.signupotpscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navigation.Routes

@Composable
fun RegisterOtpScreen(navHostController: NavHostController) {
    // --- State Management ---
    var otpValue by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf(VerificationMethod.MOBILE) }

    // State for the "Send OTP" button
    // It starts FALSE (Not sent yet -> Bright Button)
    // Becomes TRUE (Sent -> Faded Button)
    var isOtpSent by remember { mutableStateOf(false) }

    // Logic for "Verify & Continue" button
    // It starts FALSE (Not filled -> Faded Button)
    // Becomes TRUE (Filled -> Bright Button)
    val isVerifyActive = otpValue.length == 6

    // Background Gradient
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
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = colorResource(id = R.color.gradient_start),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Back",
                    color = colorResource(id = R.color.gradient_start),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { navHostController.navigate(Routes.SignUpScreen) }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- 2. Header ---
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
                    tint = colorResource(id = R.color.brand_orange),
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Verification",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            )

            // Step Indicator
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                color = colorResource(id = R.color.brand_orange).copy(alpha = 0.1f),
                shape = CircleShape
            ) {
                Text(
                    text = "Step 2 of 4",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.brand_orange),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. Method Selector ---
            Text(
                text = "Send verification code to:",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                SelectionCard(
                    text = "Mobile",
                    icon = Icons.Outlined.Phone,
                    isSelected = selectedMethod == VerificationMethod.MOBILE,
                    onClick = { selectedMethod = VerificationMethod.MOBILE },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                SelectionCard(
                    text = "Email",
                    icon = Icons.Outlined.Email,
                    isSelected = selectedMethod == VerificationMethod.EMAIL,
                    onClick = { selectedMethod = VerificationMethod.EMAIL },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- 4. NEW: Send OTP Button ---
            // Visual Logic: Bright if NOT sent yet. Faded if SENT.
            // Click Logic: Sets isOtpSent to true.
            StateButton(
                text = if (isOtpSent) "OTP Sent" else "Send OTP",
                isBright =!isOtpSent,
                onClick = { isOtpSent = true }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- 5. OTP Input ---
            Text(
                text = "Enter 6-digit code",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(12.dp))

            OtpInputField(
                otpText = otpValue,
                onOtpChange = { if (it.length <= 6) otpValue = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Didn't receive code? Resend",
                fontSize = 14.sp,
                color = colorResource(id = R.color.brand_orange),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    // Reset to allow sending again
                    isOtpSent = false
                    otpValue = ""
                }
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))

            // --- 6. Verify & Continue Button ---
            // Visual Logic: Faded if empty. Bright if Filled.
            StateButton(
                text = "Verify & Continue",
                isBright = isVerifyActive,
                onClick = { navHostController.navigate(Routes.CreatePasswordScreen) }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// --- Enum ---
enum class VerificationMethod { MOBILE, EMAIL }

// --- Reusable Components ---

@Composable
fun StateButton(
    text: String,
    isBright: Boolean, // True = Gradient, False = Faded
    onClick: () -> Unit
) {
    val startColor = colorResource(id = R.color.gradient_start)
    val endColor = colorResource(id = R.color.gradient_end)

    val buttonBrush = if (isBright) {
        Brush.horizontalGradient(listOf(startColor, endColor))
    } else {
        // Faded version
        Brush.horizontalGradient(
            listOf(startColor.copy(alpha = 0.4f), endColor.copy(alpha = 0.4f))
        )
    }

    Button(
        onClick = onClick,
        // We keep it 'enabled' so the click registers, but visual changes handle the look
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = if (isBright) 8.dp else 0.dp,
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
                    color = if (isBright) Color.White else Color.White.copy(alpha = 0.8f)
                )
                if (isBright) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun SelectionCard(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) colorResource(id = R.color.brand_orange) else Color.Transparent
    val bgColor = if (isSelected) Color(0xFFFFF4E3) else Color.White
    val contentColor = if (isSelected) colorResource(id = R.color.brand_orange) else Color.Gray

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if(isSelected) 0.dp else 2.dp),
        modifier = modifier.border(1.dp, borderColor, RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontWeight = FontWeight.SemiBold, color = contentColor, fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun OtpInputField(
    otpText: String,
    onOtpChange: (String) -> Unit
) {
    BasicTextField(
        value = otpText,
        onValueChange = onOtpChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                repeat(6) { index ->
                    val char = when {
                        index < otpText.length -> otpText[index].toString()
                        else -> ""
                    }
                    val isFocused = index == otpText.length

                    val borderColor = if (isFocused || char.isNotEmpty()) colorResource(id = R.color.brand_orange) else Color(0xFFE0E0E0)

                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .border(1.dp, borderColor, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.brand_orange),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}
