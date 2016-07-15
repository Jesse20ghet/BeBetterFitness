package com.example.jessemoreland.bebetterfitness.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jessemoreland.bebetterfitness.Helpers.ActionBarHelper;
import com.example.jessemoreland.bebetterfitness.R;

public class NutritionActivity extends AppCompatActivity
{
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_main);

        //-----------------------------------------------------
        // Slide out set up
        //-----------------------------------------------------
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
        //-----------------------------------------------------

    }

    public void onResume()
    {
        super.onResume();

        // Reload popout, there could be a current workout
        ListView mDrawerList = (ListView)findViewById(R.id.navList);
        DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        final String mActivityTitle = getTitle().toString();

        ActionBarHelper abh = new ActionBarHelper(mDrawerList, mDrawerLayout, mDrawerToggle, mActivityTitle, this);
        abh.SetupActionBar(this);
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