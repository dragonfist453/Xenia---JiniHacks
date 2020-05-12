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

public class services extends AppCompatActivity {
Button home,send;
EditText com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        home=findViewById(R.id.home3);
        send=findViewById(R.id.send2);
        com=findViewById(R.id.comment);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i21=new Intent(services.this,mainpage.class);
                startActivity(i21);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no="9611671111";
                String message=com.getText().toString();

                Intent i22=new Intent(getApplicationContext(),services.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,i22,0);

                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(no,null,message,pi,null);

                Toast.makeText(services.this, "Query sent successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
