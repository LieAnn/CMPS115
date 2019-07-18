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
        setupInfo()
    }

    private fun setupInfo(){
        if (intent.hasExtra("keyIdentifier")) {
            Glide.with(this).load(intent.getStringExtra("keyIdentifier")).into(imageView3)
        } else {
            Toast.makeText(this, "There was an issue", Toast.LENGTH_SHORT).show()
        }
    }




}