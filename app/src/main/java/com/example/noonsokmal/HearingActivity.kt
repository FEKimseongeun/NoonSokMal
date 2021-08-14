package com.example.noonsokmal

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.hearing_main.*

//비청각장애인 메인화면 기능
class HearingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.hearing_main)

        val REQUEST_VIDEO_CAPTURE = 1

        //fun dispatchTakeVideoIntent() {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
                takeVideoIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
                }
            }
        //}

        play_Start_button.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.hearing_result_bottomsheet, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(dialogView)
            dialog.show()
        }


    }

}