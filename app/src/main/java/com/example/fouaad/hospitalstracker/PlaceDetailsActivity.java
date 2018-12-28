package com.example.fouaad.hospitalstracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fouaad.hospitalstracker.helper.Configuration;
import com.example.fouaad.hospitalstracker.helper.MyApplication;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PlaceDetailsActivity extends AppCompatActivity {

    private TextView rating;
    private RecyclerView recyclerView;
    private List<Department> row_list;
    private DepartmentsAdapter mAdapter;
    private String hospital_name;
    private String hospital_id;
    private LatLng hospital_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        Intent i = getIntent();
        hospital_name = i.getStringExtra("hospital name");
        hospital_id = i.getStringExtra("hospital id");
        hospital_position = i.getParcelableExtra("hospital location");

        rating = findViewById(R.id.txt_rating);
        recyclerView = findViewById(R.id.recycler_view);
        row_list = new ArrayList<>();
        mAdapter = new DepartmentsAdapter(row_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllDepartments(hospital_name, hospital_id);
    }

    public void getAllDepartments(final String hospitalName, final String hospitalID){

        String tag_string_req = "req_register";

        String url = Configuration.DEPARTMENTS_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                try {
                    //JSONObject jObji = new JSONObject(response);
                    JSONArray a=new JSONArray(response);
                    Log.d("loginres",a.toString());
                    //overall_rating
                    rating.setText("Rating : "+a.getJSONObject(0).get("overall_rating").toString());
                    int sizeofarray=a.length();
                    for(int i=0;i<sizeofarray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        Department d = new Department(jObj.get("hospital_department").toString(), jObj.get("doctor_name").toString(),
                                jObj.get("doctor_start_time").toString(), jObj.get("doctor_end_time").toString(), jObj.get("doctor_prices_range").toString());
                        row_list.add(d);
                        /*Course c= Course(jObj.get("name").toString(), jObj.get("link").toString(),
                                jObj.get("body").toString(), jObj.get("photo").toString());
                        row_list.add(c);*/

                    }

                    mAdapter.notifyDataSetChanged();

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
                params.put("hospitalName", hospitalName);
                params.put("hospitalID", hospitalID);

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void Visit(View view) {
        showAlertDialog();
    }

    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    public void showAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Code : "+gen() +"\n"+"\n"+" please take a screenshot");
                alertDialogBuilder.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                visitDB();
                            }
                        });

        alertDialogBuilder.setNegativeButton("cancel",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void visitDB(){
        String tag_string_req = "req_register";

        String url = Configuration.VISIT_HOSPITAL_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                if(response.equals("success")){
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr="+hospital_position.latitude+","+hospital_position.longitude+""));
                    startActivity(intent);
                    getResources();
                    Button rate = findViewById(R.id.btn_rate);
                    Button visit = findViewById(R.id.btn_visit);
                    rate.setEnabled(true);
                    rate.setBackgroundColor(getResources().getColor(R.color.buttonOne));
                    visit.setEnabled(false);
                    visit.setBackgroundColor(getResources().getColor(R.color.buttonTwo));
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
                params.put("state", "0");

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void Rate(View view) {
        Intent i = new Intent(PlaceDetailsActivity.this, FinishVisitActivity.class);
        i.putExtra("hospital",hospital_name);
        startActivity(i);
        finish();
    }
}
