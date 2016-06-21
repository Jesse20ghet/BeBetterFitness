package com.example.jessemoreland.bebetterfitness.Activities;

import android.app.Activity;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.sql.SQLException;

public class ExerciseActivity extends Activity {

    public int tableRowCount = 1;
    private LiftCollection liftCollection = new LiftCollection(this);

    public void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);

            liftCollection.LoadAll(this);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        setContentView(R.layout.lift_main);
    }

}