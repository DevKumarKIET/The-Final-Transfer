package com.example.thefinaltransfer.presentation.bottomnavigation

import android.net.http.SslCertificate.restoreState
import android.net.http.SslCertificate.saveState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navigation.Routes

// Data class mapping routing logic to complex UI representation parameters
data class BottomNavItem(
    val title: String,
    val route: Any, // Expects a Type-safe serialization object
    val iconRes: Int? = null, // For the custom icons
    val iconVector: ImageVector? = null, // Standard Material icons k liye
    val isCenterButton: Boolean = false
)

@Composable
fun TFTBottomNavigationBar(
    navHostController: NavHostController,
    currentDestination: NavDestination?
) {
    val uploadGradient = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.gradient_start),
            colorResource(id = R.color.gradient_end)
        )
    )

    val navItems = listOf(
        BottomNavItem("Home", Routes.HomeScreen, iconVector = Icons.Outlined.Home),
        BottomNavItem("Vault", Routes.VaultScreen, iconVector = Icons.Outlined.FileOpen),
        BottomNavItem("Upload", Routes.UploadScreen, iconVector = Icons.Outlined.Upload, isCenterButton = true),
        BottomNavItem("About", Routes.AboutScreen, iconVector = Icons.Outlined.Info),
        BottomNavItem("Profile", Routes.ProfileScreen, iconVector = Icons.Outlined.AccountCircle)
    )

    NavigationBar(
        modifier = Modifier.shadow(elevation = 16.dp, ambientColor = Color.Black, spotColor = Color.Black),
        containerColor = colorResource(id = R.color.navbackground),
        tonalElevation = 0.dp
    ) {
        navItems.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route?.contains(item.route::class.qualifiedName?: "") == true
            } == true

            // Micro-interaction scaling animation for the icon utilizing fluid spring kinematics
            val iconScale by animateFloatAsState(
                targetValue = if (isSelected) 1.25f else 1.0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "icon_scale_animation"
            )

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navHostController.navigate(item.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    if (item.isCenterButton) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .offset(y = (-4).dp)
                                .shadow(2.dp, CircleShape)
                                .clip(CircleShape)
                                .background(uploadGradient)
                                .scale(iconScale),
                            contentAlignment = Alignment.Center
                        ) {
                            item.iconRes?.let { res ->
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = res),
                                    contentDescription = item.title,
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    } else {
                        val iconModifier = Modifier
                            .size(22.dp)
                            .scale(iconScale)

                        if (item.iconRes!= null) {
                            Icon(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.title,
                                modifier = iconModifier
                            )
                        } else if (item.iconVector!= null) {
                            Icon(
                                imageVector = item.iconVector,
                                contentDescription = item.title,
                                modifier = iconModifier
                            )
                        }
                    }
                },
                label = {
                    if (!item.isCenterButton) {
                        Text(
                            text = item.title,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.offset(y = 2.dp)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.brand_orange),
                    selectedTextColor = colorResource(id = R.color.brand_orange),
                    unselectedIconColor = colorResource(id = R.color.black),
                    unselectedTextColor = colorResource(id = R.color.black),
                    indicatorColor = Color.Transparent
                ),
                interactionSource = remember { MutableInteractionSource() }
            )
        }
    }
}