package com.example.jessemoreland.bebetterfitness.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.DAL.Workout;
import com.example.jessemoreland.bebetterfitness.DAL.WorkoutLift;
import com.example.jessemoreland.bebetterfitness.R;

import java.util.ArrayList;
import java.util.List;

public class EditAWorkoutActivity extends AppCompatActivity {

    private Workout workout = new Workout(this);
    private LiftCollection liftCollection = new LiftCollection(this);

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_workout);

        Bundle extras = getIntent().getExtras();
        int workoutId;
        if(extras != null)
        {
            workoutId = extras.getInt("WorkoutId");
            workout.Id = workoutId;
            workout.Load(this);
        }

        liftCollection.LoadAll();

        // Change title
        ((TextView)findViewById(R.id.liftCreateWorkoutTitle)).setText("Edit Workout");

        PopulateLists();
    }

    private void PopulateLists()
    {
        // Populate workout name edit text
        EditText workoutNameEditText = (EditText)findViewById(R.id.editText_newWorkout_WorkoutName);
        workoutNameEditText.setText(workout.WorkoutName);

        // Populate  all associated lifts
        Lift lift;
        for(int i = 0; i < workout.workoutLiftCollection.Count(); i++)
        {
            WorkoutLift currentWorkoutLift = workout.workoutLiftCollection._collection.get(i);

            lift = liftCollection.FindItemByPrimaryKey(currentWorkoutLift.LiftId);

            PopulateAndAddLiftRow(lift, currentWorkoutLift);
        }

    }

    private void PopulateAndAddLiftRow(Lift lift, WorkoutLift currentWorkoutLift)
    {
        try
        {
            // find the table and create a new row view
            TableLayout addNewLiftTable = (TableLayout) findViewById(R.id.liftTable);
            RelativeLayout btn = (RelativeLayout) addNewLiftTable.findViewById(R.id.addAnotherLiftButtonContainer);

            ((ViewGroup) btn.getParent()).removeView(btn);

            TableRow liftRow = CreateLiftRowView();

            // Fill the lifts spinner with lifts in the database
            ArrayAdapter<Lift> liftAdapter;
            Spinner spinRef = (Spinner)liftRow.findViewById(R.id.liftViewLiftName);
            liftAdapter = new ArrayAdapter<Lift>(this, R.layout.spinnerlayout, liftCollection._collection);
            liftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinRef.setAdapter(liftAdapter);
            spinRef.setSelection(liftAdapter.getPosition(lift));

            // Fill sets spinner with numbers 1 - 20
            List<String> setSpinnerArray = new ArrayList<String>();
            for(int i = 1; i < 21; i++)
            {
                setSpinnerArray.add(Integer.toString(i));
            }
            ArrayAdapter<String> setAdapter = new ArrayAdapter<String>(this, R.layout.spinnerlayout, setSpinnerArray);
            Spinner setSpinRef = (Spinner) liftRow.findViewById(R.id.liftRowReps);
            setSpinRef.setAdapter(setAdapter);
            setSpinRef.setSelection(setAdapter.getPosition(Integer.toString(currentWorkoutLift.Sets)));

            // Fill reps spinner with numbers
            List<String> repSpinnerArray = new ArrayList<String>();
            for(int i = 1; i < 21; i++)
            {
                repSpinnerArray.add(Integer.toString(i));
            }
            ArrayAdapter<String> repAdapter = new ArrayAdapter<String>(this, R.layout.spinnerlayout, repSpinnerArray);
            Spinner repSpinRef = (Spinner) liftRow.findViewById(R.id.liftRowSets);
            repSpinRef.setAdapter(repAdapter);
            repSpinRef.setSelection(repAdapter.getPosition(Integer.toString(currentWorkoutLift.Reps)));

            // Add newly inflated lift view to table
            addNewLiftTable.addView(liftRow);
            addNewLiftTable.addView(btn);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void AddNewLiftRow(View view)
    {
        try
        {
            // find the table and create a new row view
            TableLayout addNewLiftTable = (TableLayout) findViewById(R.id.liftTable);
            RelativeLayout btn = (RelativeLayout) addNewLiftTable.findViewById(R.id.addAnotherLiftButtonContainer);

            ((ViewGroup) btn.getParent()).removeView(btn);

            TableRow liftRow = CreateLiftRowView();

            // Fill the lifts spinner with lifts in the database
            ArrayAdapter<Lift> liftAdapter;
            Spinner spinRef = (Spinner)liftRow.findViewById(R.id.liftViewLiftName);
            liftAdapter = new ArrayAdapter<Lift>(this, R.layout.spinnerlayout, liftCollection._collection);
            liftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinRef.setAdapter(liftAdapter);

            // Fill sets spinner with numbers 1 - 20
            List<String> setSpinnerArray = new ArrayList<String>();
            for(int i = 1; i < 21; i++)
            {
                setSpinnerArray.add(Integer.toString(i));
            }
            ArrayAdapter<String> setAdapter = new ArrayAdapter<String>(this, R.layout.spinnerlayout, setSpinnerArray);
            Spinner setSpinRef = (Spinner) liftRow.findViewById(R.id.liftRowReps);
            setSpinRef.setAdapter(setAdapter);

            // Fill reps spinner with numbers
            List<String> repSpinnerArray = new ArrayList<String>();
            for(int i = 1; i < 21; i++)
            {
                repSpinnerArray.add(Integer.toString(i));
            }
            ArrayAdapter<String> repAdapter = new ArrayAdapter<String>(this, R.layout.spinnerlayout, repSpinnerArray);
            Spinner repSpinRef = (Spinner) liftRow.findViewById(R.id.liftRowSets);
            repSpinRef.setAdapter(repAdapter);

            // Add newly inflated lift view to table
            addNewLiftTable.addView(liftRow);
            addNewLiftTable.addView(btn);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private TableRow CreateLiftRowView()
    {
        TableRow tableRow = new TableRow(this);

        LayoutInflater inflater = EditAWorkoutActivity.this.getLayoutInflater();
        inflater.inflate(R.layout.new_lift_row, tableRow);

        //if(tableRowCount % 2 == 0)
        //{

        //}

        return tableRow;
    }

    public void SaveWorkout(View view)
    {
        try
        {
            DeleteOldWorkoutLiftCollection();

            // Get the reference to the table with all the lifts
            TableLayout table = (TableLayout) findViewById(R.id.liftTable);

            // Get the name of the workout
            String workoutName = ((EditText) findViewById(R.id.editText_newWorkout_WorkoutName)).getText().toString();

            // Add Name to workout object
            workout.WorkoutName = workoutName;

            // Add Each lift to a workout lift collection
            for (int i = 0; i < table.getChildCount(); i++)
            {
                View liftsTable = (View)table.getChildAt(i);

                if(liftsTable.getId() == R.id.addAnotherLiftButtonContainer)
                {
                    break;
                }

                WorkoutLift workoutLift = new WorkoutLift(this);

                Spinner setsSpinner = ((Spinner)liftsTable.findViewById(R.id.liftRowSets));
                Integer sets = Integer.parseInt(setsSpinner.getSelectedItem().toString());

                Spinner repsSpinner = ((Spinner)liftsTable.findViewById(R.id.liftRowReps));
                Integer reps = Integer.parseInt(repsSpinner.getSelectedItem().toString());

                Spinner liftsSpinner = ((Spinner)liftsTable.findViewById(R.id.liftViewLiftName));
                Lift lift = (Lift)liftsSpinner.getSelectedItem();
                Integer liftId = Integer.parseInt(Integer.toString(lift.Id));

                workoutLift.LiftId = liftId;
                workoutLift.Reps = reps;
                workoutLift.Sets = sets;

                workout.workoutLiftCollection.AddWorkoutLift(workoutLift);
            }

            // Save workout
            workout.IsDirty = true;
            workout.Save();

            // Display alert confirming that the workout successfully saved
            new AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setCancelable(false)
                    .setMessage("Your workout has been successfully saved.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();

            // TODO: Alert user that there was a problem saving this workout
        }
    }

    private void DeleteOldWorkoutLiftCollection()
    {
        workout.DeleteWorkoutLiftCollection();
    }

    private void DeleteOldWorkout()
    {
        workout.Delete();
    }

    public void DeleteCurrentLiftRow(View view)
    {
        try
        {
            View viewToDelete = (View)view.getParent().getParent();
            ((TableLayout)viewToDelete.getParent()).removeView(viewToDelete);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}