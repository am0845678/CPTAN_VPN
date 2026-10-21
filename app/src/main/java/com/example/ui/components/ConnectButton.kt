package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberBlackBg
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberConnectedGreen
import com.example.ui.theme.CyberCrimsonDark
import com.example.ui.theme.CyberCrimsonDim
import com.example.ui.theme.CyberCrimsonGlow
import com.example.ui.theme.CyberCrimsonMain
import com.example.ui.theme.CyberErrorRed
import com.example.ui.theme.CyberGunmetalBorder
import com.example.ui.theme.CyberPureWhite
import com.example.ui.theme.CyberSilverMuted
import com.example.ui.theme.CyberSilverSubtle
import com.example.ui.theme.CyberSilverText
import com.example.vpn.VpnStatus
import kotlinx.coroutines.launch

@Composable
fun ConnectButton(
    status: VpnStatus,
    formattedDuration: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isConnected = status == VpnStatus.CONNECTED
    val isConnecting = status == VpnStatus.CONNECTING
    val isFailed = status == VpnStatus.FAILED

    val scope = rememberCoroutineScope()
    val buttonScale = remember { Animatable(1f) }

    // Restrained crimson energy pulse ring
    val infiniteTransition = rememberInfiniteTransition(label = "crimson_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isConnected || isConnecting) 1.22f else 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = if (isConnected || isConnecting) 0.35f else 0.12f,
        targetValue = if (isConnected || isConnecting) 0.05f else 0.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    // Smooth status color transitions
    val targetStatusColor = when (status) {
        VpnStatus.CONNECTED -> CyberConnectedGreen
        VpnStatus.CONNECTING -> CyberCrimsonGlow
        VpnStatus.FAILED -> CyberErrorRed
        else -> CyberSilverMuted
    }
    val animatedStatusColor by animateColorAsState(
        targetValue = targetStatusColor,
        animationSpec = tween(400),
        label = "status_color"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tactical Status Badge Card
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(CyberCardBg)
                .border(1.dp, CyberGunmetalBorder, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(animatedStatusColor)
                )

                Spacer(modifier = Modifier.width(8.dp))

                val statusTitle = when (status) {
                    VpnStatus.CONNECTED -> "CONNECTED // متصل"
                    VpnStatus.CONNECTING -> "CONNECTING // در حال اتصال..."
                    VpnStatus.DISCONNECTING -> "DISCONNECTING // در حال قطع..."
                    VpnStatus.FAILED -> "ERROR // خطا در اتصال"
                    VpnStatus.DISCONNECTED -> "DISCONNECTED // قطع شده"
                }

                Text(
                    text = statusTitle,
                    color = animatedStatusColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    modifier = Modifier.testTag("vpn_status_text")
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Large Cyberpunk Connection Button
        Box(
            modifier = Modifier.size(230.dp),
            contentAlignment = Alignment.Center
        ) {
            // Outermost Restrained Crimson Glow Ring
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(
                        when {
                            isConnected -> CyberConnectedGreen.copy(alpha = pulseAlpha)
                            isConnecting -> CyberCrimsonGlow.copy(alpha = pulseAlpha)
                            isFailed -> CyberErrorRed.copy(alpha = pulseAlpha)
                            else -> CyberCrimsonMain.copy(alpha = pulseAlpha)
                        }
                    )
            )

            // Outer Metallic Radar Ring
            Box(
                modifier = Modifier
                    .size(184.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.2.dp,
                        color = when {
                            isConnected -> CyberConnectedGreen.copy(alpha = 0.5f)
                            isConnecting -> CyberCrimsonGlow.copy(alpha = 0.5f)
                            else -> CyberGunmetalBorder
                        },
                        shape = CircleShape
                    )
            )

            // Inner Intermediate Dark Ring
            Box(
                modifier = Modifier
                    .size(164.dp)
                    .clip(CircleShape)
                    .background(CyberBlackBg)
                    .border(
                        width = 2.dp,
                        color = if (isConnected) CyberConnectedGreen.copy(alpha = 0.3f)
                                else CyberCrimsonDim,
                        shape = CircleShape
                    )
            )

            // Central Matte Core Button (140dp) with Press Animation
            val coreGradient = when {
                isConnected -> Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF00B050),
                        Color(0xFF004D22),
                        CyberBlackBg
                    )
                )
                isConnecting -> Brush.radialGradient(
                    colors = listOf(
                        CyberCrimsonGlow,
                        CyberCrimsonMain,
                        CyberCrimsonDark
                    )
                )
                isFailed -> Brush.radialGradient(
                    colors = listOf(
                        CyberErrorRed,
                        CyberCrimsonDark,
                        CyberBlackBg
                    )
                )
                else -> Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2A080E),
                        Color(0xFF19060A),
                        CyberBlackBg
                    )
                )
            }

            Box(
                modifier = Modifier
                    .size(140.dp)
                    .scale(buttonScale.value)
                    .clip(CircleShape)
                    .background(coreGradient)
                    .border(
                        width = 2.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                if (isConnected) CyberConnectedGreen else CyberCrimsonGlow,
                                if (isConnected) Color(0xFF005224) else CyberCrimsonDark
                            )
                        ),
                        shape = CircleShape
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                scope.launch { buttonScale.animateTo(0.93f, tween(100)) }
                                tryAwaitRelease()
                                scope.launch { buttonScale.animateTo(1f, tween(150)) }
                                onClick()
                            }
                        )
                    }
                    .testTag("main_connect_button"),
                contentAlignment = Alignment.Center
            ) {
                if (isConnecting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(54.dp),
                        color = CyberPureWhite,
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.PowerSettingsNew,
                        contentDescription = "اتصال یا قطع VPN",
                        tint = if (isConnected) CyberPureWhite else CyberCrimsonGlow,
                        modifier = Modifier.size(58.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Tactical Connection Timer (00:00:00) with High Contrast Typography
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(CyberCardBg)
                .border(0.8.dp, CyberGunmetalBorder, RoundedCornerShape(6.dp))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                text = formattedDuration,
                color = if (isConnected) CyberPureWhite else CyberSilverMuted,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 2.sp,
                modifier = Modifier.testTag("connection_timer_text")
            )
        }
    }
}
