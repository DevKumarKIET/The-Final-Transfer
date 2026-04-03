package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val OrangePrimary = Color(0xFFFFA62A)
private val DeleteRed     = Color(0xFFEF4444)

@Composable
fun ActionButtons(
    showDeleteDialog: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {

        // ── Edit Packet button ────────────────────────────────────────────────
        OutlinedButton(
            onClick = onEditClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 1.5.dp,
                brush = androidx.compose.ui.graphics.SolidColor(OrangePrimary)
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor   = OrangePrimary
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Edit Packet",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = OrangePrimary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Delete Packet button ──────────────────────────────────────────────
        OutlinedButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 1.5.dp,
                brush = androidx.compose.ui.graphics.SolidColor(DeleteRed)
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFFFFF5F5),
                contentColor   = DeleteRed
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                tint = DeleteRed,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Delete Packet",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = DeleteRed
            )
        }
    }

    // ── Delete confirmation AlertDialog ───────────────────────────────────────
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = onDeleteDismiss,
            title = {
                Text(
                    text = "Delete Packet",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = "This action cannot be undone. Are you sure you want to delete Critical Documents?",
                    fontSize = 14.sp,
                    color = Color(0xFF4A4A4A),
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                TextButton(onClick = onDeleteConfirm) {
                    Text(
                        text = "Delete",
                        color = DeleteRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDeleteDismiss) {
                    Text(
                        text = "Cancel",
                        color = Color.Gray
                    )
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF6EE)
@Composable
private fun ActionButtonsPreview() {
    ActionButtons(
        showDeleteDialog = false,
        onEditClick = {},
        onDeleteClick = {},
        onDeleteConfirm = {},
        onDeleteDismiss = {}
    )
}