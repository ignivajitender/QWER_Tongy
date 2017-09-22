package com.igniva.qwer.ui.activities;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.igniva.qwer.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.igniva.qwer.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.igniva.qwer.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.igniva.qwer.ui.adapters.LanguageListAdapter;
import com.igniva.qwer.ui.callbacks.MyCallBack;
import com.igniva.qwer.ui.views.TextViewRegular;
import com.igniva.qwer.utils.Log;
import com.igniva.qwer.utils.fcm.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karanveer on 20/9/17.
 */

public class SetPreferrencesActivity extends BaseActivity implements MyCallBack {


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
    @BindView(R.id.rv_language_speak)
    RecyclerView mRvLanguageSpeak;
    @BindView(R.id.rv_language_to_learn)
    RecyclerView mRvLanguageToLearn;
    Context context = SetPreferrencesActivity.this;
    ArrayList<String> mAlLangListSpeak;
    ArrayList<String> mAlLangListLearn;

    String mLanguageSpeakSelection;
    String mLanguageLearnSelection;
    HashMap<String, String> mHashMapLangSpeak = new HashMap<String, String>();//put in it
    HashMap<String, String> mHashMapLangLearn = new HashMap<String, String>();//put in it
    LanguageListAdapter mAdapterLangSpeak;
    LanguageListAdapter mAdapterLanLearn;
    @BindView(R.id.tv_lets_learn)
    TextView mTvLetsLearn;

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
        mAlLangListSpeak = new ArrayList<>();
        mAlLangListLearn = new ArrayList<>();
        final String[] countries = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.auto_complete_tv_item, R.id.tv_languagename, countries);
        mActvLangISpeak.setAdapter(adapter);
        mActvLangISpeak.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
//                Log.e("prinvalue", i + "   " + selection);
                mLanguageSpeakSelection = selection;
                mAlLangListSpeak.add(selection);
//                Log.e("prinvalue", i + "   " + selection);
//                Toast.makeText(SetPreferrencesActivity.this, "ho gea " + " pos: " + i + selection, Toast.LENGTH_SHORT).show();
                callSetPrefPopUp(mLanguageSpeakSelection, Constants.LANGUAGE_SPEAK);
            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.auto_complete_tv_item, R.id.tv_languagename, countries);
        mActvLangILearn.setAdapter(adapter1);
        mActvLangILearn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
//                Log.e("prinvalue", i + "   " + selection);
                mLanguageSpeakSelection = selection;
                mAlLangListSpeak.add(selection);
//                Log.e("prinvalue", i + "   " + selection);
//                Toast.makeText(SetPreferrencesActivity.this, "ho gea " + " pos: " + i + selection, Toast.LENGTH_SHORT).show();
                callSetPrefPopUp(mLanguageSpeakSelection, Constants.LANGUAGE_LEARN);
            }
        });


        mAdapterLangSpeak = new LanguageListAdapter(context, mHashMapLangSpeak, Constants.LANGUAGE_SPEAK, this);
        mAdapterLanLearn = new LanguageListAdapter(context, mHashMapLangLearn, Constants.LANGUAGE_LEARN, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        mRvLanguageSpeak.setLayoutManager(linearLayoutManager);
        mRvLanguageSpeak.setItemAnimator(new DefaultItemAnimator());
        mRvLanguageSpeak.setAdapter(mAdapterLangSpeak);

        mRvLanguageToLearn.setLayoutManager(linearLayoutManager1);
        mRvLanguageToLearn.setItemAnimator(new DefaultItemAnimator());
        mRvLanguageToLearn.setAdapter(mAdapterLanLearn);
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

    public void callSetPrefPopUp(String selection, String type) {

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
                        mHashMapLangSpeak.put(mAlLangListSpeak.get(mAlLangListSpeak.size() - 1), Constants.BEGINNER);
                        dialog.dismiss();
                        mRvLanguageSpeak.setVisibility(View.VISIBLE);
                        mAdapterLangSpeak.notifyDataSetChanged();

                    }
                });
                mLlintermediate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mHashMapLangSpeak.put(mAlLangListSpeak.get(mAlLangListSpeak.size() - 1), Constants.INTERMEDIATE);
                        dialog.dismiss();
                        mRvLanguageSpeak.setVisibility(View.VISIBLE);
                        mAdapterLangSpeak.notifyDataSetChanged();

                    }
                });
                mLlprofessional.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mHashMapLangSpeak.put(mAlLangListSpeak.get(mAlLangListSpeak.size() - 1), Constants.PROFESSIONAL);
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
                        mHashMapLangLearn.put(mAlLangListLearn.get(mAlLangListLearn.size() - 1), Constants.BEGINNER);
                        dialog.dismiss();
                        mRvLanguageToLearn.setVisibility(View.VISIBLE);
                        mAdapterLanLearn.notifyDataSetChanged();
                    }
                });
                mLlintermediate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mHashMapLangLearn.put(mAlLangListLearn.get(mAlLangListLearn.size() - 1), Constants.INTERMEDIATE);
                        dialog.dismiss();
                        mRvLanguageToLearn.setVisibility(View.VISIBLE);
                        mAdapterLanLearn.notifyDataSetChanged();
                    }
                });
                mLlprofessional.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mHashMapLangLearn.put(mAlLangListLearn.get(mAlLangListLearn.size() - 1), Constants.PROFESSIONAL);
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
    @OnClick({R.id.rb_nearby_me, R.id.rb_world_wide, R.id.iv_male, R.id.iv_female, R.id.tv_lets_learn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_nearby_me:
                mLlNearbyLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_world_wide:
                mLlNearbyLayout.setVisibility(View.GONE);
                break;
            case R.id.iv_male:
                mIvMale.setImageDrawable(getResources().getDrawable(R.drawable.male_selected));
                mIvFemale.setImageDrawable(getResources().getDrawable(R.drawable.female_unselected));
                break;
            case R.id.iv_female:
                mIvMale.setImageDrawable(getResources().getDrawable(R.drawable.male_unselected));
                mIvFemale.setImageDrawable(getResources().getDrawable(R.drawable.female_selected));
                break;
            case R.id.tv_lets_learn:
             Intent intent=new Intent(SetPreferrencesActivity.this,MyProfileActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void removeItemFromHashMap(String type, String hashmap_key, int position) {
        switch (type) {
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
    }
}
