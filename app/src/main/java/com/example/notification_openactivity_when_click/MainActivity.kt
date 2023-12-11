package com.example.notification_openactivity_when_click

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    var NOTIFICATION_ID1: Int = 1
    @RequiresApi(Build.VERSION_CODES.TIRAMISU) //chỉ định mã nguồn chỉ nên được sử dụng trên các thiết bị chạy phiên bản Android có mã API là TIRAMISU hoặc cao hơn.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        actionBar?.title = "MainActivity"
//        supportActionBar?.show()

        var btnSendNotification : Button = findViewById(R.id.btn_send_notification)
        var btnGoToListProduct : Button = findViewById(R.id.btn_go_to_list_product)

        btnSendNotification.setOnClickListener{
            sendPushNotification()
        }

        btnGoToListProduct.setOnClickListener{
            var intent : Intent = Intent(this, ListProductActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun sendPushNotification() {
        var uri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // chưa thấy hiện được actionBar()@@@@@
        // Set up a regular activity PendingIntent
        // Create an Intent for the activity you want to start.
//        val resultIntent = Intent(this, DetailActivity::class.java)
//        // Create the TaskStackBuilder.
//        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
//            // Add the intent, which inflates the back stack.
//            addNextIntentWithParentStack(resultIntent)
//            // Get the PendingIntent containing the entire back stack.
//            getPendingIntent(NOTIFICATION_ID1,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//        }

        //Set up a special activity PendingIntent : ko hoạt động theo trình tự luồng activity.
        // dùng khi muốn click vào notification mà app mở ra 1 màn hình riêng biệt, ko liên quan gì đến luồng hay backstack của app.
        // phải thêm vào result activity của manifest 2 attribute
//        android:taskAffinity=""
//        android:excludeFromRecents="true"
        val notifyIntent = Intent(this, DetailActivity::class.java).apply {
            //Đặt Activityđể bắt đầu một tác vụ mới, trống
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, NOTIFICATION_ID1, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val notification : Notification = NotificationCompat.Builder(this, MyApplication.getInstance().CHANNEL_ID1)
            .setContentTitle("Hoc android basic")
            .setContentText("Thuc hanh bai activity va notification")
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(uri) // lấy âm thanh mặc định của push notification
            .setAutoCancel(true)// khi click vào notification trên drawer thì tự động xóa notification đó khỏi drawer
//            .setContentIntent(resultPendingIntent)
            .setContentIntent(notifyPendingIntent)
            .build()

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
        notificationManagerCompat.notify(NOTIFICATION_ID1, notification)

    }
}