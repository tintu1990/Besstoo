package com.rplanx.besstoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rplanx.besstoo.R;
import com.rplanx.besstoo.getset.Locations;

import java.util.List;

/**
 * Created by soumya on 21/4/17.
 */
public class Locations_adapter extends ArrayAdapter<Locations> {
    LayoutInflater vi;
    int resource;
    Context context;
    Location_holder location_holder;
    List<Locations> locations;
    Locations obj_locations;


    public Locations_adapter(Context context, int resource, List<Locations> locations) {
        super(context, resource,locations);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.resource = resource;
        this.locations=locations;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        obj_locations=locations.get(position);
        if (view == null) {
            location_holder=new Location_holder();
            view = vi.inflate(resource, null);
            location_holder.txt_location_name=(TextView)view.findViewById(R.id.address_header);
            location_holder.txt_location_city=(TextView)view.findViewById(R.id.address3);
            location_holder.txt_user_location_name=(TextView)view.findViewById(R.id.address1);
            location_holder.txt_location_details=(TextView)view.findViewById(R.id.address2);
            view.setTag(location_holder);
        }
        else{
            location_holder=(Location_holder)view.getTag();
        }

        location_holder.txt_location_name.setText(obj_locations.getLocation_name());
        location_holder.txt_location_details.setText(obj_locations.getUser_location_details());
        location_holder.txt_user_location_name.setText(obj_locations.getUser_location_name());
        location_holder.txt_location_city.setText(obj_locations.getLocation_city());

        return  view;
    }

    class Location_holder{
        TextView txt_location_name;
        TextView txt_location_city;
        TextView txt_user_location_name;
        TextView txt_location_details;

    }
}
