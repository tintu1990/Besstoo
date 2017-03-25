package com.rplanx.besstoo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rplanx.besstoo.OneFragment;
import com.rplanx.besstoo.TwoFragment;

/**
 * Created by soumya on 24/2/17.
 */
public class TabAdapter  extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"BESSTOO KITCHEN", "HOME MADE DELECACY"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // Top Rated fragment activity
                return  OneFragment.newInstance(position+1);


            case 1:

                return TwoFragment.newInstance(position+1);
    /*               case 2:
    return  ThirdFragment.newInstance(position+1);*/

            // Games fragment activity
        }
        return  null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


    @Override
    public int getCount() {
        return 2;
    }
}
