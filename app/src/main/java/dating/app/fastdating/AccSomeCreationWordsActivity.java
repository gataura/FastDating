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

public class AccSomeCreationWordsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText pochtaEdit;
    private EditText parolEdit;
    private EditText parolPodtvEdit;
    Employee newRab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_some_creation_words);
        initView();

        newRab = new Employee();
    }

    private void initView() {
        pochtaEdit = findViewById(R.id.pochta_create_acc);
        parolEdit = findViewById(R.id.parol_create_acc);
        parolPodtvEdit = findViewById(R.id.bla_pass_confirm);
        Button mSignInBtn = findViewById(R.id.bla_knopka_sign);
        mSignInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.bla_knopka_sign) {
            String email = pochtaEdit.getText().toString();
            String password = parolEdit.getText().toString();
            String passwordRepeat = parolPodtvEdit.getText().toString();

            if (!password.equals(passwordRepeat)) {
                Toast.makeText(this, "Password is not similar", Toast.LENGTH_SHORT).show();
                return;
            }


            if (addUser(email, password)) {
                intent = new Intent(this, QuestionActivity.class);
                intent.putExtra("newEmployee", newRab);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "User exists", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean addUser(String email, String password) {
        Presenter presenter = new Presenter();

        newRab.email = email;
        newRab.password = password;

        return presenter.addNewUser(newRab);

    }
}
