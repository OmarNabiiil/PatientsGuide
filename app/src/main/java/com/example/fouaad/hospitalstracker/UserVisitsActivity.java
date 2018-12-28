package com.example.fouaad.hospitalstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fouaad.hospitalstracker.helper.Configuration;
import com.example.fouaad.hospitalstracker.helper.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserVisitsActivity extends AppCompatActivity implements UserVisitsAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private List<UserVisit> row_list;
    private UserVisitsAdapter mAdapter;
    private String hospitalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_visits);

        recyclerView = findViewById(R.id.recycler_view);
        row_list = new ArrayList<>();
        mAdapter = new UserVisitsAdapter(row_list, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllVisits();
    }

    public void getAllVisits(){

        String tag_string_req = "req_register";

        String url = Configuration.VISITS_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                try {
                    //JSONObject jObji = new JSONObject(response);
                    JSONArray a=new JSONArray(response);
                    Log.d("loginres",a.toString());
                    ArrayList<JSONObject> allDepartments=new ArrayList<JSONObject>();
                    int sizeofarray=a.length();
                    for(int i=0;i<sizeofarray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        UserVisit d = new UserVisit(jObj.get("hospital_name").toString(), jObj.get("user_rating").toString(), jObj.get("visit_state").toString());
                        row_list.add(d);
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
                params.put("userID", LoginActivity.USER_ID);

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onItemClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        UserVisit u = row_list.get(viewHolder.getAdapterPosition());
        Log.d("test",u.getState());
        if(u.getState().equals("0")){
            Intent i = new Intent(UserVisitsActivity.this, FinishVisitActivity.class);
            i.putExtra("hospital",u.getHospital());
            Log.d("test",u.getHospital());
            startActivity(i);
        }
    }
}
