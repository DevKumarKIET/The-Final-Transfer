package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.FileCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Design tokens ────────────────────────────────────────────────────────────
private val OrangeGradient  = Brush.linearGradient(listOf(Color(0xFFFFA62A), Color(0xFFFFB703)))
private val GreenGradient   = Brush.linearGradient(listOf(Color(0xFF4CAF50), Color(0xFF81C784)))
private val DividerColor    = Color(0xFFF0E0D0)

/**
 * Floating stats row card with 3 items — Files, Status, Created.
 * Uses zIndex + offset in parent to overlap the header slightly.
 */
@Composable
fun StatsRowCard(
    fileCount: Int,
    status: String,
    createdDate: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ── Stat 1 — Files ────────────────────────────────────────────────
            StatItem(
                icon       = Icons.Rounded.FileCopy,
                iconBrush  = OrangeGradient,
                value      = "$fileCount",
                valueColor = Color(0xFFFFA62A),
                label      = "Files",
                modifier   = Modifier.weight(1f)
            )

            // Vertical divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(48.dp)
                    .background(DividerColor)
            )

            // ── Stat 2 — Status ───────────────────────────────────────────────
            StatItem(
                icon       = Icons.Rounded.CheckCircle,
                iconBrush  = GreenGradient,
                value      = status,
                valueColor = Color(0xFF4CAF50),
                label      = "Status",
                modifier   = Modifier.weight(1f)
            )

            // Vertical divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(48.dp)
                    .background(DividerColor)
            )

            // ── Stat 3 — Created ──────────────────────────────────────────────
            StatItem(
                icon       = Icons.Rounded.CalendarToday,
                iconBrush  = OrangeGradient,
                value      = createdDate,
                valueColor = Color(0xFFFFA62A),
                label      = "Created",
                modifier   = Modifier.weight(1f)
            )
        }
    }
}

// ─── Single stat item ─────────────────────────────────────────────────────────
@Composable
private fun StatItem(
    icon: ImageVector,
    iconBrush: Brush,
    value: String,
    valueColor: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Gradient icon box
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconBrush),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color(0xFF9E9E9E)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF6EE)
@Composable
private fun StatsRowCardPreview() {
    StatsRowCard(fileCount = 8, status = "Active", createdDate = "Dec 10")
}