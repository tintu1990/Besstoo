package com.rplanx.besstoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rplanx.besstoo.R;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.interfaces.CustomButtonListener;
import com.rplanx.besstoo.interfaces.ModelDataSet;

import java.util.List;


/**
 * Created by soumya on 11/4/17.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>  {

    private List<Model2> items;
    //private OnRecyclerViewItemClickListener<Model2> itemClickListener;
    private int itemLayout;
    String string;
    private final ModelDataSet productDataSet;
    CustomButtonListener customButtonListener;
    int counter;
    TextView subtotal;
    TextView discount;
    TextView deleverycost;
    TextView vat;
    TextView totalcost;
    String delevery_charge;
    int r1;
    String str_tax;
    String str_discount;
    Context context;
    //private  final Handler mHandler = new Handler();
//private PaletteManager paletteManager = new PaletteManager();

    public MyRecyclerAdapter( ModelDataSet modelDataSet,CustomButtonListener customButtonListener,List<Model2> items, int itemLayout,TextView subtotal,TextView discount,TextView deleverycost,TextView vat,TextView totalcost,String delevery_charge,String str_tax,String str_discount) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.subtotal=subtotal;
        this.discount=discount;
        this.deleverycost=deleverycost;
        this.vat=vat;
        this.totalcost=totalcost;
        this.productDataSet=modelDataSet;
        this.customButtonListener=customButtonListener;
        this.delevery_charge=delevery_charge;
        this.str_tax=str_tax;
        this.str_discount=str_discount;
        }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
       // v.setOnClickListener(this);
        context=parent.getContext();
        return new ViewHolder(v);
        }

    @Override public void onBindViewHolder(final ViewHolder holder, int position) {
    final Model2 item = productDataSet.get(position);
    holder.itemView.setTag(item);
        if (item.getQuantity()>0){
            holder.txt_quantity.setVisibility(View.VISIBLE);
            holder.amt.setVisibility(View.VISIBLE);
            holder.title.setVisibility(View.VISIBLE);
            holder.minus.setVisibility(View.VISIBLE);
            holder.plus.setVisibility(View.VISIBLE);
            string=item.getStr_food_name();
            //  string =string.replace("\\\n", System.getProperty("line.separator"));
            holder.title.setText(string);
            holder.txt_quantity.setText(String.valueOf(item.getQuantity()));
            String inp_str=item.getRuppee();
            inp_str=inp_str.replaceAll("[^\\d.]", "");
          //  String inp_str1=inp_str.substring(0,inp_str.indexOf(" "));
        //    int r=Integer.parseInt(item.getRuppee());
            int r=Integer.parseInt(inp_str);
            holder.amt.setText(String.valueOf(item.getQuantity()*r));
            counter=item.getQuantity();
            r1=r1+ Integer.parseInt(inp_str)*item.getQuantity();
            subtotal.setText(context.getResources().getString(R.string.Rs) + " " +String.valueOf(r1));
            deleverycost.setText(context.getResources().getString(R.string.Rs) + " " +delevery_charge);
            double tax=Double.parseDouble(str_tax);
            double tax1=(tax/100.0f)*r1;
            vat.setText(context.getResources().getString(R.string.Rs) + " " +String.valueOf(tax1));
            double discount1=Double.parseDouble(str_discount);
            double discount2=(discount1/100.0f)*r1;
            discount.setText(context.getResources().getString(R.string.Rs) + " " +String.valueOf(discount2));
            int delevery=Integer.parseInt(delevery_charge);
            double total=(r1 + delevery + tax1 -discount2);
            totalcost.setText(context.getResources().getString(R.string.Rs) + " " +String.valueOf(String.valueOf(total)));
            holder.plus.setVisibility(View.INVISIBLE);
            holder.minus.setVisibility(View.INVISIBLE);


            /* holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customButtonListener.onPlusClick(item);
                    r1=0;
                }
            });

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getQuantity()>1){
                        customButtonListener.onMinusClick(item);
                        r1=0;
                    }

                }
            });*/
        }

        else {
            holder.txt_quantity.setVisibility(View.GONE);
            holder.amt.setVisibility(View.GONE);
            holder.title.setVisibility(View.GONE);
            holder.minus.setVisibility(View.GONE);
            holder.plus.setVisibility(View.GONE);
        }

}

    @Override public int getItemCount() {
        return productDataSet.size();
        }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_quantity;
        TextView title;
        TextView amt;
        ImageView plus;
        ImageView minus;

    public ViewHolder(View itemView) {
        super(itemView);
        txt_quantity=(TextView)itemView.findViewById(R.id.count);
        title=(TextView)itemView.findViewById(R.id.food_name);
        amt=(TextView)itemView.findViewById(R.id.txt_amt);
        plus=(ImageView)itemView.findViewById(R.id.plus);
        minus=(ImageView)itemView.findViewById(R.id.minus);
    }


}
}
