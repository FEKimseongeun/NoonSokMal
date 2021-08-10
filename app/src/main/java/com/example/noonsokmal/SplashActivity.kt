package com.example.noonsokmal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//앱 처음 실행시 잠깐 뜨는 로고화면 기능
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}