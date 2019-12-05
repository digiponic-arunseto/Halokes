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
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListStudent;
import com.digiponic.halokes.Models.ListStudentIdentity;
import com.digiponic.halokes.Models.ListStudentParent;
import com.digiponic.halokes.Models.ModelStudent;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountBioFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    ProgressBar skvLoading;
    LinearLayout llAccountBioContainer;
    List<String> labelBio;
    private ModelStudent ms;

    public void setMs(ModelStudent ms) {
        this.ms = ms;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_bio, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        skvLoading = view.findViewById(R.id.skvLoading);
        llAccountBioContainer = view.findViewById(R.id.llAccountBioContainer);


        labelBio = new ArrayList<>();
        labelBio.add("Nama");
        labelBio.add("Nisn");
        labelBio.add("Tempat Lahir");
        labelBio.add("Tanggal Lahir");
        labelBio.add("Jenis Kelamin");
        labelBio.add("Alamat");
        labelBio.add("Nomer HP");
        labelBio.add("Email");
        labelBio.add("Agama");
        labelBio.add("Asal Sekolah");
        labelBio.add("Nama Ayah");
        labelBio.add("Nomer HP");
        labelBio.add("Nama Ibu");
        labelBio.add("Nomer HP");
        labelBio.add("Nama Wali");
        labelBio.add("Nomer HP");

        showBio();
        return view;
    }

    public void showBio() {

        skvLoading.setVisibility(View.VISIBLE);
        llAccountBioContainer.removeAllViews();
        Call<ModelStudent> call = RetrofitClient.getInstance().getApi()
                .showStudentBio(
                        session.getUser().getId_user()
                );


        call.enqueue(new Callback<ModelStudent>() {
            @Override
            public void onResponse(Call<ModelStudent> call, Response<ModelStudent> response) {
                ms = response.body();
                if (response.isSuccessful() && isAdded()) {
                    ListStudent lsData = ms.getData();
                    ListStudentIdentity lsiData = lsData.getData_diri();
                    ListStudentParent lspData = lsData.getData_ortu();
                    for (int i = 0; i < labelBio.size(); i++) {
                        View rowBio = getLayoutInflater().inflate(R.layout.template_account_bio, null);
                        LinearLayout llBioRow = rowBio.findViewById(R.id.llBioRow);
                        TextView tvBioLabel = rowBio.findViewById(R.id.tvBioLabel);
                        TextView tvBioValue = rowBio.findViewById(R.id.tvBioValue);
                        View vBioMargin = rowBio.findViewById(R.id.vBioMargin);

                        tvBioLabel.setText(labelBio.get(i) + "");
                        switch (i) {
                            case 0:
                                tvBioValue.setText(lsData.getNama());
                                break;
                            case 1:
                                tvBioValue.setText(lsiData.getNisn());
                                break;
                            case 2:
                                tvBioValue.setText(lsiData.getTempat_lahir());
                                break;
                            case 3:
                                tvBioValue.setText(lsiData.getTgl_lahir());
                                break;
                            case 4:
                                tvBioValue.setText(lsiData.getJkel());
                                break;
                            case 5:
                                tvBioValue.setText(lsiData.getAlamat());
                                break;
                            case 6:
                                tvBioValue.setText(lsiData.getNo_hp());
                                break;
                            case 7:
                                tvBioValue.setText(lsiData.getEmail());
                                break;
                            case 8:
                                tvBioValue.setText(lsiData.getAgama());
                                break;
                            case 9:
                                tvBioValue.setText(lsiData.getAsal_sekolah());
                                break;
                            case 10:
                                tvBioValue.setText(lspData.getNama_ayah());
                                break;
                            case 11:
                                tvBioValue.setText(lspData.getNohp_ayah());
                                break;
                            case 12:
                                tvBioValue.setText(lspData.getNama_ibu());
                                break;
                            case 13:
                                tvBioValue.setText(lspData.getNohp_ibu());
                                break;
                            case 14:
                                tvBioValue.setText(lspData.getNama_wali());
                                break;
                            case 15:
                                tvBioValue.setText(lspData.getNohp_wali());
                                break;
                        }

                        if (i == 9) {
                            vBioMargin.setVisibility(View.VISIBLE);
                        } else {
                            llBioRow.setBackground(getResources().getDrawable(R.drawable.bg_textlines));
                        }

                        llAccountBioContainer.addView(rowBio);
                    }
                    skvLoading.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ModelStudent> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();


            }
        });

    }

}
