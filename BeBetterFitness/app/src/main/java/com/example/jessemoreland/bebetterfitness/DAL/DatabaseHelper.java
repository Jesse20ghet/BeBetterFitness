package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            db.execSQL("CREATE TABLE `Muscle` ( [_id] INTEGER PRIMARY KEY AUTOINCREMENT, [MuscleName] Text )");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Shoulder')");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Traps')");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Biceps')");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Triceps')");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Forearm')");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Pecs')");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Abs')");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Upper Back')");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Lower Back')");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Quads')");
            db.execSQL("INSERT INTO Muscle Values (NULL, 'Calves')");

            db.execSQL("CREATE TABLE 'Workout' ( [_id] INTEGER PRIMARY KEY AUTOINCREMENT, [WorkoutName] Text )");

            db.execSQL("INSERT INTO Workout Values (NULL, 'First Test Workout')");

            db.execSQL("CREATE TABLE 'Lift' ( [_id] INTEGER PRIMARY KEY AUTOINCREMENT, [LiftName] Text, [Description] Text )");
            db.execSQL("INSERT INTO Lift Values (1, 'Dumbbell Press', 'testDescrip for dumbell press')");
            db.execSQL("INSERT INTO Lift Values (2, 'Shoulder Fly', 'testDescrip for shoulder fly')");
            db.execSQL("INSERT INTO Lift Values (3, 'Lat Pull', 'testDescrip for lat pull down')");

            db.execSQL("CREATE TABLE 'WorkoutLift'" +
                    "( [_id] INTEGER PRIMARY KEY AUTOINCREMENT, [workoutId] INTEGER, [liftId] INTEGER, [Sets] SMALLINT, [Reps] SMALLINT, FOREIGN KEY(workoutId) REFERENCES Workout(_id), FOREIGN KEY(LiftId) REFERENCES Lift(_id) )");

            db.execSQL("INSERT INTO WorkoutLift VALUES (NULL, 1, 1, 2, 3)");
            db.execSQL("INSERT INTO WorkoutLift VALUES (NULL, 1, 2, 8, 8)");
            db.execSQL("INSERT INTO WorkoutLift VALUES (NULL, 1, 3, 12, 12)");

            db.execSQL("CREATE TABLE 'LiftMuscle'" +
                    "([_id] INTEGER PRIMARY KEY AUTOINCREMENT, [liftId] INTEGER, [muscleId] INTEGER, FOREIGN KEY(liftId) REFERENCES Lift(_id), FOREIGN KEY (muscleId) REFERENCES Muscle(_id) )");

            db.execSQL("CREATE TABLE 'CurrentWorkout' ( [_id] INTEGER PRIMARY KEY AUTOINCREMENT, [WorkoutId] INTEGER, [DateStarted] smalldatetime, FOREIGN KEY (WorkoutId) REFERENCES Workout(_id))");
            db.execSQL("CREATE TABLE 'CurrentLifts' ([_id] INTEGER PRIMARY KEY AUTOINCREMENT, [LiftId] INTEGER, FOREIGN KEY (LiftId) REFERENCES Lift(_id))");
            db.execSQL("CREATE TABLE 'CompletedWorkouts'([_id] INTEGER PRIMARY KEY AUTOINCREMENT, [WorkoutId] INTEGER, [StartTime] smalldatetime, [EndTime] smallDatetime, FOREIGN KEY (WorkoutId) REFERENCES Workout(_id))");

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Muscle");
        db.execSQL("DROP TABLE IF EXISTS Workout");
        db.execSQL("DROP TABLE IF EXISTS Lift");
        db.execSQL("DROP TABLE IF EXISTS WorkoutLift");
        db.execSQL("DROP TABLE IF EXISTS Workout");
        db.execSQL("DROP TABLE IF EXISTS LiftMuscle");
        db.execSQL("DROP TABLE IF EXISTS CurrentWorkout");
        db.execSQL("DROP TABLE IF EXISTS CurrentLifts");
        //db.execSQL("DROP TABLE IF EXISTS CompletedWorkouts");
        onCreate(db);
    }

    public String getAllRowsInTable(SQLiteDatabase db, String tableName) {
        Log.d("DbHelper", "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }

    public List<String> getAllTablesinDatabase(SQLiteDatabase db)
    {
        List<String> accumlativeString = new ArrayList<String>();

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                accumlativeString.add(c.getString(0));
                c.moveToNext();
            }
        }

        return accumlativeString;
    }
}