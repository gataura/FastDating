package com.newapp2.datingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newapp2.datingapp.Model.Employee;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;

public class AnketaLastPageActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mOtherDataEt;
    private EditText mOptionalTextEt;
    private TextView mTermsTv;
    private Button mAddImageBtn;
    private Button mSignInBtn;
    private ImageView mProfilePicIv;
    private CheckBox mTermsCheckbox;
    private LinearLayout mTermsLl;
    private CardView mViewTermsCard;

    private Employee newEmployee;
    private ImagePicker imagePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anketa_last_page);
        initView();

        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");
    }

    private void initView() {
        mOtherDataEt = (EditText) findViewById(R.id.et_about_you);
        mOptionalTextEt = (EditText) findViewById(R.id.et_optional_text);
        mTermsTv = (TextView) findViewById(R.id.tv_terms);
        mTermsTv.setOnClickListener(this);
        mAddImageBtn = (Button) findViewById(R.id.btn_add_image);
        mAddImageBtn.setOnClickListener(this);
        mSignInBtn = (Button) findViewById(R.id.btn_sign_in);
        mSignInBtn.setOnClickListener(this);
        mProfilePicIv = (ImageView) findViewById(R.id.iv_profile_pic);
        mTermsCheckbox = (CheckBox) findViewById(R.id.checkbox_terms);
        mTermsLl = (LinearLayout) findViewById(R.id.ll_terms);
        mViewTermsCard = (CardView) findViewById(R.id.card_view_terms);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_terms:
                intent = new Intent(this, TermsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_add_image:// TODO 19/03/11
//                intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, 1);

                imagePicker = new ImagePicker(this, null, new OnImagePickedListener() {
                    @Override
                    public void onImagePicked(Uri imageUri) {
                        mProfilePicIv.setImageURI(imageUri);
                    }
                });

                imagePicker.choosePicture(true);


//                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                getIntent.setType("image/*");
//
//                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                pickIntent.setType("image/*");
//
//                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
//
//                startActivityForResult(chooserIntent, 1);
                break;
            case R.id.btn_sign_in:
                if (isCheckboxTermsChecked()) {

                    intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("newEmployee", newEmployee);
                    startActivity(intent);


                }
                break;
            default:
                break;
        }
    }


    private boolean isCheckboxTermsChecked() {
        if (mTermsCheckbox.isChecked()) {
            return true;
        }
        Toast.makeText(this, "Agree to Terms and Conditions", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);
        Images.saveToInternalStorage(getApplicationContext(), mProfilePicIv, newEmployee.id);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }
//
//    @Override
//    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//
//
//        if (resultCode == RESULT_OK) {
//            try {
//                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                mProfilePicIv.setImageBitmap(selectedImage);
//                Images.saveToInternalStorage(getApplicationContext(), mProfilePicIv, newEmployee.id);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//
//        } else {
//            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
//        }
//    }
}
