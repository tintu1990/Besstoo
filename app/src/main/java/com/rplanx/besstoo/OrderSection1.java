package com.rplanx.besstoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.rplanx.besstoo.adapter.MyRecycleAdapter1;
import com.rplanx.besstoo.adapter.MyRecycleAdapter2;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.getset.Party;
import com.rplanx.besstoo.interfaces.ModelDataSet;
import com.rplanx.besstoo.interfaces.PartyButtonListener;
import com.rplanx.besstoo.interfaces.PartyButtonListener1;
import com.rplanx.besstoo.interfaces.PartyDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSection1 extends Activity implements PaymentResultWithDataListener {
    TextView date;
    TextView time;
    TextView address;
    TextView locality;
    RecyclerView listView;
    MyRecycleAdapter1 myRecyclerAdapter;
    TextView txt_sub_total;
    TextView txt_delevery_cost;
    TextView txt_discount;
    TextView txt_vat;
    int r;
    TextView txt_total_cost;
    String amt;
    private LinearLayoutManager mLinearLayoutManager;
    private static final String TAG = Order_section.class.getSimpleName();
    ImageView besstoo_cash;
    ImageView online;
    ImageView cod;
    ImageView order;
    int flag=0;
    int flag1=0;
    int flag2=0;
    String payment ="";
    String r1;
    double r2;
    SharedPreferences.Editor editor;
    RelativeLayout relativeLayout;
    String str_tot;
    //Products products;
    boolean networkerror;
    ProgressDialog pDialog;
    String str_r_payment_id;
    String str_mode_of_payment;
    String str_delevery_date;
    String str_time;
    //String JSON_RAZOR="http://besstoo.com/public/service/save_success_data";
    String JSON_RAZOR= Constant.URL+"save_success_data";
    String str_location_id;
    String str_no_of_person="1";
    String str_page;
    String str_razor_signature;
    String str_order_id;
    static List<Party> newProductList;
    Party_product party_product;
    SharedPreferences sharedPreferences;
    List<Party> list;
    List<Model2> model2_list;
    static List<Model2> newModelProductList;
    String str_people;
    SharedPreferences sharedPreferences1;
    String str_discount;
    String str_tax;
    String str_delevery_charge;
    SharedPreferences log_in_pref;
    String str_email;
    String str_cell_phone;
    String str_us_id;
    String str_disc;
    String str_user_status;
    String str_user_pay_status;
    double discount1;
    EditText coupon_code;
    Button apply;
    String code;
    Model_product model_product;
   // String JSON_COUPON_CODE="http://besstoo.com/service/coupon_code";
    String JSON_COUPON_CODE=Constant.ORDER_URL+"coupon_code";
    ProgressDialog progressDialog;
    MyRecycleAdapter2 myRecycleAdapter2;
    String JSON_CHECK_QUANTITY=Constant.ORDER_URL+"check_quantity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_section1);
        date=(TextView) findViewById(R.id.txt_date);
        time=(TextView)findViewById(R.id.txt_time);
        address=(TextView)findViewById(R.id.txt_address);
        locality=(TextView)findViewById(R.id.txt_locality);
        listView=(RecyclerView) findViewById(R.id.list_cart);
        txt_sub_total=(TextView)findViewById(R.id.txt_subtotal);
        txt_delevery_cost=(TextView)findViewById(R.id.txt_delevery_cost);
        txt_discount=(TextView)findViewById(R.id.txt_discount);
        txt_vat=(TextView)findViewById(R.id.txtvat);
        besstoo_cash=(ImageView)findViewById(R.id.besstoo_cash);
   //     cod=(ImageView)findViewById(R.id.cash_on_del);
      //  cod.setVisibility(View.GONE);
        online=(ImageView)findViewById(R.id.online);
        order=(ImageView)findViewById(R.id.order_btn);
       // coupon_code=(EditText)findViewById(R.id.drop_coupon);
       // apply=(Button)findViewById(R.id.apply);
        newModelProductList=new ArrayList<>();
        newProductList=new ArrayList<>();
        str_location_id=getIntent().getStringExtra("loc_id");
        online=(ImageView)findViewById(R.id.online);
        str_people=getIntent().getStringExtra("people");
        relativeLayout=(RelativeLayout)findViewById(R.id.back_layout);
        log_in_pref = getSharedPreferences("login",Context.MODE_PRIVATE);
        str_email=log_in_pref.getString("email1","");
        str_cell_phone=log_in_pref.getString("mobile","");
        str_us_id=log_in_pref.getString("user_id","");
        sharedPreferences1=getSharedPreferences("Discount_info", Context.MODE_PRIVATE);
       // if (sharedPreferences1.contains("myJson")){
            str_discount="0";
            str_tax="0";
            str_delevery_charge="0";
      //  }
        /*else{
            str_discount="15";
            str_tax="0";
            str_delevery_charge="0";

        }*/
        coupon_code=(EditText)findViewById(R.id.drop_coupon);
        apply=(Button)findViewById(R.id.apply);
        str_page=getIntent().getStringExtra("tag");
        discount1=Double.parseDouble(str_discount);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent =new Intent(Order_section.this,Food_list.class);
                //  startActivity(intent);
                onBackPressed();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code=coupon_code.getText().toString();
                if (code.isEmpty()){
                    Toast.makeText(getApplicationContext(),"You have to put the code",Toast.LENGTH_SHORT).show();
                }
                else{
                    send();
                }

            }
        });

       /* besstoo_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag1==0 && flag2==0) {
                    if (flag == 0) {
                        besstoo_cash.setImageResource(R.drawable.besstoo_cash_actv);
                        flag = 1;
                        payment = "besstoo cash";
                    } else {
                        besstoo_cash.setImageResource(R.drawable.besstoo_cash_non);
                        flag = 0;
                    }
                }
            }
        });*/

        online.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          if (flag1 == 0) {
                                                  online.setImageResource(R.drawable.online_actv);
                                                  flag1 = 1;
                                                  payment = "online";
                                              } else {
                                                  online.setImageResource(R.drawable.online_non);
                                                  flag1 = 0;
                                                  payment="";
                                              }
                                          }

                                  }
        );

        /*cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag==0 && flag1==0) {
                    if (flag2 == 0) {
                        payment = "cod";
                        cod.setImageResource(R.drawable.cod_actv);
                        flag2 = 1;
                    } else {
                        flag2 = 0;
                        cod.setImageResource(R.drawable.cod_non);
                    }
                }
            }
        });*/

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payment.equals("")){
                    Toast.makeText(OrderSection1.this,"you have to select payment option",Toast.LENGTH_LONG).show();
                }
                else if (payment.equals("online")){
                    str_user_pay_status="1";
                    str_mode_of_payment="3";
                    str_user_status="1";
                    checkQuantity();
                    //startPayment();
                }
                /*else if (payment.equals("cod")){
                    str_user_pay_status="0";
                    str_user_status="1";
                    str_mode_of_payment="2";
                    str_r_payment_id="pay_7nHw44reSBluPu";
                    startPay();

                }
                else if(payment.equals("besstoo cash")){
                    Toast.makeText(OrderSection1.this,"besstoo cash is not available now",Toast.LENGTH_SHORT).show();
                }*/


            }
        });

        cod=(ImageView)findViewById(R.id.cash_on_del);
        order=(ImageView)findViewById(R.id.order_btn);
        txt_total_cost=(TextView)findViewById(R.id.txt_total);
        listView.setHasFixedSize(true);
        //list=new ArrayList<>();
        sharedPreferences=getSharedPreferences("Party_pref", Context.MODE_PRIVATE);
        time.setText(getIntent().getStringExtra("time"));
        str_time=getIntent().getStringExtra("time");
        address.setText(getIntent().getStringExtra("address"));
        locality.setText(getIntent().getStringExtra("locality"));
        date.setText(getIntent().getStringExtra("date"));
        str_delevery_date=getIntent().getStringExtra("date");


        try {
            model2_list=new ArrayList<>();
            model2_list.clear();
            Gson gson1=new Gson();
            String response1 = sharedPreferences.getString("myJson", "");
            ArrayList<Model2> lstArrayList1 = gson1.fromJson(response1,
                    new TypeToken<List<Model2>>() {
                    }.getType());
            model2_list.addAll(lstArrayList1);
            if (model2_list.size()>0){
                model_product=new Model_product(model2_list,OrderSection1.this);
                newModelProductList.addAll(model2_list);
                myRecycleAdapter2=new MyRecycleAdapter2(model_product,partyButtonListener1,model2_list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_people,str_delevery_charge,str_tax,str_discount);
                listView.setLayoutManager(new LinearLayoutManager(OrderSection1.this));
                listView.setAdapter(myRecycleAdapter2);
                myRecyclerAdapter.notifyDataSetChanged();
            }


        }
        catch (java.lang.NullPointerException e){
            e.printStackTrace();
        }

        /*try {
            list = new ArrayList<Party>();
            list.clear();

            Gson gson1 = new Gson();
            String response1 = sharedPreferences.getString("myJson", "");
            ArrayList<Party> lstArrayList1 = gson1.fromJson(response1,
                    new TypeToken<List<Party>>() {
                    }.getType());
            list.addAll(lstArrayList1);

            if (list.size() > 0)
            {
                party_product=new Party_product(list,OrderSection1.this);
                newProductList.addAll(list);
                //orders_adapter=new Orders_adapter(Order_section.this,R.layout.order_cart_list_item,list);
                myRecyclerAdapter=new MyRecycleAdapter1(party_product,partyButtonListener,list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_people,str_delevery_charge,str_tax,str_discount);
                listView.setLayoutManager(new LinearLayoutManager(OrderSection1.this));
                listView.setAdapter(myRecyclerAdapter);
                myRecyclerAdapter.notifyDataSetChanged();

            }
        }catch (java.lang.NullPointerException e){
            e.printStackTrace();
        }*/
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try {
            //Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();
            editor=sharedPreferences.edit();
            editor.clear().apply();
            //editor1=shref.edit();
          //  editor1.clear().apply();
            str_r_payment_id=s;
            Log.e("payment success", s);
            Log.e("pa_id",paymentData.getPaymentId());
            //  Log.e("order_id",paymentData.getOrderId());
            // Log.e("payment signature",paymentData.getSignature());
            //new SaveOrders().execute();
            sendRequest1();
        }catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentsuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
           // Toast.makeText(this, "Payment failed: " + Integer.toString(i) + " " + s , Toast.LENGTH_SHORT).show();
            Log.e("payment error",Integer.toString(i) + " " + s);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    private  static  class Party_product implements PartyDataSet {
        private final List<Party> productList;
        Context context;

        private Party_product(List<Party> productList, Context context) {
            this.productList =productList;
            this.context= context;
        }

        @Override
        public int size() {
            return productList.size();
        }

        @Override
        public Party get(int position) {
            return productList.get(position);
        }

        @Override
        public long getId(int position) {
            return position;
        }

        public void removeOneFrom(Party product) {
        Party model=product;
            int i = productList.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            model.setAmount(String.valueOf(Integer.parseInt(model.getAmount())/10));
            model.setBesstoo_kitchen_id(model.getBesstoo_kitchen_id());
            model.setDate(model.getDate());
            model.setMenu_name(model.getMenu_name());
            model.setPartyImage(model.getPartyImage());
            model.setMenu_id(model.getMenu_id());
            model.setStr_id(model.getStr_id());
            model.setNo_of_item(String.valueOf(Integer.parseInt(model.getAmount())-10));
            model.setQuantity(model.getQuantity());
            productList.remove(product);
            productList.add(i, model);
            newProductList.addAll(productList);

        }

        public void addOneTo(Party product) {
            Party model=product;
            int i = productList.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            model.setAmount(String.valueOf(Integer.parseInt(model.getAmount())*10));
            model.setBesstoo_kitchen_id(model.getBesstoo_kitchen_id());
            model.setDate(model.getDate());
            model.setMenu_name(model.getMenu_name());
            model.setPartyImage(model.getPartyImage());
            model.setMenu_id(model.getMenu_id());
            model.setStr_id(model.getStr_id());
            model.setNo_of_item(String.valueOf(Integer.parseInt(model.getAmount()) +10));
            model.setQuantity(model.getQuantity());
            productList.remove(product);
            productList.add(i, model);
            newProductList.addAll(productList);
           // model.setAmount(String.valueOf(model.getAmount()*10));

        }
    }

    private  final PartyButtonListener1 partyButtonListener1 =new PartyButtonListener1(){

        @Override
        public void onMinusClick(Model2 product, String string) {

        }

        @Override
        public void onPlusClick(Model2 product, String string) {

        }
    };
    private  final PartyButtonListener partyButtonListener =new PartyButtonListener() {

        @Override
        public void onMinusClick(Party product, String string) {
            party_product.removeOneFrom(product);
            myRecyclerAdapter.notifyDataSetChanged();

        }

        @Override
        public void onPlusClick(Party product, String string) {
            party_product.addOneTo(product);
            myRecyclerAdapter.notifyDataSetChanged();
        }
    };
    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        str_tot=txt_total_cost.getText().toString();
        str_tot=str_tot.replace(getResources().getString(R.string.Rs),"");
        str_tot=str_tot.replace(" ","");
        r2= Double.parseDouble(str_tot);
        r1=String.valueOf(100*r2);
        double dic= r2*discount1/100;
        str_disc=String.valueOf(dic);
        Log.e("disc",str_disc);
        // r1=Integer.parseInt(txt_total_cost.getText().toString())*100;


        /**
         * Set your logo here
         */
        // checkout.setImage(R.mipmap.ic_launcher);
        checkout.setFullScreenDisable(true);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();
            /**
             * Merchant Name
             * eg: Rentomojo || HasGeek etc.
             */
            options.put("name", "Merchant Name");
            options.put("image", "http://besstoo.com/public/frontend/images/logo.png");
            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Order #123456");

            options.put("currency", "INR");
            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            JSONObject preFill = new JSONObject();
            preFill.put("email", str_email);
            preFill.put("contact", str_cell_phone);

            options.put("prefill", preFill);

            JSONObject theme =new JSONObject();
            theme.put("color","#B22222");
            options.put("theme",theme);
            options.put("amount", r1);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }
    private void sendRequest1(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_RAZOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.getString("Status").equals("Success")){
                                Toast.makeText(OrderSection1.this,"Order Successfully Placed",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OrderSection1.this,My_order.class);
                                intent.putExtra("order_sec","order");
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(OrderSection1.this,jsonObject.getString("Status"),Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(OrderSection1.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                JSONArray jsonArray=new JSONArray();
                for (int i=0;i<newModelProductList.size();i++){
                    try {
                        JSONObject jsonObject = new JSONObject();
                        //if (newProductList.get(i).getQuantity()>0){
                            jsonObject.put("id",newModelProductList.get(i).getId());
                            jsonObject.put("qty",newModelProductList.get(i).getQuantity());
                            jsonArray.put(i,jsonObject);
                       // }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String data= jsonArray.toString();
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", str_us_id);
                params.put("user_razor_payment_id",str_r_payment_id);
                params.put("payment_mode",str_mode_of_payment);
                params.put("all_products",data);
                params.put("total",str_tot);
                params.put("order_id",str_order_id);
                params.put("user_payment_status",str_user_pay_status);
                params.put("user_slot",str_time);
                params.put("user_delivary_date",str_delevery_date);
                params.put("user_location_id",str_location_id);
                params.put("number_of_person",str_no_of_person);
                params.put("order_type",str_page);
                params.put("status",str_user_status);
                params.put("discount",str_disc);
                params.put("discount_code",str_discount);
                params.put("refund","0");
               /* params.put("razorpay_payment_id",str_r_payment_id);
                params.put("mode_of_payment",str_mode_of_payment);
                params.put("scheduled_time",str_time);
                params.put("delivary_date",str_delevery_date);
                params.put("location_id",str_location_id);
                params.put("no_of_person",str_no_of_person);
                params.put("page",str_page);*/
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(OrderSection1.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

   /*  progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Logging please wait....");
        progressDialog.show();*/
    }


    public  void startPay(){
        str_tot=txt_total_cost.getText().toString();
        str_tot=str_tot.replace(getResources().getString(R.string.Rs),"");
        str_tot=str_tot.replace(" ","");
       /* double discount1=Double.parseDouble(str_discount);
        double discount2=(discount1/100.0f)*r1;*/
        r2= Double.parseDouble(str_tot);
        double dic= r2*discount1/100;
        str_disc=String.valueOf(dic);
        sendRequest1();
    }
    private  void send(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_COUPON_CODE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // jsonObject= null;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Status").equals("BS15P"))
                            {
                                Toast.makeText(getApplicationContext(),"coupon applied successfully",Toast.LENGTH_SHORT).show();
                                str_discount="15";
                                discount1=Double.parseDouble(str_discount);
                            }

                            else if(jsonObject.getString("Status").equals("BES10RB")){
                                Toast.makeText(getApplicationContext(),"coupon applied successfully",Toast.LENGTH_SHORT).show();
                                str_discount="10";
                                discount1=Double.parseDouble(str_discount);
                            }
                            else if(jsonObject.getString("Status").equals("Failure")){
                                str_discount="0";
                                discount1=Double.parseDouble(str_discount);
                                Toast.makeText(OrderSection1.this,"Your Coupon code is not valid",Toast.LENGTH_SHORT).show();
                            }
                            /* try {
                                list = new ArrayList<Party>();
                                list.clear();

                                Gson gson1 = new Gson();
                                String response1 = sharedPreferences.getString("myJson", "");
                                ArrayList<Party> lstArrayList1 = gson1.fromJson(response1,
                                        new TypeToken<List<Party>>() {
                                        }.getType());
                                list.addAll(lstArrayList1);

                                if (list.size() > 0)
                                {
                                    party_product=new Party_product(list,OrderSection1.this);
                                    //orders_adapter=new Orders_adapter(Order_section.this,R.layout.order_cart_list_item,list);
                                    myRecyclerAdapter=new MyRecycleAdapter1(party_product,partyButtonListener,list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_people,str_delevery_charge,str_tax,str_discount);
                                    listView.setLayoutManager(new LinearLayoutManager(OrderSection1.this));
                                    listView.setAdapter(myRecyclerAdapter);
                                    myRecyclerAdapter.notifyDataSetChanged();

                                }
                            }catch (java.lang.NullPointerException e){
                                e.printStackTrace();
                            }*/

                            try {
                                model2_list=new ArrayList<>();
                                model2_list.clear();
                                Gson gson1=new Gson();
                                String response1 = sharedPreferences.getString("myJson", "");
                                ArrayList<Model2> lstArrayList1 = gson1.fromJson(response1,
                                        new TypeToken<List<Model2>>() {
                                        }.getType());
                                model2_list.addAll(lstArrayList1);
                                if (model2_list.size()>0){
                                    model_product=new Model_product(model2_list,OrderSection1.this);
                                    newModelProductList.clear();
                                    newModelProductList.addAll(model2_list);
                                    myRecycleAdapter2=new MyRecycleAdapter2(model_product,partyButtonListener1,model2_list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_people,str_delevery_charge,str_tax,str_discount);
                                    listView.setLayoutManager(new LinearLayoutManager(OrderSection1.this));
                                    listView.setAdapter(myRecycleAdapter2);
                                    myRecyclerAdapter.notifyDataSetChanged();
                                }

                            }
                            catch (java.lang.NullPointerException e){
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            String errorMessage = error.getClass().getSimpleName();
                            if (!TextUtils.isEmpty(errorMessage)) {
                                Toast.makeText(OrderSection1.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", str_us_id);
                params.put("coupon_code", code);
                //    params.put("discount",)
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private  static class Model_product implements ModelDataSet {
        private final List<Model2> productLists;
        Context context;

        private Model_product(List<Model2> productLists,Context context) {
            this.productLists = productLists;
            this.context=context;
        }

        @Override
        public int size() {
            return productLists.size();
        }

        @Override
        public Model2 get(int position) {
            return productLists.get(position);
        }

        @Override
        public long getId(int position) {
            return position;
        }
    }
   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this,OrderSection1.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/


    private  void  checkQuantity(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_RAZOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.getString("Status").equals("Success")){
                                startPayment();
                            }
                            else{
                                outOfStock(jsonObject.getString("Status"));

                               // Toast.makeText(OrderSection1.this,jsonObject.getString("Status"),Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(OrderSection1.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                JSONArray jsonArray=new JSONArray();
                for (int i=0;i<newModelProductList.size();i++){
                    try {

                        JSONObject jsonObject = new JSONObject();
                        //if (newProductList.get(i).getQuantity()>0){
                        jsonObject.put("id",newModelProductList.get(i).getId());
                        jsonObject.put("qty",newModelProductList.get(i).getQuantity());
                        jsonArray.put(i,jsonObject);
                        // }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String data= jsonArray.toString();
                Map<String, String> params = new HashMap<String, String>();
                params.put("all_products",data);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(OrderSection1.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }



    private  void  outOfStock(String message){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
        // alertDialog.setTitle(getResources().getString(R.string.app_name));
        TextView image = new TextView(this);
        image.setGravity(Gravity.CENTER|Gravity.BOTTOM);
        // image.setImageResource(R.drawable.besstoo_pop);
        image.setTextSize(15);
        image.setTextColor(Color.RED);
        image.setBackgroundResource(R.drawable.besstoo_pop);
        image.setText(message);
        alertDialog.setView(image);
        //alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.mipmap.besstoo_launcher);
        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }


}
