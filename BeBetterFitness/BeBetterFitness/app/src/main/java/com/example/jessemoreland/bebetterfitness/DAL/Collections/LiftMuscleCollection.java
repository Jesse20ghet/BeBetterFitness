package com.example.jessemoreland.bebetterfitness.DAL.Collections;

import android.content.Context;
import android.database.Cursor;

import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.DAL.LiftMuscle;

import java.util.LinkedList;
import java.util.List;

public class LiftMuscleCollection
{
    private Context _context;
    public List<LiftMuscle> _collection;

    DatabaseHelper db;

    public LiftMuscleCollection(Context context)
    {
        try
        {
            db = new DatabaseHelper(context);
            _context = context;
            _collection = new LinkedList<LiftMuscle>();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void AddLiftMuscle(LiftMuscle liftMuscle)
    {
        _collection.add(liftMuscle);
    }

    public void SetLiftId(int ID)
    {
        for(LiftMuscle liftMuscle: _collection)
        {
            liftMuscle.liftId = ID;
        }
    }

    public void SetIsNew(boolean isNew)
    {
        for(LiftMuscle liftMuscle: _collection)
        {
            liftMuscle.IsNew = isNew;
        }
    }

    public void Save()
    {
        for (LiftMuscle liftMuscle: _collection)
        {
            liftMuscle.Save();
        }
    }

    public void LoadAll(Context context)
    {
        _collection.clear();
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT _id, liftId, workoutId FROM LiftMuscle", null);

        while(cursor.moveToNext())
        {
            LiftMuscle LiftMuscle = new LiftMuscle(context);

            LiftMuscle.Id = cursor.getInt(cursor.getColumnIndex("_id"));
            LiftMuscle.liftId = cursor.getInt(cursor.getColumnIndex("liftId"));
            LiftMuscle.muscleId = cursor.getInt(cursor.getColumnIndex("muscleId"));

            _collection.add(LiftMuscle);
        }
    }

    public int Count()
    {
        return _collection.size();
    }

}