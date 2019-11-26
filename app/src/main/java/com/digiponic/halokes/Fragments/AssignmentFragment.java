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

import com.digiponic.halokes.Models.ListAssignment;
import com.digiponic.halokes.Models.ModelAssignment;
import com.digiponic.halokes.R;
import com.digiponic.halokes.Retrofit.RetrofitClient;
import com.digiponic.halokes.Storage.Session;
import com.digiponic.halokes.UI.AssignmentPagerAdapter;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    SpinKitView skvLoading;

    //PageAdapter
    AssignmentPagerAdapter paCategory;
    ViewPager viewPager;
    TabLayout tlCategory;

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

        skvLoading = view.findViewById(R.id.skvLoading);

        configTitleBar();

        showAssignment();

        return view;
    }

    public void configTitleBar() {
        btnSearch = view.findViewById(R.id.btnSearch);
        llSearchBar = view.findViewById(R.id.llSearchBar);
        btnSearchBack = view.findViewById(R.id.btnSearchBack);
        etSearch = view.findViewById(R.id.etSearch);
        btnFilter = view.findViewById(R.id.btnSort);
        configSearchBar();
        configBtnSort();
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

    public void configBtnSort() {
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
                        } else {
                            return false;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void showAssignmentCategory(int tabCount, List<ListAssignment> lData) {
        paCategory = new AssignmentPagerAdapter(getChildFragmentManager());
        paCategory.setTabCount(tabCount);//setting the tab
        paCategory.setlData(lData);

        tlCategory = view.findViewById(R.id.tlCategory);
        viewPager = view.findViewById(R.id.pager);

        viewPager.setAdapter(paCategory);
    }

    private void showAssignment() {
        skvLoading.setVisibility(View.VISIBLE);

        Call<ModelAssignment> call = RetrofitClient.getInstance()
                .getApi()
                .showAssignment(session.getUser().getId_user());
        call.enqueue(new Callback<ModelAssignment>() {
            //init
            ModelAssignment las;

            @Override
            public void onResponse(Call<ModelAssignment> call, Response<ModelAssignment> response) {
                //removing loading spinning bar

                las = response.body();
                if (response.isSuccessful() && isAdded()) {
                    //category

                    showAssignmentCategory(las.getData().size(), las.getData());
                } else {
                    Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                }
                skvLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelAssignment> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                skvLoading.setVisibility(View.GONE);
            }
        });

    }
}
