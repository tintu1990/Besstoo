package com.rplanx.besstoo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rplanx.besstoo.adapter.CustomAdapter1;
import com.rplanx.besstoo.application.VolleyApplication;
import com.rplanx.besstoo.constant.Constant;
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
public class TwoFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    ListView lv;
    TextView textview;
    String JSON="http://192.168.3.8/besstoo/public/service/homemaker/all/0";
    Products1 products;
    CustomAdapter1 customAdapter1;
    Model2 model;
    public static TwoFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TwoFragment fragment = new TwoFragment();
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
        lv= (ListView) getView().findViewById(R.id.list2);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        fetch();
    }


   private  final  CustomButtonListener1 customButtonListener1= new CustomButtonListener1(){


       @Override
       public void onMinusClick(Model2 product) {
           products.removeOneFrom(product);
           customAdapter1.notifyDataSetChanged();
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
               customAdapter1.notifyDataSetChanged();
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
        public long getId(int position) {
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
            String response=sp.getString("myJson" , "");
            Model2 model=product;
            model.setQuantity(model.getQuantity() + 1);
            model.setRuppee(model.getRuppee());
            model.setDescription(model.getDescription());
            model.setNo_of_item(model.getNo_of_item());
            model.setImage(model.getImage());
            model.setId(model.getId());
            model.setStr_food_name(model.getStr_food_name());
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

        JsonArrayRequest request = new JsonArrayRequest(JSON,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonObject) {
                        try {
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
                            customAdapter1=new CustomAdapter1(getActivity(),products,customButtonListener1,getActivity().getLayoutInflater());
                            lv.setAdapter(customAdapter1);
                            customAdapter1.notifyDataSetChanged();
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
                model=new Model2();
                model.setImage(jsonObject.getString("image"));
                model.setRuppee(jsonObject.getString("menu_amount"));
                model.setNames(jsonObject.getString("homemaker_name"));
                model.setDescription(jsonObject.getString("menu_description"));
                model.setQuantity(0);
                model.setStr_food_name(jsonObject.getString("menu_name"));
                model.setNo_of_item(jsonObject.getString("homemaker_qunatity"));
                records.add(model);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return records;

    }




}


