package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.OtherUserProfilePojo;
import com.igniva.qwer.ui.adapters.LanguageAdapter;
import com.igniva.qwer.ui.adapters.LanguageSpeakAdapter;
import com.igniva.qwer.ui.adapters.MultiImages;
import com.igniva.qwer.ui.views.TextViewLight;
import com.igniva.qwer.utils.DepthPageTransformer;
import com.igniva.qwer.utils.FieldValidators;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.GridSpacingItemDecoration;
import com.igniva.qwer.utils.Utility;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OtherUserProfileActivity extends BaseActivity {


    @BindView(R.id.ivbackIcon)
    ImageView mivbackIcon;
    @BindView(R.id.ivDotIcon)
    ImageView mivDotIcon;
    @BindView(R.id.toolbar_top)
    Toolbar mtoolbarTop;
    @BindView(R.id.iv_user_image)
    ViewPager mivUserImage;
    @BindView(R.id.dotsLayout)
    LinearLayout mdotsLayout;

    @Inject
    Retrofit retrofit;
    MultiImages multiImages;
    ArrayList mImagesSlidingArray;
    public int NUM_PAGES = 4;
    boolean isOpaque = true;
    @BindView(R.id.iv_videoCall)
    ImageButton ivVideoCall;
    @BindView(R.id.iv_voiceCall)
    ImageButton ivVoiceCall;
    @BindView(R.id.iv_message)
    ImageButton ivMessage;
    @BindView(R.id.tvName)
    TextView mtvName;
    @BindView(R.id.tv_age_heading)
    TextViewLight tvAgeHeading;
    @BindView(R.id.tv_age)
    TextViewLight tvAge;
    @BindView(R.id.tv_radius)
    TextViewLight tvRadius;
    @BindView(R.id.languageSpeak)
    TextView mtvlanguageSpeak;
    @BindView(R.id.rvLanguageSpeaks)
    RecyclerView mrvLanguageSpeaks;
    @BindView(R.id.tvInterestedIn)
    TextView mtvInterestedIn;
    @BindView(R.id.rvInterestedIn)
    RecyclerView mrvInterestedIn;
    @BindView(R.id.tvAbout)
    TextView mtvAbout;
    @BindView(R.id.tvAboutDetails)
    TextView mtvAboutDetails;
    @BindView(R.id.tvFrom)
    TextView mtvFrom;
    @BindView(R.id.tvFromDetails)
    TextView mtvFromDetails;
    private int userId;
    private int size;

    @OnClick(R.id.ivbackIcon)
    public void back(){
        Utility.hideSoftKeyboard(OtherUserProfileActivity.this);
        onBackPressed();
    }
    @OnClick(R.id.ivDotIcon)
    public void openOptionsMenu(){
        final PopupWindow popup = new PopupWindow(OtherUserProfileActivity.this);
        View layout = LayoutInflater.from(OtherUserProfileActivity.this).inflate(R.layout.layout_options_popup, null);
        //popup.showAtLocation(layout, Gravity.RIGHT, 0, 0);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);



        popup.setBackgroundDrawable(new BitmapDrawable());

        LinearLayout layout_block = (LinearLayout) layout.findViewById(R.id.layout_block);
        LinearLayout layout_report = (LinearLayout) layout.findViewById(R.id.layout_report);
        LinearLayout layout_remove = (LinearLayout) layout.findViewById(R.id.layout_remove);
        final TextView mtvBlockUnblock=(TextView)layout.findViewById(R.id.tv_block);
/*

        if(text.equalsIgnoreCase("") && text.equalsIgnoreCase("block"))
            mtvBlockUnblock.setText("Block");
        else
            mtvBlockUnblock.setText("Unblock");
*/

        layout_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popup.dismiss();
                ApiControllerClass.callblockApi(OtherUserProfileActivity.this,retrofit,userId,popup,mtvBlockUnblock);
            }
        });
        layout_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
                ApiControllerClass.callUserAction(OtherUserProfileActivity.this,retrofit,getResources().getString(R.string.delete_action),popup,userId);
            }
        });
        layout_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReportUserPopup(userId,popup);

            }
        });

        popup.showAsDropDown(mivDotIcon);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpLayout();
        setDataInViewObjects();
        ApiControllerClass.getOtherUserProfile(retrofit,OtherUserProfileActivity.this, userId);
    }


    @Override
    protected void setUpLayout() {
        if(getIntent()!=null && getIntent().hasExtra("userId"))
            userId = getIntent().getIntExtra("userId",0);
        Log.e("userId",userId+"");

        mivUserImage.setAdapter(multiImages);
        mivUserImage.setPageTransformer(true, new DepthPageTransformer());


    }

    @Override
    protected void setDataInViewObjects() {
        mivUserImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == NUM_PAGES - 1 && positionOffset > 0) {
                    if (isOpaque) {
                        mivUserImage.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        mivUserImage.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    @Override
    protected void setUpToolbar() {

    }

    public void buildCircles(int size) {
        try {
            float scale = getResources().getDisplayMetrics().density;
            int padding = (int) (5 * scale + 0.5f);


            for (int i = 0; i < size; i++) {
                ImageView circle = new ImageView(this);
                circle.setImageResource(R.drawable.login_checkbox);
                circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                circle.setAdjustViewBounds(true);
                circle.setPadding(padding, 0, padding, 0);
                mdotsLayout.addView(circle);
            }

            setIndicator(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIndicator(int index) {
        if (index < size) {
            for (int i = 0; i < size; i++) {
                ImageView circle = (ImageView) mdotsLayout.getChildAt(i);
                if (i == index) {
                    circle.setColorFilter(getResources().getColor(android.R.color.white));
                } else {
                    circle.setColorFilter(getResources().getColor(R.color.text_medium_grey));
                }
            }
        }
    }

    public void setData(Response<OtherUserProfilePojo> response){

        mtvAboutDetails.setText(response.body().getUsers().getAbout());
        if(response.body().getUsers().getAge()!=null)
        tvAge.setText(response.body().getUsers().getAge()+" Years");
        mtvName.setText(response.body().getUsers().getName());
        //tvRadius.setText(response.body().getUsers().get);

        if(response.body().getUsers().getIs_videocall()==1)
            ivVideoCall.setVisibility(View.VISIBLE);
        else
            ivVideoCall.setVisibility(View.GONE);
        if(response.body().getUsers().getIs_voicecall()==1)
            ivVoiceCall.setVisibility(View.VISIBLE);
        else
            ivVoiceCall.setVisibility(View.GONE);


        GridLayoutManager linearLayoutManager = new GridLayoutManager(OtherUserProfileActivity.this,3);
        mrvInterestedIn.setLayoutManager(linearLayoutManager);
        mrvInterestedIn.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));

        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(OtherUserProfileActivity.this,3);
        mrvLanguageSpeaks.setLayoutManager(linearLayoutManager1);
        mrvLanguageSpeaks.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));


        mrvInterestedIn.setVisibility(View.VISIBLE);
        mrvLanguageSpeaks.setVisibility(View.VISIBLE);
        LanguageSpeakAdapter adapter = new LanguageSpeakAdapter(OtherUserProfileActivity.this, response.body().getUsers().getUser_learn());
        mrvInterestedIn.setAdapter(adapter);

        LanguageAdapter adapter1 = new LanguageAdapter(OtherUserProfileActivity.this, response.body().getUsers().getUser_speak());
        mrvLanguageSpeaks.setAdapter(adapter1);

        if(response.body().getUsers().getUser_image()!=null && response.body().getUsers().getUser_image().size()>0) {
        multiImages = new MultiImages(OtherUserProfileActivity.this, response.body().getUsers().getUser_image(),response.body().getUsers().getUser_image().get(0).getImage());
        mivUserImage.setAdapter(multiImages);
        buildCircles(response.body().getUsers().getUser_image().size());
        size = response.body().getUsers().getUser_image().size();
        }
        else
        {
           mivUserImage.setBackgroundResource(R.drawable.login_bg);
        }

    }


    /**
     * report user pop up
     * @param userId
     */
    private void openReportUserPopup(final int userId,final PopupWindow popupMenu) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(OtherUserProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.layout_report_abuse);
        final TextView mtvDialogTitle=(TextView)dialog.findViewById(R.id.tvDialogTitle);
        mtvDialogTitle.setText(getResources().getString(R.string.report_user));
        final EditText metReason = (EditText) dialog.findViewById(R.id.etReason);
        final EditText metComment = (EditText) dialog.findViewById(R.id.et_comment);
        metReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu dropDownMenu = new PopupMenu(OtherUserProfileActivity.this, metReason);
                dropDownMenu.getMenuInflater().inflate(R.menu.drop_down_menu, dropDownMenu.getMenu());
                //showMenu.setText("DropDown Menu");
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //Toast.makeText(mContext, "You have clicked " + menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        metReason.setText(menuItem.getTitle());
                        metReason.setError(null);
                        return true;
                    }
                });
                dropDownMenu.show();
            }
        });
        TextView mBtnOk = (TextView) dialog.findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metReason.setError(null);
                metComment.setError(null);
                if (FieldValidators.isNullOrEmpty(metReason)) {
                    metReason.setFocusable(true);
                    metReason.requestFocus();
                    metReason.setError("Please select reason for report");
                    return;
                } else if (FieldValidators.isNullOrEmpty(metComment)) {
                    metComment.setFocusable(true);
                    metComment.requestFocus();
                    metComment.setError("Please enter your comment");
                    return;
                } else {
                    dialog.dismiss();
                    popupMenu.dismiss();
                    ApiControllerClass.callReportUserApi(OtherUserProfileActivity.this, retrofit, metReason, metComment, userId, dialog);
                }
                //((Activity)mContext).finish();
            }
        });

        TextView mbtnCancel = (TextView) dialog.findViewById(R.id.btn_cancel);
        mbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //((Activity)mContext).finish();
            }
        });
//         dialog.setTitle("Custom Dialog");
        dialog.show();
    }
}
