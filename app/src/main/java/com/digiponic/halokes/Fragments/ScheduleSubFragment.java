package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListSchedule;
import com.digiponic.halokes.Models.ListScheduleDetail;
import com.digiponic.halokes.Models.ModelSchedule;
import com.digiponic.halokes.Models.ModelScheduleDetail;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleSubFragment extends Fragment {
    View view;
    int flContent;
    Context context;
    Session session;

    LinearLayout llScheduleContainer;

    SpinKitView skvLoading;

    private ListSchedule lData;

    public void setlData(ListSchedule lData) {
        this.lData = lData;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.template_schedule, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        skvLoading = view.findViewById(R.id.skvLoading);
        llScheduleContainer = view.findViewById(R.id.llScheduleContainer);


        try {
            showSchedule();
        } catch (Exception e) {
//            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void showSchedule() {
        skvLoading.setVisibility(View.VISIBLE);
        llScheduleContainer.removeAllViews();


        Call<ModelSchedule> call = RetrofitClient.getInstance().getApi()
                .showScheduleDetail(
                        session.getUser().getId_user(),
                        lData.getHari());
        call.enqueue(new Callback<ModelSchedule>() {
            @Override
            public void onResponse(Call<ModelSchedule> call, Response<ModelSchedule> response) {
                if (isAdded() && response.isSuccessful()) {
                    ModelSchedule ms = response.body();
                    for (ListSchedule lsData : ms.getData()) {
                        for (ListScheduleDetail lsdData : lsData.getData_jadwal()) {
                            View vSchedule = getLayoutInflater().inflate(R.layout.template_schedule_detail, null);
                            TextView tvScheduleHours = vSchedule.findViewById(R.id.tvScheduleHours);
                            TextView tvScheduleRoom = vSchedule.findViewById(R.id.tvScheduleRoom);
                            TextView tvScheduleSubject = vSchedule.findViewById(R.id.tvScheduleSubject);
                            TextView tvScheduleTeacher = vSchedule.findViewById(R.id.tvScheduleTeacher);

                            tvScheduleHours.setText(lsdData.getJam().replaceAll("-", "  -  "));
//                        tvScheduleRoom.setText();
                            tvScheduleSubject.setText(lsdData.getMapel());
                            tvScheduleTeacher.setText(lsdData.getGuru());
                            llScheduleContainer.addView(vSchedule);
                        }
                    }
                }
                skvLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelSchedule> call, Throwable t) {

            }
        });
    }
}
