package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Quote
{
    public int Id;
    public String Quote;
    public String Author;
    public boolean Enabled;

    private Context _context;
    private DatabaseHelper db;

    public Quote(Context context)
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
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT Quote, Author, Enabled FROM Quotes WHERE _id = ?", selectionArgs);

            Quote = cursor.getString(cursor.getColumnIndex("Quote"));
            Author = cursor.getString(cursor.getColumnIndex("Author"));
            Enabled = cursor.getInt(cursor.getColumnIndex("Enabled")) > 0;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void Delete()
    {

    }

    public void Save()
    {

    }

    public void Disable()
    {
        try
        {
            SQLiteDatabase sqLiteDatabase;

            sqLiteDatabase = db.getWritableDatabase();

            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(Id);
            sqLiteDatabase.execSQL("Update Quotes SET Enabled = 0 WHERE _id = ?", selectionArgs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void Enable()
    {
        try
        {
            SQLiteDatabase sqLiteDatabase;

            sqLiteDatabase = db.getWritableDatabase();

            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(Id);
            sqLiteDatabase.execSQL("Update Quotes SET Enabled = 1 WHERE _id = ?", selectionArgs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


}