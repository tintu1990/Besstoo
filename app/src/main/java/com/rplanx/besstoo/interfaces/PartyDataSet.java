package com.rplanx.besstoo.interfaces;

import com.rplanx.besstoo.getset.Party;

/**
 * Created by soumya on 22/4/17.
 */
public interface PartyDataSet {
    int size();

    Party get(int position);

    long getId(int position);
}
