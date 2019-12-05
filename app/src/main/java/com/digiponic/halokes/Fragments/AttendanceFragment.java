package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.content.res.ColorStateList;
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

import com.digiponic.halokes.Models.ListAttendance;
import com.digiponic.halokes.Models.ModelAttendance;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    TextView tvPercentage, tvPercentageDesc, tvAttendanceStudentName, tvAttendanceStudentInfo;
    //day count
    TextView tvAttendanceH, tvAttendanceS, tvAttendanceI, tvAttendanceA;
    //day label
    TextView tvAttendanceHDay, tvAttendanceSDay, tvAttendanceIDay, tvAttendanceADay;
    SpinKitView skvLoading;
    ProgressBar pbPercentage;
    ScrollView svContent;
    // TextView  tvAttendanceSDate, tvAttendanceIDate, tvAttendanceADate;
//    RelativeLayout rlAttendanceS, rlAttendanceI, rlAttendanceA;
    // cardview attendance
    LinearLayout llAttendanceH, llAttendanceS, llAttendanceI, llAttendanceA;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_attendance, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        svContent = view.findViewById(R.id.svContent);
        skvLoading = view.findViewById(R.id.skvLoading);

        tvAttendanceStudentName = view.findViewById(R.id.tvAttendanceStudentName);
        tvAttendanceStudentInfo = view.findViewById(R.id.tvAttendanceStudentInfo);

        tvPercentage = view.findViewById(R.id.tvAttendancePercentage);
        pbPercentage = view.findViewById(R.id.pbAttendancePercentage);
        tvPercentageDesc = view.findViewById(R.id.tvAttendancePercentageDesc);

        tvAttendanceHDay = view.findViewById(R.id.tvAttendanceHDay);
        tvAttendanceSDay = view.findViewById(R.id.tvAttendanceSDay);
        tvAttendanceIDay = view.findViewById(R.id.tvAttendanceIDay);
        tvAttendanceADay = view.findViewById(R.id.tvAttendanceADay);

        tvAttendanceH = view.findViewById(R.id.tvAttendanceH);
        tvAttendanceS = view.findViewById(R.id.tvAttendanceS);
        tvAttendanceI = view.findViewById(R.id.tvAttendanceI);
        tvAttendanceA = view.findViewById(R.id.tvAttendanceA);

        llAttendanceH = view.findViewById(R.id.llAttendanceH);
        llAttendanceS = view.findViewById(R.id.llAttendanceS);
        llAttendanceI = view.findViewById(R.id.llAttendanceI);
        llAttendanceA = view.findViewById(R.id.llAttendanceA);


//        configExpandCollapse();
        showAttendanceData();

        return view;
    }

    public void showAttendanceData() {
        //hiding Content first, to show loading bar
        svContent.setVisibility(View.GONE);

        Call<ModelAttendance> call = RetrofitClient.getInstance().getApi().showAttendance(session.getUser().getId_user());

        call.enqueue(new Callback<ModelAttendance>() {
            @Override
            public void onResponse(Call<ModelAttendance> call, Response<ModelAttendance> response) {
                ModelAttendance ma = response.body();
                if (response.isSuccessful() && isAdded()) {

                    tvAttendanceStudentName.setText(session.getUser().getNama_siswa());
                    tvAttendanceStudentInfo.setText("Kelas 7A");

//                    Toast.makeText(context, res.getMessage() + "", Toast.LENGTH_SHORT).show();
                    ListAttendance laData = ma.getData();

                    float persenHadir = Float.parseFloat(laData.getPersen_hadir());
                    String persenHadirDesc;
                    int persenHadirColor;
                    int progressBg;

                    if (persenHadir < 50) {
                        persenHadirDesc = "Kurang";
                        persenHadirColor = getResources().getColor(R.color.colorDanger);
                        progressBg = getResources().getColor(R.color.colorDangerLighter);
                    } else if (persenHadir < 75) {
                        persenHadirDesc = "Cukup";
                        persenHadirColor = getResources().getColor(R.color.colorWarning);
                        progressBg = getResources().getColor(R.color.colorWarningLighter);
                    } else {
                        persenHadirDesc = "Baik";
                        persenHadirColor = getResources().getColor(R.color.colorPrimary);
                        progressBg = getResources().getColor(R.color.colorPrimaryLighter);
                    }

                    tvPercentage.setText(persenHadir + " %");
                    tvPercentage.setTextColor(persenHadirColor);
                    tvPercentageDesc.setTextColor(persenHadirColor);
                    tvPercentageDesc.setText(persenHadirDesc);
                    pbPercentage.setProgress(Math.round(persenHadir));
                    pbPercentage.setProgressTintList(ColorStateList
                            .valueOf(persenHadirColor));
                    pbPercentage.setProgressBackgroundTintList(ColorStateList
                            .valueOf(progressBg));

                    calculateAttendance(tvAttendanceH, tvAttendanceHDay, laData.getHadir());
                    calculateAttendance(tvAttendanceS, tvAttendanceSDay, laData.getSakit());
                    calculateAttendance(tvAttendanceI, tvAttendanceIDay, laData.getIzin());
                    calculateAttendance(tvAttendanceA, tvAttendanceADay, laData.getAlpha());

                    clickAttendanceDetail(llAttendanceS, "Detail Sakit Siswa", laData.getTgl_sakit());
                    clickAttendanceDetail(llAttendanceI, "Detail Ijin Siswa", laData.getTgl_izin());
                    clickAttendanceDetail(llAttendanceA, "Detail Alpha Siswa", laData.getTgl_alpha());

                    //showing Content while hiding loading bar
                    svContent.setVisibility(View.VISIBLE);
                    skvLoading.setVisibility(View.GONE);
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

    public void calculateAttendance(TextView tvDay, TextView tvDayLabel, String day) {
        if (day.equals("0")) {
            tvDay.setText("-");
            tvDayLabel.setText("");
        } else {
            tvDay.setText(day);
            tvDayLabel.setText("Hari");
        }
    }

    public void clickAttendanceDetail(LinearLayout ll, String detailName, String[] detailDate) {
        if (detailDate.length == 0) {
            return;
        }
        final AttendanceSubFragment attendanceDetail = new AttendanceSubFragment();
        attendanceDetail.setName(detailName);
        attendanceDetail.setDate(detailDate);


        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendanceDetail.show(getFragmentManager(), "1");

            }
        });
    }
}
