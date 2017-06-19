package com.rplanx.besstoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rplanx.besstoo.R;
import com.rplanx.besstoo.getset.Model2;

import java.util.ArrayList;

/**
 * Created by soumya on 10/3/17.
 */
public class CartAdapter extends ArrayAdapter<Model2> {
    ArrayList<Model2> newmodels;
    LayoutInflater vi;
    int resource;
    Model2 model2;
    Context context;
    NewViewHolder newViewHolder;
    String string;

    public CartAdapter(Context context, int resource, ArrayList<Model2> modelCarts) {
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
        if (view == null) {
            newViewHolder = new NewViewHolder();
                view = vi.inflate(resource, null);
                newViewHolder.txt_quantity = (TextView) view.findViewById(R.id.cart_quantity);
                newViewHolder.title = (TextView) view.findViewById(R.id.cart_dec);
                newViewHolder.amt=(TextView)view.findViewById(R.id.car_amt);
                view.setTag(newViewHolder);

        } else {
            newViewHolder = (NewViewHolder) view.getTag();
        }
       if (model2.getQuantity()>0) {
            newViewHolder.txt_quantity.setVisibility(View.VISIBLE);
            newViewHolder.amt.setVisibility(View.VISIBLE);
            newViewHolder.title.setVisibility(View.VISIBLE);
            string=model2.getStr_food_name();
            newViewHolder.title.setText(string);
            newViewHolder.txt_quantity.setText(String.valueOf(model2.getQuantity()));
            String inp_str=model2.getRuppee();
            inp_str=inp_str.replaceAll("[^\\d.]", "");
           int r=Integer.parseInt(inp_str);
           newViewHolder.amt.setText(String.valueOf(model2.getQuantity()*r));
      }
       else {
            newViewHolder.txt_quantity.setVisibility(View.GONE);
            newViewHolder.title.setVisibility(View.GONE);
            newViewHolder.amt.setVisibility(View.GONE);
        }
        return view;
    }
    class NewViewHolder {
        TextView txt_quantity;
        TextView title;
        TextView amt;
    }

}
