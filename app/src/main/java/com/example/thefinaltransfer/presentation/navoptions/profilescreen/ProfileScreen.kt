package com.example.thefinaltransfer.presentation.navoptions.profilescreen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// --- Theme Colors for Profile Screen ---
object ProfileColors {
    val PastelTop = Color(0xFFFFF7ED)
    val PastelMid = Color(0xFFFFFBEB)
    val PastelBot = Color(0xFFFFEDD4)
    val GradientStart = Color(0xFFFF9F45)
    val GradientEnd = Color(0xFFFFB703)
    val BrandOrange = Color(0xFFE26A2C)
    val TextBlack = Color(0xFF1A1A1A)
    val TextGray = Color(0xFF757575)
    val CardBg = Color.White
    val BorderColor = Color(0xFFFFE0B2)
    val ErrorRedBg = Color(0xFFFFF0F0)
    val ErrorRedIcon = Color(0xFFD32F2F)
}

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    // Exact background gradient matching the rest of the application
    val bgBrush = Brush.verticalGradient(
        colors = listOf(
            ProfileColors.PastelTop,
            ProfileColors.PastelMid,
            ProfileColors.PastelBot
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
    ) {
        // --- 1. Curved Premium Header ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(Brush.linearGradient(listOf(
                    Color(0xFFFFD6B0),
                    Color(0xFFFF9F45)
                )))
        ) {

        }

        // --- 2. Scrollable Content ---
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp) // Leave space for the BottomNavBar
        ) {
            // Avatar & Basic Info (Overlapping the header)

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp), // Pushes the avatar down so it sits on the curve
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Profile",
                        style = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 30.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    // Profile Image Container
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .shadow(12.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(4.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        // Using an Icon, but this could easily be replaced with AsyncImage for a real photo
                        Icon(
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = "User Avatar",
                            tint = ProfileColors.GradientStart,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "User Name",
                        style = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProfileColors.TextBlack
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "example@gmail.com",
                        fontSize = 14.sp,
                        color = ProfileColors.TextGray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Edit Profile Action
//                    OutlinedButton(
//                        onClick = { /* Handle Edit */ },
//                        shape = RoundedCornerShape(50.dp),
//                        border = BorderStroke(1.5.dp, ProfileColors.BrandOrange),
//                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ProfileColors.BrandOrange),
//                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
//                    ) {
//                        Text("Edit Profile", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
//                    }
                }
            }

            // --- 3. Account Settings Group ---
            item {
                Spacer(modifier = Modifier.height(32.dp))
                SectionHeading(title = "Account Settings")

                ProfileOptionCard(
                    title = "Personal Details",
                    subtitle = "Manage your name, email, and phone",
                    icon = Icons.Outlined.Badge,
                    onClick = { /* Navigate */ }
                )
                ProfileToggleCard(
                    title = "Biometric Authentication",
                    subtitle = "Use Face ID or Fingerprint to unlock vault",
                    icon = Icons.Outlined.Fingerprint,
                    initialState = true,
                    onToggle = { /* Handle Biometric switch */ }
                )
            }

            // --- 4. Preferences Group ---
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeading(title = "Preferences")

                ProfileOptionCard(
                    title = "Notifications",
                    subtitle = "Configure alert and trigger preferences",
                    icon = Icons.Outlined.NotificationsNone,
                    onClick = { /* Navigate */ }
                )
                ProfileOptionCard(
                    title = "Subscription Plan",
                    subtitle = "Lifetime Vault Access",
                    icon = Icons.Outlined.WorkspacePremium,
                    onClick = { /* Navigate */ }
                )
            }

            // --- 5. Support & Legal Group ---
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeading(title = "Support & Legal")

                ProfileOptionCard(
                    title = "Help & Support",
                    subtitle = "Read FAQs or contact our team",
                    icon = Icons.Outlined.SupportAgent,
                    onClick = { /* Navigate */ }
                )
                ProfileOptionCard(
                    title = "Privacy Policy",
                    subtitle = "How we protect your zero-knowledge data",
                    icon = Icons.Outlined.PrivacyTip,
                    onClick = { /* Navigate */ }
                )
            }

            // --- 6. Log Out Button ---
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .shadow(2.dp, RoundedCornerShape(16.dp))
                        .clickable {  },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ProfileColors.ErrorRedBg)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(Color(0xFFFFCDD2), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.Logout,
                                contentDescription = "Logout",
                                tint = ProfileColors.ErrorRedIcon
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "Log Out",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProfileColors.ErrorRedIcon
                        )
                    }
                }
            }
        }
    }
}

// --- Reusable UI Components ---

@Composable
fun SectionHeading(title: String) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = ProfileColors.TextBlack,
            letterSpacing = 0.5.sp
        ),
        modifier = Modifier.padding(start = 24.dp, bottom = 12.dp)
    )
}

@Composable
fun ProfileOptionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .border(1.dp, ProfileColors.BorderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ProfileColors.CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gradient Icon Box
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(ProfileColors.GradientStart, ProfileColors.GradientEnd)),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(22.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = ProfileColors.TextBlack)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, fontSize = 13.sp, color = ProfileColors.TextGray)
            }

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = Color.LightGray
            )
        }
    }
}

@Composable
fun ProfileToggleCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    initialState: Boolean,
    onToggle: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(initialState) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .border(1.dp, ProfileColors.BorderColor, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ProfileColors.CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gradient Icon Box
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(ProfileColors.GradientStart, ProfileColors.GradientEnd)),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(22.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = ProfileColors.TextBlack)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, fontSize = 13.sp, color = ProfileColors.TextGray)
            }

            Switch(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onToggle(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = ProfileColors.BrandOrange,
                    uncheckedThumbColor = ProfileColors.TextGray,
                    uncheckedTrackColor = Color(0xFFE0E0E0)
                )
            )
        }
    }
}
