package com.example.examplegraph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.examplegraph.databinding.ActivityBarBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarActivity extends AppCompatActivity {
    BarChart bc;
    ArrayList<BarEntry> series;
    ActivityBarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_bar);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bar);
       // bc=findViewById(R.id.bar);
       /* series = new ArrayList<>();

        series.add(new BarEntry(2000f,1));
        series.add(new BarEntry(2001f,2));
        series.add(new BarEntry(2002f,3));
        series.add(new BarEntry(2003f,4));
        series.add(new BarEntry(2004f,5));

        BarDataSet set = new BarDataSet(series,"Sales");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(16f);

        BarData data = new BarData(set);
        bc.setData(data);*/

    }

    public void plot(View view) {
        binding.bar.clear();
        float x1 = Float.parseFloat(binding.x1.getText().toString());
        float x2 = Float.parseFloat(binding.x2.getText().toString());
        float x3 = Float.parseFloat(binding.x3.getText().toString());
        float x4 = Float.parseFloat(binding.x4.getText().toString());
        float x5 = Float.parseFloat(binding.x5.getText().toString());
        int y1 = Integer.parseInt(binding.y1.getText().toString());
        int y2 = Integer.parseInt(binding.y2.getText().toString());
        int y3 = Integer.parseInt(binding.y3.getText().toString());
        int y4 = Integer.parseInt(binding.y4.getText().toString());
        int y5 = Integer.parseInt(binding.y5.getText().toString());
        series = new ArrayList<>();
        series.add(new BarEntry(x1,y1));
        series.add(new BarEntry(x2,y2));
        series.add(new BarEntry(x3,y3));
        series.add(new BarEntry(x4,y4));
        series.add(new BarEntry(x5,y5));

        BarDataSet set = new BarDataSet(series,"Sales");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(16f);

        BarData data = new BarData(set);
        binding.bar.setData(data);
    }
}