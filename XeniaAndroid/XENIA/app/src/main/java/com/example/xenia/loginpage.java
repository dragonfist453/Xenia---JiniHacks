package com.example.xenia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONException;
import org.json.JSONObject;

public class loginpage extends AppCompatActivity {
    Button signin;
    EditText email,pass;
    String input_name, input_pass;
    static Boolean authorised;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        signin=findViewById(R.id.signin);
        email=findViewById(R.id.email1);
        pass=findViewById(R.id.pass1);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_name = email.getText().toString();
                input_pass = pass.getText().toString();
                putJSON();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        email=findViewById(R.id.email1);
        pass=findViewById(R.id.pass1);

        input_name = null;
        input_pass = null;

        pass.setText("");
        email.setText("");
    }

    protected void putJSON() {
        JSONObject input_details = new JSONObject();
        try {
            input_details.put("email", input_name);
            input_details.put("password", input_pass);
        }
        catch(JSONException jsone) {
            Toast.makeText(this, "JSON failed to make", Toast.LENGTH_SHORT).show();
        }
        String url = "http://192.168.86.149:5000/login_user";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, input_details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    authorised = response.getBoolean("authorised");
                    if (authorised) {
                        Toast.makeText(loginpage.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        Intent i4 = new Intent(loginpage.this, mainpage.class);
                        i4.putExtra("email", input_name);
                        i4.putExtra("password", input_pass);
                        startActivity(i4);
                    } else {
                        Toast.makeText(loginpage.this, "Failed log in", Toast.LENGTH_SHORT).show();
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