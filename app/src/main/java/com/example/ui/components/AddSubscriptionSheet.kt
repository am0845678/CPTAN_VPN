package com.example.ui.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.VpnConfigEntity
import com.example.ui.theme.CyberBlackBg
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCharcoalSurface
import com.example.ui.theme.CyberConnectedGreen
import com.example.ui.theme.CyberCrimsonGlow
import com.example.ui.theme.CyberCrimsonMain
import com.example.ui.theme.CyberErrorRed
import com.example.ui.theme.CyberGunmetalBorder
import com.example.ui.theme.CyberPureWhite
import com.example.ui.theme.CyberSilverMuted
import com.example.ui.theme.CyberSilverText
import com.example.ui.theme.CyberTelegram
import com.example.ui.viewmodel.SubscriptionUiNotice

private const val PARS_PING_BOT_URL = "https://t.me/pars_pingbot?start=8361681340"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubscriptionSheet(
    urlInput: String,
    isLoading: Boolean,
    notice: SubscriptionUiNotice?,
    configs: List<VpnConfigEntity>,
    onUrlChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onPasteClipboard: () -> Unit,
    onSelectConfig: (Long) -> Unit,
    onDeleteConfig: (VpnConfigEntity) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

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
            // Sheet Header with Close Button
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
                    text = "افزودن اشتراک // SUBSCRIPTION",
                    color = CyberPureWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Section 1: Subscription Input Form
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(CyberCardBg)
                            .border(1.dp, CyberGunmetalBorder, RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "لینک اشتراک خود را وارد کنید",
                            color = CyberPureWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "فقط اشتراک‌های مجاز اختصاصی پارس پینگ قابل پذیرش هستند",
                            color = CyberSilverMuted,
                            fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // URL TextField
                        OutlinedTextField(
                            value = urlInput,
                            onValueChange = onUrlChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("subscription_url_input"),
                            placeholder = {
                                Text(
                                    text = "https://zerofee.ok-exor.ir/sub/...",
                                    color = CyberSilverMuted,
                                    fontSize = 13.sp
                                )
                            },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = CyberBlackBg,
                                unfocusedContainerColor = CyberBlackBg,
                                focusedBorderColor = CyberCrimsonGlow,
                                unfocusedBorderColor = CyberGunmetalBorder,
                                focusedTextColor = CyberPureWhite,
                                unfocusedTextColor = CyberPureWhite
                            ),
                            trailingIcon = {
                                if (urlInput.isNotEmpty()) {
                                    IconButton(onClick = { onUrlChange("") }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "پاک کردن",
                                            tint = CyberSilverMuted
                                        )
                                    }
                                }
                            },
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Action Buttons: Paste Clipboard & Submit
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Paste from Clipboard Button
                            Button(
                                onClick = onPasteClipboard,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("paste_clipboard_button"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CyberCharcoalSurface,
                                    contentColor = CyberPureWhite
                                ),
                                shape = RoundedCornerShape(10.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CyberGunmetalBorder)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Assignment,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = CyberCrimsonGlow
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "از کلیپبورد", fontSize = 12.sp)
                            }

                            // Submit & Check Button
                            Button(
                                onClick = onSubmit,
                                enabled = !isLoading,
                                modifier = Modifier
                                    .weight(1.3f)
                                    .height(48.dp)
                                    .testTag("submit_subscription_button"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CyberCrimsonMain,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Link,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "بررسی و افزودن",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        // Status / Notification Box
                        if (notice != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            when (notice) {
                                is SubscriptionUiNotice.Success -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(CyberConnectedGreen.copy(alpha = 0.15f))
                                            .border(1.dp, CyberConnectedGreen.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                            .padding(12.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = CyberConnectedGreen,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = notice.message,
                                                color = CyberConnectedGreen,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                                is SubscriptionUiNotice.Error -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(CyberErrorRed.copy(alpha = 0.15f))
                                            .border(1.dp, CyberErrorRed.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                            .padding(12.dp)
                                            .testTag("subscription_error_notice")
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Error,
                                                contentDescription = null,
                                                tint = CyberErrorRed,
                                                modifier = Modifier.size(22.dp)
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(
                                                text = notice.message,
                                                color = CyberErrorRed,
                                                fontSize = 13.sp,
                                                fontWeight = if (notice.isRestricted) FontWeight.Bold else FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Section 2: Buy Config Button (Directly below subscription input)
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(CyberCardBg)
                            .border(1.2.dp, CyberTelegram.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "کانفیگ نداری؟ از ربات پارس پینگ خرید کن",
                            color = CyberSilverText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PARS_PING_BOT_URL))
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("buy_config_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyberTelegram,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "خرید کانفیگ",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "خرید کانفیگ از پارس پینگ",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Section 3: Imported Configs List Header
                item {
                    Text(
                        text = "کانفیگ‌های فعال (${configs.size})",
                        color = CyberPureWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Section 4: Imported Config Items
                if (configs.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "هنوز کانفیگی اضافه نشده است",
                                color = CyberSilverMuted,
                                fontSize = 13.sp
                            )
                        }
                    }
                } else {
                    items(configs, key = { it.id }) { config ->
                        ConfigListItem(
                            config = config,
                            onSelect = { onSelectConfig(config.id) },
                            onDelete = { onDeleteConfig(config) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConfigListItem(
    config: VpnConfigEntity,
    onSelect: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (config.isSelected) Color(0xFF1E1015) else CyberCardBg)
            .border(
                width = if (config.isSelected) 1.5.dp else 1.dp,
                color = if (config.isSelected) CyberCrimsonGlow else CyberGunmetalBorder,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onSelect)
            .padding(horizontal = 14.dp, vertical = 12.dp)
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
                RadioButton(
                    selected = config.isSelected,
                    onClick = onSelect,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = CyberCrimsonGlow,
                        unselectedColor = CyberSilverMuted
                    )
                )

                Spacer(modifier = Modifier.width(6.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = config.name,
                        color = CyberPureWhite,
                        fontSize = 14.sp,
                        fontWeight = if (config.isSelected) FontWeight.Bold else FontWeight.Medium,
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

            IconButton(onClick = onDelete) {
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
