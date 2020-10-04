package com.example.a3dstep.View.Personal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a3dstep.Constant.SharePreference;
import com.example.a3dstep.Model.Data;
import com.example.a3dstep.R;
import com.example.a3dstep.View.Community.CommunityFragment;
import com.example.a3dstep.View.Login.LoginActivity;
import com.example.a3dstep.View.Personal.Setting.SettingActivity;
import com.example.a3dstep.View.StartActivity;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

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
    private static final String TAG = "PersonalFragment" ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //ImageView imageAvata;
    CardView cardViewAva;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imgWalk , imgWarter , imgBike;
    ImageView imgWalk1, imgWarter1, imgBike1;
    ImageView imgWalk2, imgWarter2, imgBike2;
    ImageView imgWalk3, imgWarter3, imgBike3;
    Button btnSetting;
    TextView title;
    ProfilePictureView imageAvataFB;
    ImageView imageAvata,imageBackGround;
    ImageView imgWarter11;
    private static final int PICK_IMAGE = 100;
    Uri imageUriBackground;
    private static final int REQUEST_IMAGE = 1;
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
        View view =inflater.inflate(R.layout.fragment_personal, container, false);
        //addControl
        SharePreference.getInstance().Init(getContext());
        addControl(view);
        addEvent();
        loadSetting(view);

        return view;
    }

    private void loadSetting(View view) {

        if( SharePreference.getInstance().getLoginMode() == Data.LOGIN_WITH_FACEBOOK){
            String nameFaceBook =   SharePreference.getInstance().getNameFaceBook();
            String imageUrlFacebook = SharePreference.getInstance().getImageUrlFacebook();
            String idFacebook =  SharePreference.getInstance().getIdFacebook();
            title.setText(nameFaceBook+"");
            Log.e(TAG,nameFaceBook);
            Log.e(TAG,imageUrlFacebook);
            Log.e(TAG,idFacebook);
            imageAvataFB.setProfileId(idFacebook);
            imageAvata.setVisibility(View.INVISIBLE);
        }
        else if(SharePreference.getInstance().getLoginMode() == Data.LOGIN_NORMAL){
            String name = "Minh Tuấn";
            imageAvataFB.setVisibility(View.INVISIBLE);
            imageAvata.setImageDrawable(getResources().getDrawable(R.drawable.icon));
            title.setText(name+"");

            String avaUri = SharePreference.getInstance().getUriAvata();
            if(avaUri.equals("false")){
                loadProfileDefault();
            }
            else {
                Uri uri = Uri.parse(avaUri);
                loadImageProfile(uri.toString());
            }

            String backgroundUri = SharePreference.getInstance().getUriBackground();

            if(backgroundUri.equals("false")){
               // loadProfileDefault();
            }
            else {
                Uri uri = Uri.parse(backgroundUri);
                imageBackGround.setImageURI(uri);
            }
        }
}
    private void addEvent() {
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);

            }
        });
        imageAvata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerOptions();
            }
        });
        imageBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0) {
                    //  Collapsed
                    cardViewAva.setVisibility(View.INVISIBLE);
                    collapsingToolbarLayout.setTitle("PROFILE");
                    //  collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
                    //  collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                }
                else {
                    //Expanded
                    cardViewAva.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle("");
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
        title = (TextView) view.findViewById(R.id.title);
        btnSetting =(Button)view.findViewById(R.id.btnSetting);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorAccent));
        imageBackGround = (ImageView) view.findViewById(R.id.imageBackGround);
        imageAvata =(ImageView) view.findViewById(R.id.imageAvata);
        imageAvataFB = (ProfilePictureView) view.findViewById(R.id.imageAvataFB);
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

    public void loadProfileDefault() {
        Glide.with(this).load(R.drawable.facebook_shadow)
                .into(imageAvata);
        imageAvata.setColorFilter(ContextCompat.getColor(getContext(), R.color.profile_default_tint));
    }


    public void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getContext(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onCameraSelected() {
                launchCamera();
            }

            @Override
            public void onGallerySelected() {
                launchGallery();
            }
        });
    }

    public void launchCamera() {
        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.REQUEST_CODE_TYPE, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // Gán tỉ lệ khóa là 1x1
        intent.putExtra(ImagePickerActivity.EXTRA_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.EXTRA_ASPECT_RATIO_X, 1);
        intent.putExtra(ImagePickerActivity.EXTRA_ASPECT_RATIO_Y, 1);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void launchGallery() {
        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.REQUEST_CODE_TYPE, ImagePickerActivity.REQUEST_IMAGE_GALLERY);

        // Gán kích thước tối đa cho ảnh
        intent.putExtra(ImagePickerActivity.EXTRA_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.EXTRA_BITMAP_MAX_WIDTH, 480);
        intent.putExtra(ImagePickerActivity.EXTRA_BITMAP_MAX_HEIGHT, 640);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void loadImageProfile(String url) {
        Glide.with(this).load(url)
                .into(imageAvata);
        imageAvata.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                loadImageProfile(uri.toString());
                SharePreference.getInstance().setUriAvata(uri+"");
            }
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUriBackground = data.getData();
            imageBackGround.setImageURI(imageUriBackground);
            SharePreference.getInstance().setUriBackground(imageUriBackground+"");
        }
    }
}