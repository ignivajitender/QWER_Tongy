package com.igniva.qwer.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.igniva.qwer.R;
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class CreateTeachingPostActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;
    @BindView(R.id.tv_tap_to_rename)
    TextView mtvToolbartitle;
    @BindView(R.id.ivbackIcon)
    ImageView mIvbackIcon;
    @BindView(R.id.ll_change_title)
    LinearLayout mLlChangeTitle;
    @BindView(R.id.toolbar_top)
    Toolbar mToolbarTop;
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_description)
    EditText mEtDescription;
    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.et_start_time)
    EditText mEtStartTime;
    @BindView(R.id.et_end_time)
    EditText mEtEndTime;

    @BindView(R.id.ll_post_now)
    LinearLayout mLlPostNow;

    @OnClick(R.id.ivbackIcon)
    public void back() {
        onBackPressed();
    }
    @OnClick(R.id.et_schedule_start_date)
    public void open() {
       showDialog(metScheduleStartDate);
    }
    @OnClick(R.id.et_schedule_end_date)
    public void open1() {
        showDialog(metScheduleEndDate);
    }


    @OnClick(R.id.et_start_time)
    public void openTime() {
        showDialogTime(mEtStartTime);
    }
    @OnClick(R.id.et_end_time)
    public void openTime1() {
        showDialogTime(mEtEndTime);
    }


    @BindView(R.id.rlAddress)
    RelativeLayout mrlAddress;
    @OnClick(R.id.rb_online)
    public void hideAddress(){
        if(mrlAddress.getVisibility()==View.VISIBLE)
            mrlAddress.setVisibility(View.GONE);

        typeOfClass="online";
    }

   @OnClick(R.id.rb_physical)
    public void showAddress(){
       mrlAddress.setVisibility(View.VISIBLE);
       typeOfClass="physical";
    }

    @BindView(R.id.autocomTextViewAddress)
    AutoCompleteTextView mautocomTextViewAddress;

    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Gson gson;
    String typeOfClass="";


    @OnClick(R.id.ivLocation)
    public void openLocation(){
        changeLocation();



    }


    private void showDialogTime(final EditText mEditText) {
        // Get Current time
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTeachingPostActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        mEditText.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    @OnClick(R.id.tvPostNow)
    public void post(){
       // call api to create teachinbg post
        ApiControllerClass.createTeachingPostApi(CreateTeachingPostActivity.this,retrofit,mEtTitle,mEtDescription,mEtPrice,metScheduleStartDate,metScheduleEndDate,mEtStartTime,mEtEndTime,typeOfClass);
    }

    @BindView(R.id.et_schedule_start_date)
    EditText metScheduleStartDate;
    @BindView(R.id.et_schedule_end_date)
    EditText metScheduleEndDate;


    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global) getApplication()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teaching_post);

        ButterKnife.bind(this);
        setUpToolbar();
        setUpLayout();
        setDataInViewObjects();
    }

    @Override
    protected void setUpLayout() {
        mautocomTextViewAddress.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 2) {
                    // call google place api to fetch addresses
                    Utility.callGoogleApi(CreateTeachingPostActivity.this, mautocomTextViewAddress, "", okHttpClient, gson);
                }


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

    @Override
    protected void setDataInViewObjects() {


    }

    public void showDialog(EditText ed) {
        final EditText e = ed;
        ed.setFocusable(false);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                DatePickerDialog dpd = new DatePickerDialog(CreateTeachingPostActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        e.setText(dayOfMonth + " - " + (monthOfYear + 1) + " - " + year);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
    }

    private void updateLabel(EditText metScheduleStartDate) {
        String myFormat = "dd-MM-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        metScheduleStartDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void setUpToolbar() {
        mtvToolbartitle.setText(getResources().getString(R.string.create_teaching_post));
    }


}
