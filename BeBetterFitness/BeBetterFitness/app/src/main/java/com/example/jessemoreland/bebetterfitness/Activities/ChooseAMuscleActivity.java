package com.example.jessemoreland.bebetterfitness.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Collections.MuscleCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Muscle;
import com.example.jessemoreland.bebetterfitness.R;

import java.util.Random;

public class ChooseAMuscleActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.choose_intermediate);

            // Set header text
            TextView header = (TextView) findViewById(R.id.editIntermediateheaderText);
            header.setText("Choose a muscle");

            // Set button text
            Button goButton = (Button)findViewById(R.id.editIntermediateButton);
            goButton.setText("Start lift");

            // Load all workouts
            MuscleCollection muscleCollection = new MuscleCollection(this);
            muscleCollection.LoadAll();

            // Set spinner to show all those workouts
            Spinner spinRef = (Spinner) findViewById(R.id.editIntermediateSpinner);
            ArrayAdapter<Muscle> workoutAdapter;
            workoutAdapter = new ArrayAdapter<Muscle>(this, R.layout.spinnerlayout, muscleCollection._collection);
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
        Muscle selectedMuscle = (Muscle)spinRef.getSelectedItem();

        try
        {
            //TODO: Get lift collection with all lifts that are associated with a selected muscle
            LiftCollection liftCollection = new LiftCollection(this);

            liftCollection.LoadLiftsAssociatedToMuscleId(selectedMuscle.Id);

            if(liftCollection.Count() == 0)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Oops")
                        .setMessage("You have no lifts associated with this selected muscle.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                            }
                        }).show();

                return;
            }

            Random randGenerator = new Random();
            int minimum = 0;
            int maximum = liftCollection.Count() - 1;
            int randomLiftIndex = randGenerator.nextInt((maximum - minimum) + 1) + minimum;

            int liftId = liftCollection._collection.get(randomLiftIndex).Id;

            Intent myIntent = new Intent(view.getContext(), PerformLiftActivity.class);
            myIntent.putExtra("LiftId", liftId);
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

}