package com.example.biometricattendanceapplication

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.biometricattendanceapplication.data.User
import com.example.biometricattendanceapplication.ui.*
import com.example.biometricattendanceapplication.ui.auth.*
import com.example.biometricattendanceapplication.ui.theme.BiometricAttendanceApplicationTheme
import com.example.biometricattendanceapplication.utils.BiometricHelper
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: AttendanceViewModel by viewModels()
    private lateinit var biometricHelper: BiometricHelper

    // Office coordinates (Assumption)
    private val officeLat = 37.422 // Example (Googleplex)
    private val officeLng = -122.084
    private val proximityRadius = 500 // meters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biometricHelper = BiometricHelper(this)
        enableEdgeToEdge()
        setContent {
            BiometricAttendanceApplicationTheme {
                val navController = rememberNavController()
                val currentUser by viewModel.currentUser.collectAsState()
                val attendanceList by viewModel.attendanceList.collectAsState()

                var tempName by remember { mutableStateOf("") }
                var tempEmail by remember { mutableStateOf("") }
                var loginError by remember { mutableStateOf<String?>(null) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(
                                onLogin = { email, password ->
                                    lifecycleScope.launch {
                                        val user = viewModel.login(email)
                                        if (user != null && user.password == password) {
                                            navController.navigate("home") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                        } else {
                                            loginError = "Invalid email or password"
                                        }
                                    }
                                },
                                onNavigateToSignup = { navController.navigate("signup") },
                                errorMessage = loginError
                            )
                        }
                        composable("signup") {
                            SignupScreen(onNext = { name, email ->
                                tempName = name
                                tempEmail = email
                                navController.navigate("set_password")
                            })
                        }
                        composable("set_password") {
                            SetPasswordScreen(onSignupComplete = { password ->
                                lifecycleScope.launch {
                                    val newUser = User(tempEmail, tempName, password)
                                    viewModel.signup(newUser)
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            })
                        }
                        composable("home") {
                            HomeScreen(
                                userName = currentUser?.name ?: "",
                                onCheckIn = { handleAttendance("CHECK_IN") },
                                onCheckOut = { handleAttendance("CHECK_OUT") },
                                onViewAttendance = { navController.navigate("attendance_list") },
                                onLogout = { 
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("attendance_list") {
                            AttendanceListScreen(attendanceList = attendanceList)
                        }
                    }
                }
            }
        }
    }

    private fun handleAttendance(type: String) {
        val user = viewModel.currentUser.value ?: return

        lifecycleScope.launch {
            if (viewModel.hasAlreadyMarkedToday(type)) {
                Toast.makeText(this@MainActivity, "Already $type for today!", Toast.LENGTH_SHORT).show()
                return@launch
            }

            if (!user.isBiometricRegistered) {
                // Case 1: Register Biometric
                biometricHelper.showBiometricPrompt(
                    this@MainActivity,
                    "Register Biometric",
                    "Please authenticate to register your biometric",
                    onSuccess = {
                        lifecycleScope.launch {
                            viewModel.registerBiometric()
                            Toast.makeText(this@MainActivity, "Biometric registered successfully!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onError = { _, err -> Toast.makeText(this@MainActivity, "Error: $err", Toast.LENGTH_SHORT).show() },
                    onFailed = { Toast.makeText(this@MainActivity, "Authentication failed", Toast.LENGTH_SHORT).show() }
                )
            } else {
                // Case 2: Already registered, verify and check location
                biometricHelper.showBiometricPrompt(
                    this@MainActivity,
                    "Verify Biometric",
                    "Please authenticate to $type",
                    onSuccess = {
                        checkLocationAndMarkAttendance(type)
                    },
                    onError = { _, err -> Toast.makeText(this@MainActivity, "Error: $err", Toast.LENGTH_SHORT).show() },
                    onFailed = { Toast.makeText(this@MainActivity, "Authentication failed", Toast.LENGTH_SHORT).show() }
                )
            }
        }
    }

    private fun checkLocationAndMarkAttendance(type: String) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val results = FloatArray(1)
                Location.distanceBetween(location.latitude, location.longitude, officeLat, officeLng, results)
                val distanceInMeters = results[0]

                if (distanceInMeters <= proximityRadius) {
                    lifecycleScope.launch {
                        viewModel.markAttendance(type)
                        Toast.makeText(this@MainActivity, "$type successful!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "You are not at office premises!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "Could not get location", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
