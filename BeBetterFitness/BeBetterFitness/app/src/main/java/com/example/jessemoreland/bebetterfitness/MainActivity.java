package com.example.jessemoreland.bebetterfitness;

import android.app.Activity;
import android.content.Intent;

import com.example.jessemoreland.bebetterfitness.Activities.ConfigurationActivity;
import com.example.jessemoreland.bebetterfitness.Activities.DebugActivity;
import com.example.jessemoreland.bebetterfitness.Activities.ExerciseActivity;
import com.example.jessemoreland.bebetterfitness.Activities.NutritionActivity;
import com.example.jessemoreland.bebetterfitness.Activities.RequestActivity;
import com.example.jessemoreland.bebetterfitness.DAL.Collections.CompletedWorkoutCollection;
import com.example.jessemoreland.bebetterfitness.DAL.CompletedWorkout;
import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Quote;
import com.example.jessemoreland.bebetterfitness.Helpers.ActionBarHelper;
import com.example.jessemoreland.bebetterfitness.Helpers.QuoteHelper;
import com.example.jessemoreland.bebetterfitness.R;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private DatabaseHelper dbHelper;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            dbHelper = new DatabaseHelper(this);
            //dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 2, 2);
            setContentView(R.layout.content_main);

            // ===================================================
            // Slide out set up
            // ===================================================
            ListView mDrawerList = (ListView)findViewById(R.id.navList);
            DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
            final String mActivityTitle = getTitle().toString();
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    getSupportActionBar().setTitle("Navigation!");
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    getSupportActionBar().setTitle(mActivityTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };
            ActionBarHelper abh = new ActionBarHelper(mDrawerList, mDrawerLayout, mDrawerToggle, mActivityTitle, this);
            abh.SetupActionBar(this);
            // ======================================================

            GetQuoteOfTheDay();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void onResume()
    {
        super.onResume();
        GetQuoteOfTheDay();
    }

    private void GetQuoteOfTheDay()
    {
        QuoteHelper quoteHelper = new QuoteHelper(this);
        Date today = new Date();
        Quote quoteOfTheDay = quoteHelper.GetQuoteOfTheDay(today);

        TextView quoteOfTheDayTextView = (TextView)findViewById(R.id.quoteOfTheDayTextView);
        quoteOfTheDayTextView.setText("\"" + quoteOfTheDay.Quote + "\"" + "\n" + "- " + quoteOfTheDay.Author);
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

        try
        {
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

    @Override
    public void onBackPressed() {
    }

    //-----------------------------------------------------
    // Toolbar functions
    //-----------------------------------------------------
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
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

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //-----------------------------------------------------
}
