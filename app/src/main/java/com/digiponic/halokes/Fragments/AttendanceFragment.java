package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    TextView tvH, tvS, tvI, tvA, tvPercentage, tvPercentageDesc, tvAttendanceSDate, tvAttendanceIDate, tvAttendanceADate, tvAttendanceStudentName, tvAttendanceStudentInfo;
    SpinKitView skvLoading;
    ProgressBar pbPercentage;
    ScrollView svContent;
    RelativeLayout rlAttendanceS, rlAttendanceI, rlAttendanceA;
    LinearLayout llAttendanceSDate, llAttendanceIDate, llAttendanceADate;

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


        tvH = view.findViewById(R.id.tvAttendanceH);
        tvS = view.findViewById(R.id.tvAttendanceS);
        tvI = view.findViewById(R.id.tvAttendanceI);
        tvA = view.findViewById(R.id.tvAttendanceA);
        tvPercentage = view.findViewById(R.id.tvAttendancePercentage);
        pbPercentage = view.findViewById(R.id.pbAttendancePercentage);
        tvPercentageDesc = view.findViewById(R.id.tvAttendancePercentageDesc);

        rlAttendanceS = view.findViewById(R.id.rlAttendanceS);
        rlAttendanceI = view.findViewById(R.id.rlAttendanceI);
        rlAttendanceA = view.findViewById(R.id.rlAttendanceA);

        llAttendanceSDate = view.findViewById(R.id.llAttendanceSDate);
        llAttendanceIDate = view.findViewById(R.id.llAttendanceIDate);
        llAttendanceADate = view.findViewById(R.id.llAttendanceADate);

        tvAttendanceSDate = view.findViewById(R.id.tvAttendanceSDate);
        tvAttendanceIDate = view.findViewById(R.id.tvAttendanceIDate);
        tvAttendanceADate = view.findViewById(R.id.tvAttendanceADate);

        configExpandCollapse();
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
                    // ? is true
                    // : is false
                    tvH.setText(
                            (laData.getHadir().equals("0")
                                    ? "-"
                                    : laData.getHadir() + " Hari"));
                    tvS.setText(
                            (laData.getSakit().equals("0")
                                    ? "-"
                                    : laData.getSakit() + " Hari"));
                    tvI.setText(
                            (laData.getIzin().equals("0")
                                    ? "-"
                                    : laData.getIzin() + " Hari"));
                    tvA.setText(
                            (laData.getAlpha().equals("0")
                                    ? "-"
                                    : laData.getAlpha() + " Hari"));

                    float persenHadir = Float.parseFloat(laData.getPersen_hadir());
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

                    if (laData.getTgl_sakit().length != 0) {
                        for (String date : laData.getTgl_sakit()) {
                            tvAttendanceSDate.append(date + "\n");
                        }
                    } else {
                        tvAttendanceSDate.setVisibility(View.GONE);
                    }

                    if (laData.getTgl_izin().length != 0) {
                        for (String date : laData.getTgl_izin()) {
                            tvAttendanceIDate.append(date + "\n");
                        }
                    } else {
                        tvAttendanceIDate.setVisibility(View.GONE);
                    }
                    if (laData.getTgl_alpha().length != 0) {
                        for (String date : laData.getTgl_alpha()) {
                            tvAttendanceADate.append(date + "\n");
                        }
                    } else {
                        tvAttendanceADate.setVisibility(View.GONE);
                    }

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

    public void configExpandCollapse() {

        tvAttendanceSDate.setText("");
        tvAttendanceIDate.setText("");
        tvAttendanceADate.setText("");

        llAttendanceSDate.setVisibility(View.GONE);
        llAttendanceIDate.setVisibility(View.GONE);
        llAttendanceADate.setVisibility(View.GONE);


        rlAttendanceS.setOnClickListener(new View.OnClickListener() {
            boolean hide = false;

            @Override
            public void onClick(View view) {
                if (hide) {
                    llAttendanceSDate.setVisibility(View.GONE);
                    hide = false;
                } else {
                    llAttendanceSDate.setVisibility(View.VISIBLE);
                    hide = true;
                }
                configIconExpandCollapse(tvS, hide);

            }
        });


        rlAttendanceI.setOnClickListener(new View.OnClickListener() {
            boolean hide = false;

            @Override
            public void onClick(View view) {
                if (hide) {
                    llAttendanceIDate.setVisibility(View.GONE);
                    hide = false;
                } else {
                    llAttendanceIDate.setVisibility(View.VISIBLE);
                    hide = true;
                }
                configIconExpandCollapse(tvI, hide);

            }
        });

        rlAttendanceA.setOnClickListener(new View.OnClickListener() {
            boolean hide = false;

            @Override
            public void onClick(View view) {
                if (hide) {
                    llAttendanceADate.setVisibility(View.GONE);
                    hide = false;
                } else {
                    llAttendanceADate.setVisibility(View.VISIBLE);
                    hide = true;
                }
                configIconExpandCollapse(tvA, hide);
            }
        });
    }

    public void configIconExpandCollapse(TextView tv, boolean hide) {
        if (hide) {
            tv.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_collapse_24dp,
                    0);
        } else {
            tv.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_expand_24dp,
                    0);
        }
    }
}
