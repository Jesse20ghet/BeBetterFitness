package com.example.jessemoreland.bebetterfitness.Activities;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.CompletedWorkoutCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Workout;
import com.example.jessemoreland.bebetterfitness.Helpers.ActionBarHelper;
import com.example.jessemoreland.bebetterfitness.Helpers.CurrentWorkoutHelper;
import com.example.jessemoreland.bebetterfitness.Helpers.WorkoutHelper;
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
import android.widget.TextView;

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
            liftCollection.LoadAll();
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

            SetupHeaderStatistics();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void SetupHeaderStatistics()
    {
        // Top text view of exercise
        TextView headerTextView = (TextView)findViewById(R.id.topTextView);
        String headerText = new String();

        CompletedWorkoutCollection completedWorkoutCollection = new CompletedWorkoutCollection(this);
        completedWorkoutCollection.LoadAll(this);
        String totalWorkoutsCompleted = Integer.toString(completedWorkoutCollection.Count());
        headerText += "Total workouts completed: " + totalWorkoutsCompleted;

        WorkoutHelper workoutHelper = new WorkoutHelper(this);
        String averageWorkoutLength = workoutHelper.GetAverageWorkoutLength();
        headerText += "\nAverage workout time: " + averageWorkoutLength;



        headerTextView.setText(headerText);
    }

    public void ChooseAMuscle(final View view)
    {
        try
        {
            final int currentWorkoutId = currentWorkoutHelper.GetCurrentWorkoutId();

            if(currentWorkoutId != -1)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Workout In Progress")
                        .setMessage("You currently have an active workout. What do you want to do?")
                        .setPositiveButton("Continue Workout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //Send to PerformAWorkoutActivity with some flag set to load from
                                Intent myIntent = new Intent(view.getContext(), PerformWorkoutActivity.class);
                                myIntent.putExtra("WorkoutId", currentWorkoutId);
                                myIntent.putExtra("ContinueWorkout", true);
                                startActivity(myIntent);
                                return;
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
                                return;
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
                Intent myIntent = new Intent(view.getContext(), ChooseAMuscleActivity.class);
                startActivity(myIntent);
            }

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
            final int currentWorkoutId = currentWorkoutHelper.GetCurrentWorkoutId();

            if(currentWorkoutId != -1)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Workout In Progress")
                        .setMessage("You currently have an active workout. What do you want to do?")
                        .setPositiveButton("Continue Workout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //Send to PerformAWorkoutActivity with some flag set to load from
                                Intent myIntent = new Intent(view.getContext(), PerformWorkoutActivity.class);
                                myIntent.putExtra("WorkoutId", currentWorkoutId);
                                myIntent.putExtra("ContinueWorkout", true);
                                startActivity(myIntent);
                                return;
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
                                return;
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
                startActivity(myIntent);
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void RandomWorkout(final View view)
    {
        final int currentWorkoutId = currentWorkoutHelper.GetCurrentWorkoutId();

        if(currentWorkoutId != -1)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Workout In Progress")
                    .setMessage("You currently have an active workout. What do you want to do?")
                    .setPositiveButton("Continue Workout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            //Send to PerformAWorkoutActivity with some flag set to load from
                            Intent myIntent = new Intent(view.getContext(), PerformWorkoutActivity.class);
                            myIntent.putExtra("WorkoutId", currentWorkoutId);
                            myIntent.putExtra("ContinueWorkout", true);
                            startActivity(myIntent);
                            return;
                        }
                    })
                    .setNegativeButton("End Current Workout", new DialogInterface.OnClickListener() {
                        @Override
                        public final void onClick(DialogInterface dialog, int which)
                        {
                            Intent myIntent = new Intent(view.getContext(), PerformWorkoutActivity.class);
                            WorkoutHelper workoutHelper = new WorkoutHelper(getBaseContext());
                            Workout returnedWorkout = workoutHelper.GetRandomWorkout();

                            myIntent.putExtra("WorkoutId", returnedWorkout.Id);
                            startActivity(myIntent);
                            return;
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
            Intent myIntent = new Intent(view.getContext(), PerformWorkoutActivity.class);
            WorkoutHelper workoutHelper = new WorkoutHelper(this);
            Workout returnedWorkout = workoutHelper.GetRandomWorkout();

            myIntent.putExtra("WorkoutId", returnedWorkout.Id);
            startActivity(myIntent);
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

        SetupHeaderStatistics();
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