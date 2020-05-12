package com.example.xenia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class signup extends AppCompatActivity {
    String us,em,p,first,last,phnum;
    Button register;
    EditText username,emailID,password,confirm,firstName,lastName,phoneNo;
    static Boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    @Override
    protected void onStart() {
        super.onStart();

        register=findViewById(R.id.register);
        username=findViewById(R.id.username);
        emailID=findViewById(R.id.email1);
        firstName=findViewById(R.id.firstname);
        lastName=findViewById(R.id.lastname);
        phoneNo=findViewById(R.id.phonenum);
        password=findViewById(R.id.pass1);
        confirm=findViewById(R.id.pass2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                us=username.getText().toString();
                em=emailID.getText().toString();
                p=password.getText().toString();
                first=firstName.getText().toString();
                last=lastName.getText().toString();
                phnum=phoneNo.getText().toString();
                String p2 = confirm.getText().toString();
                if(!(p.equals(p2))) {
                    password.setText("");
                    confirm.setText("");
                    return;
                }
                putJSON();
                Intent i3=new Intent(signup.this,loginpage.class);
                startActivity(i3);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        us = null;
        em = null;
        p = null;
        first = null;
        last = null;
        phnum = null;

        username=findViewById(R.id.username);
        emailID=findViewById(R.id.email1);
        firstName=findViewById(R.id.firstname);
        lastName=findViewById(R.id.lastname);
        phoneNo=findViewById(R.id.phonenum);
        password=findViewById(R.id.pass1);
        confirm=findViewById(R.id.pass2);

        phoneNo.setText("");
        lastName.setText("");
        firstName.setText("");
        confirm.setText("");
        password.setText("");
        emailID.setText("");
        username.setText("");
    }

    protected void putJSON() {
        JSONObject register_details = new JSONObject();
        try {
            register_details.put("username", us);
            register_details.put("email", em);
            register_details.put("password", p);
            register_details.put("phone", phnum);
            register_details.put("name", first);
            register_details.put("last", last);
        }
        catch(JSONException jsone) {
            Toast.makeText(this, "JSON failed to make", Toast.LENGTH_SHORT).show();
        }
        String url = "http://192.168.86.149:5000/register_user";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, register_details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    status = response.getBoolean("status");
                    if (status) {
                        Toast.makeText(signup.this, "Document was added to database", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(signup.this, "Failed adding document to database", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(JSONException jsone) {
                    Log.e("JSON error", jsone.toString());
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error){
                Log.e("Error Response", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}