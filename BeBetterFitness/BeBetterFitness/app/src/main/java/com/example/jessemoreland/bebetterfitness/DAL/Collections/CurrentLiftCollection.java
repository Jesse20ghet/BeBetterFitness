package com.example.jessemoreland.bebetterfitness.DAL.Collections;

import android.content.Context;
import android.database.Cursor;

import com.example.jessemoreland.bebetterfitness.DAL.CurrentLift;
import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class CurrentLiftCollection
{
    private Context _context;
    public List<CurrentLift> _collection;

    DatabaseHelper db;

    public CurrentLiftCollection(Context context)
    {
        try {
            db = new DatabaseHelper(context);
            _context = context;
            _collection = new LinkedList<CurrentLift>();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void LoadAll(Context context)
    {
        _collection.clear();
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT _id, LiftId FROM CurrentLifts", null);

        while(cursor.moveToNext())
        {
            CurrentLift liftToAdd = new CurrentLift(context);

            liftToAdd.Id = cursor.getInt(cursor.getColumnIndex("_id"));
            liftToAdd.LiftId = cursor.getInt(cursor.getColumnIndex("LiftId"));

            _collection.add(liftToAdd);
        }
    }

    public boolean IsLiftFinished(Lift lift)
    {
        for(int i = 0; i < _collection.size(); i++)
        {
            if(_collection.get(i).LiftId == lift.Id)
                return true;
        }

        return false;
    }

    public CurrentLift FindItemByPrimaryKey( int primaryKey)
    {
        CurrentLift cl = new CurrentLift(_context);

        for(int i = 0; i < _collection.size(); i++)
        {
            if(_collection.get(i).Id == primaryKey)
            {
                cl = _collection.get(i);
                break;
            }
        }

        return cl;
    }

    public int Count()
    {
        return _collection.size();
    }
}