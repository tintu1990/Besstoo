package com.rplanx.besstoo;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rplanx.besstoo.adapter.CartAdapter;
import com.rplanx.besstoo.adapter.TabAdapter;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.getset.ModelCart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Food_list extends AppCompatActivity implements SimpleGestureFilter.SimpleGestureListener,View.OnClickListener {
    ViewPager viewPager;
    private Animation animUp;
    private Animation animDown;
    int flag=0;
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
    ModelCart modelCart;
    List<Model2>modelCarts;
    SharedPreferences sharedPreferences;
    SharedPreferences .Editor editor;
    SharedPreferences shref;
    SharedPreferences .Editor editor1;
    SharedPreferences shr;
    RelativeLayout profile;
    PagerSlidingTabStrip tabsStrip;
    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor2;
    int c;
    private String format = "";
    private int mHour, mMinute;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        viewPager=(ViewPager) findViewById(R.id.viewpager);
        nav=(ImageView)findViewById(R.id.nav);
        cart=(ImageView)findViewById(R.id.noti);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        sharedPreferences=getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        shref=getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);
        shr=getSharedPreferences("MyPREFERENCES1",Context.MODE_PRIVATE);
        if (shref.contains("incr")){
            c=shref.getInt("incr",0);
        }
        textView=(TextView)findViewById(R.id.badge_notification_1);
        if (c>0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(c));
        }
     //  floatingActionButton.setOnClickListener(this);

        /*mAddOnForSaladLnrLyt.addView(listView);*/
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.select_address);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Food_list.this,R.style.AppTheme_AppBarOverlay);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.cart);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.getAttributes().alpha = 0.9f;
                RelativeLayout relativeLayout=(RelativeLayout) dialog.findViewById(R.id.clear_button);
                final ListView listView=(ListView)dialog.findViewById(R.id.cart_list);
                try {
                    ArrayList<Model2> list=new ArrayList<Model2>();
                    list.clear();
                    if (shr.contains("myJson" )&&shref.contains("myJson")){
                        Gson gson1=new Gson();
                        String response1=shr.getString("myJson" , "");
                        ArrayList<Model2> lstArrayList1 = gson1.fromJson(response1,
                                new TypeToken<List<Model2>>(){}.getType());
                        list.addAll(lstArrayList1);
                        Gson gson = new Gson();
                        String response=shref.getString("myJson" , "");
                        ArrayList<Model2> lstArrayList = gson.fromJson(response,
                                new TypeToken<List<Model2>>(){}.getType());
                        list.addAll(lstArrayList);

                    }

                    else if (shr.contains("myJson")&&!shref.contains("myJson")){
                        Gson gson1=new Gson();
                        String response1=shr.getString("myJson" , "");
                        ArrayList<Model2> lstArrayList1 = gson1.fromJson(response1,
                                new TypeToken<List<Model2>>(){}.getType());
                        list.addAll(lstArrayList1);
                    }

                    else if (shref.contains("myJson") && !shr.contains("myJson")){
                        Gson gson = new Gson();
                        String response=shref.getString("myJson" , "");
                        ArrayList<Model2> lstArrayList = gson.fromJson(response,
                                new TypeToken<List<Model2>>(){}.getType());
                        list.addAll(lstArrayList);
                    }

                    else{
                        list.clear();
                    }

                    /*Gson gson = new Gson();
                    if (shref.contains("myJson")){
                        String response=shref.getString("myJson" , "");
                        ArrayList<Model2> lstArrayList = gson.fromJson(response,
                                new TypeToken<List<Model2>>(){}.getType());
                        list.addAll(lstArrayList);
                    }*/
                    if (list.size()>0) {
                        cartAdapter = new CartAdapter(Food_list.this, R.layout.cart_list_item, list);
                        listView.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();
                    }
                    else{
                        listView.setAdapter(null);
                        cartAdapter.notifyDataSetChanged();
                    }


                    /*if (lstArrayList.size()>0 && lstArrayList1.size()<0){
                        list.addAll(lstArrayList);
                    }
                    if (lstArrayList.size()<0 &&lstArrayList1.size()>0){
                        list.addAll(lstArrayList1);
                    }

                    if (lstArrayList.size()>0 && lstArrayList1.size()>0){
                        list.addAll(lstArrayList);
                        list.addAll(lstArrayList1);
                    }

                    if (list.size()>0) {
                        cartAdapter = new CartAdapter(Food_list.this, R.layout.cart_list_item, list);
                        listView.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();
                    }*/
                }catch (java.lang.NullPointerException e){
                    e.printStackTrace();
                }
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  Constant.exercise_arraylist1.clear();
                        editor=shr.edit();
                        editor.clear().apply();
                        editor1=shref.edit();
                        editor1.clear().apply();
                        listView.setAdapter(null);
                      //  cartAdapter.notifyDataSetChanged();
                        Intent intent=new Intent(Food_list.this,Food_list.class);
                        startActivity(intent);
                        dialog.dismiss();
                        //textView.setVisibility(View.INVISIBLE);
                    }
                });

                dialog.show();
            }
        });

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
         tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setViewPager(viewPager);
        animUp = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
        animDown = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        layouttobring = findViewById(R.id.nav_slide);
        profile=(RelativeLayout)layouttobring.findViewById(R.id.first_r);
        Display mDisplay = Food_list.this.getWindowManager().getDefaultDisplay();
        width = mDisplay.getWidth();
        height = mDisplay.getHeight();
        slide_menu_layout=(RelativeLayout)layouttobring.findViewById(R.id.slide_menu_layout1);
        nav.setOnClickListener(this);
        profile.setOnClickListener(this);

        simple_detector = new SimpleGestureFilter(this,this);

        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

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
            finishAffinity();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }


    @Override
    public void onSwipe(int direction) {
        String str = "";
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT :
                if (flag==1) {
                    nav.setImageResource(R.drawable.navs);
                    layouttobring.startAnimation(animUp);
                    layouttobring.setVisibility(View.GONE);
                    flag = 0;
                    str = "Swipe Right";
                }
                break;
            case SimpleGestureFilter.SWIPE_LEFT :
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

            case R.id.first_r:
                layouttobring.startAnimation(animUp);
                layouttobring.setVisibility(View.GONE);
                nav.setImageResource(R.drawable.navs);

                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Intent intent=new Intent(Food_list.this,Register.class);
                            startActivity(intent);

                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }
                });
                closeActivity.start();


                break;
            case R.id.nav:

                final LayoutInflater factory = getLayoutInflater();
                final View textEntryView = factory.inflate(R.layout.food_list1, null);
                ListView listView=(ListView)textEntryView.findViewById(R.id.list1);
               // View view1 = LayoutInflater.from(getApplication()).inflate(R.layout.food_list1, null);
             //   ListView listView=(ListView)view1.findViewById(R.id.list1);
                if (flag==0){
                    nav.setImageResource(R.drawable.navs_fill);
                    layouttobring.setVisibility(View.VISIBLE);
                    layouttobring.startAnimation(animDown);
                    flag=1;
                }

                else{
                    nav.setImageResource(R.drawable.navs);
                    layouttobring.setVisibility(View.GONE);
                    layouttobring.startAnimation(animUp);
                    flag=0;


                }
                break;
        }
    }





}
