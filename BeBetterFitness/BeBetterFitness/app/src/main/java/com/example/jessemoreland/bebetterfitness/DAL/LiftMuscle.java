package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class LiftMuscle
{
    private Context _context;
    public int Id;
    public int liftId;
    public int muscleId;
    public boolean IsDirty;
    public boolean IsNew;
    private DatabaseHelper db;

    public LiftMuscle(Context context)
    {
        db = new DatabaseHelper(context);
        _context = context;
        IsDirty = false;
        IsNew = false;
    }

    public void Load()
    {

    }

    public void Save()
    {
        try
        {
            if(IsDirty)
            {
                Object[] args = new Object[3];
                args[0] = (Integer.toString(liftId));
                args[1] = (Integer.toString(muscleId));
                args[2] = (Integer.toString(Id));

                db.getWritableDatabase().execSQL("UPDATE LiftMuscle SET liftId = ?, muscleId = ? WHERE _id = ?", args);
            }
            else if(IsNew)
            {
                Object[] args = new Object[2];
                args[0] = liftId;
                args[1] = muscleId;

                db.getWritableDatabase().execSQL("INSERT INTO LiftMuscle VALUES(NULL, ?, ?)", args);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void Delete()
    {
        try
        {
            SQLiteDatabase sqlLiteDatabase = db.getWritableDatabase();

            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(Id);

            sqlLiteDatabase.execSQL("DELETE FROM LiftMuscle WHERE _id = ?", selectionArgs);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}