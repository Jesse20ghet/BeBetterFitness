package com.example.jessemoreland.bebetterfitness.DAL.Collections;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.DAL.LiftMuscle;
import com.example.jessemoreland.bebetterfitness.DAL.WorkoutLift;

import java.util.LinkedList;
import java.util.List;

public class WorkoutLiftCollection
{
    private Context _context;
    public List<WorkoutLift> _collection;

    DatabaseHelper db;

    public WorkoutLiftCollection(Context context)
    {
        try 
        {
            db = new DatabaseHelper(context);
            _context = context;
            _collection = new LinkedList<WorkoutLift>();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void AddWorkoutLift(WorkoutLift workoutLift)
    {
        _collection.add(workoutLift);
    }

    public void SetWorkoutId(int ID)
    {
        for( WorkoutLift workoutLift: _collection)
        {
            workoutLift.WorkoutId = ID;
        }
    }

    public void SetIsNew(boolean isNew)
    {
        for(WorkoutLift workoutLift: _collection)
        {
            workoutLift.IsNew = isNew;
        }
    }
    
    public void Save()
    {
        for(WorkoutLift workoutLift : _collection)
        {
            workoutLift.Save();
        }
    }

    public void LoadByWorkoutId(Context context, int workoutId)
    {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();

        try
        {
            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(workoutId);

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT _id, workoutId, liftId, Sets, Reps FROM WorkoutLift WHERE workoutId = ?", selectionArgs);

            while(cursor.moveToNext())
            {
                WorkoutLift workoutLift = new WorkoutLift(context);

                workoutLift.Id = cursor.getInt(cursor.getColumnIndex("_id"));
                workoutLift.LiftId = cursor.getInt(cursor.getColumnIndex("liftId"));
                workoutLift.WorkoutId = cursor.getInt(cursor.getColumnIndex("workoutId"));
                workoutLift.Sets = cursor.getInt(cursor.getColumnIndex("Sets"));
                workoutLift.Reps = cursor.getInt(cursor.getColumnIndex("Reps"));
                _collection.add(workoutLift);
            }

            cursor.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void DeleteAll()
    {
        for(int i = 0; i < _collection.size(); ++i)
        {
            _collection.get(i).Delete();
        }
    }

    public void LoadAll(Context context)
    {

    }

    public int Count()
    {
        return _collection.size();
    }

}