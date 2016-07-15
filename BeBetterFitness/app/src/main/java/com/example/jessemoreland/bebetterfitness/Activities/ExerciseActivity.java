package com.example.jessemoreland.bebetterfitness.Activities;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftCollection;
import com.example.jessemoreland.bebetterfitness.Helpers.ActionBarHelper;
import com.example.jessemoreland.bebetterfitness.Helpers.CurrentWorkoutHelper;
import com.example.jessemoreland.bebetterfitness.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ExerciseActivity extends AppCompatActivity {

    public int tableRowCount = 1;
    private LiftCollection liftCollection = new LiftCollection(this);
    CurrentWorkoutHelper currentWorkoutHelper;
    ActionBarDrawerToggle mDrawerToggle;

    public void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            currentWorkoutHelper = new CurrentWorkoutHelper(this);
            liftCollection.LoadAll(this);
            setContentView(R.layout.exercise_main);

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

    public void ChooseAWorkout(final View view)
    {
        try
        {
            final int test = currentWorkoutHelper.GetCurrentWorkoutId();

            // If
            if(test != -1)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Workout In Progress")
                        .setMessage("You currently have an active workout. What do you want to do?")
                        .setPositiveButton("Continue Workout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //TODO: Send to PerformAWorkoutActivity with some flag set to load from
                                Intent myIntent = new Intent(view.getContext(), PerformWorkoutActivity.class);
                                myIntent.putExtra("WorkoutId", test);
                                myIntent.putExtra("ContinueWorkout", true);
                                startActivity(myIntent);
                            }
                        })
                        .setNegativeButton("End Current Workout", new DialogInterface.OnClickListener() {
                            @Override
                            public final void onClick(DialogInterface dialog, int which)
                            {
                                CurrentWorkoutHelper cwh = new CurrentWorkoutHelper(view.getContext());
                                cwh.DeleteCurrentWorkout();
                                Intent myIntent = new Intent(view.getContext(), ChooseAWorkoutIntermediateActivity.class);
                                startActivity(myIntent);
                            }
                        })
                        .setNeutralButton("Nothing", new DialogInterface.OnClickListener() {
                        @Override
                            public final void onClick(DialogInterface dialog, int which)
                            {
                            }
                        }).show();
            }

            else
            {
                Intent myIntent = new Intent(view.getContext(), ChooseAWorkoutIntermediateActivity.class);
                startActivityForResult(myIntent, 0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void onResume()
    {
        super.onResume();

        // Reload popout, there could be a current workout
        ListView mDrawerList = (ListView)findViewById(R.id.navList);
        DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        final String mActivityTitle = getTitle().toString();

        ActionBarHelper abh = new ActionBarHelper(mDrawerList, mDrawerLayout, mDrawerToggle, mActivityTitle, this);
        abh.SetupActionBar(this);
    }

    @Override
    public void onBackPressed()
    { }

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
    //-----------------------------------------------------
}