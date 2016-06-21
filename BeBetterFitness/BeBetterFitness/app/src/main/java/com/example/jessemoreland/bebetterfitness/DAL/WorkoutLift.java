package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WorkoutLift
{
    private Context _context;
    public int Id;
    public int WorkoutId;
    public int LiftId;
    public int Sets;
    public int Reps;
    public boolean IsDirty;
    public boolean IsNew;
    private DatabaseHelper db;

    public WorkoutLift(Context context)
    {
        db = new DatabaseHelper(context);
        _context = context;
    }

    public void Delete()
    {
        try
        {
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(Id);

            sqLiteDatabase.execSQL("DELETE FROM WorkoutLift WHERE _id = ?", selectionArgs);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void Save()
    {
        try
        {
            if (IsDirty)
            {
                Object[] args = new Object[5];
                args[0] = (Integer.toString(WorkoutId));
                args[1] = (Integer.toString(LiftId));
                args[2] = (Integer.toString(Sets));
                args[3] = (Integer.toString(Reps));
                args[4] = (Integer.toString(Id));

                SQLiteDatabase database = db.getWritableDatabase();

                database.execSQL("UPDATE WorkoutLift SET workoutID = ?, liftId = ?, Sets = ?, Reps = ? WHERE _id = ?", args);

            }
            else if (IsNew)
            {
                Object[] args = new Object[4];
                args[0] = (Integer.toString(WorkoutId));
                args[1] = (Integer.toString(LiftId));
                args[2] = (Integer.toString(Sets));
                args[3] = (Integer.toString(Reps));
                db.getWritableDatabase().execSQL("INSERT INTO WorkoutLift VALUES (NULL, ?, ?, ?, ?)", args);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}