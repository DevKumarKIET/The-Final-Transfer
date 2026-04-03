package com.example.thefinaltransfer.presentation.trusteduser

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.data.model.TrustedUserModel

@Composable
fun TrustedUserScreen(
    navHostController: NavHostController,
    viewModel: TrustedUserViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddForm by remember { mutableStateOf(false) }

    // Edit & Delete States
    var editingUserId by remember { mutableStateOf<String?>(null) }
    var showDeleteConfirmFor by remember { mutableStateOf<String?>(null) }

    // Form States
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }

    val primaryColor = colorResource(id = R.color.brand_orange)
    val gradientStart = colorResource(id = R.color.gradient_start)
    val gradientEnd = colorResource(id = R.color.gradient_end)
    val backgroundPastel = Color(0xFFFFF9F0) // Matching mockup bg

    val snackbarHostState = remember { SnackbarHostState() }


    Box(modifier = Modifier.fillMaxSize().background(backgroundPastel)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // --- HEADER ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(listOf(gradientEnd, gradientStart)),
                        shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                    )
                    .padding(top = 40.dp, bottom = 40.dp, start = 24.dp, end = 24.dp)
            ) {
                Column {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            .padding(4.dp)
                            .clickable { navHostController.popBackStack() }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Trusted Users",
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${uiState.users.size} trusted users",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 16.sp
                            )
                        }

                        Button(
                            onClick = {
                                editingUserId = null
                                fullName = ""
                                email = ""
                                mobile = ""
                                showAddForm = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("+ Add", color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
            }

            // --- CONTENT ---
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(top = 20.dp, bottom = 100.dp)
            ) {
                // Warning Banner if < 2 users
                if (uiState.users.size < 2 && !showAddForm) {
                    item {
                        ActionRequiredBanner()
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Add / Edit Form Logic
                item {
                    AnimatedVisibility(visible = showAddForm) {
                        AddTrustedUserCard(
                            isEditing = editingUserId != null,
                            fullName = fullName,
                            onFullNameChange = { fullName = it },
                            email = email,
                            onEmailChange = { email = it },
                            mobile = mobile,
                            onMobileChange = { mobile = it },
                            onClose = {
                                showAddForm = false
                                viewModel.clearMessages()
                            },
                            isLoading = uiState.isLoading,
                            onSave = {
                                if (editingUserId != null) {
                                    viewModel.updateExistingUser(editingUserId!!, fullName, email, mobile)
                                } else {
                                    viewModel.addTrustedUser(fullName, email, mobile)
                                }
                            }
                        )
                    }
                    if (showAddForm) Spacer(modifier = Modifier.height(16.dp))
                }

                // Priority Explanation Box
                if (uiState.users.isNotEmpty()) {
                    item {
                        PriorityInfoBox()
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                // User Cards
                items(uiState.users) { user ->
                    TrustedUserCard(
                        user = user,
                        isPrimary = user.priorityOrder == 1,
                        primaryColor = primaryColor,
                        onMakePrimary = { viewModel.makePrimary(user) },
                        onEdit = {
                            fullName = user.fullName
                            email = user.email
                            mobile = user.mobileNumber
                            editingUserId = user.id
                            showAddForm = true
                        },
                        onDelete = { showDeleteConfirmFor = user.id }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // Modal Delete Confirmation
        if (showDeleteConfirmFor != null) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmFor = null },
                title = { Text("Remove Trusted User", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to permanently remove this user? This action cannot be undone.") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.removeTrustedUser(showDeleteConfirmFor!!)
                            showDeleteConfirmFor = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                    ) {
                        Text("Delete", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirmFor = null }) {
                        Text("Cancel", color = Color.Gray)
                    }
                }
            )
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp)
        )
    }

    // Effect for showing messages and coordinating successful form closures
    LaunchedEffect(uiState.errorMessage, uiState.successMessage) {
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessages()
            // Clear inputs when successfully saved and hide form
            showAddForm = false
            editingUserId = null
            fullName = ""
            email = ""
            mobile = ""
        }
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessages()
        }
    }
}

@Composable
fun ActionRequiredBanner() {
    Card(
        modifier = Modifier.fillMaxWidth().border(1.dp, Color(0xFFFFCDD2), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0F0)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier.size(40.dp).background(Color(0xFFFFCDD2), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("⚠️", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Action Required!", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "You must add at least 2 trusted users. Trusted users can verify your check-ins and help manage your digital legacy in case of emergency.",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun PriorityInfoBox() {
    Card(
        modifier = Modifier.fillMaxWidth().border(1.dp, Color(0xFFFFE0B2), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = "About Priority: The priority user receives additional verification requests and is contacted first in case of missed check-ins.",
            fontSize = 13.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(16.dp),
            lineHeight = 18.sp
        )
    }
}

@Composable
fun TrustedUserCard(
    user: TrustedUserModel,
    isPrimary: Boolean,
    primaryColor: Color,
    onMakePrimary: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar with Star badge
                Box(contentAlignment = Alignment.TopEnd) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(
                                color = if (isPrimary) primaryColor else Color(0xFFFFCC80),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                    if (isPrimary) {
                        Box(
                            modifier = Modifier.offset(x = 8.dp, y = (-8).dp).size(24.dp).background(Color.White, CircleShape).padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Outlined.Star, contentDescription = "Primary", tint = primaryColor, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = user.fullName, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF333333))
                        if (isPrimary) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(color = Color(0xFFFFF3E0), shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, primaryColor)
                            ) {
                                Text("Priority", fontSize = 11.sp, color = primaryColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = user.email, fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Phone, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = user.mobileNumber, fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(modifier = Modifier.fillMaxWidth()) {
                if (!isPrimary) {
                    Button(
                        onClick = onMakePrimary,
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF3E0)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Outlined.StarBorder, contentDescription = null, tint = primaryColor, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Priority", color = primaryColor, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Button(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f).height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF3E0)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Outlined.Edit, contentDescription = null, tint = primaryColor, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Edit", color = primaryColor, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onDelete,
                    modifier = Modifier.width(56.dp).height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Outlined.DeleteOutline, contentDescription = "Delete", tint = Color(0xFFD32F2F))
                }
            }
        }
    }
}

@Composable
fun AddTrustedUserCard(
    isEditing: Boolean,
    fullName: String,
    onFullNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    mobile: String,
    onMobileChange: (String) -> Unit,
    onClose: () -> Unit,
    isLoading: Boolean = false,
    onSave: () -> Unit
) {
    // Basic Input Validation
    val isEmailValid = email.contains("@") && email.contains(".") && email.length > 5
    val isMobileValid = mobile.length == 10 && mobile.all { it.isDigit() }
    val isFormValid = fullName.isNotBlank() && isEmailValid && isMobileValid

    val titleText = if (isEditing) "Edit Trusted User" else "Add Trusted User"
    val buttonText = if (isEditing) "Save Changes" else "Add Trusted User"

    Card(
        modifier = Modifier.fillMaxWidth().border(1.dp, Color(0xFFFFCC80), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(titleText, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF333333))
                Icon(
                    Icons.Outlined.Close,
                    contentDescription = "Close",
                    tint = Color.Gray,
                    modifier = Modifier.clickable { onClose() }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = onFullNameChange,
                placeholder = { Text("Full Name", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedBorderColor = colorResource(id = R.color.brand_orange)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = { Text("Email Address", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                isError = email.isNotBlank() && !isEmailValid,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedBorderColor = colorResource(id = R.color.brand_orange),
                    errorBorderColor = Color(0xFFD32F2F)
                )
            )
            if (email.isNotBlank() && !isEmailValid) {
                Text("Enter a valid email address", color = Color(0xFFD32F2F), fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = mobile,
                onValueChange = onMobileChange,
                placeholder = { Text("Phone Number (10 digits)", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                isError = mobile.isNotBlank() && !isMobileValid,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedBorderColor = colorResource(id = R.color.brand_orange),
                    errorBorderColor = Color(0xFFD32F2F)
                )
            )
            if (mobile.isNotBlank() && !isMobileValid) {
                Text("Phone number must be exactly 10 digits", color = Color(0xFFD32F2F), fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.gradient_start),
                    disabledContainerColor = Color(0xFFEEEEEE),
                    disabledContentColor = Color(0xFFAAAAAA)
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = isFormValid && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.Gray, modifier = Modifier.size(24.dp))
                } else {
                    Text(buttonText, color = if (isFormValid) Color.White else Color.Gray, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
