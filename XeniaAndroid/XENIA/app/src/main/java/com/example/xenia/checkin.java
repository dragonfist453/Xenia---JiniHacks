package com.example.xenia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class checkin extends AppCompatActivity {
    Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        makeQR();
        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(checkin.this, options.class);
                startActivity(intent);
            }
        });
    }
    protected void makeQR() {
        ImageView qrcode = (ImageView) findViewById(R.id.qrcode);;
        Picasso.get().load("http://192.168.86.149:5000/qrcode?qr=" + user._id).into(qrcode);
    }
}
