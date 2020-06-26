package com.example.a3dstep;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.a3dstep.View.Community.CommunityFragment;
import com.example.a3dstep.View.PersonalFragment;
import com.example.a3dstep.View.RankFragment;
import com.example.a3dstep.View.Record.RunFragment;
import com.example.a3dstep.View.StepFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES =
            new int[] { R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3 };
    private final Context mContext;
    public TabsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CommunityFragment.newInstance("","");
            case 1:
                return StepFragment.newInstance("","");
            case 2:
                return RunFragment.newInstance("","");
            case 3:
                return RankFragment.newInstance("","");
            case 4:
                return PersonalFragment.newInstance("","");
            default:
                return null;
        }
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
    @Override
    public int getCount() {
        // Show 5 total pages.
        return 5;
    }
}