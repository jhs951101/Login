package spcompany.sharping.joinandlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SigninActivity extends Activity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        username = (EditText) findViewById(R.id.signin_eTxtUsername);
        password = (EditText) findViewById(R.id.signin_eTxtPassword);
    }

    public void backButtonPressed(View view) {
        super.onBackPressed();
    }

    public void signinButtonPressed(View view) {
        if(username.getText().toString().equals("")){
            Toast.makeText(this, "You must input your username", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().equals("")){
            Toast.makeText(this, "You must input your password", Toast.LENGTH_SHORT).show();
        }
        else{  // 서버에 데이터 전달
            String userID = username.getText().toString();
            String userPassword = password.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                            String username = jsonResponse.getString("username");
                            String name = jsonResponse.getString("name");
                            String gender = jsonResponse.getString("gender");
                            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);

                            // 로그인 하면서 사용자 정보 넘기기
                            intent.putExtra("username", username);
                            intent.putExtra("name", name);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
            RequestQueue queue = Volley.newRequestQueue(SigninActivity.this);
            queue.add(loginRequest);
        }
    }

    public void toSignupButtonPressed(View view) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
}