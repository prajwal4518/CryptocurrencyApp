package com.shohozapps.cryptocurrencytracker;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;


public class CryptocurrencyModel extends AndroidViewModel implements OnFetchesCompleteListener {

    public Cryptocurrency selectededCrypto;
    public HomeFragment homeFragment;
    public GraphFragment graphFragment;
    public ConverterFragment converterFragment;

    public MutableLiveData<Boolean> isLoading;

    private Application application;


    public CryptocurrencyModel(@NonNull Application applicationParam) {
        super(applicationParam);


        application = applicationParam;

        homeFragment = new HomeFragment();
        graphFragment = new GraphFragment();
        converterFragment = new ConverterFragment();
        selectededCrypto = new Bitcoin(application);
        selectededCrypto.setOnFetchesCompleteListener(this);


        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);
        isLoading.setValue(true);
    }


    @Override
    public void onFetchesComplete() {
        homeFragment.updateHomeFragment(selectededCrypto);
        graphFragment.updateGraphFragment(selectededCrypto);
        converterFragment.updateConverterFragment(selectededCrypto);
        isLoading.setValue(!isLoading.getValue());
    }

    public void changeSelectedCrypto(int i) {
        if(i == 0)
            selectededCrypto = new Bitcoin(application, this);
        else if(i == 1)
            selectededCrypto = new Ethereum(application, this);
        else if(i == 2)
            selectededCrypto = new Litecoin(application, this);

    }


}
