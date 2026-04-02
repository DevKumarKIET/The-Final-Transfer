package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.editcheckin.componentsedit


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.Sms
//import androidx.compose.material3.BorderStroke
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val OrangePrimary = Color(0xFFFFA62A)
private val CardBorder    = Color(0xFFF5E6D8)
private val DividerColor  = Color(0xFFF0E0D0)

@Composable
fun ReminderSettingsCard(
    uiState: EditCheckInUiState,
    onReminderDaysChanged: (String) -> Unit,
    onEmailToggle: (Boolean) -> Unit,
    onSmsToggle: (Boolean) -> Unit,
    onPushToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // ── Header ────────────────────────────────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                GradientIconBox {
                    Icon(
                        imageVector = Icons.Rounded.NotificationsActive,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Reminder Settings",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ── Remind before days input ──────────────────────────────────────
            Text(
                text = "Remind me before check-in deadline",
                fontSize = 13.sp,
                color = Color(0xFF4A4A4A)
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = uiState.reminderDaysBefore,
                onValueChange = onReminderDaysChanged,
                placeholder = { Text("e.g., 2", fontSize = 13.sp) },
                suffix = {
                    Text(
                        text = "days before",
                        fontSize = 13.sp,
                        color = Color(0xFF9E9E9E)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = uiState.reminderDaysError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
                    unfocusedBorderColor = CardBorder
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Error or helper text
            if (uiState.reminderDaysError != null) {
                Text(
                    text = uiState.reminderDaysError,
                    fontSize = 11.sp,
                    color = Color(0xFFE53935),
                    modifier = Modifier.padding(top = 2.dp, start = 4.dp)
                )
            } else {
                Text(
                    text = "Get reminded ${uiState.reminderDaysBefore.ifBlank { "X" }} days before your check-in is due",
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E),
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = DividerColor, thickness = 1.dp)

            // ── Email toggle ──────────────────────────────────────────────────
            NotificationToggleRow(
                icon = Icons.Rounded.Email,
                title = "Email Notifications",
                subtitle = "Receive check-in reminders via email",
                checked = uiState.emailEnabled,
                onCheckedChange = onEmailToggle
            )

            HorizontalDivider(color = DividerColor, thickness = 1.dp)

            // ── SMS toggle ────────────────────────────────────────────────────
            NotificationToggleRow(
                icon = Icons.Rounded.Sms,
                title = "SMS Notifications",
                subtitle = "Receive check-in reminders via SMS",
                checked = uiState.smsEnabled,
                onCheckedChange = onSmsToggle
            )

            HorizontalDivider(color = DividerColor, thickness = 1.dp)

            // ── Push notifications toggle ─────────────────────────────────────
            NotificationToggleRow(
                icon = Icons.Rounded.Notifications,
                title = "Push Notifications",
                subtitle = "In-app alerts for check-in reminders",
                checked = uiState.pushEnabled,
                onCheckedChange = onPushToggle
            )
        }
    }
}

// ─── Reusable toggle row ──────────────────────────────────────────────────────

@Composable
private fun NotificationToggleRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GradientIconBox {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1A1A1A)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = OrangePrimary,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFE0E0E0)
            )
        )
    }
}
