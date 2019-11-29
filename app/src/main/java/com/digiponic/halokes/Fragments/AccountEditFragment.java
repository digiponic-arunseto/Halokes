package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.MainActivity;
import com.digiponic.halokes.Models.StructureDefault;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountEditFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    SpinKitView skvLoading;
    ScrollView svContent;
    EditText etPassword, etPasswordConfirm, etEmail;
    TextView tvUsername, tvAccountName;
    Button btnConfirm;
    String email, pw, pwC;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_edit, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        skvLoading = view.findViewById(R.id.skvLoading);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvAccountName = view.findViewById(R.id.tvAccountName);
        etPassword = view.findViewById(R.id.etPassword);
        etEmail = view.findViewById(R.id.etEmail);
        etPasswordConfirm = view.findViewById(R.id.etPasswordConfirm);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        configBtnConfirm();


        showProfileForm();
        return view;
    }

    public void showProfileForm() {
        skvLoading.setVisibility(View.GONE);
        tvUsername.setText(session.getUser().getUsername());
        tvAccountName.setText(session.getUser().getNama_siswa());


    }

    public void configBtnConfirm() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean go = true;
                email = etEmail.getText().toString();
                pw = etPassword.getText().toString();
                pwC = etPasswordConfirm.getText().toString();

                // email security
                if (email.isEmpty()) {
                    go = false;
                    etEmail.setError("Email tidak boleh kosong");
                    etEmail.requestFocus();
                }
                if (!email.contains("@")) {
                    go = false;
                    etEmail.setError("Penulisan Email salah");
                    etEmail.requestFocus();
                }

                //password and passowrd confirmation security
                if (pw.isEmpty()) {
                    go = false;
                    etPassword.setError("Password tidak boleh kosong");
                    etPassword.requestFocus();
                }
                if (pw.isEmpty()) {
                    go = false;
                    etPassword.setError("Password tidak boleh kosong");
                    etPassword.requestFocus();
                }
                if (pwC.isEmpty()) {
                    go = false;
                    etPasswordConfirm.setError("Password tidak boleh kosong");
                    etPasswordConfirm.requestFocus();
                }
                if (!pw.equals(pwC)) {
                    go = false;
                    etPasswordConfirm.setError("Password harus sama");
                    etPasswordConfirm.requestFocus();
                }
                if (go) {
                    Call<StructureDefault> call = RetrofitClient.getInstance().getApi()
                            .actInitialize(
                                    session.getUser().getId_user(),
                                    email,
                                    pwC);
                    call.enqueue(new Callback<StructureDefault>() {
                        @Override
                        public void onResponse(Call<StructureDefault> call, Response<StructureDefault> response) {
                            if (response.body().getStatus()) {
                                Toast.makeText(context, "Data telah diubah!", Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            } else {
                                Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<StructureDefault> call, Throwable t) {
                            Toast.makeText(context, t.getMessage()+"Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}
