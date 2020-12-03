package com.example.examplegraph;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieActivity extends AppCompatActivity {
    PieChart pieChart;
    ArrayList<PieEntry> pieEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        pieChart = findViewById(R.id.pie);
        pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(1,"2015"));
        pieEntries.add(new PieEntry(2,"2016"));
        pieEntries.add(new PieEntry(3,"2017"));
        pieEntries.add(new PieEntry(4,"2018"));
        pieEntries.add(new PieEntry(5,"2019"));

        PieDataSet set = new PieDataSet(pieEntries,"students");
        set.setColors(ColorTemplate.PASTEL_COLORS);
        set.setValueTextSize(16f);
        set.setValueTextColor(Color.WHITE);

        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.setCenterText("Students");
        pieChart.animate();

    }
}