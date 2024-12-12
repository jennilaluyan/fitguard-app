package com.dicodingg.bangkit.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dicodingg.bangkit.R

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        private val TAG = NotificationWorker::class.java.simpleName
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "FitGuard"
    }

    override fun doWork(): Result {
        // Retrieve the medication name from the input data
        val medicationName = inputData.getString("medication_name") ?: "Obat"
        Log.d(TAG, "Notification scheduled for $medicationName")

        // Show the notification
        showNotification(medicationName)
        return Result.success()
    }

    private fun showNotification(medicationName: String) {
        // Create notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for daily medication reminders"
            }

            val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Create the notification with the personalized message
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)  // Use appropriate icon
            .setContentTitle("Reminder!")
            .setContentText("Jangan lupa minum obat $medicationName ya!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)  // Sound, vibration, etc.
            .setAutoCancel(true)  // Dismiss the notification after clicking
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
