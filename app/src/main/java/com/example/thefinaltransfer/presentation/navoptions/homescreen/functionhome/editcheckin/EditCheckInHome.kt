package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.editcheckin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.editcheckin.componentsedit.*

private val OrangePrimary  = Color(0xFFFFA62A)
private val OrangeDeep     = Color(0xFFFF6B35)
private val BgCream        = Color(0xFFFFF6EE)
private val SuccessGreen   = Color(0xFF4CAF50)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCheckInHomeScreen(
    navController: NavHostController,
    viewModel: EditCheckInViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(uiState.showSuccessSnackbar) {
        if (uiState.showSuccessSnackbar) {
            snackbarHostState.showSnackbar(
                message = "Check-in settings updated successfully ✓",
                duration = SnackbarDuration.Short
            )
            viewModel.onSnackbarDismissed()
        }
    }


    Scaffold(
        containerColor = BgCream,
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = SuccessGreen,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {

            //Header
            item {
                EditCheckInHeader(onBackClick = { navController.navigateUp() })
            }

            //Current Status Card
            item {
                CheckInStatusCard()
            }

            //Interval Selector Card
            item {
                IntervalSelectorCard(
                    uiState = uiState,
                    onChipSelected = viewModel::onIntervalChipSelected,
                    onCustomDurationChanged = viewModel::onCustomDurationChanged,
                    onCustomUnitChanged = viewModel::onCustomUnitChanged,
                    onMissedCountChanged = viewModel::onMissedCheckInCountChanged
                )
            }

            //Reminder Settings Card
            item {
                ReminderSettingsCard(
                    uiState = uiState,
                    onReminderDaysChanged = viewModel::onReminderDaysChanged,
                    onEmailToggle = viewModel::onEmailToggle,
                    onSmsToggle = viewModel::onSmsToggle,
                    onPushToggle = viewModel::onPushToggle
                )
            }

            // History Preview Card
            item {
                HistoryPreviewCard(onViewAll = { /* TODO: Navigate to full history */ })
            }

            // Save Button
            item {
                Spacer(Modifier.height(8.dp))
                SaveButton(
                    enabled = uiState.isSaveEnabled,
                    onClick = {
                        keyboardController?.hide()
                        viewModel.onSaveChanges()
                    }
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

//Header composable
@Composable
private fun EditCheckInHeader(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(
                Brush.linearGradient(listOf(OrangePrimary, OrangeDeep))
            )
            .height(170.dp)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Back button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Title + subtitle
            Column {
                Text(
                    text = "Edit Check-in",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Configure your check-in schedule and notification preferences",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }
    }
}
