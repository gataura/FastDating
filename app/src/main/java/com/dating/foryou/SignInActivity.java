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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailEt;
    private EditText mPasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();


    }

    private void initView() {
        mEmailEt = findViewById(R.id.enter_email);
        mPasswordEt = findViewById(R.id.enter_password);
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

                Employee newEmployee = new Employee();
                newEmployee.email = email;
                newEmployee.password = password;

                Presenter presenter = new Presenter();

                if (presenter.isLoginCredentialsCorrect(newEmployee)) {

                    newEmployee = presenter.getByEmail(newEmployee.email);
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra("newEmployee", newEmployee);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Not correct Login or Password", Toast.LENGTH_SHORT).show();
                }


                break;
            default:
                break;
        }
    }
}
