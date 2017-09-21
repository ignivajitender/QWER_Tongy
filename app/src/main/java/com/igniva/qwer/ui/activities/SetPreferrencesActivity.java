package com.igniva.qwer.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.igniva.qwer.R;
import com.igniva.qwer.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.igniva.qwer.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.igniva.qwer.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.igniva.qwer.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.igniva.qwer.ui.views.TextViewRegular;
import com.igniva.qwer.utils.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karanveer on 20/9/17.
 */

public class SetPreferrencesActivity extends BaseActivity {


    @BindView(R.id.iv_male)
    ImageView mIvMale;
    @BindView(R.id.iv_female)
    ImageView mIvFemale;
    @BindView(R.id.seekbar_preferred_age)
    CrystalRangeSeekbar mSeekbarPreferredAge;
    @BindView(R.id.tv_preferred_age_min)
    TextViewRegular mTvPreferredAgeMin;
    @BindView(R.id.tv_preferred_age_max)
    TextViewRegular mTvPreferredAgeMax;
    @BindView(R.id.rb_nearby_me)
    RadioButton mRbNearbyMe;
    @BindView(R.id.rb_world_wide)
    RadioButton mRbWorldWide;
    @BindView(R.id.rbg_preferred_area)
    RadioGroup mRbgPreferredArea;
    @BindView(R.id.seekbar_nearby_area)
    CrystalSeekbar mSeekbarNearbyArea;
    @BindView(R.id.tv_nearby_area_value)
    TextViewRegular mTvNearbyAreaValue;
    @BindView(R.id.ll_nearby_layout)
    LinearLayout mLlNearbyLayout;
    @BindView(R.id.actv_lang_i_speak)
    AutoCompleteTextView mActvLangISpeak;
    @BindView(R.id.actv_lang_i_learn)
    AutoCompleteTextView mActvLangILearn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpreferrences);
        ButterKnife.bind(this);
        setupSeekbarListerns();
        setUpLayout();
    }

    @Override
    protected void setUpLayout() {
        final String[] countries = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.auto_complete_tv_item,R.id.tv_languagename, countries);
        mActvLangISpeak.setAdapter(adapter);
        mActvLangILearn.setAdapter(adapter);
        mActvLangISpeak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mActvLangILearn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String)adapterView.getItemAtPosition(i);
                Toast.makeText(SetPreferrencesActivity.this, "ho gea "+" pos: "+i+selection, Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void setupSeekbarListerns() {
        mSeekbarNearbyArea.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                int x = (int) mSeekbarNearbyArea.getLeftThumbRect().left;
                Log.e("valuepfx", x + "");
                mTvNearbyAreaValue.setX(x);
                mTvNearbyAreaValue.setText("" + value + " km");
            }
        });
        mSeekbarPreferredAge.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                int y = (int) mSeekbarPreferredAge.getLeftThumbRect().left;
                int x = (int) mSeekbarPreferredAge.getRightThumbRect().left;


                mTvPreferredAgeMin.setText("" + minValue);
                int max_age = maxValue.intValue();
                Log.e("print value", max_age + "");
                ;
                Log.e("valuejhjffjfj" +
                        "pfx", x + "");
                mTvPreferredAgeMax.setX(x);
                mTvPreferredAgeMin.setX(y);
                if (max_age == 66) {

                    mTvPreferredAgeMax.setText("65+");
                } else {
                    mTvPreferredAgeMax.setText(maxValue + " ");
                }
            }
        });
    }

    @Override
    protected void setDataInViewObjects() {

    }

    @Override
    protected void setUpToolbar() {

    }

    @OnClick({R.id.rb_nearby_me, R.id.rb_world_wide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_nearby_me:
                mLlNearbyLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_world_wide:
                mLlNearbyLayout.setVisibility(View.GONE);
                break;
        }
    }
}
