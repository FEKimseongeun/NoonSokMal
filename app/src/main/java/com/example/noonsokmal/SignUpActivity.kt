package com.example.noonsokmal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.signup.*

class SignUpActivity : AppCompatActivity() {
    var ID_join: EditText? = null
    var pwd_join: EditText? = null
    var name_join: EditText? = null
    var phone_join: EditText? = null
    var btn: LinearLayout? = null
    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        //loss_degree Spinner
        val degreeSpinner: Spinner = findViewById(R.id.loss_degree)
        val degreeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.loss_degree,
            android.R.layout.simple_spinner_item
        )
        degreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        degreeSpinner.adapter = degreeAdapter
        date.maxDate = System.currentTimeMillis()


        ID_join = findViewById<View>(R.id.id_content) as EditText
        pwd_join = findViewById<View>(R.id.password_content) as EditText
        name_join = findViewById<View>(R.id.name_content) as EditText
        phone_join = findViewById<View>(R.id.Phone_number_content) as EditText
        btn = findViewById<View>(R.id.signup_button) as LinearLayout
        firebaseAuth = FirebaseAuth.getInstance()

        //비밀번호 일치 여부 확인
        password_content_check.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            // EditText 입력이 끝난 후
            override fun afterTextChanged(p0: Editable?) {
                if(password_content.getText().toString().equals(password_content_check.getText().toString())){
                    password_check_txt.setText("일치합니다")
                }
                else
                    password_check_txt.setText("일치하지 않습니다")
            }
        })

        //회원가입 버튼을 누를시
        btn!!.setOnClickListener {
            val id = ID_join!!.text.toString().trim { it <= ' ' }
            val pwd = pwd_join!!.text.toString().trim { it <= ' ' }
            val name = name_join!!.text.toString().trim { it <= ' ' }
            val phone = phone_join!!.text.toString().trim { it <= ' ' }
            //공백인 부분을 제거하고 보여주는 trim();

            firebaseAuth!!.createUserWithEmailAndPassword(id, pwd)
                .addOnCompleteListener(this@SignUpActivity,
                    OnCompleteListener { task ->
                        if(password_check_txt.getText().toString().equals("일치하지 않습니다")){
                            Toast.makeText(this@SignUpActivity, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT)
                                .show()
                            return@OnCompleteListener
                        }
                        else {
                            if (task.isSuccessful) { //성공시 로그인화면으로 이동
                                Toast.makeText(this, "${id}님 회원가입 되었습니다.", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                                return@OnCompleteListener
                            }
                        }
                    })

        }
    }
}