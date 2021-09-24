package com.example.noonsokmal;


//view plaincopy to clipboardprint?
//        package kr.pe.miksnug;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


//미리보기와 녹화파일에 대한 작업이 이루어지는 클래스

public class Preview extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * Instance Of MediaRecorder
     */
    MediaRecorder m_mediaRecorder;
    /**
     * Instance Of SurfaceHolder
     */
    SurfaceHolder m_sufaceHolder;
    /**
     * Instance Of Recoding file Path
     */
    String m_recPath = null;
    /**
     * Instance Of Recoding filename
     */
    String m_recFile = null;
    /**
     * Check Flag Of Recording Status
     */
    boolean m_bNowRecording = false;



    public Preview(Context context) {
        super(context);
        m_sufaceHolder = getHolder();
// m_sufaceHolder.setFixedSize(320, 240);
        m_sufaceHolder.addCallback(this);
        m_sufaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        m_mediaRecorder = new MediaRecorder();
        m_recPath = "/sdcard/";
        m_recFile = "test.mp4";

// Source 설정
        m_mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        m_mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
// 출력 형식 설정
        m_mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
// 인코더 설정
        m_mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        m_mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
// 비디오 옵션 설정
        m_mediaRecorder.setVideoSize(320, 240);
        m_mediaRecorder.setVideoFrameRate(15);
// m_mediaRecorder.setMaxDuration(60000);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        m_recFile = m_recPath + "/" + m_recFile;
        m_mediaRecorder.setOutputFile(m_recFile);
        // 미리보기 설정
        m_mediaRecorder.setPreviewDisplay(m_sufaceHolder.getSurface());
        if (m_mediaRecorder != null) {
            try {
                m_mediaRecorder.prepare();
            } catch (IllegalStateException e) {
                Log.d("yeongeon", "==[A]====>" + e.toString());
            } catch (IOException e) {
                Log.d("yeongeon", "==[B]====>" + e.toString());
            }
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }


    @Override
    public void surfaceChanged(
            SurfaceHolder holder, int format, int width,
            int height
    ) {
    }

    //미디어 레코더를 반환
    public MediaRecorder getRecorder() {
        return m_mediaRecorder;
    }


    public void setRecorder(MediaRecorder mediaRecorder) {
        m_mediaRecorder = mediaRecorder;
    }

    /**
     * 녹화를 시작합니다.
     */
    public void start() {
        m_mediaRecorder.start();
        m_bNowRecording = true;
    }

    /**
     * 녹화를 종료합니다.
     */
    public void stop() {
        try {
            m_mediaRecorder.stop();
        } catch (IllegalStateException e) {
            Log.d("yeongeon", "==[G]====>" + e.toString());
        }
// m_mediaRecorder.release();
// m_mediaRecorder = null;
        m_bNowRecording = false;
    }
}