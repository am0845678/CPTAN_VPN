package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberConnectedGreen
import com.example.ui.theme.CyberCrimsonGlow
import com.example.ui.theme.CyberCrimsonMain
import com.example.ui.theme.CyberGunmetalBorder
import com.example.ui.theme.CyberPureWhite
import com.example.ui.theme.CyberSilverMuted
import com.example.ui.theme.CyberSilverSubtle
import com.example.ui.theme.CyberSilverText
import com.example.vpn.VpnStatus

@Composable
fun HeaderBar(
    vpnStatus: VpnStatus,
    onAddClick: () -> Unit,
    onClipboardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Subtle animated red energy line beneath the header
    val infiniteTransition = rememberInfiniteTransition(label = "energy_pulse")
    val pulseOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_offset"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Action Buttons: Tactical Add (+) & Clipboard
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Add Subscription Button (+) with tactical angular styling
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(CyberCardBg)
                        .border(1.dp, CyberGunmetalBorder, RoundedCornerShape(10.dp))
                        .clickable(onClick = onAddClick)
                        .testTag("header_add_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "افزودن اشتراک",
                        tint = CyberCrimsonGlow,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Clipboard Paste Button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(CyberCardBg)
                        .border(1.dp, CyberGunmetalBorder, RoundedCornerShape(10.dp))
                        .clickable(onClick = onClipboardClick)
                        .testTag("header_clipboard_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Assignment,
                        contentDescription = "چسباندن از کلیپبورد",
                        tint = CyberSilverText,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Right: Brand & Tactical Protection Status
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    // Custom Tactical Typographic Brand Treatment
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CyberCrimsonMain.copy(alpha = 0.2f))
                                .border(0.8.dp, CyberCrimsonGlow.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "PRO",
                                color = CyberCrimsonGlow,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "PARS PING",
                            color = CyberPureWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.SansSerif,
                            letterSpacing = 1.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    // Status Indicator Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val statusColor = when (vpnStatus) {
                            VpnStatus.CONNECTED -> CyberConnectedGreen
                            VpnStatus.CONNECTING -> CyberCrimsonGlow
                            else -> CyberSilverMuted
                        }
                        val statusLabel = when (vpnStatus) {
                            VpnStatus.CONNECTED -> "محافظت فعال // SECURE"
                            VpnStatus.CONNECTING -> "در حال رمزنگاری..."
                            VpnStatus.FAILED -> "خطای اتصال // ALERT"
                            else -> "غیرفعال // STANDBY"
                        }

                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(statusColor)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = statusLabel,
                            color = statusColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.3.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Preserved Tactical Cyber Shield Emblem
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    CyberCardBg,
                                    Color(0xFF1F080E)
                                )
                            )
                        )
                        .border(1.2.dp, CyberCrimsonGlow.copy(alpha = 0.6f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Pars Ping Shield",
                        tint = CyberCrimsonGlow,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Animated red energy pulse line beneath header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            CyberCrimsonMain.copy(alpha = 0.4f),
                            CyberCrimsonGlow,
                            CyberCrimsonMain.copy(alpha = 0.4f),
                            Color.Transparent
                        ),
                        startX = pulseOffset * 400f,
                        endX = (pulseOffset + 1f) * 400f
                    )
                )
        )
    }
}
