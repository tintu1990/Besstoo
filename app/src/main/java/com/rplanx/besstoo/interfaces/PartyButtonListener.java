package com.rplanx.besstoo.interfaces;

import com.rplanx.besstoo.getset.Party;

/**
 * Created by soumya on 22/4/17.
 */
public interface PartyButtonListener {
    void onMinusClick(Party product,String string);

    void onPlusClick(Party product,String string);
}
