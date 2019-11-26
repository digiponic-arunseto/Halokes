package com.digiponic.halokes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.digiponic.halokes.Models.StructureDefault;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitializeActivity extends AppCompatActivity {

    EditText etEmail, etPassword, etPasswordConfirm;
    Button btnNext, btnSkip, btnBack, btnConfirm;
    ViewFlipper vfPage;
    String email = "", pw = "", pwC = "";
    private Context context = InitializeActivity.this;
    private Session session = Session.getInstance(this);
    private Dialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);

        pDialog = new Dialog(this);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.layout_dialog_progress);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setCancelable(false);


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        vfPage = findViewById(R.id.vfPage);
        btnSkip = findViewById(R.id.btnSkip);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean go = true;
                email = etEmail.getText().toString();
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
                if (go) {
                    vfPage.showNext();
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vfPage.showPrevious();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean go = true;
                pw = etPassword.getText().toString();
                pwC = etPasswordConfirm.getText().toString();
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
                                Toast.makeText(context, "Selamat Datang!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<StructureDefault> call, Throwable t) {
                            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });


    }

}
