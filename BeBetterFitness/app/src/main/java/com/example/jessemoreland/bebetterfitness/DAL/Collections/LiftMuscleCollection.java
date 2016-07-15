package com.example.jessemoreland.bebetterfitness.DAL.Collections;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public void DeleteAll()
    {
        for(int i = 0; i < _collection.size(); ++i)
        {
            _collection.get(i).Delete();
        }
    }

    public void Save()
    {
        for (LiftMuscle liftMuscle: _collection)
        {
            liftMuscle.Save();
        }
    }

    public void LoadByLiftId(int liftId)
    {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        try
        {
            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(liftId);

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT _id, liftId, muscleId FROM LiftMuscle WHERE liftId = ?", selectionArgs);

            while(cursor.moveToNext())
            {
                LiftMuscle liftMuscle = new LiftMuscle(_context);

                liftMuscle.Id = cursor.getInt(cursor.getColumnIndex("_id"));
                liftMuscle.liftId = cursor.getInt(cursor.getColumnIndex("liftId"));
                liftMuscle.muscleId = cursor.getInt(cursor.getColumnIndex("muscleId"));
                _collection.add(liftMuscle);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void LoadAll()
    {
        _collection.clear();
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT _id, liftId, workoutId FROM LiftMuscle", null);

        while(cursor.moveToNext())
        {
            LiftMuscle LiftMuscle = new LiftMuscle(_context);

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