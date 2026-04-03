package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical.ActionButtons
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical.AssignedNomineeCard
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical.CriticalDetailHeader
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical.FilesListCard
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical.NotesCard
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical.StatsRowCard
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical.TriggerStatusCard
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc.componentcritical.TrustedNomineesCard

@Composable
fun CriticalDetailScreen(
    navController: NavHostController,
    viewModel: CrticialDetialViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // ✅ No bottomBar — sits inside TFTNavigationSystem Scaffold
    Scaffold(
        containerColor = Color(0xFFFFF6EE)
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {

            // ── Section 1: Header ─────────────────────────────────────────────
            item {
                CriticalDetailHeader(
                    packetTitle = uiState.packetTitle,
                    vaultType   = uiState.vaultType,
                    onBackClick = { navController.navigateUp() },
                    onEditClick = { /* TODO: Navigate to edit screen */ }
                )
            }

            // ── Section 2: Stats row — floats over header ─────────────────────
            // Uses offset(-20.dp) to overlap the header bottom slightly
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-20).dp)
                        .zIndex(1f)
                ) {
                    StatsRowCard(
                        fileCount   = uiState.fileCount,
                        status      = uiState.status,
                        createdDate = uiState.createdDate
                    )
                }
            }

            // ── Section 3: Assigned Nominee card ──────────────────────────────
            item {
                AssignedNomineeCard(
                    nomineeName         = uiState.nomineeName,
                    nomineeRelationship = uiState.nomineeRelationship,
                    nomineeEmail        = uiState.nomineeEmail,
                    nomineePhone        = uiState.nomineePhone,
                    nomineeAddedDate    = uiState.nomineeAddedDate,
                    modifier            = Modifier.padding(top = 4.dp)
                )
            }

            // ── Section 4: Notes card ─────────────────────────────────────────
            item {
                NotesCard(
                    notes    = uiState.notes,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            // ── Section 5: Files list card ────────────────────────────────────
            item {
                FilesListCard(
                    files    = uiState.files,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            // ── Section 6: Trusted Nominees card ──────────────────────────────
            item {
                TrustedNomineesCard(
                    nominees = uiState.trustedNominees,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            // ── Section 7: Trigger Status card ────────────────────────────────
            item {
                TriggerStatusCard(
                    triggerType     = uiState.triggerType,
                    checkInInterval = uiState.checkInInterval,
                    missedThreshold = uiState.missedThreshold,
                    triggerStatus   = uiState.triggerStatus,
                    modifier        = Modifier.padding(top = 12.dp)
                )
            }

            // ── Section 8: Action buttons ─────────────────────────────────────
            item {
                ActionButtons(
                    showDeleteDialog = uiState.showDeleteDialog,
                    onEditClick      = { /* TODO: Navigate to edit screen */ },
                    onDeleteClick    = { viewModel.onDeleteClick() },
                    onDeleteConfirm  = { viewModel.onDeleteConfirmed() },
                    onDeleteDismiss  = { viewModel.onDeleteDismiss() },
                    modifier         = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PacketDetailScreenPreview() {
    CriticalDetailScreen(navController = rememberNavController())
}