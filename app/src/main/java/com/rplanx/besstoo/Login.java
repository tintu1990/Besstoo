package com.rplanx.besstoo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.rplanx.besstoo.constant.Constant;

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
    SharedPreferences sharedPreferences;
    SharedPreferences .Editor editor;
    String str_time;
    String str_tag;
    String str_no_of_people;
    String str_id;
    //String JSON_FORGOTPASS="http://besstoo.com/public/service/forget_password";
    String JSON_FORGOTPASS=Constant.URL+"forget_password";

    String str_date="";
    String str_discount;
    String str_tax;
    String str_delevery_charge;
    String str_tag1;
    String str_reg="";
    TextView forgot_password;
    final Context c = this;
    String m = "";

    //String JSON_URI="http://besstoo.com/public/service/do_login";
    String JSON_URI= Constant.URL+"do_login";

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
        forgot_password=(TextView)findViewById(R.id.forgot_pass);
        forgot_password.setOnClickListener(this);
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
        if (getIntent().hasExtra("time")){
            str_time=getIntent().getStringExtra("time");
        }
        if (getIntent().hasExtra("tag")){
            str_tag=getIntent().getStringExtra("tag");
        }

        if (getIntent().hasExtra("no_of_people")){
            str_no_of_people=getIntent().getStringExtra("no_of_people");
        }

        if (getIntent().hasExtra("date")){
            str_date=getIntent().getStringExtra("date");
        }

        if (getIntent().hasExtra("reg")){
            str_reg=getIntent().getStringExtra("reg");
        }
        if (getIntent().hasExtra("tag1")){
            str_tag1=getIntent().getStringExtra("tag1");
        }

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

            case R.id.forgot_pass:

                LayoutInflater layoutInflater=LayoutInflater.from(c);
                View mView = layoutInflater.inflate(R.layout.forgot_password, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(true)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                m=userInputDialogEditText.getText().toString();
                                if (m.equals("")){
                                    Toast.makeText(Login.this,"field cannot be empty",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Send();
                                }
                                // ToDo get user input here
                                dialogBox.cancel();
                            }
                        });



                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();


               /* LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.otp_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                // if (!message.equals("")){
                //     userInputDialogEditText.setText(message);
                // }

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setNegativeButton("Resend OTP", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               // sendRequest3();
                            }
                        })
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                m=userInputDialogEditText.getText().toString();
                                if (m.equals("")){
                                    Toast.makeText(Login.this,"you have not put the otp",Toast.LENGTH_LONG).show();
                                }
                                else{
                                  //  sendRequest1();
                                }

                                // ToDo get user input here
                                dialogBox.cancel();
                            }
                        });



                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();*/



                break;

            case R.id.nav:
                //final LayoutInflater factory = getLayoutInflater();
                //final View textEntryView = factory.inflate(R.layout.food_list1, null);
               // ListView listView=(ListView)textEntryView.findViewById(R.id.list1);
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
               // Intent intent1=new Intent(Login.this,Food_list.class);
                //startActivity(intent1);
                onBackPressed();
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
                    sharedPreferences =getSharedPreferences("login", Context.MODE_PRIVATE);
                    editor=sharedPreferences.edit();
                    editor.putString("email",str_emobile);
                    editor.apply();
                    sendRequest1();
                }
        }
    }

    @Override
    public void onSwipe(int direction) {
       // String str = "";
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT :
                if (flag==1) {
                    nav.setImageResource(R.drawable.navs);
                    layouttobring.startAnimation(animUp);
                    layouttobring.setVisibility(View.GONE);
                    flag = 0;
                   // str = "Swipe Right";
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
                         progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("Status")) {
                                String strm = jsonObject.getString("Status");
                                if (strm.equals("Failure")) {
                                    Toast.makeText(Login.this, jsonObject.getString("Status"), Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                               editor=sharedPreferences.edit();
                                editor.putString("user_id",jsonObject.getString("user_id"));
                                Log.e("user",jsonObject.getString("user_id"));
                                editor.putString("email1",jsonObject.getString("user_email"));
                                editor.putString("mobile",jsonObject.getString("user_mobile"));
                                editor.putString("name",jsonObject.getString("user_name"));
                                str_id=jsonObject.getString("user_id");
                                editor.apply();

                                if (str_reg.equals("")){
                                    Intent intent=new Intent(Login.this,Address_list.class);
                                    intent.putExtra("time",str_time);
                                    intent.putExtra("tag",str_tag);
                                    intent.putExtra("no_of_people",str_no_of_people);
                                    intent.putExtra("tag1",str_tag1);
                                    intent.putExtra("date",str_date);
                                    startActivity(intent);
                                    /* if (str_date.equals("")){
                                        Intent intent=new Intent(Login.this,Address_list.class);
                                        intent.putExtra("time",str_time);
                                        intent.putExtra("tag",str_tag);
                                        intent.putExtra("no_of_people",str_no_of_people);
                                        intent.putExtra("tag1",str_tag1);
                                        startActivity(intent);
                                    }

                                    else{
                                        Intent intent=new Intent(Login.this,Address_list.class);
                                        intent.putExtra("time",str_time);
                                        intent.putExtra("tag",str_tag);
                                        intent.putExtra("no_of_people",str_no_of_people);
                                        intent.putExtra("tag1",str_tag1);
                                        intent.putExtra("date",str_date);
                                        startActivity(intent);
                                    }*/
                                }
                                else{
                                    Intent intent=new Intent(Login.this,Food_list.class);
                                    startActivity(intent);
                                    finish();
                                }

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

       /* stringRequest.setRetryPolicy(new RetryPolicy() {
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
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }


    private void   Send(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_FORGOTPASS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Login.this,"Email has been sent successfully",Toast.LENGTH_SHORT).show();
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
                     //   progressDialog.dismiss();
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
                params.put("email", m);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

       /* stringRequest.setRetryPolicy(new RetryPolicy() {
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
       /* progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Logging please wait....");
        progressDialog.show();*/
    }

   @Override
    public void onBackPressed() {
       super.onBackPressed();
       /*if (str_reg.equals("")) {
           Intent intent = new Intent(Login.this, Food_list.class);
           startActivity(intent);
           intent.putExtra("login","login");
           finish();
       }
       else{
           Intent intent = new Intent(Login.this, Register.class);
           startActivity(intent);
           finish();
       }*/

    }





}
