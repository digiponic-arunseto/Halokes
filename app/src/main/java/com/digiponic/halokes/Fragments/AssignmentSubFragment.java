package com.digiponic.halokes.Fragments;

import android.content.Context;
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

import com.digiponic.halokes.Models.ListAssignmentSubject;
import com.digiponic.halokes.Models.ListAssignmentTask;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Storage.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignmentSubFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    ProgressBar pbLoading;
    LinearLayout llAssignmentSubjectContainer;
    private ListAssignmentSubject lasData;
    private int tabCount;

    public void setLasData(ListAssignmentSubject lasData) {
        this.lasData = lasData;
    }

    public void setTabCount(int tabCount) {
        this.tabCount = tabCount;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.template_assignment_subject, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        llAssignmentSubjectContainer = view.findViewById(R.id.llAssignmentSubjectContainer);


        showAssignment();
        return view;
    }


    public void showAssignment() {
        llAssignmentSubjectContainer.removeAllViews();

        for (ListAssignmentTask latData : lasData.getData_tugas()) {
            LinearLayout rowTask = (LinearLayout) getLayoutInflater()
                    .inflate(R.layout.template_assignment_task, null);
            //initializing elemen dari view yang di loop
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

            //printing data
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
                tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_success));
            }
            llAssignmentSubjectContainer.addView(rowTask);
        }
    }

}
