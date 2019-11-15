package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ModelAttendance;
import com.digiponic.halokes.Models.ModelUser;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    TextView tvH, tvS, tvI, tvA, tvPercentage, tvPercentageDesc;
    ProgressBar pbLoading, pbPercentage;
    ScrollView svContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_attendance, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        pbLoading = view.findViewById(R.id.pbLoading);
        svContent = view.findViewById(R.id.svContent);

        tvH = view.findViewById(R.id.tvH);
        tvS = view.findViewById(R.id.tvS);
        tvI = view.findViewById(R.id.tvI);
        tvA = view.findViewById(R.id.tvA);
        tvPercentage = view.findViewById(R.id.tvPercentage);
        pbPercentage = view.findViewById(R.id.pbPercentage);
        tvPercentageDesc = view.findViewById(R.id.tvPercentageDesc);


        //hiding Content first, to show loading bar
        svContent.setVisibility(View.GONE);

        showAttendanceData();
        return view;
    }

    public void showAttendanceData() {
        Call<ModelAttendance> call = RetrofitClient.getInstance().getApi().showAttendance(session.getUser().getId_user());

        call.enqueue(new Callback<ModelAttendance>() {
            @Override
            public void onResponse(Call<ModelAttendance> call, Response<ModelAttendance> response) {
                ModelAttendance res = response.body();
                if (response.isSuccessful() && isAdded()) {
//                    Toast.makeText(context, res.getMessage() + "", Toast.LENGTH_SHORT).show();

                    // ? is true
                    // : is false
                    tvH.setText(
                            (res.getData().getHadir().equals("0")
                                    ? "-"
                                    : res.getData().getHadir() + " Hari"));
                    tvS.setText(
                            (res.getData().getSakit().equals("0")
                                    ? "-"
                                    : res.getData().getSakit() + " Hari"));
                    tvI.setText(
                            (res.getData().getIzin().equals("0")
                                    ? "-"
                                    : res.getData().getIzin() + " Hari"));
                    tvA.setText(
                            (res.getData().getAlpha().equals("0")
                                    ? "-"
                                    : res.getData().getAlpha() + " Hari"));

                    float persenHadir = Float.parseFloat(res.getData().getPersen_hadir());
                    String persenHadirDesc;
                    int persenHadirColor;

                    if (persenHadir < 50) {
                        persenHadirDesc = "Kurang";
                        persenHadirColor = getResources().getColor(R.color.colorDanger);
                    } else if (persenHadir < 75) {
                        persenHadirDesc = "Cukup";
                        persenHadirColor = getResources().getColor(R.color.colorWarning);
                    } else {
                        persenHadirDesc = "Baik";
                        persenHadirColor = getResources().getColor(R.color.colorSuccess);
                    }

                    tvPercentage.setText(persenHadir + " %");
                    tvPercentage.setTextColor(persenHadirColor);
                    tvPercentageDesc.setTextColor(persenHadirColor);
                    tvPercentageDesc.setText(persenHadirDesc);
                    pbPercentage.setProgress(Math.round(persenHadir));
                    pbPercentage.setProgressTintList(ColorStateList
                            .valueOf(persenHadirColor));

                    //showing Content while hiding loading bar
                    svContent.setVisibility(View.VISIBLE);
                    pbLoading.setVisibility(View.GONE);
                } else {
                    Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelAttendance> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
