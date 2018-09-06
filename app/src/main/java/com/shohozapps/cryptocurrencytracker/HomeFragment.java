package com.shohozapps.cryptocurrencytracker;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeFragment extends Fragment {

    private TextView oneCryptoTextView = null;
    private TextView hourTextView, dayTextView, sevenDayTextView, monthTextView, yearTextView;
    private ImageView logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        attachIDs(view);



        return view;
    }

    private void attachIDs(View view) {
        oneCryptoTextView = view.findViewById(R.id.tv_oneCrypto);
        hourTextView = view.findViewById(R.id.tv_1h);
        dayTextView = view.findViewById(R.id.tv_24h);
        sevenDayTextView = view.findViewById(R.id.tv_7D);
        monthTextView = view.findViewById(R.id.tv_30D);
        yearTextView = view.findViewById(R.id.tv_1Y);
        logo = view.findViewById(R.id.iv_logo);
    }


    /** COME BACK TOO
     * This method below is sometimes called before the fragment's onCreateView which then causes a
     * null pointer exception. Temporary fix is to sleep the thread for a little bit then recall
     * the method in hopes that the fragment's onCreateView has been called and the TextViews
     * are no longer null.
     */
    public void updateHomeFragment(Cryptocurrency selectedCrypto) {


        if(oneCryptoTextView == null) {
            Log.v("tester", "WAS NULL");
            final Cryptocurrency temp = selectedCrypto;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateHomeFragment(temp);
                }
            },15);

            return;
        }

        oneCryptoTextView.setText("One " + selectedCrypto.getName() + " = " + selectedCrypto.getCurrentPrice());

        display(selectedCrypto.getHourChange(), hourTextView, "1H: ");
        display(selectedCrypto.getDayChange(), dayTextView, "1D: ");
        display(selectedCrypto.getWeekChange(), sevenDayTextView, "7D: ");
        display(selectedCrypto.getMonthChange(), monthTextView, "30D: ");
        display(selectedCrypto.getYearChange(), yearTextView, "1Y: ");

        logo.setImageResource(selectedCrypto.getLogoID());
    }

    private void display(double percentage, TextView et, String interval) {

        if(percentage > 0)
            et.setTextColor(getResources().getColor(R.color.stockGreen));
        else if(percentage < 0)
            et.setTextColor(getResources().getColor(R.color.stockRed));
        else
            et.setTextColor(getResources().getColor(R.color.stockBlack));

        et.setText(interval + String.format("%.2f",percentage) + '%');

    }

}
