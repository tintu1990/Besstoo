package com.rplanx.besstoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rplanx.besstoo.R;
import com.rplanx.besstoo.getset.Party;

import java.util.ArrayList;

/**
 * Created by soumya on 22/5/17.
 */
public class Party_cart_adapter  extends ArrayAdapter<Party> {
    ArrayList<Party> newmodels;
    LayoutInflater vi;
    int resource;
    Party model2;
    Context context;
    Party_newHolder newViewHolder;
    String string;

    public Party_cart_adapter(Context context, int resource, ArrayList<Party> modelCarts) {
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
            newViewHolder = new Party_newHolder();
            view = vi.inflate(resource, null);
            newViewHolder.txt_quantity = (TextView) view.findViewById(R.id.cart_quantity);
            newViewHolder.title = (TextView) view.findViewById(R.id.cart_dec);
            newViewHolder.amt=(TextView)view.findViewById(R.id.car_amt);
            view.setTag(newViewHolder);

        } else {
            newViewHolder = (Party_newHolder) view.getTag();
        }

            newViewHolder.title.setText(model2.getMenu_name());
            newViewHolder.txt_quantity.setText(model2.getQuantity());
            newViewHolder.amt.setText(model2.getAmount());

        return view;

    }

    class Party_newHolder {
        TextView txt_quantity;
        TextView title;
        TextView amt;
    }
}
