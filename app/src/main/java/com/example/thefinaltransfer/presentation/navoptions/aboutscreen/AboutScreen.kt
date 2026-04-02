package com.example.thefinaltransfer.presentation.navoptions.aboutscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thefinaltransfer.presentation.bottomnavigation.TFTBottomNavigationBar
import com.example.thefinaltransfer.presentation.navoptions.aboutscreen.aboutcomponents.AboutHeader
import com.example.thefinaltransfer.presentation.navoptions.aboutscreen.aboutcomponents.AboutViewModel
import com.example.thefinaltransfer.presentation.navoptions.aboutscreen.aboutcomponents.HowItWorksCard
import com.example.thefinaltransfer.presentation.navoptions.aboutscreen.aboutcomponents.LegalCard
import com.example.thefinaltransfer.presentation.navoptions.aboutscreen.aboutcomponents.MissionCard
import com.example.thefinaltransfer.presentation.navoptions.aboutscreen.aboutcomponents.TrustFeatureCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AboutScreen(
    navHostController: NavHostController,
    viewModel: AboutViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        containerColor = Color(0xFFFFF6EE)
    ) { innerPadding ->

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFFFA62A))
            }
        } else if (uiState.howItWorksItems.isEmpty() && uiState.trustFeatures.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No data available", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                // --- SECTION 1: HEADER ---
                item {
                    AboutHeader()
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // --- SECTION 2: MISSION CARD ---
                item {
                    MissionCard(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .animateItem()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // --- SECTION 3: HOW IT WORKS ---
                item {
                    SectionHeader("How It Works")
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(
                    items = uiState.howItWorksItems,
                    key = { it.stepNumber }
                ) { item ->
                    HowItWorksCard(
                        item = item,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 5.dp)
                            .animateItem()
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                // --- SECTION 4: TRUST & SECURITY ---
                item {
                    SectionHeader("Trust & Security")
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(
                    items = uiState.trustFeatures,
                    key = { it.title }
                ) { feature ->
                    TrustFeatureCard(
                        item = feature,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 5.dp)
                            .animateItem()
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                // --- SECTION 5: LEGAL & PRIVACY ---
                item {
                    LegalCard(
                        title = "Legal Compliance & KYC",
                        icon = Icons.Outlined.Verified,
                        bodyText = "All users undergo Aadhaar-based KYC verification. Nominees are verified before packet delivery. We comply with legal frameworks for digital asset transfer and maintain audit trails for regulatory compliance.",
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .animateItem()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    LegalCard(
                        title = "Your Privacy Matters",
                        icon = Icons.Outlined.Shield,
                        bodyText = "We use zero-knowledge encryption, meaning we never have access to your unencrypted data. Your packets remain completely private and can only be unlocked by your nominees when properly triggered and verified.",
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .animateItem()
                    )
                }
            }
        }
    }
}

// Reusable localized component for section headers
@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF1A1A1A),
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen(navHostController = rememberNavController())
}