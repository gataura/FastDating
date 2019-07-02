package com.pretty.foryou;

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

import com.pretty.foryou.Model.Employee;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class QuestionLPageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView profPic;
    private CheckBox termsCheck;

    private Employee newEmp;
    private ImagePicker imgPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_l_page);
        initView();

        newEmp = (Employee) getIntent().getSerializableExtra("newEmployee");
    }

    private void initView() {
        EditText mOtherDataEt = findViewById(R.id.bla_inform_about);
        EditText mOptionalTextEt = findViewById(R.id.bla_inform_opt_txt);
        TextView mTermsTv = findViewById(R.id.bla_term_conditions);
        mTermsTv.setOnClickListener(this);
        Button mAddImageBtn = findViewById(R.id.pin_picture_button);
        mAddImageBtn.setOnClickListener(this);
        Button mSignInBtn = findViewById(R.id.blaur_btn_sign_in);
        mSignInBtn.setOnClickListener(this);
        profPic = findViewById(R.id.iv_profile_pic);
        termsCheck = findViewById(R.id.chbox_term_condi);
        LinearLayout mTermsLl = findViewById(R.id.ll_terms);
        CardView mViewTermsCard = findViewById(R.id.card_view_terms);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bla_term_conditions:
                intent = new Intent(this, TermsAnConditionsActivity.class);
                startActivity(intent);
                break;
            case R.id.pin_picture_button:// TODO 19/03/11
//                intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, 1);

                imgPicker = new ImagePicker(this, null, new OnImagePickedListener() {
                    @Override
                    public void onImagePicked(Uri imageUri) {
                        profPic.setImageURI(imageUri);
                    }
                });

                imgPicker.choosePicture(true);


//                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                getIntent.setType("image/*");
//
//                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.AllImgs.Media.EXTERNAL_CONTENT_URI);
//                pickIntent.setType("image/*");
//
//                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
//
//                startActivityForResult(chooserIntent, 1);
                break;
            case R.id.blaur_btn_sign_in:
                if (isCheckboxTermsChecked()) {

                    intent = new Intent(this, TheFirstActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("newEmployee", newEmp);
                    startActivity(intent);


                }
                break;
            default:
                break;
        }
    }


    private boolean isCheckboxTermsChecked() {
        if (termsCheck.isChecked()) {
            return true;
        }
        Toast.makeText(this, "Agree to Terms and Conditions", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgPicker.handleActivityResult(resultCode, requestCode, data);
        AllImgs.saveToInternalStorage(getApplicationContext(), profPic, newEmp.id);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imgPicker.handlePermission(requestCode, grantResults);
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
//                AllImgs.saveToInternalStorage(getApplicationContext(), mProfilePicIv, newEmployee.id);
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
