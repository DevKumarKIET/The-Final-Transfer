package com.example.thefinaltransfer.presentation.navoptions.uploadscreen.componentsupload

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navoptions.uploadscreen.CategoryLeadingIcon
import com.example.thefinaltransfer.presentation.navoptions.uploadscreen.defaultInputColors
import kotlin.collections.isNotEmpty

@Composable
fun UploadHeader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFFFFDD00),
                        Color(0xFFFF8000),
                        Color(0xFFFFEDB2)
                    )
                )
            )
            .padding(horizontal = 24.dp, vertical = 32.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Column {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Create Packet",
                color = colorResource(id = R.color.white),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Fill in the details to create a new packet",
                color = colorResource(id = R.color.white).copy(alpha = 0.9f),
                fontSize = 14.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrustedUsersDropdown(
    selectedTrustedUsers: List<String>,
    trustedUsersOptions: List<String>,
    expanded: Boolean,
    showErrors: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onToggleUser: (String) -> Unit
) {

    val userText =
        if (selectedTrustedUsers.isEmpty())
            "Select trusted users"
        else
            "${selectedTrustedUsers.size} user(s) selected"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandChange
    ) {

        OutlinedTextField(
            value = userText,
            onValueChange = {},
            readOnly = true,
            isError = showErrors && selectedTrustedUsers.isEmpty(),

            supportingText = {
                if (showErrors && selectedTrustedUsers.isEmpty())
                    Text("Select at least one", color = Color(0xFFD32F2F))
            },

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },

            leadingIcon = { CategoryLeadingIcon() },

            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),

            shape = RoundedCornerShape(12.dp),
            colors = defaultInputColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChange(false) },
            modifier = Modifier.background(colorResource(id = R.color.white))
        ) {

            trustedUsersOptions.forEach { user ->

                val isSelected = selectedTrustedUsers.contains(user)

                DropdownMenuItem(
                    text = {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = null,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = colorResource(id = R.color.gradient_start)
                                )
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(user, color = colorResource(id = R.color.black))
                        }
                    },
                    onClick = { onToggleUser(user) }
                )
            }
        }
    }
}

@Composable
fun CreatePacketButton(
    vaultCategory: String,
    packetTitle: String,
    uploadedFiles: List<Uri>,
    selectedTrustedUsers: List<String>,
    nominees: List<NomineeState>,
    showErrors: Boolean,
    onValidationResult: (Boolean) -> Unit
) {

    Button(
        onClick = {

            val isNomineesValid =
                nominees.all {
                    it.fullName.isNotBlank() &&
                            it.mobile.isNotBlank() &&
                            it.email.isNotBlank()
                }

            val isValid =
                vaultCategory.isNotBlank() &&
                        packetTitle.isNotBlank() &&
                        uploadedFiles.isNotEmpty() &&
                        selectedTrustedUsers.isNotEmpty() &&
                        isNomineesValid

            onValidationResult(isValid)
        },

        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(
                8.dp,
                RoundedCornerShape(20.dp),
                spotColor = colorResource(id = R.color.gradient_start)
            ),

        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            colorResource(id = R.color.gradient_start),
                            colorResource(id = R.color.gradient_end)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                "Create Packet",
                color = colorResource(id = R.color.white),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun UploadNoteBox() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {

        val noteText = buildAnnotatedString {

            withStyle(
                SpanStyle(
                    color = colorResource(id = R.color.gradient_start),
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Note: ")
            }

            withStyle(
                SpanStyle(color = colorResource(id = R.color.gray_text))
            ) {
                append("All files are encrypted. The nominee will receive access only when the configured triggers are activated.")
            }
        }

        Text(
            text = noteText,
            fontSize = 12.sp,
            lineHeight = 18.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaultDropdown(
    vaultCategory: String,
    vaultDropdownExpanded: Boolean,
    vaultOptions: List<String>,
    showErrors: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onSelect: (String) -> Unit
) {

    ExposedDropdownMenuBox(
        expanded = vaultDropdownExpanded,
        onExpandedChange = onExpandChange
    ) {

        OutlinedTextField(
            value = vaultCategory.ifEmpty { "Choose Vault Type" },
            onValueChange = {},
            readOnly = true,
            isError = showErrors && vaultCategory.isEmpty(),

            supportingText = {
                if (showErrors && vaultCategory.isEmpty())
                    Text("Required", color = Color(0xFFD32F2F))
            },

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = vaultDropdownExpanded)
            },

            leadingIcon = { CategoryLeadingIcon() },

            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),

            shape = RoundedCornerShape(12.dp),
            colors = defaultInputColors()
        )

        ExposedDropdownMenu(
            expanded = vaultDropdownExpanded,
            onDismissRequest = { onExpandChange(false) },
            modifier = Modifier.background(colorResource(id = R.color.white))
        ) {

            vaultOptions.forEach { option ->

                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            color = colorResource(id = R.color.black)
                        )
                    },
                    onClick = { onSelect(option) }
                )
            }
        }
    }
}