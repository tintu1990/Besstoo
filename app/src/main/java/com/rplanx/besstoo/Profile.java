package com.rplanx.besstoo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rplanx.besstoo.constant.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends Activity implements View.OnClickListener{
    RelativeLayout back;
    RelativeLayout log_out;
    EditText name;
    EditText email;
    SharedPreferences sh_p;
    TextView ch_password;
    final Context c = this;
    String m;
    String str_uid;
    SharedPreferences sharedPreferences;
    Button btn_submit;
    String str_name;
    //String CHANGE_PASS="http://besstoo.com/public/service/change_pass";
    String CHANGE_PASS= Constant.URL+"change_pass";
    String CHANGE_NAME=Constant.URL+"change_name";
    // String CHANGE_NAME="http://besstoo.com/public/service/change_name";
    SharedPreferences user_pro;
    SharedPreferences .Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        back=(RelativeLayout)findViewById(R.id.back_layout);
        log_out=(RelativeLayout)findViewById(R.id.r_logout);
        sh_p=getSharedPreferences("login",Context.MODE_PRIVATE);
        log_out.setOnClickListener(this);
        back.setOnClickListener(this);
        name=(EditText)findViewById(R.id.edt_name);
        email=(EditText)findViewById(R.id.edt_email);
        btn_submit=(Button)findViewById(R.id.btn_update);
        user_pro=getSharedPreferences("user_pro",Context.MODE_PRIVATE);
        btn_submit.setOnClickListener(this);
        if (user_pro.contains("name")){
            name.setText(user_pro.getString("name",""));
        }
        else {
            name.setText(sh_p.getString("name",""));
        }
       // name.setText(sh_p.getString("name",""));
        email.setText(sh_p.getString("email1",""));
        str_uid=sh_p.getString("user_id","");
        email.setEnabled(false);
        ch_password=(TextView)findViewById(R.id.ch_password);
        ch_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.change_pass, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(true)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                m=userInputDialogEditText.getText().toString();
                                if (m.equals("")){
                                    Toast.makeText(Profile.this,"you have not put the password",Toast.LENGTH_LONG).show();
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
        });
        email.setKeyListener(null);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                onBackPressed();
                break;
            case R.id.r_logout:
                Constant.counter=0;
                SharedPreferences profile_pref=getSharedPreferences("user_pro",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor4 = profile_pref.edit();
                editor4.clear();
                editor4.apply();
                SharedPreferences sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                SharedPreferences shr=getSharedPreferences("Party_pref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1=shr.edit();
                editor1.clear().apply();
                SharedPreferences sher=getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);
                SharedPreferences .Editor e=sher.edit();
                e.clear().apply();
                SharedPreferences shre=getSharedPreferences("MyPREFERENCES1",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2=shre.edit();
                editor2.clear().apply();
                Intent intent =new Intent(Profile.this,Food_list.class);
                intent.putExtra("login","login");
                startActivity(intent);
                finish();
                break;
            case R.id.btn_update:
                str_name=name.getText().toString();
                send();

                break;
        }

    }


    private  void  send(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHANGE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            name.setText(jsonObject.getString("Status"));
                            editor=user_pro.edit();
                            editor.putString("name",jsonObject.getString("Status"));
                            editor.apply();
                            Toast.makeText(getApplicationContext(),"Name Updated successfully",Toast.LENGTH_SHORT).show();
                           // Toast.makeText(Profile.this,jsonObject.getString("Status"),Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            String errorMessage = error.getClass().getSimpleName();
                            if (!TextUtils.isEmpty(errorMessage)) {
                                Toast.makeText(Profile.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", str_name);
                params.put("user_id",str_uid);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        stringRequest.setRetryPolicy(new RetryPolicy() {
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
        });

    }

    private  void sendRequest1(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHANGE_PASS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Profile.this,jsonObject.getString("Status"),Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            String errorMessage = error.getClass().getSimpleName();
                            if (!TextUtils.isEmpty(errorMessage)) {
                                Toast.makeText(Profile.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("password", m);
                params.put("user_id",str_uid);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        stringRequest.setRetryPolicy(new RetryPolicy() {
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
        });

    }
}
