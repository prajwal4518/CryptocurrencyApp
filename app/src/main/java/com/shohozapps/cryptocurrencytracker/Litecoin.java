package com.shohozapps.cryptocurrencytracker;

import android.content.Context;

public class Litecoin extends Cryptocurrency {

    public static final int logo = R.drawable.logo_litecoin;
    private static final String NAME = "Litecoin", TICKER = "LTC";

    public Litecoin(Context c) {
        super(c);
        fetchAll();
    }

    public Litecoin(Context c, OnFetchesCompleteListener listener) {
        super(c);
        this.mListener = listener;

        fetchAll();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTicker() {
        return TICKER;
    }

    @Override
    public int getLogoID() {
        return logo;
    }
}
