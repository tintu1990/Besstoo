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
import com.rplanx.besstoo.adapter.MyRecyclerAdapter;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.interfaces.CustomButtonListener;
import com.rplanx.besstoo.interfaces.ModelDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@SuppressWarnings({"ALL", "StatementWithEmptyBody"})
public class Order_section extends Activity implements PaymentResultWithDataListener {
    TextView date;
    TextView time;
    TextView address;
    TextView locality;
    RecyclerView listView;
    SharedPreferences shref;
    SharedPreferences shr;
    ArrayList<Model2> list;
   // Orders_adapter orders_adapter;
    MyRecyclerAdapter myRecyclerAdapter;
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
    SharedPreferences .Editor editor;
    SharedPreferences .Editor editor1;
    RelativeLayout relativeLayout;
    String str_tot;
    Products products;
    boolean networkerror;
    ProgressDialog pDialog;
    String str_r_payment_id;
    String str_mode_of_payment;
    String str_delevery_date;
    String str_time;
   // String JSON_RAZOR="http://besstoo.com/public/service/save_success_data";
    String JSON_RAZOR= Constant.URL+"save_success_data";
    String str_location_id;
    String str_no_of_person="1";
    String str_page;
    String str_razor_signature;
    String str_order_id;
    static List<Model2> newProductList;
    ProgressDialog progressDialog;
    SharedPreferences shp;
    SharedPreferences sharedPreferences;
    String str_us_id;
    String str_extra;
    String str_delevery_charge="0";
    String str_discount="0";
    String str_tax="0";
    String JSON_URI=Constant.ORDER_URL+"order";
   // String JSON_URI="http://besstoo.com/service/order";
    Bundle bundle;
    String formattedDate;
    SharedPreferences sharedPreferences1;
    SharedPreferences log_in_pref;
    String str_tag1;
    String str_cell_phone;
    String str_user_pay_status;
    String str_email;
    String str_user_status;
    String str_disc;
    double discount1;
    EditText coupon_code;
    Button apply;
    String code;
    String str_time_to_stop_order="15:00:00";
    String str_for_next_day="16:00:00";
    int FLAG_DAY=0;
    String str_discount_code;
    static List<Model2> newProductList1;
    // String JSON_COUPON_CODE="http://besstoo.com/service/coupon_code";
    String JSON_COUPON_CODE=Constant.ORDER_URL+"coupon_code";
    String JSON_CHECK_QUANTITY=Constant.ORDER_URL+"check_quantity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_section);
        date=(TextView) findViewById(R.id.txt_date);
        time=(TextView)findViewById(R.id.txt_time);
        address=(TextView)findViewById(R.id.txt_address);
        locality=(TextView)findViewById(R.id.txt_locality);
        listView=(RecyclerView) findViewById(R.id.list_cart);
        txt_sub_total=(TextView)findViewById(R.id.txt_subtotal);
        txt_delevery_cost=(TextView)findViewById(R.id.txt_delevery_cost);
        txt_discount=(TextView)findViewById(R.id.txt_discount);
        txt_vat=(TextView)findViewById(R.id.txtvat);
        coupon_code=(EditText)findViewById(R.id.drop_coupon);
        apply=(Button)findViewById(R.id.apply);
        besstoo_cash=(ImageView)findViewById(R.id.besstoo_cash);
        shp=getSharedPreferences("login",Context.MODE_PRIVATE);
        newProductList=new ArrayList<Model2>();
        str_location_id=getIntent().getStringExtra("loc_id");
        str_us_id=shp.getString("user_id","");
        newProductList1=new ArrayList<>();
        online=(ImageView)findViewById(R.id.online);
        relativeLayout=(RelativeLayout)findViewById(R.id.back_layout);
        Checkout.preload(getApplicationContext());
        sharedPreferences1=getSharedPreferences("Discount_info", Context.MODE_PRIVATE);
        log_in_pref = getSharedPreferences("login",Context.MODE_PRIVATE);
        str_email=log_in_pref.getString("email1","");
        str_cell_phone=log_in_pref.getString("mobile","");
        str_page=getIntent().getStringExtra("tag");

        Calendar to_date = Calendar.getInstance();

        if(to_date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            FLAG_DAY=1;
            System.out.println("OK");
        }
        else {
            FLAG_DAY=0;
        }
        /* if (sharedPreferences1.contains("myJson")){
            str_discount="0";
            str_tax="0";
            str_delevery_charge="0";

        }
        else{
            str_discount="15";
            str_tax="0";
            str_delevery_charge="0";

        }*/
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

         discount1=Double.parseDouble(str_discount);

        str_tag1=getIntent().getStringExtra("tag1");
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent =new Intent(Order_section.this,Food_list.class);
              //  startActivity(intent);
                onBackPressed();
            }
        });
        cod=(ImageView)findViewById(R.id.cash_on_del);
        order=(ImageView)findViewById(R.id.order_btn);
        txt_total_cost=(TextView)findViewById(R.id.txt_total);
        //sendRequest12();
        listView.setHasFixedSize(true);
        list=new ArrayList<>();
        sharedPreferences=getSharedPreferences("NewPref",Context.MODE_PRIVATE);
        shref=getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        shr=getSharedPreferences("MyPREFERENCES1",Context.MODE_PRIVATE);
        sharedPreferences=getSharedPreferences("NewPref",Context.MODE_PRIVATE);

        // mLinearLayoutManager = new LinearLayoutManager(getActivity());
      //  mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
       /* Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
         formattedDate = df.format(c.getTime());*/

       // RazorpayClient razorpay = new RazorpayClient("<api_key>", "<api_secret>");

        if (getIntent().hasExtra("date")){
             formattedDate=getIntent().getStringExtra("date");
        }
        date.setText(formattedDate);
        str_delevery_date=formattedDate;
        time.setText(getIntent().getStringExtra("time"));
        str_time=getIntent().getStringExtra("time");
        address.setText(getIntent().getStringExtra("address"));
        str_no_of_person=getIntent().getStringExtra("people");
        locality.setText(getIntent().getStringExtra("locality"));


        try {
            if (str_tag1.equals("menu")){
                list = new ArrayList<Model2>();
                list.clear();
                Gson gson = new Gson();
                String response = shref.getString("myJson", "");
                ArrayList<Model2> lstArrayList = gson.fromJson(response,
                        new TypeToken<List<Model2>>() {
                        }.getType());
                list.addAll(lstArrayList);

                /*if (list.size() > 0) {
                    newProductList.addAll(list);
                    products=new Products(list,Order_section.this);
                    myRecyclerAdapter=new MyRecyclerAdapter(products,productClickListener,list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_delevery_charge,str_tax,str_discount);
                    listView.setLayoutManager(new LinearLayoutManager(Order_section.this));
                    listView.setAdapter(myRecyclerAdapter);
                    myRecyclerAdapter.notifyDataSetChanged();

                } else {
                    listView.setAdapter(null);
                    // cartAdapter.notifyDataSetChanged();
                }*/
            }
            else if (str_tag1.equals("bake")){
                list = new ArrayList<Model2>();
                list.clear();
                Gson gson1 = new Gson();
                String response1 = shr.getString("myJson", "");
                ArrayList<Model2> lstArrayList1 = gson1.fromJson(response1,
                        new TypeToken<List<Model2>>() {
                        }.getType());
                list.addAll(lstArrayList1);
                str_page="cakes";
            }

           /* if (list.size() > 0) {
                newProductList.addAll(list);
                products=new Products(list,Order_section.this);
                myRecyclerAdapter=new MyRecyclerAdapter(products,productClickListener,list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_delevery_charge,str_tax,str_discount);
                listView.setLayoutManager(new LinearLayoutManager(Order_section.this));
                listView.setAdapter(myRecyclerAdapter);
                myRecyclerAdapter.notifyDataSetChanged();

            } else {
                listView.setAdapter(null);
                // cartAdapter.notifyDataSetChanged();
            }*/
            if (list.size() > 0) {
                newProductList.addAll(list);
                products=new Products(list,Order_section.this);
                myRecyclerAdapter=new MyRecyclerAdapter(products,productClickListener,list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_delevery_charge,str_tax,str_discount);
                listView.setLayoutManager(new LinearLayoutManager(Order_section.this));
                listView.setAdapter(myRecyclerAdapter);
                myRecyclerAdapter.notifyDataSetChanged();

            } else {
                listView.setAdapter(null);
                // cartAdapter.notifyDataSetChanged();
            }

        }




        catch (java.lang.NullPointerException e){
            e.printStackTrace();
        }
       /* try {
             list = new ArrayList<Model2>();
            list.clear();
            if (shr.contains("myJson") && shref.contains("myJson")) {
                Gson gson1 = new Gson();
                String response1 = shr.getString("myJson", "");
                ArrayList<Model2> lstArrayList1 = gson1.fromJson(response1,
                        new TypeToken<List<Model2>>() {
                        }.getType());
                list.addAll(lstArrayList1);
                Gson gson = new Gson();
                String response = shref.getString("myJson", "");
                ArrayList<Model2> lstArrayList = gson.fromJson(response,
                        new TypeToken<List<Model2>>() {
                        }.getType());
                list.addAll(lstArrayList);
            } else if (shr.contains("myJson") && !shref.contains("myJson")) {
                Gson gson1 = new Gson();
                String response1 = shr.getString("myJson", "");
                ArrayList<Model2> lstArrayList1 = gson1.fromJson(response1,
                        new TypeToken<List<Model2>>() {
                        }.getType());
                list.addAll(lstArrayList1);
            } else if (shref.contains("myJson") && !shr.contains("myJson")) {
                Gson gson = new Gson();
                String response = shref.getString("myJson", "");
                ArrayList<Model2> lstArrayList = gson.fromJson(response,
                        new TypeToken<List<Model2>>() {
                        }.getType());
                list.addAll(lstArrayList);
            } else {
                list.clear();
            }
            if (list.size() > 0) {
                newProductList.addAll(list);
                products=new Products(list,Order_section.this);
                myRecyclerAdapter=new MyRecyclerAdapter(products,productClickListener,list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_delevery_charge,str_tax,str_discount);
                listView.setLayoutManager(new LinearLayoutManager(Order_section.this));
                listView.setAdapter(myRecyclerAdapter);
                myRecyclerAdapter.notifyDataSetChanged();

            } else {
                listView.setAdapter(null);
                // cartAdapter.notifyDataSetChanged();
            }
        }catch (java.lang.NullPointerException e){
            e.printStackTrace();
        }*/



        /* if (products.size()>0){
            for (int i=0;i<products.size();i++){
                if (products.get(i).getQuantity()>0){
                    r = r +Integer.parseInt(products.get(i).getRuppee())*products.get(i).getQuantity();
                }
            }
            txt_sub_total.setText(String.valueOf(r));
            txt_discount.setText("Rs 0/-");
            txt_delevery_cost.setText("Rs 0/-");
            txt_vat.setText(String.valueOf(r*15/100));
            txt_total_cost.setText(String.valueOf(r + r*15/100));
           // r1=String.valueOf(100 *(r + r*15/100));
        }*/


/*
        besstoo_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag1==0 && flag2==0){
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

        if (str_tag1.equals("menu")){
            online.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

                                              if (flag==0 && flag2==0){
                                                  if (flag1==0){
                                                      online.setImageResource(R.drawable.online_actv);
                                                      flag1=1;
                                                      payment="online";
                                                  }
                                                  else{
                                                      online.setImageResource(R.drawable.online_non);
                                                      flag1=0;
                                                      payment="";
                                                  }
                                              }


                                          }
                                      }
            );

            cod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (flag==0 && flag1==0){
                        if (flag2==0){
                            payment="cod";
                            cod.setImageResource(R.drawable.cod_actv);
                            flag2=1;
                        }
                        else{
                            flag2=0;
                            payment="";
                            cod.setImageResource(R.drawable.cod_non);
                        }
                    }

                }
            });
        }
        else if (str_tag1.equals("bake")){
            cod.setVisibility(View.INVISIBLE);
            online.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              //   if (flag==0 && flag2==0){
                                                  if (flag1==0){
                                                      online.setImageResource(R.drawable.online_actv);
                                                      flag1=1;
                                                      payment="online";
                                                  }
                                                  else{
                                                      online.setImageResource(R.drawable.online_non);
                                                      flag1=0;
                                                      payment="";
                                                  }
                                              }
                                        }
            );
        }

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
                //final String formattedDate = df.format(c.getTime());
                final  String str_current_time= dateFormat.format(c.getTime());
                Date d1 = null;
                Date d2 = null;
                Date d3=null;
                try {
                    d1 = dateFormat.parse(str_time_to_stop_order);
                    d2 = dateFormat.parse(str_current_time);
                    d3=dateFormat.parse(str_for_next_day);
                    //in milliseconds
                    long diff = d1.getTime() - d2.getTime();
                    long diff1=d3.getTime()-d2.getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    /*if (diff<0){
                        Toast.makeText(Order_section.this,"You cannot order after 3 pm",Toast.LENGTH_SHORT).show();
                    }
                    else {*/
                        /* System.out.print(diffDays + " days, ");
                    System.out.print(diffHours + " hours, ");
                    System.out.print(diffMinutes + " minutes, ");
                    System.out.print(diffSeconds + " seconds.");*/
                        if (payment.equals("")) {
                            if (str_tag1.equals("menu")) {
                                Toast.makeText(Order_section.this, "you have to select atleast one payment option", Toast.LENGTH_LONG).show();
                            } else if (str_tag1.equals("bake")) {
                                Toast.makeText(Order_section.this, "you have to select payment option", Toast.LENGTH_LONG).show();
                            }

                        } else if (payment.equals("online")) {
                            str_user_pay_status = "1";
                            str_mode_of_payment = "3";
                            str_user_status = "1";
                            if (str_tag1.equals("bake")){
                                checkQuantity();
                            }
                            else{
                               // if (!newProductList.get(0).getForDay().equals("Saturday")) {

                                if (FLAG_DAY==0){

                                    if (diff < 0 && diff1 > 0) {
                                        Toast.makeText(Order_section.this, "You cannot order after 3 pm", Toast.LENGTH_SHORT).show();
                                    } else {
                                        checkQuantity();
                                    }
                                }
                                else{
                                    if (diff<0){
                                        Toast.makeText(Order_section.this, "You cannot order after 3 pm", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        checkQuantity();
                                    }
                                }

                           //     }
                               /* else{
                                    if (diff<0){
                                        Toast.makeText(Order_section.this, "You cannot order after 3 pm", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        checkQuantity();
                                    }
                                }*/
                            }
                            // startPayment();
                        } else if (payment.equals("cod")) {
                            str_user_pay_status = "0";
                            str_user_status = "1";
                            str_mode_of_payment = "2";
                            str_r_payment_id = "pay_7nHw44reSBluPu";
                            StringBuilder salt = new StringBuilder();
                            Random rnd = new Random();
                            while (salt.length() < 18) { // length of the random string.
                                int index = (int) (rnd.nextFloat() * str_r_payment_id.length());
                                salt.append(str_r_payment_id.charAt(index));
                            }
                            str_r_payment_id = salt.toString();

                            // checkQuantity();
                           // if (!newProductList.get(0).getForDay().equals("Saturday")){

                            if (FLAG_DAY==0){
                                if (diff <0&& diff1>0) {
                                    Toast.makeText(Order_section.this, "You cannot order after 3 pm", Toast.LENGTH_SHORT).show();
                                } else {
                                    checkQuantity();
                                }
                            }
                            else{
                                if (diff<0){
                                    Toast.makeText(Order_section.this, "You cannot order after 3 pm", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    checkQuantity();
                                }
                            }


                          //  }
                           /* */


                        } else if (payment.equals("besstoo cash")) {
                            Toast.makeText(Order_section.this, "besstoo cash is not available now", Toast.LENGTH_SHORT).show();
                        }
                  //  }

                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });
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

    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        str_tot=txt_total_cost.getText().toString();
        str_tot=str_tot.replace(getResources().getString(R.string.Rs),"");
        str_tot=str_tot.replace(" ","");
       /* double discount1=Double.parseDouble(str_discount);
        double discount2=(discount1/100.0f)*r1;*/
         r2= Double.parseDouble(str_tot);
         double dic= r2*discount1/100;
        str_disc=String.valueOf(dic);
     //   Log.e("disc",str_disc);

         r1=String.valueOf(100*r2);
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
            options.put("name", "Besstoo");
            options.put("image", "https://besstoo.com/public/frontend/images/logo.png");
            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Order #123456");
            //options.put("theme")

            options.put("currency", "INR");
            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            options.put("amount", r1);
            JSONObject theme =new JSONObject();
            theme.put("color","#B22222");

            JSONObject preFill = new JSONObject();
            preFill.put("email", str_email);
            preFill.put("contact", str_cell_phone);

            options.put("prefill", preFill);
            options.put("theme",theme);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try {
            //Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();
            editor=shr.edit();
            editor.clear().apply();
            editor1=shref.edit();
            editor1.clear().apply();
            str_r_payment_id=s;
            Log.e("payment success", s);
            Log.e("pa_id",paymentData.getPaymentId());
            str_no_of_person="1";
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
   /* @Override
    public void onPaymentSuccess(String s) {
        try {
            //Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();
            editor=shr.edit();
            editor.clear().apply();
            editor1=shref.edit();
            editor1.clear().apply();

            str_r_payment_id=s;
            Log.e("payment success", s);
            new SaveOrders().execute();
        }catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentsuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(this, "Payment failed: " + Integer.toString(i) + " " + s, Toast.LENGTH_SHORT).show();
            Log.e("payment error",Integer.toString(i) + " " + s);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }

    }*/


    private  static class Products implements ModelDataSet{
        private final List<Model2> productList;
        Context context;

        private Products(List<Model2> productList,Context context) {
            this.productList = productList;
            this.context=context;
        }

        @Override
        public int size() {
            return productList.size();
        }

        @Override
        public Model2 get(int position) {
            return productList.get(position);
        }

        @Override
        public long getId(int position) {
            return position;
        }

        public void removeOneFrom(Model2 product) {
            Model2 model=product;
            int i = productList.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            if (model.getQuantity()>0) {
                model.setQuantity(model.getQuantity() - 1);
                model.setImage(model.getImage());
                model.setRuppee(model.getRuppee());
                model.setDescription(model.getDescription());
                model.setNo_of_item(model.getNo_of_item());
                model.setId(model.getId());
                model.setStr_food_name(model.getStr_food_name());
                productList.remove(product);
                productList.add(i, model);
                newProductList.addAll(productList);
            }

        }
        public void addOneTo(Model2 product) {
            int i = productList.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            Model2 model=product;
            model.setQuantity(model.getQuantity() + 1);
            model.setRuppee(model.getRuppee());
            model.setDescription(model.getDescription());
            model.setNo_of_item(model.getNo_of_item());
            model.setImage(model.getImage());
            model.setId(model.getId());
            model.setStr_food_name(model.getStr_food_name());
            productList.remove(product);
            productList.add(i, model);
            newProductList.addAll(productList);
        }
    }
    private final CustomButtonListener productClickListener = new CustomButtonListener() {

        @Override
        public void onMinusClick(Model2 product) {
            products.removeOneFrom(product);
            myRecyclerAdapter.notifyDataSetChanged();
        }
        @Override
        public void onPlusClick(Model2 product) {
            int n= Integer.parseInt(product.getNo_of_item());
            int n1=product.getQuantity();
            if (n1<n) {
                products.addOneTo(product);
                myRecyclerAdapter.notifyDataSetChanged();
            }
        }
    };
    private void sendRequest1(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_RAZOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        // jsonObject= null;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("response",response);
                            if (jsonObject.getString("Status").equals("Success"))
                            {
                                Toast.makeText(Order_section.this,"Order Successfully Placed",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Order_section.this,My_order.class);
                                intent.putExtra("order_sec","order");
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(Order_section.this,jsonObject.getString("Status"),Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Order_section.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                JSONArray jsonArray=new JSONArray();
                newProductList1.clear();
                for (int i=0;i<newProductList.size();i++){
                    if (newProductList.get(i).getQuantity()>0){
                        newProductList1.add(newProductList.get(i));
                    }
                }
                for (int k=0;k<newProductList1.size();k++){
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id",newProductList1.get(k).getId());
                        jsonObject.put("qty",newProductList1.get(k).getQuantity());
                        jsonArray.put(k,jsonObject);
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
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(Order_section.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        /*stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 70000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 70000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });*/
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
                                //Toast.makeText(Order_section.this,jsonObject.getString("Status"),Toast.LENGTH_SHORT).show();
                                Toast.makeText(Order_section.this,"Your Coupon code is not valid",Toast.LENGTH_SHORT).show();
                            }

                            try {
                                if (str_tag1.equals("menu")){
                                    list = new ArrayList<Model2>();
                                    list.clear();
                                    Gson gson = new Gson();
                                    String response2 = shref.getString("myJson", "");
                                    ArrayList<Model2> lstArrayList = gson.fromJson(response2,
                                            new TypeToken<List<Model2>>() {
                                            }.getType());
                                    list.addAll(lstArrayList);
                                    /* if (list.size() > 0) {
                                        newProductList.addAll(list);
                                        products=new Products(list,Order_section.this);
                                        myRecyclerAdapter=new MyRecyclerAdapter(products,productClickListener,list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_delevery_charge,str_tax,str_discount);
                                        listView.setLayoutManager(new LinearLayoutManager(Order_section.this));
                                        listView.setAdapter(myRecyclerAdapter);
                                        myRecyclerAdapter.notifyDataSetChanged();

                                    } else {
                                        listView.setAdapter(null);
                                        // cartAdapter.notifyDataSetChanged();
                                    }*/
                                }
                                else if (str_tag1.equals("bake")){
                                    list = new ArrayList<Model2>();
                                    list.clear();
                                    Gson gson1 = new Gson();
                                    String response1 = shr.getString("myJson", "");
                                    ArrayList<Model2> lstArrayList1 = gson1.fromJson(response1,
                                            new TypeToken<List<Model2>>() {
                                            }.getType());
                                    list.addAll(lstArrayList1);
                                }
                                /* if (list.size() > 0) {
                                    newProductList.addAll(list);
                                    products=new Products(list,Order_section.this);
                                    myRecyclerAdapter=new MyRecyclerAdapter(products,productClickListener,list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_delevery_charge,str_tax,str_discount);
                                    listView.setLayoutManager(new LinearLayoutManager(Order_section.this));
                                    listView.setAdapter(myRecyclerAdapter);
                                    myRecyclerAdapter.notifyDataSetChanged();

                                } else {
                                    listView.setAdapter(null);
                                    // cartAdapter.notifyDataSetChanged();
                                }*/
                                if (list.size() > 0) {
                                    newProductList.clear();
                                    newProductList.addAll(list);
                                    products=new Products(list,Order_section.this);
                                    myRecyclerAdapter=new MyRecyclerAdapter(products,productClickListener,list,R.layout.order_cart_list_item,txt_sub_total,txt_discount,txt_delevery_cost,txt_vat,txt_total_cost,str_delevery_charge,str_tax,str_discount);
                                    listView.setLayoutManager(new LinearLayoutManager(Order_section.this));
                                    listView.setAdapter(myRecyclerAdapter);
                                    myRecyclerAdapter.notifyDataSetChanged();

                                } else {
                                    listView.setAdapter(null);
                                    // cartAdapter.notifyDataSetChanged();
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
                                Toast.makeText(Order_section.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
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

    private  void  checkQuantity(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_CHECK_QUANTITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        // jsonObject= null;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("response",response);
                            if (jsonObject.getString("Status").equals("Success")){
                               // outOfStock(jsonObject.getString("Status"));

                                if (payment.equals("online")){
                                    startPayment();
                                }
                                else if (payment.equals("cod")){
                                    startPay();
                                }

                            }
                            else{
                                outOfStock(jsonObject.getString("Status"));
                            }

                           /* if (jsonObject.getString("Status").equals("Success"))
                            {
                                Toast.makeText(Order_section.this,"Order Successfully Placed",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(Order_section.this,jsonObject.getString("Status"),Toast.LENGTH_SHORT).show();
                            }*/
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
                                Toast.makeText(Order_section.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                JSONArray jsonArray=new JSONArray();
                for (int i=0;i<newProductList.size();i++){
                    if (newProductList.get(i).getQuantity()>0){
                        newProductList1.add(newProductList.get(i));
                    }
                }
                for (int k=0;k<newProductList1.size();k++){
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id",newProductList1.get(k).getId());

                        //jsonObject.put("qty",newProductList1.get(k).getQuantity());
                        jsonArray.put(k,jsonObject);
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
        progressDialog = new ProgressDialog(Order_section.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }



   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this,Order_section.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

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
