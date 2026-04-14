package com.androidfocusmode.app.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androidfocusmode.app.ui.navigation.Destination
import com.androidfocusmode.app.ui.screen.DetailScreen
import com.androidfocusmode.app.ui.screen.HomeScreen
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
                    val showPermissionDialog = remember { mutableStateOf(true) }

                    val permissionLauncher = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestMultiplePermissions()
                    ) { _ ->
                        showPermissionDialog.value = false
                    }

                    LaunchedEffect(Unit) {
                        val permissionsToRequest = mutableListOf<String>().apply {
                            add(Manifest.permission.ACCESS_FINE_LOCATION)
                            add(Manifest.permission.ACCESS_COARSE_LOCATION)

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                add(Manifest.permission.POST_NOTIFICATIONS)
                            }

                            add(Manifest.permission.READ_PHONE_STATE)
                            add(Manifest.permission.READ_CONTACTS)
                        }

                        if (permissionsToRequest.isNotEmpty()) {
                            permissionLauncher.launch(permissionsToRequest.toTypedArray())
                        }
                    }

                    if (showPermissionDialog.value) {
                        AlertDialog(
                            onDismissRequest = { showPermissionDialog.value = false },
                            title = { Text("Permissions Required") },
                            text = {
                                Text(
                                    "Android Focus Mode needs the following permissions:\n\n" +
                                        "📍 Location - For geofence-based focus modes\n" +
                                        "🔔 Notifications - To manage notification policy\n" +
                                        "📞 Phone State - For call detection\n" +
                                        "👥 Contacts - To identify favorite contacts\n\n" +
                                        "Please grant these permissions to use all features."
                                )
                            },
                            confirmButton = {
                                Button(onClick = { showPermissionDialog.value = false }) {
                                    Text("OK")
                                }
                            }
                        )
                    }

                    NavHost(
                        navController = navController,
                        startDestination = Destination.HOME,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(Destination.HOME) {
                            HomeScreen(
                                onEditMode = { modeId ->
                                    navController.navigate(
                                        Destination.DETAIL.replace("{modeId}", modeId.toString())
                                    )
                                },
                                onCreateMode = {
                                    navController.navigate(
                                        Destination.DETAIL.replace("{modeId}", "-1")
                                    )
                                }
                            )
                        }

                        composable(Destination.DETAIL) { backStackEntry ->
                            val modeId = backStackEntry.arguments
                                ?.getString("modeId")
                                ?.toLongOrNull() ?: -1L

                            DetailScreen(
                                modeId = modeId,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
