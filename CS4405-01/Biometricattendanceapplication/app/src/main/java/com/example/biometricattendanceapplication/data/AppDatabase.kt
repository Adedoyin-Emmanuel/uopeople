package com.example.biometricattendanceapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("UPDATE users SET isBiometricRegistered = 1 WHERE email = :email")
    suspend fun registerBiometric(email: String)
}

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insertAttendance(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE userEmail = :email ORDER BY timestamp DESC")
    fun getAttendanceForUser(email: String): Flow<List<Attendance>>
    
    @Query("SELECT * FROM attendance WHERE userEmail = :email AND timestamp >= :startOfDay AND timestamp <= :endOfDay")
    suspend fun getAttendanceToday(email: String, startOfDay: Long, endOfDay: Long): List<Attendance>
}

@Database(entities = [User::class, Attendance::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun attendanceDao(): AttendanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "biometric_attendance_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
