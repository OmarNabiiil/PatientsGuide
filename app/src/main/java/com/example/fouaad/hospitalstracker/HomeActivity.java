package com.example.fouaad.hospitalstracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fouaad.hospitalstracker.helper.Configuration;
import com.example.fouaad.hospitalstracker.helper.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView name = findViewById(R.id.txt_username);
        name.setText("Name : "+LoginActivity.USER_FULL_NAME);
        checkVisits();
    }

    public void Search(View view) {
        Intent i=new Intent(HomeActivity.this, NewMapsActivity.class);
        startActivity(i);
    }

    public void Previous(View view) {
        Intent i=new Intent(HomeActivity.this, UserVisitsActivity.class);
        startActivity(i);
    }

    public void Call(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:01115979192"));

        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("login", "hiiiiiiii");
            return;
        }

        startActivity(intent);
    }

    public void checkVisits(){
        String tag_string_req = "req_register";

        String url = Configuration.CHECKVISITS_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                try {
                    //JSONObject jObji = new JSONObject(response);
                    JSONObject jObj = new JSONObject(response);//all the users in the database
                    UserVisit d = new UserVisit(jObj.get("hospital_name").toString(), jObj.get("user_rating").toString(), jObj.get("visit_state").toString());
                    Intent i = new Intent(HomeActivity.this, FinishVisitActivity.class);
                    i.putExtra("hospital",d.getHospital());
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
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

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
