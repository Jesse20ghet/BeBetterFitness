package com.example.jessemoreland.bebetterfitness.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.WorkoutCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Workout;
import com.example.jessemoreland.bebetterfitness.R;

public class ChooseAWorkoutIntermediateActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.choose_intermediate);

            // Set header text
            TextView header = (TextView) findViewById(R.id.editIntermediateheaderText);
            header.setText("Choose a workout");

            // Set button text
            Button goButton = (Button)findViewById(R.id.editIntermediateButton);
            goButton.setText("Start workout");

            // Load all workouts
            WorkoutCollection workoutCollection = new WorkoutCollection(this);
            workoutCollection.LoadAll(this);

            // Set spinner to show all those workouts
            Spinner spinRef = (Spinner) findViewById(R.id.editIntermediateSpinner);
            ArrayAdapter<Workout> workoutAdapter;
            workoutAdapter = new ArrayAdapter<Workout>(this, R.layout.spinnerlayout, workoutCollection._collection);
            spinRef.setAdapter(workoutAdapter);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void GoClick(View view)
    {
        Spinner spinRef = (Spinner) findViewById(R.id.editIntermediateSpinner);
        Workout selectedWorkout = (Workout)spinRef.getSelectedItem();

        try
        {
            Intent myIntent = new Intent(view.getContext(), PerformWorkoutActivity.class);
            myIntent.putExtra("WorkoutId", selectedWorkout.Id);
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

}