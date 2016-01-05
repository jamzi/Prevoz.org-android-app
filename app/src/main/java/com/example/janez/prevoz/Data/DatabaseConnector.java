package com.example.janez.prevoz.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnector {
    private static final String DATABASE_NAME = "CarsharesDB";
    private static final int DATABASE_VERSION = 1;

    // database object
    private SQLiteDatabase database;

    // database helper
    private DatabaseOpenHelper databaseOpenHelper;

    public DatabaseConnector(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Opens the database connection
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    /**
     * Closes the database connection
     */
    public void close() {
        if (database != null) database.close(); // close the database connection
    }

    /**
     * Adds a new contact to the database
     *
     * @param fromCity
     * @param toCity
     * @param date
     */
    public void insertCarshare(String fromCity, String toCity, String date) {
        final ContentValues newCarshare = new ContentValues();
        newCarshare.put("fromCity", fromCity);
        newCarshare.put("toCity", toCity);
        newCarshare.put("date", date);

        open();
        database.insert("carshares", null, newCarshare);
        close();
    }


    /**
     * Returns a Cursor with all contact information in the database
     * <p/>
     * The database connection object has to be open before calling
     * this method and closed afterwards.
     *
     * @return
     */
    public Cursor getAllCarshares() {
        return database.query("carshares", new String[]{"_id", "fromCity","toCity","date"},
                null, null, null, null, "_id DESC");
    }

    /**
     * Deletes the contact specified by the given id
     * <p/>
     * The database connection object has to be open before calling
     * this method and closed afterwards.
     *
     */
    public String deleteAllCarshares() {
        open();
        database.execSQL("delete from carshares");
        close();

        return "Zadnji vnosi uspešno pobrisani";
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        /**
         * Creates the contacts table when the database is created
         *
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            final String createQuery = "CREATE TABLE carshares" +
                    "(_id integer primary key autoincrement, " +
                    "fromCity TEXT, " +
                    "toCity TEXT, " +
                    "date TEXT);";

            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Executed each time the DB version is changed
        }

    }
}
