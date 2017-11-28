package com.igniva.qwer.ui.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.LanguagesResponsePojo;
import com.igniva.qwer.model.PrefInputPojo;
import com.igniva.qwer.ui.adapters.LanguageListAdapter;
import com.igniva.qwer.ui.callbacks.MyCallBack;
import com.igniva.qwer.ui.views.TextViewRegular;
import com.igniva.qwer.utils.Constants;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Log;
import com.igniva.qwer.utils.Utility;
import com.igniva.qwer.utils.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.igniva.qwer.utils.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.igniva.qwer.utils.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.igniva.qwer.utils.crystalrangeseekbar.widgets.CrystalSeekbar;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

/**
 * Created by karanveer on 20/9/17.
 */

public class SetPreferrencesActivity extends BaseActivity implements MyCallBack {


    @BindView(R.id.actv_lang_i_speak)
    public
    AutoCompleteTextView mActvLangISpeak;
    @BindView(R.id.actv_lang_i_learn)
    public
    AutoCompleteTextView mActvLangILearn;
    public ArrayList<LanguagesResponsePojo.LanguagesPojo> mAlLangList;
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
    @BindView(R.id.rv_language_speak)
    RecyclerView mRvLanguageSpeak;
    @BindView(R.id.rv_language_to_learn)
    RecyclerView mRvLanguageToLearn;
    Context context = SetPreferrencesActivity.this;
    ArrayList<PrefInputPojo.LanguagesProficiency> mAlLangListSpeak;
    ArrayList<PrefInputPojo.LanguagesProficiency> mAlLangListLearn;
    LanguageListAdapter mAdapterLangSpeak;
    LanguageListAdapter mAdapterLanLearn;
    @BindView(R.id.tv_lets_learn)
    TextView mTvLetsLearn;
    Boolean isEditable = false;
    @BindView(R.id.tv_start_looking_again)
    TextView mTvStartLookingAgain;

    @Inject
    Retrofit retrofit;

    String mGender = "male", mAreaType = "worldwide";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        ((Global) getApplicationContext()).getNetComponent().inject(this);
        setContentView(R.layout.setpreferrences);
        ButterKnife.bind(this);
        setupSeekbarListerns();
        setUpLayout();
    }

    @Override
    protected void setUpLayout() {
        ApiControllerClass.getLanguages(this, retrofit);
        if (getIntent().hasExtra(Constants.TO_EDIT_PREFERENCES)) {
            if (getIntent().getStringExtra(Constants.TO_EDIT_PREFERENCES).equalsIgnoreCase("Yes")) {
                isEditable = true;
                mTvStartLookingAgain.setVisibility(View.VISIBLE);
                mTvLetsLearn.setVisibility(View.GONE);
            } else {
                mTvStartLookingAgain.setVisibility(View.VISIBLE);
                mTvLetsLearn.setVisibility(View.GONE);
            }

        }
        mAlLangListSpeak = new ArrayList<>();
        mAlLangListLearn = new ArrayList<>();

        mActvLangISpeak.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                onLangItemClick(mActvLangISpeak, selection, Constants.LANGUAGE_SPEAK);
            }
        });

        mActvLangILearn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                Log.e("prinvalue", i + "   " + selection);
                onLangItemClick(mActvLangILearn, selection, Constants.LANGUAGE_LEARN);

            }
        });


        mAdapterLangSpeak = new LanguageListAdapter(context, mAlLangListSpeak, Constants.LANGUAGE_SPEAK, this);
        mAdapterLanLearn = new LanguageListAdapter(context, mAlLangListLearn, Constants.LANGUAGE_LEARN, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        mRvLanguageSpeak.setLayoutManager(linearLayoutManager);
        mRvLanguageSpeak.setItemAnimator(new DefaultItemAnimator());
        mRvLanguageSpeak.setAdapter(mAdapterLangSpeak);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRvLanguageSpeak.getContext(),linearLayoutManager.getOrientation());
//        mRvLanguageSpeak.addItemDecoration(dividerItemDecoration);


        mRvLanguageToLearn.setLayoutManager(linearLayoutManager1);
        mRvLanguageToLearn.setItemAnimator(new DefaultItemAnimator());
        mRvLanguageToLearn.setAdapter(mAdapterLanLearn);
    }

    private synchronized void onLangItemClick(AutoCompleteTextView autoCompleteTextView, String selection, String languageType) {
        try {
            PrefInputPojo.LanguagesProficiency tempPojo = new PrefInputPojo().new LanguagesProficiency();
            tempPojo.setProficiency(Constants.BEGINNER);
            tempPojo.setName(selection);
            if (languageType.equals(Constants.LANGUAGE_LEARN))
                for (PrefInputPojo.LanguagesProficiency tPojo : mAlLangListLearn
                        ) {
                    if (tPojo.getName().equals(selection)) {
                        mAlLangListLearn.remove(tPojo);
                        break;
                    }
                }
            else
                for (PrefInputPojo.LanguagesProficiency tPojo : mAlLangListSpeak
                        ) {
                    if (tPojo.getName().equals(selection)) {
                        mAlLangListSpeak.remove(tPojo);
                        break;
                    }
                }
            for (LanguagesResponsePojo.LanguagesPojo lanPojo : mAlLangList
                    ) {
                if (selection.equals(lanPojo.getName())) {
                    tempPojo.setLanguage_id(lanPojo.getId() + "");
                    break;
                }
            }
            if (languageType.equals(Constants.LANGUAGE_LEARN))
                mAlLangListLearn.add(tempPojo);
            else
                mAlLangListSpeak.add(tempPojo);

            callSetPrefPopUp(selection, languageType);
            autoCompleteTextView.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setupSeekbarListerns() {
        mSeekbarNearbyArea.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                if (!mTvNearbyAreaValue.getText().toString().replace("km","").equals(value.toString())) {
                    int x = (int) mSeekbarNearbyArea.getLeftThumbRect().left;
                    mTvNearbyAreaValue.setX(x);
                    Log.e("valuepfx", x + "");
                    mTvNearbyAreaValue.setText("" + value + "km");
                }
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
                    mTvPreferredAgeMax.setText(maxValue + "");
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

    public void callSetPrefPopUp(String selection, final String type) {

        try {
            // Create custom dialog object
            final Dialog dialog = new Dialog(SetPreferrencesActivity.this,
                    R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.setContentView(R.layout.set_preferences_pop_up);


            TextViewRegular mTv_lan_name = (TextViewRegular) dialog.findViewById(R.id.tv_lang_name);
            mTv_lan_name.setText(selection);
            LinearLayout mLlbeginer = (LinearLayout) dialog.findViewById(R.id.ll_beginner);
            LinearLayout mLlintermediate = (LinearLayout) dialog.findViewById(R.id.ll_intermediate);
            LinearLayout mLlprofessional = (LinearLayout) dialog.findViewById(R.id.ll_professional);

            switch (type) {
                case Constants.LANGUAGE_SPEAK:
                    mLlbeginer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAlLangListSpeak.get(mAlLangListSpeak.size() - 1).setProficiency(Constants.BEGINNER);
                            dialog.dismiss();
                            mRvLanguageSpeak.setVisibility(View.VISIBLE);
                            mAdapterLangSpeak.notifyDataSetChanged();
                        }
                    });
                    mLlintermediate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAlLangListSpeak.get(mAlLangListSpeak.size() - 1).setProficiency(Constants.INTERMEDIATE);
                            dialog.dismiss();
                            mRvLanguageSpeak.setVisibility(View.VISIBLE);
                            mAdapterLangSpeak.notifyDataSetChanged();
                        }
                    });
                    mLlprofessional.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAlLangListSpeak.get(mAlLangListSpeak.size() - 1).setProficiency(Constants.PROFESSIONAL);
                            dialog.dismiss();
                            mRvLanguageSpeak.setVisibility(View.VISIBLE);
                            mAdapterLangSpeak.notifyDataSetChanged();
                        }
                    });
                    break;
                case Constants.LANGUAGE_LEARN:
                    mLlbeginer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAlLangListLearn.get(mAlLangListLearn.size() - 1).setProficiency(Constants.BEGINNER);
                            dialog.dismiss();
                            mRvLanguageToLearn.setVisibility(View.VISIBLE);
                            mAdapterLanLearn.notifyDataSetChanged();
                        }
                    });
                    mLlintermediate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAlLangListLearn.get(mAlLangListLearn.size() - 1).setProficiency(Constants.INTERMEDIATE);
                            dialog.dismiss();
                            mRvLanguageToLearn.setVisibility(View.VISIBLE);
                            mAdapterLanLearn.notifyDataSetChanged();
                        }
                    });
                    mLlprofessional.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAlLangListLearn.get(mAlLangListLearn.size() - 1).setProficiency(Constants.PROFESSIONAL);
                            dialog.dismiss();
                            mRvLanguageToLearn.setVisibility(View.VISIBLE);
                            mAdapterLanLearn.notifyDataSetChanged();
                        }
                    });
                    break;
            }
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.setTitle("Custom Dialog");
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /* public void removeItemFromHashMap(String type,String hashmap_key,int position){

         switch (type){
             case Constants.LANGUAGE_SPEAK:
                 mHashMapLangSpeak.remove(hashmap_key);
                 mAdapterLangSpeak.notifyItemRemoved(position);
 //                mAdapterLangSpeak.notifyItemRemoved(position);
                 break;
             case Constants.LANGUAGE_LEARN:
                 mHashMapLangLearn.remove(hashmap_key);
                 mAdapterLanLearn.notifyItemChanged(position);
                 break;
         }
     }*/

    @OnClick({R.id.rb_nearby_me, R.id.rb_world_wide, R.id.iv_male, R.id.iv_female, R.id.tv_lets_learn, R.id.tv_start_looking_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_nearby_me:
                mAreaType = "nearby";
                mLlNearbyLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_world_wide:
                mAreaType = "worldwide";
                mLlNearbyLayout.setVisibility(View.GONE);
                break;
            case R.id.iv_male:
                mGender = "male";
                mIvMale.setImageDrawable(getResources().getDrawable(R.drawable.male_selected));
                mIvFemale.setImageDrawable(getResources().getDrawable(R.drawable.female_unselected));
                break;
            case R.id.iv_female:
                mGender = "female";
                mIvMale.setImageDrawable(getResources().getDrawable(R.drawable.male_unselected));
                mIvFemale.setImageDrawable(getResources().getDrawable(R.drawable.female_selected));
                break;
            case R.id.tv_lets_learn:
                if (setPref()) {
                    Intent intent = new Intent(SetPreferrencesActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_start_looking_again:
                if (setPref()) {
                    Intent intentlookagain = new Intent(SetPreferrencesActivity.this, MainActivity.class);
                    startActivity(intentlookagain);
                }
                break;
        }
    }

    Boolean setPref() {


        if (mAlLangListSpeak == null || mAlLangListSpeak.size() == 0) {
            Utility.showToastMessageLong((Activity) context, context.getResources().getString(R.string.select_atleast_one_speak));
        } else if (mAlLangListSpeak.size() > 4) {
            Utility.showToastMessageLong((Activity) context, context.getResources().getString(R.string.select_speak_limit));
        } else if (mAlLangListLearn == null || mAlLangListLearn.size() == 0) {
            Utility.showToastMessageLong((Activity) context, context.getResources().getString(R.string.select_atleast_one_learn));
        } else if (mAlLangListLearn.size() > 6) {
            Utility.showToastMessageLong((Activity) context, context.getResources().getString(R.string.select_learn_limit));
        } else {
            PrefInputPojo prefInputPojo = new PrefInputPojo();
            prefInputPojo.setLearn(mAlLangListLearn);
            prefInputPojo.setPrefered_age_from(Integer.valueOf(mTvPreferredAgeMin.getText().toString().trim()));
            prefInputPojo.setPrefered_age_to(Integer.valueOf(mTvPreferredAgeMax.getText().toString().replace("+", "").trim()));
            prefInputPojo.setPrefered_gender(mGender.toLowerCase().trim());
            prefInputPojo.setSpeak(mAlLangListSpeak);
            prefInputPojo.setPrefered_area_type(mAreaType);
            if (mAreaType.equals("nearby")) {
                prefInputPojo.setArea_km(Integer.valueOf(mTvNearbyAreaValue.getText().toString().replace("km", "").trim()));
            }
            ApiControllerClass.setPrefrences(context, retrofit, prefInputPojo);
            return true;
        }
        return false;
    }

    @Override
    public void removeItem(String type, int pos) {
        switch (type) {
            case Constants.LANGUAGE_SPEAK:
                mAlLangListSpeak.remove(pos);
//                mAdapterLangSpeak.notifyItemRemoved(position);
                mAdapterLangSpeak.notifyDataSetChanged();
                if (mAlLangListSpeak.size() == 0) {
                    mRvLanguageSpeak.setVisibility(View.GONE);
                }
//                mAdapterLangSpeak.notifyItemRemoved(position);
                break;
            case Constants.LANGUAGE_LEARN:
                mAlLangListLearn.remove(pos);
                mAdapterLanLearn.notifyDataSetChanged();
                if (mAlLangListLearn.size() == 0) {
                    mRvLanguageToLearn.setVisibility(View.GONE);
                }
                //
                break;
        }

    }
}
