package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digiponic.halokes.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class SummaryFragment extends Fragment {
    View view;
    int flContent;
    Context context;

    GraphView graph, graph1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_summary, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();

        graph = view.findViewById(R.id.lineGraph);
        graph1 = view.findViewById(R.id.barGraph);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(100);

        graph1.getViewport().setYAxisBoundsManual(true);
        graph1.getViewport().setMaxY(110);

        try {
            DataPoint[] dp = new DataPoint[10];
            for (int i = 0; i < dp.length; i++) {
                dp[i] = new DataPoint(i + 1, (i + 1) * 10);
            }
            //data set for line graph
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(
                    dp
            );

            series.setDrawDataPoints(true);
            series.setDataPointsRadius(12);
            series.setThickness(6);

            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
            String[] labelX = new String[dp.length];
            for (int i = 0; i < labelX.length; i++) {
                labelX[i] = (i + 1) + "";
            }
            staticLabelsFormatter.setHorizontalLabels(labelX);
            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            graph.addSeries(series);
//========================================BAR GRAPH=================================================

            //data set for bar graph
            BarGraphSeries<DataPoint> series1 = new BarGraphSeries<>(
                    dp
            );

            //spacing
            series1.setSpacing(24);
            series1.setDataWidth(0.6);

            // draw values on top
            series1.setDrawValuesOnTop(true);
            series1.setValuesOnTopColor(getResources().getColor(R.color.colorAccent));

            // static LABEL
            StaticLabelsFormatter staticLabelsFormatter1 = new StaticLabelsFormatter(graph1);
            String[] labelX1 = new String[dp.length];
            for (int i = 0; i < labelX1.length; i++) {
                labelX1[i] = (i + 1) + "";
            }
            staticLabelsFormatter1.setHorizontalLabels(labelX1);
            graph1.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter1);

            graph1.addSeries(series1);

        } catch (IllegalArgumentException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

//        //line graph settings
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(10);

//        //scrollable & zoomable
//        graph.getViewport().setScalable(true);
//        graph.getViewport().setScrollable(true);


//        //bar graph settings
        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMaxX(10);

//        //scrollable & zoomable
//        graph1.getViewport().setScalable(true);
//        graph1.getViewport().setScrollable(true);

        return view;
    }


}
