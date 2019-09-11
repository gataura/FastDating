package datings.apps.fastdatings;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import datings.apps.fastdatings.Model.Employee;
import datings.apps.fastdatings.Presenter.Presenter;


public class MyAnketaSecsPagaActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton radioSingleKnopka;
    /**
     *
     */
    private Button sledKnopka;
    private EditText infaOTebe;
    private EditText opciiEdit;
    private RadioGroup myMaritalStatusRadiogroup;
    Employee newRab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_anketa_secs_paga);
        initView();

        newRab = (Employee) getIntent().getSerializableExtra("newEmployee");
    }

    private void initView() {
        radioSingleKnopka = findViewById(R.id.bla_vibor_single);
        RadioButton mCoupleRb = findViewById(R.id.bls_vibor_couple);
        sledKnopka = findViewById(R.id.knopka_sled_page);
        sledKnopka.setOnClickListener(this);
        infaOTebe = findViewById(R.id.infa_o_tebe);
        opciiEdit = findViewById(R.id.infa_optimus_txt);
        myMaritalStatusRadiogroup = findViewById(R.id.radiogroup_marital_status);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.knopka_sled_page) {
            if (isMaritalStatusEmpty() && isOtherDataEmpty()) {

                updateUserProfile();

                intent = new Intent(this, QuestionLPageActivity.class);
                intent.putExtra("newEmployee", newRab);
                startActivity(intent);
            }
        }
    }

    private void updateUserProfile() {


        int maritalStatus = radioSingleKnopka.isChecked() ? 1 : 2;
        String aboutYou = infaOTebe.getText().toString();
        String wantToMakeClear = opciiEdit.getText().toString();
        ;

        newRab.maritalStatus = maritalStatus;
        newRab.aboutYou = aboutYou;
        newRab.wantToMakeClear = wantToMakeClear;

        Presenter presenter = new Presenter();
        presenter.updateUserProfile(newRab);
    }

    private boolean isMaritalStatusEmpty() {
        if (myMaritalStatusRadiogroup.getCheckedRadioButtonId() != -1) {
            return true;
        }
        Toast.makeText(this, "Choose Marital status", Toast.LENGTH_SHORT).show();
        return false;

    }


    private boolean isOtherDataEmpty() {
        if (!infaOTebe.getText().toString().equals("")) {
            return true;
        }
        Toast.makeText(this, "Add other data", Toast.LENGTH_SHORT).show();
        return false;

    }


}
