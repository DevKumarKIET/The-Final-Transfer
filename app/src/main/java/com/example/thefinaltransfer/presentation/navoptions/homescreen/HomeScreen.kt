package com.example.thefinaltransfer.presentation.navoptions.homescreen

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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.thefinaltransfer.presentation.bottomnavigation.TFTBottomNavigationBar
import com.example.thefinaltransfer.presentation.navigation.Routes

object HomeColors {
    val BgCream = Color(0xFFFFFDF9)
    val TextBlack = Color(0xFF1A1A1A)
    val TextGray = Color(0xFF757575)

    // Header Gradients
    val HeaderStart = Color(0xFFFF9F45)
    val HeaderEnd = Color(0xFFBC3535)

    // Emergency Section Colors
    val AlertRedBg = Color(0xFFFCA5A5).copy(alpha = 0.15f) // Soft pinkish background
    val AlertRedIconBg = Color(0xFFFCA5A5).copy(alpha = 0.4f)
    val AlertRedText = Color(0xFFBC3535)
    val AlertRedBorder = Color(0xFFFCA5A5).copy(alpha = 0.5f)

    // Overview Icons
    val IconBgOrange = Color(0xFFFF9F45)
    val IconBgLightOrange = Color(0xFFFFB703)

    val IconBgNextCheckIn = Color(0xFFFFD6B0)

    // Quick Action Gradients
    val ActionRedStart = Color(0xFFEF4444)
    val ActionRedEnd = Color(0xFFFF8080)
    val ActionOrangeStart = Color(0xFFFF9F45)
    val ActionOrangeEnd = Color(0xFFFFB703)
}

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        containerColor = HomeColors.BgCream
    ) { innerPadding ->

        // Main Scrollable Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(HomeColors.BgCream)
                .padding(innerPadding), // Respects Scaffold's bottom bar padding
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // --- 1. Curved Premium Header ---
            item {
                HeaderSection()
            }

            // --- 2. Emergency Packet Section ---
            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {
                    SectionTitle(
                        title = "Emergency Packet",
                        icon = Icons.Outlined.ErrorOutline,
                        iconTint = HomeColors.AlertRedText
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    EmergencyPacketCard(navHostController)
                }
            }

            // --- 3. Overview Section ---
            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    SectionTitle(title = "Overview")
                    Spacer(modifier = Modifier.height(16.dp))

                    OverviewCard(
                        title = "Total Packets",
                        count = "12",
                        icon = Icons.Outlined.Inventory2,
                        iconBg = HomeColors.IconBgOrange,
                        iconTint = Color(0xFFFFFFFF),
                        onClick = { navHostController.navigate(Routes.AllPacketsScreen) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OverviewCard(
                        title = "Assigned Nominees",
                        count = "3",
                        icon = Icons.Outlined.Group,
                        iconBg = HomeColors.IconBgLightOrange,
                        iconTint = Color(0xFFFFFFFF),
                        onClick = { navHostController.navigate(Routes.TotalNomineeScreen) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    CheckInCard(navHostController)
                }
            }

            // --- 4. Quick Actions Section ---
            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)) {
                    SectionTitle(title = "Quick Actions")
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        QuickActionCard(
                            title = "Create Emergency\nPacket",
                            icon = Icons.Outlined.AddBox,
                            gradient = Brush.linearGradient(
                                listOf(
                                    HomeColors.ActionRedStart,
                                    HomeColors.ActionRedEnd
                                )
                            ),
                            modifier = Modifier.weight(1f),

                        )

                        QuickActionCard(
                            title = "Manage Trusted\nusers",
                            icon = Icons.Outlined.ManageAccounts,
                            gradient = Brush.linearGradient(
                                listOf(
                                    HomeColors.ActionOrangeStart,
                                    HomeColors.ActionOrangeEnd
                                )
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = { navHostController.navigate(Routes.TrustedUserScreen) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(HomeColors.HeaderStart, HomeColors.HeaderEnd)
                )
            )
            .padding(start = 24.dp, end = 24.dp, top = 64.dp, bottom = 48.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Welcome,",
                    fontSize = 15.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Text(
                    text = "User name",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            // Notification Bell Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .border(1.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                    .clickable { /* Handle Notifications */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    icon: ImageVector? = null,
    iconTint: Color = HomeColors.TextBlack
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = HomeColors.TextBlack
        )
    }
}

@Composable
private fun EmergencyPacketCard(navHostController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = HomeColors.AlertRedBg),
        border = BorderStroke(1.dp, HomeColors.AlertRedBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navHostController.navigate(Routes.CriticalDetailScreen) }
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Red Error Box Icon
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(HomeColors.AlertRedIconBg, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    tint = HomeColors.AlertRedText,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Critical Documents",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = HomeColors.TextBlack
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Emergency medical records & contacts",
                    fontSize = 13.sp,
                    color = HomeColors.TextGray,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.PersonOutline,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = HomeColors.AlertRedText.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Nominee: 8",
                        fontSize = 12.sp,
                        color = HomeColors.AlertRedText.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "Go",
                tint = HomeColors.AlertRedText.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun OverviewCard(
    title: String,
    count: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp), spotColor = Color(0x1A000000)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(iconBg, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 13.sp, color = HomeColors.TextGray)
                Text(
                    text = count,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = HomeColors.TextBlack
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
}

@Composable
private fun CheckInCard(navHostController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp), spotColor = Color(0x1A000000)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(HomeColors.IconBgNextCheckIn, RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Schedule,
                        null,
                        tint = Color(0xFFFFFFFF),
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Next Check-in", fontSize = 13.sp, color = HomeColors.TextGray)
                    Text(
                        text = "2 days left",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = HomeColors.TextBlack
                    )
                }
                Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null, tint = Color.LightGray)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Edit Button
                OutlinedButton(
                    onClick = { navHostController.navigate(Routes.EditCheckInHome) },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, Color(0xFFFFB703)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Edit",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFB703)
                    )
                }

                // Verify Button
                Button(
                    onClick = { /* Verify Action */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB703)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Verify",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    icon: ImageVector,
    gradient: Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .shadow(6.dp, RoundedCornerShape(20.dp), spotColor = Color(0x33000000)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally // Centers contents inside the card perfectly
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
