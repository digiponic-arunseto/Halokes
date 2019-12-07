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
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListGrade;
import com.digiponic.halokes.Models.ListGradeDetail;
import com.digiponic.halokes.Models.ModelGrade;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;

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
                                TextView tvGradeDetailSubject = vGrade.findViewById(R.id.tvGradeDetailSubject);
                                TextView tvGradeDetailType = vGrade.findViewById(R.id.tvGradeDetailType);
                                TextView tvGradeDetailGrade = vGrade.findViewById(R.id.tvGradeDetailGrade);
                                TextView tvGradeDetailFail = vGrade.findViewById(R.id.tvGradeDetailFail);
                                BarChart bcGrade = vGrade.findViewById(R.id.bcGrade);

                                tvGradeDetailSubject.setText(lgData.getMapel());
                                tvGradeDetailType.setText(lgdData.getTopik());
                                int[] grade = lgdData.getNilai();
                                tvGradeDetailGrade.setText(calculateAvgGrade(grade) + "");
                                if (grade.length != 1) {
                                    showGraph(bcGrade, grade);
                                    tvGradeDetailFail.setText(calculateFailedGrade(grade) + "");
                                } else {
                                    bcGrade.setVisibility(View.GONE);
                                    View failParent = (View) tvGradeDetailFail.getParent();
                                    failParent.setVisibility(View.GONE);
                                }


                                llGradeDetailContainer.addView(vGrade);
                            }
                        } else {
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

    public double calculateAvgGrade(int[] grade) {
        double total = 0;
        for (int val : grade) {
            total += val;
        }
        return total / grade.length;
    }

    public int calculateFailedGrade(int[] grade) {
        int count = 0;
        for (int val : grade) {
            if (val < 75) {
                count++;
            }
        }
        return count;
    }

    public void showGraph(BarChart bcGraph, int[] grade) {

        ArrayList<BarEntry> entries = new ArrayList<>();
        int count = 1;
        for (int val : grade) {
            entries.add(new BarEntry(count, val));
            count++;
        }
        BarDataSet dataset = new BarDataSet(entries, "Tugas");
//        dataset.setColors(ColorTemplate.LIBERTY_COLORS);

        //set data
        BarData data = new BarData(dataset);
        bcGraph.setData(data);


        //config y axis
        XAxis xAxis = bcGraph.getXAxis();

        //config x axis
        YAxis yAxis = bcGraph.getAxisLeft();

        //Styling
        //data set style
        dataset.setColor(getResources().getColor(R.color.colorPrimary));
        dataset.setValueTextColor(getResources().getColor(R.color.colorPrimary));
        dataset.setValueTextSize(15f);

        //x axis style
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM.BOTTOM);
        xAxis.setGridColor(getResources().getColor(R.color.colorBackground));
        xAxis.setGranularityEnabled(true);
        xAxis.setTextColor(getResources().getColor(R.color.colorPrimary));
        xAxis.setAxisLineColor(getResources().getColor(R.color.colorPrimary));

        //y axis style
        yAxis.setAxisMaximum(100);
        yAxis.setAxisMinimum(0);
        yAxis.setGridColor(getResources().getColor(R.color.colorBackground));
        yAxis.setTextColor(getResources().getColor(R.color.colorPrimary));
        yAxis.setAxisLineColor(getResources().getColor(R.color.colorPrimary));

        //data  style
        data.setHighlightEnabled(true);
        data.setBarWidth(0.4f);
        //graph style
        bcGraph.setDrawValueAboveBar(true);
        //fitting bar
        bcGraph.setFitBars(true);
        bcGraph.animateY(1200);
        bcGraph.getDescription().setEnabled(false);
        //remove right label
        bcGraph.getAxisRight().setEnabled(false);


    }

}
