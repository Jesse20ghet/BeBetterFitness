package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.WorkoutLiftCollection;

import java.util.PriorityQueue;

public class Workout
{
    private Context _context;
    public int Id;
    public String WorkoutName;
    public boolean IsNew;
    public boolean IsDirty;
    public WorkoutLiftCollection workoutLiftCollection;
    private DatabaseHelper db;

    public Workout(Context context)
    {
        workoutLiftCollection = new WorkoutLiftCollection(context);
        db = new DatabaseHelper(context);
        _context = context;
        IsNew = false;
        IsDirty = false;
    }
    
    public void Load(Context context)
    {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        try 
        {
            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(Id);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT _id, WorkoutName FROM Workout WHERE _id = ?", selectionArgs);
            cursor.moveToFirst();
            
            WorkoutName = cursor.getString(cursor.getColumnIndex("WorkoutName"));
            
            workoutLiftCollection.LoadByWorkoutId(context, Id);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void Delete()
    {
        workoutLiftCollection.DeleteAll();
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        String[] selectionArgs = new String[1];
        selectionArgs[0] = Integer.toString(Id);

        sqLiteDatabase.execSQL("DELETE FROM Workout WHERE _id = ?", selectionArgs);
    }

    public void Save()
    {
        SQLiteDatabase sqLiteDatabase= db.getWritableDatabase();

        try
        {
            if(IsNew)
            {
                sqLiteDatabase.execSQL("INSERT INTO Workout VALUES (NULL, '" + WorkoutName + "')");

                Cursor cursor = sqLiteDatabase.rawQuery("SELECT last_insert_rowid() FROM Workout", null);
                cursor.moveToFirst();
                int ID = cursor.getInt(0);
                cursor.close();

                workoutLiftCollection.SetWorkoutId(ID);
                workoutLiftCollection.SetIsNew(true);

                workoutLiftCollection.Save();
            }
            else if(IsDirty)
            {
                String[] selectionArgs = new String[2];
                selectionArgs[0] = WorkoutName;
                selectionArgs[1] = Integer.toString(Id);
                sqLiteDatabase.execSQL("UPDATE Workout SET WorkoutName = ? WHERE _id = ?", selectionArgs);

                workoutLiftCollection.SetWorkoutId(Id);
                workoutLiftCollection.SetIsNew(true);

                workoutLiftCollection.Save();
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public String toString()
    {
        return this.WorkoutName;
    }

    public void DeleteWorkoutLiftCollection()
    {
        workoutLiftCollection.DeleteAll();
        workoutLiftCollection._collection.clear();
    }
}