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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rplanx.besstoo.adapter.Locations_adapter;
import com.rplanx.besstoo.application.VolleyApplication;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.getset.Locations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Address_list extends Activity {
    ListView listView;
  //  String ADDRESS_URL="http://besstoo.com/public/service/all_user_location";
    String ADDRESS_URL=Constant.URL +"all_user_location";
    Locations locations;
    Locations_adapter locations_adapter;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String str_user_id;
    AutoCompleteTextView autoCompleteTextView;
   // String JSON="http://besstoo.com/public/service/all_locations";
    String JSON=Constant.URL+"all_locations";
    List<String> list;
    List<String> list1;
    EditText edit1;
    EditText edit2;
    Button button;
    String str_address1;
    String str_address2;
    String str_address3;
    String str_address4;
    String JSON_URI=Constant.URL+"fetch_location";
   // String JSON_URI="http://besstoo.com/public/service/fetch_location";
    String str_time;
    List<Locations>img;
    String str_tag;
    String str_no_of_people;
    String str_date;
    String str_tag1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        listView=(ListView)findViewById(R.id.list_address);
        sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
        str_user_id=sharedPreferences.getString("user_id","");
        str_time=getIntent().getStringExtra("time");
        str_tag=getIntent().getStringExtra("tag");
        str_no_of_people=getIntent().getStringExtra("no_of_people");
        str_date=getIntent().getStringExtra("date");
        if (getIntent().hasExtra("tag1")){
            str_tag1=getIntent().getStringExtra("tag1");
        }
        list=new ArrayList<>();
        list1=new ArrayList<>();
        img=new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (str_tag.equals("menu")){
                    Intent intent=new Intent(Address_list.this,Order_section.class);
                    locations=img.get(i-1);
                    intent.putExtra("time",str_time);
                    intent.putExtra("locality",locations.getLocation_name());
                    intent.putExtra("address",locations.getUser_location_name() + " ," + locations.getLocation_city());
                    intent.putExtra("loc_id",locations.getLocation_id());
                    intent.putExtra("tag",str_tag);
                    intent.putExtra("tag1",str_tag1);
                    intent.putExtra("date",str_date);
                    intent.putExtra("people",str_no_of_people);
                    startActivity(intent);
                }
                if (str_tag.equals("party")){
                    Intent intent=new Intent(Address_list.this,OrderSection1.class);
                    locations=img.get(i-1);
                    intent.putExtra("time",str_time);
                    intent.putExtra("locality",locations.getLocation_name());
                    intent.putExtra("address",locations.getUser_location_name() + " ," + locations.getLocation_city());
                    intent.putExtra("loc_id",locations.getLocation_id());
                    intent.putExtra("tag",str_tag);
                    intent.putExtra("date",str_date);
                    intent.putExtra("tag1",str_tag1);
                    intent.putExtra("people",str_no_of_people);
                    startActivity(intent);
                }
                // intent.putExtra("address",str_address1 + " " + str_address2);
               // intent.putExtra("time",str_time);
                //intent.putExtra("locality",str_address3);
            }
        });
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.single_new_address, listView, false);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Address_list.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_pick_address2);
                dialog.show();
                fetch1();
                edit1=(EditText)dialog.findViewById(R.id.edt_address2);
                edit2=(EditText)dialog.findViewById(R.id.edt_address1);
                button=(Button)dialog.findViewById(R.id.button);
                autoCompleteTextView =(AutoCompleteTextView)dialog.findViewById(R.id.search);
                autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int itemid = android.R.layout.simple_dropdown_item_1line;
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Address_list.this, itemid, list);
                        autoCompleteTextView.setAdapter(arrayAdapter);
                        autoCompleteTextView.setThreshold(1);
                        InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });


                autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        // fetch1();
                        int itemid = android.R.layout.simple_dropdown_item_1line;
                        String[] str_rad = getResources().getStringArray(R.array.location);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Address_list.this, itemid, list);
                        autoCompleteTextView.setAdapter(arrayAdapter);
                       // autoCompleteTextView.setThreshold(1);
                        InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        autoCompleteTextView.showDropDown();
                        return false;

                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                            Toast.makeText(Address_list.this,"fields cannot be empty",Toast.LENGTH_SHORT).show();
                        }
                        else{
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

        listView.addHeaderView(header,null,false);
        fetch();
    }
    private void fetch() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADDRESS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        img.clear();
                        try {
                            JSONArray jsonObject = new JSONArray(response);
                            List<Locations> imageRecords = parse(jsonObject);
                            if (imageRecords.size()>0){
                                img.addAll(imageRecords);
                                locations_adapter=new Locations_adapter(Address_list.this,R.layout.single_list_address,imageRecords);
                                listView.setAdapter(locations_adapter);
                                locations_adapter.notifyDataSetChanged();
                            }
                            else{
                                img.addAll(imageRecords);
                                locations_adapter=new Locations_adapter(Address_list.this,R.layout.single_list_address,imageRecords);
                                listView.setAdapter(locations_adapter);
                                locations_adapter.notifyDataSetChanged();


                            }
                            // Toast.makeText(Login.this,jsonObject.getString("Status"),Toast.LENGTH_LONG).show();
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
                                Toast.makeText(Address_list.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", str_user_id);
                //params.put("password",str_em_password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(Address_list.this);
        progressDialog.setMessage("Loading ....");
        progressDialog.show();


    }

    private List<Locations> parse(JSONArray json) throws JSONException {
        ArrayList<Locations> records = new ArrayList<>();
        try {
            for (int i=0;i<json.length();i++) {
            JSONObject jsonObject = json.getJSONObject(i);
            locations = new Locations();
            locations.setLocation_city(jsonObject.getString("location_city"));
            locations.setLocation_id(jsonObject.getString("user_address_id"));
            locations.setUser_location_details(jsonObject.getString("user_location_details"));
            locations.setUser_location_name(jsonObject.getString("user_location_name"));
            locations.setLocation_name(jsonObject.getString("location_name"));
            records.add(locations);
        }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return  records;
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
                        Toast.makeText(Address_list.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
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
                                fetch();
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
                                Toast.makeText(Address_list.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
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
        progressDialog = new ProgressDialog(Address_list.this);
        progressDialog.setMessage("Saving please wait....");
        progressDialog.show();
    }

}
