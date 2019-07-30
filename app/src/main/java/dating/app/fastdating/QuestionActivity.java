package dating.app.fastdating;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import dating.app.fastdating.Model.Employee;
import dating.app.fastdating.Presenter.Presenter;


import java.util.Calendar;

import me.angrybyte.numberpicker.listener.OnValueChangeListener;
import me.angrybyte.numberpicker.view.ActualNumberPicker;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText imyaEdit;
    private ActualNumberPicker rostVibor;
    private TextView drText;



    private TextView moyRostText;
    private TextView moyVesText;
    private ActualNumberPicker vesVibor;
    private Spinner myRaceSpinner;

    Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initView();

        setListenerToPickerHeight();
        setListenerToPickerWeight();
        setRacesToSpinner();


        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");

        if (newEmployee.name!=null) {
            imyaEdit.setText(newEmployee.name);

        }
    }

    private void setRacesToSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.races, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        myRaceSpinner.setAdapter(adapter);
    }


    private void setListenerToPickerWeight() {
        vesVibor.setListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                moyVesText.setText(String.valueOf(newValue));
            }
        });
    }

    private void setListenerToPickerHeight() {
        rostVibor.setListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                moyRostText.setText(String.valueOf(newValue));
            }
        });
    }


    private void initView() {

        imyaEdit = findViewById(R.id.vvedi_nick);
        Button mNextBtn = findViewById(R.id.knopka_sled_page);
        mNextBtn.setOnClickListener(this);
        rostVibor = findViewById(R.id.rost_vibor);

        CardView mViewNicknameCard = findViewById(R.id.card_view_nickname);
        CardView mViewBirthdayCard = findViewById(R.id.card_view_birthday);
        drText = findViewById(R.id.pikni_dnykhu_picker);
        drText.setOnClickListener(this);
        LinearLayout mBirthLl = findViewById(R.id.birth_date_card);
        moyRostText = findViewById(R.id.rost_show);
        moyVesText = findViewById(R.id.bla_weight_show);
        vesVibor = findViewById(R.id.ves_vibor);


        myRaceSpinner = findViewById(R.id.vipad_race);
        LinearLayout mRaceLl = findViewById(R.id.ppicker_race);
        CardView mViewRaceCard = findViewById(R.id.card_view_race);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.knopka_sled_page:
                if (isNickEmpty() && isBirthdayEmpty()) {

                    updateUserProfile();

                    intent = new Intent(this, MyAnketaSecsPagaActivity.class);
                    intent.putExtra("newEmployee", newEmployee);
                    startActivity(intent);
                }
                break;
            case R.id.pikni_dnykhu_picker:
                showDatePicker();
                break;
            default:
                break;
        }
    }


    private void showDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        drText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private boolean isNickEmpty() {
        if (!imyaEdit.getText().toString().isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Choose Nick name", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isBirthdayEmpty() {
        if (!drText.getText().toString().isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Choose your Birthday", Toast.LENGTH_SHORT).show();
        return false;
    }


    private void updateUserProfile() {
        String name = imyaEdit.getText().toString();
        String birthday = drText.getText().toString();
        int weight = Integer.valueOf(moyVesText.getText().toString());
        int height = Integer.valueOf(moyRostText.getText().toString());
        String race = myRaceSpinner.getSelectedItem().toString();

        newEmployee.name = name;
        newEmployee.birthday = birthday;
        newEmployee.weight = weight;
        newEmployee.height = height;
        newEmployee.race = race;

        Presenter presenter = new Presenter();
        presenter.updateUserProfile(newEmployee);


    }
}
