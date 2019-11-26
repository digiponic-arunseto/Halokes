package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.digiponic.halokes.Models.ListAchievement;
import com.digiponic.halokes.Models.ListCounseling;
import com.digiponic.halokes.Models.ListViolation;
import com.digiponic.halokes.Models.ListViolationDetail;
import com.digiponic.halokes.Models.ModelCounseling;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CounselingFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;

    ScrollView svContent;
    TextView tvCounselingStudentName, tvCounselingClassName, tvCounselingStudentStatus, tvCounselingViolationPoint;
    LinearLayout llCounselingAchievementContainer, llCounselingViolationContainer;
    SpinKitView skvLoading, skvLoading1;
    TabLayout tlCategory;
    ViewFlipper vfCategory;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_counseling, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        svContent = view.findViewById(R.id.svContent);
        tvCounselingStudentName = view.findViewById(R.id.tvCounselingStudentName);
        tvCounselingClassName = view.findViewById(R.id.tvCounselingClassName);
        tvCounselingStudentStatus = view.findViewById(R.id.tvCounselingStudentStatus);
        tvCounselingViolationPoint = view.findViewById(R.id.tvCounselingViolationPoint);

        skvLoading = view.findViewById(R.id.skvLoading);
        skvLoading1 = view.findViewById(R.id.skvLoading1);

        tlCategory = view.findViewById(R.id.tlCategory);
        vfCategory = view.findViewById(R.id.vfCategory);
        llCounselingAchievementContainer = view.findViewById(R.id.llCounselingAchievementContainer);
        llCounselingViolationContainer = view.findViewById(R.id.llCounselingViolationContainer);


        configTabCategory();
        showCounseling();

        return view;
    }

    public void configTabCategory() {
        tlCategory.addTab(tlCategory.newTab().setText("Prestasi"));
        tlCategory.addTab(tlCategory.newTab().setText("Pelanggaran"));
        tlCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vfCategory.setDisplayedChild(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void showCounseling() {

        //hiding content first, to show the loading view
        svContent.setVisibility(View.GONE);
        skvLoading.setVisibility(View.VISIBLE);
        skvLoading1.setVisibility(View.VISIBLE);

        //removing all views from view flipper
        llCounselingAchievementContainer.removeAllViews();
        llCounselingViolationContainer.removeAllViews();


        Call<ModelCounseling> call = RetrofitClient.getInstance().getApi()
                .showCounseling(session.getUser().getId_user());
        call.enqueue(new Callback<ModelCounseling>() {
            @Override
            public void onResponse(Call<ModelCounseling> call, Response<ModelCounseling> response) {
                ModelCounseling mc = response.body();
                if (isAdded() && response.isSuccessful()) {
                    ListCounseling lcData = mc.getData();
                    tvCounselingStudentName.setText(lcData.getNama_siswa());
                    tvCounselingClassName.setText(lcData.getKelas());
                    tvCounselingStudentStatus.setText(lcData.getStatus_siswa());
                    tvCounselingViolationPoint.setText(lcData.getData_pelanggaran().getPoin_pelanggaran()+"");
                    for (ListAchievement laData :
                            lcData.getData_prestasi()) {
                        View rowAchievement = getLayoutInflater().inflate(R.layout.template_counseling_achievement, null);
                        TextView tvAchievementName = rowAchievement.findViewById(R.id.tvAchievementName);
                        TextView tvAchievementDesc = rowAchievement.findViewById(R.id.tvAchievementDesc);
                        TextView tvAchievementDate = rowAchievement.findViewById(R.id.tvAchievementDate);
                        TextView tvAchievementLevel = rowAchievement.findViewById(R.id.tvAchievementLevel);

                        tvAchievementName.setText(laData.getNama_lomba());
                        tvAchievementDesc.setText(laData.getPrestasi_keterangan());
                        tvAchievementDate.setText(laData.getTanggal());
                        tvAchievementLevel.setText("Tingkat "+laData.getTingkat());

                        llCounselingAchievementContainer.addView(rowAchievement);
                    }
                    for (ListViolationDetail lvdData :
                            lcData.getData_pelanggaran().getData_pelanggaran_detail()) {
                        View rowViolation = getLayoutInflater().inflate(R.layout.template_counseling_violation, null);
                        TextView tvViolationName = rowViolation.findViewById(R.id.tvViolationName);
                        TextView tvViolationDate = rowViolation.findViewById(R.id.tvViolationDate);
                        TextView tvViolationLevel = rowViolation.findViewById(R.id.tvViolationLevel);
                        TextView tvViolationPoint = rowViolation.findViewById(R.id.tvViolationPoint);

                        tvViolationName.setText(lvdData.getPelanggaran());
                        tvViolationDate.setText(lvdData.getTanggal());
                        tvViolationLevel.setText("Kategori : " + lvdData.getKategori());
                        tvViolationPoint.setText(lvdData.getPoin());

                        llCounselingViolationContainer.addView(rowViolation);
                    }


                    svContent.setVisibility(View.VISIBLE);
                    skvLoading.setVisibility(View.GONE);
                    skvLoading1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ModelCounseling> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
