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

            // ---------------------------- PEC AND TRICEP WORKOUTS -----------------------------------------

            db.execSQL("INSERT INTO Lift Values (1, 'Bench Press Dumbbell', 'Take two dumbbells and lay on a bench. Keep the weights above your chest, arms fully extended. Lower the dumbbells down to the left and right of your pecs. Push them to starting position. Repeat. ')");
            db.execSQL("INSERT INTO Lift Values (2, 'Bench Press Barbell Standard Grip', 'Lay down on a bench with a barbell located on a rest above you. Grab and lift the bar, keeping hands shoulder width apart. Lower to chest. Push until arms are fully extended. Repeat.')");
            db.execSQL("INSERT INTO Lift Values (3, 'Seated Dumbbell Overhead Extenstion', 'Sit upright on a bench with a back. Grab a dumbbell by one of the weighted ends keeping hands tight on the weighted end. Extend arms above your head. Lower weight behind your head until elbows are 90 degrees. Fully extend arms above your head. Repeat.')");
            db.execSQL("INSERT INTO Lift Values (4, 'Skull Crushers EZ-Bar', 'Lay down on a bench. Take an EZ-Bar and grab it with your palms facing away from you. Extend arms straight up above your chest. Keeping your elbows in and in place, lower the bar down toward your forehead. When it almost touches yoru forehead, extend arms back to start position. Repeat. ')");
            db.execSQL("INSERT INTO Lift Values (5, 'Skull Crushers Dumbbell', 'Lay down on a bench. Take two dumbbells and hold them above your chest, arms fully extended. Keep your elbows in place and upper arm straight. Lower than dumbbells toward your forehead. When it almost touches your forehead, extend arms back to starting position. Repeat.')");
            db.execSQL("INSERT INTO Lift Values (6, 'Dips', 'Put your arms behind your back with palms on a bench, arms fully extended. Keep feet out in front of you. Lower your body down until your elbows are at 90 degrees. Return to starting position. Repeat.')");
            db.execSQL("INSERT INTO Lift Values (7, 'Dumbbell Flys', 'Lay down on a bench. Take two dumbells and hold them above your chest, arms fully extended. Keep arms slightly bent and lower your arms to your side, away from your body until you feel a stretch in your chest. Pull back to starting position. Repeat. ')");
            db.execSQL("INSERT INTO Lift Values (8, 'Seated Dumbbell flys', '')");

            // ---------------------- BICEP WORKOUTS ---------------------------------------

            db.execSQL("INSERT INTO Lift Values (9, 'Bicep Curls Dumbbell Standing', '')");
            db.execSQL("INSERT INTO Lift Values (10, 'Bicep Curls Dumbbell Seated', '')");
            db.execSQL("INSERT INTO Lift Values (11, 'Bicep Hammer Curls Standing', '')");
            db.execSQL("INSERT INTO Lift Values (12, 'Bicep Hammer Curls Seated', '')");

            // ------------------ SHOULDER WORKOUTS -----------------------------------------

            db.execSQL("INSERT INTO Lift Values (13, 'Military Press Barbell Seated', '')");
            db.execSQL("INSERT INTO Lift Values (14, 'Lateral Raise Dumbbell', '')");
            db.execSQL("INSERT INTO Lift Values (15, 'Rear-Delt Raise', '')");
            db.execSQL("INSERT INTO Lift Values (16, 'Reverse Machine Fly', '')");
            db.execSQL("INSERT INTO Lift Values(17, 'Shoulder Fly', '')");

            // ----------------- BACK WORKOUTS ------------------------------------------

            db.execSQL("INSERT INTO Lift Values (18, 'PullDown', '')");
            db.execSQL("INSERT INTO Lift Values (19, 'DeadLift Hex Bar', '')");
            db.execSQL("INSERT INTO Lift Values (20, 'Deadlift Barbell', '')");
            db.execSQL("INSERT INTO Lift Values (21, 'Bent Over Barbell Row', '')");
            db.execSQL("INSERT INTO Lift Values (22, 'Seated Cable Row', '')");
            db.execSQL("INSERT INTO Lift Values (23, 'Pullups', '')");

            // ------------------- TRAP WORKOUTS ----------------------------------------

            db.execSQL("INSERT INTO Lift Values (24, 'Shrugs Dumbbell', '')");
            db.execSQL("INSERT INTO Lift Values (25, 'Shrugs Barbell', '')");

            // ------------------- LEG WORKOUTS ------------------------------------

            db.execSQL("INSERT INTO Lift Values (26, 'Calf Raises', '')");
            db.execSQL("INSERT INTO Lift Values (27, 'Hamstring Curls', '')");
            db.execSQL("INSERT INTO Lift Values (28, 'Leg Extensions', '')");
            db.execSQL("INSERT INTO Lift Values (29, 'One legged Squats', '')");
            db.execSQL("INSERT INTO Lift Values (30, 'Squats', '')");

            // ------------------ AB WORKOUTS ------------------------------------

            db.execSQL("INSERT INTO Lift Values (31, 'Crunches', '')");
            db.execSQL("INSERT INTO Lift Values (32, 'Sit-Ups', '')");
            //db.execSQL("INSERT INTO Lift Values (33, '', '')");


            db.execSQL("CREATE TABLE 'WorkoutLift'" +
                    "( [_id] INTEGER PRIMARY KEY AUTOINCREMENT, [workoutId] INTEGER, [liftId] INTEGER, [Sets] SMALLINT, [Reps] SMALLINT, FOREIGN KEY(workoutId) REFERENCES Workout(_id), FOREIGN KEY(LiftId) REFERENCES Lift(_id) )");

            db.execSQL("INSERT INTO 'WorkoutLift' VALUES (NULL, 1, 2, 3, 3)");
            db.execSQL("INSERT INTO 'WorkoutLift' VALUES (NULL, 1, 11, 3, 3)");
            db.execSQL("INSERT INTO 'WorkoutLift' VALUES (NULL, 1, 16, 3, 3)");

            db.execSQL("CREATE TABLE 'LiftMuscle'" +
                    "([_id] INTEGER PRIMARY KEY AUTOINCREMENT, [liftId] INTEGER, [muscleId] INTEGER, FOREIGN KEY(liftId) REFERENCES Lift(_id), FOREIGN KEY (muscleId) REFERENCES Muscle(_id) )");

            db.execSQL("CREATE TABLE 'CurrentWorkout' ( [_id] INTEGER PRIMARY KEY AUTOINCREMENT, [WorkoutId] INTEGER, [DateStarted] smalldatetime, FOREIGN KEY (WorkoutId) REFERENCES Workout(_id))");
            db.execSQL("CREATE TABLE 'CurrentLifts' ([_id] INTEGER PRIMARY KEY AUTOINCREMENT, [LiftId] INTEGER, FOREIGN KEY (LiftId) REFERENCES Lift(_id))");

            //db.execSQL("CREATE TABLE 'CompletedWorkouts'([_id] INTEGER PRIMARY KEY AUTOINCREMENT, [WorkoutId] INTEGER, [StartTime] smalldatetime, [EndTime] smallDatetime, FOREIGN KEY (WorkoutId) REFERENCES Workout(_id))");
            //db.execSQL("CREATE TABLE 'CompletedLifts'([_id] INTEGER PRIMARY KEY AUTOINCREMENT, [LiftId] INTEGER, [Reps] INTEGER, [SETS] INTEGER, [WorkoutId] relatedWorkout, FOREIGN KEY (LiftId) REFERENCES Lift(_id))");

            db.execSQL("CREATE TABLE 'Quotes' ([_id] INTEGER PRIMARY KEY AUTOINCREMENT, [Quote] Text, [Author] Text, [Enabled] bit)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'The best preparation for tomorrow is doing your best today.', 'H. Jackson Brown Jr.', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Believe you can and you''re halfway there.', 'Theodore Roosevelt', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'I hated every minute of training, but I said, ''Dont quit. Suffer now and live the rest of your life as a champion.''', 'Muhammad Ali', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'If you always put limits on everything you do, physical or anything else. It will spread into your work and into your life. There are no limits. There are only plateaus, and you must not stay there, you must go beyond them.', 'Bruce Lee', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'No matter what people tell you, words and ideas can change the world.', 'Robin Williams', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'The measure of who we are is what we do with what we have.', 'Vince Lombardi', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'It always seems impossible until it''s done', 'Nelson Mandela', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Good, better, best. Never let it rest. ''Til your good is better and your better is best.', 'St. Jerome', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Life is 10% what happens to you and 90% how you react to it.', 'Charles R. Swindoll', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'You are never too old to set another goal or to dream a new dream.', 'C. S. Lewis', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'The way to get started is to quit talking and begin doing.', 'Walt Disney', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'It does not matter how slowly you go as long as you do not stop.', 'Confucius', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Start where you are. Use what you have. Do what you can.', 'Arthur Ashe', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Keep your eyes on the stars, and your feet on the ground.', 'Theodore Roosevelt', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'The will to win, the desire to succeed, the urge to reach your full potential... these are the keys that will unlock the door to personal excellence.', 'Confucius', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'If you can dream it, you can do it.', 'Walt Disney', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'I''d rather attempt to do something great and fail than to attempt to do nothing and succeed.', 'Robert H. Schuller', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'We may encounter many defeats but we must not be defeated.', 'Maya Angelou', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Either you run the day or the day runs you.', 'Jim Rohn', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'There is no passion to be found playing small - to settle for a life that is less than the one you are capable of living.', 'Nelson Mandela', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Without hard work, nothing grows but weeds.', 'Gordon B. Hinckly', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Motivation will almost always beat mere talent.', 'Norman Ralph Augustine', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'If you don''t design your own life plan, chances are you''ll fall into someone else''s plan. And guess what they have planned for you? Not much.', 'Jim Rohn', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'If you fell down yesterday, stand up today.', 'H. G. Wells', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Quality is not an act, it is a habit', 'Aristotle', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Seek first to understand, then to be understood.', 'Unknown', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'By failing to prepare, you are preparing to fail.', 'Benjamin Franklin', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'If you don''t like how things are, change it! You''re not a tree.', 'Jim Rohn', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Act as if what you do makes a difference. It does.', 'William James', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'If you''re going through hell, keep going.', 'Winston Churchill', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'I really believe that everyone has a talent, ability, or skill that he can mine to support himself and to succeed in life.', 'Dean Koontz', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Only I can change my life. No one can do it for me.', 'Carol Burnett', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Never give in and never give up.', 'Hubert H. Humphrey', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'I am not a has-been. I am a will be.', 'Lauren Bacall', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'You can''t expect to hit the jackpot if you don''t put a few nickels into the machine.', 'Flip Wilson', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'There is always room at the top.', 'Daniel Webster', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Do your work with your whole heart, and you will succeed - there''s so little competition.', 'Elbert Hubbard', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'I''ve always tried to go a step past wherever people expected me to end up.', 'Beverly Sills', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'I think people who are creative are the luckiest people on earth. I know that there are not shortcuts, but you must keep your faith in something greater than you, and keep doing what you love. Do what you love, and you will find the way to get it out to the world.', 'Judy Collins', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Opportunity does not knock, it presents itself when you beat down the door.', 'Kyle Chandler', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Go big or go home. Because it''s true. What do you have to lose?', 'Eliza Dushku', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Whatever you want in life, other people are going to want it too. Believe in yourself enough to accept the idea that you have an equal right to it.', 'Diane Sawyer', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'A somebody was once a nobody who wanted to and did.', 'John Burroughs', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'I am not afraid... I was born to do this.', 'Joan of Arc', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Believe that life is worth living and your belief will help create the fact.', 'William James', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Float like a butterfly, sting like a bee.', 'Muhammad Ali', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'I don''t have to be what you want me to be.', 'Muhammad Ali', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Fitness is not about being better than someone else. It''s about being better than you used to be.', 'Unknown', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'The only bad workout is the one you didn''t do.', 'Unknown', 1)");
            db.execSQL("INSERT INTO 'Quotes' VALUES(NULL, 'Sore. The most satisfying pain.', 'Unknown', 1)");




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
        db.execSQL("DROP TABLE IF EXISTS Quotes");
        //db.execSQL("DROP TABLE IF EXISTS CompletedLifts");
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