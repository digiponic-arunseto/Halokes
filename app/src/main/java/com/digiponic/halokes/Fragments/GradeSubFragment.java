package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListGrade;
import com.digiponic.halokes.Models.ListGradeDetail;
import com.digiponic.halokes.Models.ModelGrade;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradeSubFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    SpinKitView skvLoading;
    LinearLayout llGradeDetailContainer;
    TabLayout tabDots;
    HorizontalScrollView hsvGradeDetailContainer;
    TextView tvGradeSubject;
    private ListGrade lData;

    public void setlData(ListGrade lData) {
        this.lData = lData;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.template_grade, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        llGradeDetailContainer = view.findViewById(R.id.llGradeDetailContainer);
        tabDots = view.findViewById(R.id.tabDots);
        hsvGradeDetailContainer = view.findViewById(R.id.hsvGradeDetailContainer);
        skvLoading = view.findViewById(R.id.skvLoading);
//        tvGradeSubject = view.findViewById(R.id.tvGradeSubject);

        try {
            showGrade();

        } catch (Exception e) {
            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    public void showGrade() {
        llGradeDetailContainer.removeAllViews();

        Call<ModelGrade> call = RetrofitClient.getInstance().getApi().showGradeDetail(
                session.getUser().getId_user(),
                lData.getId_mapel());
        call.enqueue(new Callback<ModelGrade>() {
            @Override
            public void onResponse(Call<ModelGrade> call, Response<ModelGrade> response) {
                ModelGrade mg = response.body();
                if (isAdded() && response.isSuccessful()) {
                    for (ListGrade lgData : mg.getData()) {
                        //
                        if (!lgData.getData_nilai().isEmpty()) {
//                            tvGradeSubject.setText("xD");
                            configTabDotsIndicator(lgData.getData_nilai().size());

                            for (ListGradeDetail lgdData : lgData.getData_nilai()) {
                                View vGrade = getLayoutInflater().inflate(R.layout.template_grade_detail, null);
                                TextView tvGradeDetailType = vGrade.findViewById(R.id.tvGradeDetailType);
                                TextView tvGradeDetailGrade = vGrade.findViewById(R.id.tvGradeDetailGrade);
                                GraphView gvGradeDetailGraph = vGrade.findViewById(R.id.gvGradeDetailGraph);

                                tvGradeDetailType.setText(lgdData.getTopik());
                                tvGradeDetailGrade.setText(lgdData.getNilai());
                                populateGraph(gvGradeDetailGraph);

                                llGradeDetailContainer.addView(vGrade);
                            }
                        } else {
//                            tvGradeSubject.setText("------------");
//                            Toast.makeText(context, "null gan", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
//                    Toast.makeText(context, mg.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
                skvLoading.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ModelGrade> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void populateGraph(GraphView graph1) {

        DataPoint[] dp = new DataPoint[7];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = new DataPoint(i, (i+1) * 10);
        }
        graph1.getViewport().setYAxisBoundsManual(true);
        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMaxY(110);
        graph1.getViewport().setMaxX(dp.length+1);
        graph1.getViewport().setScrollable(true);
        graph1.getViewport().setScalable(true);



        //data set for bar graph
        BarGraphSeries<DataPoint> series1 = new BarGraphSeries<>(
                dp
        );

        //spacing
        series1.setDataWidth(0.6);

        // draw values on top
        series1.setDrawValuesOnTop(true);
        series1.setValuesOnTopColor(getResources().getColor(R.color.colorAccent));
        series1.setAnimated(true);

        // static LABEL
        StaticLabelsFormatter staticLabelsFormatter1 = new StaticLabelsFormatter(graph1);
        String[] labelX1 = new String[dp.length];
        for (int i = 0; i < labelX1.length; i++) {
            labelX1[i] = (i + 1) + "";
        }
        staticLabelsFormatter1.setHorizontalLabels(labelX1);

        graph1.addSeries(series1);


    }

    public void configTabDotsIndicator(int tabCount) {
        for (int i = 1; i <= tabCount; i++) {
            //add tab
            tabDots.addTab(tabDots.newTab());
        }

        //tab item margin
        for (int i = 0; i < tabDots.getTabCount(); i++) {
            View tab = ((ViewGroup) tabDots.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(6, 0, 6, 0);
            tab.requestLayout();
        }


        hsvGradeDetailContainer.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (isAdded()) {
                    int interval = (hsvGradeDetailContainer.getScrollX() / 640);

                    if (hsvGradeDetailContainer.getChildAt(0).getRight()
                            == (hsvGradeDetailContainer.getWidth() + hsvGradeDetailContainer.getScrollX())) {
                        //scroll view is at bottom
                        tabDots.getTabAt(tabDots.getTabCount() - 1).select();
                    } else {
                        //scroll view is not at bottom
                        tabDots.getTabAt(interval).select();
                    }
                }

            }
        });
    }

}
