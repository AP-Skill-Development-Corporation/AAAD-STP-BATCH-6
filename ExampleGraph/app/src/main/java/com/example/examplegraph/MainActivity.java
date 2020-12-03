package com.example.examplegraph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.examplegraph.databinding.ActivityMainBinding;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.net.Inet4Address;

public class MainActivity extends AppCompatActivity {
    GraphView gv;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
       // gv = findViewById(R.id.lineGraph);
       /* LineGraphSeries<DataPoint> series = new LineGraphSeries<>(
                new DataPoint[]{new DataPoint(0,0),new DataPoint(1,1),new DataPoint(2,4)
                ,new DataPoint(3,6),new DataPoint(4,8)}
        );
        gv.addSeries(series);*/
    }

    public void plot(View view) {
        binding.lineGraph.removeAllSeries();
        double x1 = Double.parseDouble(binding.x1.getText().toString());
        double x2 = Double.parseDouble(binding.x2.getText().toString());
        double x3 = Double.parseDouble(binding.x3.getText().toString());
        double x4 = Double.parseDouble(binding.x4.getText().toString());
        double x5 = Double.parseDouble(binding.x5.getText().toString());
        double y1 = Double.parseDouble(binding.y1.getText().toString());
        double y2 = Double.parseDouble(binding.y2.getText().toString());
        double y3 = Double.parseDouble(binding.y3.getText().toString());
        double y4 = Double.parseDouble(binding.y4.getText().toString());
        double y5 = Double.parseDouble(binding.y5.getText().toString());
         LineGraphSeries<DataPoint> series = new LineGraphSeries<>(
                new DataPoint[]{
                        new DataPoint(x1,y1),
                        new DataPoint(x2,y2),
                        new DataPoint(x3,y3),
                        new DataPoint(x4,y4),
                        new DataPoint(x5,y5)}
        );
        binding.lineGraph.addSeries(series);
    }

    public void bargraph(View view) {
        startActivity(new Intent(this,BarActivity.class));
    }

    public void piechart(View view) {
    startActivity(new Intent(this,PieActivity.class));
    }
}