package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical

import kotlin.collections.forEachIndexed
import kotlin.collections.lastIndex
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material.icons.rounded.VerifiedUser
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefinaltransfer.data.model.TrustedNomineeItem

private val OrangeGradient = Brush.linearGradient(listOf(Color(0xFFFFA62A), Color(0xFFFFB703)))

/**
 * Card listing all trusted nominees with verified badges.
 */
@Composable
fun TrustedNomineesCard(
    nominees: List<TrustedNomineeItem>,
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
                        imageVector = Icons.Rounded.VerifiedUser,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Trusted Nominees",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A)
                )
            }

            Spacer(Modifier.height(14.dp))

            // ── Nominee rows ──────────────────────────────────────────────────
            nominees.forEachIndexed { index, nominee ->
                NomineeRow(nominee = nominee)
                if (index < nominees.lastIndex) {
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

// ─── Single nominee row ───────────────────────────────────────────────────────
@Composable
private fun NomineeRow(nominee: TrustedNomineeItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFF8F0))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(OrangeGradient),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Name + verified badge
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = nominee.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
                if (nominee.isVerified) {
                    Spacer(Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Rounded.Verified,
                        contentDescription = "Verified",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = "Verified",
                        fontSize = 11.sp,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Text(
                text = nominee.email,
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF6EE)
@Composable
private fun TrustedNomineesCardPreview() {
    TrustedNomineesCard(
        nominees = listOf(
            TrustedNomineeItem("Harshil", "harshil@email.com", true),
            TrustedNomineeItem("Devvaaa", "devvaa@email.com", true)
        )
    )
}