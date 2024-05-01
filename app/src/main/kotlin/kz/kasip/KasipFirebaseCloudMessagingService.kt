package kz.kasip

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kz.kasip.data.repository.DataStoreRepository

class KasipFirebaseCloudMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val dataStoreRepository = DataStoreRepository(
            getSharedPreferences(
                "kasip",
                Context.MODE_PRIVATE
            )
        )
        dataStoreRepository.getUserId()?.let {
            Firebase.firestore.document("users/$it").update("fcmToken", token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message);
        sendNotification(message);
    }

    private fun sendNotification(message: RemoteMessage) {
        val intent = Intent(this, RootActivity::class.java)
        intent.putExtra("charId", message.data["chatId"])
        println("chatId: " + message.data["chatId"])
        val pendingIntent = PendingIntent.getActivity(
            this,
            5,
            intent,
            FLAG_ONE_SHOT or FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
        );
        val channelId = "asdasdasd";
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        val notificationBuilder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(kz.kasip.onboarding.R.drawable.icon)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["body"])
                .setExtras(Bundle().also {
                    it.putString("charId", message.data["charId"])
                })
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(5, notificationBuilder.build())
    }
}