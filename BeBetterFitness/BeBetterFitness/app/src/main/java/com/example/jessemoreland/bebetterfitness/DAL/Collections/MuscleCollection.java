package com.example.jessemoreland.bebetterfitness.DAL.Collections;

import android.content.Context;
import android.database.Cursor;

import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.DAL.Muscle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MuscleCollection
{
    private Context _context;
    public List<Muscle> _collection;
    private DatabaseHelper db;

    public MuscleCollection(Context context)
    {
        db = new DatabaseHelper(context);
        _context = context;
        _collection = new ArrayList<Muscle>();
    }


    public void LoadAll()
    {
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT _id, MuscleName FROM Muscle", null);

        while(cursor.moveToNext())
        {
            Muscle currentMuscle = new Muscle(_context);
            currentMuscle.Id = cursor.getInt(cursor.getColumnIndex("_id"));
            currentMuscle.name = cursor.getString(cursor.getColumnIndex("MuscleName"));
            _collection.add(currentMuscle);
        }

        cursor.close();
    }
    public Muscle FindItemByPrimaryKey( int primaryKey)
    {
        Muscle muscleToReturn = new Muscle(_context);
        muscleToReturn.Id = -1;
        for(int i = 0; i < _collection.size(); i++)
        {
            if(_collection.get(i).Id == primaryKey)
            {
                muscleToReturn = _collection.get(i);
                break;
            }
        }

        return muscleToReturn;
    }
}