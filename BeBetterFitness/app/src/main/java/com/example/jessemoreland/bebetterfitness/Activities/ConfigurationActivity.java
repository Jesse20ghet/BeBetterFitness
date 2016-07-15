package com.example.jessemoreland.bebetterfitness.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Collections.WorkoutCollection;
import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.Helpers.ActionBarHelper;
import com.example.jessemoreland.bebetterfitness.R;

import java.sql.SQLException;

public class ConfigurationActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    ActionBarDrawerToggle mDrawerToggle;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.configuration_main);

            updateConfigurationSummary();

            // ===================================================
            // Slide out set up
            // ===================================================
            ListView mDrawerList = (ListView)findViewById(R.id.navList);
            DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
            final String mActivityTitle = getTitle().toString();
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    getSupportActionBar().setTitle("Navigation!");
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    getSupportActionBar().setTitle(mActivityTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };
            ActionBarHelper abh = new ActionBarHelper(mDrawerList, mDrawerLayout, mDrawerToggle, mActivityTitle, this);
            abh.SetupActionBar(this);
            // ======================================================
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void onResume()
    {
        super.onResume();
        updateConfigurationSummary();

        // Reload popout, there could be a current workout
        ListView mDrawerList = (ListView)findViewById(R.id.navList);
        DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        final String mActivityTitle = getTitle().toString();

        ActionBarHelper abh = new ActionBarHelper(mDrawerList, mDrawerLayout, mDrawerToggle, mActivityTitle, this);
        abh.SetupActionBar(this);

    }

    public void OpenCreateAWorkoutView(View view)
    {
        try
        {
            Intent myIntent = new Intent(view.getContext(), CreateAWorkoutActivity.class);
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void OpenCreateALiftView(View view)
    {
        try
        {
            Intent myIntent = new Intent(view.getContext(), CreateALiftActivity.class);
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void OpenEditAWorkoutView(View view)
    {
        try
        {
            Intent myIntent = new Intent(view.getContext(), EditIntermediateActivity.class);
            myIntent.putExtra("editContext", "Workout");
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void OpenEditALiftView(View view)
    {
        try
        {
            Intent myIntent = new Intent(view.getContext(), EditIntermediateActivity.class);
            myIntent.putExtra("editContext", "Lift");
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void updateConfigurationSummary()
    {
        TextView configStats = ((TextView) findViewById(R.id.configStatsEditText));

        LiftCollection liftCollection = new LiftCollection(this);
        liftCollection.LoadAll(this);
        int numberOfLifts = liftCollection.Count();

        WorkoutCollection workoutCollection = new WorkoutCollection(this);
        workoutCollection.LoadAll(this);
        int numberOfWorkouts = workoutCollection.Count();

        configStats.setText("");
        configStats.append("Number of Lifts: " + numberOfLifts + "\n");
        configStats.append("Number of Workouts: " + numberOfWorkouts + "\n");
    }

    @Override
    public void onBackPressed() {
    }

    //-----------------------------------------------------
    // Toolbar functions
    //-----------------------------------------------------
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}