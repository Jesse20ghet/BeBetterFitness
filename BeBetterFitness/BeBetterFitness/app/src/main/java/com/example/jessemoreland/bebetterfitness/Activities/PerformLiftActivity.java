package com.example.jessemoreland.bebetterfitness.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.CurrentLiftCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Collections.WorkoutLiftCollection;
import com.example.jessemoreland.bebetterfitness.DAL.CompletedLift;
import com.example.jessemoreland.bebetterfitness.DAL.CurrentLift;
import com.example.jessemoreland.bebetterfitness.DAL.CurrentWorkout;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.DAL.Workout;
import com.example.jessemoreland.bebetterfitness.DAL.WorkoutLift;
import com.example.jessemoreland.bebetterfitness.Helpers.CurrentWorkoutHelper;
import com.example.jessemoreland.bebetterfitness.R;

import java.util.Date;
import java.util.Random;

public class PerformLiftActivity extends AppCompatActivity
{
    Lift currentLift = new Lift(this);
    int reps;
    int sets;

    public void onCreate(final Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.perform_a_lift);

            // Gets parameters passed into this intent, WorkoutID in this case
            Bundle extras = getIntent().getExtras();
            if(extras != null)
            {
                currentLift.Id = extras.getInt("LiftId");
                currentLift.Load();
            }

            // Generate random reps and sets
            Random random = new Random();
            reps = random.nextInt(6) + 6;
            sets = random.nextInt(2) + 4;

            //Set lift labels
            TextView liftNameTextView = (TextView)findViewById(R.id.performALiftLiftName);
            liftNameTextView.setText(currentLift.LiftName);

            TextView liftSetsRepsTextView = (TextView)findViewById(R.id.performALiftSetsReps);
            liftSetsRepsTextView.setText("Reps " + reps + " | " + "Sets " + sets);

            TextView liftDescriptionTextView = (TextView)findViewById(R.id.performALiftLiftDescription);
            liftDescriptionTextView.setText(currentLift.LiftDescription);

            // TODO: Set Up picture carousel

            SeekBar finishSeekBarRef = (SeekBar)findViewById(R.id.performALiftFinishLiftSeekBar);
            finishSeekBarRef.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (seekBar.getProgress() <= 95)
                    {
                        seekBar.setProgress(0);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onProgressChanged(final SeekBar seekBar, int progress, boolean fromUser) {
                    if (seekBar.getProgress() > 95 && fromUser) {
                        try
                        {
                            new AlertDialog.Builder(seekBar.getContext())
                                .setTitle("Lift Completed")
                                .setMessage("Are you sure you want to complete this lift?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        CompletedLift completedLift = new CompletedLift(seekBar.getContext());
                                        completedLift.LiftId = currentLift.Id;
                                        completedLift.Reps = reps;
                                        completedLift.Sets = sets;
                                        completedLift.WorkoutId = -1;
                                        completedLift.Save();

                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public final void onClick(DialogInterface dialog, int which) {
                                        seekBar.setProgress(0);
                                        seekBar.setEnabled(true);
                                    }
                                }).show();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setTitle("Lift NOT Completed")
                .setMessage("Leaving this screen will lose progress. Do you want to leave?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public final void onClick(DialogInterface dialog, int which)
                    {

                    }
                }).show();
    }
}