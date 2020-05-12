package com.example.xenia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class options extends AppCompatActivity {
Button service,food,eg,al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        service=findViewById(R.id.service);
        food=findViewById(R.id.food);
        eg=findViewById(R.id.eguide);
        al=findViewById(R.id.alarm);

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i12=new Intent(options.this,services.class);
                startActivity(i12);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i13=new Intent(options.this,food.class);
                startActivity(i13);
            }
        });

        eg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Search for restaurants nearby
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=tourist+places");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
        });

        al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i15=new Intent(options.this,alarm.class);
                startActivity(i15);
            }
        });
    }
}
