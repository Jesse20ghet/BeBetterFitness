package com.example.jessemoreland.bebetterfitness.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Collections.WorkoutCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.R;

import java.sql.SQLException;

public class ConfigurationActivity extends Activity {

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.configuration_main);

            updateConfigurationSummary();
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
}