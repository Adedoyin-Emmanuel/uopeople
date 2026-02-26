package com.example.biometricattendanceapplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.biometricattendanceapplication.data.AppDatabase
import com.example.biometricattendanceapplication.data.Attendance
import com.example.biometricattendanceapplication.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val userDao = db.userDao()
    private val attendanceDao = db.attendanceDao()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _attendanceList = MutableStateFlow<List<Attendance>>(emptyList())
    val attendanceList: StateFlow<List<Attendance>> = _attendanceList.asStateFlow()

    fun setUser(user: User) {
        _currentUser.value = user
        loadAttendance(user.email)
    }

    private fun loadAttendance(email: String) {
        viewModelScope.launch {
            attendanceDao.getAttendanceForUser(email).collect {
                _attendanceList.value = it
            }
        }
    }

    suspend fun signup(user: User) {
        userDao.insertUser(user)
        _currentUser.value = user
    }

    suspend fun login(email: String): User? {
        val user = userDao.getUserByEmail(email)
        if (user != null) {
            _currentUser.value = user
            loadAttendance(email)
        }
        return user
    }

    suspend fun registerBiometric() {
        _currentUser.value?.let { user ->
            userDao.registerBiometric(user.email)
            _currentUser.value = user.copy(isBiometricRegistered = true)
        }
    }

    suspend fun markAttendance(type: String) {
        _currentUser.value?.let { user ->
            val attendance = Attendance(
                userEmail = user.email,
                timestamp = System.currentTimeMillis(),
                type = type
            )
            attendanceDao.insertAttendance(attendance)
        }
    }

    suspend fun hasAlreadyMarkedToday(type: String): Boolean {
        val user = _currentUser.value ?: return false
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startOfDay = calendar.timeInMillis
        
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val endOfDay = calendar.timeInMillis

        val todayAttendance = attendanceDao.getAttendanceToday(user.email, startOfDay, endOfDay)
        return todayAttendance.any { it.type == type }
    }
}
