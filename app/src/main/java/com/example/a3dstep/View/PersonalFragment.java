package com.example.a3dstep.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a3dstep.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imageAvata;
    CardView cardViewAva;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imgWalk,imgWarter,imgBike;
    ImageView imgWalk1,imgWarter1,imgBike1;
    ImageView imgWalk2,imgWarter2,imgBike2;
    ImageView imgWalk3,imgWarter3,imgBike3;

    ImageView imgWarter11;
    public PersonalFragment() {
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
    public static PersonalFragment newInstance(String param1, String param2) {
        PersonalFragment fragment = new PersonalFragment();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_personal, container, false);
        //addControl
        addControl(view);


       /* Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ava3d);

        imageAvata.setImageBitmap(largeIcon);
        getRoundedCroppedBitmap(largeIcon);*/
       addEvent();

        return view;
    }

    private void addEvent() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0) {
                    //  Collapsed
                    cardViewAva.setVisibility(View.INVISIBLE);
                }
                else {
                    //Expanded
                    cardViewAva.setVisibility(View.VISIBLE);
                }
            }
        });
        imgWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWalk.setBackgroundResource(R.color.BackgroundButtonTab);
                imgWarter.setBackgroundResource(R.color.BackgroundButtonTabPress);
                imgBike.setBackgroundResource(R.color.BackgroundButtonTabPress);

                imgWalk3.setBackgroundResource(R.color.iconColorMode2);
                imgWarter3.setBackgroundResource(R.color.BackgroundButtonTabPress);
                imgBike3.setBackgroundResource(R.color.BackgroundButtonTabPress);

                imgWalk2.setBackgroundResource(R.color.BackgroundButtonTab);
                imgWarter2.setBackgroundResource(R.color.BackgroundButtonTabPress);
                imgBike2.setBackgroundResource(R.color.BackgroundButtonTabPress);

                imgWarter11.setBackgroundResource(R.color.BackgroundButtonTabPress);
            }
        });
        imgWarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWarter.setBackgroundResource(R.color.BackgroundButtonTab);
                imgWalk.setBackgroundResource(R.color.BackgroundButtonTabPress);
                imgBike.setBackgroundResource(R.color.BackgroundButtonTabPress);

                imgWarter3.setBackgroundResource(R.color.iconColorMode2);
                imgWalk3.setBackgroundResource(R.color.BackgroundButtonTabPress);
                imgBike3.setBackgroundResource(R.color.BackgroundButtonTabPress);

                imgWarter2.setBackgroundResource(R.color.BackgroundButtonTab);
                imgWalk2.setBackgroundResource(R.color.BackgroundButtonTabPress);
                imgBike2.setBackgroundResource(R.color.BackgroundButtonTabPress);

                imgWarter11.setBackgroundResource(R.color.BackgroundButtonTabPress);
            }
        });
        imgBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBike.setBackgroundResource(R.color.BackgroundButtonTab);
                imgWalk.setBackgroundResource(R.color.BackgroundButtonTabPress);
                imgWarter.setBackgroundResource(R.color.BackgroundButtonTabPress);

                imgBike3.setBackgroundResource(R.color.iconColorMode2);
                imgWalk3.setBackgroundResource(R.color.BackgroundButtonTabPress);
                imgWarter3.setBackgroundResource(R.color.BackgroundButtonTabPress);

                imgBike2.setBackgroundResource(R.color.BackgroundButtonTab);
                imgWalk2.setBackgroundResource(R.color.BackgroundButtonTabPress);
                imgWarter2.setBackgroundResource(R.color.BackgroundButtonTabPress);

                imgWarter11.setBackgroundResource(R.color.BackgroundButtonTab);
            }
        });
    }

    private void addControl(View view) {
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("PROFILE");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorAccent));

        imageAvata = (ImageView) view.findViewById(R.id.imageAvata);
        appBarLayout =  (AppBarLayout) view.findViewById(R.id.appBarLayout);
        cardViewAva =  (CardView) view.findViewById(R.id.cardViewAva);
        imgWalk = (ImageView) view.findViewById(R.id.imgWalk);
        imgWarter = (ImageView) view.findViewById(R.id.imgWarter);
        imgBike = (ImageView) view.findViewById(R.id.imgBike);

        imgWalk1 = (ImageView) view.findViewById(R.id.imgWalk1);
        imgWarter1 = (ImageView) view.findViewById(R.id.imgWarter1);
        imgBike1 = (ImageView) view.findViewById(R.id.imgBike1);

        imgWalk2 = (ImageView) view.findViewById(R.id.imgWalk2);
        imgWarter2 = (ImageView) view.findViewById(R.id.imgWarter2);
        imgBike2 = (ImageView) view.findViewById(R.id.imgBike2);

        imgWalk3 = (ImageView) view.findViewById(R.id.imgWalk3);
        imgWarter3 = (ImageView) view.findViewById(R.id.imgWarter3);
        imgBike3 = (ImageView) view.findViewById(R.id.imgBike3);

        imgWarter11 = (ImageView) view.findViewById(R.id.imgWarter11);
    }

    private Bitmap getRoundedCroppedBitmap(Bitmap bitmap) {
        int widthLight = bitmap.getWidth();
        int heightLight = bitmap.getHeight();

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Paint paintColor = new Paint();
        paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);

        RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

        canvas.drawRoundRect(rectF, widthLight / 2 ,heightLight / 2,paintColor);

        Paint paintImage = new Paint();
        paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, 0, 0, paintImage);

        return output;
    }
}