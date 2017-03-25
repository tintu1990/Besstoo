package com.rplanx.besstoo;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rplanx.besstoo.adapter.CustomAdapter;
import com.rplanx.besstoo.application.VolleyApplication;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.interfaces.CustomButtonListener;
import com.rplanx.besstoo.interfaces.ModelDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by soumya on 24/2/17.
 */
public class OneFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
  //  String JSON_URL1= Constant.url + "jsonattachements/";
    String JSON_URL1="http://192.168.3.8/besstoo/public/service/kitchen/New+Town/0";
    CustomAdapter customAdapter;
    ListView lv;
    Products products;
    TextView textview;
    Model2 model2;
    ImageView cart;
    SharedPreferences.Editor editor;
    TextView txt_address;
    AutoCompleteTextView text;
    int dflag=0;
    int new_flag;
    private String format = "";
    private int mHour, mMinute;
    FloatingActionButton floatingActionButton;

    public static OneFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        OneFragment fragment = new OneFragment();
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
        txt_address=(TextView)getActivity().findViewById(R.id.txt_address);
        text=(AutoCompleteTextView)getActivity().findViewById(R.id.select_address);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View headerView = (View) layoutInflater.inflate(R.layout.listview_single_image, lv, false);
        floatingActionButton=(FloatingActionButton)getView().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(),Login.class);
                startActivity(intent);
                /* final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay==0){
                                    hourOfDay=+12;
                                    format="AM";
                                }
                                else if (hourOfDay == 12) {
                                    format = "PM";
                                }
                                else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                }
                                else {
                                    format = "AM";
                                }
                                        Toast.makeText(getContext(),hourOfDay + ":" + minute + format,Toast.LENGTH_LONG).show();
                              //  txtTime.setText(hourOfDay + ":" + minute + format);
                                // txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time Slot");
                timePickerDialog.show();*/
            }
        });
        lv= (ListView) getView().findViewById(R.id.list1);
        lv.addHeaderView(headerView);
        ImageView imageView =(ImageView)getActivity().findViewById(R.id.next);

        txt_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dflag==0) {
                    text.setVisibility(View.VISIBLE);
                    dflag = 1;
                    int itemid = android.R.layout.simple_dropdown_item_1line;
                    String[] str_rad = getResources().getStringArray(R.array.location);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), itemid, str_rad);
                    text.setAdapter(arrayAdapter);
                }
                else{
                    text.setVisibility(View.GONE);
                    dflag=0;
                }
            }
        });
        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String)parent.getItemAtPosition(position);
                if (dflag==1){
                    dflag=0;
                    text.setVisibility(View.GONE);
                    txt_address.setText(selection);
                    JSON_URL1="http://192.168.3.8/besstoo/public/service/kitchen/" + selection +"/0";
                    fetch();
                    //Toast.makeText(PostShow.this,selection,Toast.LENGTH_LONG).show();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Main2Activity.class);
                startActivity(intent);
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

            if (Constant.counter>0) {
                Constant.counter--;
                textview = (TextView)getActivity().findViewById(R.id.badge_notification_1);
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
            }
        }
        @Override
        public void onPlusClick(Model2 product) {
            int n= Integer.parseInt(product.getNo_of_item());
            int n1=product.getQuantity();
            if (n1<n) {
                products.addOneTo(product);
                customAdapter.notifyDataSetChanged();
                SharedPreferences p=getActivity().getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);
                Constant.counter=p.getInt("incr",0);
                textview = (TextView) getActivity().findViewById(R.id.badge_notification_1);
                if (Constant.counter >= 0) {
                    textview.setVisibility(View.VISIBLE);
                } else {
                    textview.setVisibility(View.INVISIBLE);
                }
                Constant.counter++;
                SharedPreferences prefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("incr", Constant.counter);
                editor.apply();
                textview.setText(String.valueOf(Constant.counter));
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
                productList.remove(product);
                productList.add(i, model);
                Constant.exercise_arraylist1.addAll(productList);
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
                productList.remove(product);
                productList.add(i, model);
                 Constant.exercise_arraylist1.addAll(productList);
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
        JsonArrayRequest request = new JsonArrayRequest(JSON_URL1,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonObject) {
                        try {
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
                            products=new Products(imageRecords,getActivity());
                            customAdapter=new CustomAdapter(getActivity(),products,productClickListener,getActivity().getLayoutInflater());
                            lv.setAdapter(customAdapter);
                            customAdapter.notifyDataSetChanged();

                            //customAdapter.swapImageRecords(imageRecords);
                        }
                        catch(JSONException e) {
                            Toast.makeText(getActivity(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
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
                records.add(model2);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return records;

    }
}
