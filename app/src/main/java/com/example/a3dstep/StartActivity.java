package com.example.a3dstep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StartActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    ViewPager viewPager;
    MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        addControl();
       // openFragment(StepFragment.newInstance("", ""));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    bottomNavigation.getMenu().getItem(0).setChecked(false);

                bottomNavigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigation.getMenu().getItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void addControl() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //openFragment(CommunityFragment.newInstance("", ""));
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsPagerAdapter);


    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_community:
                            viewPager.setCurrentItem(0);
                            // openFragment(CommunityFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_step:
                            viewPager.setCurrentItem(1);
                            //openFragment(StepFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_run:
                            viewPager.setCurrentItem(2);
                            // openFragment(newInstance("", ""));
                            return true;
                        case R.id.navigation_rank:
                            viewPager.setCurrentItem(3);
                            // openFragment(RankFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_personal:
                            viewPager.setCurrentItem(4);
                            //openFragment(PersonalFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
