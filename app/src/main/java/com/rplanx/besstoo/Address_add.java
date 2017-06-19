package com.rplanx.besstoo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soumya on 22/4/17.
 */
public class Address_add extends Activity {
    CardView cardView;
    List<String> list;
    List<String> list1;
    EditText edit1;
    EditText edit2;
    Button button;
    String str_address1;
    String str_address2;
    String str_address3;
    SharedPreferences sharedPreferences;
    String str_address4;
    ProgressDialog progressDialog;
    String str_user_id;
    String JSON_URI="http://besstoo.com/public/service/fetch_location";
    String JSON="http://besstoo.com/public/service/all_locations";
    AutoCompleteTextView autoCompleteTextView;
    String str_time;
    String str_tag;
    String str_date;
    String str_no_of_people;
    String str_tag1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_new_address);
        cardView = (CardView) findViewById(R.id.card_view);
        sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
        str_user_id=sharedPreferences.getString("user_id","");

        if (getIntent().hasExtra("time")){
            str_time=getIntent().getStringExtra("time");
        }
        if (getIntent().hasExtra("tag")){
            str_tag=getIntent().getStringExtra("tag");
        }

        if (getIntent().hasExtra("date")){
            str_date=getIntent().getStringExtra("date");
        }
        if (getIntent().hasExtra("people")){
            str_no_of_people=getIntent().getStringExtra("people");
        }
        if (getIntent().hasExtra("tag1")){
            str_tag1=getIntent().getStringExtra("tag1");
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Address_add.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_pick_address2);
                dialog.show();
                fetch1();
                edit1 = (EditText) dialog.findViewById(R.id.edt_address2);
                edit2 = (EditText) dialog.findViewById(R.id.edt_address1);
                button = (Button) dialog.findViewById(R.id.button);
                autoCompleteTextView = (AutoCompleteTextView) dialog.findViewById(R.id.search);
                autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int itemid = android.R.layout.simple_dropdown_item_1line;
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Address_add.this, itemid, list);
                        autoCompleteTextView.setAdapter(arrayAdapter);
                        autoCompleteTextView.setThreshold(1);
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        str_address1 = edit1.getText().toString();
                        str_address2 = edit2.getText().toString();
                        str_address3 = autoCompleteTextView.getText().toString();
                        for (int i = 0; i < list.size(); i++) {
                            if (str_address3.equals(list.get(i))) {
                                str_address4 = list1.get(i);
                                Log.e("id", str_address4);
                                break;
                            }
                        }
                        if (str_address1.isEmpty() || str_address2.isEmpty() || str_address3.isEmpty()) {
                            Toast.makeText(Address_add.this, "fields cannot be empty", Toast.LENGTH_SHORT).show();
                        } else {
                            sendRequest1();
                            dialog.dismiss();
                        }
                    }
                });
                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
            }
        });
    }


    public  void fetch1(){
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
                        Toast.makeText(Address_add.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        VolleyApplication.getInstance().getRequestQueue().add(request);
    }


    private void sendRequest1() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("Status").equals("Success")){
                                //fetch();
                                Intent intent =new Intent(Address_add.this,Address_list.class);
                                intent.putExtra("time",str_time);
                                intent.putExtra("tag",str_tag);
                                intent.putExtra("date",str_date);
                                intent.putExtra("people",str_no_of_people);
                                intent.putExtra("tag1",str_tag1);
                                startActivity(intent);
                            }

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
                                Toast.makeText(Address_add.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
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
        progressDialog = new ProgressDialog(Address_add.this);
        progressDialog.setMessage("Saving please wait....");
        progressDialog.show();
    }


}


