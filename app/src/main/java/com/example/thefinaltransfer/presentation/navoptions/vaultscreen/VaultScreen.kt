package com.example.thefinaltransfer.presentation.navoptions.vaultscreen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.thefinaltransfer.presentation.bottomnavigation.TFTBottomNavigationBar

val VaultBackground = Color(0xFFFFF6EE)
val HeaderGradientStart = Color(0xFFFFA62A)
val HeaderGradientEnd = Color(0xFFCD5052)

val EmergencyStart = Color(0xFFEF4444)
val EmergencyEnd = Color(0xFFFCA5A5)

val PersonalStart = Color(0xFFFF9F45)
val PersonalEnd = Color(0xFFFFB703)

val OrgStart = Color(0xFF00A6FF)
val OrgEnd = Color(0xFF84D4FF)

val PrimaryOrange = Color(0xFFFFA62A)
val TextPrimary = Color(0xFF1A1A1A)
val bg = Color(0xFFFFFFFF)
val TextGrey = Color(0xFFA0A0A0)


@Composable
fun VaultScreen(navHostController: NavHostController) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var expandedEmergency by remember { mutableStateOf(false) }
    var expandedPersonal by remember { mutableStateOf(false) }
    var expandedOrg by remember { mutableStateOf(false) }
    var expandedOther by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = VaultBackground
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {

            item {
                VaultHeader()
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                CategoryCard(
                    title = "Emergency",
                    packetCount = 1,
                    gradientStart = EmergencyStart,
                    gradientEnd = EmergencyEnd,
                    borderColor = EmergencyStart.copy(alpha = 0.4f),
                    icon = Icons.Default.Warning,
                    expanded = expandedEmergency,
                    onToggle = { expandedEmergency = !expandedEmergency }
                ) {

                    VaultPacket(
                        packetTitle = "Medical Information",
                        packetSubtitle = "Emergency Packet",
                        nomineeCount = 6,
                        fileCount = 5,
                        gradientStart = EmergencyStart,
                        gradientEnd = EmergencyEnd,
                        onEditClick = {},
                        onDeleteClick = {},
                        onViewDetailsClick = {}
                    )
                }
            }

            item {
                CategoryCard(
                    title = "Personal",
                    packetCount = 2,
                    gradientStart = PersonalStart,
                    gradientEnd = PersonalEnd,
                    borderColor = PersonalStart.copy(alpha = 0.4f),
                    icon = Icons.Default.Description,
                    expanded = expandedPersonal,
                    onToggle = { expandedPersonal = !expandedPersonal }
                ) {

                    VaultPacket(
                        packetTitle = "Family Photos & Videos",
                        packetSubtitle = "Personal Packet",
                        nomineeCount = 8,
                        fileCount = 248,
                        gradientStart = PersonalStart,
                        gradientEnd = PersonalEnd,
                        onEditClick = {},
                        onDeleteClick = {},
                        onViewDetailsClick = {}
                    )

                    VaultPacket(
                        packetTitle = "Financial Documents",
                        packetSubtitle = "Personal Packet",
                        nomineeCount = 3,
                        fileCount = 12,
                        gradientStart = PersonalStart,
                        gradientEnd = PersonalEnd,
                        onEditClick = {},
                        onDeleteClick = {},
                        onViewDetailsClick = {}
                    )
                }
            }

            item {
                CategoryCard(
                    title = "Organization",
                    packetCount = 1,
                    gradientStart = OrgStart,
                    gradientEnd = OrgEnd,
                    borderColor = OrgStart.copy(alpha = 0.4f),
                    icon = Icons.Default.Business,
                    expanded = expandedOrg,
                    onToggle = { expandedOrg = !expandedOrg }
                ) {
                    VaultPacket(
                        packetTitle = "Company Credentials",
                        packetSubtitle = "Organization Packet",
                        nomineeCount = 5,
                        fileCount = 15,
                        gradientStart = OrgStart,
                        gradientEnd = OrgEnd,
                        onEditClick = {},
                        onDeleteClick = {},
                        onViewDetailsClick = {}
                    )
                }
            }

            item {
                CategoryCard(
                    title = "Other",
                    packetCount = 0,
                    gradientStart = Color(0xFF626262),
                    gradientEnd = Color(0xFF9B9C9D),
                    borderColor = Color(0xFF626262).copy(alpha = 0.4f),
                    icon = Icons.Default.Folder,
                    expanded = expandedOther,
                    onToggle = { expandedOther = !expandedOther }
                ) {
                    Text(
                        text = "No packets added yet",
                        color = TextGrey,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VaultHeader() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 28.dp,
                    bottomEnd = 28.dp
                )
            )
            .background(
                Brush.linearGradient(
                    listOf(HeaderGradientStart, HeaderGradientEnd)
                )
            )
            .padding(24.dp)
    ) {

        Column {

            Text(
                text = "Vault",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Your encrypted packets organized by category",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

//Vault Category
@Composable
fun CategoryCard(
    title: String,
    packetCount: Int,
    gradientStart: Color,
    gradientEnd: Color,
    borderColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    expanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = ""
    )
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = if (expanded) 9.dp else 5.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {

            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggle() }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(gradientStart, gradientEnd)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            icon,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )

                        Text(
                            text = "$packetCount packets",
                            fontSize = 13.sp,
                            color = TextGrey
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.rotate(rotation)
                    )
                }

                if (expanded) {
                    Column(
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

