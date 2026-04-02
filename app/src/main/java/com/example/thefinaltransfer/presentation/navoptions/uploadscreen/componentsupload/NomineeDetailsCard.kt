package com.example.thefinaltransfer.presentation.navoptions.uploadscreen.componentsupload

import com.example.thefinaltransfer.presentation.navoptions.uploadscreen.RequiredLabel
import com.example.thefinaltransfer.presentation.navoptions.uploadscreen.defaultInputColors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefinaltransfer.R
import java.util.UUID

// Data Model for Nominee State
data class NomineeState(
    val id: String = UUID.randomUUID().toString(),
    val fullName: String = "",
    val mobile: String = "",
    val email: String = ""
)

@Composable
fun NomineeDetailsCard(
    index: Int,
    nominee: NomineeState,
    showErrors: Boolean,
    onUpdate: (NomineeState) -> Unit,
    onRemove: () -> Unit,
    canRemove: Boolean
) {
    // Colors not in XML, defined specifically for this component's requested theme
    val NomineeCardBg = Color(0xFFFFF0E0)
    val ErrorRed = Color(0xFFD32F2F)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NomineeCardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // --- Header Row ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Orange Document Icon
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(id = R.color.gradient_start)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Description,
                        contentDescription = null,
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Nominee Details ${if (index > 0) "#${index + 1}" else ""}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.black)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Delete Button (Only visible if there is more than 1 nominee)
                if (canRemove) {
                    IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteOutline,
                            contentDescription = "Remove",
                            tint = ErrorRed
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Full Name ---
            val isNameError = showErrors && nominee.fullName.isBlank()
            RequiredLabel("Full Name")
            OutlinedTextField(
                value = nominee.fullName,
                onValueChange = { onUpdate(nominee.copy(fullName = it)) },
                placeholder = { Text("Enter nominee's full name", color = colorResource(id = R.color.gray_text).copy(alpha = 0.6f)) },
                isError = isNameError,
                supportingText = { if (isNameError) Text("Required field", color = ErrorRed) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = defaultInputColors(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- Mobile Number ---
            val isMobileError = showErrors && nominee.mobile.isBlank()
            RequiredLabel("Mobile Number")
            OutlinedTextField(
                value = nominee.mobile,
                onValueChange = { onUpdate(nominee.copy(mobile = it)) },
                placeholder = { Text("Enter mobile number", color = colorResource(id = R.color.gray_text).copy(alpha = 0.6f)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = isMobileError,
                supportingText = { if (isMobileError) Text("Required field", color = ErrorRed) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = defaultInputColors(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- Email Address ---
            val isEmailError = showErrors && nominee.email.isBlank()
            RequiredLabel("Email Address")
            OutlinedTextField(
                value = nominee.email,
                onValueChange = { onUpdate(nominee.copy(email = it)) },
                placeholder = { Text("Enter email address", color = colorResource(id = R.color.gray_text).copy(alpha = 0.6f)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = isEmailError,
                supportingText = { if (isEmailError) Text("Required field", color = ErrorRed) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = defaultInputColors(),
                singleLine = true
            )
        }
    }
}