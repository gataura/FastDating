package com.newapp2.datingapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.newapp2.datingapp.Model.Employee;
import com.newapp2.datingapp.Presenter.Presenter;

public class AnketaSecondPageActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton mSinglePersonRb;
    private RadioButton mCoupleRb;
    private Button mNextBtn;
    private EditText mAboutYouEt;
    private EditText mOptionalTextEt;
    private RadioGroup mMaritalStatusRadiogroup;
    Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anketa_second_page);
        initView();

        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");
    }

    private void initView() {
        mSinglePersonRb = (RadioButton) findViewById(R.id.rb_singlePerson);
        mCoupleRb = (RadioButton) findViewById(R.id.rb_couple);
        mNextBtn = (Button) findViewById(R.id.btn_next);
        mNextBtn.setOnClickListener(this);
        mAboutYouEt = (EditText) findViewById(R.id.et_about_you);
        mOptionalTextEt = (EditText) findViewById(R.id.et_optional_text);
        mMaritalStatusRadiogroup = (RadioGroup) findViewById(R.id.radiogroup_marital_status);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_next:

                if (isMaritalStatusEmpty() && isOtherDataEmpty()) {

                    updateUserProfile();

                    intent = new Intent(this, AnketaLastPageActivity.class);
                    intent.putExtra("newEmployee", newEmployee);
                    startActivity(intent);
                }


                break;
            default:
                break;
        }
    }

    private void updateUserProfile() {


        int maritalStatus = mSinglePersonRb.isChecked() ? 1 : 2;
        String aboutYou = mAboutYouEt.getText().toString();
        String wantToMakeClear = mOptionalTextEt.getText().toString();
        ;

        newEmployee.maritalStatus = maritalStatus;
        newEmployee.aboutYou = aboutYou;
        newEmployee.wantToMakeClear = wantToMakeClear;

        Presenter presenter = new Presenter();
        presenter.updateUserProfile(newEmployee);
    }

    private boolean isMaritalStatusEmpty() {
        if (mMaritalStatusRadiogroup.getCheckedRadioButtonId() != -1) {
            return true;
        }
        Toast.makeText(this, "Choose Marital status", Toast.LENGTH_SHORT).show();
        return false;

    }


    private boolean isOtherDataEmpty() {
        if (!mAboutYouEt.getText().toString().equals("")) {
            return true;
        }
        Toast.makeText(this, "Add other data", Toast.LENGTH_SHORT).show();
        return false;

    }


}
