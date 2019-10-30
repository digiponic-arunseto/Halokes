package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.digiponic.halokes.R;

public class HomeFragment extends Fragment {
    View view;
    int flContent;
    Context context;
    LinearLayout llMenuMore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        flContent = R.id.framelayout_content;
        context = getActivity();

//        llMenuMore = view.findViewById(R.id.llMenuMore);


//        llMenuMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////               getFragmentManager().beginTransaction().replace(flContent,
////                       new SettingsFragment()).commit();
//
//                /* BottomSheet With dialog */
//        View dialogView = getLayoutInflater().inflate(R.layout.fragment_more_menu, null);
//        BottomSheetDialog dialog = new BottomSheetDialog(context);
//        dialog.setContentView(dialogView);
//        dialog.show();
//
//                /* BottomSheet With fragment */
////                MoreMenuFragment bottomSheetFragment = new MoreMenuFragment();
////                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
//
//            }
//        });
        return view;
    }
}
