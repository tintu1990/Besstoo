package com.rplanx.besstoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rplanx.besstoo.R;
import com.rplanx.besstoo.getset.Model2;

import java.util.ArrayList;

/**
 * Created by soumya on 11/4/17.
 */
public class Orders_adapter extends ArrayAdapter<Model2> {
    ArrayList<Model2> newmodels;
    LayoutInflater vi;
    int resource;
    Model2 model2;
    Context context;
    //NewViewHolder newViewHolder;
    Myholder myholder;
   /* ModelCart modelCart;*/
    String string;

    public Orders_adapter(Context context, int resource, ArrayList<Model2> modelCarts) {
        super(context, resource,modelCarts);
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
        if (view == null) {
            myholder=new Myholder();
            view = vi.inflate(resource, null);
            myholder.txt_quantity=(TextView)view.findViewById(R.id.count);
            myholder.title=(TextView)view.findViewById(R.id.food_name);
            myholder.amt=(TextView)view.findViewById(R.id.txt_amt);
            myholder.plus=(ImageView)view.findViewById(R.id.plus);
            myholder.minus=(ImageView)view.findViewById(R.id.minus);
            view.setTag(myholder);
        }

     else {
        myholder = (Myholder) view.getTag();
        }

        if (model2.getQuantity()>0) {
            myholder.txt_quantity.setVisibility(View.VISIBLE);
            myholder.amt.setVisibility(View.VISIBLE);
            myholder.title.setVisibility(View.VISIBLE);
            myholder.minus.setVisibility(View.VISIBLE);
            myholder.plus.setVisibility(View.VISIBLE);
            string=model2.getStr_food_name();
            myholder.title.setText(string);
            myholder.txt_quantity.setText(String.valueOf(model2.getQuantity()));
            int r=Integer.parseInt(model2.getRuppee());
            myholder.amt.setText(String.valueOf(model2.getQuantity()*r));
        }
        else {
            myholder.txt_quantity.setVisibility(View.GONE);
            myholder.amt.setVisibility(View.GONE);
            myholder.title.setVisibility(View.GONE);
            myholder.minus.setVisibility(View.GONE);
            myholder.plus.setVisibility(View.GONE);
        }
        return  view;
    }

    class Myholder{
        TextView txt_quantity;
        TextView title;
        TextView amt;
        ImageView plus;
        ImageView minus;

    }
}
