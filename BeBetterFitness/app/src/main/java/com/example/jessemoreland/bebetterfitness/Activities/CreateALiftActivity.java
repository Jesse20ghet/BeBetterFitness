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

import com.example.jessemoreland.bebetterfitness.DAL.Collections.MuscleCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.DAL.LiftMuscle;
import com.example.jessemoreland.bebetterfitness.DAL.Muscle;
import com.example.jessemoreland.bebetterfitness.R;

public class CreateALiftActivity extends AppCompatActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_lift);
    }

    public void SaveLift(View view)
    {
        // Get the reference to the table with all the muscles
        TableLayout table = (TableLayout) findViewById(R.id.NewLift_AddAMuscle);

        String description = ((EditText)findViewById(R.id.createALift_LiftDescription)).getText().toString();
        String name = ((EditText)findViewById(R.id.createALift_LiftName)).getText().toString();

        Lift liftToSave = new Lift(view.getContext());
        liftToSave.LiftName = name;
        liftToSave.LiftDescription = description;

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

            liftToSave._liftMuscleCollection.AddLiftMuscle(liftMuscle);
        }

        liftToSave.Save();

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

        LayoutInflater inflater = CreateALiftActivity.this.getLayoutInflater();
        inflater.inflate(R.layout.new_muscle_row, tableRow);

        return tableRow;
    }
}