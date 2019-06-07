package com.dream.best;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dream.best.Model.Employee;
import com.dream.best.Presenter.Presenter;

public class AccountCreatingActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText myEmailEt;
    private EditText myPasswordEt;
    private EditText myPasswordRepeatEt;
    Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        initView();

        newEmployee = new Employee();
    }

    private void initView() {
        myEmailEt = findViewById(R.id.enter_your_email_sign_up);
        myPasswordEt = findViewById(R.id.enter_your_password_sign_up);
        myPasswordRepeatEt = findViewById(R.id.enter_your_password_confirm_sign_up);
        Button mSignInBtn = findViewById(R.id.btn_log_in_sign_in);
        mSignInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.btn_log_in_sign_in) {
            String email = myEmailEt.getText().toString();
            String password = myPasswordEt.getText().toString();
            String passwordRepeat = myPasswordRepeatEt.getText().toString();

            if (!password.equals(passwordRepeat)) {
                Toast.makeText(this, "Password is not similar", Toast.LENGTH_SHORT).show();
                return;
            }


            if (addNewUser(email, password)) {
                intent = new Intent(this, AnketaActivity.class);
                intent.putExtra("newEmployee", newEmployee);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "User exists", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean addNewUser(String email, String password) {
        Presenter presenter = new Presenter();

        newEmployee.email = email;
        newEmployee.password = password;

        return presenter.addNewUser(newEmployee);

    }
}
