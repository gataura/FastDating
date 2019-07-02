package com.pretty.foryou;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.pretty.foryou.Model.Employee;
import com.pretty.foryou.Presenter.Presenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class BlaBlaStartingActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginButton myButtonFacebookLogin;
    private CallbackManager myCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bla_bla_starting);
        initView();
        myCallbackManager = CallbackManager.Factory.create();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        myCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initView() {

        Button mCreateAccountBtn = findViewById(R.id.btn_create_acc);
        mCreateAccountBtn.setOnClickListener(this);
        Button mSignInBtn = findViewById(R.id.bla_button_sign);
        mSignInBtn.setOnClickListener(this);
        View mView1 = findViewById(R.id.view1);
        LinearLayout mSignInLinearLayout = findViewById(R.id.linearLayout_sign_in);
        myButtonFacebookLogin = findViewById(R.id.facebook_login_button);
        myButtonFacebookLogin.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));

        myButtonFacebookLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        Intent intent;
        switch (v.getId()) {

            case R.id.btn_create_acc:
                intent = new Intent(this, AccCreationActivity.class);
                startActivity(intent);
                break;
            case R.id.bla_button_sign:
                intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
                break;
            case R.id.facebook_login_button:

                myButtonFacebookLogin.registerCallback(myCallbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                setFacebookData(loginResult);
                            }

                            @Override
                            public void onCancel() {
                            }

                            @Override
                            public void onError(FacebookException exception) {
                            }
                        });
                break;
            default:
                break;
        }


    }

    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response", response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
//                            String lastName = response.getJSONObject().getString("last_name");

                            Employee employee = new Employee();
                            employee.email = email;
                            employee.name = firstName;

                            Presenter presenter = new Presenter();

                            Intent intent1;

                            if (presenter.isUserExists(employee)) {
                                intent1 = new Intent(getApplicationContext(), TheFirstActivity.class);
                            } else {
                                intent1 = new Intent(getApplicationContext(), QuestionActivity.class);

                            }
                            intent1.putExtra("newEmployee", employee);
                            startActivity(intent1);
                            presenter.addNewUser(employee);
                            finish();


//                            Profile profile = Profile.getCurrentProfile();
//                            String id = profile.getId();
//                            String link = profile.getLinkUri().toString();
//                            Log.i("Link", link);
//                            if (Profile.getCurrentProfile() != null) {
//                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
//                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
