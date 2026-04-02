package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.editcheckin.componentsedit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Design tokens ────────────────────────────────────────────────────────────
private val OrangePrimary = Color(0xFFFFA62A)
private val OrangeDeep    = Color(0xFFFF6B35)
private val CardBorder    = Color(0xFFF5E6D8)
private val DividerColor  = Color(0xFFF0E0D0)

private val quickChips  = listOf("Daily", "3 Days", "Weekly", "2 Weeks", "Monthly", "3 Months", "Custom")
private val unitOptions = listOf("Days", "Weeks", "Months")

// ─── Shared gradient icon box — used by all component cards ──────────────────
/**
 * Small orange gradient rounded square container for icons.
 * Defined here and reused across ReminderSettingsCard, CheckInStatusCard etc.
 */
@Composable
fun GradientIconBox(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(OrangePrimary, OrangeDeep)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}

// Main Card
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntervalSelectorCard(
    uiState: EditCheckInUiState,
    onChipSelected: (String) -> Unit,
    onCustomDurationChanged: (String) -> Unit,
    onCustomUnitChanged: (String) -> Unit,
    onMissedCountChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var unitDropdownExpanded by remember { mutableStateOf(false) }

    // Dynamic helper text — updates as user types
    val helperText = if (uiState.selectedChip == "Custom") {
        val dur = uiState.customDuration.ifBlank { "X" }
        "You will receive a check-in notification every $dur ${uiState.customUnit}"
    } else {
        "You will receive a check-in notification every ${uiState.selectedChip}"
    }

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

            //Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                GradientIconBox {
                    Icon(
                        imageVector = Icons.Rounded.Timer,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Check-in Interval",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1A1A1A)
                    )
                    Text(
                        text = "How often should we check on you?",
                        fontSize = 12.sp,
                        color = Color(0xFF9E9E9E)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            //Quick select chips
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                quickChips.forEach { chip ->
                    val isSelected = uiState.selectedChip == chip

                    val bgColor by animateColorAsState(
                        targetValue = if (isSelected) OrangePrimary else Color.White,
                        animationSpec = tween(durationMillis = 200),
                        label = "chip_bg_$chip"
                    )
                    val textColor by animateColorAsState(
                        targetValue = if (isSelected) Color.White else OrangePrimary,
                        animationSpec = tween(durationMillis = 200),
                        label = "chip_text_$chip"
                    )

                    Surface(
                        onClick = { onChipSelected(chip) },
                        shape = RoundedCornerShape(50.dp),
                        color = bgColor,
                        border = BorderStroke(1.dp, OrangePrimary),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = chip,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = textColor
                            )
                        }
                    }
                }
            }

            //Custom input — AnimatedVisibility
            AnimatedVisibility(
                visible = uiState.selectedChip == "Custom",
                enter = expandVertically(animationSpec = tween(300)),
                exit = shrinkVertically(animationSpec = tween(300))
            ) {
                Column {
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Duration number field
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Duration",
                                fontSize = 13.sp,
                                color = Color(0xFF4A4A4A)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = uiState.customDuration,
                                onValueChange = onCustomDurationChanged,
                                placeholder = { Text("e.g., 10", fontSize = 13.sp) },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                isError = uiState.customDurationError != null,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = OrangePrimary,
                                    unfocusedBorderColor = CardBorder
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (uiState.customDurationError != null) {
                                Text(
                                    text = uiState.customDurationError,
                                    fontSize = 11.sp,
                                    color = Color(0xFFE53935),
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }

                        // Unit dropdown
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Unit",
                                fontSize = 13.sp,
                                color = Color(0xFF4A4A4A)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            ExposedDropdownMenuBox(
                                expanded = unitDropdownExpanded,
                                onExpandedChange = { unitDropdownExpanded = it }
                            ) {
                                OutlinedTextField(
                                    value = uiState.customUnit,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = unitDropdownExpanded
                                        )
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = OrangePrimary,
                                        unfocusedBorderColor = CardBorder
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor()
                                )
                                ExposedDropdownMenu(
                                    expanded = unitDropdownExpanded,
                                    onDismissRequest = { unitDropdownExpanded = false }
                                ) {
                                    unitOptions.forEach { unit ->
                                        DropdownMenuItem(
                                            text = { Text(unit) },
                                            onClick = {
                                                onCustomUnitChanged(unit)
                                                unitDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            //Helper note
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = null,
                    tint = OrangePrimary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = helperText,
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = DividerColor, thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))

            //Missed check-in stepper
            Text(
                text = "Trigger after missed check-ins",
                fontSize = 13.sp,
                color = Color(0xFF4A4A4A),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { onMissedCountChanged(-1) },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, OrangePrimary),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "−",
                        fontSize = 18.sp,
                        color = OrangePrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier.widthIn(min = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${uiState.missedCheckInThreshold}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangePrimary
                    )
                }

                OutlinedButton(
                    onClick = { onMissedCountChanged(+1) },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, OrangePrimary),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "+",
                        fontSize = 18.sp,
                        color = OrangePrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Trusted nominees notified after ${uiState.missedCheckInThreshold} consecutive missed check-ins",
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E),
                fontStyle = FontStyle.Italic
            )
        }
    }
}
