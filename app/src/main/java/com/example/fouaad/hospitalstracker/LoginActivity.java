package com.example.fouaad.hospitalstracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    EditText name;
    EditText password;
    CheckBox adminCheck;
    FloatingActionButton fab;
    public static String USER_ID = "";
    public static String USER_FULL_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);
        adminCheck = findViewById(R.id.check_admin);

    }

    public void Login(View view) {

        if(name.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
            Toast.makeText(LoginActivity.this, "please enter you username and password", Toast.LENGTH_LONG).show();
        }else{
            if(name.getText().toString().isEmpty()){
                Toast.makeText(LoginActivity.this, "please enter you username", Toast.LENGTH_LONG).show();
            }else {
                if(password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "please enter you password", Toast.LENGTH_LONG).show();
                }else{
                    if(adminCheck.isChecked()){
                        loginAdmin(name.getText().toString(), password.getText().toString());
                    }else{
                        loginUser(name.getText().toString(), password.getText().toString());
                    }

                }
            }
        }

    }

    public void SignUp(View view) {
        Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(i);
    }

    private void loginUser(final String name, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Configuration.LOGIN_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("login", "" + response);
                if (response.equals("not exist")) {
                    Toast.makeText(LoginActivity.this, "Sorry, you need to register first!", Toast.LENGTH_LONG).show();
                } else {
                    if (response.equals("failed")) {
                        Toast.makeText(LoginActivity.this, "Sorry, network connection failed!", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            JSONObject obj = new JSONObject(response);
                            USER_ID = obj.get("id").toString();
                            USER_FULL_NAME = obj.get("fullname").toString();
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                Log.d("login", "" + name);

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", name);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void loginAdmin(final String name, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Configuration.LOGINADMIN_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("login", "" + response);
                if (response.equals("not exist")) {
                    Toast.makeText(LoginActivity.this, "Sorry, you are not an admin!", Toast.LENGTH_LONG).show();
                } else {
                    if (response.equals("failed")) {
                        Toast.makeText(LoginActivity.this, "Sorry, network connection failed!", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(LoginActivity.this, AddHospitalActivity.class);
                        startActivity(i);
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
                params.put("username", name);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void Call(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:01115979192"));

        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("login", "hiiiiiiii");
            return;
        }

        startActivity(intent);

    }
}
