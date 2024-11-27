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
            }
        }

        // Create the NotificationChannel only once (avoid creating every time activity is created)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)
            val channelId = "default"
            if (notificationManager.getNotificationChannel(channelId) == null) {
                val channel = NotificationChannel(
                    channelId,
                    "Default",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.description = "Channel for default notifications"
                notificationManager.createNotificationChannel(channel)
            }
        }

        // Build the WorkRequest for NotificationWorker
        val notificationWorkRequest: WorkRequest =
            OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build()

        // Log the work request details for debugging
        Log.i("WorkManager", "WorkRequest created: $notificationWorkRequest")

        // Enqueue the WorkRequest with WorkManager
        WorkManager.getInstance(this).enqueue(notificationWorkRequest)

        // Handle window insets (edge-to-edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
