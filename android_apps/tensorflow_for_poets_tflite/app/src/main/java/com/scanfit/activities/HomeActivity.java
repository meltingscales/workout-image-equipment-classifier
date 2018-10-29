package com.scanfit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.scanfit.R;

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

                    viewSwitcher.removeAllViews(); // Remove other views.

                    View v = getLayoutInflater().inflate(R.layout.fragment_user_view, null); // Inflate our 'user' view.
                    viewSwitcher.addView(v); // Add it.

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewSwitcher = findViewById(R.id.viewSwitcher);
    }

}
