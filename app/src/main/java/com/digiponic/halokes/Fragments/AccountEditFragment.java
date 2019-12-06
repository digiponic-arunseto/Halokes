package com.digiponic.halokes.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.Models.ModelUser;
import com.digiponic.halokes.Models.StructureDefault;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AccountEditFragment extends Fragment {

    //storage permission properties
    private static final int PERMISSION_REQUEST_CODE = 1;
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
    //dialog
    Dialog pDialog;
    //image properties
    RequestBody finalImage;
    Bitmap selectedImg, defaultImg;

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

        //jika OS dibawah marsmellow
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
//                Toast.makeText(context, "GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                requestPermission(); // Code for permission
            }
        } else {

            // Code for Below 23 API Oriented Device
            // Do next code
        }
        configDialogLoading();
        configBtnConfirm();
        showProfileForm();

        return view;
    }

    public void configDialogLoading(){
        pDialog = new Dialog(context);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.layout_dialog_progress);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setCancelable(false);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Aplikasi telah diizinkan! Silahkan unggah foto anda!", Toast.LENGTH_SHORT).show();
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Toast.makeText(context, "Aplikasi tidak diizinkan! Silahkan izinkan aplikasi ini di pengaturan!", Toast.LENGTH_SHORT).show();
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void showProfileForm() {
        skvLoading.setVisibility(View.GONE);
        if (!session.getUser().getFoto().isEmpty()) {
            Picasso.with(context).load(session.getUser().getFoto()).into(ivAccountPic);
        }
        configUploadPic();
    }

    public void configUploadPic() {
        ivAccountPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK && requestCode == 100) {
            try {
                Uri pathImg = data.getData();
                //get image
                selectedImg = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pathImg);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                selectedImg.compress(Bitmap.CompressFormat.JPEG, 60, out);

                ivAccountPic.setImageBitmap(selectedImg);

                //get filename/path
                File fileImg = new File(getRealPathFromURI(pathImg));

                //creating request body for file
                finalImage = RequestBody.create(MediaType.parse("image/*"), fileImg);

            } catch (IOException e) {
                Toast.makeText(context, e.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        }else {
//            Toast.makeText(context, "KEKEKEKE", Toast.LENGTH_SHORT).show();
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

                commitEditProfile(false);
            }
        } else {
            commitEditProfile(true);
        }
    }

    public void commitEditProfile(boolean editPic) {
        pDialog.show();
        if (finalImage == null && email.isEmpty() && pwC.isEmpty()) {
            getActivity().onBackPressed();
            pDialog.hide();
            return;
        }
        try {
            RequestBody finalEmail;
            RequestBody finalPw;
            if (editPic) {
                finalEmail = null;
                finalPw = null;
            } else {
                finalEmail = RequestBody.create(MediaType.parse("text/plain"), email);
                finalPw = RequestBody.create(MediaType.parse("text/plain"), pwC);
            }

            Call<ModelUser> call = RetrofitClient.getInstance().getApi()
                    .actEditProfile(
                            session.getUser().getId_user(),
                            finalEmail,
                            finalPw,
                            finalImage);
            try {
                call.enqueue(new Callback<ModelUser>() {
                    @Override
                    public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                        ModelUser res = response.body();
                        if (response.isSuccessful() && isAdded() && res.getStatus()) {
                            session.saveUser(res.getData());
                            Toast.makeText(context, Html.fromHtml(res.getMessage()) + "", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        } else {
                            Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                        }
                        pDialog.hide();
                    }

                    @Override
                    public void onFailure(Call<ModelUser> call, Throwable t) {
                        Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage() + "", Toast.LENGTH_SHORT).show();
        }
    }

}
