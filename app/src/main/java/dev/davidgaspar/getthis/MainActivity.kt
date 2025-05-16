package dev.davidgaspar.getthis

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import dev.davidgaspar.getthis.services.utils.DOWNLOAD_DETAILS_CHANNEL_ID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeMessageToTopic()
        setupNotification()
        checkAndRequestNotificationPermission()
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

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val areEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled()
            if (!areEnabled) {
                AlertDialog.Builder(this)
                    .setTitle("Enable Notifications")
                    .setMessage("Please enable notifications to receive important updates.")
                    .setPositiveButton("Go to Settings") { _, _ ->
                        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                            putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                        }
                        startActivity(intent)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val TOPIC = "content-downloaded"
    }
}