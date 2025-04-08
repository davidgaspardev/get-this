package dev.davidgaspar.getthis

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeMessageToTopic()
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

    companion object {
        private const val TAG = "MainActivity"
        private const val TOPIC = "content-downloaded"
    }
}