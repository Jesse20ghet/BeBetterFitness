package com.example.jessemoreland.bebetterfitness.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Collections.WorkoutCollection;
import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.DAL.Workout;
import com.example.jessemoreland.bebetterfitness.R;

public class EditIntermediateActivity extends Activity {

    private String editContext;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.edit_intermediate);
            Bundle extras = getIntent().getExtras();

            if(extras != null)
                editContext = extras.getString("editContext");

            if(editContext.equals("Workout")) // Populate list with workouts
            {
                WorkoutCollection workoutCollection = new WorkoutCollection(this);
                workoutCollection.LoadAll(this);

                Spinner spinRef = (Spinner)findViewById(R.id.editIntermediateSpinner);
                ArrayAdapter<Workout> workoutAdapter;
                workoutAdapter = new ArrayAdapter<Workout>(this, R.layout.spinnerlayout, workoutCollection._collection);
                spinRef.setAdapter(workoutAdapter);
            }
            else if(editContext.equals("Lift")) // Populate list with lifts
            {
                LiftCollection liftCollection = new LiftCollection(this);
                liftCollection.LoadAll(this);

                Spinner spinRef = (Spinner)findViewById(R.id.editIntermediateSpinner);
                ArrayAdapter<Lift> liftAdapter;
                liftAdapter = new ArrayAdapter<Lift>(this, R.layout.spinnerlayout, liftCollection._collection);
                spinRef.setAdapter(liftAdapter);
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void GoClick(View view)
    {
        if(editContext.equals("Workout"))
        {
            OpenEditAWorkoutView(view);
        }
        else if(editContext.equals("Lift"))
        {
            OpenEditALiftView(view);
        }
    }

    public void OpenEditAWorkoutView(View view)
    {
        Spinner spinRef = (Spinner) findViewById(R.id.editIntermediateSpinner);
        Workout selectedWorkout = (Workout)spinRef.getSelectedItem();

        try
        {
            Intent myIntent = new Intent(view.getContext(), EditAWorkoutActivity.class);
            myIntent.putExtra("WorkoutId", selectedWorkout.Id);
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void OpenEditALiftView(View view)
    {

    }


}