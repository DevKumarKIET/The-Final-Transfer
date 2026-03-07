package com.example.thefinaltransfer.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.thefinaltransfer.presentation.navigation.Routes
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random

// --- Configuration ---
private const val SPLASH_ENTER_AND_HOLD_MS = 3500L
private const val SPLASH_EXIT_DURATION_MS = 1000L
private const val HEXAGON_COUNT = 16
private const val TUNNEL_SPEED = 1.25f

// --- Luxury Colors ---
val DeepSpaceBlack = Color(0xFF050505)
val NeonOrange = Color(0xFFFF8C00)
val BrightGold = Color(0xFFFFD700)
val TextGoldStart = Color(0xFFFFD54F)
val TextGoldEnd = Color(0xFFFFA000)

@Composable
fun SplashScreen(
    navHostController: NavHostController? = null // Nullable for safe previews
) {
    // We now have THREE states: Hidden -> Visible (Enter) -> Exiting (Blast forward)
    var isVisible by remember { mutableStateOf(false) }
    var isExiting by remember { mutableStateOf(false) }

    // 1. Navigation & State Timer
    LaunchedEffect(Unit) {
        isVisible = true
        delay(SPLASH_ENTER_AND_HOLD_MS)

        isExiting = true // Trigger the 3D "Pop-forward" exit animation
        delay(SPLASH_EXIT_DURATION_MS)

        navHostController?.navigate(Routes.StartingScreen) {
            // Remove splash screen from backstack so users can't press 'back' to it
            popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
        }
    }

    // 2. Infinite Animation Driver
    val infiniteTransition = rememberInfiniteTransition(label = "InfiniteLoop")
    val tunnelTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Time"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing)
        ),
        label = "Rotation"
    )

    // --- 3. Dynamic "Blast Forward" Text Animations ---

    // Scale: Starts slightly zoomed out (0.8), settles to normal (1.0), blasts past the camera (15.0) on exit
    val titleScale by animateFloatAsState(
        targetValue = when {
            isExiting -> 15f
            isVisible -> 1f
            else -> 0.8f
        },
        animationSpec = tween(
            durationMillis = if (isExiting) SPLASH_EXIT_DURATION_MS.toInt() else 2000,
            delayMillis = if (isExiting) 0 else 500,
            // Accelerate fast when exiting to simulate a forward zoom
            easing = if (isExiting) FastOutLinearInEasing else EaseOut
        ),
        label = "TitleScale"
    )

    // Alpha: Fades in normally, fades out late during the blast forward
    val titleAlpha by animateFloatAsState(
        targetValue = when {
            isExiting -> 0f
            isVisible -> 1f
            else -> 0f
        },
        animationSpec = tween(
            durationMillis = if (isExiting) 800 else 2000,
            delayMillis = if (isExiting) 200 else 500,
            easing = EaseOut
        ),
        label = "TitleAlpha"
    )

    // Quote fades out quickly when exit begins
    val quoteAlpha by animateFloatAsState(
        targetValue = when {
            isExiting -> 0f
            isVisible -> 1f
            else -> 0f
        },
        animationSpec = tween(
            durationMillis = if (isExiting) 400 else 2000,
            delayMillis = if (isExiting) 0 else 1500,
            easing = EaseOut
        ),
        label = "QuoteAlpha"
    )

    // Whole screen fade out to ensure a buttery smooth transition to the next screen
    val screenAlpha by animateFloatAsState(
        targetValue = if (isExiting) 0f else 1f,
        animationSpec = tween(
            durationMillis = SPLASH_EXIT_DURATION_MS.toInt(),
            easing = LinearEasing
        ),
        label = "ScreenAlpha"
    )

    // Acceleration of the background tunnel during exit
    val tunnelScale by animateFloatAsState(
        targetValue = if (isExiting) 3f else 1f,
        animationSpec = tween(SPLASH_EXIT_DURATION_MS.toInt(), easing = FastOutLinearInEasing),
        label = "TunnelScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepSpaceBlack)
            // The entire view fades out right before navigation
            .graphicsLayer { alpha = screenAlpha }
    ) {
        // --- LAYER 1: The Warp Tunnel with Particles ---
        Box(
            modifier = Modifier.fillMaxSize()
                .graphicsLayer {
                    scaleX = tunnelScale
                    scaleY = tunnelScale
                }
        ) {
            WarpTunnelCanvas(time = tunnelTime, rotation = rotation)
        }

        // --- LAYER 2: Cinematic Vignette ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                    center = center,
                    radius = size.minDimension * 0.85f
                )
            )
        }

        // --- LAYER 3: Luxury Typography ---
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    alpha = titleAlpha
                    scaleX = titleScale
                    scaleY = titleScale
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "The Final",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White.copy(alpha = 0.95f),
                    letterSpacing = 4.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Gradient Text
            Text(
                text = "Transfer",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(TextGoldStart, TextGoldEnd, TextGoldStart)
                    ),
                    letterSpacing = 1.sp
                )
            )
        }

        // --- LAYER 4: The Quote ---
        Text(
            text = "Better 5 years earlier,\nthan 5 minutes later.",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
                .graphicsLayer {
                    alpha = quoteAlpha
                    translationY = (1f - quoteAlpha) * 50f // Slide down on exit, slide up on enter
                }
        )
    }
}

/**
 * The Drawing Engine
 */
@Composable
fun WarpTunnelCanvas(
    time: Float,
    rotation: Float
) {
    val particles = remember { List(45) { Particle() } }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val maxRadius = size.width.coerceAtLeast(size.height) * 0.9f

        // 1. Draw Particles (Data Dust)
        particles.forEach { particle ->
            val rawProgress = (time * TUNNEL_SPEED * 0.5f + particle.offset) % 1000f
            val progress = rawProgress / 1000f

            val z = progress.pow(4f)

            if (z > 0.01f) {
                val r = particle.radius * z * maxRadius
                val angle = particle.angle + (rotation * 0.01f)
                val x = centerX + r * cos(angle)
                val y = centerY + r * sin(angle)

                val alpha = (z * 0.8f).coerceIn(0f, 1f)
                val size = 3.dp.toPx() * z

                drawCircle(
                    color = BrightGold.copy(alpha = alpha),
                    radius = size,
                    center = Offset(x.toFloat(), y.toFloat())
                )
            }
        }

        // 2. Draw Hexagon Tunnel
        for (i in 0 until HEXAGON_COUNT) {
            val rawProgress = (time * TUNNEL_SPEED + i * (1000f / HEXAGON_COUNT)) % 1000f
            val progress = rawProgress / 1000f
            val z = progress.pow(3f)

            if (z > 0.01f) {
                val radius = maxRadius * z

                // Fade logic
                val alpha = when {
                    progress < 0.1f -> progress * 10f
                    progress > 0.8f -> (1f - progress) * 5f
                    else -> 1f
                }.coerceIn(0f, 1f)

                val strokeWidth = 3.dp.toPx() * z
                val colorMix = if (progress > 0.7f) BrightGold else NeonOrange

                // Glow Pass
                drawHexagon(
                    cx = centerX, cy = centerY, radius = radius,
                    rotation = rotation,
                    color = colorMix.copy(alpha = alpha * 0.3f),
                    width = strokeWidth * 5f // Wider glow
                )

                // Core Pass
                drawHexagon(
                    cx = centerX, cy = centerY, radius = radius,
                    rotation = rotation,
                    color = Color.White.copy(alpha = alpha * 0.9f),
                    width = strokeWidth
                )
            }
        }
    }
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawHexagon(
    cx: Float, cy: Float, radius: Float, rotation: Float, color: Color, width: Float
) {
    val path = Path()
    val sides = 6
    val angleStep = (2 * Math.PI / sides)
    val rotationRad = Math.toRadians(rotation.toDouble())

    for (i in 0 until sides) {
        val angle = i * angleStep + Math.PI / 2 + rotationRad
        val x = cx + (radius * cos(angle)).toFloat()
        val y = cy + (radius * sin(angle)).toFloat()
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = width, cap = StrokeCap.Round)
    )
}

data class Particle(
    val angle: Double = Random.nextDouble(0.0, 2 * Math.PI),
    val radius: Float = Random.nextDouble(0.2, 1.5).toFloat(),
    val offset: Float = Random.nextFloat() * 1000f
)