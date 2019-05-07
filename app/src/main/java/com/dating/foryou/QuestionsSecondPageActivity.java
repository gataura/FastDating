package com.dating.foryou;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dating.foryou.Model.Employee;
import com.dating.foryou.Presenter.Presenter;

public class QuestionsSecondPageActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton mSinglePersonRb;
    /**
     *
     */
    private Button mNextBtn;
    private EditText mAboutYouEt;
    private EditText mOptionalTextEt;
    private RadioGroup mMaritalStatusRadiogroup;
    Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_second_page);
        initView();

        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");
    }

    private void initView() {
        mSinglePersonRb = findViewById(R.id.your_rb_singlePerson);
        RadioButton mCoupleRb = findViewById(R.id.your_rb_couple);
        mNextBtn = findViewById(R.id.button_next);
        mNextBtn.setOnClickListener(this);
        mAboutYouEt = findViewById(R.id.enter_about_you);
        mOptionalTextEt = findViewById(R.id.enter_optional_text);
        mMaritalStatusRadiogroup = findViewById(R.id.radiogroup_marital_status);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_next:

                if (isMaritalStatusEmpty() && isOtherDataEmpty()) {

                    updateUserProfile();

                    intent = new Intent(this, QuestionsLastPageActivity.class);
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
