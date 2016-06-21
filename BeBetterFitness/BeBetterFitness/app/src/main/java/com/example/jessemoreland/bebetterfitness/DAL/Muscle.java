package com.example.jessemoreland.bebetterfitness.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Property;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class Muscle
{
    private Context _context;
    public int id;
    public String name;

    public Muscle(Context context)
    {
        _context = context;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}