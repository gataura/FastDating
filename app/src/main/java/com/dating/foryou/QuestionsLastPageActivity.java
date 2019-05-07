package com.dating.foryou;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dating.foryou.Model.Employee;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class QuestionsLastPageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mProfilePicIv;
    private CheckBox mTermsCheckbox;

    private Employee newEmployee;
    private ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_last_page);
        initView();

        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");
    }

    private void initView() {
        EditText mOtherDataEt = findViewById(R.id.enter_about_you);
        EditText mOptionalTextEt = findViewById(R.id.enter_optional_text);
        TextView mTermsTv = findViewById(R.id.your_terms);
        mTermsTv.setOnClickListener(this);
        Button mAddImageBtn = findViewById(R.id.button_add_image);
        mAddImageBtn.setOnClickListener(this);
        Button mSignInBtn = findViewById(R.id.your_btn_sign_in);
        mSignInBtn.setOnClickListener(this);
        mProfilePicIv = findViewById(R.id.iv_profile_pic);
        mTermsCheckbox = findViewById(R.id.your_checkbox_terms);
        LinearLayout mTermsLl = findViewById(R.id.ll_terms);
        CardView mViewTermsCard = findViewById(R.id.card_view_terms);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.your_terms:
                intent = new Intent(this, TermsActivity.class);
                startActivity(intent);
                break;
            case R.id.button_add_image:// TODO 19/03/11
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
            case R.id.your_btn_sign_in:
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
        imagePicker.handleActivityResult(resultCode, requestCode, data);
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
