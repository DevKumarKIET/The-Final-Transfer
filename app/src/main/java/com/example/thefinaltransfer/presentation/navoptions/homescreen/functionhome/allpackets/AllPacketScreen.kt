package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.allpackets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

enum class PacketType(val colorStr: String, val icon: ImageVector) {
    EMERGENCY("#E86962", Icons.Outlined.WarningAmber),
    PERSONAL("#F8B12A", Icons.Outlined.Description),
    ORGANISATION("#49ADF4", Icons.Outlined.WorkOutline),
    OTHERS("#7E7E7E", Icons.Outlined.FolderOpen)
}

data class PacketUiModel(
    val title: String,
    val subtitle: String,
    val type: PacketType,
    val count: Int,
    val nominee: String
)

val dummyPackets = listOf(
    PacketUiModel("Critical Documents", "Emergency Packet", PacketType.EMERGENCY, 8, "Dr. James Mitchell"),
    PacketUiModel("Family Photos & Videos", "Personal Packet", PacketType.PERSONAL, 244, "Emily Mitchell"),
    PacketUiModel("Financial Documents", "Personal Packet", PacketType.PERSONAL, 12, "James Mitchell"),
    PacketUiModel("Business Credentials", "Organisation Packet", PacketType.ORGANISATION, 8, "David Chen"),
    PacketUiModel("Tax Documents", "Personal Packet", PacketType.PERSONAL, 45, "James Mitchell"),
    PacketUiModel("Documentations", "Others Packet", PacketType.OTHERS, 67, "David Chen"),
    PacketUiModel("Miscellaneous Files", "Others Packet", PacketType.OTHERS, 89, "Sarah Mitchell"),
    PacketUiModel("Medical Records", "Personal Packet", PacketType.PERSONAL, 23, "Dr. James Mitchell"),
)

val BrandOrangeStart = Color(0xFFFF9F45)
val BrandOrangeEnd = Color(0xFFBC3535)
val BackgroundCream = Color(0xFFFFF9F0)

@Composable
fun AllPacketsScreen(navHostController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().background(BackgroundCream)) {
        Column(modifier = Modifier.fillMaxSize()) {

            // --- HEADER ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(BrandOrangeEnd, BrandOrangeStart)
                        ),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(top = 40.dp, bottom = 32.dp, start = 20.dp, end = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { navHostController.popBackStack() }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "All Packets",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "12 encrypted packets",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // --- LIST ---
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 16.dp, bottom = 40.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(dummyPackets) { packet ->
                    PacketCard(packet)
                }
            }
        }
    }
}

@Composable
fun PacketCard(packet: PacketUiModel) {
    // Parse hex string dynamically into Compose Color
    val mainColor = Color(android.graphics.Color.parseColor(packet.type.colorStr))

    // Very faint, transparent border of the same colorful branding
    val faintBorderColor = mainColor.copy(alpha = 0.4f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, faintBorderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Icon Box
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(mainColor.copy(alpha = 0.8f), mainColor)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = packet.type.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Body
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = packet.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF222222),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = packet.subtitle,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Files counter
                    Icon(
                        imageVector = Icons.Outlined.InsertDriveFile,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${packet.count} files",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Assignee / Nominee
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Color(0xFFE89B2D), // Brand-orange-ish
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = packet.nominee,
                        fontSize = 12.sp,
                        color = Color(0xFFE89B2D),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Right Lock Icon
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = "Encrypted",
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
