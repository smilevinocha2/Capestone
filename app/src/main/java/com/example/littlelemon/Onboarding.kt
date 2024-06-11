package com.example.littlelemon

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.provider.Settings.Global.getString
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.components.Header
import androidx.compose.material3.AlertDialog as AlertDialog

@Composable
fun Onboarding(navController: NavHostController){
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var showAlert by remember {
        mutableStateOf(false)
    }
    val activity = LocalContext.current as Activity
    val sharedPreferences = activity?.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    Scaffold(
        topBar = {
            Header()
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                title = { Text(text = "Warning") },
                text = { Text(text = "Registration unsuccessful. Please enter all data.") },
                confirmButton = {
                    Button(
                        onClick = {
                            showAlert = false
                        }
                    ) {
                        Text(
                            text = "Accept",
                            color = Color.White
                        )
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Let's get to know you",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(150.dp)
                    .padding(vertical = 20.dp)
                    .background(color = colorResource(R.color.primary1))
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )

            Text(
                text = "Personal information",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            OutlinedTextField(
                value = firstName,
                label = { Text("First Name") },
                onValueChange = { newText ->
                    firstName = newText
                },
                textStyle = TextStyle.Default.copy(fontSize = 14.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.Gray,
                    unfocusedBorderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    unfocusedLeadingIconColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 5.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            )

            OutlinedTextField(
                value = lastName,
                label = { Text("Last Name") },
                onValueChange = { newText ->
                    lastName = newText
                },
                textStyle = TextStyle.Default.copy(fontSize = 14.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.Gray,
                    unfocusedBorderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    unfocusedLeadingIconColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 5.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            )

            OutlinedTextField(
                value = email,
                label = { Text("Email") },
                onValueChange = { newText ->
                    email = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle.Default.copy(fontSize = 14.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.Gray,
                    unfocusedBorderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    unfocusedLeadingIconColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 5.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            )

            OutlinedButton(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                        showAlert = true
                    } else {
                        sharedPreferences?.edit()
                            ?.putString("firstName", firstName)
                            ?.putString("lastName", lastName)
                            ?.putString("email", email)
                            ?.commit();
                        navController.navigate(Home.route)
                    }
                },
                border = BorderStroke(2.dp, colorResource(R.color.secondary1)),
                colors = ButtonDefaults
                    .buttonColors(containerColor = colorResource(R.color.primary2)),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(
                    top = 25.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 40.dp
                )
            ) {
                Text(
                    text = "Register",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview(){
    Onboarding(rememberNavController())
}