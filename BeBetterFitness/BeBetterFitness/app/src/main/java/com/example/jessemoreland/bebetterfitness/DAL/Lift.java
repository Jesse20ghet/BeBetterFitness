package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jessemoreland.bebetterfitness.DAL.Collections.LiftMuscleCollection;

public class Lift
{
    private Context _context;
    public int Id;
    public String LiftName;
    public String LiftDescription;
    public boolean IsNew;
    public boolean IsDirty;
    public LiftMuscleCollection _liftMuscleCollection;
    private DatabaseHelper db;

    public Lift(Context context)
    {
        db = new DatabaseHelper(context);
        _context = context;
        _liftMuscleCollection = new LiftMuscleCollection(context);
    }

    public void Load()
    {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        try
        {
            String[] selectionArgs = new String[1];
            selectionArgs[0] = Integer.toString(Id);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT _id, LiftName, Description FROM Lift WHERE _id = ?", selectionArgs);
            cursor .moveToFirst();

            LiftName = cursor.getString(cursor.getColumnIndex("LiftName"));
            LiftDescription = cursor.getString(cursor.getColumnIndex("Description"));

            _liftMuscleCollection.LoadByLiftId(Id);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void Delete()
    {
        _liftMuscleCollection.DeleteAll();
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        String[] selectionArgs = new String[1];
        selectionArgs[0] = Integer.toString(Id);

        sqLiteDatabase.execSQL("DELETE FROM Lift WHERE _id = ?", selectionArgs);
    }

    public void Save()
    {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        try
        {
            if(IsNew)
            {
                sqLiteDatabase.execSQL("INSERT INTO Lift values(NULL, ?, ? )", new String[]{LiftName, LiftDescription});

                Cursor cursor = sqLiteDatabase.rawQuery("SELECT last_insert_rowid() FROM Lift", null);
                cursor.moveToFirst();
                int ID = cursor.getInt(0);
                cursor.close();

                _liftMuscleCollection.SetLiftId(ID);
                _liftMuscleCollection.SetIsNew(true);

                _liftMuscleCollection.Save();
            }
            else if(IsDirty)
            {
                String[] selectionArgs = new String[3];
                selectionArgs[0] = LiftName;
                selectionArgs[1] = LiftDescription;
                selectionArgs[2] = Integer.toString(Id);

                sqLiteDatabase.execSQL("UPDATE Lift SET LiftName = ?, Description = ? WHERE _id = ?", selectionArgs);

                _liftMuscleCollection.SetLiftId(Id);
                _liftMuscleCollection.SetIsNew(true);
                _liftMuscleCollection.Save();
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return this.LiftName;
    }
}