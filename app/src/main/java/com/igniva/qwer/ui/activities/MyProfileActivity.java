package com.igniva.qwer.ui.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.controller.ApiInterface;
import com.igniva.qwer.model.ProfileResponsePojo;
import com.igniva.qwer.model.ResponsePojo;
import com.igniva.qwer.model.StatePojo;
import com.igniva.qwer.model.predictionsCountriesPojo;
import com.igniva.qwer.ui.adapters.CountriesAdapter;
import com.igniva.qwer.ui.views.CallProgressWheel;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.ImagePicker;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.Validation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.igniva.qwer.R.id.iv_edit_profile;
import static com.igniva.qwer.R.id.textview_title;
import static com.igniva.qwer.utils.Constants.ALPHA_ANIMATIONS_DURATION;
import static com.igniva.qwer.utils.Constants.PERCENTAGE_TO_HIDE_TITLE_DETAILS;
import static com.igniva.qwer.utils.Constants.PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR;
import static com.igniva.qwer.utils.ImagePicker.PIC_CROP;


/**
 * Created by karanveer on 21/9/17.
 */

public class MyProfileActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, EasyPermissions.PermissionCallbacks, AdapterView.OnItemSelectedListener {
    private final int RC_CAMERA_PERM = 123;
    public String Gender;
    @BindView(R.id.et_city)
    public
    AutoCompleteTextView mEtCity;
    @BindView(R.id.autocomTextViewCountry)
    public
    AutoCompleteTextView mautocomTextViewCountry;
    public ArrayList<predictionsCountriesPojo> mAlLangList;
    public ArrayList<StatePojo> mAllStateList;
    public ProfileResponsePojo mResponsePojo = null;
    public String coverIMageID = "";
    @BindView(R.id.imageview_placeholder)
    ImageView mImageviewPlaceholder;
    @BindView(R.id.linearlayout_title)
    LinearLayout mLinearlayoutTitle;
    @BindView(R.id.framelayout_title)
    FrameLayout mFramelayoutTitle;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.et_country)
    EditText mEtCountry;
    @BindView(R.id.et_pincode)
    EditText mEtPincode;
    @BindView(R.id.et_age)
    EditText mEtAge;
    /* @BindView(R.id.et_gender)
     EditText mEtGender;*/
    @BindView(R.id.et_about)
    EditText mEtAbout;
    @BindView(textview_title)
    TextView mTextviewTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.avatar)
    SimpleDraweeView mAvatar1;
    @BindView(iv_edit_profile)
    ImageView mIvEditProfile;
    @BindView(R.id.iv_add_pic)
    ImageView mIvAddPic;
    @BindView(R.id.iv_proile_pic1)
    ImageView mIvProilePic1;
    @BindView(R.id.iv_proile_pic2)
    ImageView mIvProilePic2;
    @BindView(R.id.iv_proile_pic3)
    ImageView mIvProilePic3;
    @BindView(R.id.iv_proile_pic4)
    ImageView mIvProilePic4;
    @BindView(R.id.tv_lets_start)
    TextView mTvLetsStart;
    @BindView(R.id.ll_lets_start)
    LinearLayout mLlLetsStart;
    @BindView(R.id.cross_icon1)
    ImageView mCrossIcon1;
    @BindView(R.id.cross_icon2)
    ImageView mCrossIcon2;
    @BindView(R.id.cross_icon3)
    ImageView mCrossIcon3;
    @BindView(R.id.cross_icon4)
    ImageView mCrossIcon4;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    @BindView(R.id.tvName)
    EditText mtvName;
    @BindView(R.id.ivaddImage1)
    ImageView mivaddImage1;
    @BindView(R.id.ivaddImage2)
    ImageView mivaddImage2;
    @BindView(R.id.ivaddImage3)
    ImageView mivaddImage3;
    @BindView(R.id.ivaddImage4)
    ImageView mivaddImage4;
    @BindView(R.id.genderspinner)
    Spinner mgenderSpinner;
    @Inject
    Retrofit retrofit;
    @BindView(R.id.rvCountries)
    RecyclerView mrvCountries;
    private File outPutFile;
    private int callFrom;
    private String selGender;
    private String[] gender = {"Male", "Female"};
    private int IMAGE_REQUEST_CODE = 500;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private boolean alreadyLogin;
    private String TAG = getClass().getName();
    private String country_id;

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @OnClick(R.id.iv_edit_profile)
    public void edit() {
        mautocomTextViewCountry.setEnabled(true);
        mEtCity.setEnabled(true);
        mEtPincode.setEnabled(true);
        mEtAge.setEnabled(true);
        mgenderSpinner.setEnabled(true);
        mEtAbout.setEnabled(true);
        mtvName.setEnabled(true);
        mtvName.setSelection(mtvName.getText().length());
    }

    @OnClick(R.id.ivaddImage1)
    public void pickImage() {
        callFrom = 1;
        changeImage();
    }

    @OnClick(R.id.ivaddImage2)
    public void pickImage1() {
        callFrom = 2;
        changeImage();
    }

    @OnClick(R.id.ivaddImage3)
    public void pickImage2() {
        callFrom = 3;
        changeImage();
    }

    @OnClick(R.id.ivaddImage4)
    public void pickImage3() {
        callFrom = 4;
        changeImage();
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void changeImage() {
        if (hasCameraPermission()) {
            // Have permission, do the thing!
            startActivityForResult(ImagePicker.getPickImageIntent(MyProfileActivity.this), IMAGE_REQUEST_CODE);

        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_ask_again),
                    RC_CAMERA_PERM,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);
        setUpLayout();
        getProfileApi();
        setDataInViewObjects();
        ApiControllerClass.callCountriesListApi(MyProfileActivity.this, retrofit, mautocomTextViewCountry);
    }

    // call get profile api
    private void getProfileApi() {
        try {
            if (Utility.isInternetConnection(this)) {
                CallProgressWheel.showLoadingDialog(this, "Loading...");
                Call<ProfileResponsePojo> posts = retrofit.create(ApiInterface.class).getProfile();
                posts.enqueue(new Callback<ProfileResponsePojo>() {
                    @Override
                    public void onResponse(Call<ProfileResponsePojo> call, Response<ProfileResponsePojo> response) {
                        if (response.body().getStatus() == 200) {
                            CallProgressWheel.dismissLoadingDialog();
                            //callSuccessPopUp(MyProfileActivity.this, responsePojo.getDescription());
                            // Utility.showToastMessageShort(MyProfileActivity.this,responsePojo.getDescription());
                            setDataInView(response.body());

                        } else if (response.body().getStatus() == 400) {
                            CallProgressWheel.dismissLoadingDialog();
                            Log.e("profile", response.body().getDescription());
                            // Toast.makeText(MyProfileActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                            // Log.e("profile",responsePojo.getDescription());
                            //Toast.makeText(MyProfileActivity.this, responsePojo.getDescription(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileResponsePojo> call, Throwable t) {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(MyProfileActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(MyProfileActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void setDataInView(ProfileResponsePojo responsePojo) {
        mResponsePojo = responsePojo;
        mEtAbout.setText(responsePojo.getData().about);
        mEtAge.setText(responsePojo.getData().age);
        mEtCity.setText(responsePojo.getData().city);
        mautocomTextViewCountry.setText(responsePojo.getData().country);
        // mEtGender.setText(responsePojo.getData().gender);
        mEtPincode.setText(responsePojo.getData().pincode);
        mtvName.setText(responsePojo.getData().getName());
        mTextviewTitle.setText(responsePojo.getData().getName());
        if (responsePojo.getData().getUser_image() != null && responsePojo.getData().getUser_image().size() > 0) {
            for (ProfileResponsePojo.UserImageData userImageData :
                    responsePojo.getData().getUser_image()) {
                 if (responsePojo.getData().getUser_image().indexOf(userImageData) == 0) {
                    Glide.with(this).load(userImageData.getImage()).into(mIvProilePic1);
                } else if (responsePojo.getData().getUser_image().indexOf(userImageData) == 1) {
                    Glide.with(this).load(userImageData.getImage()).into(mIvProilePic2);
                }
                if (responsePojo.getData().getUser_image().indexOf(userImageData) == 2) {
                    Glide.with(this).load(userImageData.getImage()).into(mIvProilePic3);
                }
                if (responsePojo.getData().getUser_image().indexOf(userImageData) == 3) {
                    Glide.with(this).load(userImageData.getImage()).into(mIvProilePic4);
                }
                if (userImageData.getIs_cover_image().equals("1")) {
                    coverIMageID = userImageData.getId();
                    Glide.with(this).load(userImageData.getImage()).into(mImageviewPlaceholder);
                }else if(coverIMageID.equals("") && responsePojo.getData().getUser_image().indexOf(userImageData)==responsePojo.getData().getUser_image().size()-1){
                    Glide.with(this).load(responsePojo.getData().getUser_image().get(0).getImage()).into(mImageviewPlaceholder);
                 }
             }


        }else{
            mImageviewPlaceholder.setImageDrawable(getResources().getDrawable(R.drawable.blue_skyline));
        }

        Gender = responsePojo.getData().getGender();
        if (Gender != null) {
            Log.e("Gender", Gender);
            mgenderSpinner.setSelection(getIndex(mgenderSpinner, Gender));
        }
    }

    @Override
    protected void setUpLayout() {
        getLocation();
        if (getIntent().hasExtra(Constants.MYPROFILEEDITABLE)) {
            mIvEditProfile.setVisibility(View.VISIBLE);
//            Glide.with(this).load(R.drawable.pp4).into(mIvProilePic1);
//            Glide.with(this).load(R.drawable.pp1).into(mIvProilePic2);
//            Glide.with(this).load(R.drawable.pp2).into(mIvProilePic3);
//            mIvProilePic1.setImageDrawable(getResources().getDrawable(R.drawable.pp4));
//            mIvProilePic2.setImageDrawable(getResources().getDrawable(R.drawable.pp1));
//            mIvProilePic3.setImageDrawable(getResources().getDrawable(R.drawable.pp2));
            mTvLetsStart.setVisibility(View.GONE);
//            Glide.with(this).load(R.drawable.pp4).into(mImageviewPlaceholder);
            mImageviewPlaceholder.setImageDrawable(getResources().getDrawable(R.drawable.blue_skyline));
            mautocomTextViewCountry.setEnabled(false);
            mEtCity.setEnabled(false);
            mEtPincode.setEnabled(false);
            mEtAge.setEnabled(false);
            mgenderSpinner.setEnabled(false);
            mEtAbout.setEnabled(false);
            mtvName.setEnabled(false);
        } else {
            mIvEditProfile.setVisibility(View.GONE);
            mIvProilePic1.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
            mIvProilePic2.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
            mIvProilePic3.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
            mIvProilePic4.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
            mLlLetsStart.setVisibility(View.VISIBLE);
            mImageviewPlaceholder.setImageDrawable(getResources().getDrawable(R.drawable.blue_skyline));
        }
        mToolbar.setTitle("");
        mAppbar.addOnOffsetChangedListener(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        startAlphaAnimation(mTextviewTitle, 0, View.INVISIBLE);
        //set avatar and cover
        mAvatar1.setImageDrawable(getResources().getDrawable(R.drawable.circular2));
//        avatar.setImageURI(imageUri);
        mToolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mgenderSpinner = (Spinner) findViewById(R.id.genderspinner);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, gender);
        adapter_state
                .setDropDownViewResource(R.layout.spinner_dropdown_item);
        mgenderSpinner.setAdapter(adapter_state);
        mgenderSpinner.setOnItemSelectedListener(this);

        mautocomTextViewCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                country_id = mAlLangList.get(i).getId();
                Log.e("countryid", country_id);
                ApiControllerClass.callStateListApi(MyProfileActivity.this, retrofit, mEtCity, country_id);
                //onLangItemClick(mActvLangISpeak, selection, Constants.LANGUAGE_SPEAK);
            }
        });

        mEtCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                //onLangItemClick(mActvLangISpeak, selection, Constants.LANGUAGE_SPEAK);
            }
        });

    }

    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
//        Log.e("percntqage", percentage + "");
        if (percentage == 1.0) {
//            mToolbar2.setVisibility(View.GONE);
        }
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTextviewTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);


//                startAlphaAnimation(mToolbarRating, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                startAlphaAnimation(mTvRating, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                startAlphaAnimation(toolbar1, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTextviewTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
 //                startAlphaAnimation(mToolbarRating, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                startAlphaAnimation(mTvRating, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                startAlphaAnimation(toolbar1, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLinearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLinearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    @Override
    protected void setDataInViewObjects() {
        mautocomTextViewCountry.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
               /* if (!mautocomTextviewAddress.getText().toString().equals("")) { //if edittext include text
                    mbtnClearAddress.setVisibility(View.VISIBLE);
                } else { //not include text
                    mbtnClearAddress.setVisibility(View.GONE);
                }*/
            }
        });

        mEtCity.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
               /* if (!mautocomTextviewAddress.getText().toString().equals("")) { //if edittext include text
                    mbtnClearAddress.setVisibility(View.VISIBLE);
                } else { //not include text
                    mbtnClearAddress.setVisibility(View.GONE);
                }*/
            }
        });

    }

    public void callSuccessPopUp(final MyProfileActivity myProfileActivity, String description, final boolean isSetPref) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.succuess_pop_up);

        TextView mBtnOk = (TextView) dialog.findViewById(R.id.btn_ok);
        TextView mTvMessage = (TextView) dialog.findViewById(R.id.tv_success_message);
        mTvMessage.setText(getResources().getString(R.string.profile_updated_successfully));

//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!isSetPref)
                    startActivity(new Intent(myProfileActivity, SetPreferrencesActivity.class));
                else
                    myProfileActivity.finish();
               /* mCrossIcon1.setVisibility(View.INVISIBLE);
                mCrossIcon2.setVisibility(View.INVISIBLE);
                mCrossIcon3.setVisibility(View.INVISIBLE);
                mCrossIcon4.setVisibility(View.INVISIBLE);
                mTvSave.setVisibility(View.INVISIBLE);
*/

            }
        });
        dialog.setTitle("Custom Dialog");


        dialog.show();


    }

    @Override
    protected void setUpToolbar() {

    }

    @OnClick({R.id.iv_proile_pic1, R.id.iv_proile_pic2, R.id.iv_proile_pic3, R.id.iv_proile_pic4, R.id.cross_icon1, R.id.cross_icon2, R.id.cross_icon3, R.id.cross_icon4, iv_edit_profile, R.id.tv_lets_start, R.id.tv_save})
    public void onViewClicked(View view) {
        try {
            switch (view.getId()) {
                case R.id.cross_icon1:
//                    mIvProilePic1.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
//                    mCrossIcon1.setVisibility(View.GONE);
//                    mivaddImage1.setVisibility(View.VISIBLE);
                    ApiControllerClass.imageDelete(1, mResponsePojo.getData().getUser_image(), retrofit, MyProfileActivity.this);
                    break;
                case R.id.cross_icon2:
//                    mIvProilePic2.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
//                    mCrossIcon2.setVisibility(View.GONE);
//                    mivaddImage2.setVisibility(View.VISIBLE);
                    ApiControllerClass.imageDelete(2, mResponsePojo.getData().getUser_image(), retrofit, MyProfileActivity.this);

                    break;
                case R.id.cross_icon3:
//                    mIvProilePic3.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
//                    mCrossIcon3.setVisibility(View.GONE);
//                    mivaddImage3.setVisibility(View.VISIBLE);
                    ApiControllerClass.imageDelete(3, mResponsePojo.getData().getUser_image(), retrofit, MyProfileActivity.this);

                    break;
                case R.id.cross_icon4:
//                    mIvProilePic4.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
//                    mCrossIcon4.setVisibility(View.GONE);
//                    mivaddImage4.setVisibility(View.VISIBLE);
                    ApiControllerClass.imageDelete(4, mResponsePojo.getData().getUser_image(), retrofit, MyProfileActivity.this);

                    break;
                case iv_edit_profile:
                    if (mResponsePojo != null && mResponsePojo.getData() != null) {
                        if (mResponsePojo.getData().getUser_image().size() > 0) {
                            mCrossIcon1.setVisibility(View.VISIBLE);
                        } else {
                            mivaddImage1.setVisibility(View.VISIBLE);
                        }
                        if (mResponsePojo.getData().getUser_image().size() > 1)

                        {
                            mCrossIcon2.setVisibility(View.VISIBLE);
                        } else {
                            mivaddImage2.setVisibility(View.VISIBLE);
                        }
                        if (mResponsePojo.getData().getUser_image().size() > 2)

                        {
                            mCrossIcon3.setVisibility(View.VISIBLE);
                        } else {
                            mivaddImage3.setVisibility(View.VISIBLE);
                        }
                        if (mResponsePojo.getData().getUser_image().size() > 3)
                            mCrossIcon4.setVisibility(View.VISIBLE);
                        else {
                            mivaddImage4.setVisibility(View.VISIBLE);
                        }
                    }

                    mTvSave.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_save:
                    // callSuccessPopUp();
                    callSaveUpdateProfile();

                    break;
                case R.id.tv_lets_start:
                    callSaveUpdateProfile();


                    break;
                case R.id.iv_proile_pic1:
                    setCoverImage(0);

                    break;
                case R.id.iv_proile_pic2:
                    setCoverImage(1);


                    break;
                case R.id.iv_proile_pic3:
                    setCoverImage(2);

                    break;
                case R.id.iv_proile_pic4:
                    setCoverImage(3);
                    break;
                default:
                    break;
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setCoverImage(int i) {
        if (i == -1) {
            mImageviewPlaceholder.setImageDrawable(getResources().getDrawable(R.drawable.blue_skyline));
            coverIMageID = "";
        } else if ((mTvSave.getVisibility() == View.VISIBLE || mTvLetsStart.getVisibility() == View.VISIBLE) && mResponsePojo != null && mResponsePojo.getData() != null && mResponsePojo.getData().getUser_image() != null && mResponsePojo.getData().getUser_image().size() > i) {
            Glide.with(this).load(mResponsePojo.getData().getUser_image().get(i).getImage()).into(mImageviewPlaceholder);
            coverIMageID = mResponsePojo.getData().getUser_image().get(i).getId();
        }
    }

    // method for Save Profile
    private void callSaveUpdateProfile() {
        try {
            // check validations for save profile input
            if (Validation.validateUpdateProfile(this, mautocomTextViewCountry, mEtPincode, mEtAbout, mEtCity, mEtAge, mtvName, coverIMageID)) {
                if (Utility.isInternetConnection(this)) {
                    CallProgressWheel.showLoadingDialog(this, "Loading...");
                           /*
                            payload
                            {“country”:”india”
                              "city/state":"chandigarh",
                              "gender":"male",”age”:”20”,”pincode”:”148002”,”about”:”tongy app”
                            }
                            */
                    HashMap<String, String> updateProfilePayload = new HashMap<>();
                    updateProfilePayload.put("country", "101");
                    updateProfilePayload.put("city", mEtCity.getText().toString().trim());
                    updateProfilePayload.put("gender", selGender);
                    updateProfilePayload.put("pincode", mEtPincode.getText().toString().trim());
                    updateProfilePayload.put("about", mEtAbout.getText().toString().trim());
                    updateProfilePayload.put("age", mEtAge.getText().toString().trim());
                    updateProfilePayload.put("name", mtvName.getText().toString().trim());
                    updateProfilePayload.put("image_id", coverIMageID);
                    // updateProfilePayload.put("country_id", "101");
                    if (Global.mLastLocation != null) {

                        updateProfilePayload.put("lat", Global.mLastLocation.getLatitude() + "");
                        updateProfilePayload.put("lng", Global.mLastLocation.getLongitude() + "");
                    } else {
                        updateProfilePayload.put("lat", "0.0");
                        updateProfilePayload.put("lng", "0.0");
                    }
                    Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).updateProfile(updateProfilePayload);
                    posts.enqueue(new Callback<ResponsePojo>() {
                        @Override
                        public void onResponse(Call<ResponsePojo> call, Response<ResponsePojo> response) {
                            if (response.body().getStatus() == 200) {
                                CallProgressWheel.dismissLoadingDialog();
                                PreferenceHandler.writeBoolean(MyProfileActivity.this, PreferenceHandler.IS_PROFILE_SET, true);

                                if (getIntent().hasExtra(Constants.MYPROFILEEDITABLE)) {
                                    callSuccessPopUp(MyProfileActivity.this, response.body().getDescription(), PreferenceHandler.readBoolean(MyProfileActivity.this, PreferenceHandler.IS_PROFILE_SET, false));
                                } else {
                                    Intent intent = null;
                                    if (!PreferenceHandler.readBoolean(MyProfileActivity.this, PreferenceHandler.IS_PREF_SET, false))
                                        intent = new Intent(MyProfileActivity.this, SetPreferrencesActivity.class);
                                    else
                                        intent = new Intent(MyProfileActivity.this, MainActivity.class);

                                    startActivity(intent);
                                    finish();
                                }
                                //Utility.showToastMessageShort(MyProfileActivity.this,responsePojo.getDescription());


                            } else {
                                CallProgressWheel.dismissLoadingDialog();
                                Toast.makeText(MyProfileActivity.this, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponsePojo> call, Throwable t) {
                            CallProgressWheel.dismissLoadingDialog();
                            Toast.makeText(MyProfileActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }
        } catch (Exception e) {
            CallProgressWheel.dismissLoadingDialog();
            Toast.makeText(MyProfileActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        mgenderSpinner.setSelection(position);
        selGender = (String) mgenderSpinner.getSelectedItem();


    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);
        } else if (requestCode == IMAGE_REQUEST_CODE && resultCode == -1) {
            Bitmap bitmap = ImagePicker.getImageFromResult(MyProfileActivity.this, resultCode, data);
            if (bitmap != null) {
                ImagePicker.ImageCropFunction(MyProfileActivity.this);
                outPutFile = ImagePicker.persistImage(bitmap);
                Log.e("outputFile", outPutFile.length() + outPutFile.toString() + "");
                setImageOnPosition(callFrom, bitmap);
            }
        }

        //user is returning frcountriesListom cropping the image
        else if (requestCode == PIC_CROP) {
            //get the returned data
            if (data != null) {
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                Bitmap thePic = ImagePicker.getBitmapFromData(data);
                if (thePic != null) {
                    setImageOnPosition(callFrom, thePic);
                }
                outPutFile = ImagePicker.persistImage(thePic);
            }
            if (outPutFile != null) {
                callUploadPictureApi(outPutFile);
            }
        }

    }

    public void setImageOnPosition(int pos, Bitmap bitmap) {
        if (bitmap != null) {
            if (pos == 1) {
                mIvProilePic1.setImageBitmap(bitmap);
                mCrossIcon1.setVisibility(View.VISIBLE);
                mivaddImage1.setVisibility(View.GONE);
            } else if (pos == 2) {
                mIvProilePic2.setImageBitmap(bitmap);
                mCrossIcon2.setVisibility(View.VISIBLE);
                mivaddImage2.setVisibility(View.GONE);
            } else if (pos == 3) {
                mIvProilePic3.setImageBitmap(bitmap);
                mCrossIcon3.setVisibility(View.VISIBLE);
                mivaddImage3.setVisibility(View.GONE);
            } else if (pos == 4) {
                mIvProilePic4.setImageBitmap(bitmap);
                mCrossIcon4.setVisibility(View.VISIBLE);
                mivaddImage4.setVisibility(View.GONE);
            }
        } else {

            if (pos == 1) {
                mIvProilePic1.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
                mCrossIcon1.setVisibility(View.GONE);
                mivaddImage1.setVisibility(View.VISIBLE);
            } else if (pos == 2) {
                mIvProilePic2.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
                mCrossIcon2.setVisibility(View.GONE);
                mivaddImage2.setVisibility(View.VISIBLE);
            } else if (pos == 3) {
                mIvProilePic3.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
                mCrossIcon3.setVisibility(View.GONE);
                mivaddImage3.setVisibility(View.VISIBLE);
            } else if (pos == 4) {
                mIvProilePic4.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
                mCrossIcon4.setVisibility(View.GONE);
                mivaddImage4.setVisibility(View.VISIBLE);
            }

        }
    }

    private void callUploadPictureApi(final File outPutFile) {
        // check internet connection
        if (Utility.isInternetConnection(this)) {
            Log.e("outputFile==", outPutFile.length() + "===");
            // create RequestBody instance from file
            RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), outPutFile);
            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", outPutFile.getName(), fbody);
            //  RequestBody is_video = RequestBody.create(MediaType.parse("text/plain"), );
            CallProgressWheel.showLoadingDialog(this, "Loading...");
            Call<ResponsePojo> posts = retrofit.create(ApiInterface.class).uploadImage(body);
            posts.enqueue(new Callback<ResponsePojo>() {
                @Override
                public void onResponse(Call<ResponsePojo> call, Response<ResponsePojo> response) {
                    if (response.body().getStatus() == 200) {
                        CallProgressWheel.dismissLoadingDialog();
                        ProfileResponsePojo.UserImageData userImageData = new ProfileResponsePojo.UserImageData();
                        userImageData.setId(response.body().getImage_id());
                        userImageData.setIs_cover_image("0");
                        userImageData.setUser_id(PreferenceHandler.readString(MyProfileActivity.this, PreferenceHandler.PREF_KEY_USER_ID, ""));
                        userImageData.setImage(outPutFile.getAbsolutePath());
                        mResponsePojo.getData().getUser_image().add(userImageData);

                        if(coverIMageID==null || coverIMageID.length()==0)
                        {
                            setCoverImage(mResponsePojo.getData().getUser_image().indexOf(userImageData));
                        }

                        // callSuccessPopUp(MyProfileActivity.this, responsePojo.getDescription());
                        Utility.showToastMessageShort(MyProfileActivity.this, response.body().getDescription());
                    } else if (response.body().getStatus() == 400) {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(MyProfileActivity.this, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                    } else {
                        CallProgressWheel.dismissLoadingDialog();
                        Toast.makeText(MyProfileActivity.this, response.body().getDescription(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponsePojo> call, Throwable t) {
                    CallProgressWheel.dismissLoadingDialog();
                    Toast.makeText(MyProfileActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void countriesList(ArrayList<predictionsCountriesPojo> data) {
        LinearLayoutManager manager = new LinearLayoutManager(MyProfileActivity.this, LinearLayoutManager.VERTICAL, false);
        mrvCountries.setLayoutManager(manager);
        CountriesAdapter mcountriesAdapter = new CountriesAdapter(MyProfileActivity.this, data);
        mrvCountries.setAdapter(mcountriesAdapter);
    }

}








