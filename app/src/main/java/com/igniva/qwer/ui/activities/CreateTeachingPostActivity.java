package com.igniva.qwer.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.igniva.qwer.R;
import com.igniva.qwer.utils.Global;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.et_current_email)
    EditText mEtCurrentEmail;
    @BindView(R.id.et_description)
    EditText mEtDescription;
    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.et_start_time)
    EditText mEtStartTime;
    @BindView(R.id.et_end_time)
    EditText mEtEndTime;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.ll_change_email)
    LinearLayout mLlChangeEmail;

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
                        e.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
    }

    private void updateLabel(EditText metScheduleStartDate) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        metScheduleStartDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void setUpToolbar() {
        mtvToolbartitle.setText(getResources().getString(R.string.create_teaching_post));
    }


}
