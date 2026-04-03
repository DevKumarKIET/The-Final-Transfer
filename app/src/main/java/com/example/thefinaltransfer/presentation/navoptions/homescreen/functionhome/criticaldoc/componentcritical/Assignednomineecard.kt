package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cake
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
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

private val OrangeGradient = Brush.linearGradient(listOf(Color(0xFFFFA62A), Color(0xFFFFB703)))
private val OrangePrimary  = Color(0xFFFFA62A)
private val CardBorder     = Color(0xFFF5E6D8)

/**
 * Card showing the primary assigned nominee's full details.
 */
@Composable
fun AssignedNomineeCard(
    nomineeName: String,
    nomineeRelationship: String,
    nomineeEmail: String,
    nomineePhone: String,
    nomineeAddedDate: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
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
            // ── Card header ───────────────────────────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                GradientIconContainer {
                    Icon(Icons.Rounded.Person, null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Assigned Nominee",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A)
                )
            }

            Spacer(Modifier.height(16.dp))

            // ── Avatar + Name row ─────────────────────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(OrangeGradient),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column {
                    Text(
                        text = nomineeName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )
                    Spacer(Modifier.height(4.dp))
                    // Relationship pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(OrangePrimary.copy(alpha = 0.12f))
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = nomineeRelationship,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OrangePrimary
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Contact details ───────────────────────────────────────────────
            ContactRow(icon = Icons.Rounded.Email, text = nomineeEmail, color = Color(0xFF4A4A4A))
            Spacer(Modifier.height(10.dp))
            ContactRow(icon = Icons.Rounded.Phone, text = nomineePhone, color = Color(0xFF4A4A4A))
            Spacer(Modifier.height(10.dp))
            ContactRow(icon = Icons.Rounded.Cake,  text = nomineeAddedDate, color = Color(0xFF9E9E9E))
        }
    }
}

@Composable
private fun ContactRow(icon: ImageVector, text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = OrangePrimary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(text = text, fontSize = 13.sp, color = color)
    }
}

// ─── Shared gradient icon container — used across all cards ──────────────────
@Composable
fun GradientIconContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(OrangeGradient),
        contentAlignment = Alignment.Center
    ) { content() }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF6EE)
@Composable
private fun AssignedNomineeCardPreview() {
    AssignedNomineeCard(
        nomineeName = "Dr. Deepa",
        nomineeRelationship = "Family",
        nomineeEmail = "deepa@email.com",
        nomineePhone = "+1 (555) 123-4567",
        nomineeAddedDate = "Added on Dec 10, 2025"
    )
}