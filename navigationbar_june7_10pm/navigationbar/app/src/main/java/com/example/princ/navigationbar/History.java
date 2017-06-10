package com.example.princ.navigationbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewManager;
import android.widget.FrameLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class History extends MainActivity{

    private LineChart mChart;
    static final private String LOG_TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ViewManager)findViewById(R.id.flContent).getParent()).removeView(findViewById(R.id.flContent)); //Remove the other frame layout
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.f2Content); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.content_history, contentFrameLayout);

        mChart = (LineChart) findViewById(R.id.lineChart);
        mChart.setDrawGridBackground(false); //if you set this to true you will get a weird grey color

        // add data
        setData();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend
        l.setForm(Legend.LegendForm.LINE);

        //description text
        mChart.setDescription("Your protien intake for the past 5 days");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.getAxisRight().setEnabled(false);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChart.invalidate();     // to refresh the drawing

    }

    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        Calendar cal = Calendar.getInstance();
        Date date;

        // loop adding one day in each iteration
        for(int i = 0; i< 5; i++){
            cal.add(Calendar.DAY_OF_MONTH,-1);
            date=cal.getTime();
            xVals.add(sdf.format(date));
            System.out.println(sdf.format(cal.getTime()));
        }
        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(60, 0)); //most recent protien intake
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(70.5f, 2));
        yVals.add(new Entry(100, 3));
        yVals.add(new Entry(180.9f, 4));

        //It is really important that xVals and yVals are the same size -- or else this activity will crash!!!
        return yVals;
    }

    private void setData() {
        ArrayList<String> xVals = setXAxisValues();
        ArrayList<Entry> yVals = setYAxisValues();

        // create a dataset and give it a type
        LineDataSet set1;
        set1 = new LineDataSet(yVals, "DataSet 1");

        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true); //to get the blue filling in the back, set this to true

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        // set data
        mChart.setData(data);
    }
}