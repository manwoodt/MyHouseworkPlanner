package com.example.myhouseworkplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myhouseworkplanner.presentation.navigation.AppNavigation
import com.example.myhouseworkplanner.ui.theme.MyHouseworkPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyHouseworkPlannerTheme {
                AppNavigation()
            }
        }
    }
}
