package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical

import kotlin.collections.forEachIndexed
import kotlin.collections.lastIndex
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.PictureAsPdf
import androidx.compose.material.icons.rounded.VideoFile
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.thefinaltransfer.data.model.FileItem
import com.example.thefinaltransfer.data.model.FileType

/**
 * Card listing all files inside the packet.
 * Each row has a colored type icon, name+size, and a lock icon.
 */
@Composable
fun FilesListCard(
    files: List<FileItem>,
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
            // ── Header row with count badge ───────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GradientIconContainer {
                    Icon(
                        imageVector = Icons.Rounded.Folder,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Files in this Packet",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.weight(1f)
                )
                // Count badge
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color(0xFFFFA62A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${files.size}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // ── File rows ─────────────────────────────────────────────────────
            files.forEachIndexed { index, file ->
                FileRow(file = file)
                if (index < files.lastIndex) {
                    Spacer(Modifier.height(6.dp))
                }
            }
        }
    }
}

// ─── Single file row ──────────────────────────────────────────────────────────
@Composable
private fun FileRow(file: FileItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFF8F0))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // File type icon
        val (icon, iconColor) = when (file.type) {
            FileType.PDF   -> Icons.Rounded.PictureAsPdf to Color(0xFFEF4444)
            FileType.IMAGE -> Icons.Rounded.Image        to Color(0xFF2196F3)
            FileType.VIDEO -> Icons.Rounded.VideoFile    to Color(0xFF9C27B0)
            FileType.DOC   -> Icons.Rounded.Description  to Color(0xFFFFA62A)
        }

        Icon(
            imageVector = icon,
            contentDescription = file.type.name,
            tint = iconColor,
            modifier = Modifier.size(28.dp)
        )

        Spacer(Modifier.width(12.dp))

        // File name + size
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = file.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A)
            )
            Text(
                text = file.size,
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E)
            )
        }

        // Lock icon — encrypted
        Icon(
            imageVector = Icons.Rounded.Lock,
            contentDescription = "Encrypted",
            tint = Color(0xFFCCCCCC),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF6EE)
@Composable
private fun FilesListCardPreview() {
    FilesListCard(
        files = listOf(
            FileItem("ID_Proof.pdf", "2.3 MB", FileType.PDF),
            FileItem("Family_Photo.jpg", "3.2 MB", FileType.IMAGE)
        )
    )
}