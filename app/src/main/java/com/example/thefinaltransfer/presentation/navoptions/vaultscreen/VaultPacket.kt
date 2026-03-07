package com.example.thefinaltransfer.presentation.navoptions.vaultscreen

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VaultPacket(
    packetTitle: String,
    packetSubtitle: String,
    nomineeCount: Int,
    fileCount: Int,
    gradientStart: Color,
    gradientEnd: Color,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onViewDetailsClick: () -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDeleteClick()
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Delete Packet") },
            text = { Text("Are you sure you want to delete this packet?") }
        )
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(modifier = Modifier.padding(12.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    gradientStart,
                                    gradientEnd
                                )
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Description, null, tint = Color.White)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = packetTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = packetSubtitle,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                TextButton(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color(0xFFEF4444).copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Icon(Icons.Default.Delete, null, tint = Color(0xFFEF4444))
                    Spacer(Modifier.width(4.dp))
                    Text("Delete", color = Color(0xFFEF4444))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    Icons.Default.Person,
                    null,
                    tint = Color(0xFFFFA62A)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Nominee:",
                    color = Color(0xFFFFA62A),
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = nomineeCount.toString(),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    Icons.Default.Description,
                    null,
                    tint = Color.Gray
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "$fileCount files",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Divider(
                color = Color(0xFFFFA62A).copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onEditClick,
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Icon(Icons.Default.Edit, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Edit")
                }

                OutlinedButton(
                    onClick = onViewDetailsClick,
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Description, null)
                    Spacer(Modifier.width(4.dp))
                    Text("View")
                }
            }
        }
    }
}