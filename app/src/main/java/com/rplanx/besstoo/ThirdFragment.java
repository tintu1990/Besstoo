package com.rplanx.besstoo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by soumya on 24/2/17.
 */
public class ThirdFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    ListView listView;


    public static ThirdFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ThirdFragment fragment = new ThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.food_list3,container,false);
          listView= (ListView) rootView.findViewById(R.id.list2);
        return rootView;
    }
}
