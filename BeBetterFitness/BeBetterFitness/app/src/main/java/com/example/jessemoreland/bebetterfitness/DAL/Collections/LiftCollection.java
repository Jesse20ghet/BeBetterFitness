package com.example.jessemoreland.bebetterfitness.DAL.Collections;

import android.content.Context;
import android.database.Cursor;

import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;

import java.util.LinkedList;
import java.util.List;

public class LiftCollection
{
    private Context _context;
    public List<Lift> _collection;

    DatabaseHelper db;

    public LiftCollection(Context context)
    {
        try {
            db = new DatabaseHelper(context);
            _context = context;
            _collection = new LinkedList<Lift>();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void LoadAll(Context context)
    {
        _collection.clear();
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT _id, LiftName, Description FROM Lift", null);

        while(cursor.moveToNext())
        {
            Lift liftToAdd = new Lift(context);

            liftToAdd.Id = cursor.getInt(cursor.getColumnIndex("_id"));
            liftToAdd.LiftName = cursor.getString(cursor.getColumnIndex("LiftName"));
            liftToAdd.LiftDescription = cursor.getString(cursor.getColumnIndex("Description"));
            //liftToAdd.Reps = cursor.getInt(cursor.getColumnIndex("Reps"));
            //liftToAdd.Sets = cursor.getInt(cursor.getColumnIndex("Sets"));

            _collection.add(liftToAdd);
        }
    }

    public Lift FindItemByPrimaryKey( int primaryKey)
    {
        Lift liftToReturn = new Lift(_context);
        liftToReturn.Id = -1;
        for(int i = 0; i < _collection.size(); i++)
        {
            if(_collection.get(i).Id == primaryKey)
            {
                liftToReturn = _collection.get(i);
                break;
            }
        }

        return liftToReturn;
    }

    public int Count()
    {
        return _collection.size();
    }

}