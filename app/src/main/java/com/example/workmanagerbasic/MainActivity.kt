package com.example.workmanagerbasic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit
import android.Manifest
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Ensure this is implemented somewhere
        setContentView(R.layout.activity_main)

        // Check if the permission to post notifications is granted (for Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Request permission if not granted
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1
                )
            } else {
                // Permission granted, schedule the work
                scheduleWork()
            }
        } else {
            // For Android versions below 13, no permission request needed
            scheduleWork()
        }
    }

    // This method schedules the work once permission is granted
    private fun scheduleWork() {
        // Build the WorkRequest for NotificationWorker (with a 5-minute delay)
        val notificationWorkRequest: WorkRequest =
            OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .setInitialDelay(1, TimeUnit.MINUTES)  // Delay time in minutes
                .build()

        // Log the work request details for debugging
        Log.i("WorkManager", "WorkRequest created: $notificationWorkRequest")

        // Enqueue the work request
        WorkManager.getInstance(this).enqueue(notificationWorkRequest)
    }

    // Handle the permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, schedule the work
                scheduleWork()
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission denied. Task won't be scheduled.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


