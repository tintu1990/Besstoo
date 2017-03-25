package com.rplanx.besstoo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;

public class Splash extends AppCompatActivity {
    protected int _splashTime = 2500;
    private Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        final Splash sPlashScreen = this;

        // thread for displaying the SplashScreen
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        // wait 5 sec
                        wait(_splashTime);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();

                    // start a new activity
                    Intent i = new Intent(Splash.this, Food_list.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        splashTread.start();

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return false;
    }
}
