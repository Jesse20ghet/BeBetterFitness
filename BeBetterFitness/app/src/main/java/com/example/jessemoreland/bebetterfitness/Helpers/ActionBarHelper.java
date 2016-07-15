package com.example.jessemoreland.bebetterfitness.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jessemoreland.bebetterfitness.Activities.ConfigurationActivity;
import com.example.jessemoreland.bebetterfitness.Activities.DebugActivity;
import com.example.jessemoreland.bebetterfitness.Activities.ExerciseActivity;
import com.example.jessemoreland.bebetterfitness.Activities.NutritionActivity;
import com.example.jessemoreland.bebetterfitness.Activities.PerformWorkoutActivity;
import com.example.jessemoreland.bebetterfitness.Activities.RequestActivity;
import com.example.jessemoreland.bebetterfitness.DAL.CurrentWorkout;
import com.example.jessemoreland.bebetterfitness.MainActivity;
import com.example.jessemoreland.bebetterfitness.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JesseMoreland on 7/6/2016.
 */
public class ActionBarHelper
{
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private Activity currentActivity;
    //String[] osArray = { "Home", "Exercise", "Nutrition", "Configuration", "Request", "Debug" };
    List<String> osArray2 = new ArrayList<String>()
    {{
        add("Home");
        add("Exercise");
        add("Nutrition");
        add("Configuration");
        add("Request");
        add("Debug");
    }};

    public ActionBarHelper( ListView mDrawerList, DrawerLayout mDrawerLayout, ActionBarDrawerToggle mDrawerToggle, String mActivityTitle, Activity currentActivity)
    {
        this.mDrawerList = mDrawerList;
        this.mDrawerLayout = mDrawerLayout;
        this.mDrawerToggle = mDrawerToggle;
        this.mActivityTitle = mActivityTitle;
        this.currentActivity = currentActivity;
    }

    public void SetupActionBar(final AppCompatActivity currentActivity)
    {
        addDrawerItems(currentActivity);
        setupDrawer(currentActivity);

        mDrawerToggle = new ActionBarDrawerToggle(currentActivity, mDrawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                currentActivity.getSupportActionBar().setTitle("Navigation!");
                currentActivity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                currentActivity.getSupportActionBar().setTitle(mActivityTitle);
                currentActivity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        currentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentActivity.getSupportActionBar().setHomeButtonEnabled(true);
    }

    public static void setupDrawer(DrawerLayout mDrawerLayout, ActionBarDrawerToggle mDrawerToggle)
    {
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setupDrawer(final AppCompatActivity currentActivity)
    {
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawerItems(final AppCompatActivity currentActivity)
    {
        CurrentWorkoutHelper cwh = new CurrentWorkoutHelper(currentActivity);

        if(cwh.GetCurrentWorkoutId() > 0)
        {
            osArray2.add(0, "Current Workout");
        }

        mAdapter = new ArrayAdapter<String>(currentActivity, android.R.layout.simple_list_item_1, osArray2);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
                Toast.makeText(currentActivity, Integer.toString(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectItem(int position)
    {
        String selectedItemString = osArray2.get(position);

        Intent intent;

        switch(selectedItemString)
        {
            case "Current Workout":
                intent = new Intent(currentActivity, PerformWorkoutActivity.class);
                CurrentWorkout cw = new CurrentWorkout(currentActivity);
                cw.Load();

                intent.putExtra("WorkoutId", cw.Id);
                intent.putExtra("ContinueWorkout", true);

                currentActivity.startActivity(intent);
                break;

            case "Home":
                intent = new Intent(currentActivity, MainActivity.class);
                currentActivity.startActivity(intent);
                break;

            case "Exercise":
                intent = new Intent(currentActivity, ExerciseActivity.class);
                currentActivity.startActivity(intent);
                break;

            case "Nutrition":
                intent = new Intent(currentActivity, NutritionActivity.class);
                currentActivity.startActivity(intent);
                break;

            case "Configuration":
                intent = new Intent(currentActivity, ConfigurationActivity.class);
                currentActivity.startActivity(intent);
                break;

            case "Request":
                intent = new Intent(currentActivity, RequestActivity.class);
                currentActivity.startActivity(intent);
                break;

            case "Debug":
                intent = new Intent(currentActivity, DebugActivity.class);
                currentActivity.startActivity(intent);
                break;
        }

    }
}
