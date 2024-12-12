package com.dicodingg.bangkit.data.notification

import android.content.Context
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.Calendar
import java.util.concurrent.TimeUnit

object NotificationUtils {

    // Schedules a medication notification based on the time in the medication data
    fun scheduleMedicationNotification(context: Context, hour: Int, minute: Int, medicationName: String) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        // Set notification to the specified time
        val timeDiff = calendar.timeInMillis - System.currentTimeMillis()

        // If the scheduled time has already passed for today, schedule for tomorrow
        if (timeDiff < 0) {
            calendar.add(Calendar.DATE, 1)
        }

        // Set the constraints for the notification work (network connected, etc.)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
            .build()

        // Create the one-time work request for the notification
        val notificationRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)  // Schedule the notification
            .setConstraints(constraints)  // Apply constraints
            .setInputData(workDataOf("medication_name" to medicationName))  // Pass medication name
            .build()

        // Enqueue the notification work with WorkManager
        WorkManager.getInstance(context).enqueue(notificationRequest)
    }

    // Cancels any existing scheduled notifications (useful when deleting medications)
    fun cancelNotifications(context: Context) {
        WorkManager.getInstance(context).cancelAllWorkByTag("medicationReminder")
    }
}
