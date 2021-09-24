package com.example.noonsokmal

import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.nohearing_result.*
import java.util.*


//청각장애인 결과출력 기능
class NoHearingResultActivity : AppCompatActivity() {
    var nohearing_button: ImageView? = null
    var hearing_button: ImageView? = null

    private var videoView: VideoView? = null // 비디오 실행 도와주는 뷰

    private var mediaController: MediaController? = null // 재생, 정지 등 미디어 제어 버튼 담당


    // url 사용할 경우
    //    private String videoURL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    private val array = ArrayList<String>()
    private var idx = 0
    var tt: String = (NoHearingActivity.context_main as NoHearingActivity).totalSpeak
    private val str = tt
    private var stArr = array.toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nohearing_result)


        val drawerLayout = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        val drawerView = findViewById<View>(R.id.navigation_view)

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




        text_m.text = str

        stArr = str.split(" ").toTypedArray()

        for (i in stArr.indices) {
            val s = stArr[i].substring(stArr[i].length - 1)
            when (s) {
                "을", "를", "이", "에", "게", "은", "는" -> stArr[i] =
                    stArr[i].substring(0, stArr[i].length - 1)
                else -> {
                }
            }
        }

        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.v01)
        for (i in stArr.indices) {
            if (stArr[i] == "여기") {
                array.add("android.resource://" + packageName + "/" + R.raw.v03)
            } else if (stArr[i] == "오른쪽") {
                array.add("android.resource://" + packageName + "/" + R.raw.v05)
            } else if (stArr[i] == "왼쪽") {
                array.add("android.resource://" + packageName + "/" + R.raw.v07)
            } else if (stArr[i] == "저기") {
                array.add("android.resource://" + packageName + "/" + R.raw.v09)
            } else if (stArr[i] == "항상") {
                array.add("android.resource://" + packageName + "/" + R.raw.always)
            } else if (stArr[i] == "위협") {
                array.add("android.resource://" + packageName + "/" + R.raw.danger)
            }
            videoView = findViewById(R.id.videoView) // 아이디 연결
            mediaController = MediaController(this)
            mediaController!!.setAnchorView(videoView)
            //Uri uri = Uri.parse(videoURL);
            videoView!!.setMediaController(mediaController) // 미디어 제어 버튼부 세팅
            videoView!!.setVideoURI(videoUri) // 비디오 뷰 주소 설정
            videoView!!.setOnPreparedListener(OnPreparedListener
            // 준비되면 재생
            { videoView!!.start() })
            //videoView.start(); // 비디오 실행
            videoView!!.setOnErrorListener(MediaPlayer.OnErrorListener { mp, what, extra ->

                // 오류 처리
                Log.d("video", "setOnErrorListener")
                true
            })



            // 비디오 종료시 실행
            val mComplete = OnCompletionListener {
                if (array.size > idx) {
                    val video1 = Uri.parse(array[idx])
                    idx++
                    videoView!!.setVideoURI(video1)
                    videoView!!.start()
                }
            }
            videoView!!.setOnCompletionListener(mComplete)
        }
    }
}
