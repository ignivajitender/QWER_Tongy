package com.igniva.qwer.ui.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
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
import com.igniva.qwer.utils.CustomExpandableListView;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private ArrayList<String> todoList;

    @OnClick(R.id.ivbackIcon)
    public void back() {

        Utility.hideSoftKeyboard(CreateTeachingPostActivity.this);
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
    public void hideAddress() {
        if (mllAddAddress.getVisibility() == View.VISIBLE)
            mllAddAddress.setVisibility(View.GONE);

        typeOfClass = "online";
    }

    @OnClick(R.id.rb_physical)
    public void showAddress() {
        mllAddAddress.setVisibility(View.VISIBLE);
        typeOfClass = "physical";
    }

    @BindView(R.id.autocomTextViewAddress)
    AutoCompleteTextView mautocomTextViewAddress;

    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Gson gson;
    String typeOfClass = "online";


    @OnClick(R.id.ivLocation)
    public void openLocation() {
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

                        String timeSet = "";
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            timeSet = "PM";
                        } else if (hourOfDay == 0) {
                            hourOfDay += 12;
                            timeSet = "AM";
                        } else if (hourOfDay == 12)
                            timeSet = "PM";
                        else
                            timeSet = "AM";


                        String minutes = "";
                        if (minute < 10)
                            minutes = "0" + minute;
                        else
                            minutes = String.valueOf(minute);

                        mEditText.setText( hourOfDay + ":" + minutes+" "+timeSet);

                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    @OnClick(R.id.tvPostNow)
    public void post() {
        if(getIntent().getStringExtra("comingFrom").equalsIgnoreCase("teaching")) {
            // call api to create teaching post
            ApiControllerClass.createTeachingPostApi(CreateTeachingPostActivity.this, retrofit, mEtTitle, mEtDescription, mEtPrice, metScheduleStartDate, metScheduleEndDate, mEtStartTime, mEtEndTime, typeOfClass);
        }
        else if(getIntent().getStringExtra("comingFrom").equalsIgnoreCase("meeting")) {
            // call api to create teaching post
            ApiControllerClass.createMeetingPostApi(CreateTeachingPostActivity.this, retrofit, mEtTitle, mEtDescription, metScheduleStartDate, metScheduleEndDate, mEtStartTime, mEtEndTime,mlvAddMembersList,metAddMembers,todoList);
        }

    }

    @BindView(R.id.et_schedule_start_date)
    EditText metScheduleStartDate;
    @BindView(R.id.et_schedule_end_date)
    EditText metScheduleEndDate;

    @BindView(R.id.llAddMembers)
    LinearLayout mllAddMembers;
    @BindView(R.id.llAddPrice)
    LinearLayout mllAddPrice;
    @BindView(R.id.llTypeOfClass)
    LinearLayout mllTypeOfClass;
    @BindView(R.id.llAddAddress)
    LinearLayout mllAddAddress;

    @BindView(R.id.et_add_members)
    EditText metAddMembers;
    @BindView(R.id.lv_add_members_list)
    CustomExpandableListView mlvAddMembersList;

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

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 2) {
                    // call google place api to fetch addresses
                    Utility.callGoogleApi(CreateTeachingPostActivity.this, mautocomTextViewAddress, "", okHttpClient, gson);
                }

               /* if (!mautocomTextviewAddress.getText().toString().equals("")) { //if edittext include text
                    mbtnClearAddress.setVisibility(View.VISIBLE);
                } else { //not include text
                    mbtnClearAddress.setVisibility(View.GONE);
                }*/
            }
        });
         if (getIntent() != null && getIntent().hasExtra("comingFrom")) {
            if (getIntent().getStringExtra("comingFrom").equalsIgnoreCase("teaching"))
                mllAddMembers.setVisibility(View.GONE);
            if (getIntent().getStringExtra("comingFrom").equalsIgnoreCase("meeting")) {
                mllAddMembers.setVisibility(View.VISIBLE);
                mllAddAddress.setVisibility(View.VISIBLE);
                mllAddPrice.setVisibility(View.GONE);
                mllTypeOfClass.setVisibility(View.GONE);

            }
        }

    }

    @Override
    protected void setDataInViewObjects() {
        //Create a an arraylist to hold all the todo items
        todoList = new ArrayList<String>();



        //Create an ArrayAdaptor object to be able to bind ArrayLists to ListViews
        final ArrayAdapter<String> arrayAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoList);
        mlvAddMembersList.setAdapter(arrayAdaptor);


        //Listens for certain keys to be pushed, particular the dpad center key or enter key
        metAddMembers.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER))
                    {
                        //add item in the EditText to the todo list
                        todoList.add(0, metAddMembers.getText().toString());
                        //Update the view by notifying the ArrayAdapter of the data changes
                        arrayAdaptor.notifyDataSetChanged();
                        metAddMembers.setText("");
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });

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
                        e.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
        if (getIntent().getStringExtra("comingFrom").equalsIgnoreCase("teaching")) {
            mtvToolbartitle.setText(getResources().getString(R.string.create_teaching_post));

        } else
            mtvToolbartitle.setText(getResources().getString(R.string.create_meeting_post));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Utility.address!=null)
            mautocomTextViewAddress.setText(Utility.address);
    }
    public  void callSuccessPopUp(final Context context, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.succuess_pop_up);
        TextView text_message = (TextView) dialog.findViewById(R.id.tv_success_message);
        text_message.setText(message);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        TextView mBtnOk = (TextView) dialog.findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ((Activity) context).finish();
                CreateNewPostActivity.activiy.finish();
            }
        });


        dialog.setTitle("Custom Dialog");


        dialog.show();


    }
}
