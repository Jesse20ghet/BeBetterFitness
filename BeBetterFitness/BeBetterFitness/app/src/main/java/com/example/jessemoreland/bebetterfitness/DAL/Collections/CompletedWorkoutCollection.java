package com.example.jessemoreland.bebetterfitness.DAL.Collections;

import android.content.Context;
import android.database.Cursor;

import com.example.jessemoreland.bebetterfitness.DAL.CompletedWorkout;
import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CompletedWorkoutCollection
{
    private Context _context;
    public List<CompletedWorkout> _collection;

    DatabaseHelper db;

    public CompletedWorkoutCollection(Context context)
    {
        try {
            db = new DatabaseHelper(context);
            _context = context;
            _collection = new LinkedList<CompletedWorkout>();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void LoadAll(Context context)
    {
        try {
            _collection.clear();
            Cursor cursor = db.getReadableDatabase().rawQuery("SELECT _id, WorkoutId, StartTime, EndTime FROM CompletedWorkouts", null);

            while (cursor.moveToNext()) {
                CompletedWorkout completedWorkoutToAdd = new CompletedWorkout(context);

                completedWorkoutToAdd.Id = cursor.getInt(cursor.getColumnIndex("_id"));
                completedWorkoutToAdd.WorkoutId = cursor.getInt(cursor.getColumnIndex("WorkoutId"));

                completedWorkoutToAdd.StartTime = new DateTime(cursor.getString(cursor.getColumnIndex("StartTime")));
                completedWorkoutToAdd.EndTime = new DateTime(cursor.getString(cursor.getColumnIndex("EndTime")));
//                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//
//                completedWorkoutToAdd.StartTime = dateFormat.parse(startTimeAsString);
//
//                String endTimeAsString = cursor.getString(cursor.getColumnIndex("EndTime"));
//                completedWorkoutToAdd.EndTime = dateFormat.parse(endTimeAsString);

                _collection.add(completedWorkoutToAdd);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public int Count()
    {
        return _collection.size();
    }
}