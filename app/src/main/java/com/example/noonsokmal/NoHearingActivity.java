package com.example.noonsokmal;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static android.view.View.inflate;

//청각장애인 메인화면 기능
public class NoHearingActivity extends AppCompatActivity{
    ImageView nohearing_button;
    ImageView hearing_button;
    String text="";

    // Context cThis; // context
    String LogSTT = "[STT]";
    // 음성 인식
    Intent sttIntent;
    SpeechRecognizer mRecognizer;
    // 음성 출력
    TextToSpeech tts;
    Button button;
    // 화면 처리
    ImageView btn_stt;
    TextView text_stt;
    public static Context context_main;
    public String totalSpeak;
    TextView videopage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nohearing_main);

        text= ((LoginActivity) LoginActivity.mContext).getUserName();
        // Drawer 화면(뷰) 객체 참조
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.name);
        // 계정 이름 표시
        nav_user.setText(text);

        // 전체화면인 DrawerLayout 객체 참조
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView draw = (NavigationView) findViewById(R.id.navigation_view);
        ImageView btnOpenDrawer = (ImageView) findViewById(R.id.drawer_open_button);

        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(draw);
            }
        });

        nohearing_button = (ImageView)findViewById(R.id.nohearing);
        hearing_button = (ImageView)findViewById(R.id.hearing);

        nohearing_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(getApplicationContext(), NoHearingActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
        hearing_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(getApplicationContext(), HearingActivity.class);
                startActivity(myIntent);
                finish();
            }
        });


        // 합친 코드
        context_main = this;
        videopage=(TextView)findViewById(R.id.video_btn);
        videopage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent=new Intent(getApplicationContext(), NoHearingResultActivity.class);
                startActivity(myintent);
            }
        });


        // 음성 인식
        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplicationContext().getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); // 한국어

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);


        //음성출력 생성, 리스너 초기화
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != android.speech.tts.TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });


        // 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, 1);
        }


        //음성인식 버튼
        btn_stt = (ImageView) findViewById(R.id.btn_stt);
        btn_stt.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                System.out.println("-------------------------------------- 음성인식 시작!");
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
//                    //권한을 허용하지 않는 경우
//                } else {
                //권한을 허용한 경우
                try {
                    mRecognizer.startListening(sttIntent);
                } catch (SecurityException e) {
                    Log.d(LogSTT, e.toString());
                    e.printStackTrace();
                }
//                }
            }
        });
        text_stt = (TextView) findViewById(R.id.text_stt);


        //어플이 실행되면 자동으로 1초뒤에 음성 인식 시작
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NoHearingActivity.this, "어플 자동 실행", Toast.LENGTH_SHORT).show();
//                editText_stt.setText("어플 실행됨--자동 실행-----------"+"\r\n"+editText_stt.getText());
                btn_stt.performClick();
            }
        }, 1000);//바로 실행을 원하지 않으면 지워주시면 됩니다


        //음성출력 버튼
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("-------------------------------------- 음성출력 시작!");
                //String totalSpeak = "안녕하세요 음성인식입니다!";
                totalSpeak = "여기 저기";   //text_stt.getText().toString();
                text_stt.setText(totalSpeak);
                //tts.setPitch(1.5f); //1.5톤 올려서
                tts.setSpeechRate(1.0f); //1배속으로 읽기
                tts.speak(totalSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }


    /*
     * 음성인식을 위한 메소드
     */
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) { // 사용자가 말하기 시작할 준비되면 호출
            System.out.println("onReadyForSpeech.........................");
            Toast.makeText(getApplicationContext(), "지금부터 말을 해주세요!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() { // 사용자가 말하기 시작했을 때 호출
            Toast.makeText(getApplicationContext(), "입력 시작", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRmsChanged(float rmsdB) { // 입력받는 소리의 크기
            System.out.println("onRmsChanged........................." + rmsdB);
        }

        @Override
        public void onBufferReceived(byte[] buffer) { // 사용자가 말 시작 후 인식된 단어 buffer에 담음
            System.out.println("onBufferReceived........................." + buffer);
        }

        @Override
        public void onEndOfSpeech() { // 사용자가 말하기 중지하면 호출
            System.out.println("onEndOfSpeech.........................");
        }

        @Override
        public void onError(int error) { // 오류 발생하면 호출

            System.out.println(error);
            String message;
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }
            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message, Toast.LENGTH_SHORT).show();

            //Toast.makeText(MainActivity.this, "천천히 다시 말해주세요.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPartialResults(Bundle partialResults) { // 부분 인식 결과 사용할 수 있을 때 호출
            System.out.println("onPartialResults.........................");
        }

        @Override
        public void onEvent(int eventType, Bundle params) { // 향후 이벤트 추가하기 위해 예약
            System.out.println("onEvent.........................");
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onResults(Bundle results) { // 인식 결과 준비되면 호출
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            text_stt.setText("그래요");

            Toast.makeText(getApplicationContext(), rs[0], Toast.LENGTH_SHORT).show();
            mRecognizer.startListening(sttIntent); //음성인식이 계속 되는 구문이니 필요에 맞게 쓰시길 바람
            //mRecognizer.destroy();
        }
    };
}