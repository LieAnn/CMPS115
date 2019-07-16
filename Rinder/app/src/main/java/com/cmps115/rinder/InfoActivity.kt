package com.cmps115.rinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_info.*
import android.content.Intent

import com.bumptech.glide.Glide



class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        setupButton()
        setupInfo()
    }

    private fun setupInfo(){
        if (intent.hasExtra("keyIdentifier")) {
            Glide.with(this).load(intent.getStringExtra("keyIdentifier")).into(imageView3)

            //spot_name.text = intent.getStringExtra("keyIdentifier")

        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupButton(){
        val goBack = findViewById<View>(R.id.back_button)
        goBack.setOnClickListener {
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }
    }

}