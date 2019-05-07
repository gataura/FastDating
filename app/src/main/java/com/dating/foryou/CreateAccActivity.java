package com.dating.foryou;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dating.foryou.Model.Employee;
import com.dating.foryou.Presenter.Presenter;

public class CreateAccActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailEt;
    private EditText mPasswordEt;
    private EditText mPasswordRepeatEt;
    Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        initView();

        newEmployee = new Employee();
    }

    private void initView() {
        mEmailEt = findViewById(R.id.enter_email);
        mPasswordEt = findViewById(R.id.enter_password);
        mPasswordRepeatEt = findViewById(R.id.enter_password_repeat);
        Button mSignInBtn = findViewById(R.id.button_sign_in);
        mSignInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_sign_in:

                String email = mEmailEt.getText().toString();
                String password = mPasswordEt.getText().toString();
                String passwordRepeat = mPasswordRepeatEt.getText().toString();

                if (!password.equals(passwordRepeat)) {
                    Toast.makeText(this, "Password is not similar", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (addNewUser(email, password)) {
                    intent = new Intent(this, QuestionsActivity.class);
                    intent.putExtra("newEmployee", newEmployee);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this, "User exists", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private boolean addNewUser(String email, String password) {
        Presenter presenter = new Presenter();

        newEmployee.email = email;
        newEmployee.password = password;

        return presenter.addNewUser(newEmployee);

    }
}
