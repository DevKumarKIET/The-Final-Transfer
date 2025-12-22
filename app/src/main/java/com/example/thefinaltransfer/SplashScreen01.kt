import android.R.attr.onClick
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.thefinaltransfer.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefinaltransfer.MainActivity
import com.example.thefinaltransfer.context
import kotlin.jvm.java

// --- Color Palette based on Image ---
val CreamBackground = Color(0xFFFEF9EC) // Warm cream/beige
val IconGradientStart = Color(0xFFFDBA55) // Lighter orange
val IconGradientEnd = Color(0xFFE96E31) // Darker terracotta orange
val PrimaryOrange = Color(0xFFE76A34) // Button color
val TextBrown = Color(0xFF6D4C41) // Dark brown for text
val InactiveDotColor = Color(0xFFE0D6C8) // Beige/Gray for dots

@Composable
fun OnboardingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        // 1. Lock Icon with Gradient and Shadow
        Box(
            modifier = Modifier
                .size(140.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = CircleShape,
                    spotColor = PrimaryOrange.copy(alpha = 0.3f),
                    ambientColor = PrimaryOrange.copy(alpha = 0.3f)
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(IconGradientStart, IconGradientEnd)
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.lock_icon),
                contentDescription = "Secure Lock",
                modifier = Modifier.size(70.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // 2. Typography
        Text(
            text = "Store Everything Securely",
            color = TextBrown,
            fontSize = 28.sp,
            fontFamily = FontFamily.Serif, // Matches the classic serif look in the image
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 38.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Keep all your important documents, passwords, and memories in one safe place with military-grade encryption.",
            color = TextBrown.copy(alpha = 0.7f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // 4. Buttons
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryOrange
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 4.dp
            )
        ) {
            Text(
                text = "Next",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                // Create an Intent to launch MainActivity
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text(
                text = "Skip",
                color = TextBrown.copy(alpha = 0.8f),
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    OnboardingScreen()
}