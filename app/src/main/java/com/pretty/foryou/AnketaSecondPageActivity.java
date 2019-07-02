package com.pretty.foryou;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pretty.foryou.Model.Employee;
import com.pretty.foryou.Presenter.Presenter;

public class AnketaSecondPageActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton radioSingleButton;
    /**
     *
     */
    private Button nextButton;
    private EditText infoAboutEdit;
    private EditText optEditText;
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
        radioSingleButton = findViewById(R.id.bla_predp_single);
        RadioButton mCoupleRb = findViewById(R.id.bls_predpo_couple);
        nextButton = findViewById(R.id.bla_btn_n_page);
        nextButton.setOnClickListener(this);
        infoAboutEdit = findViewById(R.id.bla_inform_about);
        optEditText = findViewById(R.id.bla_inform_opt_txt);
        myMaritalStatusRadiogroup = findViewById(R.id.radiogroup_marital_status);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.bla_btn_n_page) {
            if (isMaritalStatusEmpty() && isOtherDataEmpty()) {

                updateUserProfile();

                intent = new Intent(this, QuestionLPageActivity.class);
                intent.putExtra("newEmployee", newEmployee);
                startActivity(intent);
            }
        }
    }

    private void updateUserProfile() {


        int maritalStatus = radioSingleButton.isChecked() ? 1 : 2;
        String aboutYou = infoAboutEdit.getText().toString();
        String wantToMakeClear = optEditText.getText().toString();
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
        if (!infoAboutEdit.getText().toString().equals("")) {
            return true;
        }
        Toast.makeText(this, "Add other data", Toast.LENGTH_SHORT).show();
        return false;

    }


}
