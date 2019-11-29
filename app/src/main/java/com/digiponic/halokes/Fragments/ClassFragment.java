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
import android.widget.Toast;

import com.digiponic.halokes.Models.ListClass;
import com.digiponic.halokes.Models.ListStudent;
import com.digiponic.halokes.Models.ModelClass;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;

    ScrollView svContent;
    TextView tvClassStudentName,tvClassStudentInfo, tvClassTeacher;
    LinearLayout llClassStudentContainer;
    SpinKitView skvLoading, skvLoading1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_class, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        svContent = view.findViewById(R.id.svContent);
        tvClassStudentName = view.findViewById(R.id.tvClassStudentName);
        tvClassStudentInfo = view.findViewById(R.id.tvClassStudentInfo);
        tvClassTeacher = view.findViewById(R.id.tvClassTeacher);
        llClassStudentContainer = view.findViewById(R.id.llClassStudentContainer);
        skvLoading = view.findViewById(R.id.skvLoading);
        skvLoading1 = view.findViewById(R.id.skvLoading1);


        showClass();

        return view;
    }

    public void showClass() {

        //hiding content first, to show the loading view
        svContent.setVisibility(View.GONE);
        skvLoading.setVisibility(View.VISIBLE);
        llClassStudentContainer.removeAllViews();

        Call<ModelClass> call = RetrofitClient.getInstance().getApi()
                .showClass(session.getUser().getId_user());
        call.enqueue(new Callback<ModelClass>() {
            @Override
            public void onResponse(Call<ModelClass> call, Response<ModelClass> response) {
                ModelClass mc = response.body();
                if (isAdded() && response.isSuccessful()) {
                    ListClass lcData = mc.getData();
                    tvClassStudentName.setText(lcData.getNama());
                    tvClassStudentInfo.setText("NIS : "+ lcData.getNis() + " / Kelas "+lcData.getKelas());
                    tvClassTeacher.setText(lcData.getWali_kelas());

                    int counter = 1;
                    skvLoading1.setVisibility(View.VISIBLE);
                    for (ListStudent lsData : lcData.getData_siswa()) {
                        View rowStudent = getLayoutInflater().inflate(R.layout.template_class_student, null);
                        TextView tvStudentCounter = rowStudent.findViewById(R.id.tvStudentCounter);
                        TextView tvStudentID = rowStudent.findViewById(R.id.tvStudentID);
                        TextView tvStudentName = rowStudent.findViewById(R.id.tvStudentName);

                        tvStudentCounter.setText(counter+"");
                        tvStudentID.setText(lsData.getNis());
                        tvStudentName.setText(lsData.getNama());

                        if (counter % 2 == 0) {
                            rowStudent.setBackgroundColor(getResources().getColor(R.color.colorGrayLight));
                        }
                        counter++;
                        llClassStudentContainer.addView(rowStudent);
                    }
                    svContent.setVisibility(View.VISIBLE);
                    skvLoading.setVisibility(View.GONE);
                    skvLoading1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ModelClass> call, Throwable t) {

            }
        });

    }
}
