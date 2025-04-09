package dev.davidgaspar.getthis.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d(TAG, "From: ${message.from}")
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${message.data}")
        }

        message.notification?.let { notification ->
            if (notification.title != null) {
                Log.d(TAG, "Message Notification Title: ${notification.title}")
            } else {
                Log.w(TAG, "Message Notification Title is null")
            }

            if (notification.body != null) {
                Log.d(TAG, "Message Notification Body: ${notification.body}")
            } else {
                Log.w(TAG, "Message Notification Body is null")
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "Refreshed token: $token")
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}