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

import com.digiponic.halokes.Models.ListExtra;
import com.digiponic.halokes.Models.ListExtraDetail;
import com.digiponic.halokes.Models.ModelExtra;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExtraFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;

    ScrollView svContent;
    TextView tvExtraStudentName, tvExtraStudentInfo;
    TextView tvExtraDay, tvExtraHours, tvExtraPlace;
    SpinKitView skvLoading;
    TabLayout tlCategory;
    ViewFlipper vfCategory;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_extra, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        svContent = view.findViewById(R.id.svContent);
        tvExtraStudentName = view.findViewById(R.id.tvExtraStudentName);
        tvExtraStudentInfo = view.findViewById(R.id.tvExtraStudentInfo);
        tvExtraDay = view.findViewById(R.id.tvExtraDay);
        tvExtraHours = view.findViewById(R.id.tvExtraHours);
        tvExtraPlace = view.findViewById(R.id.tvExtraPlace);
        skvLoading = view.findViewById(R.id.skvLoading);

        tlCategory = view.findViewById(R.id.tlCategory);
        vfCategory = view.findViewById(R.id.vfCategory);

        showExtra();

        return view;
    }

    public void showExtra() {

        //hiding content first, to show the loading view
        vfCategory.removeAllViews();
        svContent.setVisibility(View.GONE);
        skvLoading.setVisibility(View.VISIBLE);

        Call<ModelExtra> call = RetrofitClient.getInstance().getApi()
                .showExtra(session.getUser().getId_user());
        call.enqueue(new Callback<ModelExtra>() {
            @Override
            public void onResponse(Call<ModelExtra> call, Response<ModelExtra> response) {
                ModelExtra me = response.body();
                if (isAdded() && response.isSuccessful()) {

                    tvExtraStudentName.setText(session.getUser().getNama_siswa());
                    tvExtraStudentInfo.setText(session.getUser().getNis() + " / " + session.getUser().getKelas());

                    for (final ListExtra leData : me.getData()) {
                        View rowExtra = getLayoutInflater().inflate(R.layout.template_extra, null);
                        tlCategory.addTab(tlCategory.newTab().setText(leData.getNama()));
                        tvExtraDay.setText(leData.getJadwal());
                        tvExtraHours.setText(leData.getJam());
                        tvExtraPlace.setText(leData.getTempat());
                        showExtraDetail(leData, rowExtra);
                        vfCategory.addView(rowExtra);
                    }

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
                    svContent.setVisibility(View.VISIBLE);
                    skvLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ModelExtra> call, Throwable t) {
                Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showExtraDetail(ListExtra leData, View rowExtra) {
        //showing extra detail
        final SpinKitView skvLoading1 = rowExtra.findViewById(R.id.skvLoading);
        final LinearLayout llExtraDetailContainer = rowExtra.findViewById(R.id.llExtraDetailContainer);
        llExtraDetailContainer.removeAllViews();

        Call<ModelExtra> callDetail = RetrofitClient.getInstance().getApi()
                .showExtraDetail(
                        session.getUser().getId_user(),
                        leData.getEkskul_url());
        callDetail.enqueue(new Callback<ModelExtra>() {
            @Override
            public void onResponse(Call<ModelExtra> call, Response<ModelExtra> response) {
                ModelExtra meDetail = response.body();
                if (isAdded() && response.isSuccessful()) {
                    for (ListExtra leData : meDetail.getData()) {
                        for (ListExtraDetail ledData : leData.getData_kegiatan()) {
                            View rowExtraDetail = getLayoutInflater().inflate(R.layout.template_extra_detail, null);
                            TextView tvExtraActivityName = rowExtraDetail.findViewById(R.id.tvExtraActivityName);
                            TextView tvExtraActivityDesc = rowExtraDetail.findViewById(R.id.tvExtraActivityDesc);
                            TextView tvExtraActivityDate = rowExtraDetail.findViewById(R.id.tvExtraActivityDate);
                            TextView tvExtraActivityPlace = rowExtraDetail.findViewById(R.id.tvExtraActivityPlace);

                            tvExtraActivityName.setText(ledData.getJudul());
                            tvExtraActivityDesc.setText(ledData.getDeskripsi());
                            tvExtraActivityDate.setText(ledData.getTanggal());
                            tvExtraActivityPlace.setText(ledData.getTempat());

                            llExtraDetailContainer.addView(rowExtraDetail);
                        }
                    }
                    skvLoading1.setVisibility(View.GONE);
                } else {
                    Toast.makeText(context, meDetail.getMessage() + "", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelExtra> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
