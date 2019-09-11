package datings.apps.fastdatings;

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

import datings.apps.fastdatings.Model.Employee;
import datings.apps.fastdatings.Presenter.Presenter;

import java.io.FileNotFoundException;
import java.io.InputStream;

import me.angrybyte.numberpicker.listener.OnValueChangeListener;
import me.angrybyte.numberpicker.view.ActualNumberPicker;

public class StranichkaActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView stranichkaImg;
    private TextView rostText;
    private ActualNumberPicker rostPickerTv;
    private TextView ves;
    private ActualNumberPicker vesPicker;
    private EditText nickEdit;

    Presenter presenter = new Presenter();

    private Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stranichka);
        initView();
        setListenerToPickerHeight();
        setListenerToPickerWeight();


        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");
        setDefaultValues();
    }

    private void setDefaultValues() {


        nickEdit.setText(String.valueOf(newEmployee.name));
        ves.setText(String.valueOf(newEmployee.weight));
        rostText.setText(String.valueOf(newEmployee.height));

        AllSomeImgs.loadImageFromStorage(getApplicationContext(), stranichkaImg, newEmployee.id);
    }


    private void initView() {
        Button mAddImageBtn = findViewById(R.id.pin_picture_button);
        mAddImageBtn.setOnClickListener(this);
        stranichkaImg = findViewById(R.id.iv_profile_pic);
        rostText = findViewById(R.id.rost_show);
        rostPickerTv = findViewById(R.id.rost_vibor);
        ves = findViewById(R.id.bla_weight_show);
        vesPicker = findViewById(R.id.ves_vibor);
        nickEdit = findViewById(R.id.vvedi_nick);
        TextView mPrefixCmTv = findViewById(R.id.your_prefix_cm);
        TextView mPrefixKgTv = findViewById(R.id.your_prefix_kg);
        Button mChangeBtn = findViewById(R.id.izmenit_btn);
        mChangeBtn.setOnClickListener(this);
    }

    private void setListenerToPickerWeight() {
        vesPicker.setListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                ves.setText(String.valueOf(newValue));
            }
        });
    }

    private void setListenerToPickerHeight() {
        rostPickerTv.setListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                rostText.setText(String.valueOf(newValue));
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pin_picture_button:


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
            case R.id.izmenit_btn:

                collectAllDataFromViewsToEmployee();
                presenter.update(newEmployee);

                if (stranichkaImg.getDrawable() != null) {
                    AllSomeImgs.saveToInternalStorage(getApplicationContext(), stranichkaImg, newEmployee.id);
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
                stranichkaImg.setImageBitmap(selectedImage);
                AllSomeImgs.saveToInternalStorage(getApplicationContext(), stranichkaImg, newEmployee.id);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    private void collectAllDataFromViewsToEmployee() {
        newEmployee.name = nickEdit.getText().toString();
        newEmployee.weight = Integer.valueOf(ves.getText().toString());
        newEmployee.height = Integer.valueOf(rostText.getText().toString());

    }
}
