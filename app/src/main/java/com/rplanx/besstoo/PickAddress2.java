package com.rplanx.besstoo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rplanx.besstoo.application.VolleyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickAddress2 extends Activity implements  View.OnClickListener {
    AutoCompleteTextView autoCompleteTextView;
    String JSON="http://besstoo.com/public/service/all_locations";
    List<String> list;
    EditText edit1;
    EditText edit2;
    Button button;
    String str_address1;
    String str_address2;
    String str_address3;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String str_user_id;
    String str_address4;
    List<String> list1;
    String str_time;
    String tag;
    String JSON_URI="http://besstoo.com/public/service/fetch_location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_address2);
        edit1=(EditText)findViewById(R.id.edt_address2);
        edit2=(EditText)findViewById(R.id.edt_address1);
        button=(Button)findViewById(R.id.button);
        sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
        str_user_id=sharedPreferences.getString("user_id","");
        str_time=getIntent().getStringExtra("time");
        list=new ArrayList<>();
        list1=new ArrayList<>();
       // tag=getIntent().getStringExtra()
        fetch();
        autoCompleteTextView =(AutoCompleteTextView)findViewById(R.id.search);
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemid = android.R.layout.simple_dropdown_item_1line;
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PickAddress2.this, itemid, list);
                autoCompleteTextView.setAdapter(arrayAdapter);
                autoCompleteTextView.setThreshold(1);
            }
        });
        button.setOnClickListener(this);
    }
    public  void fetch(){
        JsonArrayRequest request = new JsonArrayRequest(JSON,
                new Response.Listener<JSONArray>() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray jsonObject) {
                        try {
                            // ArrayList<String>list=new ArrayList<>();
                            for (int i=0;i<jsonObject.length();i++){
                                JSONObject jsonObject1=jsonObject.getJSONObject(i);
                                list.add(jsonObject1.getString("location_name"));
                                list1.add(jsonObject1.getString("location_id"));
                            }
                        }
                        catch(JSONException e) {
                            Toast.makeText(getApplicationContext(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(PickAddress2.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.button:
                str_address1=edit1.getText().toString();
                str_address2=edit2.getText().toString();
                str_address3=autoCompleteTextView.getText().toString();
                for (int i=0;i<list.size();i++){
                    if (str_address3.equals(list.get(i))){
                        str_address4= list1.get(i);
                        Log.e("id",str_address4);
                        break;
                    }
                }
                if (str_address1.isEmpty()||str_address2.isEmpty()||str_address3.isEmpty()){
                    Toast.makeText(PickAddress2.this,"fields cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    sendRequest1();
                }
        }
    }
    private void sendRequest1() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         progressDialog.dismiss();
                        try {
                            @SuppressWarnings("UnusedAssignment") JSONObject jsonObject = new JSONObject(response);
                            Intent intent=new Intent(PickAddress2.this,Order_section.class);
                            intent.putExtra("address",str_address1 + " " + str_address2);
                            intent.putExtra("time",str_time);
                            intent.putExtra("locality",str_address3);
                            startActivity(intent);
                           // Toast.makeText(PickAddress2.this,jsonObject.getString("Status"),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            String errorMessage = error.getClass().getSimpleName();
                            if (!TextUtils.isEmpty(errorMessage)) {
                                Toast.makeText(PickAddress2.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("address_name",  str_address1);
                params.put("address_details", str_address2);
                params.put("location_locality", str_address4);
                params.put("user_id",str_user_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(PickAddress2.this);
        progressDialog.setMessage("saving please wait....");
        progressDialog.show();
    }
}
