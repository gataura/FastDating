package com.dream.best;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.DialogFragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.dream.best.Model.Employee;

import org.jetbrains.annotations.NotNull;

public class TheFirstActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView myNavigationView;
    private Employee myNewEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_first);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        myNavigationView = findViewById(R.id.nav_view);
        myNavigationView.setNavigationItemSelectedListener(this);

        myNewEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");
    }

    private CircularImageView loadSettingsToCircularProfileImage(CircularImageView circularImageView) {
        // Set Border
        circularImageView.setBorderColor(getResources().getColor(R.color.accent));
        circularImageView.setBorderWidth(10);
        // Add Shadow with default param
        circularImageView.addShadow();
        // or with custom param
        circularImageView.setShadowRadius(15);
        circularImageView.setShadowColor(Color.RED);
        circularImageView.setBackgroundColor(Color.RED);
        circularImageView.setShadowGravity(CircularImageView.ShadowGravity.CENTER);
        return circularImageView;
    }

    @Override
    protected void onResume() {
        super.onResume();

        changeNavbarNickName(myNavigationView, myNewEmployee.name);
        changeNavbarResizedImage(myNavigationView);

    }

    private void changeNavbarNickName(NavigationView navigationView, String name) {

        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.navig_bar_nickname);
        nav_user.setText(name);
    }


    private void changeNavbarResizedImage(NavigationView navigationView) {

        View hView = navigationView.getHeaderView(0);
        CircularImageView circularImageView = hView.findViewById(R.id.imageView_profile_pic_from_library);
        circularImageView = loadSettingsToCircularProfileImage(circularImageView);

        if (circularImageView.getDrawable() != null) {
            AllImgs.loadImageFromStorage(getApplicationContext(), circularImageView, myNewEmployee.id);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, TermsAnConditionsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_messages) {
            // Handle the camera action
        } else if (id == R.id.nav_your_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("newEmployee", myNewEmployee);
            startActivityForResult(intent, 1);

        } else if (id == R.id.nav_free_stars) {

        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_help) {

            Intent intent = new Intent(this, TermsAnConditionsActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_feedback) {

            DialogFragment dialog = new FeedbackPageFragment();
            dialog.show(getSupportFragmentManager(), "Feedback");

        } else if (id == R.id.nav_sign_out) {
            Intent intent = new Intent(this, TheStartingActivity.class);
            startActivity(intent);
            LoginManager.getInstance().logOut();
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                myNewEmployee = (Employee) data.getSerializableExtra("newEmployee");
            }
        }
    }
}
