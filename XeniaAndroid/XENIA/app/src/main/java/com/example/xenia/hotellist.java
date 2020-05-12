package com.example.xenia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class hotellist extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public static ArrayList<hotel> hotelArrayList;
    hotelAdapter hoteladapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotellist);
        putJSON();
    }

    protected void putJSON() {
        String url = "http://192.168.86.149:5000/get_hotels";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray responseArray = response;
                hotelArrayList = new ArrayList<hotel>();

                for(int i=0;i<responseArray.length();i++) {
                    hotel hotelobj = new hotel();
                    JSONObject obj = responseArray.optJSONObject(i);
                    Log.i("Entry"+i, obj.toString());

                    hotelobj.set_id(obj.optString("_id"));
                    hotelobj.setName(obj.optString("name"));
                    hotelobj.setContactnum(obj.optString("contactnum"));
                    hotelobj.setClassicrm(obj.optString("classicrm"));
                    hotelobj.setPassword(obj.optString("password"));
                    hotelobj.setEmail(obj.optString("email"));
                    hotelobj.setClassicrma(obj.optString("classicrma"));
                    hotelobj.setDeluxerm(obj.optString("deluxerm"));
                    hotelobj.setDeluxerma(obj.optString("deluxerma"));

                    hotelArrayList.add(hotelobj);
                }
                Log.d("Number", ""+hotelArrayList.size());
                recyclerView = findViewById(R.id.hotelList);
                hoteladapter = new hotelAdapter(hotellist.this,hotelArrayList);
                recyclerView.setAdapter(hoteladapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(hotellist.this));
            }},
            new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error){
                Log.e("Error Response", error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public class hotelAdapter extends RecyclerView.Adapter<hotelAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private ArrayList<hotel> hotelArrayList;


        public hotelAdapter(Context ctx, ArrayList<hotel> dataModelArrayList){

            inflater = LayoutInflater.from(ctx);
            this.hotelArrayList = dataModelArrayList;
        }

        @Override
        public hotelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.hotel_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(hotelAdapter.MyViewHolder holder, int position) {

            holder.name.setText(hotelArrayList.get(position).getName());
            holder.classicrm.setText(hotelArrayList.get(position).getClassicrm());
            holder.deluxerm.setText(hotelArrayList.get(position).getDeluxerm());

            final int pos = position;

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent gotohotel = new Intent(hotellist.this, hotelbooking.class);
                    gotohotel.putExtra("position", pos);
                    startActivity(gotohotel);
                }
            });
        }

        @Override
        public int getItemCount() {
            return hotelArrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView name, classicrm, deluxerm;
            LinearLayout parentLayout;

            public MyViewHolder(View itemView) {
                super(itemView);
                parentLayout = itemView.findViewById(R.id.parent_layout);
                classicrm = (TextView) itemView.findViewById(R.id.classicrm);
                name = (TextView) itemView.findViewById(R.id.name);
                deluxerm = (TextView) itemView.findViewById(R.id.deluxerm);
            }

        }
    }
}

