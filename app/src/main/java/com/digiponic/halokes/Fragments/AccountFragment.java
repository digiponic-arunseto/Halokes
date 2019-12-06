package com.digiponic.halokes.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.LoginActivity;
import com.digiponic.halokes.Models.ListStudent;
import com.digiponic.halokes.Models.ModelStudent;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private static final int IMG_REQUEST = 777;
    View view;
    Context context;
    Session session;
    TextView tvAccountName, tvAccountNo, tvAccountAttendance, tvAccountGrade, tvAccountAchievement;
    Button btnMore, btnBio;
    View bnvMainNav;
    ImageView ivAccountPic;
    int flContent;
    //bio props
    private AccountBioFragment bioFragment;
    private ModelStudent mStudent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        context = getActivity();
        session = Session.getInstance(context);
        flContent = R.id.framelayout_content;

        btnMore = view.findViewById(R.id.btnMore);

        ivAccountPic = view.findViewById(R.id.ivAccountPic);
        tvAccountName = view.findViewById(R.id.tvAccountName);
        tvAccountNo = view.findViewById(R.id.tvAccountNo);
        tvAccountAttendance = view.findViewById(R.id.tvAccountAttendance);
        tvAccountGrade = view.findViewById(R.id.tvAccountGrade);
        tvAccountAchievement = view.findViewById(R.id.tvAccountAchievement);

        btnBio = view.findViewById(R.id.btnBio);
        bnvMainNav = getActivity().findViewById(R.id.bnvMainNav);


        configBtnMore();
        showAccount();
        showBio();

        return view;
    }

    public void showAccount() {
        tvAccountName.setText(session.getUser().getNama_siswa());
        tvAccountNo.setText("NISN : " + session.getUser().getNis());
        if (!session.getUser().getFoto().isEmpty()) {
            Picasso.with(context).load(session.getUser().getFoto()).into(ivAccountPic);
        }
    }

    public void navAccountBio() {
        btnBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bnvMainNav.setVisibility(View.INVISIBLE);
                bioFragment = new AccountBioFragment();

                getFragmentManager().beginTransaction().replace(flContent,
                        bioFragment).addToBackStack("1").commit();
            }
        });
    }

    public void showBio() {
        Call<ModelStudent> call = RetrofitClient.getInstance().getApi()
                .showStudentBio(
                        session.getUser().getId_user()
                );
        call.enqueue(new Callback<ModelStudent>() {
            @Override
            public void onResponse(Call<ModelStudent> call, Response<ModelStudent> response) {
                if (response.isSuccessful() && isAdded()) {
                    mStudent = response.body();
                    ListStudent lsData = mStudent.getData();
                    tvAccountAttendance.setText(lsData.getTotal_kehadiran() + " %");
                    tvAccountGrade.setText(lsData.getTotal_rr() + "");
                    tvAccountAchievement.setText(lsData.getTotal_prestasi() + " Poin");

                    navAccountBio();

                }

            }

            @Override
            public void onFailure(Call<ModelStudent> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void configBtnMore() {
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                //.add(groupId, itemId, order, title);
                popupMenu.getMenu().add(1, 1, 1, "Edit Profil");
                popupMenu.getMenu().add(1, 2, 2, "Keluar");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getOrder() == 1) {
                            NavEditProfile();
                        } else if (item.getOrder() == 2) {
                            commitLogout();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public void NavEditProfile() {
        bnvMainNav.setVisibility(View.INVISIBLE);

        getFragmentManager().beginTransaction().replace(flContent,
                new AccountEditFragment()).addToBackStack("1").commit();
    }

    public void commitLogout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View layoutLogout = getLayoutInflater().inflate(R.layout.layout_dialog_confirmation, null);
        // yes == log out
        // no == cancel
        Button btnYes = layoutLogout.findViewById(R.id.btnNo);
        Button btnNo = layoutLogout.findViewById(R.id.btnYes);
        TextView tvTitle = layoutLogout.findViewById(R.id.tvTitle);
        builder.setView(layoutLogout);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();// to resize alert dialogMoreMenu, this command line should be below the alert.show()
        dialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);// here i have fragment height 30% of window's height you can set it as per your requirement


        tvTitle.setText("Apakah Anda yakin ingin keluar akun ?");
        btnYes.setText("KELUAR");
        btnNo.setText("BATAL");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.clear();
                dialog.dismiss();
                startActivity(new Intent(context, LoginActivity.class));
                getActivity().finish();
                Toast.makeText(context, "Berhasil keluar", Toast.LENGTH_SHORT).show();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}
