package com.rplanx.besstoo;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.baoyz.widget.PullRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rplanx.besstoo.adapter.CartAdapter;
import com.rplanx.besstoo.adapter.CustomAdapter;
import com.rplanx.besstoo.application.VolleyApplication;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.interfaces.CustomButtonListener;
import com.rplanx.besstoo.interfaces.ModelDataSet;
import com.rplanx.besstoo.network.GpsTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by soumya on 24/2/17.
 */
@SuppressWarnings("ALL")
public class Besstoo_Kitchen extends Fragment  {
    public static final String ARG_PAGE = "ARG_PAGE";

   String JSON_URL1=Constant.URL + "kit/all/0";
    //String JSON_URL1="http://192.168.3.39/besstoo/service/kitchen/all/0";
    String JSON=Constant.URL + "all_locations";
    CustomAdapter customAdapter;
    ListView lv;
    Products products;
    TextView textview;
    Model2 model2;
    ImageView cart;
    SharedPreferences.Editor editor;
    AutoCompleteTextView txt_address;
    AutoCompleteTextView text;
    int dflag=0;
    int new_flag;
    private String format ="";
    private int mHour, mMinute;
    RadioGroup selecttime;
    FloatingActionButton floatingActionButton;
    RadioButton male;
    Button btn_proceed;
    ProgressDialog progressDialog;
    ArrayList<String>list;
    ProgressBar progressBar;
    String str_time="15:00:00";
    String str_time_for_next="16:00:00";
    String str_time_for_order="12:00:00";
    GpsTracker gpsTracker;
    Double latitude;
    Double longitude;
    List<Address> addresses;
    String str_my_address;
    View headerView;
    private int mPage;
    CartAdapter cartAdapter;
    SharedPreferences shref;
    SharedPreferences shr;
    SharedPreferences .Editor editor1;
    SharedPreferences .Editor editor2;
    Button btn_proceed1;
    SharedPreferences party_pref;
    //SwipeRefreshLayout mSwipeRefreshLayout;
    RadioButton twelve;
    TextView txt_day;
    String formattedDate1;
    int FLAG_DAY=0;
    int flag_for_date=0;
    PullRefreshLayout mSwipeRefreshLayout;
    String message="Please clear your cart to proceed";



    public static Besstoo_Kitchen newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Besstoo_Kitchen fragment = new Besstoo_Kitchen();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.food_list1,container,false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSwipeRefreshLayout = (PullRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout);
       // setRetainInstance(true);
        txt_address=(AutoCompleteTextView)getActivity().findViewById(R.id.txt_address);
        //  text=(AutoCompleteTextView)getActivity().findViewById(R.id.select_address);
        list=new ArrayList<>();
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        shref=getActivity().getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);
        shr=getActivity().getSharedPreferences("MyPREFERENCES1",Context.MODE_PRIVATE);
        party_pref=getActivity().getSharedPreferences("Party_pref",Context.MODE_PRIVATE);
        textview = (TextView) getActivity().findViewById(R.id.badge_notification_1);
        progressBar=(ProgressBar)getActivity().findViewById(R.id.progressBar);
        str_my_address=txt_address.getText().toString();
        str_my_address = str_my_address.replace(' ', '+');
        cart=(ImageView)getActivity().findViewById(R.id.noti);
        headerView = (View) layoutInflater.inflate(R.layout.no_internet, lv, false);
      //  mSwipeRefreshLayout.setDistanceToTriggerSync(5);
        Calendar to_date = Calendar.getInstance();

        if(to_date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            FLAG_DAY=1;
            System.out.println("OK");
        }
        else {
            FLAG_DAY=0;
        }
        floatingActionButton=(FloatingActionButton)getView().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent=new Intent(getActivity(),Login.class);
              //  startActivity(intent);
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.time_picker);
                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.show();
                txt_day=(TextView)dialog.findViewById(R.id.today);
                twelve=(RadioButton)dialog.findViewById(R.id.rb_boy);
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
                final String formattedDate = df.format(c.getTime());
                final  String str_current_time= dateFormat.format(c.getTime());
                Date d1 = null;
                Date d2 = null;
                Date d5=null;
                 try {
                     d5=dateFormat.parse(str_time_for_next);
                     d1 = dateFormat.parse(str_time);
                     d2 = dateFormat.parse(str_current_time);
                     //in milliseconds
                     long next_diff=d5.getTime()-d2.getTime();
                     long diff = d1.getTime() - d2.getTime();
                     long diffSeconds = diff / 1000 % 60;
                     long diffMinutes = diff / (60 * 1000) % 60;
                     long diffHours = diff / (60 * 60 * 1000) % 24;
                     long diffDays = diff / (24 * 60 * 60 * 1000);

                     System.out.print(diffDays + " days, ");
                     System.out.print(diffHours + " hours, ");
                     System.out.print(diffMinutes + " minutes, ");
                     System.out.print(diffSeconds + " seconds.");


                 //    if (!Constant.exercise_arraylist1.get(0).getForDay().equals("Saturday")){
                     if (FLAG_DAY==0){
                         if (diff<0 && next_diff>0){
                             dialog.dismiss();
                             outOftime("you cannot order after 3 pm for today");
                         }
                         if (next_diff<0){
                             flag_for_date=1;
                             txt_day.setText("TOMORROW");
                             Calendar calendar = Calendar.getInstance();
                             calendar.add(Calendar.DAY_OF_YEAR, 1);
                             formattedDate1=df.format(calendar.getTime());
                         }
                         else{
                             txt_day.setText("TODAY");
                         }
                     }
                     else{
                         if (diff<0){
                             dialog.dismiss();
                             outOftime("you cannot order after 3 pm for today");
                         }
                     }



                  //   }
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
                    d6=dateFormat_format.parse(str_time_for_next);
                    //in milliseconds
                    long diff = d3.getTime() - d4.getTime();
                    long diff_next=d6.getTime()-d4.getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    System.out.print(diffDays + " days, ");
                    System.out.print(diffHours + " hours, ");
                    System.out.print(diffMinutes + " minutes, ");
                    System.out.print(diffSeconds + " seconds.");
                   // if (Constant.)

                //    if (!Constant.exercise_arraylist1.get(0).getForDay().equals("Saturday")){

                    if (FLAG_DAY==0){
                        if (diff_next>0){
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


                  //  }
                   /* */
                }
                catch (Exception e) {
                    e.printStackTrace();
                }



                selecttime=(RadioGroup)dialog.findViewById(R.id.radiogrp1);
                selecttime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        male = (RadioButton)dialog.findViewById(i);
                    }
                });
                btn_proceed=(Button)dialog.findViewById(R.id.button_sub);
                btn_proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  dialog.dismiss();
                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
                       // if (sharedPreferences.contains("user_id")){
                            if (male==null){
                                Toast.makeText(getActivity(),"You have to select a time slot",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                dialog.dismiss();
                                if (flag_for_date==0) {
                                    if (sharedPreferences.contains("user_id")) {
                                        Intent intent = new Intent(getActivity(), Address_list.class);
                                        intent.putExtra("time", male.getText().toString());
                                        intent.putExtra("tag", "menu");
                                        intent.putExtra("date", formattedDate);
                                        intent.putExtra("no_of_people", "1");
                                        intent.putExtra("tag1", "menu");
                                        //intent.putExtra("page",);
                                        startActivity(intent);

                                    } else {
                                        Intent intent = new Intent(getActivity(), Login.class);
                                        // intent.putExtra("tag1","order");
                                        intent.putExtra("time", male.getText().toString());
                                        intent.putExtra("date", formattedDate);
                                        intent.putExtra("tag", "menu");
                                        intent.putExtra("no_of_people", "1");
                                        intent.putExtra("tag1", "menu");
                                        startActivity(intent);

                                    }
                                }
                                else{
                                    if (sharedPreferences.contains("user_id")) {
                                        Intent intent = new Intent(getActivity(), Address_list.class);
                                        intent.putExtra("time", male.getText().toString());
                                        intent.putExtra("tag", "menu");
                                        intent.putExtra("date", formattedDate1);
                                        intent.putExtra("no_of_people", "1");
                                        intent.putExtra("tag1", "menu");
                                        //intent.putExtra("page",);
                                        startActivity(intent);

                                    } else {
                                        Intent intent = new Intent(getActivity(), Login.class);
                                        // intent.putExtra("tag1","order");
                                        intent.putExtra("time", male.getText().toString());
                                        intent.putExtra("date", formattedDate1);
                                        intent.putExtra("tag", "menu");
                                        intent.putExtra("no_of_people", "1");
                                        intent.putExtra("tag1", "menu");
                                        startActivity(intent);

                                    }
                                }

                            }

                    }
                });
            }
        });


        lv= (ListView) getView().findViewById(R.id.list1);
        txt_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager in = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String selection = (String)adapterView.getItemAtPosition(i);
                txt_address.setText(selection);
                txt_address.clearFocus();
                //Log.e("selection",selection);
                selection = selection.replace(' ', '+');
                JSON_URL1=Constant.URL + "kit/" + selection +"/0";
                //JSON_URL1="http://192.168.3.39/besstoo/service/kitchen/" + selection +"/0";
                fetch();
                /* if (dflag==1){
                    dflag=0;
                    text.setVisibility(View.GONE);
                    JSON_URL1=Constant.URL + "kitchen/" + selection +"/0";
                    fetch();
                }*/
            }
        });

        txt_address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int itemid = android.R.layout.simple_dropdown_item_1line;
                String[] str_rad = getResources().getStringArray(R.array.location);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), itemid, list);
                txt_address.setAdapter(arrayAdapter);
                txt_address.setThreshold(1);
                InputMethodManager in = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), 0);
                txt_address.showDropDown();
                return false;
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetch();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });



        fetch();
    }
    private final  CustomButtonListener productClickListener = new CustomButtonListener() {
        @Override
        public void onMinusClick(Model2 product) {
            products.removeOneFrom(product);
            customAdapter.notifyDataSetChanged();
            SharedPreferences p=getActivity().getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);
            Constant.counter=p.getInt("incr",0);
            /*  if (Constant.counter==0){
                textview.setVisibility(View.INVISIBLE);
                floatingActionButton.setVisibility(View.GONE);
            }*/


            if (Constant.counter>0 &&Constant.counter==1){
                    Constant.counter--;
                    floatingActionButton.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    textview.setText(String.valueOf(Constant.counter));
                    SharedPreferences prefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("incr", Constant.counter);
                    editor.apply();
            }
            else if (Constant.counter>0 &&Constant.counter>1){
                Constant.counter--;
                floatingActionButton.setVisibility(View.VISIBLE);
                textview.setVisibility(View.VISIBLE);
                textview.setText(String.valueOf(Constant.counter));
                SharedPreferences prefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("incr", Constant.counter);
                editor.apply();
            }


            else
            {
                textview.setVisibility(View.INVISIBLE);
                floatingActionButton.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPlusClick(Model2 product) {
            int n= Integer.parseInt(product.getNo_of_item());
            int n1=product.getQuantity();
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
            final String formattedDate = df.format(c.getTime());
            final  String str_current_time= dateFormat.format(c.getTime());
            Date d1 = null;
            Date d2 = null;
            Date d3=null;
            try {
                d1 = dateFormat.parse(str_time);
                d2 = dateFormat.parse(str_current_time);
                d3 = dateFormat.parse(str_time_for_next);
                //in milliseconds
                long diff = d1.getTime() - d2.getTime();
                long diff_for_tomorrow = d3.getTime() - d2.getTime();
                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);
                System.out.print(diffDays + " days, ");
                System.out.print(diffHours + " hours, ");
                System.out.print(diffMinutes + " minutes, ");
                System.out.print(diffSeconds + " seconds.");
              //  if (!product.getForDay().equals("Saturday")) {

                if (FLAG_DAY==0){
                    if (diff < 0 && diff_for_tomorrow > 0) {
                    outOftime("you cannot order after 3 pm");

                } else {
                    if (shr.contains("myJson") || party_pref.contains("myJson")) {
                        clearcart(message);


                    } else {
                        if (n1 < n) {
                            products.addOneTo(product);
                            customAdapter.notifyDataSetChanged();
                            SharedPreferences p = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                            Constant.counter = p.getInt("incr", 0);
                            //  textview = (TextView) getActivity().findViewById(R.id.badge_notification_1);
                            if (Constant.counter >= 0) {
                                textview.setVisibility(View.VISIBLE);
                                floatingActionButton.setVisibility(View.VISIBLE);
                            } else {
                                floatingActionButton.setVisibility(View.GONE);
                                textview.setVisibility(View.INVISIBLE);
                            }
                            Constant.counter++;
                            SharedPreferences prefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("incr", Constant.counter);
                            editor.apply();
                            textview.setText(String.valueOf(Constant.counter));
                        } else {
                            outOftime("Sorry! Sold Out");
                            // Toast.makeText(getContext(), "Out of stock", Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }

               else{
                    if (diff<0){
                        outOftime("you cannot order after 3 pm");
                    }
                    else {
                        if (shr.contains("myJson") || party_pref.contains("myJson")) {
                            clearcart(message);


                        } else {
                            if (n1 < n) {
                                products.addOneTo(product);
                                customAdapter.notifyDataSetChanged();
                                SharedPreferences p = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                Constant.counter = p.getInt("incr", 0);
                                //  textview = (TextView) getActivity().findViewById(R.id.badge_notification_1);
                                if (Constant.counter >= 0) {
                                    textview.setVisibility(View.VISIBLE);
                                    floatingActionButton.setVisibility(View.VISIBLE);
                                } else {
                                    floatingActionButton.setVisibility(View.GONE);
                                    textview.setVisibility(View.INVISIBLE);
                                }
                                Constant.counter++;
                                SharedPreferences prefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("incr", Constant.counter);
                                editor.apply();
                                textview.setText(String.valueOf(Constant.counter));
                            } else {
                                outOftime("Sorry! Sold Out");
                                // Toast.makeText(getContext(), "Out of stock", Toast.LENGTH_LONG).show();
                            }
                        }
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

    private static class Products  implements ModelDataSet {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
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
            Constant.exercise_arraylist1.clear();
            Constant.exercise_arraylist2.clear();
            int i = productList.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            Model2 model=product;
            if (model.getQuantity()>0) {
                model.setQuantity(model.getQuantity() - 1);
                model.setImage(model.getImage());
                model.setRuppee(model.getRuppee());
                model.setDescription(model.getDescription());
                model.setNo_of_item(model.getNo_of_item());
                model.setId(model.getId());
                model.setStr_food_name(model.getStr_food_name());
                model.setForDay(model.getForDay());
                productList.remove(product);
                productList.add(i, model);
                Constant.exercise_arraylist1.addAll(productList);
               // Constant.exercise_arraylist2.addAll(productList);
                SharedPreferences shref;
                SharedPreferences.Editor editor;
                shref = context.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                editor=shref.edit();
                Gson gson = new Gson();
                String json = gson.toJson(Constant.exercise_arraylist1);
                editor.putString("myJson", json);
                editor.apply();
            }
        }
        public void addOneTo(Model2 product) {
            Constant.exercise_arraylist1.clear();
            Constant.exercise_arraylist2.clear();
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
                 model.setForDay(model.getForDay());
                productList.remove(product);
                productList.add(i, model);
                Constant.exercise_arraylist1.addAll(productList);
                Constant.exercise_arraylist2.addAll(productList);
                SharedPreferences shref;
                SharedPreferences.Editor editor;
                shref = context.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String json = gson.toJson(Constant.exercise_arraylist1);
                editor=shref.edit();
                editor.putString("myJson", json);
                editor.apply();
            }
        }
            private void fetch() {
                progressBar.setVisibility(View.VISIBLE);
                JsonArrayRequest request = new JsonArrayRequest(JSON_URL1,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonObject) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            List<Model2> imageRecords = parse(jsonObject);
                            SharedPreferences sp=getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                            Gson gson1 = new Gson();
                            String response=sp.getString("myJson" , "");
                            try {
                                if (!response.equals("")){
                                     imageRecords=gson1.fromJson(response,
                                            new TypeToken<List<Model2>>() {
                                            }.getType());
                                }
                            }catch (java.lang.NullPointerException e){
                                e.printStackTrace();
                            }
                            if (imageRecords.size()>0){
                                products=new Products(imageRecords,getActivity());
                                customAdapter=new CustomAdapter(getActivity(),products,productClickListener,getActivity().getLayoutInflater());
                                lv.setAdapter(customAdapter);
                                customAdapter.notifyDataSetChanged();
                                fetch1();
                            }
                            else{
                                lv.addHeaderView(headerView);
                                products=new Products(imageRecords,getActivity());
                                customAdapter=new CustomAdapter(getActivity(),products,productClickListener,getActivity().getLayoutInflater());
                                lv.setAdapter(customAdapter);
                                customAdapter.notifyDataSetChanged();
                                fetch1();
                                // Toast.makeText(getContext(),"Orders are only taken from Monday to Friday. But you can still plan your Party",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (java.lang.NullPointerException e){
                            e.printStackTrace();
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                          //  Toast.makeText(getActivity(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            progressBar.setVisibility(View.GONE);
                        }catch (java.lang.NullPointerException e){
                            e.printStackTrace();
                        }

                    }
                });



        VolleyApplication.getInstance().getRequestQueue().add(request);
    }
    private List<Model2> parse(JSONArray json) throws JSONException {
        ArrayList<Model2> records = new ArrayList<>();
        try {
            for (int i=0;i<json.length();i++){
                JSONObject jsonObject=json.getJSONObject(i);
                model2=new Model2();
                model2.setImage(jsonObject.getString("image"));
                model2.setQuantity(0);
                model2.setNo_of_item(jsonObject.getString("besstookitchen_quantity"));
                model2.setRuppee(jsonObject.getString("besstookitchen_amount"));
                model2.setDescription(jsonObject.getString("besstookitchen_description"));
                model2.setId(jsonObject.getString("besstookitchen_id"));
                model2.setStr_food_name(jsonObject.getString("besstookitchen_menu_name"));
                model2.setForDay(jsonObject.getString("for_day"));
                records.add(model2);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return records;
    }
    public  void fetch1(){
        JsonArrayRequest request = new JsonArrayRequest(JSON,
                new Response.Listener<JSONArray>() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray jsonObject) {
                       // progressBar.setVisibility(View.GONE);

                        try {
                            // ArrayList<String>list=new ArrayList<>();
                            for (int i=0;i<jsonObject.length();i++){
                                JSONObject jsonObject1=jsonObject.getJSONObject(i);
                                list.add(jsonObject1.getString("location_name"));
                            }
                        }
                        catch(JSONException e) {
                          //  Toast.makeText(getActivity(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            Log.e("error", String.valueOf(volleyError));
                        }catch (java.lang.NullPointerException e){
                            e.printStackTrace();
                        }
                       // progressBar.setVisibility(View.GONE);
                        //Toast.makeText(getActivity(), "Unable to fetch data: " + "in", Toast.LENGTH_SHORT).show();
                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
       // progressBar.setVisibility(View.VISIBLE);

    }
    /*@Override
    public boolean onBackPressed() {

    }*/


    private  void  clearcart(String message){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle);
        //alertDialog.setTitle(getResources().getString(R.string.app_name));
        //alertDialog.setMessage(message);
        TextView image = new TextView(getContext());
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
                textview.setVisibility(View.INVISIBLE);

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


    private  void  outOftime(String message){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle);
       // alertDialog.setTitle(getResources().getString(R.string.app_name));
        // alertDialog.setMessage(message);
        TextView image = new TextView(getContext());
        image.setGravity(Gravity.CENTER|Gravity.BOTTOM);
        // image.setImageResource(R.drawable.besstoo_pop);
        image.setTextSize(15);
        image.setTextColor(Color.RED);
        image.setBackgroundResource(R.drawable.besstoo_pop);
        image.setText(message);
        alertDialog.setView(image);
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
