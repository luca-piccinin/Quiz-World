package com.example.quizworld;

import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class MySQLite extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ClassificaDB";

    // Books table name
    private static final String TABLE_RISULTATI = "risultati";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USER = "utente";
    private static final String KEY_LENGUAGE = "lingua";
    private static final String KEY_ARGUMENT = "argomento";
    private static final String KEY_TOTAL = "totali";
    private static final String KEY_CORRECT = "corrette";

    private static final String[] COLUMNS = {KEY_ID,KEY_USER,KEY_LENGUAGE,KEY_ARGUMENT,KEY_TOTAL,KEY_CORRECT};

    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_RISULTATI_TABLE = "CREATE TABLE risultati ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "utente TEXT, "+
                "lingua TEXT, "+
                "argomento TEXT, "+
                "totali TEXT, "+
                "corrette TEXT )";

        // create books table
        db.execSQL(CREATE_RISULTATI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS risultati");

        // create fresh books table
        this.onCreate(db);
    }

    public void addResult(Risultati ris){
        // get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USER, ris.getUtente());
        values.put(KEY_LENGUAGE, ris.getLingua());
        values.put(KEY_ARGUMENT, ris.getArgomento());
        values.put(KEY_TOTAL, ris.getTotali());
        values.put(KEY_CORRECT, ris.getCorrette());

        // insert
        db.insert(TABLE_RISULTATI,
                null,
                values); // key/value -> keys = column names/ values = column values

        db.close();
    }

    public Risultati getBook(int id){

        // get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // build query
        Cursor cursor =
                db.query(TABLE_RISULTATI, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // build book object
        Risultati ris = new Risultati();
        ris.setId(Integer.parseInt(cursor.getString(0)));
        ris.setUtente(cursor.getString(1));
        ris.setLingua(cursor.getString(2));
        ris.setArgomento(cursor.getString(3));
        ris.setTotali(Integer.parseInt(cursor.getString(4)));
        ris.setCorrette(Integer.parseInt(cursor.getString(5)));

        // return book
        return ris;
    }

    public List<Risultati> getAllBooks() {
        List<Risultati> aux = new LinkedList<Risultati>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_RISULTATI;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Risultati ris = null;
        if (cursor.moveToFirst()) {
            do {
                ris = new Risultati();
                ris.setId(Integer.parseInt(cursor.getString(0)));
                ris.setUtente(cursor.getString(1));
                ris.setLingua(cursor.getString(2));
                ris.setArgomento(cursor.getString(3));
                ris.setTotali(Integer.parseInt(cursor.getString(4)));
                ris.setCorrette(Integer.parseInt(cursor.getString(5)));

                // Add book to books
                aux.add(ris);
            } while (cursor.moveToNext());
        }

        // return books
        return aux;
    }
}