package com.rplanx.besstoo;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rplanx.besstoo.application.VolleyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PickAddress1 extends AppCompatActivity {
    String JSON="http://192.168.3.8/besstoo/public/service/all_locations";
   /* String  JSON1="http://192.168.3.8/besstoo/public/service/fetch_location";
    String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};
    AutoCompleteTextView autoCompleteTextView;*/
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_address1);
        list=new ArrayList<>();
        fetch();

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
                        Toast.makeText(PickAddress1.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    }







