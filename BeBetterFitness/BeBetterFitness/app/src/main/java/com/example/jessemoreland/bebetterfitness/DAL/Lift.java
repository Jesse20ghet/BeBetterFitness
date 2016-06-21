package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftMuscleCollection;

public class Lift
{
    private Context _context;
    public int Id;
    public String LiftName;
    public String LiftDescription;
    public LiftMuscleCollection _liftMuscleCollection;
    private DatabaseHelper db;

    public Lift(Context context)
    {
        db = new DatabaseHelper(context);
        _context = context;
        _liftMuscleCollection = new LiftMuscleCollection(context);
    }

    public void Load()
    {

    }

    public void SaveLift()
    {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        try
        {
            sqLiteDatabase.execSQL("INSERT INTO Lift values(NULL, ?, ? )", new String[]{LiftName, LiftDescription});

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT last_insert_rowid() FROM Lift", null);
            cursor.moveToFirst();
            int ID = cursor.getInt(0);
            cursor.close();

            _liftMuscleCollection.SetLiftId(ID);
            _liftMuscleCollection.SetIsNew(true);

            _liftMuscleCollection.Save();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return this.LiftName;
    }
}