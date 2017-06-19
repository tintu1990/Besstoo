package com.rplanx.besstoo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.rplanx.besstoo.adapter.Expendable_adapter;
import com.rplanx.besstoo.adapter.Expendable_list_adapter;
import com.rplanx.besstoo.application.VolleyApplication;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.date.Datedialog1;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.interfaces.ModelDataSet;
import com.rplanx.besstoo.interfaces.PartyButtonListener;
import com.rplanx.besstoo.interfaces.PartyButtonListener1;
import com.rplanx.besstoo.interfaces.PartyDataSet;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soumya on 24/2/17.
 */
@SuppressWarnings("ALL")
public class Party_plan extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    //ListView listView;
    FloatingActionButton floatingActionButton;
    EditText no_of_people;
    TextView date;
    RadioGroup selecttime;
    RadioButton time;
    Button btn_proceed;
    String str_no_of_people;
    String str_date;
    int int_no_of_people;
    Expendable_list_adapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    com.rplanx.besstoo.getset.Party party;
    Party_product party_product;
    ImageView image_starter1;
    List<com.rplanx.besstoo.getset.Party> partyRecord;
    List<Model2> party_model_record;
    ImageView img_starter2;
    ImageView img_starter3;
    ImageView img_starter4;
    ImageView img_starter5;
    ImageView img_main;
    ImageView img_main2;
    ImageView img_main3;
    ImageView img_main4;
    ImageView img_desert1;
    ImageView img_desert2;
    ImageView img_desert3;
    ArrayList<com.rplanx.besstoo.getset.Party> starter;
    ArrayList<com.rplanx.besstoo.getset.Party> main_course;
    ArrayList<com.rplanx.besstoo.getset.Party> desert;
    List <com.rplanx.besstoo.getset.Party> parties1;
    List<Model2> model_parties;
    TextView total_party_amt;
    String id;
    List <com.rplanx.besstoo.getset.Party> selected_party_list;
    List<Model2> selected_model_party_list;
    int st_id;

    String PARTY_URL= Constant.URL+ "party";
    TextView t_badge;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    private int mPage;
    SharedPreferences shr;
    SharedPreferences she;
    SharedPreferences .Editor editor;
    SharedPreferences.Editor editor1;
    TextView textView;
    SharedPreferences party_pref;
    SharedPreferences shref;
    SharedPreferences.Editor editor2;
    Model2 model2;
    Model_product model_product;
    Expendable_adapter expendable_adapter;
    String message="Please clear your cart to proceed";

    public static Party_plan newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Party_plan fragment = new Party_plan();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main2,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // setRetainInstance(true);
        floatingActionButton=(FloatingActionButton)getView().findViewById(R.id.fab);
        starter=new ArrayList<>();
        desert=new ArrayList<>();
        main_course=new ArrayList<>();
        model_parties=new ArrayList<>();
        selected_model_party_list=new ArrayList<>();
        parties1=new ArrayList<>();
        selected_party_list=new ArrayList<>();
        textView=(TextView)getActivity().findViewById(R.id.badge_notification_1);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        shref=getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        shr=getActivity().getSharedPreferences("MyPREFERENCES1",Context.MODE_PRIVATE);
        party_pref=getActivity().getSharedPreferences("Party_pref",Context.MODE_PRIVATE);

        progressBar=(ProgressBar)getActivity().findViewById(R.id.progressBar);

        expListView = (ExpandableListView) getView().findViewById(R.id.lvExp);
        View view = layoutInflater.inflate(R.layout.footer, expListView, false);
        expListView.addFooterView(view);
        fetch();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   if (Constant.counter > 0) {
               //     Toast.makeText(getActivity(), "Please clear your cart to proceed", Toast.LENGTH_SHORT).show();
              //  } else {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.time_picker1);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setCancelable(true);
                    dialog.show();
                    no_of_people = (EditText) dialog.findViewById(R.id.edt_number);
                    date = (TextView) dialog.findViewById(R.id.date);
                    selecttime = (RadioGroup) dialog.findViewById(R.id.radiogrp1);
                    selecttime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            time = (RadioButton) dialog.findViewById(i);
                        }
                    });
                    btn_proceed = (Button) dialog.findViewById(R.id.button_sub);
                    date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Datedialog1 datedialog = new Datedialog1();
                            datedialog.getViews(view);
                            // FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                            //  datedialog.show(fragmentTransaction,"DatePicker");
                            FragmentManager fm = getActivity().getFragmentManager();
                            datedialog.show(fm, "DatePicker");
                        }
                    });

                    btn_proceed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           // dialog.dismiss();
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                            //  if (sharedPreferences.contains("user_id")){
                            try {
                                str_date = date.getText().toString();
                                str_no_of_people = no_of_people.getText().toString();
                                if (time == null) {
                                    Toast.makeText(getActivity(), "you have to select a time slot", Toast.LENGTH_SHORT).show();
                                }
                                if (!str_no_of_people.isEmpty()) {
                                    int_no_of_people = Integer.parseInt(str_no_of_people);
                                    if (int_no_of_people < 10) {
                                        Toast.makeText(getActivity(), "you have to select atleast 10 people", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (int_no_of_people>100){
                                        Toast.makeText(getActivity(), "you cannot select more than 100 people", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(getActivity(), "you have to select atleast 10 people", Toast.LENGTH_SHORT).show();
                                }
                                if (str_date.isEmpty()) {
                                    Toast.makeText(getActivity(), "you have to select date", Toast.LENGTH_SHORT).show();
                                }
                                if (!str_date.isEmpty() && int_no_of_people >= 10 && int_no_of_people <=100 && time != null) {
                                    dialog.dismiss();

                                    if (sharedPreferences.contains("user_id")) {
                                        Intent intent = new Intent(getActivity(), Address_list.class);
                                        intent.putExtra("time", time.getText().toString());
                                        intent.putExtra("no_of_people", str_no_of_people);
                                        intent.putExtra("date", str_date);
                                        intent.putExtra("tag", "party");
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(getActivity(), Login.class);
                                        intent.putExtra("time", time.getText().toString());
                                        intent.putExtra("no_of_people", str_no_of_people);
                                        intent.putExtra("date", str_date);
                                        intent.putExtra("tag", "party");
                                        startActivity(intent);
                                    }
                                }
                            } catch (NumberFormatException ex) {
                                //  Toast.makeText(getActivity(), "you cannot select ", Toast.LENGTH_SHORT).show();
                                ex.printStackTrace();
                            }



                        }
                    });

               // }
            }
        });



    }


    private void fetch() {
      //  progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(PARTY_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonObject) {
                   //     progressBar.setVisibility(View.GONE);
                        try {
                            party_model_record=prepareListData(jsonObject);
                            model_product = new Model_product(party_model_record,getActivity());
                            expendable_adapter=new Expendable_adapter(getContext(),model_product,partyButtonListener1,listDataHeader,listDataChild,textView);
                            expListView.setAdapter(expendable_adapter);

                         /* partyRecord=prepareListData(jsonObject);
                             party_product=new Party_product(partyRecord,getActivity());
                            listAdapter = new Expendable_list_adapter(getContext(),party_product,partyButtonListener, listDataHeader, listDataChild,textView);
                            // setting list adapter
                            expListView.setAdapter(listAdapter);*/

                        } catch (JSONException e) {
                            e.printStackTrace();
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
                     //   progressBar.setVisibility(View.GONE);
                      //  Toast.makeText(getActivity(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }


    private  List<Model2>prepareListData(JSONArray jsonObject) throws JSONException {
   // private List<com.rplanx.besstoo.getset.Party>prepareListData(JSONArray jsonObject) throws JSONException {
      //  List<com.rplanx.besstoo.getset.Party> parties=new ArrayList<>();
        List<Model2> parties=new ArrayList<>();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding child data
        listDataHeader.add("Starters");
        listDataHeader.add("Main Course");
        listDataHeader.add("Deserts");
        for (int i=0;i<jsonObject.length();i++){
            JSONObject json=jsonObject.getJSONObject(i);
            model2=new Model2();
            model2.setRuppee(json.getString("besstookitchen_amount"));
            model2.setStr_food_name(json.getString("besstookitchen_menu_name"));
            model2.setImage(json.getString("images"));
            model2.setDate(json.getString("besstookitchen_publish_date"));
            model2.setMenu_id(json.getString("category_kitchen_type"));
            model2.setQuantity(Integer.parseInt(json.getString("besstookitchen_quantity")));
            model2.setNo_of_item(json.getString("besstookitchen_quantity"));
            model2.setId(json.getString("besstookitchen_id"));
            parties.add(model2);
        }
        /* for ( int i=0;i<jsonObject.length();i++){
            JSONObject json=jsonObject.getJSONObject(i);
            party=new com.rplanx.besstoo.getset.Party();
            party.setAmount(json.getString("besstookitchen_amount"));
            party.setBesstoo_kitchen_id(json.getString("besstookitchen_id"));
            party.setDate(json.getString("besstookitchen_publish_date"));
            party.setPartyImage(json.getString("images"));
            party.setMenu_id(json.getString("category_kitchen_type"));
            party.setMenu_name(json.getString("besstookitchen_menu_name"));
            party.setQuantity(Integer.parseInt(json.getString("besstookitchen_quantity")));
            party.setNo_of_item(json.getString("besstookitchen_quantity"));
            party.setStr_id(String.valueOf(i));
            parties.add(party);
        }*/
       // parties1.addAll(parties);
        model_parties.clear();
        model_parties.addAll(parties);
        List<String> main_cource=new ArrayList<>();
        List<String> desert =new ArrayList<>();
        List<String> starter = new ArrayList<String>();
        for (int i=0;i<parties.size();i++){
            if (parties.get(i).getMenu_id().equals("1")){
                starter.add(parties.get(i).getStr_food_name());
            }
            if (parties.get(i).getMenu_id().equals("2")){
                main_cource.add(parties.get(i).getStr_food_name());
            }

            if (parties.get(i).getMenu_id().equals("3")){
                desert.add(parties.get(i).getStr_food_name());
            }
        }
        /*for ( int i=0;i<parties.size();i++){
            if (parties.get(i).getMenu_id().equals("1")){
                starter.add(parties.get(i).getMenu_name());
            }
            if (parties.get(i).getMenu_id().equals("2")){
                main_cource.add(parties.get(i).getMenu_name());
            }

           if (parties.get(i).getMenu_id().equals("3")){
                desert.add(parties.get(i).getMenu_name());
            }
        }*/
        listDataChild.put(listDataHeader.get(0),starter);
        listDataChild.put(listDataHeader.get(1),main_cource);
        listDataChild.put(listDataHeader.get(2),desert);
        return  parties;

    }

    private  final PartyButtonListener1 partyButtonListener1 =new PartyButtonListener1(){

        @Override
        public void onMinusClick(Model2 product, String string) {
            Constant.counter--;
            textView.setText(String.valueOf(Constant.counter));
            for(int i=0;i<model_parties.size();i++) {
                if (model_parties.get(i).getStr_food_name().equals(string)) {
                    model2 = model_parties.get(i);
                    id = model_parties.get(i).getMenu_id();
                    st_id = i;
                    break;
                }
            }
                selected_model_party_list.remove(model2);
                if (selected_model_party_list.size()>0){
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
                else{
                    floatingActionButton.setVisibility(View.INVISIBLE);
                }
                int party_rs=0;
                for (int v=0;v<selected_model_party_list.size();v++){
                    party_rs=party_rs+ Integer.parseInt(selected_model_party_list.get(v).getRuppee());
                }
                total_party_amt.setText("Total amount:"  + " " + party_rs);
                SharedPreferences shref;
                SharedPreferences.Editor editor;
                shref=getActivity().getSharedPreferences("Party_pref",Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String json = gson.toJson(selected_model_party_list);
                editor=shref.edit();
                editor.putString("myJson", json);
                editor.apply();
                if (id.equals("1")){
                    if (image_starter1.getTag().equals(string)){
                        image_starter1.setVisibility(View.INVISIBLE);
                    }

                    else if (img_starter2.getTag().equals(string)){
                        img_starter2.setVisibility(View.INVISIBLE);
                    }

                    else if (img_starter3.getTag().equals(string)){
                        img_starter3.setVisibility(View.INVISIBLE);
                    }

                    else if (img_starter4.getTag().equals(string)){
                        img_starter4.setVisibility(View.INVISIBLE);
                    }
                    else if (img_starter5.getTag().equals(string)){
                        img_starter5.setVisibility(View.INVISIBLE);
                    }
                }
                if (id.equals("2")){
                    if (img_main.getTag().equals(string)){
                        img_main.setVisibility(View.INVISIBLE);
                    }
                    else if (img_main2.getTag().equals(string)){
                        img_main2.setVisibility(View.INVISIBLE);
                    }
                    else  if (img_main3.getTag().equals(string)){
                        img_main3.setVisibility(View.INVISIBLE);
                    }

                    else   if (img_main4.getTag().equals(string)){
                        img_main4.setVisibility(View.INVISIBLE);
                    }
                }

                if (id.equals("3")){

                    if (img_desert1.getTag().equals(string)){
                        img_desert1.setVisibility(View.INVISIBLE);
                    }

                    else    if (img_desert2.getTag().equals(string)){
                        img_desert2.setVisibility(View.INVISIBLE);
                    }

                    else  if (img_desert3.getTag().equals(string)){
                        img_desert3.setVisibility(View.INVISIBLE);
                    }
                }
        }

        @Override
        public void onPlusClick(Model2 product, String string) {
            image_starter1 = (ImageView) getActivity().findViewById(R.id.imageView);
            img_starter2 = (ImageView) getActivity().findViewById(R.id.imageView2);
            img_starter3 = (ImageView) getActivity().findViewById(R.id.imageView3);
            img_starter4 = (ImageView) getActivity().findViewById(R.id.imageView4);
            img_main = (ImageView) getActivity().findViewById(R.id.imageview8);
            img_main2 = (ImageView) getActivity().findViewById(R.id.imageView9);
            img_main3 = (ImageView) getActivity().findViewById(R.id.imageView6);
            img_main4 = (ImageView) getActivity().findViewById(R.id.main_course);
            img_desert1 = (ImageView) getActivity().findViewById(R.id.imageView5);
            img_desert2 = (ImageView) getActivity().findViewById(R.id.imageview6);
            img_desert3 = (ImageView) getActivity().findViewById(R.id.imageview7);
            total_party_amt = (TextView) getActivity().findViewById(R.id.totl_amt);
            img_starter5 = (ImageView) getActivity().findViewById(R.id.image_starter1);
            Constant.counter++;
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(Constant.counter));
            List<Model2> starter_sec = new ArrayList<>();
            for (int i = 0; i < model_parties.size(); i++) {
                if (model_parties.get(i).getStr_food_name().equals(string)) {
                    model2 = model_parties.get(i);
                    id = model_parties.get(i).getMenu_id();
                    st_id = i;
                    break;
                }
            }
            selected_model_party_list.add(model2);
            if (selected_model_party_list.size() > 0) {
                floatingActionButton.setVisibility(View.VISIBLE);
            } else {
                floatingActionButton.setVisibility(View.INVISIBLE);
            }
            int party_rs = 0;
            for (int v = 0; v < selected_model_party_list.size(); v++) {
                party_rs = party_rs + Integer.parseInt(selected_model_party_list.get(v).getRuppee());
            }
            total_party_amt.setText("Total amount:" + " " + party_rs);
            SharedPreferences shref;
            SharedPreferences.Editor editor;
            shref = getActivity().getSharedPreferences("Party_pref", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = gson.toJson(selected_model_party_list);
            editor = shref.edit();
            editor.putString("myJson", json);
            editor.apply();
            if (id.equals("1")) {
                if (image_starter1.getVisibility() == View.INVISIBLE) {
                    image_starter1.setVisibility(View.VISIBLE);
                    image_starter1.setTag(model_parties.get(st_id).getStr_food_name());
                    //Toast.makeText(getContext(),String.valueOf(image_starter1.getTag()),Toast.LENGTH_LONG).show();
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(image_starter1);
                } else if (img_starter2.getVisibility() == View.INVISIBLE) {
                    img_starter2.setVisibility(View.VISIBLE);
                    img_starter2.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_starter2);
                } else if (img_starter3.getVisibility() == View.INVISIBLE) {
                    img_starter3.setVisibility(View.VISIBLE);
                    img_starter3.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_starter3);
                } else if (img_starter4.getVisibility() == View.INVISIBLE) {
                    img_starter4.setVisibility(View.VISIBLE);
                    img_starter4.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_starter4);
                } else if (img_starter5.getVisibility() == View.INVISIBLE) {
                    img_starter5.setVisibility(View.VISIBLE);
                    img_starter5.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_starter5);

                }
            }
            if (id.equals("2")) {
                if (img_main.getVisibility() == View.INVISIBLE) {
                    img_main.setVisibility(View.VISIBLE);
                    img_main.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_main);
                } else if (img_main2.getVisibility() == View.INVISIBLE) {
                    img_main2.setVisibility(View.VISIBLE);
                    img_main2.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_main2);
                } else if (img_main3.getVisibility() == View.INVISIBLE) {
                    img_main3.setVisibility(View.VISIBLE);
                    img_main3.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_main3);
                } else if (img_main4.getVisibility() == View.INVISIBLE) {
                    img_main4.setVisibility(View.VISIBLE);
                    img_main4.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_main4);
                }
            }
            if (id.equals("3")) {
                if (img_desert1.getVisibility() == View.INVISIBLE) {
                    img_desert1.setVisibility(View.VISIBLE);
                    img_desert1.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_desert1);
                } else if (img_desert2.getVisibility() == View.INVISIBLE) {
                    img_desert2.setVisibility(View.VISIBLE);
                    img_desert2.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_desert2);
                } else if (img_desert3.getVisibility() == View.INVISIBLE) {
                    img_desert3.setVisibility(View.VISIBLE);
                    img_desert3.setTag(model_parties.get(st_id).getStr_food_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + model_parties.get(st_id).getImage()).fit().into(img_desert3);
                }
            }




        }
    };

    private  final PartyButtonListener partyButtonListener =new PartyButtonListener() {
        @Override
        public void onMinusClick(com.rplanx.besstoo.getset.Party product, String string) {
            //party_product.removeOneFrom(product);
           // Party model=product;
            //image_starter1=(ImageView)getActivity().findViewById(R.id.imageView);
          //  if (model.getMenu_id().equals("1")) {
            for (int i=0; i<parties1.size();i++){
                if (parties1.get(i).getMenu_name().equals(string))
                {
                    party=parties1.get(i);
                    id = parties1.get(i).getMenu_id();
                    st_id=i;
                    break;
                }
            }
            selected_party_list.remove(party);
            if (selected_party_list.size()>0){
                floatingActionButton.setVisibility(View.VISIBLE);
            }
            else{
                floatingActionButton.setVisibility(View.INVISIBLE);
            }
            int party_rs=0;
            for (int v=0;v<selected_party_list.size();v++){
                party_rs=party_rs+ Integer.parseInt(selected_party_list.get(v).getAmount());
            }
            total_party_amt.setText("Total amount:"  + " " + party_rs);
            SharedPreferences shref;
            SharedPreferences.Editor editor;
            shref=getActivity().getSharedPreferences("Party_pref",Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = gson.toJson(selected_party_list);
            editor=shref.edit();
            editor.putString("myJson", json);
            editor.apply();

            if (id.equals("1")){
                if (image_starter1.getTag().equals(string)){
                    image_starter1.setVisibility(View.INVISIBLE);
                }

                else if (img_starter2.getTag().equals(string)){
                    img_starter2.setVisibility(View.INVISIBLE);
                }

                else if (img_starter3.getTag().equals(string)){
                    img_starter3.setVisibility(View.INVISIBLE);
                }

                else if (img_starter4.getTag().equals(string)){
                    img_starter4.setVisibility(View.INVISIBLE);
                }
                else if (img_starter5.getTag().equals(string)){
                    img_starter5.setVisibility(View.INVISIBLE);
                }
            }
            if (id.equals("2")){
                 if (img_main.getTag().equals(string)){
                    img_main.setVisibility(View.INVISIBLE);
                }
                else if (img_main2.getTag().equals(string)){
                    img_main2.setVisibility(View.INVISIBLE);
                }
                else  if (img_main3.getTag().equals(string)){
                    img_main3.setVisibility(View.INVISIBLE);
                }

                else   if (img_main4.getTag().equals(string)){
                    img_main4.setVisibility(View.INVISIBLE);
                }
            }

            if (id.equals("3")){

                 if (img_desert1.getTag().equals(string)){
                    img_desert1.setVisibility(View.INVISIBLE);
                }

                else    if (img_desert2.getTag().equals(string)){
                    img_desert2.setVisibility(View.INVISIBLE);
                }

                else  if (img_desert3.getTag().equals(string)){
                    img_desert3.setVisibility(View.INVISIBLE);
                }
            }



        }

        @Override
        public void onPlusClick(com.rplanx.besstoo.getset.Party product, String string) {
            //party_product.addOneTo(product);
            //  Party model=product;
            image_starter1 = (ImageView) getActivity().findViewById(R.id.imageView);
            img_starter2 = (ImageView) getActivity().findViewById(R.id.imageView2);
            img_starter3 = (ImageView) getActivity().findViewById(R.id.imageView3);
            img_starter4 = (ImageView) getActivity().findViewById(R.id.imageView4);
            img_main = (ImageView) getActivity().findViewById(R.id.imageview8);
            img_main2 = (ImageView) getActivity().findViewById(R.id.imageView9);
            img_main3 = (ImageView) getActivity().findViewById(R.id.imageView6);
            img_main4 = (ImageView) getActivity().findViewById(R.id.main_course);
            img_desert1 = (ImageView) getActivity().findViewById(R.id.imageView5);
            img_desert2 = (ImageView) getActivity().findViewById(R.id.imageview6);
            img_desert3 = (ImageView) getActivity().findViewById(R.id.imageview7);
            total_party_amt = (TextView) getActivity().findViewById(R.id.totl_amt);
            img_starter5 = (ImageView) getActivity().findViewById(R.id.image_starter1);
            List<com.rplanx.besstoo.getset.Party> starter_sec = new ArrayList<>();
            for (int i = 0; i < parties1.size(); i++) {
                if (parties1.get(i).getMenu_name().equals(string)) {
                    party = parties1.get(i);
                    id = parties1.get(i).getMenu_id();
                    st_id = i;
                    break;
                }
            }
            selected_party_list.add(party);
            if (selected_party_list.size() > 0) {
                floatingActionButton.setVisibility(View.VISIBLE);
            } else {
                floatingActionButton.setVisibility(View.INVISIBLE);
            }
            int party_rs = 0;
            for (int v = 0; v < selected_party_list.size(); v++) {
                party_rs = party_rs + Integer.parseInt(selected_party_list.get(v).getAmount());
            }
            total_party_amt.setText("Total amount:" + " " + party_rs);
            SharedPreferences shref;
            SharedPreferences.Editor editor;
            shref = getActivity().getSharedPreferences("Party_pref", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = gson.toJson(selected_party_list);
            editor = shref.edit();
            editor.putString("myJson", json);
            editor.apply();
            if (id.equals("1")) {
                if (image_starter1.getVisibility() == View.INVISIBLE) {
                    image_starter1.setVisibility(View.VISIBLE);
                    image_starter1.setTag(parties1.get(st_id).getMenu_name());
                    //Toast.makeText(getContext(),String.valueOf(image_starter1.getTag()),Toast.LENGTH_LONG).show();
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(image_starter1);
                } else if (img_starter2.getVisibility() == View.INVISIBLE) {
                    img_starter2.setVisibility(View.VISIBLE);
                    img_starter2.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_starter2);
                } else if (img_starter3.getVisibility() == View.INVISIBLE) {
                    img_starter3.setVisibility(View.VISIBLE);
                    img_starter3.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_starter3);
                } else if (img_starter4.getVisibility() == View.INVISIBLE) {
                    img_starter4.setVisibility(View.VISIBLE);
                    img_starter4.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_starter4);
                } else if (img_starter5.getVisibility() == View.INVISIBLE) {
                    img_starter5.setVisibility(View.VISIBLE);
                    img_starter5.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_starter5);

                }
            }
            if (id.equals("2")) {
                if (img_main.getVisibility() == View.INVISIBLE) {
                    img_main.setVisibility(View.VISIBLE);
                    img_main.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_main);
                } else if (img_main2.getVisibility() == View.INVISIBLE) {
                    img_main2.setVisibility(View.VISIBLE);
                    img_main2.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_main2);
                } else if (img_main3.getVisibility() == View.INVISIBLE) {
                    img_main3.setVisibility(View.VISIBLE);
                    img_main3.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_main3);
                } else if (img_main4.getVisibility() == View.INVISIBLE) {
                    img_main4.setVisibility(View.VISIBLE);
                    img_main4.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_main4);
                }
            }
            if (id.equals("3")) {
                if (img_desert1.getVisibility() == View.INVISIBLE) {
                    img_desert1.setVisibility(View.VISIBLE);
                    img_desert1.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_desert1);
                } else if (img_desert2.getVisibility() == View.INVISIBLE) {
                    img_desert2.setVisibility(View.VISIBLE);
                    img_desert2.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_desert2);
                } else if (img_desert3.getVisibility() == View.INVISIBLE) {
                    img_desert3.setVisibility(View.VISIBLE);
                    img_desert3.setTag(parties1.get(st_id).getMenu_name());
                    Picasso.with(getActivity()).load(Constant.URL1 + parties1.get(st_id).getPartyImage()).fit().into(img_desert3);
                }
            }
        }

    };


    private  static class Model_product implements ModelDataSet{
        private final List<Model2> productLists;
        Context context;

        private Model_product(List<Model2> productLists,Context context) {
            this.productLists = productLists;
            this.context=context;
        }

        @Override
        public int size() {
            return productLists.size();
        }

        @Override
        public Model2 get(int position) {
            return productLists.get(position);
        }

        @Override
        public long getId(int position) {
            return position;
        }
    }

    private  static  class Party_product implements PartyDataSet{
        private final List<com.rplanx.besstoo.getset.Party> productList;
        Context context;

        private Party_product(List<com.rplanx.besstoo.getset.Party> productList, Context context) {
            this.productList =productList;
            this.context= context;
        }

        @Override
        public int size() {
            return productList.size();
        }

        @Override
        public com.rplanx.besstoo.getset.Party get(int position) {
            return productList.get(position);
        }

        @Override
        public long getId(int position) {
            return position;
        }

        public void removeOneFrom(com.rplanx.besstoo.getset.Party product) {

        }
        public void addOneTo(com.rplanx.besstoo.getset.Party product) {
            com.rplanx.besstoo.getset.Party model=product;

        }
    }

    private  void  clearcart(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle(getResources().getString(R.string.app_name));
        alertDialog.setMessage("Please clear your cart to proceed");
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.mipmap.besstoo_launcher);
        alertDialog.setPositiveButton("clear", new DialogInterface.OnClickListener() {
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
                textView.setVisibility(View.INVISIBLE);

            }
        });

        alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();
    }


}
