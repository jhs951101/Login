package spcompany.sharping.joinandlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SignupActivity extends Activity {

    private static String userName, userID, userPassword, userGender;
    private EditText eTxtUsername;
    private EditText eTxtPassword;
    private EditText eTxtName;
    private RadioButton rbtnMale;
    private RadioButton rbtnFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        eTxtUsername = (EditText) findViewById(R.id.signup_eTxtUsername);
        eTxtPassword = (EditText) findViewById(R.id.signup_eTxtPassword);
        eTxtName = (EditText) findViewById(R.id.signup_eTxtName);
        rbtnMale = (RadioButton) findViewById(R.id.signup_rbtnMale);
        rbtnFemale = (RadioButton) findViewById(R.id.signup_rbtnFemale);
    }

    public void backButtonPressed(View view) {
        super.onBackPressed();
    }

    public void signupButtonPressed(View view) {
        if(eTxtUsername.getText().toString().equals("")){
            Toast.makeText(this, "You must input your username", Toast.LENGTH_SHORT).show();
        }
        else if(eTxtPassword.getText().toString().equals("")){
            Toast.makeText(this, "You must input your password", Toast.LENGTH_SHORT).show();
        }
        else if(eTxtName.getText().toString().equals("")){
            Toast.makeText(this, "You must input your name", Toast.LENGTH_SHORT).show();
        }
        else if(!rbtnMale.isChecked() && !rbtnFemale.isChecked()){
            Toast.makeText(this, "You must select your gender", Toast.LENGTH_SHORT).show();
        }
        else {  // 서버에 데이터 전달
            // 현재 입력된 정보를 string으로 가져오기
            userName = eTxtName.getText().toString();
            userID = eTxtUsername.getText().toString();
            userPassword = eTxtPassword.getText().toString();

            if(rbtnMale.isChecked())
                userGender = "M";
            else if(rbtnFemale.isChecked())
                userGender = "F";

            // 회원가입 절차 시작
            Response.Listener<String> responseListener = new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    try{
                        // String으로 그냥 못 보냄으로 JSON Object 형태로 변형하여 전송
                        // 서버 통신하여 회원가입 성공 여부를 jsonResponse로 받음
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){  // 회원가입이 가능한다면
                            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(SignupActivity.this, SigninActivity.class );
                            //startActivity(intent);
                            finish();  //액티비티를 종료시킴(회원등록 창을 닫음)
                        }
                        else{  // 회원가입이 안된다면
                            Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 한 번 확인해 주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 한 번 확인해 주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            };

            // Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분
            RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userGender, responseListener);
            RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
            queue.add(registerRequest);
        }
    }
}