package com.example.a3dstep.View.Community;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a3dstep.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {
	String TAG = "CommunityFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button txtWorldCommunity,txtFollowCommunity,txtMeCommunity;
    private ImageView imgWorldSelect,imgFollowSelect,imgMeSelect;
    private FragmentWorld fragmentWorld;
    private FragmentFollow fragmentFollow;
    private FragmentMe fragmentMe;

    private FragmentActivity myContext;
    public CommunityFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {

        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

  /*  @Override
    public void onAttach(@NonNull Context context) {
        myContext=(FragmentActivity) context;
        super.onAttach(context);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);
	    myContext = (FragmentActivity) container.getContext();
        getAllWidgets(view);
        addEvent();
        return view;
    }


    private void addEvent() {
        txtWorldCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWorldSelect.setBackgroundResource(R.color.BackgroundButtonTab);
                imgFollowSelect.setBackgroundResource(R.color.iconColorMode2);
                imgMeSelect.setBackgroundResource(R.color.iconColorMode2);
                replaceFragment(fragmentWorld);
            }
        });
        txtFollowCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWorldSelect.setBackgroundResource(R.color.iconColorMode2);
                imgFollowSelect.setBackgroundResource(R.color.BackgroundButtonTab);
                imgMeSelect.setBackgroundResource(R.color.iconColorMode2);
                replaceFragment(fragmentFollow);
            }
        });
        txtMeCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWorldSelect.setBackgroundResource(R.color.iconColorMode2);
                imgFollowSelect.setBackgroundResource(R.color.iconColorMode2);
                imgMeSelect.setBackgroundResource(R.color.BackgroundButtonTab);
                replaceFragment(fragmentMe);
            }
        });
    }



    private void getAllWidgets(View view) {

        txtWorldCommunity = (Button) view.findViewById(R.id.txtWorldCommunity);
        txtFollowCommunity = (Button) view.findViewById(R.id.txtFollowCommunity);
        txtMeCommunity = (Button) view.findViewById(R.id.txtMeCommunity);
        imgWorldSelect = (ImageView) view.findViewById(R.id.imgWorldSelect);
        imgMeSelect = (ImageView) view.findViewById(R.id.imgMeSelect);
        imgFollowSelect = (ImageView) view.findViewById(R.id.imgFollowSelect);
        fragmentWorld = new FragmentWorld();
        fragmentMe = new FragmentMe();
        fragmentFollow = new FragmentFollow();
        replaceFragment(fragmentWorld);
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = myContext.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
	    Fragment prev = fm.findFragmentByTag("TUAN");
	    if (prev != null) {
		    //commit immediately
		    ft.remove(prev).commitAllowingStateLoss();
		    Log.e(TAG,"Remove Fragment");
	    }
	    FragmentTransaction addTransaction  = fm.beginTransaction();
	    addTransaction .replace(R.id.frame_container, fragment,"TUAN");
	    addTransaction .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	    addTransaction .commit();
    }

}