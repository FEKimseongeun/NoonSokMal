package com.example.noonsokmal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity() {
    private var Login_button: LinearLayout? = null
    private var ID_login: EditText? = null
    private var pwd_login: EditText? = null
    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //자동 로그인 체크 확인
        // SharedPreferences 안에 값이 저장되어 있지 않을 때 -> Login함수 불러옴
        if (MySharedPreferences.getUserId(this).isBlank()
            || MySharedPreferences.getUserPass(this).isBlank()
        ) {
            //로그인 기능 구현
            Login_button = findViewById<View>(R.id.signup_button) as LinearLayout
            ID_login = findViewById<View>(R.id.id_content) as EditText
            pwd_login = findViewById<View>(R.id.password_content) as EditText

            firebaseAuth = FirebaseAuth.getInstance() //firebaseAuth의 인스턴스를 가져옴

            Login_button!!.setOnClickListener {
                val id = ID_login!!.text.toString().trim { it <= ' ' }
                val pwd = pwd_login!!.text.toString().trim { it <= ' ' }

                //자동로그인 체크시 SharedPreferences에 아이디, 비번 저장함
                if (login_checkbox.isChecked) {
                    MySharedPreferences.setUserId(this, id)
                    MySharedPreferences.setUserPass(this, pwd)
                }

                //String형 변수 id,pwd(edittext에서 받오는 값)으로 로그인하는것
                firebaseAuth!!.signInWithEmailAndPassword(id, pwd)
                    .addOnCompleteListener(this@LoginActivity, OnCompleteListener { task ->
                        if (task.isSuccessful) { //성공시 청각장애인/비청각장애인 구분해서 화면 이동해야함(아직 구현x)
                            Toast.makeText(this, "${id}님 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, NoHearingActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "이메일과 비밀번호를 다시 입력해주세요",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@OnCompleteListener
                        }
                    })

            }
        } else { // SharedPreferences 안에 값이 저장되어 있을 때 -> 청각/비청각 화면으로 이동
            Toast.makeText(
                this,
                "${MySharedPreferences.getUserId(this)}님 자동 로그인 되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, NoHearingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}