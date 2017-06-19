package com.rplanx.besstoo;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rplanx.besstoo.adapter.CartAdapter;
import com.rplanx.besstoo.adapter.CustomAdapter1;
import com.rplanx.besstoo.application.VolleyApplication;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.date.Datedialog1;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.interfaces.CustomButtonListener1;
import com.rplanx.besstoo.interfaces.ModelDataSet1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soumya on 24/2/17.
 */
@SuppressWarnings("ALL")
public class Bakery extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    ListView lv;
    TextView textview;

    String JSON=Constant .URL +"home/all/0";
   // String JSON="http://192.168.3.39/besstoo/service/homemadebakery/all/0";
    String JSON1=Constant.URL + "all_locations";
    //String JSON1="http://192.168.3.39/besstoo/service/all_locations";
    Products1 products;
    CustomAdapter1 customAdapter1;
  //  TextView txt_address;
    int dflag=0;
   // AutoCompleteTextView text;
    ArrayList<String>list;
    RadioGroup selecttime;
    RadioButton selected_time;
    FloatingActionButton floatingActionButton;
    Button btn_proceed;
    ProgressBar progressBar;
    FloatingActionMenu floatingActionMenu;
    com.github.clans.fab.FloatingActionButton time_slot;
    com.github.clans.fab.FloatingActionButton choco;
    com.github.clans.fab.FloatingActionButton muffin;
    com.github.clans.fab.FloatingActionButton cake;
    EditText no_of_people;
    TextView date;
    RadioButton time;
    String str_date;
    String str_no_of_people;
    int int_no_of_people;
    private int mPage;
    Model2 model;
    ArrayList<Model2> lists;
    View  viewFrag;
    SharedPreferences shref;
    SharedPreferences shr;
    CartAdapter cartAdapter;
    SharedPreferences .Editor editor;
    SharedPreferences.Editor editor1;
    ImageView cart;
    SharedPreferences .Editor editor2;
    SharedPreferences party_pref;
    int flag=1;
    String message="Please clear your cart to proceed";
    //SwipeRefreshLayout mSwipeRefreshLayout;
    PullRefreshLayout mSwipeRefreshLayout;

    public static Bakery newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Bakery fragment = new Bakery();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.food_list2,container,false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list=new ArrayList<>();
        lv= (ListView) getView().findViewById(R.id.list2);
        mSwipeRefreshLayout = (PullRefreshLayout) getActivity().findViewById(R.id.swipeToRefresh_bake);
        textview = (TextView) getActivity().findViewById(R.id.badge_notification_1);
        shref=getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        shr=getActivity().getSharedPreferences("MyPREFERENCES1",Context.MODE_PRIVATE);
        party_pref=getActivity().getSharedPreferences("Party_pref",Context.MODE_PRIVATE);
       // floatingActionButton=(FloatingActionButton)getView().findViewById(R.id.fab);
        progressBar=(ProgressBar)getActivity().findViewById(R.id.progressBar1);
        floatingActionMenu=(FloatingActionMenu)getView().findViewById(R.id.menu_labels_right);
        time_slot=(com.github.clans.fab.FloatingActionButton)getView().findViewById(R.id.select_time_slot);
        choco=(com.github.clans.fab.FloatingActionButton)getView().findViewById(R.id.chocolates);
        muffin=(com.github.clans.fab.FloatingActionButton)getView().findViewById(R.id.muffin);
        cake=(com.github.clans.fab.FloatingActionButton)getView().findViewById(R.id.cakes);
        cart=(ImageView)getActivity().findViewById(R.id.noti);


        muffin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
               //JSON="http://192.168.3.39/besstoo/public/service/muffin/all/0";
                JSON=Constant.URL+ "muf/all/0";
                flag=2;
                fetch();
            }
        });
        choco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
                //JSON="http://192.168.3.39/besstoo/public/service/chocolates/all/0";
                JSON=Constant.URL+ "choc/all/0";
                flag=3;
                fetch();
            }
        });
        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
               // JSON="http://192.168.3.39/besstoo/public/service/homemadebakery/all/0";
                JSON=Constant.URL + "home/all/0";
                flag=1;
                fetch();
            }
        });
        time_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.time_picker3);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.show();
               // no_of_people=(EditText)dialog.findViewById(R.id.edt_number);
                date=(TextView) dialog.findViewById(R.id.date);
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
                        Datedialog1 datedialog=new Datedialog1();
                        datedialog.getViews(view);
                        // FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                        //  datedialog.show(fragmentTransaction,"DatePicker");
                        FragmentManager fm = getActivity().getFragmentManager();
                        datedialog.show(fm,"DatePicker");
                    }
                });
                btn_proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // dialog.dismiss();
                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                      //  if (sharedPreferences.contains("user_id")){
                            str_date=date.getText().toString();
                           // no_of_people.setVisibility(View.GONE);
                          //  str_no_of_people=no_of_people.getText().toString();
                            if (time==null){
                                Toast.makeText(getActivity(),"you have to select a time slot",Toast.LENGTH_SHORT).show();
                            }
                             if (str_date.isEmpty()){
                                Toast.makeText(getActivity(),"you have to select date",Toast.LENGTH_SHORT).show();
                            }

                        if (!str_date.isEmpty()&& time!=null){
                            dialog.dismiss();
                                if (sharedPreferences.contains("user_id")){
                                    Intent intent=new Intent(getActivity(),Address_list.class);
                                    intent.putExtra("time",time.getText().toString());
                                    // intent.putExtra("no_of_people",str_no_of_people);
                                    intent.putExtra("date",str_date);
                                    intent.putExtra("tag","menu");
                                    intent.putExtra("tag1","bake");
                                    intent.putExtra("no_of_people","1");
                                    startActivity(intent);
                                }
                                else{
                                    Intent intent=new Intent(getActivity(),Login.class);
                                    intent.putExtra("time",time.getText().toString());
                                    // intent.putExtra("no_of_people",str_no_of_people);
                                    intent.putExtra("date",str_date);
                                    intent.putExtra("no_of_people","1");
                                    intent.putExtra("tag1","bake");
                                    intent.putExtra("tag","menu");
                                    startActivity(intent);
                                }
                        }
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetch();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetch();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });*/
        fetch();
    }


   private  final  CustomButtonListener1 customButtonListener1= new CustomButtonListener1(){
       @Override
       public void onMinusClick(Model2 product) {
           products.removeOneFrom(product);
           customAdapter1.notifyDataSetChanged();
           SharedPreferences p=getActivity().getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);
           Constant.counter=p.getInt("incr",0);
           if (Constant.counter>0&&Constant.counter==1) {
               Constant.counter--;
              // time_slot.setVisibility(View.INVISIBLE);
              // textview = (TextView)getActivity().findViewById(R.id.badge_notification_1);
               textview.setVisibility(View.INVISIBLE);
               textview.setText(String.valueOf(Constant.counter));
               SharedPreferences prefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = prefs.edit();
               editor.putInt("incr", Constant.counter);
               editor.apply();
           }
           else if (Constant.counter>0 &&Constant.counter>1){
               Constant.counter--;
              // floatingActionButton.setVisibility(View.VISIBLE);
             //  time_slot.setVisibility(View.VISIBLE);
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
               time_slot.setVisibility(View.GONE);
           }
       }
       @Override
       public void onPlusClick(Model2 product) {
           int n= Integer.parseInt(product.getNo_of_item());
           int n1=product.getQuantity();
           if (shref.contains("myJson")||party_pref.contains("myJson")) {
               clearcart(message);

           }


           else {
               if (n1 < n) {
                   products.addOneTo(product);
                   customAdapter1.notifyDataSetChanged();
                   SharedPreferences p = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                   Constant.counter = p.getInt("incr", 0);
                   textview = (TextView) getActivity().findViewById(R.id.badge_notification_1);
                   if (Constant.counter >= 0) {
                       textview.setVisibility(View.VISIBLE);
                       // time_slot.setVisibility(View.VISIBLE);
                   } else {
                       textview.setVisibility(View.INVISIBLE);
                       //  time_slot.setVisibility(View.GONE);
                   }
                   Constant.counter++;
                   SharedPreferences prefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = prefs.edit();
                   editor.putInt("incr", Constant.counter);
                   editor.apply();
                   textview.setText(String.valueOf(Constant.counter));
               } else {
                   //Toast.makeText(getContext(), "Out of stock", Toast.LENGTH_LONG).show();
                   outOftime("Sorry! Sold Out");
               }
           }

       }
   };
    private static class Products1  implements ModelDataSet1 {

        private final List<Model2> productListS;

        Context context;

        private Products1(List<Model2> productList,Context context) {
            this.productListS = productList;
            this.context=context;
        }

        @Override
        public int size() {
            return productListS.size();
        }

        @Override
        public Model2 get(int position) {
            return productListS.get(position);
        }

        @Override
        public long getId(int position)  {
            return position;
        }

        public void removeOneFrom(Model2 product) {
            Constant.exercise_arraylist1.clear();
            int i = productListS.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            Model2 model=product;
            SharedPreferences sp=context.getSharedPreferences("MyPREFERENCES1", Context.MODE_PRIVATE);
            Gson gson1 = new Gson();
            String response=sp.getString("myJson" , "");

            if (model.getQuantity()>0) {
                model.setQuantity(model.getQuantity() - 1);
                model.setImage(model.getImage());
                model.setRuppee(model.getRuppee());
                model.setDescription(model.getDescription());
                model.setNo_of_item(model.getNo_of_item());
                model.setId(model.getId());
                model.setStr_food_name(model.getStr_food_name());
                model.setFrom_kitchen(model.getFrom_kitchen());
                productListS.remove(product);
                productListS.add(i, model);
                Constant.exercise_arraylist1.addAll(productListS);
                SharedPreferences shref;
                SharedPreferences.Editor editor;
                shref = context.getSharedPreferences("MyPREFERENCES1", Context.MODE_PRIVATE);
                editor=shref.edit();
                Gson gson = new Gson();
                String json = gson.toJson(Constant.exercise_arraylist1);
                editor.putString("myJson", json);
                editor.apply();
            }
        }

        public void addOneTo(Model2 product) {
            Constant.exercise_arraylist1.clear();
            int i = productListS.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            SharedPreferences sp=context.getSharedPreferences("MyPREFERENCES1", Context.MODE_PRIVATE);
            Gson gson1 = new Gson();
            //noinspection UnusedAssignment,UnusedAssignment
            String response=sp.getString("myJson" , "");
            Model2 model=product;
            model.setQuantity(model.getQuantity() + 1);
            model.setRuppee(model.getRuppee());
            model.setDescription(model.getDescription());
            model.setNo_of_item(model.getNo_of_item());
            model.setImage(model.getImage());
            model.setId(model.getId());
            model.setStr_food_name(model.getStr_food_name());
            model.setFrom_kitchen(model.getFrom_kitchen());
            productListS.remove(product);
            productListS.add(i, model);
            Constant.exercise_arraylist1.addAll(productListS);
            SharedPreferences shref;
            SharedPreferences.Editor editor;
            shref = context.getSharedPreferences("MyPREFERENCES1", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = gson.toJson(Constant.exercise_arraylist1);
            editor=shref.edit();
            editor.putString("myJson", json);
            editor.apply();

        }
    }
    private void fetch() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(JSON,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonObject) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            List<Model2> imageRecords=parse(jsonObject);
                            SharedPreferences sp=getActivity().getSharedPreferences("MyPREFERENCES1", Context.MODE_PRIVATE);
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
                            products=new Products1(imageRecords,getActivity());
                            customAdapter1=new CustomAdapter1(getActivity(),products,customButtonListener1,flag,getActivity().getLayoutInflater());
                            lv.setAdapter(customAdapter1);
                            customAdapter1.notifyDataSetChanged();
                        }
                        catch (java.lang.NullPointerException e){
                            e.printStackTrace();
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
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Unable to fetch data: " + "internet connection is slow", Toast.LENGTH_SHORT).show();
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
                model=new Model2();
                model.setImage(jsonObject.getString("image"));
                model.setRuppee(jsonObject.getString("besstookitchen_amount"));
                model.setId(jsonObject.getString("besstookitchen_id"));
               // model.setNames(jsonObject.getString("homemaker_name"));
                model.setDescription(jsonObject.getString("besstookitchen_description"));
                model.setQuantity(0);
                model.setStr_food_name(jsonObject.getString("besstookitchen_menu_name"));
                model.setNo_of_item(jsonObject.getString("besstookitchen_quantity"));
                model.setFrom_kitchen(jsonObject.getString("from_kitchen"));
                records.add(model);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return records;

    }
    public void fetch1(){
        JsonArrayRequest request = new JsonArrayRequest(JSON1,
                new Response.Listener<JSONArray>() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray jsonObject) {
                        try {
                            // ArrayList<String>list=new ArrayList<>();
                            for (int i=0;i<jsonObject.length();i++){
                                JSONObject jsonObject1=jsonObject.getJSONObject(i);
                                list.add(jsonObject1.getString("location_name"));
                            }
                        }
                        catch(JSONException e) {
                           // Toast.makeText(getActivity(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            Toast.makeText(getActivity(), "Unable to fetch data: " + "internet connection is slow", Toast.LENGTH_SHORT).show();
                        }catch (java.lang.NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }



    private  void clearcart(String message){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle);
      //  alertDialog.setTitle(getResources().getString(R.string.app_name));
        TextView image = new TextView(getContext());
        image.setGravity(Gravity.CENTER|Gravity.BOTTOM);
        // image.setImageResource(R.drawable.besstoo_pop);
        image.setTextSize(15);
        image.setTextColor(Color.RED);
        image.setBackgroundResource(R.drawable.besstoo_pop);
        image.setText(message);
        alertDialog.setView(image);
        //alertDialog.setMessage(message);
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
        TextView image = new TextView(getContext());
        image.setGravity(Gravity.CENTER|Gravity.BOTTOM);
        // image.setImageResource(R.drawable.besstoo_pop);
        image.setTextSize(15);
        image.setTextColor(Color.RED);
        image.setBackgroundResource(R.drawable.besstoo_pop);
        image.setText(message);
        alertDialog.setView(image);
        //alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        //alertDialog.setIcon(R.mipmap.besstoo_launcher);
        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

}


