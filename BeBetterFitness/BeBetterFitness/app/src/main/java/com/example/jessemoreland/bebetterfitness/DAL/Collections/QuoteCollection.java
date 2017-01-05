package com.example.jessemoreland.bebetterfitness.DAL.Collections;

import android.content.Context;
import android.database.Cursor;

import com.example.jessemoreland.bebetterfitness.DAL.CurrentLift;
import com.example.jessemoreland.bebetterfitness.DAL.DatabaseHelper;
import com.example.jessemoreland.bebetterfitness.DAL.Lift;
import com.example.jessemoreland.bebetterfitness.DAL.Quote;

import java.util.LinkedList;
import java.util.List;

public class QuoteCollection
{
    private Context _context;
    public List<Quote> _collection;

    DatabaseHelper db;

    public QuoteCollection(Context context)
    {
        try {
            db = new DatabaseHelper(context);
            _context = context;
            _collection = new LinkedList<Quote>();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void LoadAll(Context context)
    {
        _collection.clear();
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT Quote, Author, Enabled FROM Quotes", null);

        while(cursor.moveToNext())
        {
            Quote quoteToAdd = new Quote(_context);
            quoteToAdd.Quote = cursor.getString(cursor.getColumnIndex("Quote"));
            quoteToAdd.Author = cursor.getString(cursor.getColumnIndex("Author"));
            quoteToAdd.Enabled = cursor.getInt(cursor.getColumnIndex("Enabled")) > 0;

            _collection.add(quoteToAdd);
        }
    }

    public void LoadEnabledQuotes(Context context)
    {
        _collection.clear();

        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT Quote, Author, Enabled FROM CurrentLifts WHERE Enabled = 1", null);

        while(cursor.moveToNext())
        {
            Quote quoteToAdd = new Quote(_context);
            quoteToAdd.Quote = cursor.getString(cursor.getColumnIndex("Quote"));
            quoteToAdd.Author = cursor.getString(cursor.getColumnIndex("Author"));
            quoteToAdd.Enabled = cursor.getInt(cursor.getColumnIndex("Enabled")) > 0;

            _collection.add(quoteToAdd);
        }
    }

    public int Count()
    {
        return _collection.size();
    }
}