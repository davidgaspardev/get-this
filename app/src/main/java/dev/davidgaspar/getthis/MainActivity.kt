package dev.davidgaspar.getthis

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import dev.davidgaspar.getthis.services.utils.DOWNLOAD_DETAILS_CHANNEL_ID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeMessageToTopic()
        setupNotification()
    }

    private fun subscribeMessageToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->
                var msg = "Subscribed to $TOPIC"
                if (!task.isSuccessful) {
                    msg = "Failed to subscribe to $TOPIC"
                }
                Log.d(TAG, msg)
            }
    }

    private fun setupNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                DOWNLOAD_DETAILS_CHANNEL_ID,                    // ID
                "Download Details",                  // Name shown to user
                NotificationManager.IMPORTANCE_HIGH // Importance
            ).apply {
                description = "Notifications for viewing details of downloaded files"
            }

            val notificationManager: NotificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            // Create notification channel
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val TOPIC = "content-downloaded"
    }
}