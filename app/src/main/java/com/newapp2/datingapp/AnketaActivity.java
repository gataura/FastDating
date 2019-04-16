package com.newapp2.datingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.newapp2.datingapp.Model.Employee;
import com.newapp2.datingapp.Presenter.Presenter;

import java.util.Calendar;

import me.angrybyte.numberpicker.listener.OnValueChangeListener;
import me.angrybyte.numberpicker.view.ActualNumberPicker;

public class AnketaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mNickEt;
    private Button mNextBtn;
    private ActualNumberPicker mPickerHeightActual;
    private CardView mViewNicknameCard;
    private CardView mViewBirthdayCard;
    private TextView mBirthdayTv;
    private int mYear, mMonth, mDay, mHour, mMinute;


    private LinearLayout mBirthLl;
    private TextView mHeightTv;
    private TextView mWeightTv;
    private ActualNumberPicker mPickerWeightActual;
    private Spinner mRaceSpinner;
    private LinearLayout mRaceLl;
    private CardView mViewRaceCard;

    Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anketa);
        initView();

        setListenerToPickerHeight();
        setListenerToPickerWeight();
        setRacesToSpinner();


        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");

        if (newEmployee.name!=null) {
            mNickEt.setText(newEmployee.name);

        }
    }

    private void setRacesToSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.races, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mRaceSpinner.setAdapter(adapter);
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


    private void initView() {

        mNickEt = (EditText) findViewById(R.id.et_nick);
        mNextBtn = (Button) findViewById(R.id.btn_next);
        mNextBtn.setOnClickListener(this);
        mPickerHeightActual = (ActualNumberPicker) findViewById(R.id.actual_picker_height);

        mViewNicknameCard = (CardView) findViewById(R.id.card_view_nickname);
        mViewBirthdayCard = (CardView) findViewById(R.id.card_view_birthday);
        mBirthdayTv = findViewById(R.id.tv_birthday);
        mBirthdayTv.setOnClickListener(this);
        mBirthLl = (LinearLayout) findViewById(R.id.ll_birth);
        mHeightTv = (TextView) findViewById(R.id.tv_height);
        mWeightTv = (TextView) findViewById(R.id.tv_weight);
        mPickerWeightActual = (ActualNumberPicker) findViewById(R.id.actual_picker_weight);


        mRaceSpinner = (Spinner) findViewById(R.id.spinner_race);
        mRaceLl = (LinearLayout) findViewById(R.id.ll_race);
        mViewRaceCard = (CardView) findViewById(R.id.card_view_race);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_next:
                if (isNickEmpty() && isBirthdayEmpty()) {

                    updateUserProfile();

                    intent = new Intent(this, AnketaSecondPageActivity.class);
                    intent.putExtra("newEmployee", newEmployee);
                    startActivity(intent);
                }
                break;
            case R.id.tv_birthday:
                showDatePicker();
                break;
            default:
                break;
        }
    }


    private void showDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mBirthdayTv.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private boolean isNickEmpty() {
        if (!mNickEt.getText().toString().isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Choose Nick name", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isBirthdayEmpty() {
        if (!mBirthdayTv.getText().toString().isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Choose your Birthday", Toast.LENGTH_SHORT).show();
        return false;
    }


    private void updateUserProfile() {
        String name = mNickEt.getText().toString();
        String birthday = mBirthdayTv.getText().toString();
        int weight = Integer.valueOf(mWeightTv.getText().toString());
        int height = Integer.valueOf(mHeightTv.getText().toString());
        String race = mRaceSpinner.getSelectedItem().toString();

        newEmployee.name = name;
        newEmployee.birthday = birthday;
        newEmployee.weight = weight;
        newEmployee.height = height;
        newEmployee.race = race;

        Presenter presenter = new Presenter();
        presenter.updateUserProfile(newEmployee);


    }
}
