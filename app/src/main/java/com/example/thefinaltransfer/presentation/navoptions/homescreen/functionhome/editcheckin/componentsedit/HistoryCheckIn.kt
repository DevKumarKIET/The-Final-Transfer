package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.editcheckin.componentsedit


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CardBorder = Color(0xFFF5E6D8)
private val Divider = Color(0xFFF0E0D0)

private enum class CheckInStatus { COMPLETED, MISSED, PENDING }

private data class HistoryItem(
    val date: String,
    val status: CheckInStatus
)

private val sampleHistory = listOf(
    HistoryItem("Dec 28, 2025", CheckInStatus.COMPLETED),
    HistoryItem("Dec 21, 2025", CheckInStatus.COMPLETED),
    HistoryItem("Dec 14, 2025", CheckInStatus.MISSED)
)

@Composable
fun HistoryPreviewCard(
    onViewAll: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GradientIconBox {
                    Icon(
                        Icons.Rounded.History,
                        null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Recent Check-in History",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onViewAll, contentPadding = PaddingValues(0.dp)) {
                    Text("View All →", fontSize = 13.sp, color = Color(0xFFFFA62A))
                }
            }

            Spacer(Modifier.height(12.dp))

            sampleHistory.forEachIndexed { index, item ->
                HistoryRow(item = item)
                if (index < sampleHistory.lastIndex) {
                    HorizontalDivider(
                        color = Divider,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun HistoryRow(item: HistoryItem) {
    val (dotColor, badgeBg, badgeText, badgeTextColor) = when (item.status) {
        CheckInStatus.COMPLETED -> listOf(
            Color(0xFF4CAF50), Color(0xFFE8F5E9), "Completed", Color(0xFF2E7D32)
        )

        CheckInStatus.MISSED -> listOf(
            Color(0xFFE53935), Color(0xFFFFEBEE), "Missed", Color(0xFFC62828)
        )

        CheckInStatus.PENDING -> listOf(
            Color(0xFFFFB703), Color(0xFFFFF8E1), "Pending", Color(0xFFE65100)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status dot
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(dotColor as Color)
        )
        Spacer(Modifier.width(12.dp))

        // Date
        Text(
            text = item.date,
            fontSize = 14.sp,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.weight(1f)
        )

        // Badge
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(badgeBg as Color)
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = badgeText as String,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = badgeTextColor as Color
            )
        }
    }
}
