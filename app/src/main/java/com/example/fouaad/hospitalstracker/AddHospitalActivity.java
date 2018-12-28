package com.example.fouaad.hospitalstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fouaad.hospitalstracker.helper.Configuration;
import com.example.fouaad.hospitalstracker.helper.MyApplication;

import java.util.HashMap;
import java.util.Map;

public class AddHospitalActivity extends AppCompatActivity {

    EditText hospitalName;
    EditText hospitalRate;
    EditText departmentName;
    EditText doctorName;
    EditText doctorStartTime;
    EditText doctorEndTime;
    EditText doctorPricesRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);

        hospitalName = findViewById(R.id.txt_hospitalName);
        hospitalRate = findViewById(R.id.txt_rating);
        departmentName = findViewById(R.id.txt_department);
        doctorName = findViewById(R.id.txt_doctor);
        doctorStartTime = findViewById(R.id.txt_startTime);
        doctorEndTime = findViewById(R.id.txt_endTime);
        doctorPricesRange = findViewById(R.id.txt_pricesRange);
    }

    public void AddHospital(View view) {

        if(hospitalName.getText().toString().isEmpty() && hospitalRate.getText().toString().isEmpty() && departmentName.getText().toString().isEmpty()
                && doctorName.getText().toString().isEmpty() && doctorStartTime.getText().toString().isEmpty() && doctorEndTime.getText().toString().isEmpty()){
            Toast.makeText(AddHospitalActivity.this, "please enter all details", Toast.LENGTH_LONG).show();
        }else{
            if(hospitalName.getText().toString().isEmpty()){
                Toast.makeText(AddHospitalActivity.this, "please enter the hospital name", Toast.LENGTH_LONG).show();
            }else {
                if(hospitalRate.getText().toString().isEmpty()){
                    Toast.makeText(AddHospitalActivity.this, "please enter the hospital rating", Toast.LENGTH_LONG).show();
                }else{
                    if(departmentName.getText().toString().isEmpty()){
                        Toast.makeText(AddHospitalActivity.this, "please enter the department name", Toast.LENGTH_LONG).show();
                    }else{
                        if(doctorName.getText().toString().isEmpty()){
                            Toast.makeText(AddHospitalActivity.this, "please enter the doctor name", Toast.LENGTH_LONG).show();
                        }else {
                            if(doctorStartTime.getText().toString().isEmpty()){
                                Toast.makeText(AddHospitalActivity.this, "please enter the doctor start time", Toast.LENGTH_LONG).show();
                            }else{
                                if(doctorEndTime.getText().toString().isEmpty()){
                                    Toast.makeText(AddHospitalActivity.this, "please enter the doctor end time", Toast.LENGTH_LONG).show();
                                }else{
                                    if(doctorPricesRange.getText().toString().isEmpty()){
                                        Toast.makeText(AddHospitalActivity.this, "please enter the prices range", Toast.LENGTH_LONG).show();
                                    }else{
                                        addHospitalDb();
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

    }

    public void addHospitalDb(){
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Configuration.ADDHOSPITAL_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("login", "" + response);
                if (response.equals("success")) {
                    Toast.makeText(AddHospitalActivity.this, "added successfully", Toast.LENGTH_LONG).show();
                    /*Intent i = new Intent(AddHospitalActivity.this, AddHospitalActivity.class);
                    startActivity(i);*/
                } else {
                    if (response.equals("failed")) {
                        Toast.makeText(AddHospitalActivity.this, "Sorry, network connection failed!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<String, String>();
                params.put("hospital", hospitalName.getText().toString());
                params.put("rate", hospitalRate.getText().toString());
                params.put("department", departmentName.getText().toString());
                params.put("doctor", doctorName.getText().toString());
                params.put("startTime", doctorStartTime.getText().toString());
                params.put("endTime", doctorEndTime.getText().toString());
                params.put("pricesRange", doctorPricesRange.getText().toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
