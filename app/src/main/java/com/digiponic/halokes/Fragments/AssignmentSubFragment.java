package com.digiponic.halokes.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListAssignment;
import com.digiponic.halokes.Models.ListAssignmentDetail;
import com.digiponic.halokes.Models.ModelAssignment;
import com.digiponic.halokes.Models.StructureDefault;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentSubFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    LinearLayout llAssignmentSubjectContainer;
    TextView tvAssignmentSubject, tvAssignmentCount;
    SpinKitView skvLoading;
    private ListAssignment lData;
    private ModelAssignment madData;
    private List<String> lParams;

    public void setlParams(List<String> lParams) {
        this.lParams = lParams;
    }

    public void setlData(ListAssignment lData) {
        this.lData = lData;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.template_assignment, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        llAssignmentSubjectContainer = view.findViewById(R.id.llAssignmentContainer);
//        tvAssignmentSubject = view.findViewById(R.id.tvAssignmentSubject);
//        tvAssignmentCount = view.findViewById(R.id.tvAssignmentCount);
        skvLoading = view.findViewById(R.id.skvLoading);
//        Toast.makeText(context, lParams.get(0)+"", Toast.LENGTH_SHORT).show();
        setRetainInstance(true);
        showAssignment(
                session.getUser().getId_user() + "",
                lData.getId_mapel() + "",
                lParams.get(0) + "",
                lParams.get(1) + "",
                lParams.get(2) + "");

        return view;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser && isAdded()) {
//            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//        }
//    }


    public void showAssignment(String id_user, String id_mapel, String keyword, String sort_date, String sort_nama_tugas) {
        try {
            skvLoading.setVisibility(View.VISIBLE);
            llAssignmentSubjectContainer.removeAllViews();
            // ganti ke ModelAssignment
            Call<ModelAssignment> call = RetrofitClient.getInstance()
                    .getApi()
                    .showAssignmentDetail(id_user, id_mapel, keyword, sort_date, sort_nama_tugas);

            call.enqueue(new Callback<ModelAssignment>() {
                @Override
                public void onResponse(Call<ModelAssignment> call, Response<ModelAssignment> response) {
                    if (isAdded() && response.isSuccessful()) {
                        madData = response.body();
                        for (ListAssignment laData : madData.getData()) {
                            for (final ListAssignmentDetail lasData : laData.getData_tugas()) {
                                View vAssignment = getLayoutInflater()
                                        .inflate(R.layout.template_assignment_detail, null);
                                //initializing elemen dari view yang di loop
                                TextView tvAssignmentTitle = vAssignment.findViewById(R.id.tvAssignmentTitle);
                                TextView tvAssignmentDeadline = vAssignment.findViewById(R.id.tvAssignmentDeadline);
                                final TextView tvAssignmentTimeLeft = vAssignment.findViewById(R.id.tvAssignmentTimeLeft);
                                TextView tvAssignmentDesc = vAssignment.findViewById(R.id.tvAssignmentDesc);
                                final TextView tvAssignmentStatus = vAssignment.findViewById(R.id.tvAssignmentStatus);
                                final TextView tvAssignmentStatusIndicator = vAssignment.findViewById(R.id.tvAssignmentStatusIndicator);
                                //Date Formatter
                                SimpleDateFormat dateFormat =
                                        new SimpleDateFormat("dd-MMM-yyy", Locale.ENGLISH);

//                            try {
//                                Date dateDeadline = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(lasData.getDeadline());
//                                tvAssignmentDeadline.setText("Batas Pengumpulan : " + dateFormat.format(dateDeadline) + "");
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
                                tvAssignmentDeadline.setText(lasData.getDeadline() + "");

                                //printing data
                                tvAssignmentTitle.setText(lasData.getJudul_tugas());
                                tvAssignmentDesc.setText(lasData.getDeskripsi());
                                int status = lasData.getStatus();
                                assignmentDetailTimeIndicator(tvAssignmentTimeLeft, tvAssignmentStatusIndicator, lasData.getSisa_waktu(), status);

                                assignmentDetailStatus(tvAssignmentStatus, status, null, null);
                                //click assignment
                                if (status != 2) {
                                    vAssignment.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            assignmentDetailOnClick(
                                                    tvAssignmentStatus,
                                                    tvAssignmentTimeLeft,
                                                    tvAssignmentStatusIndicator,
                                                    lasData.getId_tugas(),
                                                    lasData.getSisa_waktu());
                                        }
                                    });
                                }

                                llAssignmentSubjectContainer.addView(vAssignment);
                            }
                        }

                    }
                    skvLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ModelAssignment> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }
    }

    public void assignmentDetailTimeIndicator(
            TextView tvAssignmentTimeLeft,
            TextView tvAssignmentStatusIndicator,
            int timeLeft,
            int status) {

        int hoursLeft = timeLeft;
        int daysLeft = Math.round(timeLeft / 24);
        int bg;
        int bgIndicator;
        int tc;
        String timeLeftMessage;

        if (hoursLeft < 0) {
            if (hoursLeft < -24) {
                timeLeftMessage = "Lewat " + daysLeft * (-1) + " hari ";
            } else {
                timeLeftMessage = "Lewat " + hoursLeft * (-1) + " jam";
            }
        } else if (hoursLeft < 24) {
            if (hoursLeft < 1) {
                timeLeftMessage = ">1 jam lagi";
            } else {
                timeLeftMessage = hoursLeft + " jam lagi";
            }
        } else {
            timeLeftMessage = daysLeft + " hari lagi";
        }

        if (status == 0) {
            bgIndicator = R.drawable.bg_round_corner_warning;
            bg = R.drawable.bg_round_corner_border_warning;
            tc = R.color.colorWarning;
        } else if (status == 1) {
            bgIndicator = R.drawable.bg_round_corner;
            timeLeftMessage = "Proses...";
            bg = R.drawable.bg_round_corner_border_primary;
            tc = R.color.colorPrimary;
        } else if (status == 2) {
            bgIndicator = R.drawable.bg_round_corner_success;
            timeLeftMessage = "Selesai";
            bg = R.drawable.bg_round_corner_border_success;
            tc = R.color.colorSuccess;
        } else {
            bgIndicator = R.drawable.bg_round_corner_danger;
            bg = R.drawable.bg_round_corner_border_danger;
            tc = R.color.colorDanger;
        }


        tvAssignmentStatusIndicator.setBackground(getResources().getDrawable(bgIndicator));
        tvAssignmentTimeLeft.setText(timeLeftMessage);
        tvAssignmentTimeLeft.setTextColor(getResources().getColor(tc));
        tvAssignmentTimeLeft.setBackground(getResources().getDrawable(bg));
    }

    public void assignmentDetailStatus(
            final TextView tvAssignmentStatus,
            int status,
            @Nullable String id_tugas,
            @Nullable final AlertDialog dialog) {
        if (id_tugas != null && dialog != null) {
            Call<StructureDefault> call = RetrofitClient.getInstance().getApi()
                    .actAssignmentSubmit(
                            session.getUser().getId_user(),
                            id_tugas,
                            status
                    );
            call.enqueue(new Callback<StructureDefault>() {
                @Override
                public void onResponse(Call<StructureDefault> call, Response<StructureDefault> response) {
                    if (isAdded() && response.isSuccessful()) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<StructureDefault> call, Throwable t) {
                    Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            });
        }

        String desc;
        int icon;
        int textColor = 0;
        if (status == 0) {
            desc = "Belum Mengerjakan";
            icon = 0;
            textColor = getResources().getColor(R.color.colorWarning);
        } else if (status == 1) {
            desc = "Sedang Dinilai...";
            icon = R.drawable.ic_waiting_24dp;
            textColor = getResources().getColor(R.color.colorPrimary);
        } else if (status == 2) {
            desc = "Sudah Dikerjakan";
            icon = R.drawable.ic_check_success_24dp;
            textColor = getResources().getColor(R.color.colorSuccess);
        } else {
            desc = "Kadaluwarsa";
            icon = R.drawable.ic_check_success_24dp;
            textColor = getResources().getColor(R.color.colorSuccess);
        }
        tvAssignmentStatus.setText(desc);
        tvAssignmentStatus.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                icon,
                0);
        tvAssignmentStatus.setTextColor(textColor);
    }

    public void assignmentDetailOnClick(
            final TextView tvAssignmentStatus,
            final TextView tvAssignmentTimeLeft,
            final TextView tvAssignmentStatusIndicator,
            final String id_tugas,
            final int sisaWaktu) {

        //Building Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layoutLogout = getLayoutInflater().inflate(R.layout.layout_dialog_confirmation, null);
        Button btnYes = layoutLogout.findViewById(R.id.btnYes);
        Button btnNo = layoutLogout.findViewById(R.id.btnNo);
        TextView tvTitle = layoutLogout.findViewById(R.id.tvTitle);
        builder.setView(layoutLogout);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();// to resize alert dialogMoreMenu, this command line should be below the alert.show()
        dialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);

        tvTitle.setText("Apakah tugas anda sudah selesai?");
        btnYes.setText("SUDAH");
        btnNo.setText("BELUM");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assignmentDetailStatus(tvAssignmentStatus, 1, id_tugas, dialog);
                assignmentDetailTimeIndicator(tvAssignmentTimeLeft, tvAssignmentStatusIndicator, sisaWaktu, 1);

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assignmentDetailTimeIndicator(tvAssignmentTimeLeft, tvAssignmentStatusIndicator, sisaWaktu, 0);
                assignmentDetailStatus(tvAssignmentStatus, 0, id_tugas, dialog);
            }
        });
    }

}
