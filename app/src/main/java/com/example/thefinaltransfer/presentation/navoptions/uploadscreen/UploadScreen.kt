package com.example.thefinaltransfer.presentation.navoptions.uploadscreen


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.FileUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navoptions.uploadscreen.components.NomineeDetailsCard
import com.example.thefinaltransfer.presentation.navoptions.uploadscreen.components.NomineeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPacketScreen(navController: NavController) {
    // Form States
    var vaultCategory by remember { mutableStateOf("") }
    var packetTitle by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var uploadedFiles by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var selectedTrustedUsers by remember { mutableStateOf<List<String>>(emptyList()) }
    var nominees by remember { mutableStateOf(listOf(NomineeState())) }

    // Validation State
    var showErrors by remember { mutableStateOf(false) }

    // Dropdown States
    var vaultDropdownExpanded by remember { mutableStateOf(false) }
    var trustedUsersExpanded by remember { mutableStateOf(false) }

    val vaultOptions = listOf("Personal Vault", "Business Vault", "Other Vault")
    val trustedUsersOptions = listOf("Alice Johnson", "Bob Smith", "Charlie Davis")

    Scaffold(
        containerColor = colorResource(id = R.color.navbackground) // Cream color #FFFFF6EE
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 80.dp) // Avoid hugging the bottom
        ) {
            // --- 1. HEADER SECTION ---
            item {
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

            // --- 2. MAIN FORM FIELDS ---
            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {

                    // Packet Category (Dropdown)
                    RequiredLabel("Packet Category")
                    ExposedDropdownMenuBox(
                        expanded = vaultDropdownExpanded,
                        onExpandedChange = { vaultDropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = vaultCategory.ifEmpty { "Choose Vault Type" },
                            onValueChange = {},
                            readOnly = true,
                            isError = showErrors && vaultCategory.isEmpty(),
                            supportingText = {
                                if (showErrors && vaultCategory.isEmpty()) Text(
                                    "Required",
                                    color = Color(0xFFD32F2F)
                                )
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = vaultDropdownExpanded) },
                            leadingIcon = { CategoryLeadingIcon() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            colors = defaultInputColors()
                        )
                        ExposedDropdownMenu(
                            expanded = vaultDropdownExpanded,
                            onDismissRequest = { vaultDropdownExpanded = false },
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
                                    onClick = {
                                        vaultCategory = option
                                        vaultDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Packet Title
                    RequiredLabel("Packet Title")
                    OutlinedTextField(
                        value = packetTitle,
                        onValueChange = { packetTitle = it },
                        placeholder = {
                            Text(
                                "e.g., Family Photos & Videos",
                                color = colorResource(id = R.color.gray_text).copy(alpha = 0.6f)
                            )
                        },
                        isError = showErrors && packetTitle.isBlank(),
                        supportingText = {
                            if (showErrors && packetTitle.isBlank()) Text(
                                "Required",
                                color = Color(0xFFD32F2F)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = defaultInputColors(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Notes / Description
                    Text(
                        text = "Notes / Description",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black),
                        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                    )
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = {
                            Text(
                                "Add any notes or description for this packet...",
                                color = colorResource(id = R.color.gray_text).copy(alpha = 0.6f)
                            )
                        },
                        minLines = 4,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = defaultInputColors()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Upload Files Section
                    RequiredLabel("Upload Files")
                    UploadFilesSection(
                        selectedUris = uploadedFiles,
                        onFilesSelected = { uploadedFiles = uploadedFiles + it },
                        showError = showErrors
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Add Trusted Users (Multi-select)
                    RequiredLabel("Add Trusted Users")
                    ExposedDropdownMenuBox(
                        expanded = trustedUsersExpanded,
                        onExpandedChange = { trustedUsersExpanded = it }
                    ) {
                        val userText = if (selectedTrustedUsers.isEmpty()) "Select trusted users"
                        else "${selectedTrustedUsers.size} user(s) selected"

                        OutlinedTextField(
                            value = userText,
                            onValueChange = {},
                            readOnly = true,
                            isError = showErrors && selectedTrustedUsers.isEmpty(),
                            supportingText = {
                                if (showErrors && selectedTrustedUsers.isEmpty()) Text(
                                    "Select at least one",
                                    color = Color(0xFFD32F2F)
                                )
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = trustedUsersExpanded) },
                            leadingIcon = { CategoryLeadingIcon() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            colors = defaultInputColors()
                        )
                        ExposedDropdownMenu(
                            expanded = trustedUsersExpanded,
                            onDismissRequest = { trustedUsersExpanded = false },
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
                                                    checkedColor = colorResource(
                                                        id = R.color.gradient_start
                                                    )
                                                )
                                            )
                                            Spacer(Modifier.width(8.dp))
                                            Text(user, color = colorResource(id = R.color.black))
                                        }
                                    },
                                    onClick = {
                                        selectedTrustedUsers =
                                            if (isSelected) selectedTrustedUsers - user else selectedTrustedUsers + user
                                        // Keep menu open for multi-select
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // --- 3. NOMINEE DETAILS (Dynamically Rendered) ---
            itemsIndexed(nominees, key = { _, item -> item.id }) { index, nominee ->
                AnimatedVisibility(
                    visible = true,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                        NomineeDetailsCard(
                            index = index,
                            nominee = nominee,
                            showErrors = showErrors,
                            onUpdate = { updated ->
                                nominees = nominees.map { if (it.id == updated.id) updated else it }
                            },
                            onRemove = {
                                nominees = nominees.filter { it.id != nominee.id }
                            },
                            canRemove = nominees.size > 1
                        )
                    }
                }
            }

            // --- 4. ADD MORE NOMINEE BUTTON ---
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { nominees = nominees + NomineeState() }) {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = null,
                            tint = colorResource(id = R.color.gradient_start)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "Add Another Nominee",
                            color = colorResource(id = R.color.gradient_start),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // --- 5. CREATE BUTTON & NOTES ---
            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                    Button(
                        onClick = {
                            val isNomineesValid =
                                nominees.all { it.fullName.isNotBlank() && it.mobile.isNotBlank() && it.email.isNotBlank() }
                            if (vaultCategory.isNotBlank() && packetTitle.isNotBlank() && uploadedFiles.isNotEmpty() && selectedTrustedUsers.isNotEmpty() && isNomineesValid) {
                                // Form is Valid - Submit
                                showErrors = false
                            } else {
                                showErrors = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .shadow(
                                8.dp, RoundedCornerShape(20.dp),
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

                    Spacer(modifier = Modifier.height(24.dp))

                    // Note Text
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
                            withStyle(SpanStyle(color = colorResource(id = R.color.gray_text))) {
                                append("All files are encrypted. The nominee will receive access only when the configured triggers are activated.")
                            }
                        }
                        Text(text = noteText, fontSize = 12.sp, lineHeight = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun UploadFilesSection(
    selectedUris: List<Uri>,
    onFilesSelected: (List<Uri>) -> Unit,
    showError: Boolean
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        onFilesSelected(uris)
    }

    val ErrorRed = Color(0xFFD32F2F)
    val strokeColor =
        if (showError && selectedUris.isEmpty()) ErrorRed else colorResource(id = R.color.gradient_start).copy(
            alpha = 0.5f
        )
    val stroke =
        Stroke(width = 4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f))

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .drawBehind {
                    drawRoundRect(
                        color = strokeColor,
                        style = stroke,
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }
                .clip(RoundedCornerShape(16.dp))
                .clickable { launcher.launch("*/*") }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(colorResource(id = R.color.gradient_end), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.FileUpload,
                        contentDescription = "Upload",
                        tint = colorResource(id = R.color.white)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Click to upload files",
                    color = colorResource(id = R.color.gradient_start),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (selectedUris.isEmpty()) "You can select multiple files" else "${selectedUris.size} file(s) selected",
                    color = colorResource(id = R.color.gray_text),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun RequiredLabel(text: String) {
    val ErrorRed = Color(0xFFD32F2F)
    Text(
        buildAnnotatedString {
            append(text)
            withStyle(SpanStyle(color = ErrorRed)) { append(" *") }
        },
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.black),
        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
    )
}

@Composable
fun CategoryLeadingIcon() {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.gradient_end)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Rounded.Description,
            contentDescription = null,
            tint = colorResource(id = R.color.white),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun defaultInputColors() = OutlinedTextFieldDefaults.colors(
    unfocusedContainerColor = colorResource(id = R.color.white),
    focusedContainerColor = colorResource(id = R.color.white),
    errorContainerColor = colorResource(id = R.color.white),
    unfocusedBorderColor = Color(0xFFE0E0E0),
    focusedBorderColor = colorResource(id = R.color.gradient_start),
    errorBorderColor = Color(0xFFD32F2F)
)
