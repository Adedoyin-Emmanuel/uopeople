package com.example.biometricattendanceapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.biometricattendanceapplication.data.Attendance
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AttendanceListScreen(attendanceList: List<Attendance>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Attendance History", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(attendanceList) { attendance ->
                AttendanceItem(attendance = attendance)
            }
        }
    }
}

@Composable
fun AttendanceItem(attendance: Attendance) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                .format(Date(attendance.timestamp))
            Text(text = attendance.type, modifier = Modifier.weight(1f))
            Text(text = formattedDate)
        }
    }
}
