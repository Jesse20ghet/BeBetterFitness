package com.example.jessemoreland.bebetterfitness.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftCollection;
import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.R;

import java.sql.SQLException;
import java.util.List;

public class DebugActivity extends Activity
{
    DatabaseHelper dbHelper;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            dbHelper = new DatabaseHelper(this);
            setContentView(R.layout.debug_main);

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            List<String> DatabaseTables = dbHelper.getAllTablesinDatabase(db);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, DatabaseTables);
            Spinner tableSpinnerRef = (Spinner) findViewById(R.id.debugTableSpinner);
            tableSpinnerRef.setAdapter(adapter);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void GetTableInfo(View view)
    {
        TextView textView = (TextView)findViewById(R.id.debugTextView);

        Spinner tableSpinner = ((Spinner)findViewById(R.id.debugTableSpinner));
        String tableToGet = (String)tableSpinner.getSelectedItem();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tableInfo = dbHelper.getAllRowsInTable(db, tableToGet);

        textView.setText(tableInfo);
    }
}