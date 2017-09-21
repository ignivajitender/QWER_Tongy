package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.igniva.qwer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by igniva-android-31 on 8/6/17.
 */

public class AllPopUps extends AppCompatActivity {


    @BindView(R.id.btn_set_prefernce)
    Button mBtnSetPrefernce;
    @BindView(R.id.btn_remove_post)
    Button mBtnRemovePost;
    @BindView(R.id.btn_report_post)
    Button mBtnReportPost;
    @BindView(R.id.btn_delete_account)
    Button mBtnDeleteAccount;
    @BindView(R.id.btn_verify_pass)
    Button mBtnVerifyPass;
    @BindView(R.id.btn_repost_user)
    Button mBtnRepostUser;
    @BindView(R.id.btn_success)
    Button mBtnSuccess;
    @BindView(R.id.btn_thanks)
    Button mBtnThanks;
    @BindView(R.id.btn_activation)
    Button mBtnActivation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_dialog);  // show dialog for popups and additional_activity for 1st additionalscreen
        ButterKnife.bind(this);

    }


//    public void createCancellationpolicy() {
//
//        // Create custom dialog object
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.setContentView(R.layout.cancellation_policy_popup);
////        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//
//
//        dialog.setTitle("Custom Dialog");
//
//
//        dialog.show();
//
//
//    }
//
//    public void createDisputePolicy() {
//
//        // Create custom dialog object
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.setContentView(R.layout.dispute_policy);
////        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//
//
//        dialog.setTitle("Custom Dialog");
//
//
//        dialog.show();
//
//
//    }
//
//    public void createDisputeRequest() {
//
//        // Create custom dialog object
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.setContentView(R.layout.raise_dispute_pop_up);
////        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//
//
//        dialog.setTitle("Custom Dialog");
//
//
//        dialog.show();
//
//
//    }
//
//    public void createDriversNote() {
//
//        // Create custom dialog object
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.setContentView(R.layout.drivers_notes);
////        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//
//
//        dialog.setTitle("Custom Dialog");
//
//
//        dialog.show();
//
//
//    }
//
//    public void createRatingPopUp() {
//
//        // Create custom dialog object
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.setContentView(R.layout.rating_popup);
////        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//
//
//        dialog.setTitle("Custom Dialog");
//
//
//        dialog.show();
//
//
//    }
//
//    public void createYournote() {
//
//        // Create custom dialog object
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.setContentView(R.layout.yournote_pop_up);
////        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//
//
//        dialog.setTitle("Custom Dialog");
//
//
//        dialog.show();
//
//
//    }
//
//    public void createSuccessPopUP() {
//
//        // Create custom dialog object
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.setContentView(R.layout.success_pop_up);
////        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//
//
//        dialog.setTitle("Custom Dialog");
//
//
//        dialog.show();
//
//
//    }
//
//
    @OnClick({R.id.btn_set_prefernce, R.id.btn_remove_post, R.id.btn_report_post, R.id.btn_delete_account, R.id.btn_verify_pass, R.id.btn_repost_user, R.id.btn_success, R.id.btn_thanks, R.id.btn_activation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_set_prefernce:
                break;
            case R.id.btn_remove_post:
                break;
            case R.id.btn_report_post:
                break;
            case R.id.btn_delete_account:
                break;
            case R.id.btn_verify_pass:
                break;
            case R.id.btn_repost_user:
                break;
            case R.id.btn_success:
                break;
            case R.id.btn_thanks:
                break;
            case R.id.btn_activation:
                break;
        }
    }
}

