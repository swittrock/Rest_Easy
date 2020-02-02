package com.example.chowda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    TimePicker timePicker;
    TextView textView;

    int numAlarms = 0;
    ArrayList<Button> buttonList = new ArrayList<Button>();
    int mHour, mMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Hour: " + hourOfDay + " Minute: " + minute);

        Button test = new Button(this);
        test.setId(numAlarms + 1);
        buttonList.add(numAlarms, test);

        test.setText("Hour: " + hourOfDay + " Minute: " + minute);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.mainLayout);
        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        if (numAlarms == 0) {
            rp.addRule(RelativeLayout.BELOW, R.id.sample);
        }
        else{
            rp.addRule(RelativeLayout.BELOW, buttonList.get(numAlarms).getId());
        }

        numAlarms += 1;
        rl.addView(test, rp);
        setTimer(view);
    }

    public void setTimer(View v){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date date = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_now.setTime(date);
        cal_alarm.setTime(date);

        cal_alarm.set(Calendar.HOUR_OF_DAY, mHour);
        cal_alarm.set(Calendar.MINUTE, mMin);
        cal_alarm.set(Calendar.SECOND, 0);

        if(cal_alarm.before(cal_now))
        {
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent i = new Intent(MainActivity.this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 24444, i, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
    }
}
