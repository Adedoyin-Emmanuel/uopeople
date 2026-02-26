package com.example.biometricattendanceapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val timestamp: Long,
    val type: String // "CHECK_IN" or "CHECK_OUT"
)
