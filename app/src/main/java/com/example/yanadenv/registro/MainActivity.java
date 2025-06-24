package com.example.yanadenv.registro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yanadenv.registro.fragmentos.ClinicosFragment;
import com.example.yanadenv.registro.fragmentos.ParticipanteFragmen;
import com.example.yanadenv.R;
import com.example.yanadenv.registro.fragmentos.SocioeconomicoFragment;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewpager;
    private TabLayout tabLayout;
    private ClinicosFragment clinicosFragment;
    private ParticipanteFragmen participanteFragment;
    private SocioeconomicoFragment socioeconomicoFragment;
    private String valorRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassConnection connection = new ClassConnection();
        Bundle bundle = getIntent().getExtras();
        valorRecibido= getIntent().getStringExtra("nombre");


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewpager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        clinicosFragment = new ClinicosFragment();
        participanteFragment = new ParticipanteFragmen();
        socioeconomicoFragment = new SocioeconomicoFragment();
        tabLayout.setupWithViewPager(viewpager);




        //COLOR DE PESTAÑA
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#093A84"), Color.parseColor("#093A84"));


        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(participanteFragment,"Participante");
        viewPagerAdapter.addFragment(socioeconomicoFragment,"Socioeconómico");
        viewPagerAdapter.addFragment(clinicosFragment,"Clínicos");

        viewpager.setAdapter(viewPagerAdapter);
        tabLayout.getTabAt(0).view.setClickable(false); //disable clicking
        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_launcher_background);
        // tabLayout.getTabAt(0).view.set;
        tabLayout.getTabAt(1).view.setClickable(false); //disable clicking

        tabLayout.getTabAt(2).view.setClickable(false); //disable clicking
        // tabLayout.setEnableSwipe(false);
        tabLayout.setEnabled(false);

        final View touchView = findViewById(R.id.view_pager);
        touchView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });





    }

    private class viewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public viewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }







}