package com.example.xenia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.*;

public class food extends AppCompatActivity {
Button home, foodpls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        home=findViewById(R.id.home7);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i16=new Intent(food.this,mainpage.class);
                startActivity(i16);
            }
        });

        foodpls = findViewById(R.id.foodpls);

        foodpls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.food = true;
                putJSON();
            }
        });
    }

    protected void putJSON() {
        JSONObject data = new JSONObject();
        try{
            data.put("_id", user._id);
            data.put("username", user.username);
            data.put("password", user.password);
            data.put("email", user.email);
            data.put("name", user.name);
            data.put("last", user.last);
            data.put("food", user.food);
            //data.put("item", user.item);
            //data.put("room", user.room);
            //data.put("roomservice", user.roomservice);
        }
        catch(JSONException jsone) {
            Log.d("Error", "error");
        }
        String url = "http://192.168.86.149:5000/update_users";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = response.optBoolean("status");
                if(status) {
                    Toast.makeText(food.this, "Food will be on the way!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(food.this, "Try that again!", Toast.LENGTH_SHORT).show();
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
