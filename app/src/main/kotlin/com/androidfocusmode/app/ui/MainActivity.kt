package com.androidfocusmode.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androidfocusmode.app.ui.navigation.Destination
import com.androidfocusmode.app.ui.screen.HomeScreen
import com.androidfocusmode.app.ui.screen.DetailScreen
import com.androidfocusmode.app.ui.theme.AndroidFocusModeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidFocusModeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Destination.HOME,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(Destination.HOME) {
                            HomeScreen(
                                onEditMode = { modeId ->
                                    navController.navigate(Destination.DETAIL.replace("{modeId}", modeId.toString()))
                                },
                                onCreateMode = {
                                    navController.navigate(Destination.DETAIL.replace("{modeId}", "-1"))
                                }
                            )
                        }

                        composable(Destination.DETAIL) { backStackEntry ->
                            val modeId = backStackEntry.arguments?.getString("modeId")?.toLongOrNull() ?: -1L
                            DetailScreen(
                                modeId = modeId,
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
