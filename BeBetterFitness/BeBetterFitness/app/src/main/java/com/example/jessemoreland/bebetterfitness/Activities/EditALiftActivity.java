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
import com.example.jessemoreland.bebetterfitness.DAL.Collections.MuscleCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.DAL.LiftMuscle;
import com.example.jessemoreland.bebetterfitness.DAL.Muscle;
import com.example.jessemoreland.bebetterfitness.R;

public class EditALiftActivity extends AppCompatActivity {

    private Lift lift = new Lift(this);
    private MuscleCollection muscleCollection = new MuscleCollection(this);
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_lift);

        Bundle extras = getIntent().getExtras();
        int liftId;
        if(extras != null)
        {
            liftId = extras.getInt("LiftId");
            lift.Id = liftId;
            lift.Load();
        }

        muscleCollection.LoadAll();

        // Change title
        ((TextView)findViewById(R.id.liftViewTitle)).setText("Edit lift");

        PopulateLists();
    }

    private void PopulateLists()
    {
        // Populate lift name edit text
        EditText liftNameEditText = (EditText)findViewById(R.id.createALift_LiftName);
        liftNameEditText.setText(lift.LiftName);

        EditText liftDescriptionEditText = (EditText)findViewById(R.id.createALift_LiftDescription);
        liftDescriptionEditText.setText(lift.LiftDescription);

        Muscle muscle;
        for(int i = 0; i < lift._liftMuscleCollection.Count(); ++i)
        {
            LiftMuscle currentLiftMuscle = lift._liftMuscleCollection._collection.get(i);

            muscle = muscleCollection.FindItemByPrimaryKey(currentLiftMuscle.muscleId);

            PopulateAndAddMuscleRow(muscle);
        }

    }

    private void PopulateAndAddMuscleRow(Muscle muscle)
    {
        try
        {
            TableLayout addNewMuscleTable = (TableLayout) findViewById(R.id.NewLift_AddAMuscle);
            RelativeLayout btn = (RelativeLayout) addNewMuscleTable.findViewById(R.id.addAnotherMuscleButtonContainer);

            ((ViewGroup) btn.getParent()).removeView(btn);

            TableRow muscleRow = CreateMuscleRowView();

            ArrayAdapter<Muscle> muscleAdapter;
            Spinner ref = (Spinner)muscleRow.findViewById(R.id.muscleRowSpinnerMuscleName);
            muscleAdapter = new ArrayAdapter<Muscle>(this, R.layout.spinnerlayout, muscleCollection._collection);
            ref.setAdapter(muscleAdapter);
            ref.setSelection(muscleAdapter.getPosition(muscle));

            addNewMuscleTable.addView(muscleRow);
            addNewMuscleTable.addView(btn);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void SaveLift(View view)
    {
        DeleteOldLiftMuscleCollection();

        // Get the reference to the table with all the muscles
        TableLayout table = (TableLayout) findViewById(R.id.NewLift_AddAMuscle);

        String description = ((EditText)findViewById(R.id.createALift_LiftDescription)).getText().toString();
        String name = ((EditText)findViewById(R.id.createALift_LiftName)).getText().toString();

        LiftCollection lc = new LiftCollection(view.getContext());
        lc.LoadAll();

        Lift checkLift = lc.FindLiftByLiftNameAndId(name, lift.Id);
        if(checkLift.Id != -1)
        {
            // Display alert confirming that the workout successfully saved
            new AlertDialog.Builder(this)
                    .setTitle("Error Saving")
                    .setMessage("Can not have duplicate lift names in the database.")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                        }
                    }).show();

            return;
        }

        lift.LiftName = name;
        lift.LiftDescription = description;

        for(int i = 0; i< table.getChildCount(); i++)
        {
            View muscleTable = (View)table.getChildAt(i);

            if(muscleTable.getId() == R.id.addAnotherMuscleButtonContainer)
                break;

            LiftMuscle liftMuscle = new LiftMuscle(this);

            Spinner muscleSpinner = ((Spinner) muscleTable.findViewById(R.id.muscleRowSpinnerMuscleName));
            Muscle muscle = (Muscle)muscleSpinner.getSelectedItem();
            Integer muscleId = Integer.parseInt(Integer.toString(muscle.Id));

            liftMuscle.muscleId = muscleId;

            lift._liftMuscleCollection.AddLiftMuscle(liftMuscle);
        }

        lift.IsDirty = true;
        lift.Save();

        // Display alert confirming that the workout successfully saved
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Your lift has been saved.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    private void DeleteOldLiftMuscleCollection()
    {
        lift._liftMuscleCollection.DeleteAll();
    }

    private void DeleteOldLift()
    {
        lift.Delete();
    }

    public void AddNewMuscleRow(View view)
    {
        try
        {
            MuscleCollection muscleCollection = new MuscleCollection(view.getContext());
            muscleCollection.LoadAll();

            TableLayout addNewMuscleTable = (TableLayout) findViewById(R.id.NewLift_AddAMuscle);
            RelativeLayout btn = (RelativeLayout) addNewMuscleTable.findViewById(R.id.addAnotherMuscleButtonContainer);

            ((ViewGroup) btn.getParent()).removeView(btn);

            TableRow muscleRow = CreateMuscleRowView();

            ArrayAdapter<Muscle> muscleAdapter;
            Spinner ref = (Spinner)muscleRow.findViewById(R.id.muscleRowSpinnerMuscleName);
            muscleAdapter = new ArrayAdapter<Muscle>(this, R.layout.spinnerlayout, muscleCollection._collection);
            ref.setAdapter(muscleAdapter);

            addNewMuscleTable.addView(muscleRow);
            addNewMuscleTable.addView(btn);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void DeleteMuscleRow(View view)
    {
        View viewToDelete = (View)view.getParent().getParent();
        ((TableLayout)viewToDelete.getParent()).removeView(viewToDelete);
    }

    private TableRow CreateMuscleRowView()
    {
        TableRow tableRow = new TableRow(this);

        LayoutInflater inflater = EditALiftActivity.this.getLayoutInflater();
        inflater.inflate(R.layout.new_muscle_row, tableRow);

        return tableRow;
    }
}