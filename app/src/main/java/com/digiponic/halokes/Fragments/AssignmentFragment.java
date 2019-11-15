package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.digiponic.halokes.Models.ListAssignmentSubject;
import com.digiponic.halokes.Models.ModelAssignmentSubject;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.digiponic.halokes.UI.AssignmentPagerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    LinearLayout llAssignmentContainer;
    ProgressBar pbLoading;
    //PageAdapter
    AssignmentPagerAdapter paCategory;
    ViewPager viewPager;
    TabLayout tlCategory;

    List<ListAssignmentSubject> lasAssignmentData;

    Button btnSearch, btnSearchBack, btnFilter;
    EditText etSearch;
    LinearLayout llSearchBar;
    InputMethodManager immSoftKeyboard;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);

        llAssignmentContainer = view.findViewById(R.id.llAssignmentContainer);
        pbLoading = view.findViewById(R.id.pbLoading);
        btnSearch = view.findViewById(R.id.btnSearch);
        llSearchBar = view.findViewById(R.id.llSearchBar);
        btnSearchBack = view.findViewById(R.id.btnSearchBack);
        etSearch = view.findViewById(R.id.etSearch);
        btnFilter = view.findViewById(R.id.btnFilter);

        configSearchBar();
        configBtnFilter();
        showAssignment();

        return view;
    }

    public void configSearchBar() {
        immSoftKeyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchBar(true);
            }
        });

        btnSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchBar(false);
            }
        });
    }

    public void showSearchBar(boolean show) {
        if (show) {
            Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            anim.setDuration(600);
            llSearchBar.setVisibility(View.VISIBLE);
            llSearchBar.startAnimation(anim);
            etSearch.requestFocus();
            immSoftKeyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } else {
            llSearchBar.setVisibility(View.GONE);
            etSearch.clearFocus();
            immSoftKeyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showAssignmentCategory(int tabCount, List<ListAssignmentSubject> lasData) {
        paCategory = new AssignmentPagerAdapter(getChildFragmentManager());
        paCategory.setTabCount(tabCount);//setting the tab
        paCategory.setLasData(lasData);

        tlCategory = view.findViewById(R.id.tlCategory);
        viewPager = view.findViewById(R.id.pager);

        viewPager.setAdapter(paCategory);
    }

    public void configBtnFilter() {
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                //.add(groupId, itemId, order, title);

                popupMenu.getMenu().add(1, 0, 0, "Urutkan dengan :")
                        .setIcon(getResources().getDrawable(R.drawable.ic_sort_30dp));
                popupMenu.getMenu().add(1, 1, 1, "* Tanggal");
                popupMenu.getMenu().add(1, 2, 2, "* A-Z");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getOrder() == 1) {
                            Toast.makeText(context, "Adding Contact", Toast.LENGTH_SHORT).show();
                        } else if (item.getOrder() == 2) {
                            Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show();
                        }else {
                            return false;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void showAssignment() {
        pbLoading.setVisibility(View.VISIBLE);

        Call<ModelAssignmentSubject> call = RetrofitClient.getInstance()
                .getApi()
                .showAssignment(session.getUser().getId_user());
        call.enqueue(new Callback<ModelAssignmentSubject>() {
            //init
            ModelAssignmentSubject las;

            @Override
            public void onResponse(Call<ModelAssignmentSubject> call, Response<ModelAssignmentSubject> response) {
                //removing loading spinning bar
                llAssignmentContainer.removeAllViews();

                las = response.body();
                if (response.isSuccessful() && isAdded()) {
                    //category

                    showAssignmentCategory(las.getData().size(), las.getData());
// LOOK DOWN THIS======================================================
//                    for (ListAssignmentSubject lasData : las.getData()) {// mengambil data mapel
//
//                        LinearLayout rowSubject = (LinearLayout) getLayoutInflater()
//                                .inflate(R.layout.template_assignment_subject, null);
//                        TextView tvAssignmentSubject, tvAssignmentCountBadge;
//                        final ScrollView hsvAssignmentSubjectContainer;
//                        LinearLayout llAssignmentSubjectContainer;
//
//                        //initializing elemen dari view yang di loop
//                        tvAssignmentSubject = rowSubject.findViewById(R.id.tvAssignmentSubject);
//                        tvAssignmentCountBadge = rowSubject.findViewById(R.id.tvAssignmentCountBadge);
//                        hsvAssignmentSubjectContainer = rowSubject.findViewById(R.id.svAssignmentSubjectContainer);
//                        llAssignmentSubjectContainer = rowSubject.findViewById(R.id.llAssignmentSubjectContainer);
//                        llAssignmentSubjectContainer.removeAllViews();
//
//                        //printing data
//                        tvAssignmentSubject.setText(lasData.getNama_mapel());
//                        tvAssignmentCountBadge.setText(lasData.getJumlah_tugas_mapel());
//                        tvAssignmentCountBadge.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                hsvAssignmentSubjectContainer.setSmoothScrollingEnabled(true);
//                                hsvAssignmentSubjectContainer.fullScroll(View.FOCUS_RIGHT);
//                            }
//                        });
//
//
//                        for (ListAssignmentTask latData : lasData.getData_tugas()) {
//                            LinearLayout rowTask = (LinearLayout) getLayoutInflater()
//                                    .inflate(R.layout.template_assignment_task, null);
//                            //initializing elemen dari view yang di loop
//                            TextView tvAssignmentTitle = rowTask.findViewById(R.id.tvAssignmentTitle);
//                            TextView tvAssignmentDeadline = rowTask.findViewById(R.id.tvAssignmentDeadline);
//                            TextView tvAssignmentTimeLeft = rowTask.findViewById(R.id.tvAssignmentTimeLeft);
//                            TextView tvAssignmentDesc = rowTask.findViewById(R.id.tvAssignmentDesc);
//                            //Date Formatter
//                            SimpleDateFormat dateFormat =
//                                    new SimpleDateFormat("dd-MMM-yyy", Locale.ENGLISH);
//
//                            try {
//                                Date dateDeadline = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(latData.getDeadline());
//                                tvAssignmentDeadline.setText(dateFormat.format(dateDeadline) + "");
//                                tvAssignmentTimeLeft.setText("");
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//                            //printing data
//                            tvAssignmentTitle.setText(latData.getJudul_tugas());
//                            tvAssignmentDesc.setText(latData.getDeskripsi());
//
//                            int hoursLeft = latData.getSisa_waktu();
//                            int daysLeft = Math.round(latData.getSisa_waktu() / 24);
//                            String timeLeftMessage = "";
//                            if (hoursLeft < 0) {
//                                if (hoursLeft < -24) {
//                                    timeLeftMessage = "Lewat " + daysLeft * (-1) + " hari ";
//                                } else {
//                                    timeLeftMessage = "Lewat " + hoursLeft * (-1) + " jam";
//                                }
//                            } else if (hoursLeft < 24) {
//                                if (hoursLeft < 1) {
//                                    timeLeftMessage = ">1 jam lagi";
//                                } else {
//                                    timeLeftMessage = hoursLeft + " jam lagi";
//                                }
//                            } else {
//                                timeLeftMessage = daysLeft + " hari lagi";
//                            }
//                            tvAssignmentTimeLeft.setText(timeLeftMessage);
//                            if (daysLeft < 0) {
//                                tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_danger));
//                            } else if (daysLeft <= 3) {
//                                tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_warning));
//                            } else {
//                                tvAssignmentTimeLeft.setBackground(getResources().getDrawable(R.drawable.bg_round_corner_success));
//                            }
//                            llAssignmentSubjectContainer.addView(rowTask);
//                        }
//                        llAssignmentContainer.addView(rowSubject);
//                    }
                } else {
                    Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                }
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelAssignmentSubject> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                pbLoading.setVisibility(View.GONE);
            }
        });

    }
}
