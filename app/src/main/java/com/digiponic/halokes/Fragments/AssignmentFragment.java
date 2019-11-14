package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListAssignmentSubject;
import com.digiponic.halokes.Models.ListAssignmentTask;
import com.digiponic.halokes.Models.ModelAssignmentSubject;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    LinearLayout llAssignmentContainer;
ProgressBar pbLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        llAssignmentContainer = view.findViewById(R.id.llAssignmentContainer);
        pbLoading = view.findViewById(R.id.pbLoading);
        showAssignment();

        return view;
    }

    private void showAssignment() {


        Call<ModelAssignmentSubject> call = RetrofitClient.getInstance()
                .getApi()
                .showAssignment(session.getUser().getId_user());
        call.enqueue(new Callback<ModelAssignmentSubject>() {
            //init
            ModelAssignmentSubject las;
            @Override
            public void onResponse(Call<ModelAssignmentSubject> call, Response<ModelAssignmentSubject> response) {
                //removing loading spinning bar
                llAssignmentContainer.removeAllViews();

                las = response.body();
                if (response.isSuccessful() && isAdded()) {
                    for (ListAssignmentSubject lasData : las.getData()) {// mengambil data mapel
//                        Toast.makeText(context, lasData.getNama_mapel()+"", Toast.LENGTH_SHORT).show();
                        LinearLayout rowSubject = (LinearLayout) getLayoutInflater()
                                .inflate(R.layout.template_assignment_subject, null);
                        TextView tvAssignmentSubject, tvAssignmentCountBadge;
                        final HorizontalScrollView hsvAssignmentSubjectContainer;
                        LinearLayout llAssignmentSubjectContainer;

                        tvAssignmentSubject = rowSubject.findViewById(R.id.tvAssignmentSubject);
                        tvAssignmentCountBadge = rowSubject.findViewById(R.id.tvAssignmentCountBadge);
                        hsvAssignmentSubjectContainer = rowSubject.findViewById(R.id.hsvAssignmentSubjectContainer);
                        llAssignmentSubjectContainer = rowSubject.findViewById(R.id.llAssignmentSubjectContainer);
                        llAssignmentSubjectContainer.removeAllViews();

                        tvAssignmentSubject.setText(lasData.getNama_mapel());
                        tvAssignmentCountBadge.setText(lasData.getJumlah_tugas_mapel());
                        tvAssignmentCountBadge.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hsvAssignmentSubjectContainer.setSmoothScrollingEnabled(true);
                                hsvAssignmentSubjectContainer.fullScroll(View.FOCUS_RIGHT);
                            }
                        });



                        for (ListAssignmentTask latData : lasData.getData_tugas()) {
                            LinearLayout rowTask = (LinearLayout) getLayoutInflater()
                                    .inflate(R.layout.template_assignment_task, null);
                            TextView tvAssignmentTitle = rowTask.findViewById(R.id.tvAssignmentTitle);
                            TextView tvAssignmentDeadline = rowTask.findViewById(R.id.tvAssignmentDeadline);
                            TextView tvAssignmentTimeLeft = rowTask.findViewById(R.id.tvAssignmentTimeLeft);
                            TextView tvAssignmentDesc = rowTask.findViewById(R.id.tvAssignmentDesc);
                            //Date Formatter
                            SimpleDateFormat dateFormat =
                                    new SimpleDateFormat("dd-MMM-yyy", Locale.ENGLISH);

                            try {
                                Date dateDeadline = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(latData.getDeadline());
                                tvAssignmentDeadline.setText(dateFormat.format(dateDeadline) + "");
                                tvAssignmentTimeLeft.setText("");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            tvAssignmentTitle.setText(latData.getJudul_tugas());
                            tvAssignmentDesc.setText(latData.getDeskripsi());

                            int hoursLeft = latData.getSisa_waktu();
                            int daysLeft = Math.round(latData.getSisa_waktu() / 24);
                            String timeLeftMessage = "";
                            if (hoursLeft < 0) {
                                if (hoursLeft < -24) {
                                    timeLeftMessage = "Lewat " + daysLeft * (-1) + " hari ";
                                } else {
                                    timeLeftMessage = "Lewat " + hoursLeft * (-1) + " jam";
                                }
                            } else if (hoursLeft < 24) {
                                if (hoursLeft<1){
                                    timeLeftMessage =  ">1 jam lagi";
                                }else{
                                    timeLeftMessage = hoursLeft + " jam lagi";
                                }
                            } else {
                                timeLeftMessage = daysLeft + " hari lagi";
                            }
                            tvAssignmentTimeLeft.setText(timeLeftMessage);
                            if (daysLeft<0){
                                tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_danger));
                            }else if (daysLeft<=3){
                                tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_warning));
                            }else{
                                tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_success));
                            }
                            llAssignmentSubjectContainer.addView(rowTask);
                        }
                        llAssignmentContainer.addView(rowSubject);
                    }

                } else {
                    Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelAssignmentSubject> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
