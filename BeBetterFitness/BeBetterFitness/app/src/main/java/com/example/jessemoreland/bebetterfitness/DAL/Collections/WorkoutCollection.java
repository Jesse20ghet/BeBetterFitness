package com.example.jessemoreland.bebetterfitness.DAL.Collections;

import android.content.Context;
import android.database.Cursor;

import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.DAL.Workout;
import com.example.jessemoreland.bebetterfitness.DAL.WorkoutLift;

import java.util.LinkedList;
import java.util.List;

public class WorkoutCollection
{
    private Context _context;
    public List<Workout> _collection;

    DatabaseHelper db;

    public WorkoutCollection(Context context)
    {
        try 
        {
            db = new DatabaseHelper(context);
            _context = context;
            _collection = new LinkedList<Workout>();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void SetIsNew(boolean isNew)
    {
        for(Workout workout: _collection)
        {
            workout.IsNew = isNew;
        }
    }

    public void LoadAll(Context context)
    {
        _collection.clear();
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT _id, WorkoutName FROM Workout", null);

        while(cursor.moveToNext())
        {
            Workout workoutToAdd = new Workout(context);

            workoutToAdd.Id = cursor.getInt(cursor.getColumnIndex("_id"));
            workoutToAdd.WorkoutName = cursor.getString(cursor.getColumnIndex("WorkoutName"));

            _collection.add(workoutToAdd);
        }

    }
    
    public void Save()
    {
        for(Workout workout : _collection)
        {
            workout.Save();
        }
    }

    public int Count()
    {
        return _collection.size();
    }

}