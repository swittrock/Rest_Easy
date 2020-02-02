package com.example.chowda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlarmList extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        button = (Button) findViewById(R.id.newAlarm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewAlarm();
            }
        });
    }
    public void openNewAlarm(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
