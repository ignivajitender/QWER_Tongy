package com.igniva.qwer.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.igniva.qwer.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common Utility to Validate any type of user input
 */
public class Validation {

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String passsword) {
        boolean isValid = false;
        //TODO to check the validation of a password
        return isValid;
    }

    public static boolean isValidMobile(Context context, EditText countryCode, EditText mobileNumber) {
        boolean isValid = false;
        if (!isNullorEmpty(countryCode) && !isNullorEmpty(mobileNumber)) {
            String mCountryCode = countryCode.getText().toString();
            String mMobileNumber = mobileNumber.getText().toString();
            return true;
        } else {
            return isValid;
        }
//        }else{
//            Utility.showAlertDialog("Please Enter all the fields Code!",context);
//            countryCode.setFocusableInTouchMode(true);
//            countryCode.requestFocus();
//            return false;
//        }
//        if (!isNullorEmpty(mobileNumber)) {
//            return true;
//        }else{
//            Utility.showAlertDialog("Please Enter Mobile Number!",  context);
//            mobileNumber.setFocusableInTouchMode(true);
//            mobileNumber.requestFocus();
//            return false;
//        }
    }

    /**
     * check for email and password
     */

    public static String validateFields(Activity activity, EditText musername,
                                        EditText mpassword) {
        String password_str = null;
        String email_str = null;
        password_str = mpassword.getText().toString();
        email_str = musername.getText().toString();

        if (FieldValidators.isNullOrEmpty(musername)
                && FieldValidators.isNullOrEmpty(mpassword)) {
            Utility.showToastMessageLong(activity, "Please enter email and password");
            musername.setFocusable(true);
            musername.requestFocus();
            return null;
        } else if (FieldValidators.isNullOrEmpty(musername)) {
            Utility.showToastMessageLong(activity, "Please enter email");
            musername.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(musername, InputMethodManager.SHOW_IMPLICIT);
            musername.requestFocus();
        } else if (Validation.isValidEmail(musername.getText().toString()) == false) {
            Utility.showToastMessageLong(activity, "Please enter a valid email");
            musername.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(musername, InputMethodManager.SHOW_IMPLICIT);
            musername.requestFocus();
        } else if (FieldValidators.isNullOrEmpty(mpassword)) {
            Utility.showToastMessageLong(activity, "Please enter password");
            mpassword.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mpassword, InputMethodManager.SHOW_IMPLICIT);
            mpassword.requestFocus();
        } else if (mpassword.length() < 6) {
            Utility.showToastMessageLong(activity,
                    "Password length should be more than 6 characters");
            mpassword.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mpassword, InputMethodManager.SHOW_IMPLICIT);
            mpassword.requestFocus();
        } else {
            return "validated";
        }
        return null;

    }

    /**
     * To check whether a given edittext is null or empty
     *
     * @param editText
     * @return status of edittext
     */
    public static boolean isNullorEmpty(EditText editText) {
        if (editText == null || editText.length() < 1)
            return true;
        else
            return false;
    }

    /*
    * To check the fields of create profile whether empty or not
    *
    *
    * */
    static String createProfile(Activity activity, EditText firstname, EditText lastname, TextView dateofbirth, EditText desc) {

        String firstName = null;
        String lastName = null;
        String dateOfBirth = null;
        String description = null;

        firstName = firstname.getText().toString();
        lastName = lastname.getText().toString();
        dateOfBirth = dateofbirth.getText().toString();
        description = desc.getText().toString();

        if (FieldValidators.isNullOrEmpty(firstname)
                && FieldValidators.isNullOrEmpty(lastname) && dateOfBirth.isEmpty() && FieldValidators.isNullOrEmpty(desc)) {
            Utility.showToastMessageLong(activity, "Please enter all fields");
            firstname.setFocusable(true);
            lastname.requestFocus();
            return null;

        } else if (FieldValidators.isNullOrEmpty(firstname)) {

            Utility.showToastMessageLong(activity, "Please enter first name");
            firstname.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(firstname, InputMethodManager.SHOW_IMPLICIT);
            firstname.requestFocus();
        } else if (FieldValidators.isNullOrEmpty(lastname)) {
            Utility.showToastMessageLong(activity, "Please enter last name");
            firstname.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(firstname, InputMethodManager.SHOW_IMPLICIT);
            lastname.requestFocus();

        } else if (dateOfBirth.isEmpty()) {

            Utility.showToastMessageLong(activity, "Please choose your date of birth");
        } else if (FieldValidators.isNullOrEmpty(desc)) {

            Utility.showToastMessageLong(activity, "Please enter description");
            desc.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(firstname, InputMethodManager.SHOW_IMPLICIT);
            desc.requestFocus();
        } else {
            return "validated";
        }
        return null;
    }


    public static void showKeyBoard(EditText edittext) {

    }

    // to validation of signup field

    public static boolean isValidatedSignup(Activity activity, EditText mName, TextInputLayout mTextInputLayoutName, EditText mEmail, TextInputLayout mTextInputLayoutEmail, EditText mPassword, TextInputLayout mTextInputLayoutPass, AppCompatCheckBox appCompatCheckBox) {
        mTextInputLayoutName.setError(null);
        mTextInputLayoutEmail.setError(null);
        mTextInputLayoutPass.setError(null);
        if (FieldValidators.isNullOrEmpty(mName)) {
            mName.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mName, InputMethodManager.SHOW_IMPLICIT);
            mName.requestFocus();
            mTextInputLayoutName.setError(activity.getString(R.string.please_enter_name));
            return false;
        } else if (!Validation.isValidEmail(mEmail.getText().toString())) {
            mEmail.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEmail, InputMethodManager.SHOW_IMPLICIT);
            mEmail.requestFocus();
            mTextInputLayoutEmail.setError(activity.getString(R.string.please_enter_valid_email));
            return false;
        } else if (FieldValidators.isNullOrEmpty(mPassword)) {
            mPassword.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mPassword, InputMethodManager.SHOW_IMPLICIT);
            mPassword.requestFocus();
            mTextInputLayoutPass.setError(activity.getString(R.string.please_enter_pass));
            return false;
        } else if (mPassword.length() < 6) {
            mPassword.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mPassword, InputMethodManager.SHOW_IMPLICIT);
            mPassword.requestFocus();
            mTextInputLayoutPass.setError(activity.getString(R.string.please_pass_length));
            return false;
        } else if (!appCompatCheckBox.isChecked()) {
            appCompatCheckBox.setError(activity.getString(R.string.please_agree_terms_and_condition));
            return false;
        }
        return true;
    }


    public static boolean isValidatedLogin(Activity activity, EditText mEmail, TextInputLayout mTextInputLayoutEmail, EditText mPassword, TextInputLayout mTextInputLayoutPass) {
        mTextInputLayoutEmail.setError(null);
        mTextInputLayoutPass.setError(null);
        if (!Validation.isValidEmail(mEmail.getText().toString())) {
            mEmail.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEmail, InputMethodManager.SHOW_IMPLICIT);
            mEmail.requestFocus();
            mTextInputLayoutEmail.setError(activity.getString(R.string.please_enter_valid_email));
            return false;
        } else if (FieldValidators.isNullOrEmpty(mPassword)) {
            mPassword.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mPassword, InputMethodManager.SHOW_IMPLICIT);
            mPassword.requestFocus();
            mTextInputLayoutPass.setError(activity.getString(R.string.please_enter_pass));
            return false;
        }
        return true;
    }


    /**
     * check for Title and description
     */

    public static boolean validateDescription(Activity activity, EditText etContactUsTitle,
                                              EditText etContactUsDescription) {
        String subject = null;
        String message = null;
        subject = etContactUsTitle.getText().toString();
        message = etContactUsDescription.getText().toString();

        if (FieldValidators.isNullOrEmpty(etContactUsTitle)
                && FieldValidators.isNullOrEmpty(etContactUsDescription)) {
            Utility.showToastMessageLong(activity, "Please enter subject and message");
            etContactUsTitle.setFocusable(true);
            etContactUsTitle.requestFocus();
            return false;
        } else if (FieldValidators.isNullOrEmpty(etContactUsTitle)) {
            Utility.showToastMessageLong(activity, "Please enter subject");
            etContactUsTitle.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etContactUsTitle, InputMethodManager.SHOW_IMPLICIT);
            etContactUsTitle.requestFocus();
            return false;
        } else if (FieldValidators.isNullOrEmpty(etContactUsDescription)) {
            Utility.showToastMessageLong(activity, "Please enter message");
            etContactUsDescription.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etContactUsDescription, InputMethodManager.SHOW_IMPLICIT);
            etContactUsDescription.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateNewEmail(Activity activity, EditText etCurrentEmail, EditText etNewEmail) {
        String subject = null;
        String message = null;
        subject = etCurrentEmail.getText().toString();
        message = etNewEmail.getText().toString();

        if (FieldValidators.isNullOrEmpty(etCurrentEmail)
                && FieldValidators.isNullOrEmpty(etNewEmail)) {
            Utility.showToastMessageLong(activity, "Please enter current and new email.");
            etCurrentEmail.setFocusable(true);
            etCurrentEmail.requestFocus();
            return false;
        }
        else if (FieldValidators.isNullOrEmpty(etCurrentEmail)) {
            Utility.showToastMessageLong(activity, "Please enter current email.");
            etCurrentEmail.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etCurrentEmail, InputMethodManager.SHOW_IMPLICIT);
            etCurrentEmail.requestFocus();
            return false;
        }
        else if (!Validation.isValidEmail(etCurrentEmail.getText().toString())) {
            etCurrentEmail.setFocusable(true);

            etCurrentEmail.requestFocus();
            etCurrentEmail.setError(activity.getString(R.string.please_enter_valid_email));
            return false;
        }

        else if (FieldValidators.isNullOrEmpty(etNewEmail)) {
            Utility.showToastMessageLong(activity, "Please enter new email.");
            etNewEmail.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etNewEmail, InputMethodManager.SHOW_IMPLICIT);
            etNewEmail.requestFocus();
            return false;
        }
        else if (!Validation.isValidEmail(etNewEmail.getText().toString())) {
            etNewEmail.setFocusable(true);

            etNewEmail.requestFocus();
            etNewEmail.setError(activity.getString(R.string.please_enter_valid_email));
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean validatePassword(Activity activity, EditText etCurrentEmail) {
        String password = null;
        password = etCurrentEmail.getText().toString();

        if (FieldValidators.isNullOrEmpty(etCurrentEmail)) {
            Utility.showToastMessageLong(activity, "Please enter current and new email.");
            etCurrentEmail.setFocusable(true);
            etCurrentEmail.requestFocus();
            return false;
        }  else {
            return true;
        }
    }

    public static boolean validateChangePassword(Activity activity, EditText mEtCuurentPass, EditText mEtNewPass, EditText mEtConfirmPassword) {


        if (FieldValidators.isNullOrEmpty(mEtCuurentPass)) {
            mEtCuurentPass.setFocusable(true);

            mEtCuurentPass.requestFocus();
            mEtCuurentPass.setError(activity.getString(R.string.please_enter_current_pass));
            return false;
        }
        else if (mEtCuurentPass.getText().toString().trim().length() < 6) {
            mEtCuurentPass.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEtCuurentPass, InputMethodManager.SHOW_IMPLICIT);
            mEtCuurentPass.requestFocus();
            mEtCuurentPass.setError(activity.getString(R.string.please_pass_length));
            return false;
        }
        if (FieldValidators.isNullOrEmpty(mEtNewPass)) {
            mEtNewPass.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEtCuurentPass, InputMethodManager.SHOW_IMPLICIT);
            mEtNewPass.requestFocus();
            mEtNewPass.setError(activity.getString(R.string.please_enter_new_pass));
            return false;
        }
        else if (mEtNewPass.getText().toString().trim().length() < 6) {
            mEtNewPass.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEtCuurentPass, InputMethodManager.SHOW_IMPLICIT);
            mEtNewPass.requestFocus();
            mEtNewPass.setError(activity.getString(R.string.please_pass_length));
            return false;
        }
        if (FieldValidators.isNullOrEmpty(mEtConfirmPassword)) {
            mEtConfirmPassword.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEtCuurentPass, InputMethodManager.SHOW_IMPLICIT);
            mEtConfirmPassword.requestFocus();
            mEtConfirmPassword.setError(activity.getString(R.string.please_enter_confirm_pass));
            return false;
        }
        else if (mEtConfirmPassword.getText().toString().trim().length() < 6) {
            mEtConfirmPassword.setFocusable(true);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEtCuurentPass, InputMethodManager.SHOW_IMPLICIT);
            mEtConfirmPassword.requestFocus();
            mEtConfirmPassword.setError(activity.getString(R.string.please_pass_length));
            return false;
        }



        else {
            return true;
        }
    }

    public static boolean validateForgotPassword(Activity activity, EditText metEmail, TextInputLayout mtilEmail) {

        if (FieldValidators.isNullOrEmpty(metEmail)) {
            metEmail.setFocusable(true);
            metEmail.requestFocus();
            mtilEmail.setError(activity.getString(R.string.please_enter_email));
            return false;
        }

        else if (!Validation.isValidEmail(metEmail.getText().toString())) {
            metEmail.setFocusable(true);

            metEmail.requestFocus();
            mtilEmail.setError(activity.getString(R.string.please_enter_valid_email));
            return false;
        }

    return  true;
    }


    public static boolean validateUpdateProfile(Activity activity, EditText mEtCountry, EditText mEtPincode, EditText mEtAbout, EditText mEtCity, EditText mEtGender, EditText mEtAge) {

        if(FieldValidators.isNullOrEmpty(mEtCountry)){
            mEtCountry.setFocusable(true);
            mEtCountry.requestFocus();
            mEtCountry.setError(activity.getString(R.string.please_enter_country));
            return false;
        }
       else if(FieldValidators.isNullOrEmpty(mEtCity)){
            mEtCity.setFocusable(true);
            mEtCity.requestFocus();
            mEtCity.setError(activity.getString(R.string.please_enter_city));
            return false;
        }
        if(FieldValidators.isNullOrEmpty(mEtPincode)){
            mEtPincode.setFocusable(true);
            mEtPincode.requestFocus();
            mEtPincode.setError(activity.getString(R.string.please_enter_pincode));
            return false;
        }
        if(FieldValidators.isNullOrEmpty(mEtAge)){
            mEtAge.setFocusable(true);
            mEtAge.requestFocus();
            mEtAge.setError(activity.getString(R.string.please_enter_age));
            return false;
        }
        if(FieldValidators.isNullOrEmpty(mEtAbout)){
            mEtAbout.setFocusable(true);
            mEtAbout.requestFocus();
            mEtAbout.setError(activity.getString(R.string.please_enter_about));
            return false;
        }


        return true;
    }
}
