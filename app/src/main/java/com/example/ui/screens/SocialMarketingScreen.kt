package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AmazonNavy
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandOrange
import com.example.ui.theme.WhatsAppGreen

@Composable
fun SocialMarketingScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Avatar / App Icon
                Surface(
                    color = AmazonNavy,
                    shape = CircleShape,
                    modifier = Modifier.size(72.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_app_icon),
                        contentDescription = "Three Brothers Logo",
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Three Brothers Garments",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AmazonNavy
                )

                Text(
                    text = "Official E-Commerce & Social Marketing Hub",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    color = Color(0xFFFFF3E0),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Owner: Mis Farhana Nadeem",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF222222)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "WhatsApp Business: +92 347 2065158",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = BrandOrange
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Connect & Market with us",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Facebook Button
                SocialPlatformCard(
                    title = "Facebook Page",
                    description = "Follow us for new lawn suits and gents kurta launches",
                    buttonText = "Visit Facebook",
                    buttonColor = Color(0xFF1877F2),
                    url = "https://www.facebook.com/share/1Ay3e8LXLm/"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Instagram Button
                SocialPlatformCard(
                    title = "Instagram Profile",
                    description = "Check out photos and fashion showcases",
                    buttonText = "Visit Instagram",
                    buttonColor = Color(0xFFE4405F),
                    url = "https://www.instagram.com/s.shaheryar.ahmed?igsh=MWxudDFuNHRhNWJpdQ=="
                )

                Spacer(modifier = Modifier.height(10.dp))

                // TikTok Button
                SocialPlatformCard(
                    title = "TikTok Channel",
                    description = "Watch fabric previews and trendy garment reels",
                    buttonText = "Visit TikTok",
                    buttonColor = Color(0xFF000000),
                    url = "https://tiktok.com/@s.sbrothergarments"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Direct WhatsApp Button
                SocialPlatformCard(
                    title = "WhatsApp Direct Chat",
                    description = "Direct chat with owner Mis Farhana Nadeem",
                    buttonText = "Open WhatsApp",
                    buttonColor = WhatsAppGreen,
                    url = "https://wa.me/923472065158?text=Hello%20Three%20Brothers%20Garments!"
                )
            }
        }
    }
}

@Composable
private fun SocialPlatformCard(
    title: String,
    description: String,
    buttonText: String,
    buttonColor: Color,
    url: String
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF222222)
                )
                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        // Ignore
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.testTag("social_link_${title.lowercase().replace(" ", "_")}")
            ) {
                Text(
                    text = buttonText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
