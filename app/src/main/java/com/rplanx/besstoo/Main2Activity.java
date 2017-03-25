package com.rplanx.besstoo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rplanx.besstoo.adapter.Expendable_list_adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements SimpleGestureFilter.SimpleGestureListener,View.OnClickListener {
    Expendable_list_adapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private Animation animUp;
    private Animation animDown;
    int flag=0;
    View layouttobring;
    int width;
    int height;
    ImageView nav;
    ImageView imageView;
    RelativeLayout slide_menu_layout;
    private SimpleGestureFilter simple_detector;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        animUp = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
        animDown = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        layouttobring = findViewById(R.id.nav_slide);
        Display mDisplay = Main2Activity.this.getWindowManager().getDefaultDisplay();
        width = mDisplay.getWidth();
        height = mDisplay.getHeight();
        relativeLayout=(RelativeLayout)findViewById(R.id.back_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(Main2Activity.this,Food_list.class);
                startActivity(intent);
            }
        });
       /* layouttobring.setPadding(40 ,0,0,0);*/
        // layouttobring.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        slide_menu_layout=(RelativeLayout)layouttobring.findViewById(R.id.slide_menu_layout1);
        simple_detector = new SimpleGestureFilter(this,this);
        // preparing list data
        prepareListData();
        listAdapter = new Expendable_list_adapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        imageView=(ImageView)findViewById(R.id.noti);
        nav=(ImageView)findViewById(R.id.nav);
        nav.setOnClickListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Main2Activity.this,R.style.AppTheme_AppBarOverlay);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.cart);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.getAttributes().alpha = 0.9f;
                /*window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/
                dialog.show();
            }
        });
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
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
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
            case SimpleGestureFilter.SWIPE_LEFT :
               /* layouttobring.startAnimation(animDown);
                layouttobring.setVisibility(View.VISIBLE);
                flag=1;
                // post.setVisibility(View.INVISIBLE);

                *//*if (str_profile_picture1.equals("")){

                    new DownloadImageTask1(slide_picture).execute("http://bonunio.56h7jbmx8k.us-west-2.elasticbeanstalk.com" + str_profile_picture);
                }

                else {
                    new DownloadImageTask1(slide_picture).execute( str_profile_picture1);
                }*//*
                str = "Swipe Left";*/
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
            case R.id.nav:
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
