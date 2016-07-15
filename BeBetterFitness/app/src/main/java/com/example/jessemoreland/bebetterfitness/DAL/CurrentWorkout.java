package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftMuscleCollection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentWorkout
{
    public int Id;
    public int WorkoutId;
    public Date DateStarted;

    private Context _context;
    private DatabaseHelper db;

    public CurrentWorkout(Context context)
    {
        db = new DatabaseHelper(context);
        _context = context;
    }

    public void Load()
    {
        SQLiteDatabase sqLiteDatabase;

        try
        {
            sqLiteDatabase = db.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT _id, WorkoutId, DateStarted FROM CurrentWorkout", null);

            cursor.moveToFirst();

            if(!cursor.isAfterLast())
            {
                Id = cursor.getInt(cursor.getColumnIndex("_id"));
                WorkoutId = cursor.getInt(cursor.getColumnIndex("WorkoutId"));
                String dateAsString = cursor.getString(cursor.getColumnIndex("DateStarted"));
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                DateStarted = dateFormat.parse(dateAsString);
            }
            else
            {
                WorkoutId = -1;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void Delete()
    {
        SQLiteDatabase sqLiteDatabase;

        try
        {
            sqLiteDatabase = db.getWritableDatabase();
            sqLiteDatabase.execSQL("DELETE FROM CurrentWorkout");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void Save()
    {
        SQLiteDatabase sqLiteDatabase;

        try
        {
            sqLiteDatabase = db.getWritableDatabase();

            String[] selectionArgs = new String[2];
            selectionArgs[0] = Integer.toString(WorkoutId);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            selectionArgs[1] = dateFormat.format(DateStarted);

            sqLiteDatabase.execSQL("INSERT INTO CurrentWorkout VALUES (NULL, ?, ?)", selectionArgs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


}