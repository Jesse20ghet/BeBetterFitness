package com.example.jessemoreland.bebetterfitness;

import android.app.Activity;
import android.content.Intent;

import com.example.jessemoreland.bebetterfitness.Activities.ConfigurationActivity;
import com.example.jessemoreland.bebetterfitness.Activities.DebugActivity;
import com.example.jessemoreland.bebetterfitness.Activities.ExerciseActivity;
import com.example.jessemoreland.bebetterfitness.Activities.NutritionActivity;
import com.example.jessemoreland.bebetterfitness.Activities.RequestActivity;
import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.R;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            dbHelper = new DatabaseHelper(this);
            dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 2, 2);
            setContentView(R.layout.content_main);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void LiftActivityButtonClick(View view)
    {
        try
        {
            Intent myIntent = new Intent(view.getContext(), ExerciseActivity.class);
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void NutritionActivityButtonClick(View view)
    {
        try {
            Intent myIntent = new Intent(view.getContext(), NutritionActivity.class);
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void ConfigurationActivityButtonClick(View view)
    {
        try {
            Intent myIntent = new Intent(view.getContext(), ConfigurationActivity.class);
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void RequestActivityButtonClick(View view)
    {
        try {
            Intent myIntent = new Intent(view.getContext(), RequestActivity.class);
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void DebugActivityButtonClick(View view)
    {
        try
        {
            Intent myIntent = new Intent(view.getContext(), DebugActivity.class);
            startActivityForResult(myIntent, 0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
