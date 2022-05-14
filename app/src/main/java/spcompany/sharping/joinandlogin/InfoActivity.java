package spcompany.sharping.joinandlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends Activity {

    private String userID;
    private String userName;

    private TextView tInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        tInfo = (TextView) findViewById(R.id.info_tInfo);

        Intent intent = getIntent();
        userID = intent.getStringExtra("username");
        userName = intent.getStringExtra("name");

        setProfile(userID, userName);
    }

    private void setProfile(String username, String name) {
        tInfo.setText(name + "님(" + username + ") 환영합니다!");
    }

    public void backButtonPressed(View view) {
        super.onBackPressed();
    }
}