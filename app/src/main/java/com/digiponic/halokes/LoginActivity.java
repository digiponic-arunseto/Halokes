package com.digiponic.halokes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListUser;
import com.digiponic.halokes.Models.ModelUser;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    private Context context = LoginActivity.this;
    private Session session = Session.getInstance(this);
    private Dialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pDialog = new Dialog(this);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.layout_dialog_progress);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setCancelable(false);

        etUsername = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);


//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(LoginActivity.this, "Navigation Drawer", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(context,NavDrawer.class));
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });
    }

    private void attemptLogin() {
        String email = etUsername.getText() + "";
        String password = etPassword.getText() + "";

        if (email.equals("")) {
            etUsername.setError("Silahkan isi username Anda");
        }
        if (password.equals("")) {
            etPassword.setError("Silahkan isi password Anda");
        } else {
            commitLogin(email, password);
        }
    }

    private void commitLogin(String email, String password) {
        pDialog.show();
        Call<ModelUser> call = RetrofitClient.getInstance().getApi().actLogin(email, password);

        call.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                ModelUser res = response.body();
                if (response.isSuccessful()) {
                    session.saveUser(res.getData());
                    // bypass
//                    session.saveUser(new ListUser("TBOuGhQHUxMjfrqJ19Vd","test", "Testarino Subagio Reitmeier", "123","siswa"));

                    Toast.makeText(context, res.getMessage() + "", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(context, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                }

                // bypass
//                session.saveUser(new ListUser("TBOuGhQHUxMjfrqJ19Vd","test", "Testarino Subagio Reitmeier", "123","siswa"));

                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();

            }
        });
    }
}
