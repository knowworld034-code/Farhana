package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmazonNavy
import com.example.ui.theme.BrandGold
import com.example.ui.theme.DarkSocialBar
import com.example.ui.theme.WhatsAppGreen

@Composable
fun HeaderAndOwnerSection(
    userEmail: String,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxWidth()) {
        // Main Header
        Surface(
            color = AmazonNavy,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = "Shop Logo",
                        tint = BrandGold,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Column {
                        Text(
                            text = "Three Brothers E-Commerce",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        if (userEmail.isNotBlank()) {
                            Text(
                                text = "Logged in as: $userEmail",
                                fontSize = 11.sp,
                                color = BrandGold
                            )
                        }
                    }
                }

                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD9534F),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = ButtonDefaults.ContentPadding,
                    modifier = Modifier.testTag("logout_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text("Logout", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Owner Info Banner
        Surface(
            color = BrandGold,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Owner: Mis Farhana Nadeem | WhatsApp: +92 347 2065158",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111111),
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    color = WhatsAppGreen,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .clickable {
                            val whatsappIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://wa.me/923472065158?text=Hello%20Three%20Brothers%20E-Commerce!")
                            )
                            context.startActivity(whatsappIntent)
                        }
                        .testTag("owner_whatsapp_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Chat",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = "WhatsApp",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Social Links Marketing Bar
        Surface(
            color = DarkSocialBar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Market with us: ",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )

                SocialTextLink(
                    text = "Facebook",
                    url = "https://www.facebook.com/share/1Ay3e8LXLm/"
                )

                Text(" | ", color = Color.Gray, fontSize = 12.sp)

                SocialTextLink(
                    text = "Instagram",
                    url = "https://www.instagram.com/s.shaheryar.ahmed?igsh=MWxudDFuNHRhNWJpdQ=="
                )

                Text(" | ", color = Color.Gray, fontSize = 12.sp)

                SocialTextLink(
                    text = "TikTok",
                    url = "https://tiktok.com/@s.sbrothergarments"
                )
            }
        }
    }
}

@Composable
private fun SocialTextLink(text: String, url: String) {
    val context = LocalContext.current
    Text(
        text = text,
        color = BrandGold,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clickable {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                } catch (e: Exception) {
                    // Fallback
                }
            }
            .padding(horizontal = 4.dp)
    )
}
