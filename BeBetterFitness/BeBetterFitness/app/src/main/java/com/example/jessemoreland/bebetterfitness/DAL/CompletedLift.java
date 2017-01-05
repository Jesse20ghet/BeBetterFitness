package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompletedLift
{
    public int Id;
    public int LiftId;
    public int Reps;
    public int Sets;
    public int WorkoutId;

    private Context _context;
    private DatabaseHelper db;

    public CompletedLift(Context context)
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

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT _id, LiftId, Reps, Sets FROM CompletedLifts WHERE _id = ?", selectionArgs);
            cursor.moveToFirst();

            LiftId = cursor.getInt(cursor.getColumnIndex("LiftId"));
            Reps = cursor.getInt(cursor.getColumnIndex("Reps"));
            Sets = cursor.getInt(cursor.getColumnIndex("Sets"));
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

            sqLiteDatabase.execSQL("DELETE FROM CompletedLifts WHERE _id = ?", selectionArgs);
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

            String[] selectionArgs = new String[4];
            selectionArgs[0] = Integer.toString(LiftId);
            selectionArgs[1] = Integer.toString(Reps);
            selectionArgs[2] = Integer.toString(Sets);
            selectionArgs[3] = Integer.toString(WorkoutId);
            sqLiteDatabase.execSQL("INSERT INTO CompletedLifts VALUES (NULL, ?, ?, ?, ?)", selectionArgs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


}