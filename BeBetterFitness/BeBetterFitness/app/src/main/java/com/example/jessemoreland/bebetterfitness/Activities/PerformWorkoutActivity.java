package com.example.jessemoreland.bebetterfitness.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import org.joda.time.DateTime;

import java.util.Date;

public class PerformWorkoutActivity extends AppCompatActivity
{
    Workout currentWorkout = new Workout(this);
    WorkoutLiftCollection workoutLiftCollection = new WorkoutLiftCollection(this);
    LiftCollection liftCollection = new LiftCollection(this);
    CurrentWorkoutHelper currentWorkoutHelper = new CurrentWorkoutHelper(this);

    public void onCreate(final Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.perform_workout);

            boolean continuingWorkout = false;

            // Gets parameters passed into this intent, WorkoutID in this case
            Bundle extras = getIntent().getExtras();
            int workoutId = -1;
            if(extras != null)
            {
                workoutId = extras.getInt("WorkoutId");
                continuingWorkout = extras.getBoolean("ContinueWorkout");
                currentWorkout.Id = workoutId;
                currentWorkout.Load(this);
            }

            // Load all workoutlifts
            workoutLiftCollection.LoadByWorkoutId(this, currentWorkout.Id);

            liftCollection.LoadAll();
            liftCollection.FilterForWorkout(currentWorkout.workoutLiftCollection);

            // Create containers with all lifts associated with this workout
            CreateLiftContainers();

            if(continuingWorkout)
            {
                SetFinishedLifts();
            }

            // Set title
            ((TextView)findViewById(R.id.permormAWorkoutTitle)).setText(currentWorkout.WorkoutName);

            SeekBar testSeekBar = (SeekBar)findViewById(R.id.performAWorkoutCompleteWorkoutSeekBar);
            testSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                                .setTitle("Workout Completed")
                                .setMessage("Are you sure you want to complete this workout?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // They said yes, end the workout now
                                        CurrentWorkout cw = new CurrentWorkout(seekBar.getContext());
                                        cw.Load(); // Load the current workout from the system(It has start time)
                                        currentWorkoutHelper.SaveCompletedWorkout(currentWorkout, cw.DateStarted, DateTime.now()); // Save this workout
                                        cw.Delete(); // Workouts over, so there is no current workout
                                        CurrentLift cl = new CurrentLift(seekBar.getContext());
                                        cl.DeleteAll(); // WOrkout is done, All lifts are done.
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

            // Save the current workout so we know that this is the workout that was started
            CurrentWorkout currentWorkoutToSave = new CurrentWorkout(this);
            currentWorkoutToSave.Load();
            if(currentWorkoutToSave.WorkoutId <= 0)
            {
                currentWorkoutToSave.DateStarted = DateTime.now();
                currentWorkoutToSave.WorkoutId = currentWorkout.Id;
                currentWorkoutToSave.Save();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void SetFinishedLifts()
    {
        try
        {
            LinearLayout liftsContainer = (LinearLayout)this.findViewById(R.id.performAWorkoutLiftsContainer);
            CurrentLiftCollection clc = new CurrentLiftCollection(this);
            clc.LoadAll(this);
            int liftsCount = liftsContainer.getChildCount();

            for(int i = 0; i < liftsCount; i++)
            {
                View currentLiftLayout = liftsContainer.getChildAt(i);

                if(currentLiftLayout instanceof LinearLayout && currentLiftLayout.getId() == R.id.performAWorkoutLiftContainer)
                {
                    currentLiftLayout.setBackgroundResource(R.drawable.perform_a_workout_lift_container_background);

                    String liftName = ((TextView)currentLiftLayout.findViewById(R.id.performAWorkoutLiftName)).getText().toString();
                    Lift lift = liftCollection.FindLiftByLiftName(liftName);

                    if(clc.IsLiftFinished(lift))
                    {
                        // For some reason, current LiftLayout and sb.parent.parent are not the same.
                        SeekBar sb = (SeekBar) ((LinearLayout) (currentLiftLayout.findViewById(R.id.performAWorkoutLiftSliderContainer))).findViewById(R.id.performAWorkoutSeekBar);
                        LinearLayout viewToPass = ((LinearLayout) sb.getParent().getParent());

                        SetToLiftCompleted(viewToPass, null);
                    }
                }

            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void CreateLiftContainers()
    {
        try {
            LinearLayout liftsContainer = (LinearLayout) findViewById(R.id.performAWorkoutLiftsContainer);
            LayoutInflater inflater = PerformWorkoutActivity.this.getLayoutInflater();

            for (int i = 0; i < liftCollection.Count(); ++i) {
                Lift currentLift = liftCollection._collection.get(i);
                WorkoutLift currentWorkoutLift = workoutLiftCollection.GetByIdFromCollection(currentLift.Id);
                LinearLayout liftContainer = new LinearLayout(this);
                inflater.inflate(R.layout.perform_workout_lift_container, liftContainer);
                LinearLayout LiftSetsRepsContainer = (LinearLayout)(liftContainer.findViewById(R.id.LiftSetsRepsContainer).findViewById(R.id.performAWorkoutLiftSetsReps));
//                RelativeLayout LiftDescriptionContainer = (RelativeLayout)(liftContainer.findViewById(R.id.performAWorkoutLiftDescriptionContainer));
                SeekBar sb = (SeekBar)((LinearLayout)liftContainer.findViewById(R.id.performAWorkoutLiftSliderContainer)).findViewById(R.id.performAWorkoutSeekBar);

                SetupSeekBar(sb);

                ((TextView) LiftSetsRepsContainer.findViewById(R.id.performAWorkoutLiftName)).setText(currentLift.LiftName);
                ((TextView) LiftSetsRepsContainer.findViewById(R.id.performAWorkoutSets)).setText(currentWorkoutLift.Sets + " Sets");
                ((TextView) LiftSetsRepsContainer.findViewById(R.id.performAWorkoutReps)).setText(currentWorkoutLift.Reps + " Reps");
                //((TextView) LiftDescriptionContainer.findViewById(R.id.performAWorkoutLiftDescription)).setText(currentLift.LiftDescription);

                liftContainer.setId(R.id.performAWorkoutLiftContainer);

                liftsContainer.addView(liftContainer);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void SetupSeekBar(SeekBar seekBar)
    {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() <= 95) {
                    seekBar.setProgress(0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if(seekBar.getProgress() > 95 && fromUser)
                {
                    try
                    {
                        LinearLayout parent = (LinearLayout) seekBar.getParent().getParent();

                        // Change view to show completed workout -------------------------------------------
                        SetToLiftCompleted(parent, seekBar);
                        // -----------------------------------------------------------------------------------------------------

                        // This lift is completed, add to completed lift section
                        String liftName = ((TextView) parent.findViewById(R.id.performAWorkoutLiftName)).getText().toString();
                        Lift thisLift = liftCollection.FindLiftByLiftName(liftName);
                        CurrentLift currentLift = new CurrentLift(seekBar.getContext());
                        currentLift.LiftId = thisLift.Id;
                        currentLift.Save();

                        // Also add to Completed Lifts table
                        TextView repsTextView = (TextView)parent.findViewById(R.id.performAWorkoutReps);
                        String[] splitRepsString = repsTextView.getText().toString().split(" ");

                        if(splitRepsString.length < 1)
                            return;

                        int reps = Integer.parseInt(splitRepsString[0]);

                        TextView setsTextView = (TextView)parent.findViewById(R.id.performAWorkoutSets);
                        String[] splitSetsString = setsTextView.getText().toString().split(" ");
                        int sets = Integer.parseInt(splitRepsString[0]);

                        CompletedLift completedLift = new CompletedLift(seekBar.getContext());
                        completedLift.WorkoutId = currentWorkout.Id;
                        completedLift.LiftId = thisLift.Id;
                        completedLift.Reps = reps;
                        completedLift.Sets = sets;
                        completedLift.Save();

                        // -----------------------------------------------------

                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public void ShowLiftDescription(View view)
    {
        LinearLayout parent = (LinearLayout)view.getParent().getParent().getParent();

        String liftName = ((TextView)parent.findViewById(R.id.performAWorkoutLiftName)).getText().toString();
        Lift lift = liftCollection.FindLiftByLiftName(liftName);

        String liftDescriptionToShow;

        if(lift.LiftDescription.isEmpty())
            liftDescriptionToShow = "-No Lift Description-";
        else
            liftDescriptionToShow = lift.LiftDescription;

        new AlertDialog.Builder(this)
                .setTitle(lift.LiftName)
                .setMessage(liftDescriptionToShow)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public final void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void SetToLiftCompleted(LinearLayout parent, SeekBar seekBar)
    {
        parent.setBackgroundResource(R.drawable.perform_a_workout_lift_container_background);
        parent.getLayoutParams().height = parent.getLayoutParams().height / 2;

        if(seekBar != null)
        {
            seekBar.setProgress(100);
            seekBar.setEnabled(false);
        }
        parent.findViewById(R.id.performAWorkoutLiftSliderContainer).setVisibility(View.GONE);
        parent.findViewById(R.id.performAWorkoutLiftSliderContainer).setVisibility(View.GONE);
        parent.findViewById(R.id.performAWorkoutLiftCompleted).setVisibility(View.VISIBLE);

        String liftName = ((TextView) parent.findViewById(R.id.performAWorkoutLiftName)).getText().toString();
        //((TextView) parent.findViewById(R.id.performAWorkoutLiftCompletedText)).setText(liftName + " Complete");
        ((TextView) parent.findViewById(R.id.performAWorkoutLiftCompletedText)).setText(liftName);
    }
}