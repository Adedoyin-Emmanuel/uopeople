package com.example.biometricattendanceapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    userName: String,
    onCheckIn: () -> Unit,
    onCheckOut: () -> Unit,
    onViewAttendance: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome, $userName", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onCheckIn,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check-In")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCheckOut,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check-Out")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onViewAttendance,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Attendance")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        TextButton(onClick = onLogout) {
            Text("Logout")
        }
    }
}
