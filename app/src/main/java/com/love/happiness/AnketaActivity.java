package com.love.happiness;

import android.annotation.SuppressLint;
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

import com.love.happiness.Model.Employee;
import com.love.happiness.Presenter.Presenter;

import java.util.Calendar;

import me.angrybyte.numberpicker.listener.OnValueChangeListener;
import me.angrybyte.numberpicker.view.ActualNumberPicker;

public class AnketaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText myNickEt;
    private ActualNumberPicker myPickerHeightActual;
    private TextView myBirthdayTv;
    private int myHour;
    private int myMinute;


    private TextView myHeightTv;
    private TextView myWeightTv;
    private ActualNumberPicker myPickerWeightActual;
    private Spinner myRaceSpinner;

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
            myNickEt.setText(newEmployee.name);

        }
    }

    private void setRacesToSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.races, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        myRaceSpinner.setAdapter(adapter);
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


    private void initView() {

        myNickEt = findViewById(R.id.enter_your_nickname);
        Button mNextBtn = findViewById(R.id.the_button_next_page);
        mNextBtn.setOnClickListener(this);
        myPickerHeightActual = findViewById(R.id.actual_height_picker);

        CardView mViewNicknameCard = findViewById(R.id.card_view_nickname);
        CardView mViewBirthdayCard = findViewById(R.id.card_view_birthday);
        myBirthdayTv = findViewById(R.id.your_birthday_date_picker);
        myBirthdayTv.setOnClickListener(this);
        LinearLayout mBirthLl = findViewById(R.id.card_birth);
        myHeightTv = findViewById(R.id.your_height_display);
        myWeightTv = findViewById(R.id.your_weight_display);
        myPickerWeightActual = findViewById(R.id.actual_weight_picker);


        myRaceSpinner = findViewById(R.id.your_spinner_race_picker);
        LinearLayout mRaceLl = findViewById(R.id.ll_race);
        CardView mViewRaceCard = findViewById(R.id.card_view_race);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.the_button_next_page:
                if (isNickEmpty() && isBirthdayEmpty()) {

                    updateUserProfile();

                    intent = new Intent(this, AnketaSecondPageActivity.class);
                    intent.putExtra("newEmployee", newEmployee);
                    startActivity(intent);
                }
                break;
            case R.id.your_birthday_date_picker:
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
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myBirthdayTv.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private boolean isNickEmpty() {
        if (!myNickEt.getText().toString().isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Choose Nick name", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isBirthdayEmpty() {
        if (!myBirthdayTv.getText().toString().isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Choose your Birthday", Toast.LENGTH_SHORT).show();
        return false;
    }


    private void updateUserProfile() {
        String name = myNickEt.getText().toString();
        String birthday = myBirthdayTv.getText().toString();
        int weight = Integer.valueOf(myWeightTv.getText().toString());
        int height = Integer.valueOf(myHeightTv.getText().toString());
        String race = myRaceSpinner.getSelectedItem().toString();

        newEmployee.name = name;
        newEmployee.birthday = birthday;
        newEmployee.weight = weight;
        newEmployee.height = height;
        newEmployee.race = race;

        Presenter presenter = new Presenter();
        presenter.updateUserProfile(newEmployee);


    }
}
