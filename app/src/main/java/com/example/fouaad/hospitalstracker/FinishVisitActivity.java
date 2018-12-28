package com.example.fouaad.hospitalstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fouaad.hospitalstracker.helper.Configuration;
import com.example.fouaad.hospitalstracker.helper.MyApplication;

import java.util.HashMap;
import java.util.Map;

public class FinishVisitActivity extends AppCompatActivity {

    private Button finish;
    private Button done;
    private TextView hospital;
    private LinearLayout rating_layout;
    private RatingBar visitRate;
    private String hospital_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_visit);

        hospital_name = getIntent().getStringExtra("hospital");

        hospital = findViewById(R.id.txt_hospitalName);
        visitRate = findViewById(R.id.visit_rate);
        rating_layout = findViewById(R.id.layout);
        rating_layout.setVisibility(View.INVISIBLE);
        hospital.setText("Hospital Name: "+hospital_name);

        finish = findViewById(R.id.btn_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishHospitalVisit();
            }
        });
        done = findViewById(R.id.btn_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setHospitalRating();
            }
        });

    }

    public void finishHospitalVisit(){

        String tag_string_req = "req_register";

        String url = Configuration.FINISHVISIT_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                if(response.equals("success")){
                    rating_layout.setVisibility(View.VISIBLE);
                    finish.setVisibility(View.GONE);
                    Toast.makeText(FinishVisitActivity.this,"you finished successfully", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Registration Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<String, String>();
                params.put("userID", LoginActivity.USER_ID);
                params.put("hospitalName", hospital_name);

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void setHospitalRating(){

        String tag_string_req = "req_register";

        String url = Configuration.RATE_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                if(response.equals("success")){
                    Toast.makeText(FinishVisitActivity.this,"you rated successfully", Toast.LENGTH_LONG).show();
                    updateOverallRating();
                    Intent i = new Intent(FinishVisitActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Registration Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<String, String>();
                params.put("userID", LoginActivity.USER_ID);
                params.put("hospitalName", hospital_name);
                params.put("hospitalRating", visitRate.getRating()+"");

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void updateOverallRating(){

        String tag_string_req = "req_register";

        String url = Configuration.UPDATEOVERALLRATING_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards rate: " + response.toString());
                if(response.equals("success")){
                    Toast.makeText(FinishVisitActivity.this,"you rated successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(FinishVisitActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Registration Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<String, String>();
                params.put("hospitalName", hospital_name);
                params.put("userRate", visitRate.getRating()+"");

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
