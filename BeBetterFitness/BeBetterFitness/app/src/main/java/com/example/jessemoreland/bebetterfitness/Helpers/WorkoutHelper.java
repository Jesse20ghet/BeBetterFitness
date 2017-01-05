package com.example.jessemoreland.bebetterfitness.Helpers;

import android.content.Context;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.CompletedWorkoutCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Collections.WorkoutCollection;
import com.example.jessemoreland.bebetterfitness.DAL.Workout;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.Date;
import java.util.Random;

/**
 * Created by JesseMoreland on 7/6/2016.
 */
public class WorkoutHelper
{
    private Context _context;

    public WorkoutHelper(Context context)
    {
        try
        {
            _context = context;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public String GetAverageWorkoutLength()
    {
        CompletedWorkoutCollection completedWorkoutCollection = new CompletedWorkoutCollection(_context);

        completedWorkoutCollection.LoadAll(_context);

        Integer totalHours = 0;
        Integer totalMinutes = 0;
        Integer totalSeconds = 0;

        for(int i = 0; i < completedWorkoutCollection.Count(); i++)
        {
            DateTime startDay = completedWorkoutCollection._collection.get(i).StartTime;
            DateTime endDay = completedWorkoutCollection._collection.get(i).EndTime;

            Duration timeBetween = new Duration(startDay, endDay);
            totalSeconds = totalSeconds + (int)timeBetween.getStandardSeconds();
        }

        if(completedWorkoutCollection.Count() > 0)
        {
            totalSeconds = totalSeconds / completedWorkoutCollection.Count();
        }

        totalHours = totalSeconds / 3600;
        totalMinutes = ((totalSeconds % 3600) / 60);


        return totalHours.toString() + "h:" + totalMinutes.toString() + "m";
    }

    public Workout GetRandomWorkout()
    {
       try
       {
           WorkoutCollection wCollection = new WorkoutCollection(_context);
           wCollection.LoadAll(_context);
           int numberOfWorkouts = wCollection.Count();

           Random rand = new Random();
           int index = rand.nextInt(numberOfWorkouts);

           if(index == numberOfWorkouts)
               index--;

           Workout randomWorkout = wCollection._collection.get(index);
           return randomWorkout;
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
           return new Workout(_context);
       }
    }
}
