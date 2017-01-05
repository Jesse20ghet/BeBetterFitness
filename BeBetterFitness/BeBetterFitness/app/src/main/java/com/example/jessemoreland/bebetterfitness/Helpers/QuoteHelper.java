package com.example.jessemoreland.bebetterfitness.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jessemoreland.bebetterfitness.Activities.ConfigurationActivity;
import com.example.jessemoreland.bebetterfitness.Activities.DebugActivity;
import com.example.jessemoreland.bebetterfitness.Activities.ExerciseActivity;
import com.example.jessemoreland.bebetterfitness.Activities.NutritionActivity;
import com.example.jessemoreland.bebetterfitness.Activities.PerformWorkoutActivity;
import com.example.jessemoreland.bebetterfitness.Activities.RequestActivity;
import com.example.jessemoreland.bebetterfitness.DAL.Collections.QuoteCollection;
import com.example.jessemoreland.bebetterfitness.DAL.CurrentWorkout;
import com.example.jessemoreland.bebetterfitness.DAL.Quote;
import com.example.jessemoreland.bebetterfitness.MainActivity;
import com.example.jessemoreland.bebetterfitness.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JesseMoreland on 7/6/2016.
 */
public class QuoteHelper
{
    private Context _context;
    private Date _constQuoteDate = new Date();
    final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24; // Milliseconds in a second * seconds in a minute * minutes in an hour * hours in a day

    public QuoteHelper(Context context)
    {
        try {
            _context = context;
            _constQuoteDate.setYear(2000);
            _constQuoteDate.setDate(1);
            _constQuoteDate.setMonth(1);
            _constQuoteDate.setHours(0);
            _constQuoteDate.setMinutes(1);
            _constQuoteDate.setSeconds(0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Quote GetQuoteOfTheDay(Date date)
    {
        try
        {
            QuoteCollection qCollection = new QuoteCollection(_context);
            qCollection.LoadAll(_context);
            if (date == null) {
                date = new Date();
            }
            int days = (int) ((_constQuoteDate.getTime() - date.getTime()) / DAY_IN_MILLIS);
            int numberOfQuotes = qCollection.Count();

            days = Math.abs(days % (numberOfQuotes - 1));

            return qCollection._collection.get(days);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return new Quote(_context);
    }
}
