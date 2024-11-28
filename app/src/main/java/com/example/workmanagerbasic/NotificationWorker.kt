package com.example.workmanagerbasic

import android.content.Context
import androidx.work.WorkerParameters
import android.widget.Toast
import androidx.work.CoroutineWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    // Show a toast message after the background task is completed
    override suspend fun doWork(): Result {
        // Use withContext to switch to the main thread for UI operations
        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, "Task completed successfully!", Toast.LENGTH_SHORT).show()
        }

        return Result.success()
    }
}
