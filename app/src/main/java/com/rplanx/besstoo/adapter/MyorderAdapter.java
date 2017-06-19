package com.rplanx.besstoo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.rplanx.besstoo.My_order;
import com.rplanx.besstoo.R;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.getset.Order_get_set;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soumya on 25/4/17.
 */
public class MyorderAdapter extends ArrayAdapter<Order_get_set> {
    List<Order_get_set> newmodels;
    LayoutInflater vi;
    int resource;
    Order_get_set model2;
    Context context;
    //NewViewHolder newViewHolder;
    MyOrderHolder myOrderHolder;
    List<Order_get_set> order_get_sets;
    String str_set_time="12:00:00";
    ProgressDialog progressDialog;
    String string;
    // String JSON_URI="http://besstoo.com/service/order/cancel";
    String JSON_URI=Constant.ORDER_URL+"order/cancel";

    public MyorderAdapter(Context context, int resource, List<Order_get_set> modelCarts) {
        super(context, resource, modelCarts);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.resource = resource;
        this.newmodels = modelCarts;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        model2= newmodels.get(position);
        order_get_sets=new ArrayList<>();
        if (view == null) {
            myOrderHolder = new MyOrderHolder();
            view = vi.inflate(resource, null);
            myOrderHolder.txt_status_of_del=(TextView)view.findViewById(R.id.txt_status_of_del);
            myOrderHolder.r_del_details=(RelativeLayout)view.findViewById(R.id.delevary_detail);
            myOrderHolder.price=(LinearLayout)view.findViewById(R.id.l_price);
            myOrderHolder.meal_name=(LinearLayout)view.findViewById(R.id.l_meal);
            myOrderHolder.qty=(LinearLayout)view.findViewById(R.id.l_qty);
            myOrderHolder.txt_status=(TextView)view.findViewById(R.id.status);
            myOrderHolder.txt_ordr_type=(TextView)view.findViewById(R.id.menu);
            myOrderHolder.txt_date=(TextView)view.findViewById(R.id.txt_scheduled_date);
            myOrderHolder.txt_time=(TextView)view.findViewById(R.id.txt_sheduled_time);
            myOrderHolder.txt_address=(TextView)view.findViewById(R.id.txt_address);
            myOrderHolder.txt_no_of_person=(TextView)view.findViewById(R.id.txt_no);
            myOrderHolder.txt_delevery_cost=(TextView)view.findViewById(R.id.delevary_charge);
            myOrderHolder.txt_service_tax=(TextView)view.findViewById(R.id.txt_servicetax);
            myOrderHolder.txt_discount=(TextView)view.findViewById(R.id.txt_discount);
            myOrderHolder.txt_total=(TextView)view.findViewById(R.id.txt_total_amt);
            myOrderHolder.txt_payable_amt=(TextView)view.findViewById(R.id.payable_amount);
            myOrderHolder.txt_method_payment=(TextView)view.findViewById(R.id.txt_payment_mode);
            myOrderHolder.cancel=(Button)view.findViewById(R.id.btn_cancel);
            myOrderHolder.del_boy=(ImageView)view.findViewById(R.id.del_boy_photo);
            myOrderHolder.del_boy_name=(TextView)view.findViewById(R.id.txt_del_boy_name);
           // myOrderHolder.del_boy_bike=(TextView)view.findViewById(R.id.txt_bike_no);
            myOrderHolder.del_boy_status=(TextView)view.findViewById(R.id.del_status);
            myOrderHolder.txt_del_boy_phone=(TextView)view.findViewById(R.id.txt_del_phone);
            myOrderHolder.logo=(ImageView)view.findViewById(R.id.img_logo);
            myOrderHolder.cancel.setTag(position);
            view.setTag(myOrderHolder);
        }
        else{
            myOrderHolder=(MyOrderHolder)view.getTag();
        }
        if (model2.getUser_payment_status().equals("1")){
            myOrderHolder.txt_status.setText("ORDER #" + model2.getUser_order_otp()+ " " + "Paid");
        }

        else if (model2.getUser_payment_status().equals("0")){
            myOrderHolder.txt_status.setText("ORDER #" + model2.getUser_order_otp()+ " " + "UnPaid");
        }
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        final String strtime = "Current Time : " + mdformat.format(calendar.getTime());
        final String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        if (model2.getOrder_get_sets().size()>0){
            order_get_sets.addAll(model2.getOrder_get_sets());
            myOrderHolder.price.removeAllViews();
            myOrderHolder.qty.removeAllViews();
            myOrderHolder.meal_name.removeAllViews();
            for (int i=0;i<model2.getOrder_get_sets().size();i++){
                TextView txt_meal=new TextView(context);
                txt_meal.setMinLines(1);
               // txt_meal.setMaxLines(2);
               // txt_meal.setMaxWidth(80);
                txt_meal.setWidth(300);
                TextView txt_qt=new TextView(context);
                TextView txt_price=new TextView(context);
                txt_meal.setText(model2.getOrder_get_sets().get(i).getMenu_name());
                txt_qt.setText(model2.getOrder_get_sets().get(i).getQty());
                txt_price.setText(model2.getOrder_get_sets().get(i).getAmount());
                myOrderHolder.price.addView(txt_price);
                myOrderHolder.meal_name.addView(txt_meal);
                myOrderHolder.qty.addView(txt_qt);
            }
        }
        else{
            order_get_sets.clear();
        }
        if (model2.getOrder_date().equals(date)){
            //if (model2.getOrder_time().equals())
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = dateFormat.parse(model2.getOrder_time());
                d2 = dateFormat.parse(str_set_time);

                //in milliseconds
                long diff = d1.getTime() - d2.getTime();
                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                System.out.print(diffDays + " days, ");
                System.out.print(diffHours + " hours, ");
                System.out.print(diffMinutes + " minutes, ");
                System.out.print(diffSeconds + " seconds.");
               /* if (diff>0){
                    myOrderHolder.r_del_details.setVisibility(View.VISIBLE);
                }
                else{
                    myOrderHolder.r_del_details.setVisibility(View.GONE);
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else{
          //  myOrderHolder.r_del_details.setVisibility(View.VISIBLE);
        }
        if (model2.getUser_payment_mode().equals("1")){
            myOrderHolder.txt_method_payment.setText("Besstoo cash");
            myOrderHolder.cancel.setVisibility(View.GONE);
        }
        else if (model2.getUser_payment_mode().equals("2")){
            myOrderHolder.txt_method_payment.setText("C.O.D");
            myOrderHolder.cancel.setVisibility(View.VISIBLE);
        }
        else{
            myOrderHolder.txt_method_payment.setText("Online");
            myOrderHolder.cancel.setVisibility(View.GONE);
        }
        Picasso.with(context).load(model2.getImg_logo()).into(myOrderHolder.logo);
        if (model2.getStatus().equals("3")){
           // Picasso.with(context).load(Constant.URL2 + model2.getDelevery_boy_image()).into(myOrderHolder.del_boy);
            /*if (model2.getDelevery_boy_name().equals("null")){
                myOrderHolder.del_boy_name.setText("");
            }
            else{
                myOrderHolder.del_boy_name.setText(model2.getDelevery_boy_name());
            }
            if (model2.getContact().equals("null")){
                myOrderHolder.txt_del_boy_phone.setText("");
            }

            else{
                myOrderHolder.txt_del_boy_phone.setText(model2.getContact());
            }*/
           // myOrderHolder.del_boy_status.setText("Delivered");
            myOrderHolder.txt_status_of_del.setVisibility(View.VISIBLE);

            myOrderHolder.txt_status_of_del.setText("Delivered");
            myOrderHolder.cancel.setVisibility(View.GONE);
          //  myOrderHolder.r_del_details.setVisibility(View.VISIBLE);
        }
        else if(model2.getStatus().equals("1")){
            /* Picasso.with(context).load(Constant.URL2 + model2.getDelevery_boy_image()).into(myOrderHolder.del_boy);
            if (model2.getDelevery_boy_name().equals("null")){
                myOrderHolder.del_boy_name.setText("");
            }
            else{
                myOrderHolder.del_boy_name.setText(model2.getDelevery_boy_name());
            }
            if (model2.getContact().equals("null")){
                myOrderHolder.txt_del_boy_phone.setText("");
            }

            else{
                myOrderHolder.txt_del_boy_phone.setText(model2.getContact());
            }*/

           // myOrderHolder.del_boy_status.setText("On his way");
           // myOrderHolder.r_del_details.setVisibility(View.VISIBLE);
           // myOrderHolder.cancel.setVisibility(View.VISIBLE);

            if (model2.getUser_payment_mode().equals("1")){
                myOrderHolder.txt_method_payment.setText("Besstoo cash");
                myOrderHolder.cancel.setVisibility(View.GONE);
            }
            else if (model2.getUser_payment_mode().equals("2")){
                myOrderHolder.txt_method_payment.setText("C.O.D");
                myOrderHolder.cancel.setVisibility(View.VISIBLE);
            }
            else{
                myOrderHolder.txt_method_payment.setText("Online");
                myOrderHolder.cancel.setVisibility(View.GONE);
            }
            myOrderHolder.txt_status_of_del.setVisibility(View.INVISIBLE);
        }
        else if (model2.getStatus().equals("0")){
           // myOrderHolder.r_del_details.setVisibility(View.GONE);
            myOrderHolder.txt_status_of_del.setVisibility(View.VISIBLE);
            myOrderHolder.txt_status_of_del.setText("Cancelled:");
            myOrderHolder.cancel.setVisibility(View.GONE);
           /* myOrderHolder.txt_del_boy_phone.setVisibility(View.INVISIBLE);
            myOrderHolder.del_boy_name.setVisibility(View.INVISIBLE);
            myOrderHolder.del_boy_status.setVisibility(View.INVISIBLE);
            myOrderHolder.del_boy.setVisibility(View.INVISIBLE);*/

        }
        //myOrderHolder.txt_del_boy_phone.setText(model2.getContact());
        //myOrderHolder.del_boy_bike.setText(model2.getDelevery_boy_bike_details());
        myOrderHolder.txt_service_tax.setText(context.getResources().getString(R.string.Rs) + " "+model2.getService_tax());
        myOrderHolder.txt_delevery_cost.setText(context.getResources().getString(R.string.Rs) + " "+model2.getDelivary_cost());
        myOrderHolder.txt_discount.setText(context.getResources().getString(R.string.Rs) + " "+model2.getDiscount());
        myOrderHolder.txt_ordr_type.setText(model2.getOrder_type());
        myOrderHolder.txt_date.setText(model2.getOrder_date());
        myOrderHolder.txt_time.setText(model2.getUser_slot());
        myOrderHolder.txt_address.setText(model2.getLocation_name() +"," +model2.getLocation_city());
        myOrderHolder.txt_no_of_person.setText(model2.getOrder_person());
        myOrderHolder.txt_total.setText(context.getResources().getString(R.string.Rs) + " "+model2.getTotal_amt());
        myOrderHolder.txt_payable_amt.setText(context.getResources().getString(R.string.Rs) + " "+model2.getTotal_amt());
        myOrderHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date d1 = null;
                Date d2 = null;
                int p=(Integer)view.getTag();
                String order_id=newmodels.get(p).getOrder_id();
                String str_user_id=newmodels.get(p).getStr_user_id();
                cancelOrder(order_id,str_user_id);
                //sendRequest1(order_id,str_user_id);


            }
        });


        return  view;
    }

    class MyOrderHolder {
        TextView txt_status;
        TextView txt_ordr_type;
        TextView txt_date;
        TextView txt_time;
        TextView txt_address;
        TextView txt_no_of_person;
        TextView txt_delevery_cost;
        TextView txt_service_tax;
        TextView txt_discount;
        TextView txt_total;
        TextView txt_payable_amt;
        TextView txt_method_payment;
        Button cancel;
        LinearLayout meal_name;
        LinearLayout price;
        LinearLayout qty;
        ImageView del_boy;
        TextView del_boy_name;
        TextView del_boy_bike;
        TextView del_boy_status;
        TextView txt_del_boy_phone;
        RelativeLayout r_del_details;
        TextView txt_status_of_del;
        ImageView logo;
    }
    private void sendRequest1(final String order_id, final String str_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(context,jsonObject.getString("Status"),Toast.LENGTH_LONG).show();
                           //notifyDataSetChanged();
                            Intent next=new Intent(context, My_order.class);
                            next.putExtra("order_sec","order");
                            context.startActivity(next);
                            ((Activity)context).finish();
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
                            //    Toast.makeText(Login.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               // params.put("user_id", str_id);
                params.put("order_id",order_id);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

    }
    private  void  cancelOrder(final String order_id,final  String str_id){

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context,R.style.MyAlertDialogStyle);
        TextView image = new TextView(getContext());
        image.setGravity(Gravity.CENTER|Gravity.BOTTOM);
        // image.setImageResource(R.drawable.besstoo_pop);
        image.setTextSize(15);
        image.setTextColor(Color.RED);
        image.setBackgroundResource(R.drawable.besstoo_pop);
        image.setText("Do you want to cancel?");
        alertDialog.setView(image);


        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                sendRequest1(order_id,str_id);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }




}
