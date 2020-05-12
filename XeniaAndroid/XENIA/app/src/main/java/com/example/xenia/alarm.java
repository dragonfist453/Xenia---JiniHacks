package com.example.xenia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class alarm extends AppCompatActivity {
Button home,send; EditText sch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        home=findViewById(R.id.home4);
        send=findViewById(R.id.send2);
        sch=findViewById(R.id.schedule);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i23=new Intent(alarm.this,mainpage.class);
                startActivity(i23);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no="9611671111";
                String message=sch.getText().toString();

                Intent i24=new Intent(getApplicationContext(),services.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,i24,0);

                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(no,null,message,pi,null);

                Toast.makeText(alarm.this, "Query sent successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
