package com.scanfit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import com.scanfit.EquipmentArrayAdapter;
import com.scanfit.R;
import com.scanfit.modeling.fitness.Equipment;
import com.scanfit.modeling.user.BodyType;
import com.scanfit.modeling.user.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private static Person defaultPerson = new Person("Jack Skellington", "jacktheripped", "jacktheboneman@hotbodshotrods.ru",
            "cool gender", BodyType.NONBINARY, new Date());

    private static ArrayList<Equipment> defaultEquipment = new ArrayList<Equipment>() {{
        add(new Equipment("barbell", "lift it", "throw it", "eat it"));
        add(new Equipment("treadmill", "run", "walk"));
    }};

    private ViewSwitcher viewSwitcher = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {

                case R.id.navigation_home:

                    viewSwitcher.removeAllViews();
                    inflateUserView();
                    populateUserView(defaultPerson);

                    return true;

                case R.id.navigation_gear:

                    viewSwitcher.removeAllViews();
                    inflateGearView();
                    populateGearView(defaultEquipment);

                    return true;

                case R.id.navigation_notifications:

                    viewSwitcher.removeAllViews();
                    //TODO Make an activity for this too.
                    return true;
            }

            return false;

        }
    };


    private void inflateUserView() {
        viewSwitcher.addView(getLayoutInflater().inflate(R.layout.fragment_user_view, null)); // Add and inflate our user view.
    }

    private void inflateGearView() {
        viewSwitcher.addView(getLayoutInflater().inflate(R.layout.fragment_gear_view, null)); // Add and inflate our gear view.
    }

    /***
     * Populate our UserView with a Person's info.
     * @param person A Person.
     */
    private void populateUserView(Person person) {
        ((EditText) findViewById(R.id.editTextUsername)).setText(person.getUsername());
        ((EditText) findViewById(R.id.editTextEmail)).setText(person.getEmail());
    }

    /**
     * Populate our GearView with a list of Equipment.
     *
     * @param equipments A list of Equipment.
     */
    private void populateGearView(List<Equipment> equipments) {
        ArrayAdapter<Equipment> equipmentArrayAdapter = new EquipmentArrayAdapter(this, R.id.listViewGear, equipments);

        ListView listView = findViewById(R.id.listViewGear);

        listView.setAdapter(equipmentArrayAdapter);

        findViewById(R.id.buttonAddGear).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), CameraActivity.class)); //TODO: Instead of starting CameraActivity, give them a choice
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewSwitcher = findViewById(R.id.viewSwitcher);
        viewSwitcher.removeAllViews();
        inflateUserView();
        populateUserView(new Person("Jack Skellington", "jacktheripped", "jacktheboneman@hotbodshotrods.ru",
                "cool gender", BodyType.NONBINARY, new Date()));
    }

}
