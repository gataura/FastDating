package com.newapp2.datingapp;

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
import com.newapp2.datingapp.Model.Employee;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    private Employee newEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        newEmployee = (Employee) getIntent().getSerializableExtra("newEmployee");
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

        changeNavbarNickName(navigationView, newEmployee.name);
        changeNavbarResizedImage(navigationView);

    }

    private void changeNavbarNickName(NavigationView navigationView, String name) {

        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.nav_bar_nickname);
        nav_user.setText(name);
    }


    private void changeNavbarResizedImage(NavigationView navigationView) {

        View hView = navigationView.getHeaderView(0);
        CircularImageView circularImageView = (CircularImageView) hView.findViewById(R.id.imageView_profile_pic_from_library);
        circularImageView = loadSettingsToCircularProfileImage(circularImageView);

        if (circularImageView.getDrawable() != null) {
            Images.loadImageFromStorage(getApplicationContext(), circularImageView, newEmployee.id);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            Intent intent = new Intent(this, TermsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_messages) {
            // Handle the camera action
        } else if (id == R.id.nav_your_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("newEmployee", newEmployee);
            startActivityForResult(intent, 1);

        } else if (id == R.id.nav_free_stars) {

        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_help) {

            Intent intent = new Intent(this, TermsActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_feedback) {

            DialogFragment dialog = new FeedbackDialogFragment();
            dialog.show(getSupportFragmentManager(), "Feedback");

        } else if (id == R.id.nav_sign_out) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            LoginManager.getInstance().logOut();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                newEmployee = (Employee) data.getSerializableExtra("newEmployee");
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
