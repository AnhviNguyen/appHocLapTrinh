package com.example.mobilefinalproject.view.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.mobilefinalproject.config.AuthSharedPreferencesUtil
import com.example.mobilefinalproject.navigation.NavigationItem

@Composable
fun MainScreen(authManager: AuthSharedPreferencesUtil, navController: NavController) {
    val context = LocalContext.current
    val email = authManager.getEmail(context)
    val id = authManager.getID(context)
    LaunchedEffect(Unit) {
        if (email != null && id!=null) {
            navController.navigate("${NavigationItem.Home.route}/$id")
        } else {
            navController.navigate(NavigationItem.Login.route)
        }
    }
}
