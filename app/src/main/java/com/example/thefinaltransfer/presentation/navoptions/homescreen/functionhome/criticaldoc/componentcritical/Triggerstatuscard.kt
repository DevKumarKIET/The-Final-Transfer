package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Trigger status card with info rows and "Armed & Watching" pill.
 */
@Composable
fun TriggerStatusCard(
    triggerType: String,
    checkInInterval: String,
    missedThreshold: String,
    triggerStatus: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF5E6D8)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // ── Header ────────────────────────────────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                GradientIconContainer {
                    Icon(
                        imageVector = Icons.Rounded.Bolt,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Trigger Status",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A)
                )
            }

            Spacer(Modifier.height(14.dp))

            // ── Info rows ─────────────────────────────────────────────────────
            TriggerInfoRow(label = "Trigger Type:", value = triggerType)
            HorizontalDivider(color = Color(0xFFF0E0D0), thickness = 1.dp)
            TriggerInfoRow(label = "Check-in Interval:", value = checkInInterval)
            HorizontalDivider(color = Color(0xFFF0E0D0), thickness = 1.dp)
            TriggerInfoRow(label = "Missed Threshold:", value = missedThreshold)
            HorizontalDivider(color = Color(0xFFF0E0D0), thickness = 1.dp)

            // ── Current status row with "Armed & Watching" pill ───────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Current Status:",
                    fontSize = 13.sp,
                    color = Color(0xFF9E9E9E)
                )
                // Armed & Watching pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color(0xFFE8F5E9))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Green dot
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50))
                        )
                        Text(
                            text = triggerStatus,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }
    }
}

// ─── Single trigger info row ──────────────────────────────────────────────────
@Composable
private fun TriggerInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 13.sp, color = Color(0xFF9E9E9E))
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF6EE)
@Composable
private fun TriggerStatusCardPreview() {
    TriggerStatusCard(
        triggerType = "Inactivity Based",
        checkInInterval = "7 Days",
        missedThreshold = "3 Check-ins",
        triggerStatus = "Armed & Watching"
    )
}