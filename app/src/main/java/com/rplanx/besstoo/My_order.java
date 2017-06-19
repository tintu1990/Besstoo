package com.rplanx.besstoo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.rplanx.besstoo.adapter.MyorderAdapter;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.getset.Order_get_set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_order extends Activity {
    ListView listView;
    MyorderAdapter myorderAdapter;
   // String ORDER_URL="http://besstoo.com/service/order";
    String ORDER_URL= Constant.ORDER_URL+"order";
    SharedPreferences sharedPreferences;
    SharedPreferences mealPreference;
    SharedPreferences partyPeference;
    SharedPreferences cakePreference;
    SharedPreferences .Editor editor;
    SharedPreferences.Editor editor1;
    SharedPreferences .Editor editor2;
    String us_id;
    ProgressDialog progressDialog;
    Order_get_set order_get_set;
    RelativeLayout back;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        listView=(ListView)findViewById(R.id.list1);
        back=(RelativeLayout)findViewById(R.id.back_layout);
        partyPeference=getSharedPreferences("Party_pref",Context.MODE_PRIVATE);
        mealPreference=getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);
        cakePreference=getSharedPreferences("MyPREFERENCES1",Context.MODE_PRIVATE);
        sharedPreferences =getSharedPreferences("login", Context.MODE_PRIVATE);
        us_id=sharedPreferences.getString("user_id","");
        if (getIntent().hasExtra("order_sec")){
            flag=1;
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if (getIntent().hasExtra("order_sec")){
                    Intent intent=new Intent(My_order.this,Food_list.class);
                    intent.putExtra("order","order");
                    startActivity(intent);
                }
                */
                //else{
                    onBackPressed();
              //  }
               /* Intent intent=new Intent(My_order.this,Food_list.class);
                intent.putExtra("order","order");
                startActivity(intent);*/


            }
        });
        sendRequest1();
       // fetch();
    }

   /* private void fetch() {
        JsonArrayRequest request = new JsonArrayRequest(ORDER_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonObject) {
                        try {
                            List<Order_get_set> imageRecords = parse(jsonObject);
                            myorderAdapter=new MyorderAdapter(My_order.this,R.layout.single_order,imageRecords);
                            listView.setAdapter(myorderAdapter);
                            myorderAdapter.notifyDataSetChanged();
                        }
                        catch(JSONException e) {
                            Toast.makeText(My_order.this, "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(My_order.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }



                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }
*/


    private void sendRequest1() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonObject = new JSONArray(response);
                            List<Order_get_set> imageRecords = parse(jsonObject);
                            if (imageRecords.size()>0){
                                SharedPreferences shref;
                                SharedPreferences.Editor editor;
                                shref = getSharedPreferences("Discount_info", Context.MODE_PRIVATE);
                                Gson gson = new Gson();
                                String json = gson.toJson(imageRecords);
                                editor=shref.edit();
                                editor.putString("myJson", json);
                                editor.apply();
                                myorderAdapter=new MyorderAdapter(My_order.this,R.layout.single_order,imageRecords);
                                listView.setAdapter(myorderAdapter);
                                myorderAdapter.notifyDataSetChanged();
                            }
                            else{
                               // onBackPressed();
                                Toast.makeText(My_order.this,"You have no orders",Toast.LENGTH_LONG).show();
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
                                Toast.makeText(My_order.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", us_id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(My_order.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    private List<Order_get_set> parse(JSONArray json) throws JSONException {
        ArrayList<Order_get_set> records = new ArrayList<>();
        ArrayList<Order_get_set> records1;
        for (int k=0;k<json.length();k++){
            JSONObject jsonObject=json.getJSONObject(k);
            order_get_set=new Order_get_set();
            order_get_set.setTotal_amt(jsonObject.getString("user_total_payment_amount"));
            order_get_set.setContact(jsonObject.getString("contact_number"));
            order_get_set.setCancel_at(jsonObject.getString("cancel_at"));
           // order_get_set.setMenu_name(jsonObject.getString("order_type"));
            order_get_set.setDelevery_boy_bike_details(jsonObject.getString("delivaryBoy_bike_details"));
            order_get_set.setDelevery_boy_image(jsonObject.getString("delivaryBoy_path"));
            order_get_set.setDelevery_boy_name(jsonObject.getString("delivaryBoy_name"));
            order_get_set.setLocation_city(jsonObject.getString("location_city"));
            order_get_set.setLocation_name(jsonObject.getString("location_name"));
            order_get_set.setImg_logo("http://besstoo.com/public/images/amar_besstoo.jpg");
            JSONArray jsonArray=jsonObject.getJSONArray("individual");
            records1=new ArrayList<>();
            for (int v=0;v<jsonArray.length();v++){
                JSONObject jsonObject2=jsonArray.getJSONObject(v);
                Order_get_set order_get_set1=new Order_get_set();
                order_get_set1.setMenu_name(jsonObject2.getString("besstookitchen_menu_name"));
                order_get_set1.setQty(jsonObject2.getString("user_individual_kitchen_quantity"));
                order_get_set1.setAmount(jsonObject2.getString("besstookitchen_amount"));
                records1.add(order_get_set1);
            }
            order_get_set.setOrder_get_sets(records1);
            JSONObject jsonObject1=jsonObject.getJSONObject("extra");
            order_get_set.setDiscount(jsonObject.getString("discount"));
            order_get_set.setService_tax(jsonObject1.getString("extra_payment_service_tax"));
            order_get_set.setDelivary_cost(jsonObject1.getString("extra_payment_delivary_charge"));
            order_get_set.setOrder_type(jsonObject.getString("order_type"));
            order_get_set.setOrder_date(jsonObject.getString("user_delivary_date"));
            order_get_set.setOrder_time(jsonObject.getString("order_time"));
            order_get_set.setRefund(jsonObject.getString("refund"));
            order_get_set.setStatus(jsonObject.getString("status"));
            order_get_set.setOrder_id(jsonObject.getString("order_id"));
            order_get_set.setStr_user_id(jsonObject.getString("user_id"));
            order_get_set.setUser_order_otp(jsonObject.getString("user_order_otp"));
            order_get_set.setUser_payment_status(jsonObject.getString("user_payment_status"));
            order_get_set.setUser_payment_mode(jsonObject.getString("user_payment_mode"));
            order_get_set.setOrder_person(jsonObject.getString("order_persons"));
            order_get_set.setUser_slot(jsonObject.getString("user_slot"));
            records.add(order_get_set);
        }
        return records;
    }
    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra("order_sec")){
            Intent intent = new Intent(My_order.this,Food_list.class);
            intent.putExtra("order","order");
            startActivity(intent);
            editor=mealPreference.edit();
            editor.clear().apply();
            editor1=cakePreference.edit();
            editor1.clear().apply();
            editor2=partyPeference.edit();
            editor2.clear().apply();
            finish();
        }
        else{
            super.onBackPressed();
        }
    }
}
