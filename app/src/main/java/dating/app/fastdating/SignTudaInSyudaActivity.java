package dating.app.fastdating;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dating.app.fastdating.Model.Employee;
import dating.app.fastdating.Presenter.Presenter;

public class SignTudaInSyudaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText pochtaEdit;
    private EditText parolEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_tuda_in_syuda);
        initView();


    }

    private void initView() {
        pochtaEdit = findViewById(R.id.pochta_create_acc);
        parolEdit = findViewById(R.id.parol_create_acc);
        Button mSignInBtn = findViewById(R.id.bla_knopka_sign);
        mSignInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bla_knopka_sign:

                String email = pochtaEdit.getText().toString();
                String password = parolEdit.getText().toString();

                Employee newEmployee = new Employee();
                newEmployee.email = email;
                newEmployee.password = password;

                Presenter presenter = new Presenter();

                if (presenter.isLoginCredentialsCorrect(newEmployee)) {

                    newEmployee = presenter.getByEmail(newEmployee.email);
                    intent = new Intent(this, SamayaPervayaActivity.class);
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
