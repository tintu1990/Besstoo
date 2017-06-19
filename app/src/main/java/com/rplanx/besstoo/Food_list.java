package com.rplanx.besstoo;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rplanx.besstoo.adapter.CartAdapter;
import com.rplanx.besstoo.adapter.Party_cart_adapter;
import com.rplanx.besstoo.adapter.TabAdapter;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.date.Datedialog1;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.network.GpsTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Food_list extends AppCompatActivity implements SimpleGestureFilter.SimpleGestureListener,View.OnClickListener {
    ViewPager viewPager;
   // CustomViewpager viewPager;
    private Animation animUp;
    private Animation animDown;
    int flag=0;
    int flag1=0;
    View layouttobring;
    int width;
    int height;
    ImageView nav;
    RelativeLayout slide_menu_layout;
    private SimpleGestureFilter simple_detector;
    AutoCompleteTextView autoCompleteTextView;
    TextView textView;
    ImageView cart;
    CartAdapter cartAdapter;
   // ModelCart modelCart;
   // List<Model2>modelCarts;
    SharedPreferences sharedPreferences;
    SharedPreferences .Editor editor;
    SharedPreferences shref;
    SharedPreferences .Editor editor1;
    SharedPreferences shr;
    RelativeLayout profile;
    PagerSlidingTabStrip tabsStrip;
   // CustomTabStrip tabsStrip;
    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor2;
    RelativeLayout my_order;
    int c;
    Context mContext;
    Double latitude;
    Double longitude;
    GpsTracker gpsTracker;

    FloatingActionButton floatingActionButton;
    AutoCompleteTextView txt_address;
    List<Address>addresses;
    TabAdapter tabAdapter;
    RelativeLayout about_us;
    TextView libs_login;
    TextView txt_about_us;
    int pos=0;
    TextView contact_us;
    Dialog dialog;
    RelativeLayout rl_contact_us;
    RadioGroup selecttime;
    RadioButton male;
    TextView date;
    RadioButton time;
    Button btn_proceed;
    String str_date;
    String str_time="15:00:00";
    String str_time_for_order="12:00:00";
    String str_time_for_tomorrow="16:00:00";
    RadioButton twelve;
    EditText no_of_people;
    String str_no_of_people;
    int int_no_of_people;
    String value = "<html><a href=\"https://besstoo.com/about\">About Us</a></html>";
    String str_contact_us="<html><a href=\"http://besstoo.com/contact\">Contact Us</a></html>";
    SharedPreferences party_pref;
    Party_cart_adapter party_cart_adapter;
    RelativeLayout rl_terms;
    RelativeLayout rl_privacy;
    RelativeLayout rl_quality;
    String CHECK_VERSION=Constant.URL+"check_version";
    int version;
    TextView terms;
     String formattedDate;
    TextView tx_day;
    int FLAG_DAY=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        version= GetVersionCode();
        checkVersion(String.valueOf(version));
       /* if (version<5){
            Log.e("version",String.valueOf(version));
            gotoPlayStore();
        }*/

        Calendar to_date = Calendar.getInstance();

        if(to_date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            FLAG_DAY=1;
            System.out.println("OK");
        }
        else {
            FLAG_DAY=0;
        }
        /*else{
            Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_SHORT).show();
        }*/




        viewPager=(ViewPager)findViewById(R.id.viewpager);
        nav=(ImageView)findViewById(R.id.nav);
        cart=(ImageView)findViewById(R.id.noti);
        libs_login=(TextView)findViewById(R.id.lib_login);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        layouttobring = findViewById(R.id.nav_slide);
        txt_address=(AutoCompleteTextView) findViewById(R.id.txt_address);
        party_pref=getSharedPreferences("Party_pref",Context.MODE_PRIVATE);
        sharedPreferences=getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        shref=getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);
        my_order=(RelativeLayout)layouttobring.findViewById(R.id.third_r);
        shr=getSharedPreferences("MyPREFERENCES1",Context.MODE_PRIVATE);
        if (shref.contains("incr")){
            c=shref.getInt("incr",0);
        }
        mContext=this;
        textView=(TextView)findViewById(R.id.badge_notification_1);
        SharedPreferences sharedPreferences=getSharedPreferences("login",Context.MODE_PRIVATE);
        if (Constant.counter>0){
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(Constant.counter));
        }
        else {
            textView.setVisibility(View.INVISIBLE);
        }
        if (sharedPreferences.contains("user_id")){
            libs_login.setText("My Profile");
            my_order.setVisibility(View.VISIBLE);
        }
        else{
            libs_login.setText("Login");
            my_order.setVisibility(View.GONE);
        }
        contact_us=(TextView)findViewById(R.id.txt_contactus);
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.select_address);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!shr.contains("myJson") && !shref.contains("myJson") && !party_pref.contains("myJson")) {
                    outOftime("cart is empty");
                } else {
                    final Dialog dialog = new Dialog(Food_list.this, R.style.AppTheme_AppBarOverlay);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.cart);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.getAttributes().alpha = 0.9f;
                RelativeLayout relativeLayout = (RelativeLayout) dialog.findViewById(R.id.clear_button);
                RelativeLayout btn_procced_cart = (RelativeLayout) dialog.findViewById(R.id.btn_proceed_cart);
                btn_procced_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if (pos == 0) {
                            if (shr.contains("myJson")||party_pref.contains("myJson")) {
                                clearcart("Please clear your cart to proceed");
                               // Toast.makeText(Food_list.this, "Please clear your cart to proceed", Toast.LENGTH_SHORT).show();
                            } else {
                                final Dialog dialog = new Dialog(Food_list.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.time_picker);
                                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.setCancelable(true);
                                dialog.show();
                                tx_day=(TextView)dialog.findViewById(R.id.today);
                                twelve=(RadioButton)dialog.findViewById(R.id.rb_boy);
                                Calendar c = Calendar.getInstance();
                                System.out.println("Current time => " + c.getTime());
                                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                formattedDate = df.format(c.getTime());
                               // c.add(Calendar.DAY_OF_YEAR, 1);

                                final String str_current_time = dateFormat.format(c.getTime());
                                Date d1 = null;
                                Date d2 = null;
                                Date d5 = null;
                                try {
                                    d1 = dateFormat.parse(str_time);
                                    d2 = dateFormat.parse(str_current_time);
                                    d5=dateFormat.parse(str_time_for_tomorrow);
                                    //in milliseconds
                                    long diff = d1.getTime() - d2.getTime();
                                    long diff_for_tomorrow=d5.getTime()-d2.getTime();
                                    long diffSeconds = diff / 1000 % 60;
                                    long diffMinutes = diff / (60 * 1000) % 60;
                                    long diffHours = diff / (60 * 60 * 1000) % 24;
                                    long diffDays = diff / (24 * 60 * 60 * 1000);

                                    System.out.print(diffDays + " days, ");
                                    System.out.print(diffHours + " hours, ");
                                    System.out.print(diffMinutes + " minutes, ");
                                    System.out.print(diffSeconds + " seconds.");

                                    if (FLAG_DAY==0){
                                        if (diff < 0 && diff_for_tomorrow>0) {
                                            Toast.makeText(Food_list.this, "you cannot order after 3 pm", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }

                                        if (diff_for_tomorrow<0){
                                            // flag_for_date=1;
                                            tx_day.setText("TOMORROW");
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.add(Calendar.DAY_OF_YEAR, 1);
                                            formattedDate=df.format(calendar.getTime());
                                        }
                                        else{
                                            tx_day.setText("TODAY");
                                        }
                                    }
                                    else{
                                        if (diff<0){
                                            dialog.dismiss();
                                            outOftime("you cannot order after 3 pm for today");
                                        }
                                    }
                              //      if (!Constant.exercise_arraylist1.get(0).getForDay().equals("Saturday")){

                                //    }
                                   /* */


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                SimpleDateFormat dateFormat_format=new SimpleDateFormat("HH:mm:ss");
                                final String formattedDate_forOrder = df.format(c.getTime());
                                final  String str_current_time_for_order= dateFormat.format(c.getTime());
                                Date d3 = null;
                                Date d4 = null;
                                Date d6=null;
                                try {
                                    d3 = dateFormat_format.parse(str_time_for_order);
                                    d4 = dateFormat_format.parse(str_current_time);
                                    d6=dateFormat_format.parse(str_time_for_tomorrow);
                                    //in milliseconds
                                    long diff = d3.getTime() - d4.getTime();
                                    long diff_for_tomorrow=d6.getTime()-d4.getTime();
                                    long diffSeconds = diff / 1000 % 60;
                                    long diffMinutes = diff / (60 * 1000) % 60;
                                    long diffHours = diff / (60 * 60 * 1000) % 24;
                                    long diffDays = diff / (24 * 60 * 60 * 1000);

                                    System.out.print(diffDays + " days, ");
                                    System.out.print(diffHours + " hours, ");
                                    System.out.print(diffMinutes + " minutes, ");
                                    System.out.print(diffSeconds + " seconds.");
                              //      if (!Constant.exercise_arraylist1.get(0).getForDay().equals("Saturday")){

                                    if (FLAG_DAY==0){
                                        if (diff_for_tomorrow>0){
                                            if (diff<0){
                                                // dialog.dismiss();
                                                twelve.setVisibility(View.INVISIBLE);

                                                // outOftime("you cannot order after 3 pm");

                                                //Toast.makeText(getContext(),"you cannot order after 3 pm",Toast.LENGTH_LONG).show();
                                            }
                                            else{
                                                twelve.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        else{
                                            twelve.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    else {
                                        if (diff<0){
                                            // dialog.dismiss();
                                            twelve.setVisibility(View.INVISIBLE);
                                            // outOftime("you cannot order after 3 pm");
                                            //Toast.makeText(getContext(),"you cannot order after 3 pm",Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            twelve.setVisibility(View.VISIBLE);
                                        }
                                    }



                                   // }
                                   /*
*/

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                selecttime = (RadioGroup) dialog.findViewById(R.id.radiogrp1);
                                selecttime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                        male = (RadioButton) dialog.findViewById(i);
                                    }
                                });
                                btn_proceed = (Button) dialog.findViewById(R.id.button_sub);
                                btn_proceed.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // dialog.dismiss();
                                        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                        if (male == null) {
                                            Toast.makeText(Food_list.this, "You have to select a time slot", Toast.LENGTH_SHORT).show();
                                        } else {
                                            dialog.dismiss();
                                            if (sharedPreferences.contains("user_id")) {
                                                Intent intent = new Intent(Food_list.this, Address_list.class);
                                                intent.putExtra("time", male.getText().toString());
                                                intent.putExtra("tag", "menu");
                                                intent.putExtra("date", formattedDate);
                                                intent.putExtra("no_of_people", "1");
                                                intent.putExtra("tag1", "menu");
                                                //intent.putExtra("page",);
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(Food_list.this, Login.class);
                                                // intent.putExtra("tag1","order");
                                                intent.putExtra("time", male.getText().toString());
                                                intent.putExtra("date", formattedDate);
                                                intent.putExtra("tag", "menu");
                                                intent.putExtra("no_of_people", "1");
                                                intent.putExtra("tag1", "menu");
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                });
                            }

                        } else if (pos == 1) {

                            if (shref.contains("myJson")||party_pref.contains("myJson")) {
                                //Toast.makeText(Food_list.this, "Please clear your cart to proceed", Toast.LENGTH_SHORT).show();
                                clearcart("Please clear your cart to proceed");
                            } else {
                                final Dialog dialog = new Dialog(Food_list.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.time_picker3);
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.setCancelable(true);
                                dialog.show();
                                date = (TextView) dialog.findViewById(R.id.date);
                                selecttime = (RadioGroup) dialog.findViewById(R.id.radiogrp1);
                                selecttime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                        time = (RadioButton) dialog.findViewById(i);
                                    }
                                });
                                btn_proceed = (Button) dialog.findViewById(R.id.button_sub);
                                date.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Datedialog1 datedialog = new Datedialog1();
                                        datedialog.getViews(view);
                                        // FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                                        //  datedialog.show(fragmentTransaction,"DatePicker");
                                        FragmentManager fm = getFragmentManager();
                                        datedialog.show(fm, "DatePicker");
                                    }
                                });
                                btn_proceed.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // dialog.dismiss();
                                        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                        //  if (sharedPreferences.contains("user_id")){
                                        str_date = date.getText().toString();
                                        // no_of_people.setVisibility(View.GONE);
                                        //  str_no_of_people=no_of_people.getText().toString();
                                        if (time == null) {
                                            Toast.makeText(Food_list.this, "you have to select a time slot", Toast.LENGTH_SHORT).show();
                                        }
                                        if (str_date.isEmpty()) {
                                            Toast.makeText(Food_list.this, "you have to select date", Toast.LENGTH_SHORT).show();
                                        }
                                        if (!str_date.isEmpty() && time != null) {
                                            dialog.dismiss();
                                            if (sharedPreferences.contains("user_id")) {
                                                Intent intent = new Intent(Food_list.this, Address_list.class);
                                                intent.putExtra("time", time.getText().toString());
                                                // intent.putExtra("no_of_people",str_no_of_people);
                                                intent.putExtra("date", str_date);
                                                intent.putExtra("tag", "menu");
                                                intent.putExtra("tag1", "bake");
                                                intent.putExtra("no_of_people", "1");
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(Food_list.this, Login.class);
                                                intent.putExtra("time", time.getText().toString());
                                                // intent.putExtra("no_of_people",str_no_of_people);
                                                intent.putExtra("date", str_date);
                                                intent.putExtra("no_of_people", "1");
                                                intent.putExtra("tag1", "bake");
                                                intent.putExtra("tag", "menu");
                                                startActivity(intent);
                                            }
                                        }

                                    }
                                });
                            }
                        } else {

                            if (shref.contains("myJson") || shr.contains("myJson")) {
                                clearcart("Please clear your cart to proceed");

                            }
                            else{
                                final Dialog dialog = new Dialog(Food_list.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.time_picker1);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.setCancelable(true);
                            dialog.show();
                            no_of_people = (EditText) dialog.findViewById(R.id.edt_number);
                            date = (TextView) dialog.findViewById(R.id.date);
                            selecttime = (RadioGroup) dialog.findViewById(R.id.radiogrp1);
                            selecttime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    time = (RadioButton) dialog.findViewById(i);
                                }
                            });
                            btn_proceed = (Button) dialog.findViewById(R.id.button_sub);
                            date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Datedialog1 datedialog = new Datedialog1();
                                    datedialog.getViews(view);
                                    // FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                                    //  datedialog.show(fragmentTransaction,"DatePicker");
                                    FragmentManager fm = getFragmentManager();
                                    datedialog.show(fm, "DatePicker");
                                }
                            });

                            btn_proceed.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // dialog.dismiss();
                                    SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                    //  if (sharedPreferences.contains("user_id")){
                                    try {
                                        str_date = date.getText().toString();
                                        str_no_of_people = no_of_people.getText().toString();
                                        if (time == null) {
                                            Toast.makeText(Food_list.this, "you have to select a time slot", Toast.LENGTH_SHORT).show();
                                        }
                                        if (!str_no_of_people.isEmpty()) {
                                            int_no_of_people = Integer.parseInt(str_no_of_people);
                                            if (int_no_of_people < 10) {
                                                Toast.makeText(Food_list.this, "you have to select atleast 10 people", Toast.LENGTH_SHORT).show();
                                            } else if (int_no_of_people > 100) {
                                                Toast.makeText(Food_list.this, "you cannot select more than 100 people", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(Food_list.this, "you have to select atleast 10 people", Toast.LENGTH_SHORT).show();
                                        }
                                        if (str_date.isEmpty()) {
                                            Toast.makeText(Food_list.this, "you have to select date", Toast.LENGTH_SHORT).show();
                                        }
                                        if (!str_date.isEmpty() && int_no_of_people >= 10 && int_no_of_people <= 100 && time != null) {
                                            dialog.dismiss();

                                            if (sharedPreferences.contains("user_id")) {
                                                Intent intent = new Intent(Food_list.this, Address_list.class);
                                                intent.putExtra("time", time.getText().toString());
                                                intent.putExtra("no_of_people", str_no_of_people);
                                                intent.putExtra("date", str_date);
                                                intent.putExtra("tag", "party");
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(Food_list.this, Login.class);
                                                intent.putExtra("time", time.getText().toString());
                                                intent.putExtra("no_of_people", str_no_of_people);
                                                intent.putExtra("date", str_date);
                                                intent.putExtra("tag", "party");
                                                startActivity(intent);
                                            }
                                        }
                                    } catch (NumberFormatException ex) {
                                        //  Toast.makeText(getActivity(), "you cannot select ", Toast.LENGTH_SHORT).show();
                                        ex.printStackTrace();
                                    }


                                }
                            });
                        }
                            //}
                        }

                        //Toast.makeText(Food_list.this,String.valueOf(pos),Toast.LENGTH_SHORT).show();
                    }
                });

                //  if (pos==1||pos==0){
                final ListView listView = (ListView) dialog.findViewById(R.id.cart_list);
                try {
                    ArrayList<Model2> list = new ArrayList<Model2>();
                    list.clear();

                    if (shr.contains("myJson")) {
                        Gson gson1 = new Gson();
                        String response1 = shr.getString("myJson", "");
                        ArrayList<Model2> lstArrayList1 = gson1.fromJson(response1,
                                new TypeToken<List<Model2>>() {
                                }.getType());
                        list.addAll(lstArrayList1);
                    }

                    if (shref.contains("myJson")) {
                        Gson gson1 = new Gson();
                        String response1 = shref.getString("myJson", "");
                        ArrayList<Model2> lstArrayList1 = gson1.fromJson(response1,
                                new TypeToken<List<Model2>>() {
                                }.getType());
                        list.addAll(lstArrayList1);
                    }
                    if (party_pref.contains("myJson")) {
                        Gson gson2 = new Gson();
                        String response2 = party_pref.getString("myJson", "");
                        ArrayList<Model2> lstArrayList2 = gson2.fromJson(response2,
                                new TypeToken<List<Model2>>() {
                                }.getType());
                        list.addAll(lstArrayList2);
                    }



                    if (list.size() > 0) {
                        cartAdapter = new CartAdapter(Food_list.this, R.layout.cart_list_item, list);
                        listView.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();
                    } else {
                        dialog.dismiss();
                        outOftime("cart is empty");
                        relativeLayout.setVisibility(View.GONE);
                        btn_procced_cart.setVisibility(View.GONE);
                        listView.setAdapter(null);
                        cartAdapter.notifyDataSetChanged();

                    }

                } catch (java.lang.NullPointerException e) {
                    e.printStackTrace();
                }


                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Constant.exercise_arraylist1.clear();
                        editor = shr.edit();
                        editor.clear().apply();
                        editor1 = shref.edit();
                        editor1.clear().apply();
                        editor2 = party_pref.edit();
                        editor2.clear().apply();
                        listView.setAdapter(null);
                        viewPager.getAdapter().notifyDataSetChanged();
                        Constant.counter = 0;
                        Constant.exercise_arraylist1.clear();
                        Constant.exercise_arraylist2.clear();
                        textView.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                        //textView.setVisibility(View.INVISIBLE);
                    }
                });



                dialog.show();
            }
            }
        });

        viewPager.setAdapter(new TabAdapter(this,getSupportFragmentManager()));


        tabAdapter=new TabAdapter(this,getSupportFragmentManager());

        tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setViewPager(viewPager);
        animUp = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
        animDown = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
       // layouttobring = findViewById(R.id.nav_slide);
        profile=(RelativeLayout)layouttobring.findViewById(R.id.first_r);

        Display mDisplay = Food_list.this.getWindowManager().getDefaultDisplay();
        width = mDisplay.getWidth();
        height = mDisplay.getHeight();
        about_us=(RelativeLayout)layouttobring.findViewById(R.id.fifth_r);
        txt_about_us=(TextView)layouttobring.findViewById(R.id.txt_about_us);
      //  txt_about_us.setMovementMethod(LinkMovementMethod.getInstance());
       // txt_about_us.setText(Html.fromHtml(value));
        rl_contact_us=(RelativeLayout)layouttobring.findViewById(R.id.sixth_r);
        terms=(TextView)layouttobring.findViewById(R.id.txt_terms);
        terms.setText("Terms & Conditions");
        rl_terms=(RelativeLayout)layouttobring.findViewById(R.id.ninth_r);
        rl_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layouttobring.startAnimation(animUp);
                layouttobring.setVisibility(View.GONE);
                nav.setImageResource(R.drawable.navs);
                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://besstoo.com/terms"));
                            startActivity(browserIntent);
                            //  finish();

                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }
                });
                closeActivity.start();
            }
        });
        rl_quality=(RelativeLayout)layouttobring.findViewById(R.id.eighth_r);
        rl_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layouttobring.startAnimation(animUp);
                layouttobring.setVisibility(View.GONE);
                nav.setImageResource(R.drawable.navs);
                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://besstoo.com/quality"));
                            startActivity(browserIntent);
                            //  finish();

                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }
                });
                closeActivity.start();
            }
        });
        rl_privacy=(RelativeLayout)layouttobring.findViewById(R.id.seventh_r);
        rl_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layouttobring.startAnimation(animUp);
                layouttobring.setVisibility(View.GONE);
                nav.setImageResource(R.drawable.navs);
                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://besstoo.com/privacy"));
                            startActivity(browserIntent);
                            //  finish();

                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }
                });
                closeActivity.start();
            }
        });

        rl_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layouttobring.startAnimation(animUp);
                layouttobring.setVisibility(View.GONE);
                nav.setImageResource(R.drawable.navs);

                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://besstoo.com/contact"));
                            startActivity(browserIntent);
                            //  finish();
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }
                });
                closeActivity.start();

                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://besstoo.com/contact"));
                //startActivity(browserIntent);
            }
        });

       // contact_us=(TextView)findViewById(R.id.txt_contactus);
        //contact_us.setMovementMethod(LinkMovementMethod.getInstance());
       // contact_us.setText(Html.fromHtml(str_contact_us));

        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layouttobring.startAnimation(animUp);
                layouttobring.setVisibility(View.GONE);
                nav.setImageResource(R.drawable.navs);

                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://besstoo.com/about"));
                            startActivity(browserIntent);
                            //  finish();

                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }
                });
                closeActivity.start();
            }
        });

        slide_menu_layout=(RelativeLayout)layouttobring.findViewById(R.id.slide_menu_layout1);
        nav.setOnClickListener(this);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("login",Context.MODE_PRIVATE);
                if (sharedPreferences.contains("user_id")){
                   /* SharedPreferences sharedPreferences1=getSharedPreferences("login",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.clear();
                    editor.apply();*/
                    layouttobring.startAnimation(animUp);
                    layouttobring.setVisibility(View.GONE);
                    nav.setImageResource(R.drawable.navs);
                    Thread closeActivity = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                Intent intent=new Intent(Food_list.this,Profile.class);
                                startActivity(intent);
                              //  finish();

                            } catch (Exception e) {
                                e.getLocalizedMessage();
                            }
                        }
                    });
                    closeActivity.start();
                }
                else{
                    layouttobring.startAnimation(animUp);
                    layouttobring.setVisibility(View.GONE);
                    nav.setImageResource(R.drawable.navs);

                    Thread closeActivity = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                Intent intent=new Intent(Food_list.this,Login.class);
                                intent.putExtra("reg","reg");
                                startActivity(intent);

                            } catch (Exception e) {
                                e.getLocalizedMessage();
                            }
                        }
                    });
                    closeActivity.start();

                }
            }
        });
        my_order.setOnClickListener(this);
        simple_detector = new SimpleGestureFilter(this,this);


        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos=position;
                Log.e("pos",String.valueOf(position));
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {// TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
         //   int count = getSupportFragmentManager().getBackStackEntryCount();
          //  if (count == 0) {
            editor=shr.edit();
            editor.clear().apply();
            editor1=shref.edit();
            editor1.clear().apply();
            editor2=party_pref.edit();
            editor2.clear().apply();
            finishAffinity();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        flag=0;
        version= GetVersionCode();
        checkVersion(String.valueOf(version));
        /*if (version<5){
            gotoPlayStore();
        }*/
        viewPager.setAdapter(new TabAdapter(this,getSupportFragmentManager()));


        tabAdapter=new TabAdapter(this,getSupportFragmentManager());

        tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setViewPager(viewPager);
        //Constant.counter;
        SharedPreferences sharedPreferences=getSharedPreferences("login",Context.MODE_PRIVATE);
        if (sharedPreferences.contains("user_id")){
            libs_login.setText("My Profile");
            my_order.setVisibility(View.VISIBLE);
        }
        else{
            libs_login.setText("Login");
            my_order.setVisibility(View.GONE);
        }
       // flag1=1;
        /*if (sharedPreferences.contains("user_id")){
            libs_login.setText("My Profile");
            my_order.setVisibility(View.VISIBLE);
        }
        else{
            libs_login.setText("Login");
            my_order.setVisibility(View.GONE);
        }*/

       // finish();
       // startActivity(getIntent());
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }
  /*  @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
          //  getSupportFragmentManager().popBackStackImmediate();
         editor=shr.edit();
               editor.clear().apply();
                editor1=shref.edit();
               editor1.clear().apply();
           super.onBackPressed();
        }
    }*/

    @Override
    public void onSwipe(int direction) {
       // String str = "";
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT :
                if (flag==1) {
                    nav.setImageResource(R.drawable.navs);
                    layouttobring.startAnimation(animUp);
                    layouttobring.setVisibility(View.GONE);
                   // viewPager.setDisabled(false);
                    flag = 0;
                }

                /*else{
                    // viewPager.setDisabled(true);
                }*/
                break;
            case SimpleGestureFilter.SWIPE_LEFT :
                if (flag==0){
                    // viewPager.setDisabled(true);
                }
                else{
                    //viewPager.setDisabled(false);

                }

               /* layouttobring.startAnimation(animDown);
                layouttobring.setVisibility(View.VISIBLE);
                flag=1;

                str = "Swipe Left";*/
                break;
            case SimpleGestureFilter.SWIPE_DOWN :
                break;
            case SimpleGestureFilter.SWIPE_UP :
                break;

        }
    //    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        this.simple_detector.onTouchEvent(event);

       /* float StartX = 0, EndX = 0, EndY = 0, diffX, diffY , upX, upY;
        float StartY = 0;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                StartX = event.getX();
                StartY = event.getY();
                Constant.StartX=(int) StartX;
                Constant.EndX=(int) StartX;
                Constant.StartY=(int) StartY;
                Constant.EndY=(int) StartY;
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                EndX= event.getX();
                EndY= event.getY();
                Constant.EndX=(int) EndX;
                Constant.EndY=(int) EndY;
                break;
            }
            case MotionEvent.ACTION_UP:
            {


                //Consume if necessary and perform the fling / swipe action
                //if it has been determined to be a fling / swipe
                break;
            }

        }
        diffX=Constant.EndX-Constant.StartX;
        diffY=Constant.EndY-Constant.StartY;
        if(diffX>diffY){
            if((diffX>70)&&(flag==0) && Constant.StartX<200){
                layouttobring.setVisibility(View.VISIBLE);
                layouttobring.startAnimation(animDown);
                flag=1;
            }
        }
        if((diffX<-50)&&(flag==1)){
            layouttobring.startAnimation(animUp);
            layouttobring.setVisibility(View.GONE);
            flag=0;

        }*/
        return super.dispatchTouchEvent(event);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /* case R.id.fab:
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay==0){
                                    hourOfDay=+12;
                                    format="AM";
                                }
                                else if (hourOfDay == 12) {
                                    format = "PM";
                                }
                                else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                }
                                else {
                                    format = "AM";
                                }

                                Toast.makeText(Food_list.this,hourOfDay + ":" + minute + format,Toast.LENGTH_LONG).show();

                               // txtTime.setText(hourOfDay + ":" + minute + format);
                                // txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;*/

            case R.id.third_r:
                layouttobring.startAnimation(animUp);
                layouttobring.setVisibility(View.GONE);
                nav.setImageResource(R.drawable.navs);
                Thread closeActivity1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            SharedPreferences sharedPreferences=getSharedPreferences("login",Context.MODE_PRIVATE);
                            if (sharedPreferences.contains("user_id")){
                                Intent intent=new Intent(Food_list.this,My_order.class);
                                startActivity(intent);
                            }
                            else{
                                Intent intent=new Intent(Food_list.this,Login.class);
                                intent.putExtra("reg","reg");
                                startActivity(intent);
                            }


                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }
                });
                closeActivity1.start();
                break;


            case R.id.nav:
                final LayoutInflater factory = getLayoutInflater();
                @SuppressWarnings("UnusedAssignment") final View textEntryView = factory.inflate(R.layout.food_list1, null);
               // ListView listView=(ListView)textEntryView.findViewById(R.id.list1);
               // View view1 = LayoutInflater.from(getApplication()).inflate(R.layout.food_list1, null);
             //   ListView listView=(ListView)view1.findViewById(R.id.list1);
                if (flag==0){
                    nav.setImageResource(R.drawable.navs_fill);
                    layouttobring.setVisibility(View.VISIBLE);
                    layouttobring.startAnimation(animDown);
                  //  viewPager.setDisabled(false);
                    flag=1;
                }

                else{
                    nav.setImageResource(R.drawable.navs);
                    layouttobring.setVisibility(View.GONE);
                    layouttobring.startAnimation(animUp);
                   // viewPager.setDisabled(true);
                    flag=0;
                }

                break;
        }
    }

    private void showDialog() {

     //   if (flag1==0) {
            // custom dialog
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.banner);

            // set the custom dialog components - text, image and button
            ImageButton close = (ImageButton) dialog.findViewById(R.id.btnClose);
            //Button buy = (Button) dialog.findViewById(R.id.btnBuy);

            // Close Button
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    //TODO Close button action
                }
            });

            // Buy Button
      /*  buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //TODO Buy button action
            }
        });*/

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

            dialog.show();
        }

   // }



    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }*/

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }*/


    private  void  outOftime(String message){
        TextView image = new TextView(this);
        image.setGravity(Gravity.CENTER|Gravity.BOTTOM);
       // image.setImageResource(R.drawable.besstoo_pop);
        image.setTextSize(15);
        image.setTextColor(Color.RED);
        image.setBackgroundResource(R.drawable.besstoo_pop);
        image.setText(message);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Food_list.this,R.style.MyAlertDialogStyle);
        //alertDialog.setTitle(getResources().getString(R.string.app_name));
        //alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setView(image);
        /*AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.getWindow().setLayout(600, 400);*/

        /*AlertDialog alert11 = alertDialog.create();
        alert11.show();
        Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground.setBackgroundColor(Color.RED);*/

        //alertDialog.setIcon(R.drawable.besstoo_pop);
        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }


    private  void  gotoPlayStore(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Food_list.this,R.style.MyAlertDialogStyle);
        //alertDialog.setTitle(getResources().getString(R.string.app_name));
        //alertDialog.setMessage(message);
        TextView image = new TextView(this);
        image.setGravity(Gravity.CENTER|Gravity.BOTTOM);
        // image.setImageResource(R.drawable.besstoo_pop);
        image.setTextSize(15);
        image.setTextColor(Color.RED);
        image.setBackgroundResource(R.drawable.besstoo_pop);
        image.setText("Please update your app to proceed");
        alertDialog.setView(image);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
                try {
                    Intent appStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.rplanx.besstoo"));
                    appStoreIntent.setPackage("com.android.vending");
                    startActivity(appStoreIntent);
                } catch (android.content.ActivityNotFoundException exception) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.rplanx.besstoo")));
                }
            }
        });
        alertDialog.show();
    }


    private  void  clearcart(String message){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Food_list.this,R.style.MyAlertDialogStyle);
        //alertDialog.setTitle(getResources().getString(R.string.app_name));
        //alertDialog.setMessage(message);
        TextView image = new TextView(this);
        image.setGravity(Gravity.CENTER|Gravity.BOTTOM);
        // image.setImageResource(R.drawable.besstoo_pop);
        image.setTextSize(15);
        image.setTextColor(Color.RED);
        image.setBackgroundResource(R.drawable.besstoo_pop);
        image.setText(message);
        alertDialog.setView(image);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.mipmap.besstoo_launcher);
        alertDialog.setNegativeButton("clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                editor=shr.edit();
                editor.clear().apply();
                editor2=party_pref.edit();
                editor2.clear().apply();
                editor1=shref.edit();
                editor1.clear().apply();
                Constant.counter=0;
                Constant.exercise_arraylist1.clear();
                Constant.exercise_arraylist2.clear();
                textView.setVisibility(View.INVISIBLE);

            }
        });

        alertDialog.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();
    }



    private  void  checkVersion(final String version){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_VERSION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Status").equals("Failure")){
                               gotoPlayStore();
                            }
                            //Toast.makeText(getApplicationContext(),jsonObject.getString("Status"),Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            String errorMessage = error.getClass().getSimpleName();
                            if (!TextUtils.isEmpty(errorMessage)) {
                               // Toast.makeText(Login.this,"No response from server. Please try after checking your connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("version", version);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
       // progressDialog = new ProgressDialog(Login.this);
       // progressDialog.setMessage("Verifying....");
      //  progressDialog.show();

    }

    private int GetVersionCode(){
        try {
            PackageInfo _info = getPackageManager().getPackageInfo(Food_list.this.getPackageName(), 0);
            return _info.versionCode;

        }
    catch (PackageManager.NameNotFoundException e)
    {
        e.printStackTrace();
        return -1;
    }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
       // return  false;
    }







}
