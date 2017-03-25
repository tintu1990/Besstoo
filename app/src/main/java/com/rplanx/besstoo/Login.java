package com.rplanx.besstoo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity implements View.OnClickListener,SimpleGestureFilter.SimpleGestureListener {
    EditText em_password;
    EditText em_mobile;
    ImageView button;
    String str_emobile;
    String str_em_password;
    ProgressDialog progressDialog;
    CoordinatorLayout coordinatorLayout;
    View.OnClickListener mOnClickListener;
    RelativeLayout r_back_layout;
    ImageView img_sign_up;
    private SimpleGestureFilter simple_detector;
    private Animation animUp;
    private Animation animDown;
    int flag=0;
    int width;
    int height;
    RelativeLayout profile;
    RelativeLayout slide_menu_layout;
    ImageView nav;
    View layouttobring;

    String JSON_URI="http://192.168.3.8/besstoo/public/service/do_login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nav=(ImageView)findViewById(R.id.nav);
        em_mobile=(EditText)findViewById(R.id.mobile);
        em_password=(EditText)findViewById(R.id.password);
        img_sign_up=(ImageView)findViewById(R.id.sign_up);
        r_back_layout=(RelativeLayout)findViewById(R.id.back_layout);
        r_back_layout.setOnClickListener(this);
        img_sign_up.setOnClickListener(this);
        button=(ImageView) findViewById(R.id.proceed);
        button.setOnClickListener(this);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        animUp = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
        animDown = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        layouttobring = findViewById(R.id.nav_slide);
        profile=(RelativeLayout)layouttobring.findViewById(R.id.first_r);
        Display mDisplay = Login.this.getWindowManager().getDefaultDisplay();
        width = mDisplay.getWidth();
        height = mDisplay.getHeight();
        slide_menu_layout=(RelativeLayout)layouttobring.findViewById(R.id.slide_menu_layout1);
        nav.setOnClickListener(this);
        profile.setOnClickListener(this);
        simple_detector = new SimpleGestureFilter(this,this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.first_r:
                layouttobring.startAnimation(animUp);
                layouttobring.setVisibility(View.GONE);
                nav.setImageResource(R.drawable.navs);

                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Intent intent=new Intent(Login.this,Register.class);
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

            case R.id.back_layout:
                Intent intent1=new Intent(Login.this,Food_list.class);
                startActivity(intent1);
                break;

            case R.id.sign_up:
                Intent intent =new Intent(Login.this,Register.class);
                intent.putExtra("login","login");
                startActivity(intent);
                break;

            case R.id.proceed:
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Message", Snackbar.LENGTH_LONG)
                        .setAction("Undo", mOnClickListener);
                snackbar.setActionTextColor(Color.RED);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.DKGRAY);
                str_emobile=em_mobile.getText().toString();
                str_em_password=em_password.getText().toString();
                if (str_em_password.isEmpty() ||str_emobile.isEmpty()){
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setText("Fields cannot be empty");
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
                else{
                    sendRequest1();
                }

        }
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


    private void sendRequest1() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Login.this,jsonObject.getString("Status"),Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Login.this,Food_list.class);
                            startActivity(intent);

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
                                Toast.makeText(Login.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_email_mobile", str_emobile);
                params.put("password",str_em_password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
     progressDialog = new ProgressDialog(Login.this);
      progressDialog.setMessage("Logging please wait....");
        progressDialog.show();
    }

}
