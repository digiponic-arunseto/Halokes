package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListGrade;
import com.digiponic.halokes.Models.ListGradeDetail;
import com.digiponic.halokes.Models.ModelGrade;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradeFragment extends Fragment {
    View view;
    int flContent;
    Context context;
    Session session;
    LinearLayout llGradeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grade, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);
        llGradeContainer = view.findViewById(R.id.llGradeContainer);


        showGrade();

        return view;
    }

    public void showGrade() {

        Call<ModelGrade> call = RetrofitClient.getInstance().getApi().showGrade(session.getUser().getId_user());
        call.enqueue(new Callback<ModelGrade>() {
            @Override
            public void onResponse(Call<ModelGrade> call, Response<ModelGrade> response) {
                ModelGrade mg = response.body();
                if (isAdded() && response.isSuccessful()) {
                    llGradeContainer.removeAllViews();
                    for (ListGrade lg : mg.getData()) {
                        View rowGradeSubject = getLayoutInflater().inflate(R.layout.template_grade_subject, null);
                        TextView tvGradeSubject = rowGradeSubject.findViewById(R.id.tvGradeSubject);
                        TextView tvGradeSubjectGrade = rowGradeSubject.findViewById(R.id.tvGradeSubjectGrade);
                        LinearLayout llGradeDetailContainer = rowGradeSubject.findViewById(R.id.llGradeDetailContainer);
                        llGradeDetailContainer.removeAllViews();

                        tvGradeSubject.setText(lg.getMapel());

                        tvGradeSubjectGrade.setText(lg.getNilai_keseluruhan()+"");
                        int nilaiKeseluruhan = lg.getNilai_keseluruhan();
                        if (nilaiKeseluruhan < 50) {
                            tvGradeSubjectGrade.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_danger));
                        } else if (nilaiKeseluruhan < 70) {
                            tvGradeSubjectGrade.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_warning));
                        } else if (nilaiKeseluruhan <= 100) {
                            tvGradeSubjectGrade.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_success));
                        }
                        if (lg.getNilai() != null) {

                            for (ListGradeDetail lgd : lg.getNilai()) {
                                View rowGradeDetail = getLayoutInflater().inflate(R.layout.template_grade_detail, null);
                                TextView tvGradeDetailType = rowGradeDetail.findViewById(R.id.tvGradeDetailType);
                                TextView tvGradeDetailGrade = rowGradeDetail.findViewById(R.id.tvGradeDetailGrade);

                                tvGradeDetailType.setText(lgd.getTopik());
                                tvGradeDetailGrade.setText(lgd.getNilai());
                                int nilai = Integer.parseInt(lgd.getNilai());
                                if (nilai < 50) {
                                    tvGradeDetailGrade.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_danger));
                                } else if (nilai < 70) {
                                    tvGradeDetailGrade.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_warning));
                                } else if (nilai <= 100) {
                                    tvGradeDetailGrade.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_success));
                                }


                                llGradeDetailContainer.addView(rowGradeDetail);
                            }
                        }
                        llGradeContainer.addView(rowGradeSubject);
                    }
                } else {
                    Toast.makeText(context, mg.getMessage()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelGrade> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
