package com.digiponic.halokes.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListAssignment;
import com.digiponic.halokes.Models.ListAssignmentDetail;
import com.digiponic.halokes.Models.ModelAssignment;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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


        try {
            showAssignment();

        } catch (Exception e) {
            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }
        return view;
    }


    public void showAssignment() {
        skvLoading.setVisibility(View.VISIBLE);
        llAssignmentSubjectContainer.removeAllViews();
        // ganti ke ModelAssignment
        Call<ModelAssignment> call = RetrofitClient.getInstance()
                .getApi()
                .showAssignmentDetail(session.getUser().getId_user(), lData.getId_mapel());

        call.enqueue(new Callback<ModelAssignment>() {
            @Override
            public void onResponse(Call<ModelAssignment> call, Response<ModelAssignment> response) {
                if (isAdded() && response.isSuccessful()) {
                    ModelAssignment madData = response.body();
                    for (ListAssignment laData : madData.getData()) {
                        for (ListAssignmentDetail lasData : laData.getData_tugas()) {
                            View vAssignment = getLayoutInflater()
                                    .inflate(R.layout.template_assignment_detail, null);
                            //initializing elemen dari view yang di loop
                            TextView tvAssignmentTitle = vAssignment.findViewById(R.id.tvAssignmentTitle);
                            TextView tvAssignmentDeadline = vAssignment.findViewById(R.id.tvAssignmentDeadline);
                            final TextView tvAssignmentTimeLeft = vAssignment.findViewById(R.id.tvAssignmentTimeLeft);
                            TextView tvAssignmentDesc = vAssignment.findViewById(R.id.tvAssignmentDesc);
                            //Date Formatter
                            SimpleDateFormat dateFormat =
                                    new SimpleDateFormat("dd-MMM-yyy", Locale.ENGLISH);

                            try {
                                Date dateDeadline = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(lasData.getDeadline());
                                tvAssignmentDeadline.setText(dateFormat.format(dateDeadline) + "");
                                tvAssignmentTimeLeft.setText("");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            //printing data
                            tvAssignmentTitle.setText(lasData.getJudul_tugas());
                            tvAssignmentDesc.setText(lasData.getDeskripsi());
                            AssignmentDetailTimeIndicator(tvAssignmentTimeLeft, lasData.getSisa_waktu(), false);

                            //click assignment
                            vAssignment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AssignmentDetailOnClick(tvAssignmentTimeLeft);
                                }
                            });

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
    }

    public void AssignmentDetailTimeIndicator(TextView tvAssignmentTimeLeft, int timeLeft, boolean done) {

        if (done) {
            tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_border_primary));
            tvAssignmentTimeLeft.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvAssignmentTimeLeft.setText("Dikonfirmasi...");
            return;
        }

        int hoursLeft = timeLeft;
        int daysLeft = Math.round(timeLeft / 24);
        String timeLeftMessage = "";
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
        tvAssignmentTimeLeft.setText(timeLeftMessage);
        if (daysLeft < 0) {
            tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_danger));
        } else if (daysLeft <= 3) {
            tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_warning));
        } else {
            tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner));
        }
    }

    public void AssignmentDetailOnClick(final TextView tvAssignmentTimeLeft) {

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
                AssignmentDetailTimeIndicator(tvAssignmentTimeLeft, 0, true);
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
