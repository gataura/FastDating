package com.newapp2.datingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.newapp2.datingapp.Model.Employee;
import com.newapp2.datingapp.Presenter.Presenter;

import java.io.FileNotFoundException;
import java.io.InputStream;

import me.angrybyte.numberpicker.listener.OnValueChangeListener;
import me.angrybyte.numberpicker.view.ActualNumberPicker;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mAddImageBtn;
    private ImageView mProfilePicIv;
    private TextView mHeightTv;
    private ActualNumberPicker mPickerHeightActual;
    private TextView mWeightTv;
    private ActualNumberPicker mPickerWeightActual;
    private EditText mNickEt;

    Presenter presenter = new Presenter();
    private TextView mPrefixCmTv;
    private TextView mPrefixKgTv;
    private Button mChangeBtn;

    private Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        setListenerToPickerHeight();
        setListenerToPickerWeight();


        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");
        setDefaultValues();
    }

    private void setDefaultValues() {


        mNickEt.setText(String.valueOf(newEmployee.name));
        mWeightTv.setText(String.valueOf(newEmployee.weight));
        mHeightTv.setText(String.valueOf(newEmployee.height));

        Images.loadImageFromStorage(getApplicationContext(), mProfilePicIv, newEmployee.id);
    }


    private void initView() {
        mAddImageBtn = (Button) findViewById(R.id.btn_add_image);
        mAddImageBtn.setOnClickListener(this);
        mProfilePicIv = (ImageView) findViewById(R.id.iv_profile_pic);
        mHeightTv = (TextView) findViewById(R.id.tv_height);
        mPickerHeightActual = (ActualNumberPicker) findViewById(R.id.actual_picker_height);
        mWeightTv = (TextView) findViewById(R.id.tv_weight);
        mPickerWeightActual = (ActualNumberPicker) findViewById(R.id.actual_picker_weight);
        mNickEt = (EditText) findViewById(R.id.et_nick);
        mPrefixCmTv = (TextView) findViewById(R.id.tv_prefix_cm);
        mPrefixKgTv = (TextView) findViewById(R.id.tv_prefix_kg);
        mChangeBtn = (Button) findViewById(R.id.btn_change);
        mChangeBtn.setOnClickListener(this);
    }

    private void setListenerToPickerWeight() {
        mPickerWeightActual.setListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                mWeightTv.setText(String.valueOf(newValue));
            }
        });
    }

    private void setListenerToPickerHeight() {
        mPickerHeightActual.setListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                mHeightTv.setText(String.valueOf(newValue));
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_image:


//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, 1);

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, 1);
                break;
            case R.id.btn_change:

                collectAllDataFromViewsToEmployee();
                presenter.update(newEmployee);

                if (mProfilePicIv.getDrawable() != null) {
                    Images.saveToInternalStorage(getApplicationContext(), mProfilePicIv, newEmployee.id);
                }

                Toast.makeText(v.getContext(), "Profile saved", Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("newEmployee", newEmployee);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

                Presenter presenter = new Presenter();
                presenter.updateUserProfile(newEmployee);

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mProfilePicIv.setImageBitmap(selectedImage);
                Images.saveToInternalStorage(getApplicationContext(), mProfilePicIv, newEmployee.id);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    private void collectAllDataFromViewsToEmployee() {
        newEmployee.name = mNickEt.getText().toString();
        newEmployee.weight = Integer.valueOf(mWeightTv.getText().toString());
        newEmployee.height = Integer.valueOf(mHeightTv.getText().toString());

    }
}
