package com.pretty.foryou;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pretty.foryou.Model.Employee;
import com.pretty.foryou.Presenter.Presenter;

public class AccCreationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mailEdit;
    private EditText passEdit;
    private EditText passConfirmEdit;
    Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_creation);
        initView();

        newEmployee = new Employee();
    }

    private void initView() {
        mailEdit = findViewById(R.id.bla_email_create_acc);
        passEdit = findViewById(R.id.bla_pass_create_acc);
        passConfirmEdit = findViewById(R.id.bla_pass_confirm);
        Button mSignInBtn = findViewById(R.id.bla_button_sign);
        mSignInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.bla_button_sign) {
            String email = mailEdit.getText().toString();
            String password = passEdit.getText().toString();
            String passwordRepeat = passConfirmEdit.getText().toString();

            if (!password.equals(passwordRepeat)) {
                Toast.makeText(this, "Password is not similar", Toast.LENGTH_SHORT).show();
                return;
            }


            if (addUser(email, password)) {
                intent = new Intent(this, QuestionActivity.class);
                intent.putExtra("newEmployee", newEmployee);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "User exists", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean addUser(String email, String password) {
        Presenter presenter = new Presenter();

        newEmployee.email = email;
        newEmployee.password = password;

        return presenter.addNewUser(newEmployee);

    }
}
