package com.example.thefinaltransfer.presentation.bottomnavigation

import com.example.thefinaltransfer.presentation.navigation.Routes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: Routes,
    val isCenter: Boolean = false
)


val bottomNavItems = listOf(
    BottomNavItem(
        label = "Home",
        icon = Icons.Outlined.Home,
        route = Routes.HomeScreen
    ),
    BottomNavItem(
        label = "Vault",
        icon = Icons.Outlined.Lock,
        route = Routes.VaultScreen
    ),
    BottomNavItem(
        label = "Upload",
        icon = Icons.Outlined.Upload,
        route = Routes.UploadScreen,
        isCenter = true
    ),
    BottomNavItem(
        label = "About",
        icon = Icons.Outlined.Info,
        route = Routes.AboutScreen
    ),
    BottomNavItem(
        label = "Profile",
        icon = Icons.Outlined.Person,
        route = Routes.ProfileScreen
    )
)


@Composable
fun TFTBottomNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    val primaryOrange = Color(0xFFF5A623)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                ambientColor = Color.Black.copy(alpha = 0.3f),
                spotColor = Color.Black.copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { item ->
                val isSelected = currentDestination?.hasRoute(item.route::class) == true

                if (item.isCenter) {
                    CenterNavItem(
                        item = item,
                        isSelected = isSelected,
                        primaryColor = primaryOrange,
                        onClick = {
                            navigateToBottomNavDestination(navController, item.route)
                        }
                    )
                } else {
                    RegularNavItem(
                        item = item,
                        isSelected = isSelected,
                        primaryColor = primaryOrange,
                        onClick = {
                            navigateToBottomNavDestination(navController, item.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RegularNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    primaryColor: Color,
    onClick: () -> Unit
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) primaryColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        animationSpec = tween(durationMillis = 250),
        label = "iconColor_${item.label}"
    )

    val labelColor by animateColorAsState(
        targetValue = if (isSelected) primaryColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        animationSpec = tween(durationMillis = 250),
        label = "labelColor_${item.label}"
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = item.label,
            color = labelColor,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            maxLines = 1
        )
    }
}

@Composable
private fun CenterNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    primaryColor: Color,
    onClick: () -> Unit
) {
    val containerSize: Dp by animateDpAsState(
        targetValue = if (isSelected) 60.dp else 56.dp,
        animationSpec = tween(durationMillis = 250),
        label = "centerSize"
    )

    val containerColor by animateColorAsState(
        targetValue = primaryColor,
        animationSpec = tween(durationMillis = 250),
        label = "centerColor"
    )

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(containerSize)
                .shadow(
                    elevation = if (isSelected) 12.dp else 6.dp,
                    shape = CircleShape,
                    ambientColor = primaryColor.copy(alpha = 0.4f),
                    spotColor = primaryColor.copy(alpha = 0.4f)
                )
                .clip(CircleShape)
                .background(containerColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = item.label,
            color = primaryColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
    }
}

private fun navigateToBottomNavDestination(
    navController: NavController,
    route: Routes
) {
    navController.navigate(route) {
        // Pop up to the start destination to avoid stacking screens
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid duplicate copies of the same destination
        launchSingleTop = true
        // Restore state when navigating back to a previously selected tab
        restoreState = true
    }
}