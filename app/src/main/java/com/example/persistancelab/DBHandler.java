package com.example.persistancelab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "namesdb";
    // This may be used for migration in the future.
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "names";
    private static final String ID_COL = "id";
    private static final String FIRST_COL = "first";
    private static final String LAST_COL = "last";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FIRST_COL + " TEXT,"
                        + LAST_COL + " TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FIRST_COL + " TEXT,"
                        + LAST_COL + " TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    public void add(Name n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRST_COL, n.getFirst());
        values.put(LAST_COL, n.getLast());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Name> getNames() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Name> names = new ArrayList<>();
        Cursor cursor =
                db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(new Name(cursor.getString(1),
                        cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }
}
