package com.example.xenia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class hotelbooking extends AppCompatActivity {
    TextView hotelname, roomcost, roomsno;
    RadioGroup roomtype;
    Button book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotelbooking);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("position", 0);

        final hotel h = hotellist.hotelArrayList.get(pos);

        hotelname = findViewById(R.id.hotelname);
        hotelname.setText(h.getName());

        roomcost = findViewById(R.id.roomcost);
        roomsno = findViewById(R.id.roomsno);

        roomtype = findViewById(R.id.roomtype);
        roomtype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.classic) {
                    roomcost.setText(h.getClassicrm());
                    roomsno.setText(h.getClassicrma());
                }
                else
                {
                    roomcost.setText(h.getDeluxerm());
                    roomsno.setText(h.getDeluxerma());
                }
            }
        });

        book = findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject input_details = new JSONObject();
                try {
                    input_details.put("user_id",user._id);
                    input_details.put("hotel_id", h.get_id());
                }
                catch(JSONException jsone) {
                    Toast.makeText(hotelbooking.this, "JSON failed to make", Toast.LENGTH_SHORT).show();
                }
                String url = "http://192.168.86.149:5000/make_transaction";
                RequestQueue requestQueue = Volley.newRequestQueue(hotelbooking.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, input_details, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Boolean status = response.optBoolean("status");
                        if(status) {
                            Toast.makeText(hotelbooking.this, "Room booked successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(hotelbooking.this, hotellist.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(hotelbooking.this, "Failed to book a room. Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }},
                    new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse (VolleyError error){
                        Log.e("Error Response", error.toString());
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });
    }

}
