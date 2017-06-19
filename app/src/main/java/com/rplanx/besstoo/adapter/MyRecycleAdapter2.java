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
import com.rplanx.besstoo.interfaces.ModelDataSet;
import com.rplanx.besstoo.interfaces.PartyButtonListener1;

import java.util.List;

/**
 * Created by soumya on 23/5/17.
 */
public class MyRecycleAdapter2 extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>  {
    private List<Model2> items;
    //private OnRecyclerViewItemClickListener<Model2> itemClickListener;
    private int itemLayout;
    String string;
    private final ModelDataSet productDataSet;
    PartyButtonListener1 customButtonListener;
    int counter;
    TextView subtotal;
    TextView discount;
    TextView deleverycost;
    TextView vat;
    TextView totalcost;
    String str_people;
    int r1;
    Context context;
    String str_deleverycharge;
    String str_tax;
    String str_discount;

    public MyRecycleAdapter2(ModelDataSet modelDataSet,PartyButtonListener1 customButtonListener,List<Model2> items,int itemLayout,TextView subtotal,TextView discount,TextView deleverycost,TextView vat,TextView totalcost,String str_people,String str_delivery_charge,String tax,String str_discount) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.subtotal=subtotal;
        this.discount=discount;
        this.deleverycost=deleverycost;
        this.vat=vat;
        this.totalcost=totalcost;
        this.productDataSet=modelDataSet;
        this.customButtonListener=customButtonListener;
        this.str_people=str_people;
        this.str_deleverycharge=str_delivery_charge;
        this.str_tax=tax;
        this.str_discount=str_discount;


    }

    @Override public int getItemCount() {
        return productDataSet.size();
    }

    @Override public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        // v.setOnClickListener(this);
        context=parent.getContext();
        return new MyRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {
        final Model2 item = productDataSet.get(position);
        holder.itemView.setTag(item);
        holder.amt.setVisibility(View.VISIBLE);
        holder.title.setVisibility(View.VISIBLE);
        holder.minus.setVisibility(View.INVISIBLE);
        holder.plus.setVisibility(View.INVISIBLE);
        string=item.getStr_food_name();
        holder.title.setText(string);
        holder.amt.setText(String.valueOf(Integer.parseInt(item.getRuppee())*Integer.parseInt(str_people)));
        r1=r1+ Integer.parseInt(item.getRuppee())*Integer.parseInt(str_people);
        subtotal.setText(context.getResources().getString(R.string.Rs) + " " +String.valueOf(r1));
        deleverycost.setText(context.getResources().getString(R.string.Rs) + " " +str_deleverycharge);
        double tax=Double.parseDouble(str_tax);
        double tax1=(tax/100.0f)*r1;
        vat.setText(context.getResources().getString(R.string.Rs) + " " +String.valueOf(tax1));
        double discount1=Double.parseDouble(str_discount);
        double discount2=(discount1/100.0f)*r1;
        discount.setText(context.getResources().getString(R.string.Rs) + " " +String.valueOf(discount2));
        int delevery=Integer.parseInt(str_deleverycharge);
        double total=(r1 + delevery + tax1 -discount2);
        totalcost.setText(context.getResources().getString(R.string.Rs) + " " +String.valueOf(String.valueOf(total)));




    }



    public static class PartyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_quantity;
        TextView title;
        TextView amt;
        ImageView plus;
        ImageView minus;

        public PartyViewHolder(View itemView) {
            super(itemView);
            txt_quantity=(TextView)itemView.findViewById(R.id.count);
            title=(TextView)itemView.findViewById(R.id.food_name);
            amt=(TextView)itemView.findViewById(R.id.txt_amt);
            plus=(ImageView)itemView.findViewById(R.id.plus);
            minus=(ImageView)itemView.findViewById(R.id.minus);
        }


    }
}
