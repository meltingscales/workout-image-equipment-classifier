package com.scanfit.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ViewSwitcher;

import com.scanfit.R;
import com.scanfit.modeling.user.BodyType;
import com.scanfit.modeling.user.Person;

import java.util.Date;


public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ViewSwitcher viewSwitcher = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home); // For debug purposes.

                    viewSwitcher.removeAllViews();
                    inflateUserView();
                    return true;

                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_gear);
                    startActivity(new Intent(getApplicationContext(), CameraActivity.class)); //TODO: Instead of starting CameraActivity, make a 'gear' view.
                    return true;

                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    //TODO Make an activity for this too.
                    return true;
            }
            return false;
        }
    };

    private void inflateUserView() {
        viewSwitcher.addView(getLayoutInflater().inflate(R.layout.fragment_user_view, null)); // Add and inflate our user view.
    }

    /***
     * Populate our UserView with a Person's info.
     * @param person A Person.
     */
    private void populateUserView(Person person) {
        ((EditText) findViewById(R.id.editTextUsername)).setText(person.getUsername());
        ((EditText) findViewById(R.id.editTextEmail)).setText(person.getEmail());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewSwitcher = findViewById(R.id.viewSwitcher);

        viewSwitcher.removeAllViews();
        inflateUserView();
        populateUserView(new Person("jack", "jacktheripped", "jack@hotbodshotrods.ru", "cool gender", BodyType.NONBINARY, new Date()));
    }

}
