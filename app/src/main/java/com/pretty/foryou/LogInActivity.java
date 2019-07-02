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

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mailEdit;
    private EditText passEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initView();


    }

    private void initView() {
        mailEdit = findViewById(R.id.bla_email_create_acc);
        passEdit = findViewById(R.id.bla_pass_create_acc);
        Button mSignInBtn = findViewById(R.id.bla_button_sign);
        mSignInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bla_button_sign:

                String email = mailEdit.getText().toString();
                String password = passEdit.getText().toString();

                Employee newEmployee = new Employee();
                newEmployee.email = email;
                newEmployee.password = password;

                Presenter presenter = new Presenter();

                if (presenter.isLoginCredentialsCorrect(newEmployee)) {

                    newEmployee = presenter.getByEmail(newEmployee.email);
                    intent = new Intent(this, TheFirstActivity.class);
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
