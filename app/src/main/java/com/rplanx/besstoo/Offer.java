package com.rplanx.besstoo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Offer extends Activity implements SimpleGestureFilter.SimpleGestureListener,View.OnClickListener{

    private Animation animUp;
    private Animation animDown;
    int flag=0;
    View layouttobring;
    int width;
    int height;
    RelativeLayout slide_menu_layout;
    private SimpleGestureFilter simple_detector;
    ImageView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        animUp = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
        animDown = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        layouttobring = findViewById(R.id.nav_slide);
        Display mDisplay = Offer.this.getWindowManager().getDefaultDisplay();
        width = mDisplay.getWidth();
        height = mDisplay.getHeight();
       /* layouttobring.setPadding(40 ,0,0,0);*/
        // layouttobring.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        slide_menu_layout=(RelativeLayout)layouttobring.findViewById(R.id.slide_menu_layout1);
        simple_detector = new SimpleGestureFilter(this,this);
        nav=(ImageView)findViewById(R.id.nav);
        nav.setOnClickListener(this);
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
    public void onSwipe(int direction) {
        String str = "";
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT :
                layouttobring.startAnimation(animUp);
                layouttobring.setVisibility(View.GONE);
                flag=0;
                str = "Swipe Right";
                break;
            case SimpleGestureFilter.SWIPE_LEFT :
                layouttobring.startAnimation(animDown);
                layouttobring.setVisibility(View.VISIBLE);
                flag=1;
                // post.setVisibility(View.INVISIBLE);

                /*if (str_profile_picture1.equals("")){

                    new DownloadImageTask1(slide_picture).execute("http://bonunio.56h7jbmx8k.us-west-2.elasticbeanstalk.com" + str_profile_picture);
                }

                else {
                    new DownloadImageTask1(slide_picture).execute( str_profile_picture1);
                }*/
                str = "Swipe Left";
                break;
            case SimpleGestureFilter.SWIPE_DOWN :
                break;
            case SimpleGestureFilter.SWIPE_UP :
                break;

        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nav:
                if (flag==0){
                    nav.setImageResource(R.drawable.nav_fill);
                    layouttobring.setVisibility(View.VISIBLE);
                    layouttobring.startAnimation(animDown);
                    flag=1;
                }

                else{
                    nav.setImageResource(R.drawable.nav);
                    layouttobring.setVisibility(View.GONE);
                    layouttobring.startAnimation(animUp);
                    flag=0;
                }

                break;
        }
    }
}
