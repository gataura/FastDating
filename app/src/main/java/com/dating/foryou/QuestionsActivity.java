package com.dating.foryou;

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

import com.dating.foryou.Model.Employee;
import com.dating.foryou.Presenter.Presenter;

import java.util.Calendar;

import me.angrybyte.numberpicker.listener.OnValueChangeListener;
import me.angrybyte.numberpicker.view.ActualNumberPicker;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mNickEt;
    private ActualNumberPicker mPickerHeightActual;
    private TextView mBirthdayTv;
    private int mHour;
    private int mMinute;


    private TextView mHeightTv;
    private TextView mWeightTv;
    private ActualNumberPicker mPickerWeightActual;
    private Spinner mRaceSpinner;

    Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
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

        mNickEt = findViewById(R.id.enter_nick);
        Button mNextBtn = findViewById(R.id.button_next);
        mNextBtn.setOnClickListener(this);
        mPickerHeightActual = findViewById(R.id.your_actual_picker_height);

        CardView mViewNicknameCard = findViewById(R.id.card_view_nickname);
        CardView mViewBirthdayCard = findViewById(R.id.card_view_birthday);
        mBirthdayTv = findViewById(R.id.your_birthday);
        mBirthdayTv.setOnClickListener(this);
        LinearLayout mBirthLl = findViewById(R.id.ll_birth);
        mHeightTv = findViewById(R.id.your_height);
        mWeightTv = findViewById(R.id.your_weight);
        mPickerWeightActual = findViewById(R.id.your_actual_picker_weight);


        mRaceSpinner = findViewById(R.id.your_spinner_race);
        LinearLayout mRaceLl = findViewById(R.id.ll_race);
        CardView mViewRaceCard = findViewById(R.id.card_view_race);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_next:
                if (isNickEmpty() && isBirthdayEmpty()) {

                    updateUserProfile();

                    intent = new Intent(this, QuestionsSecondPageActivity.class);
                    intent.putExtra("newEmployee", newEmployee);
                    startActivity(intent);
                }
                break;
            case R.id.your_birthday:
                showDatePicker();
                break;
            default:
                break;
        }
    }


    private void showDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

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
