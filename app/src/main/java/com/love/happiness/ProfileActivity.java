package com.love.happiness;

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

import com.love.happiness.Model.Employee;
import com.love.happiness.Presenter.Presenter;

import java.io.FileNotFoundException;
import java.io.InputStream;

import me.angrybyte.numberpicker.listener.OnValueChangeListener;
import me.angrybyte.numberpicker.view.ActualNumberPicker;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView myProfilePicIv;
    private TextView myHeightTv;
    private ActualNumberPicker myPickerHeightActual;
    private TextView myWeightTv;
    private ActualNumberPicker myPickerWeightActual;
    private EditText myNickEt;

    Presenter presenter = new Presenter();

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


        myNickEt.setText(String.valueOf(newEmployee.name));
        myWeightTv.setText(String.valueOf(newEmployee.weight));
        myHeightTv.setText(String.valueOf(newEmployee.height));

        AllImgs.loadImageFromStorage(getApplicationContext(), myProfilePicIv, newEmployee.id);
    }


    private void initView() {
        Button mAddImageBtn = findViewById(R.id.add_img_btn);
        mAddImageBtn.setOnClickListener(this);
        myProfilePicIv = findViewById(R.id.iv_profile_pic);
        myHeightTv = findViewById(R.id.your_height_display);
        myPickerHeightActual = findViewById(R.id.actual_height_picker);
        myWeightTv = findViewById(R.id.your_weight_display);
        myPickerWeightActual = findViewById(R.id.actual_weight_picker);
        myNickEt = findViewById(R.id.enter_your_nickname);
        TextView mPrefixCmTv = findViewById(R.id.your_prefix_cm);
        TextView mPrefixKgTv = findViewById(R.id.your_prefix_kg);
        Button mChangeBtn = findViewById(R.id.change_btn);
        mChangeBtn.setOnClickListener(this);
    }

    private void setListenerToPickerWeight() {
        myPickerWeightActual.setListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                myWeightTv.setText(String.valueOf(newValue));
            }
        });
    }

    private void setListenerToPickerHeight() {
        myPickerHeightActual.setListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                myHeightTv.setText(String.valueOf(newValue));
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_img_btn:


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
            case R.id.change_btn:

                collectAllDataFromViewsToEmployee();
                presenter.update(newEmployee);

                if (myProfilePicIv.getDrawable() != null) {
                    AllImgs.saveToInternalStorage(getApplicationContext(), myProfilePicIv, newEmployee.id);
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
                myProfilePicIv.setImageBitmap(selectedImage);
                AllImgs.saveToInternalStorage(getApplicationContext(), myProfilePicIv, newEmployee.id);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    private void collectAllDataFromViewsToEmployee() {
        newEmployee.name = myNickEt.getText().toString();
        newEmployee.weight = Integer.valueOf(myWeightTv.getText().toString());
        newEmployee.height = Integer.valueOf(myHeightTv.getText().toString());

    }
}
