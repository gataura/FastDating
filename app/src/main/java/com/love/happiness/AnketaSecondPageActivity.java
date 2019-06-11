package com.love.happiness;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.love.happiness.Model.Employee;
import com.love.happiness.Presenter.Presenter;

public class AnketaSecondPageActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton mySinglePersonRb;
    /**
     *
     */
    private Button myNextBtn;
    private EditText myAboutYouEt;
    private EditText myOptionalTextEt;
    private RadioGroup myMaritalStatusRadiogroup;
    Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anketa_second_page);
        initView();

        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");
    }

    private void initView() {
        mySinglePersonRb = findViewById(R.id.you_prefer_singlePerson);
        RadioButton mCoupleRb = findViewById(R.id.you_prefer_couple);
        myNextBtn = findViewById(R.id.the_button_next_page);
        myNextBtn.setOnClickListener(this);
        myAboutYouEt = findViewById(R.id.enter_info_about_you);
        myOptionalTextEt = findViewById(R.id.enter_info_optional_text);
        myMaritalStatusRadiogroup = findViewById(R.id.radiogroup_marital_status);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.the_button_next_page) {
            if (isMaritalStatusEmpty() && isOtherDataEmpty()) {

                updateUserProfile();

                intent = new Intent(this, AnketaLastPageActivity.class);
                intent.putExtra("newEmployee", newEmployee);
                startActivity(intent);
            }
        }
    }

    private void updateUserProfile() {


        int maritalStatus = mySinglePersonRb.isChecked() ? 1 : 2;
        String aboutYou = myAboutYouEt.getText().toString();
        String wantToMakeClear = myOptionalTextEt.getText().toString();
        ;

        newEmployee.maritalStatus = maritalStatus;
        newEmployee.aboutYou = aboutYou;
        newEmployee.wantToMakeClear = wantToMakeClear;

        Presenter presenter = new Presenter();
        presenter.updateUserProfile(newEmployee);
    }

    private boolean isMaritalStatusEmpty() {
        if (myMaritalStatusRadiogroup.getCheckedRadioButtonId() != -1) {
            return true;
        }
        Toast.makeText(this, "Choose Marital status", Toast.LENGTH_SHORT).show();
        return false;

    }


    private boolean isOtherDataEmpty() {
        if (!myAboutYouEt.getText().toString().equals("")) {
            return true;
        }
        Toast.makeText(this, "Add other data", Toast.LENGTH_SHORT).show();
        return false;

    }


}
