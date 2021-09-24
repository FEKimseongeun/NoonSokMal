package com.example.noonsokmal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.splash_black_yellow.*


class Black_yellow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_black_yellow)


        Glide.with(this).load(R.raw.nohearing_ver).override(50, 50).into(yellow_gif_image)


        val intent = Intent(this, NoHearingActivity::class.java)
        startActivity(intent)
        finish()
    }
}