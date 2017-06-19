package com.rplanx.besstoo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rplanx.besstoo.Bakery;
import com.rplanx.besstoo.Besstoo_Kitchen;
import com.rplanx.besstoo.Party_plan;

/**
 * Created by soumya on 24/2/17.
 */
public class TabAdapter  extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"GRAB A MEAL","HOME MADE BAKERY"," PLAN YOUR PARTY"};
    Context context;

    public TabAdapter(Context ctx,FragmentManager fm) {
        super(fm);
        context=ctx;
    }
    private Fragment f = null;

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                f = new Besstoo_Kitchen();
                break;
            case 1:
                f = new Bakery();
                break;
            case  2:
                f = new Party_plan();
                break;




        }
        return  f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(Object object){
       return POSITION_NONE;

    }

}
