package bap.training.com.equizlocal.splashscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bap.training.com.equizlocal.R;
import bap.training.com.equizlocal.activity.LoginActivity;
import bap.training.com.equizlocal.activity.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    //TODO user shared preferences
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
