package com.example.jessemoreland.bebetterfitness.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jessemoreland.bebetterfitness.DAL.CurrentLift;
import com.example.jessemoreland.bebetterfitness.DAL.CurrentWorkout;
import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Workout;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentWorkoutHelper
{
    DatabaseHelper db;
    Context _context;

    public CurrentWorkoutHelper(Context context)
    {
        _context = context;
        db = new DatabaseHelper(context);
    }

    public int GetCurrentWorkoutId()
    {
        try
        {
            CurrentWorkout currentWorkout = new CurrentWorkout(_context);
            currentWorkout.Load();

            return currentWorkout.WorkoutId;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return -2;
        }
    }

    public void DeleteCurrentWorkout()
    {
        try
        {
            CurrentWorkout currentWorkout = new CurrentWorkout(_context);
            CurrentLift currentLift = new CurrentLift(_context);
            currentWorkout.Delete();
            currentLift.DeleteAll();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void SaveCompletedWorkout(Workout workout, DateTime startTime, DateTime endTime)
    {
        try
        {
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

            String[] selectionArgs = new String[3];
            selectionArgs[0] = Integer.toString(workout.Id);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            selectionArgs[1] = startTime.toString();
            selectionArgs[2] = endTime.toString();

            sqLiteDatabase.execSQL("INSERT INTO CompletedWorkouts VALUES (NULL, ?, ?, ?)", selectionArgs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
