package com.pretty.foryou;

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

import com.pretty.foryou.Model.Employee;
import com.pretty.foryou.Presenter.Presenter;

import java.util.Calendar;

import me.angrybyte.numberpicker.listener.OnValueChangeListener;
import me.angrybyte.numberpicker.view.ActualNumberPicker;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nickEdit;
    private ActualNumberPicker heightPicker;
    private TextView birthdayTv;



    private TextView myHeightTv;
    private TextView myWeightTv;
    private ActualNumberPicker myPickerWeightActual;
    private Spinner myRaceSpinner;

    Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initView();

        setListenerToPickerHeight();
        setListenerToPickerWeight();
        setRacesToSpinner();


        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");

        if (newEmployee.name!=null) {
            nickEdit.setText(newEmployee.name);

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
        heightPicker.setListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                myHeightTv.setText(String.valueOf(newValue));
            }
        });
    }


    private void initView() {

        nickEdit = findViewById(R.id.enter_your_nickname);
        Button mNextBtn = findViewById(R.id.bla_btn_n_page);
        mNextBtn.setOnClickListener(this);
        heightPicker = findViewById(R.id.height_picker_bla);

        CardView mViewNicknameCard = findViewById(R.id.card_view_nickname);
        CardView mViewBirthdayCard = findViewById(R.id.card_view_birthday);
        birthdayTv = findViewById(R.id.bla_birth_date_picker);
        birthdayTv.setOnClickListener(this);
        LinearLayout mBirthLl = findViewById(R.id.birth_date_card);
        myHeightTv = findViewById(R.id.bla_height_show);
        myWeightTv = findViewById(R.id.bla_weight_show);
        myPickerWeightActual = findViewById(R.id.bla_weight_picker);


        myRaceSpinner = findViewById(R.id.bla_spin_race);
        LinearLayout mRaceLl = findViewById(R.id.ppicker_race);
        CardView mViewRaceCard = findViewById(R.id.card_view_race);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bla_btn_n_page:
                if (isNickEmpty() && isBirthdayEmpty()) {

                    updateUserProfile();

                    intent = new Intent(this, AnketaSecondPageActivity.class);
                    intent.putExtra("newEmployee", newEmployee);
                    startActivity(intent);
                }
                break;
            case R.id.bla_birth_date_picker:
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
                        birthdayTv.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private boolean isNickEmpty() {
        if (!nickEdit.getText().toString().isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Choose Nick name", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isBirthdayEmpty() {
        if (!birthdayTv.getText().toString().isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Choose your Birthday", Toast.LENGTH_SHORT).show();
        return false;
    }


    private void updateUserProfile() {
        String name = nickEdit.getText().toString();
        String birthday = birthdayTv.getText().toString();
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
