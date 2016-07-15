package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompletedWorkout
{
    public int Id;
    public int WorkoutId;
    public Date StartTime;
    public Date EndTime;

    private Context _context;
    private DatabaseHelper db;

    public CompletedWorkout(Context context)
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
            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(Id);

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT _id, WorkoutId, StartTime, EndTime FROM CompletedWorkouts WHERE _id = ?", selectionArgs);
            cursor.moveToFirst();

            WorkoutId = cursor.getInt(cursor.getColumnIndex("WorkoutId"));

            String startTimeAsString = cursor.getString(cursor.getColumnIndex("StartTime"));
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            StartTime = dateFormat.parse(startTimeAsString);

            String endTimeAsString = cursor.getString(cursor.getColumnIndex("EndTime"));
            EndTime = dateFormat.parse(endTimeAsString);

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

            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(Id);

            sqLiteDatabase.execSQL("DELETE FROM CompletedWorkouts WHERE _id = ?", selectionArgs);
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

            String[] selectionArgs = new String[3];
            selectionArgs[0] = Integer.toString(WorkoutId);
            selectionArgs[1] = StartTime.toString();
            selectionArgs[2] = EndTime.toString();
            sqLiteDatabase.execSQL("INSERT INTO CurrentLifts VALUES (NULL, ?, ?, ?)", selectionArgs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


}