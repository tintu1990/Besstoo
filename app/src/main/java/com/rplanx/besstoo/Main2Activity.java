package com.rplanx.besstoo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.rplanx.besstoo.adapter.Expendable_list_adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity  {
    Expendable_list_adapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    //private Animation animUp;
 //   private Animation animDown;
  //  int flag=0;
    View layouttobring;
    int width;
    int height;
    //ImageView nav;
    //ImageView imageView;
    RelativeLayout slide_menu_layout;
   // private SimpleGestureFilter simple_detector;
   // RelativeLayout relativeLayout;
    FloatingActionButton floatingActionButton;
   // RadioGroup selecttime;
   // RadioButton time;
   // Button btn_proceed;
   // EditText no_of_people;
   // EditText date;
   // String str_no_of_people;
   // String str_date;
   // int int_no_of_people;
  //  String str_time;
    //private static final String LOG_TAG = "ElistCBox2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
      //  animUp = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
       // animDown = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        layouttobring = findViewById(R.id.nav_slide);
        Display mDisplay = Main2Activity.this.getWindowManager().getDefaultDisplay();
        width = mDisplay.getWidth();
        height = mDisplay.getHeight();

        /* relativeLayout=(RelativeLayout)findViewById(R.id.back_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(Main2Activity.this,Food_list.class);
                startActivity(intent);
            }
        });*/
       /* layouttobring.setPadding(40 ,0,0,0);*/
        // layouttobring.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        slide_menu_layout=(RelativeLayout)layouttobring.findViewById(R.id.slide_menu_layout1);
      //  simple_detector = new SimpleGestureFilter(this,this);
        // preparing list data
        prepareListData();
       /* listAdapter = new Expendable_list_adapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);*/
        //imageView=(ImageView)findViewById(R.id.noti);
       // nav=(ImageView)findViewById(R.id.nav);
       // nav.setOnClickListener(this);
        /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Main2Activity.this,R.style.AppTheme_AppBarOverlay);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.cart);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.getAttributes().alpha = 0.9f;
                *//*window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*//*
                dialog.show();
            }
        });*/
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        /*floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Main2Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.time_picker1);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.show();
                no_of_people=(EditText)dialog.findViewById(R.id.edt_number);
                date=(EditText)dialog.findViewById(R.id.date);
                selecttime=(RadioGroup)dialog.findViewById(R.id.radiogrp1);
                selecttime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        time = (RadioButton)dialog.findViewById(i);
                    }
                });
                btn_proceed=(Button)dialog.findViewById(R.id.button_sub);
                date .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Datedialog datedialog=new Datedialog();
                        datedialog.getViews(view);
                        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                        datedialog.show(fragmentTransaction,"DatePicker");
                    }
                });
                btn_proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        SharedPreferences sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
                        if (sharedPreferences.contains("user_id")){
                            str_date=date.getText().toString();
                            str_no_of_people=no_of_people.getText().toString();
                            if (time==null){
                                Toast.makeText(Main2Activity.this,"you have to select a time slot",Toast.LENGTH_SHORT).show();
                            }

                            if (!str_no_of_people.isEmpty()){
                                int_no_of_people=Integer.parseInt(str_no_of_people);
                                if (int_no_of_people<10){
                                    Toast.makeText(Main2Activity.this,"you have to select atleast 10 people",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Main2Activity.this,"you have to select atleast 10 people",Toast.LENGTH_SHORT).show();
                            }

                            if (str_date.isEmpty()){
                                Toast.makeText(Main2Activity.this,"you have to select date",Toast.LENGTH_SHORT).show();
                            }

                            if (!str_date.isEmpty()&& int_no_of_people>=10 && !str_time.isEmpty()){
                                Intent intent=new Intent(Main2Activity.this,PickAddress2.class);
                                intent.putExtra("time",time.getText().toString());
                                startActivity(intent);
                            }

                        }
                        else{
                            Intent intent=new Intent(Main2Activity.this,Login.class);
                            intent.putExtra("tag","order");
                            startActivity(intent);
                        }

                    }
                });
            }
        });*/
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Starters");
        listDataHeader.add("Main Course");
        listDataHeader.add("Deserts");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Kakori kebabs");
        top250.add("Chicken Lollipop");
        top250.add("Chicken Fry");
        top250.add("Veg Pakora");


        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add(" Chicken Biriyani");
        nowShowing.add("Fried Rice");
        nowShowing.add("Fry Rice ,Egg Curry,Aalu");
        nowShowing.add("Garlic Fried Rice");


        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("Fudge Marble");
        comingSoon.add("Industrial Chocolate");
        comingSoon.add("Chocobar");


        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }

   /* @Override
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
            case SimpleGestureFilter.SWIPE_LEFT :
               *//* layouttobring.startAnimation(animDown);
                layouttobring.setVisibility(View.VISIBLE);
                flag=1;
                // post.setVisibility(View.INVISIBLE);

                *//**//*if (str_profile_picture1.equals("")){

                    new DownloadImageTask1(slide_picture).execute("http://bonunio.56h7jbmx8k.us-west-2.elasticbeanstalk.com" + str_profile_picture);
                }

                else {
                    new DownloadImageTask1(slide_picture).execute( str_profile_picture1);
                }*//**//*
                str = "Swipe Left";*//*
                break;
            case SimpleGestureFilter.SWIPE_DOWN :
                break;
            case SimpleGestureFilter.SWIPE_UP :
                break;

        }

    }

    @Override
    public void onDoubleTap() {

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        this.simple_detector.onTouchEvent(event);

       *//* float StartX = 0, EndX = 0, EndY = 0, diffX, diffY , upX, upY;
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

        }*//*
        return super.dispatchTouchEvent(event);

    }*/

   /* @Override
    public void onClick(View view) {
        *//*switch (view.getId()){
            *//**//*case R.id.nav:
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
                break;*//**//*
        }*//*
    }*/



}
