package com.example.notification_openactivity_when_click

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ListProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_product)

        supportActionBar?.setTitle("ListProductActivity")

        var btnGotoDetail : Button = findViewById(R.id.btn_go_to_detail)

        btnGotoDetail.setOnClickListener{
            var intent : Intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}