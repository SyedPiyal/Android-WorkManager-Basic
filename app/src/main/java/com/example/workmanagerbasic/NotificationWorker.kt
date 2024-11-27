package com.example.workmanagerbasic

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.Manifest
import android.content.pm.PackageManager

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    // Define a notification channel ID
    private val CHANNEL_ID = "default_channel"

    override fun doWork(): Result {
        // For Android 13 or higher, check if the permission is granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check if we have POST_NOTIFICATIONS permission
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                // If the permission is not granted, notify the user and return failure
                return Result.failure()
            }
        }

        // Create the notification channel (for Android 8.0 and above)
        createNotificationChannel()

        // Build the notification
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(androidx.loader.R.drawable.notification_bg)  // Make sure to have a proper icon in res/drawable
            .setContentTitle("Task completed")
            .setContentText("The background task has completed successfully.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Show the notification
        NotificationManagerCompat.from(applicationContext).notify(1, notification)

        return Result.success()
    }

    private fun createNotificationChannel() {
        // Only create the channel on Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Default Channel", // Channel name
                NotificationManager.IMPORTANCE_DEFAULT // Importance level
            ).apply {
                description = "Channel for background task notifications"
            }

            // Register the channel with the system
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
