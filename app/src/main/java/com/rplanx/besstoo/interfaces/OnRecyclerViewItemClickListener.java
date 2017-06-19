package com.rplanx.besstoo.interfaces;

import com.rplanx.besstoo.getset.Model2;

/**
 * Created by soumya on 11/4/17.
 */
public interface OnRecyclerViewItemClickListener<Moodel2> {
    void onMinusClick(Model2 product,String string);

    void onPlusClick(Model2 product,String string);
}
