package com.digiponic.halokes.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.digiponic.halokes.R;
import com.digiponic.halokes.Storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    View view;
    int flContent;
    Context context;
    Session session;
    LinearLayout llFeedContainer;
    SpinKitView skvLoading;
    Button btnSearch, btnSearchBack, btnMore;
    EditText etSearch;
    LinearLayout llSearchBar;
    InputMethodManager immSoftKeyboard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        flContent = R.id.framelayout_content;
        context = getActivity();
        session = Session.getInstance(context);
        skvLoading = view.findViewById(R.id.skvLoading);

        btnSearch = view.findViewById(R.id.btnSearch);
        llSearchBar = view.findViewById(R.id.llSearchBar);
        btnSearchBack = view.findViewById(R.id.btnSearchBack);
        etSearch = view.findViewById(R.id.etSearch);
        btnMore = view.findViewById(R.id.btnMore);

        configSearchBar();
        configBtnMore();
        showFeed();

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

    public void configBtnMore() {
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                //.add(groupId, itemId, order, title);
                popupMenu.getMenu().add(1, 1, 1, "Read All");
                popupMenu.getMenu().add(1, 2, 2, "Delete All");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getOrder() == 1) {
                            Toast.makeText(context, "Adding Contact", Toast.LENGTH_SHORT).show();
                        } else if (item.getOrder() == 2) {
                            Toast.makeText(context, "All Read", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public void showFeed() {
        llFeedContainer = view.findViewById(R.id.llFeedContainer);
        skvLoading.setVisibility(View.VISIBLE);

        List<String[]> dummy = new ArrayList<>();
        llFeedContainer.removeAllViews();
        for (int i = 1; i <= 7; i++) {
            View rowFeed = getLayoutInflater().inflate(R.layout.template_feed, null);
//            final LinearLayout llFeed = child.findViewById(R.id.llFeed);
            ImageView ivFeedIcon = rowFeed.findViewById(R.id.ivFeedIcon);
            TextView tvFeedTitle = rowFeed.findViewById(R.id.tvFeedTitle);
            TextView tvFeedContent = rowFeed.findViewById(R.id.tvFeedContent);
            TextView tvFeedTag = rowFeed.findViewById(R.id.tvFeedTag);
            TextView tvFeedTime = rowFeed.findViewById(R.id.tvFeedTime);

            String title = "";
            String content = "";
            String date = "";
            String tag = "";
            if (i == 1) {
                title = "Tugas Bahasa Inggris: Tugas Membuat Clipping telah dinilai";
                date = "19 Des\n2019";
                content = "Tugas Bahasa Inggris yang anda kumpulan tgl 17 Des 2019 telah diverifikasi oleh Rahmat Muliyadi ,S.Pd\n semoga semakin semangat mengerjakan tugas yang lainnya ya!";
                tag = "Tugas - Konfirmasi";
            } else if (i == 2) {
                title = "Tugas Bahasa Indonesia: Tugas Membuat Puisi telah dinilai";
                date = "19 Des\n2019";
                content = "Tugas yang anda kumpulan tgl 17 Des 2019 telah diverifikasi oleh Rahmat Muliyadi ,S.Pd\n semoga semakin semangat mengerjakan tugas yang lainnya ya!";
                tag = "Tugas - Konfirmasi";
            } else if (i == 3) {
                title = "Penambahan Poin pelanggaran, [Membolos] : 12";
                date = "17 Des\n2019";
                content = "Pada hari Senin, 16 Des 2019, Siswa Afni Hanifah Putri masuk tanpa alasan, sebab itu Siswa akan dikenai tambahan Poin Pelanggaran sebanyak 12";
                tag = "Pelanggaran - Poin - Kehadiran";
            } else if (i == 4) {
                title = "Penambahan Poin prestasi, [peserta olimpiade sains] : 24";
                date = "11 Des\n2019";
                content = "Pada hari Selasa, 10 Des 2019, Siswa Afni Hanifah Putri mengikuti Olimpiade Sains Jawa Timur tahun 2019, sebab itu Siswa akan diberi tambahan Poin Prestasi sebanyak 24";
                tag = "Prestasi - Poin - Kompetisi";
            } else if (i == 5) {
                title = "Penambahan Poin prestasi, [peserta olimpiade matematika] : 36";
                date = "6 Des\n2019";
                content = "Pada hari Selasa, 3 Des 2019, Siswa Afni Hanifah Putri mengikuti Olimpiade Matematika Indonesia tahun 2019, sebab itu Siswa akan diberi tambahan Poin Prestasi sebanyak 36";
                tag = "Prestasi - Poin - Kompetisi";
            } else if (i == 6) {
                title = "Peringatan! UAS Semester Genap 2019 akan diselenggarakan pada 16 - 20 Des 2019, persiapkan diri kalian!";
                date = "1 Des\n2019";
                content = "Persiapkan diri kalian!, UAS Semester Genap 2019 akan diselenggarakan pada 16 - 20 Des 2019!";
                tag = "Peringatan - Ujian - Ujian Akhir Semester";
            }else{
                title = "Tugas Baru (Matematika) : Tugas Aljabar 1";
                date = "1 Des\n2019";
                content = "Tugas Matematika: Tugas Aljabar 1, deskripsi : tolong kerjakan kegiatan 3 di buku cetak pada halaman 49-51, terakhir dikumpulkan pada hari Rabu, 11 Desemnber 2019, di kertas folio bergaris, peringatan!, keterlambatan pengumpulan berakibat pengurangan nilai tugas sebanyak 10/hari.\nTugas ini dibuat oleh Miftahul Schwarzreitmeier , S.Pd, M.Kom";
                tag = "Tugas - Notifikasi";
            }
            tvFeedTitle.setText(title);
            tvFeedTime.setText(date);
            tvFeedContent.setText(content);
            tvFeedTag.setText(tag);

            llFeedContainer.addView(rowFeed);
            //click function
            clickRowView(rowFeed, ivFeedIcon, tvFeedTitle, tvFeedTag, tvFeedTime, tvFeedContent);
        }
        skvLoading.setVisibility(View.GONE);
    }

    public void clickRowView(final View rowFeed, final ImageView ivFeedIcon, final TextView tvFeedTitle, final TextView tvFeedTag, final TextView tvFeedTime, final TextView tvFeedContent) {
        rowFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivFeedIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_mail_outline_30dp));
                tvFeedTitle.setTypeface(tvFeedTitle.getTypeface(), Typeface.ITALIC);
                tvFeedTag.setBackground(getResources().getDrawable(R.drawable.bg_round_side_border_white));
                tvFeedTag.setTextColor(getResources().getColor(R.color.colorDark));
                tvFeedTime.setTextColor(getResources().getColor(R.color.colorDark));
                rowFeed.setBackgroundColor(getResources().getColor(R.color.colorBackground));

                FeedSubFragment feedSF = new FeedSubFragment();
                feedSF.setFeedId(1+"");
                feedSF.setFeedTitle(tvFeedTitle.getText()+"");
                feedSF.setFeedContent(tvFeedContent.getText()+"");
                String date = tvFeedTime.getText().toString().trim().replace("\n", " ");
                feedSF.setFeedInfo(date+" \nKategori : "+tvFeedTag.getText().toString().trim().replace(" - ", " , ")+" ");

                feedSF.show(getFragmentManager().beginTransaction().addToBackStack("1"), "Dialog Fragment");
            }
        });
    }
}
