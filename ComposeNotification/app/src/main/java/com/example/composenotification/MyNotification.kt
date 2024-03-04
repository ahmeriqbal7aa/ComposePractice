package com.example.composenotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri

object MyNotification {

    private const val CHANNEL_ID = "ComposeFCM Alerts"
    private const val CHANNEL_NAME = "ComposeFCM Channel"
    private const val CHANNEL_DESCRIPTION = "Important Alert from ComposeFCM"

    fun createNotificationChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            description = CHANNEL_DESCRIPTION
            //enableVibration(true)
            //setShowBadge(true)
            //setBypassDnd(true)
        }

        channel.enableVibration(true)
        channel.setShowBadge(true)
        channel.setBypassDnd(true)

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotificationOnStatusBar(context: Context, data: Map<String, String>) {
        /*val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()*/

        // Create Intent it will be launched when user tap on notification from status bar.
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        intent.putExtra("title", data["title"])
        intent.putExtra("body", data["body"])

        // in JSON payload we will send page name also so we can create page Uri deeplink
        //Lets create deeplink Intent
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW, ("deeplink://" + data["page"]).toUri(),
            context, MainActivity::class.java
        )

        // it should be unique when push comes.
        val requestCode = System.currentTimeMillis().toInt()

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            /*PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_MUTABLE)*/
            TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(deepLinkIntent).getPendingIntent(
                    requestCode, PendingIntent.FLAG_MUTABLE
                )
            }
        } else {
            /*PendingIntent.getActivity(
                context, requestCode, intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )*/
            TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(deepLinkIntent).getPendingIntent(
                    requestCode, PendingIntent.FLAG_CANCEL_CURRENT
                )
            }
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(data["title"])
            .setContentText(data["body"])
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText((data["body"])))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        //.setAutoCancel(false)
        //.setCategory(NotificationCompat.CATEGORY_ALARM) // for a persistent sound

        // Set a custom sound for the notification
        // builder.setSound(Uri.parse("android.resource://${context.packageName}/${R.raw.your_custom_sound}"))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // POST_NOTIFICATION permission is required
            Log.d(LOG_TAG, "POST_NOTIFICATION permission is required")
        }

        with(NotificationManagerCompat.from(context)) {
            notify(requestCode, builder.build())
        }
    }
}