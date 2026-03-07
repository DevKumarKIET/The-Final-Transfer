package com.example.thefinaltransfer.presentation.navoptions.aboutscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// --- Colors perfectly matched to your theme ---
private val CreamBackground = Color(0xFFFFF7ED)
private val GradientStart = Color(0xFFFF9F45)
private val GradientEnd = Color(0xFFFFB703)
private val CardBackground = Color(0xFFFFFBEB)
private val TextBlack = Color(0xFF1A1A1A)
private val TextGray = Color(0xFF6C6C6C)
private val BorderColor = Color(0xFFFFE0B2)

@Composable
fun AboutScreen(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
    ) {
        // --- Header Background ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(Brush.linearGradient(listOf(
                    Color(0xFFFFD6B0),
                    Color(0xFFFF9F45)
                )))
        )

        // --- Scrollable Content ---
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp) // Accounts for your Bottom Nav Bar
        ) {

            // --- 1. Header Text ---
            item {
                Column(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 56.dp, bottom = 32.dp)
                ) {
                    Text(
                        text = "About Us",
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Protecting your legacy beyond the screen.",
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            lineHeight = 22.sp
                        )
                    )
                }
            }

            // --- 2. Our Mission Card ---
            item {
                MissionCard()
            }

            // --- 3. How It Works Section ---
            item {
                SectionTitle("How It Works")
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    HowItWorksItem(
                        number = "1",
                        title = "Create Encrypted Packets",
                        description = "Upload files, photos, and messages into secure, military-grade encrypted packets."
                    )
                    HowItWorksItem(
                        number = "2",
                        title = "Assign Verified Nominees",
                        description = "Choose KYC-verified individuals to receive each packet securely."
                    )
                    HowItWorksItem(
                        number = "3",
                        title = "Configure Triggers",
                        description = "Set inactivity periods and verification methods for automatic packet release."
                    )
                    HowItWorksItem(
                        number = "4",
                        title = "Automatic Transfer",
                        description = "Packets are automatically delivered when triggers activate, ensuring your legacy lives on."
                    )
                }
            }

            // --- 4. Trust & Security Section ---
            item {
                SectionTitle("Trust & Security")
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    TrustAndSecurityItem(
                        icon = Icons.Outlined.Lock,
                        title = "Client-side Encryption",
                        description = "AES-256 encryption protects your files with the highest security standard used worldwide."
                    )
                    TrustAndSecurityItem(
                        icon = Icons.Outlined.Group,
                        title = "Trusted Nominees",
                        description = "Designate verified family members or trusted individuals to receive your digital legacy."
                    )
                    TrustAndSecurityItem(
                        icon = Icons.Outlined.Schedule,
                        title = "Automatic Triggers",
                        description = "Packets release based on inactivity periods or verified death certificates from authorities."
                    )
                    TrustAndSecurityItem(
                        icon = Icons.Outlined.Shield,
                        title = "Zero-Knowledge Privacy",
                        description = "We never access your encrypted files. Only you and your nominees hold the decryption keys."
                    )
                    TrustAndSecurityItem(
                        icon = Icons.Outlined.Bolt,
                        title = "Instant Transfer",
                        description = "When triggered, encrypted packets are delivered immediately and securely to nominees."
                    )
                    TrustAndSecurityItem(
                        icon = Icons.Outlined.FavoriteBorder,
                        title = "Peace of Mind",
                        description = "Rest assured knowing your most important files will reach the right people at the right time."
                    )
                }
            }

            // --- 5. Legal & Privacy Cards ---
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    LegalAndPrivacyCard(
                        icon = Icons.Outlined.VerifiedUser,
                        title = "Legal Compliance",
                        description = "All users undergo legal verification. Nominees are verified before packet delivery. We comply with legal frameworks for digital asset transfer and maintain audit trails for regulatory compliance."
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LegalAndPrivacyCard(
                        icon = Icons.Outlined.PrivacyTip,
                        title = "Your Privacy Matters",
                        description = "We use zero-knowledge encryption, meaning we never have access to your unencrypted data. Your packets remain completely private and can only be unlocked by your nominees when properly triggered and verified."
                    )
                }
            }
        }
    }
}

// --- Reusable UI Components ---

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextBlack
        ),
        modifier = Modifier.padding(start = 24.dp, top = 32.dp, bottom = 16.dp)
    )
}

@Composable
fun MissionCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Our Mission",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextBlack
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "The Final Transfer is a high-security digital legacy platform designed to protect and transfer your most precious digital assets like photos, documents, passwords, and personal messages—to your chosen nominees after you pass away or become incapacitated.\n\nWe believe everyone deserves the peace of mind that comes from knowing their digital legacy is secure, legally compliant, and will be passed on according to their wishes.",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = TextGray,
                    lineHeight = 22.sp
                )
            )
        }
    }
}

@Composable
fun HowItWorksItem(number: String, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Circle Number Indicator
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(GradientStart, GradientEnd)),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBlack
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = TextGray,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun TrustAndSecurityItem(icon: ImageVector, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Rounded Square Icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(GradientStart, GradientEnd)),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBlack
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = TextGray,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun LegalAndPrivacyCard(icon: ImageVector, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground), // Matches Mission Card
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = GradientEnd,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBlack
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 13.sp,
                color = TextGray,
                lineHeight = 20.sp
            )
        }
    }
}
