package com.digiponic.halokes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.digiponic.halokes.Storage.Session;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Arunstop on 23-May-19.
 */

public class SplashActivity extends Activity {

    private ProgressBar progressBar;

    private Handler handler;
    private Runnable runnable;
    private Timer timer;
    private Session session;

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        session = Session.getInstance(this);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        progressBar.setProgress(20);
        progressBar.setMax(50);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                timer.cancel();
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 10000, 1000);

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //check the user logged in
                    if (session.isLoggedIn()) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                        startActivity(new Intent(SplashActivity.this, InitializeActivity.class));

                    } else {
//                        Toast.makeText(SplashActivity.this, "Selamat datang kembali!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                }
            }
        };
        thread.start();
    }
}
