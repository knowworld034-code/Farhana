package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AmazonNavy
import com.example.ui.theme.BrandOrange
import com.example.ui.theme.LightBackground

@Composable
fun LoginScreen(
    onLoginSubmitted: (email: String, pass: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isSignUpMode by remember { mutableStateOf(false) }

    // Saved credentials in screen state (simulating localStorage / SharedPreferences)
    var savedEmail by remember { mutableStateOf("farhana@threebrothers.com") }
    var savedPassword by remember { mutableStateOf("123456") }

    var emailInput by remember { mutableStateOf(savedEmail) }
    var passwordInput by remember { mutableStateOf(savedPassword) }
    var errorMessage by remember { mutableStateOf("") }
    var infoMessage by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LightBackground),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(16.dp)
                .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Brand Logo Icon
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(AmazonNavy),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_app_icon),
                        contentDescription = "Logo",
                        modifier = Modifier.size(54.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isSignUpMode) "Three Brothers - Owner Sign Up" else "Three Brothers - Owner Login",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (isSignUpMode)
                        "Create your secure account first to manage your store."
                    else
                        "Enter your registered email and password to log in.",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = emailInput,
                    onValueChange = { emailInput = it; errorMessage = ""; infoMessage = "" },
                    label = { Text(if (isSignUpMode) "Your Email:" else "Email:") },
                    placeholder = { Text("Enter your email") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "User", tint = AmazonNavy)
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(if (isSignUpMode) "su_email_input" else "login_email_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandOrange,
                        focusedLabelColor = BrandOrange
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = { passwordInput = it; errorMessage = ""; infoMessage = "" },
                    label = { Text(if (isSignUpMode) "Create Password:" else "Password:") },
                    placeholder = { Text(if (isSignUpMode) "Create a password" else "Enter your password") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "Password", tint = AmazonNavy)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(if (isSignUpMode) "su_password_input" else "login_password_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandOrange,
                        focusedLabelColor = BrandOrange
                    )
                )

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                if (infoMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = infoMessage,
                        color = Color(0xFF25D366),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (isSignUpMode) {
                    Button(
                        onClick = {
                            if (emailInput.isBlank() || passwordInput.isBlank()) {
                                errorMessage = "Please enter both email and password for sign up!"
                            } else {
                                savedEmail = emailInput.trim()
                                savedPassword = passwordInput.trim()
                                infoMessage = "Sign Up Successful! Account created. Please log in."
                                Toast.makeText(context, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
                                isSignUpMode = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandOrange,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("signup_submit_button")
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Already have an account? Login here",
                        fontSize = 14.sp,
                        color = Color(0xFF0066C0),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .clickable {
                                isSignUpMode = false
                                errorMessage = ""
                                infoMessage = ""
                            }
                            .testTag("toggle_login_link")
                    )
                } else {
                    Button(
                        onClick = {
                            val cleanEmail = emailInput.trim()
                            val cleanPass = passwordInput.trim()
                            if (cleanEmail.isBlank() || cleanPass.isBlank()) {
                                errorMessage = "Please enter both email and password!"
                            } else if (savedEmail.isNotEmpty() && (cleanEmail != savedEmail || cleanPass != savedPassword)) {
                                errorMessage = "Incorrect Email or Password! Please try again."
                            } else {
                                onLoginSubmitted(cleanEmail, cleanPass)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandOrange,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("login_submit_button")
                    ) {
                        Text(
                            text = "Login to Store",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Don't have an account? Sign Up here",
                        fontSize = 14.sp,
                        color = Color(0xFF0066C0),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .clickable {
                                isSignUpMode = true
                                errorMessage = ""
                                infoMessage = ""
                            }
                            .testTag("toggle_signup_link")
                    )
                }
            }
        }
    }
}
