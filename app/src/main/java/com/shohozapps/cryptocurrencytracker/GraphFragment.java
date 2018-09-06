package com.shohozapps.cryptocurrencytracker;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class GraphFragment extends Fragment {

    private LineChart chart;
    private LineDataSet hourDataSet, dayDataSet, sevenDayDataSet, monthDataSet, yearDataSet;
    private RadioGroup radioGroup;
    private RadioButton hourRadioButton, dayRadioButton, sevenDayRadioButton;
    private RadioButton monthRadioButton, yearRadioButton;
    private TextView titleTextView;
    private String cryptoName;


    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

        attachIDs(view);
        setListeners();
        setGraphSettings(chart);

        return view;
    }

    // This method is called when all fetches are complete
    public void updateGraphFragment(Cryptocurrency selectedCrypto) {
        chart.clear();
        updateAllDataSets(selectedCrypto);
        cryptoName = selectedCrypto.getName();
        yearRadioButton.toggle();
        displaySeries(yearDataSet);
        titleTextView.setText(cryptoName + " 1 Year Graph View");
    }

    // This method just sets the graph's settings
    private void setGraphSettings(LineChart chart) {
        titleTextView.setPaintFlags(titleTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);

        chart.setTouchEnabled(false);
        chart.getDescription().setEnabled(false);
    }


    /**
     * These update... methods below simply take the currently selected cryptocurrency and cycle
     * through it historic price data and add the numbers to a data set to latter be displayed on
     * the graph.
     */
    private void updateAllDataSets(Cryptocurrency selectedCrypto) {
        updateHourDataSet(selectedCrypto);
        updateDayDataSet(selectedCrypto);
        update7DayDataSet(selectedCrypto);
        update30DayDataSet(selectedCrypto);
        updateYearDataSet(selectedCrypto);
    }

    private void updateHourDataSet(Cryptocurrency selectedCrypto) {
        List<Entry> entries = new ArrayList<>();

        for(int i = 0; i < 60; i++)
            entries.add(new Entry(i, (float) selectedCrypto.getIntraHourPrice(60-i)));

        hourDataSet = new LineDataSet(entries, "Minute Interval Pricing");
    }

    private void updateDayDataSet(Cryptocurrency selectedCrypto) {
        List<Entry> entries = new ArrayList<>();

        for(int i = 0; i < 288; i++)
            entries.add(new Entry(i, (float) selectedCrypto.getIntraDayPrice(288-i)));

        dayDataSet = new LineDataSet(entries, "Five Minute Interval Pricing");
    }

    private void update7DayDataSet(Cryptocurrency selectedCrypto) {
        List<Entry> entries = new ArrayList<>();

        for(int i = 0; i < 168; i++)
            entries.add(new Entry(i, (float) selectedCrypto.getIntraWeekPrice(168-i)));


        sevenDayDataSet = new LineDataSet(entries, "Hour Interval Pricing");
    }

    private void update30DayDataSet(Cryptocurrency selectedCrypto) {
        List<Entry> entries = new ArrayList<>();

        for(int i = 0; i < 240; i++)
            entries.add(new Entry(i, (float) selectedCrypto.getIntraMonthPrice(240-i)));

        monthDataSet = new LineDataSet(entries, "Three Hour Interval Pricing");
    }

    private void updateYearDataSet(Cryptocurrency selectedCrypto) {
        List<Entry> entries = new ArrayList<>();

        for(int i = 0; i < 365; i++)
            entries.add(new Entry(i, (float) selectedCrypto.getIntraYearPrice(365-i)));

        yearDataSet = new LineDataSet(entries, "Day Interval Pricing");
    }

    private void attachIDs(View view) {
        chart = view.findViewById(R.id.chart);
        titleTextView = view.findViewById(R.id.tv_title);
        radioGroup = view.findViewById(R.id.radioGroup);
        hourRadioButton = view.findViewById(R.id.rb_1h);
        dayRadioButton = view.findViewById(R.id.rb_1d);
        sevenDayRadioButton = view.findViewById(R.id.rb_7d);
        monthRadioButton = view.findViewById(R.id.rb_30d);
        yearRadioButton = view.findViewById(R.id.rb_1y);
    }

    private void setListeners() {

        hourRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySeries(hourDataSet);
                titleTextView.setText(cryptoName + " 1 Hour Graph View");
            }
        });

        dayRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySeries(dayDataSet);
                titleTextView.setText(cryptoName + " 1 Day Graph View");
            }
        });

        sevenDayRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySeries(sevenDayDataSet);
                titleTextView.setText(cryptoName + " 7 Day Graph View");
            }
        });

        monthRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySeries(monthDataSet);
                titleTextView.setText(cryptoName + " 30 Day Graph View");
            }
        });

        yearRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySeries(yearDataSet);
                titleTextView.setText(cryptoName + " 30 Day Graph View");
            }
        });
    }

    private void displaySeries(LineDataSet ds) {
        chart.clear();
        ds.setColors(new int[] {R.color.stockGreen}, getActivity());
        ds.setDrawCircles(false);
        ds.setDrawValues(false);
        chart.setData(new LineData(ds));
    }

}
