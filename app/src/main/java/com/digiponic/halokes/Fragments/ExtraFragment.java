package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.digiponic.halokes.Models.ListExtra;
import com.digiponic.halokes.Models.ListStudent;
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
    TextView tvExtraStudentName,tvExtraStudentInfo;
    LinearLayout llExtraContainer;
    SpinKitView skvLoading, skvLoading1;


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
        llExtraContainer = view.findViewById(R.id.llExtraContainer);
        skvLoading = view.findViewById(R.id.skvLoading);
        skvLoading1 = view.findViewById(R.id.skvLoading1);


        showExtra();

        return view;
    }

    public void showExtra() {

        //hiding content first, to show the loading view
        svContent.setVisibility(View.GONE);
        skvLoading.setVisibility(View.VISIBLE);
        llExtraContainer.removeAllViews();

        Call<ModelExtra> call = RetrofitClient.getInstance().getApi()
                .showExtra(session.getUser().getId_user());
        call.enqueue(new Callback<ModelExtra>() {
            @Override
            public void onResponse(Call<ModelExtra> call, Response<ModelExtra> response) {
                ModelExtra me = response.body();
                if (isAdded() && response.isSuccessful()) {

                    tvExtraStudentName.setText(session.getUser().getNama_siswa());
                    tvExtraStudentInfo.setText("Kelas 7A");

                    int counter = 1;
                    skvLoading1.setVisibility(View.VISIBLE);
                    for (ListExtra leData : me.getData()) {
                        View rowStudent = getLayoutInflater().inflate(R.layout.template_extra, null);
                        TextView tvExtraName = rowStudent.findViewById(R.id.tvExtraName);
                        TextView tvExtraSchedule = rowStudent.findViewById(R.id.tvExtraSchedule);


                        tvExtraName.setText(leData.getNama());
                        tvExtraSchedule.setText(leData.getJadwal());

                        if (counter % 2 == 0) {
                            rowStudent.setBackgroundColor(getResources().getColor(R.color.colorGrayLight));
                        }
                        counter++;
                        llExtraContainer.addView(rowStudent);
                    }
                    svContent.setVisibility(View.VISIBLE);
                    skvLoading.setVisibility(View.GONE);
                    skvLoading1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ModelExtra> call, Throwable t) {

            }
        });

    }
}
