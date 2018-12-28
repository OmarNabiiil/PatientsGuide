package com.example.fouaad.hospitalstracker;

import android.content.Intent;
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

public class RegistrationActivity extends AppCompatActivity {

    EditText username;
    EditText fullname;
    EditText password;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.txt_username);
        fullname = findViewById(R.id.txt_fullname);
        password = findViewById(R.id.txt_password);
        confirmPassword = findViewById(R.id.txt_confirm_password);
    }

    public void Register(View view) {

        if(username.getText().toString().isEmpty() && fullname.getText().toString().isEmpty()
                && password.getText().toString().isEmpty() && confirmPassword.getText().toString().isEmpty()){
            Toast.makeText(RegistrationActivity.this, "please enter all your details", Toast.LENGTH_LONG).show();
        }else{
            if(username.getText().toString().isEmpty()){
                Toast.makeText(RegistrationActivity.this, "please enter the username", Toast.LENGTH_LONG).show();
            }else {
                if(fullname.getText().toString().isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "please enter the full name", Toast.LENGTH_LONG).show();
                }else{
                    if(password.getText().toString().isEmpty()){
                        Toast.makeText(RegistrationActivity.this, "please enter the password", Toast.LENGTH_LONG).show();
                    }else {
                        if(confirmPassword.getText().toString().isEmpty()){
                            Toast.makeText(RegistrationActivity.this, "please confirm the password", Toast.LENGTH_LONG).show();
                        }else{
                            if(password.getText().toString().equals(confirmPassword.getText().toString())){
                                addUserDb();
                            }else{
                                Toast.makeText(RegistrationActivity.this, "please confirm the right password", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        }

    }

    public void addUserDb(){
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Configuration.ADDUSER_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("login", "" + response);
                if (response.equals("success")) {
                    Toast.makeText(RegistrationActivity.this, "added successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    if (response.equals("failed")) {
                        Toast.makeText(RegistrationActivity.this, "Sorry, network connection failed!", Toast.LENGTH_LONG).show();
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
                params.put("userName", username.getText().toString());
                params.put("fullName", fullname.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
