package com.example.littlelemon

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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

@Composable
fun Profile(navController: NavHostController){
    val activity = LocalContext.current as Activity
    val sharedPreferences = activity?.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    Scaffold(
        topBar = {
            Header()
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Personal Information",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(120.dp)
                    .padding(vertical = 50.dp, horizontal = 20.dp)
            )

            OutlinedTextField(
                value = sharedPreferences?.getString("firstName", "") ?: "",
                label = { Text("First Name") },
                onValueChange = {},
                readOnly = true,
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
                value = sharedPreferences?.getString("lastName", "") ?: "",
                label = { Text("Last Name") },
                onValueChange = {},
                readOnly = true,
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
                value = sharedPreferences?.getString("email", "") ?: "",
                label = { Text("Email") },
                onValueChange = {},
                readOnly = true,
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
                    sharedPreferences?.edit()?.clear()?.commit();
                    navController.navigate(OnBoarding.route)
                },
                border = BorderStroke(2.dp, colorResource(R.color.secondary1)),
                colors = ButtonDefaults
                    .buttonColors(containerColor = colorResource(R.color.primary2)),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(
                    top = 35.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 40.dp
                )
            ) {
                Text(
                    text = "Log out",
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
fun ProfilePreview(){
    Profile(rememberNavController())
}