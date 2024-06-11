package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyNavigation(sharedPreferences: SharedPreferences){
    val navController = rememberNavController()

    var firstDestination = OnBoarding.route;
    if( sharedPreferences.contains("firstName") &&
        sharedPreferences.contains("lastName") &&
        sharedPreferences.contains("email") ){
        firstDestination = Home.route
    }

    NavHost(navController = navController, startDestination = firstDestination ){
        composable( Home.route ){
            Home( navController)
        }
        composable( Profile.route ){
            Profile( navController )
        }
        composable( OnBoarding.route ){
            Onboarding(navController)
        }
    }
}