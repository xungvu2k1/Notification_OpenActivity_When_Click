package com.example.notification_openactivity_when_click

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build

class MyApplication  : Application() {
    var CHANNEL_ID1 : String = "channel1"

    override fun onCreate() {
        super.onCreate()
        createChannelNotification()
    }

    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //config âm thanh cho notification
            val uri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes: AudioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)//
                .build()

            //config channel 1
            // Create the NotificationChannel.
            val name1 = getString(R.string.channel_name1)
            val descriptionText1 = getString(R.string.channel_description1)
            val importance1 = NotificationManager.IMPORTANCE_HIGH
            val mChannel1 = NotificationChannel(CHANNEL_ID1, name1, importance1)
            mChannel1.description = descriptionText1
            mChannel1.setSound(uri, audioAttributes)

            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel1)
        }
    }
    companion object {
        private var instance: MyApplication? = null

        fun getInstance(): MyApplication {
            if (instance == null){
                instance = MyApplication()
            }
            return instance!!
        }
    }
}