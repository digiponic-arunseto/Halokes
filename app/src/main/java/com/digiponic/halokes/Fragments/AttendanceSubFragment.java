package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digiponic.halokes.R;

public class AttendanceSubFragment extends BottomSheetDialogFragment {
    View view;
    int flContent;
    Context context;
    String name;
    String[] date;

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.template_attendance_detail, container, false);
        TextView tvAttendanceDetailName = view.findViewById(R.id.tvAttendanceDetailName);
        TextView tvAttendanceDetailDate = view.findViewById(R.id.tvAttendanceDetailDate);

        tvAttendanceDetailName.setText(name);
        tvAttendanceDetailDate.setText("");
        for (String d : date) {
            tvAttendanceDetailDate.append("- " + d + "\n");
        }

        return view;
    }
}
