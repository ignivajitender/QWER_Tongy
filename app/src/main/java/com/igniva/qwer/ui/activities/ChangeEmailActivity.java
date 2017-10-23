package com.igniva.qwer.ui.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.views.TextViewBold;
import com.igniva.qwer.utils.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karanveer on 22/9/17.
 */

public class ChangeEmailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_change_email)
    TextViewBold mTvChangeEmail;
    @BindView(R.id.et_current_email)
    EditText etCurrentEmail;
    @BindView(R.id.et_new_email)
    EditText etNewEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_email_activity);
        ButterKnife.bind(this);


    }

    @Override
    protected void setUpLayout() {

    }

    @Override
    protected void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }

    public void callVerifyPassPopUp(final EditText etCurrentEmail, final EditText etNewEmail) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this,
                R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(R.layout.update_email_verify_pass_pop_up);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button btn_confirm = (Button) dialog.findViewById(R.id.btn_confirm);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final EditText et_verify_password = (EditText) dialog.findViewById(R.id.et_verify_password);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                    callChangeEmailApi(etCurrentEmail, etNewEmail,et_verify_password);

            }
        });

        dialog.setTitle("Custom Dialog");


        dialog.show();


    }

    /**
     * Call api to change the current email
     */

    private void callChangeEmailApi(EditText currentEmail, EditText etCurrentEmail, EditText etNewEmail) {
    }


    @OnClick({R.id.iv_back, R.id.tv_change_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_change_email:
                if (Validation.validateNewEmail(ChangeEmailActivity.this, etCurrentEmail, etNewEmail)) {
                    callVerifyPassPopUp(etCurrentEmail, etNewEmail);
                }

                break;
        }
    }
}
