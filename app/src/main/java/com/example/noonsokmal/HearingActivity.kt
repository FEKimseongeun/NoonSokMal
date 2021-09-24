package com.example.noonsokmal

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.hearing_main.*
import java.io.IOException

//비청각장애인 메인화면 기능
class HearingActivity : AppCompatActivity() {
    var nohearing_button: ImageView? = null
    var hearing_button: ImageView? = null

    var play_button: FloatingActionButton? = null
    var surfaceView: SurfaceView? = null
    var holder: SurfaceHolder? = null
    var recorder: MediaRecorder? = null


    var m_preview: Preview? = null
    var m_previewLayout: LinearLayout? = null
    var m_btnStart: FloatingActionButton? = null
    var m_btnStop: FloatingActionButton? = null


    @SuppressLint("SdCardPath")
    var path: String = "/sdcard/recorded_video.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hearing_main)

        val drawerLayout = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        val drawerView = findViewById(R.id.navigation_view) as View

        val btnOpenDrawer = findViewById<View>(R.id.drawer_open_button) as ImageView
        btnOpenDrawer.setOnClickListener { drawerLayout.openDrawer(drawerView) }


        nohearing_button = findViewById<View>(R.id.nohearing) as ImageView
        hearing_button = findViewById<View>(R.id.hearing) as ImageView

        nohearing_button!!.setOnClickListener {
            val myIntent = Intent(applicationContext, NoHearingActivity::class.java)
            startActivity(myIntent)
            finish()
        }
        hearing_button!!.setOnClickListener {
            val myIntent = Intent(applicationContext, HearingActivity::class.java)
            startActivity(myIntent)
            finish()
        }


        //play_button = findViewById<View>(R.id.play_Start_button) as FloatingActionButton
        //surfaceView = findViewById<View>(R.id.surfaceView) as SurfaceView
        //holder = surfaceView!!.getHolder()


        m_preview = Preview(this)
        m_previewLayout = findViewById<View>(R.id.surfaceView) as LinearLayout
        m_previewLayout!!.addView(m_preview)

        m_btnStart = findViewById<View>(R.id.play_Start_button) as FloatingActionButton
        m_btnStop = findViewById<View>(R.id.play_End_button) as FloatingActionButton
        m_btnStop!!.setClickable(false)

        m_btnStart!!.setOnClickListener {
            // Start Recording
            onRecStart()
            Toast.makeText(this@HearingActivity, "수어를 녹화합니다", Toast.LENGTH_LONG).show()
        }
        m_btnStop!!.setOnClickListener {
            // Stop Recording
            onRecStop()
            Toast.makeText(this@HearingActivity, "녹화를 중지합니다", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        // 녹화 정지
        onRecStop()
    }


    //녹화(음성포함)를 시작
    private fun onRecStart() {
        m_preview!!.start()
        m_btnStart!!.setClickable(false)
        m_btnStop!!.setClickable(true)
    }

    //녹화를 정지 (녹화파일생성)
    private fun onRecStop() {
        m_preview!!.stop()
        m_btnStart!!.setClickable(true)
        m_btnStop!!.setClickable(false)
    }


//    //카메라 연동
//    fun onButton_start(view: View) {
//        if (recorder != null) {
//            recorder!!.stop()
//            recorder!!.release()
//            recorder = null
//        }
//
//        recorder = MediaRecorder()
//        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
//        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//        recorder!!.setOutputFile(path)
//        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//
//        try {
//            recorder!!.prepare()
//        } catch (e: IOException) {
//            Log.e(TAG, "Couldn't prepare and start MediaRecorder")
//        }
//
//        recorder!!.start()
//
//        Toast.makeText(this@HearingActivity, "수어를 녹화합니다", Toast.LENGTH_LONG).show()
//    }
//    fun onButton_end(view: View) {
//        if (recorder != null) {
//            recorder!!.stop()
//            recorder!!.release()
//            recorder = null
//        }
//        Toast.makeText(this@HearingActivity, "녹화를 중지합니다", Toast.LENGTH_LONG).show()
//    }
//
////    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
////        getMenuInflater().inflate(R.menu.)
////        return true
////    }
////
////    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
////        return super.onOptionsItemSelected(item)
////    }
}