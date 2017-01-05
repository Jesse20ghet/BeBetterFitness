package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentLift
{
    public int Id;
    public int LiftId;

    private Context _context;
    private DatabaseHelper db;

    public CurrentLift(Context context)
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

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT _id, LiftId FROM CurrentLifts WHERE _id = ?", selectionArgs);
            cursor.moveToFirst();

            LiftId = cursor.getInt(cursor.getColumnIndex("LiftId"));
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

            sqLiteDatabase.execSQL("DELETE FROM CurrentLifts WHERE _id = ?", selectionArgs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void DeleteAll()
    {
        SQLiteDatabase sqLiteDatabase;

        try
        {
            sqLiteDatabase = db.getWritableDatabase();

            sqLiteDatabase.execSQL("DELETE FROM CurrentLifts");
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

            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(LiftId);
            sqLiteDatabase.execSQL("INSERT INTO CurrentLifts VALUES (NULL, ?)", selectionArgs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


}