package com.igniva.qwer.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.views.TextViewBold;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.ImagePicker;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Retrofit;

import static com.igniva.qwer.utils.ImagePicker.PIC_CROP;

public class CreateOtherPostActivity extends BaseActivity {

    @BindView(R.id.ivbackIcon)
    ImageView mIvbackIcon;
    @BindView(R.id.tv_tap_to_rename)
    TextView mTvTapToRename;
    @BindView(R.id.ll_change_title)
    LinearLayout mLlChangeTitle;
    @BindView(R.id.toolbar_top)
    Toolbar mToolbarTop;
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_description)
    EditText mEtDescription;
    @BindView(R.id.ivImage)
    ImageView mIvImage;
    @BindView(R.id.tvPostNow)
    TextViewBold mTvPostNow;
    @BindView(R.id.ll_post_now)
    LinearLayout mLlPostNow;
    @Inject
    public
    Retrofit retrofit;
    private int IMAGE_REQUEST_CODE = 500;
    private final int RC_CAMERA_PERM = 123;
    private File outPutFile;


    @OnClick(R.id.tvPostNow)
    public void postNow(){
        //ApiControllerClass.callOtherPostApi(retrofit,CreateOtherPostActivity.this,mEtDescription,mEtTitle,outPutFile);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Global)getApplicationContext()).getNetComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_other_post);
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

    @Override
    protected void setUpToolbar() {

    }


    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void changeImage() {
        if (hasCameraPermission()) {
            // Have permission, do the thing!
            startActivityForResult(ImagePicker.getPickImageIntent(CreateOtherPostActivity.this), IMAGE_REQUEST_CODE);

        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_ask_again),
                    RC_CAMERA_PERM,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);
        } else if (requestCode == IMAGE_REQUEST_CODE && resultCode == -1) {
            Bitmap bitmap = ImagePicker.getImageFromResult(CreateOtherPostActivity.this, resultCode, data);
            if (bitmap != null) {

                ImagePicker.ImageCropFunction(CreateOtherPostActivity.this);
                outPutFile = ImagePicker.persistImage(bitmap);
               ;
            }
        }

        //user is returning from cropping the image
        else if (requestCode == PIC_CROP) {
            //get the returned data
            if (data != null) {
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                Bitmap thePic = ImagePicker.getBitmapFromData(data);
                if (thePic != null) {

                        mIvImage.setImageBitmap(thePic);


                }
                outPutFile = ImagePicker.persistImage(thePic);
            }
            if (outPutFile != null) {
                //callUploadPictureApi(outPutFile);
            }
        }

    }
}
