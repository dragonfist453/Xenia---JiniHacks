package com.example.xenia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class mainpage extends AppCompatActivity {
Button mybooking,myhotel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        mybooking=findViewById(R.id.mybookings);
        myhotel=findViewById(R.id.myhotels);

        putJSON();
        mybooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i5=new Intent(mainpage.this,hotellist.class);
                startActivity(i5);
            }
        });
        myhotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i6=new Intent(mainpage.this,checkin.class);
                startActivity(i6);
            }
        });
    }

    protected void putJSON() {
        JSONObject input_details = new JSONObject();
        Intent intent = getIntent();
        try {
            input_details.put("email", intent.getStringExtra("email"));
        }
        catch(JSONException jsone) {
            Toast.makeText(this, "JSON failed to make", Toast.LENGTH_SHORT).show();
        }
        String url = "http://192.168.86.149:5000/get_userdata";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, input_details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    user._id = response.getString("_id");
                    user.email = response.getString("email");
                    user.last = response.getString("last");
                    user.name = response.getString("name");
                    user.password = response.getString("password");
                }
                catch(Exception e) {
                    Log.e("Error", e.toString());
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error){
                Log.e("Error Response", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainpage.this);

        builder.setMessage("Are you sure you want to logout?");
        builder.setTitle("Logging out!");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
