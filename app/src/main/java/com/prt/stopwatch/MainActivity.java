package com.prt.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    TextView Millisecond;
    TextView Second;
    TextView Minute;
    long mSecond = 0, startTime, timeBuff, updateTime = 0l;
    FloatingActionButton Reset, Start, Stop;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mSecond = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + mSecond;
            int m = (int) (updateTime % 1000);
            int s = (int) ((updateTime / 1000) % 60);
            int min = (int) (updateTime / 60000);

            Millisecond.setText(String.valueOf(m));
            Second.setText(String.valueOf(s));
            Minute.setText(String.valueOf(min));

            mSecond++;
            handler.postDelayed(this,0);
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Millisecond = findViewById(R.id.Millisecond);
        Second = findViewById(R.id.Second);
        Minute = findViewById(R.id.Minute);
        Reset = findViewById(R.id.Reset);
        Start = findViewById(R.id.Start);
        Stop = findViewById(R.id.Stop);

        Start.setOnClickListener(v -> {
            startTime = SystemClock.uptimeMillis();
            handler.post(runnable);
            Start.setEnabled(false);
            Stop.setEnabled(true);
            Reset.setEnabled(false);
        });

        Reset.setOnClickListener(v -> {
            Millisecond.setText("000");
            Minute.setText("00");
            Second.setText("00");
            mSecond = 0l;
            updateTime = 0l;
            timeBuff = 0l;
            startTime = 0l;
            Start.setEnabled(true);
            Stop.setEnabled(false);
            Reset.setEnabled(false);
        });

        Stop.setOnClickListener(v -> {
            timeBuff += mSecond;
            handler.removeCallbacks(runnable);
            Reset.setEnabled(true);
            Start.setEnabled(true);
            Stop.setEnabled(false);
        });


    }
}