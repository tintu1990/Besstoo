package com.rplanx.besstoo;

import android.Manifest.permission;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class Register extends AppCompatActivity implements  View.OnClickListener ,SimpleGestureFilter.SimpleGestureListener{
    RelativeLayout rl_back;
    ImageView imageView;
    final Context c = this;
    String message = "";
    EditText email;
    EditText password;
    EditText name;
    EditText mobile;
    String str_email;
    String str_mobile;
    String str_password;
    String str_fullname;
    View.OnClickListener mOnClickListener;
    CoordinatorLayout coordinatorLayout;
    ProgressDialog progressDialog;
    String m = "";
    String str_intent="";
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
    String JSON_URL = "http://192.168.3.8/besstoo/public/service/do_register";
    String JSON_URI = "http://192.168.3.8/besstoo/public/service/otp_verification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(R.id.edt_password);
        name = (EditText) findViewById(R.id.edt_name);
        nav=(ImageView)findViewById(R.id.nav);
        mobile = (EditText) findViewById(R.id.edt_phone);
        rl_back = (RelativeLayout) findViewById(R.id.back_layout);
        animUp = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
        animDown = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        layouttobring = findViewById(R.id.nav_slide);
        profile=(RelativeLayout)layouttobring.findViewById(R.id.first_r);
        Display mDisplay = Register.this.getWindowManager().getDefaultDisplay();
        width = mDisplay.getWidth();
        height = mDisplay.getHeight();
        slide_menu_layout=(RelativeLayout)layouttobring.findViewById(R.id.slide_menu_layout1);
        nav.setOnClickListener(this);
        profile.setOnClickListener(this);
        simple_detector = new SimpleGestureFilter(this,this);
        if (getIntent().hasExtra("login")){
            str_intent=getIntent().getStringExtra("login");
        }
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        imageView = (ImageView) findViewById(R.id.btn_save);
        if (ContextCompat.checkSelfPermission(c, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Register.this, new String[]{permission.RECEIVE_SMS, permission.READ_SMS}, 1);
        }

        imageView.setOnClickListener(this);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (str_intent.equals("")){
                    Intent intent = new Intent(Register.this, Food_list.class);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(Register.this,Login.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first_r:
                layouttobring.startAnimation(animUp);
                layouttobring.setVisibility(View.GONE);
                nav.setImageResource(R.drawable.navs);

                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Intent intent=new Intent(Register.this,Register.class);
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

            case R.id.btn_save:
                str_email = email.getText().toString();
                str_password = password.getText().toString();
                str_mobile = mobile.getText().toString();
                str_fullname = name.getText().toString();
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Message", Snackbar.LENGTH_LONG)
                        .setAction("Undo", mOnClickListener);
                snackbar.setActionTextColor(Color.RED);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.DKGRAY);
                if (str_fullname.isEmpty() || str_password.isEmpty() || str_mobile.isEmpty() || str_email.isEmpty()) {

                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setText("Fields cannot be empty");
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setText("Enter  valid email");
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                } else {
                    sendRequest();
                }


                /*LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.otp_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                if (!message.equals("")){
                    userInputDialogEditText.setText(message);
                }

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                dialogBox.cancel();
                            }
                        });


                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();*/


                break;
        }
    }


    private void sendRequest1() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Register.this,jsonObject.getString("Status"),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // progressDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            String errorMessage = error.getClass().getSimpleName();
                            if (!TextUtils.isEmpty(errorMessage)) {
                                Toast.makeText(Register.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_mobile", str_mobile);
                params.put("otp",m);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
//        progressDialog = new ProgressDialog(.this);
//        progressDialog.setMessage("Fetching The File....");
//        progressDialog.show();
    }




    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.getString("Status").equals("Success")){
                                 LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.otp_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                if (!message.equals("")){
                    userInputDialogEditText.setText(message);
                }

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                m=userInputDialogEditText.getText().toString();
                                if (m.equals("")){
                                    Toast.makeText(Register.this,"you have not put the otp",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    sendRequest1();
                                }

                                // ToDo get user input here
                                dialogBox.cancel();
                            }
                        });


                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();



                            }
                            else{
                                Toast.makeText(Register.this,jsonObject.getString("Status"),Toast.LENGTH_LONG).show();
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
                        if(response != null && response.data != null){
                            String errorMessage=error.getClass().getSimpleName();
                            if(!TextUtils.isEmpty(errorMessage)){
                                Toast.makeText(Register.this,"errorMessage:"+errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("user_email",str_email);
                params.put("user_mobile_number",str_mobile);
                params.put("user_password",str_password);
                params.put("user_fullname",str_fullname);
                return  params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage("Registering please wait....");
        progressDialog.show();
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                message = intent.getStringExtra("message");
                //Do whatever you want with the code here
            }
        }
    };

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
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
    public void onDoubleTap() {

    }
}
