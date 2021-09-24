package com.example.noonsokmal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.splash_black_pink.*

class Black_pink : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_black_pink)

        Glide.with(this).load(R.raw.hearing_ver).override(100, 100).into(pink_gif_image)

        val intent = Intent(this, HearingActivity::class.java)
        startActivity(intent)
        finish()
    }
}