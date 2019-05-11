package com.example.agenda.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.agenda.R;
import com.example.agenda.models.User;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    private User user;
    private int position;
    private String tag;
    private Uri file;

    @BindView(R.id.name)
    EditText nameEditText;

    @BindView(R.id.score)
    EditText scoreEditText;

    @BindView(R.id.phone)
    EditText phoneEditText;

    @BindView(R.id.address)
    EditText addressEditText;

    @BindView(R.id.url)
    EditText urlEditText;

    @BindView(R.id.input_layout_name)
    TextInputLayout nameInputLayout;

    @BindView(R.id.input_layout_score)
    TextInputLayout scoreInputLayout;

    @BindView(R.id.input_layout_phone)
    TextInputLayout phoneInputLayout;

    @BindView(R.id.input_layout_address)
    TextInputLayout addressInputLayout;

    @BindView(R.id.input_layout_url)
    TextInputLayout urlInputLayout;

    @BindView(R.id.image_photo)
    ImageView photoImageView;

    @OnClick(R.id.confirm_button)
    public void confirmButton(){
        if(checkFields()){
            user.setUrl(urlEditText.getText().toString());
            user.setName(nameEditText.getText().toString());
            user.setAddress(addressEditText.getText().toString());
            user.setPhone(phoneEditText.getText().toString());
            user.setScore(Double.parseDouble(scoreEditText.getText().toString().trim()));
            if(file != null) user.setPhotoUri(file.getPath());
            else user.setPhotoUri(null);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("EDITUSER",user);
            returnIntent.putExtra("POSITION",position);
            returnIntent.putExtra("TAG",tag);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_user);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("USER");
        position = intent.getIntExtra("POSITION",-1);
        tag = intent.getStringExtra("TAG");
        file = null;

        if(tag.equals("EDIT")){
            nameEditText.setText(user.getName());
            scoreEditText.setText(String.valueOf(user.getScore()));
            phoneEditText.setText(user.getPhone());
            addressEditText.setText(user.getAddress());
            urlEditText.setText(user.getUrl());

            if(user.getPhotoUri() != null){
                file = Uri.parse(user.getPhotoUri());
                Bitmap bitmap = BitmapFactory.decodeFile(user.getPhotoUri());
                Bitmap redux = Bitmap.createScaledBitmap(bitmap,300,300,true);
                photoImageView.setImageBitmap(redux);
            }
        } else user = new User();
    }

    private boolean checkFields() {
        boolean check = true;
        if(TextUtils.isEmpty(nameEditText.getText())){
            nameInputLayout.setError(getString(R.string.insert_name));
            check = false;
        }
        if(TextUtils.isEmpty(scoreEditText.getText())){
            scoreInputLayout.setError(getString(R.string.insert_score));
            check = false;
        }
        if(TextUtils.isEmpty(phoneEditText.getText())){
            phoneInputLayout.setError(getString(R.string.insert_phone));
            check = false;
        }
        if(TextUtils.isEmpty(addressEditText.getText())){
            addressInputLayout.setError(getString(R.string.insert_address));
            check =false;
        }
        if(TextUtils.isEmpty(urlEditText.getText())){
            urlInputLayout.setError(getString(R.string.insert_url));
            check =false;
        }
        return check;
    }

    @OnClick(R.id.fab_edit_photo)
    public void editPhoto(){
        checkAndroidVersion();
    }

    public void checkAndroidVersion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
            } catch(Exception e){
                Log.e("Android Version Error",e.getMessage());
            }
        } else {
            pickImage();
        }
    }

    public void pickImage() {
        CropImage.startPickImageActivity(this);
        Log.v("CROPERROR","pickImage");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        } else {
            checkAndroidVersion();
        }
    }

    private void cropRequest(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE :
                if(resultCode == Activity.RESULT_OK&& data!=null && data.getData()!=null){
                    Uri uri = CropImage.getPickImageResultUri(this, data);
                    cropRequest(uri);
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE :
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                        Bitmap redux = Bitmap.createScaledBitmap(bitmap,300,300,true);
                        photoImageView.setImageBitmap(redux);
                        file = result.getUri();
                    } catch (IOException e) {
                        Log.v("GETPHOTOERROR",e.getMessage());
                    }
                }
                break;
        }
    }
}