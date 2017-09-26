package com.igniva.qwer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.views.TextViewBold;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by igniva-andriod-02 on 27/12/16.
 */

public class NoResultsActivity extends BaseActivity {


    @BindView(R.id.textViewBold)
    TextViewBold mTextViewBold;
    @BindView(R.id.tv_SetPreferences)
    TextViewBold mTvSetPreferences;
    @BindView(R.id.llMain)
    LinearLayout mLlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noresults);
        ButterKnife.bind(this);

    }


    @Override
    public void setUpLayout() {
    }

    @Override
    public void setDataInViewObjects() {
    }

    @Override
    protected void setUpToolbar() {

    }


    @OnClick(R.id.tv_SetPreferences)
    public void onViewClicked() {
        startActivity(new Intent(NoResultsActivity.this, SetPreferrencesActivity.class));
        finish();
    }
}

