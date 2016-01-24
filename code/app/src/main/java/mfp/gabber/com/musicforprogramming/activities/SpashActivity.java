package mfp.gabber.com.musicforprogramming.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import mfp.gabber.com.musicforprogramming.R;

public class SpashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spash);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);

            }
        }, 2000);
    }

}
