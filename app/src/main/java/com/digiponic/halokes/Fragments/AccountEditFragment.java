package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListUser;
import com.digiponic.halokes.Models.StructureDefault;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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
    Button btnConfirm;
    String email, pw, pwC;
    ImageView ivAccountPic;
    TextView tvAccountSelectImage;
    RequestBody finalImage;
    //image changer
    Bitmap selectedImg,defaultImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_edit, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        skvLoading = view.findViewById(R.id.skvLoading);
        ivAccountPic = view.findViewById(R.id.ivAccountPic);
        tvAccountSelectImage = view.findViewById(R.id.tvAccountSelectImage);
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
        configUploadPic();
    }

    public void configUploadPic() {
        ivAccountPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 777);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri pathImg = data.getData();

            try {
                //get image
                selectedImg = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pathImg);
                ivAccountPic.setImageBitmap(selectedImg);

                //get filename/path
                File fileImg = new File(getRealPathFromURI(pathImg));

                //creating request body for file
                finalImage = RequestBody.create(MediaType.parse("image/*"), fileImg);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void configBtnConfirm() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptConfirm();
            }
        });
    }

    public void attemptConfirm() {
        boolean go = true;
        email = etEmail.getText().toString().trim();
        pw = etPassword.getText().toString();
        pwC = etPasswordConfirm.getText().toString();

        // email security
        if (!email.isEmpty() || !pw.isEmpty()) {
            if (!email.contains("@")) {
                go = false;
                etEmail.setError("Penulisan Email salah");
                etEmail.requestFocus();
            }
            if (!email.contains(".")) {
                go = false;
                etEmail.setError("Penulisan Email salah");
                etEmail.requestFocus();
            }
            if (pw.length() < 6) {
                go = false;
                etPassword.setError("Password minimal 6 karakter");
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
                actEditProfile();
            }
        } else {
            actEditProfilePic();
        }
    }

    public void actEditProfile() {
        Call<StructureDefault> call = RetrofitClient.getInstance().getApi()
                .actInitialize(
                        session.getUser().getId_user(),
                        email,
                        pwC);
        try {
            call.enqueue(new Callback<StructureDefault>() {
                @Override
                public void onResponse(Call<StructureDefault> call, Response<StructureDefault> response) {
                    if (response.body().getStatus()) {
                        btnConfirm.setVisibility(View.GONE);
                        Toast.makeText(context, "Data berhasil diubah!", Toast.LENGTH_SHORT).show();
                        ListUser lu = session.getUser();
                        ListUser updatedUser = new ListUser(
                                lu.getId_user(),
                                email,
                                pwC,
                                lu.getNis(),
                                lu.getNama_siswa(),
                                lu.getKelas(),
                                lu.getRole()
                        );
                        session.saveUser(updatedUser);
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<StructureDefault> call, Throwable t) {
                    Toast.makeText(context, t.getMessage() + "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage() + "", Toast.LENGTH_SHORT).show();
        }
    }

    public void actEditProfilePic() {
        if (finalImage == null) {
            getActivity().onBackPressed();
            return;
        }
        Toast.makeText(context, "Ubbah", Toast.LENGTH_SHORT).show();
        Call<StructureDefault> call = RetrofitClient.getInstance().getApi()
                .actUpdatePic(session.getUser().getId_user(), finalImage);

    }
}
