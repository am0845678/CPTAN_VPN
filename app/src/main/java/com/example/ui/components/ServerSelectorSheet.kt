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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.VpnConfigEntity
import com.example.ui.theme.CyberBlackBg
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCharcoalSurface
import com.example.ui.theme.CyberConnectedGreen
import com.example.ui.theme.CyberCrimsonDark
import com.example.ui.theme.CyberCrimsonGlow
import com.example.ui.theme.CyberCrimsonMain
import com.example.ui.theme.CyberErrorRed
import com.example.ui.theme.CyberGunmetalBorder
import com.example.ui.theme.CyberPureWhite
import com.example.ui.theme.CyberSilverMuted
import com.example.ui.theme.CyberSilverSubtle
import com.example.ui.theme.CyberSilverText
import com.example.ui.theme.CyberWarningAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerSelectorSheet(
    configs: List<VpnConfigEntity>,
    selectedConfig: VpnConfigEntity?,
    isTestingPingForId: Long?,
    isPingingAll: Boolean,
    onSelectConfig: (Long) -> Unit,
    onTestPing: (VpnConfigEntity) -> Unit,
    onPingAll: () -> Unit,
    onDeleteConfig: (VpnConfigEntity) -> Unit,
    onOpenAddSubscription: () -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier
) {
    // Rotation animation for Ping All radar scanner
    val infiniteTransition = rememberInfiniteTransition(label = "radar_spin")
    val radarAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_angle"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = CyberCharcoalSurface,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(42.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(CyberGunmetalBorder)
            )
        },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            // Header with Close and Add button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "بستن",
                        tint = CyberSilverText
                    )
                }

                Text(
                    text = "سرورهای اتصال // SERVERS",
                    color = CyberPureWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )

                IconButton(onClick = onOpenAddSubscription) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "افزودن اشتراک",
                        tint = CyberCrimsonGlow
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Prominent "PING ALL" Button
            if (configs.isNotEmpty()) {
                Button(
                    onClick = onPingAll,
                    enabled = !isPingingAll,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("ping_all_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CyberCardBg,
                        contentColor = CyberPureWhite
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, CyberCrimsonMain)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (isPingingAll) {
                            Icon(
                                imageVector = Icons.Default.Radar,
                                contentDescription = null,
                                tint = CyberCrimsonGlow,
                                modifier = Modifier
                                    .size(20.dp)
                                    .rotate(radarAngle)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "در حال اسکن پینگ همه سرورها...",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyberCrimsonGlow
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.NetworkCheck,
                                contentDescription = null,
                                tint = CyberCrimsonGlow,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "PING ALL // سنجش پینگ همه سرورها",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            // Server List
            if (configs.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Dns,
                        contentDescription = null,
                        tint = CyberSilverMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "هیچ سروری اضافه نشده است",
                        color = CyberSilverMuted,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onOpenAddSubscription,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyberCrimsonMain,
                            contentColor = CyberPureWhite
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "افزودن اشتراک جدید")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(configs, key = { it.id }) { config ->
                        val isSelected = config.id == selectedConfig?.id
                        val isTestingThis = isTestingPingForId == config.id

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) Color(0xFF1E1015) else CyberCardBg)
                                .border(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    color = if (isSelected) CyberCrimsonGlow else CyberGunmetalBorder,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { onSelectConfig(config.id) }
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                                .testTag("server_item_${config.id}")
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Radio / Check Indicator
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                if (isSelected) CyberCrimsonMain.copy(alpha = 0.25f)
                                                else Color(0xFF181C26)
                                            )
                                            .border(
                                                1.dp,
                                                if (isSelected) CyberCrimsonGlow else CyberGunmetalBorder,
                                                RoundedCornerShape(8.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = if (isSelected) Icons.Default.Check else Icons.Default.Dns,
                                            contentDescription = null,
                                            tint = if (isSelected) CyberCrimsonGlow else CyberSilverMuted,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = config.name,
                                            color = CyberPureWhite,
                                            fontSize = 14.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(Color(0xFF280B12))
                                                    .border(0.8.dp, CyberCrimsonGlow.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                                    .padding(horizontal = 5.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = config.protocol,
                                                    color = CyberCrimsonGlow,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Black
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(8.dp))

                                            Text(
                                                text = "${config.serverAddress}:${config.serverPort}",
                                                color = CyberSilverMuted,
                                                fontSize = 11.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }

                                // Latency value, Ping button & Delete button
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (isTestingThis) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(16.dp),
                                            color = CyberCrimsonGlow,
                                            strokeWidth = 2.dp
                                        )
                                    } else if (config.latencyMs != null) {
                                        val color = when {
                                            config.latencyMs < 130 -> CyberConnectedGreen
                                            config.latencyMs < 280 -> CyberWarningAmber
                                            else -> CyberErrorRed
                                        }
                                        Text(
                                            text = "${config.latencyMs} ms",
                                            color = color,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(4.dp))

                                    // Ping Single Server Icon
                                    IconButton(
                                        onClick = { onTestPing(config) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.NetworkCheck,
                                            contentDescription = "تست پینگ",
                                            tint = CyberCrimsonGlow,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Delete Config Icon
                                    IconButton(
                                        onClick = { onDeleteConfig(config) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "حذف کانفیگ",
                                            tint = CyberErrorRed.copy(alpha = 0.8f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
