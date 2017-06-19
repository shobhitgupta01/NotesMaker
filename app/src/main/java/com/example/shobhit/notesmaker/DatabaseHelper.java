package com.example.shobhit.notesmaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shobhit on 17/6/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Name
    private static final String  DB_Name = "MyDatabase";

    //Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_NOTES = "notes";

    //COLUMNS FOR USERS TABLE
    private static final String KEY_UNAME = "uname";  //also for notes table
    private static final String KEY_PASS = "password";
    
    //COLUMNS FOR NOTES TABLE
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";

    //USERS CREATE STATEMENT
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" + KEY_UNAME + " TEXT PRIMARY KEY, " + KEY_PASS + " TEXT" +")";

    //NOTES CREATE STATEMENT
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES + "(" + KEY_UNAME + " TEXT, " + KEY_TITLE + " TEXT," + KEY_CONTENT +" TEXT"+")";

    public static DatabaseHelper dbHelper = null;

    private DatabaseHelper(Context context){
        super(context,DB_Name,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        //CREATE TABLES AGAIN
        onCreate(db);
    }

    /**
     * Method returning singleton object
     */
    public static DatabaseHelper getDbHelper(Context context)
    {
        if(dbHelper == null)
        {
            dbHelper = new DatabaseHelper(context);
        }
        return dbHelper;
    }

    /**
     * Creating new user
     */
    public void createUser(Users users)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_UNAME,users.getUname());
        contentValues.put(KEY_PASS,users.getPassword());

        //inserting row
        db.insert(TABLE_USERS,null,contentValues);

        //closing database
        db.close();
    }

    /**
     * Creating a new note
     */
    public void createNote(Notes notes,String uname)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_UNAME,uname);
        contentValues.put(KEY_TITLE,notes.getTitle());
        contentValues.put(KEY_CONTENT,notes.getContent());

        //inserting row
        db.insert(TABLE_NOTES,null,contentValues);

        //closing database
        db.close();
    }

    /**
     * Checking if user exists
     */

    public boolean ifUserExists(Users users)
    {
        boolean found = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_USERS;

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                if(cursor.getString(0).equals(users.getUname())){
                    found = true;
                    break;

                }
            }while (cursor.moveToNext());
        }
        return found;
    }

    public boolean ifCorrectPassword(Users users)
    {
        boolean correct = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_USERS;

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                if(cursor.getString(0).equals(users.getUname()) && cursor.getString(1).equals(users.getPassword())){
                    correct = true;
                    break;

                }
            }while (cursor.moveToNext());
        }
        return correct;
    }

    /**
     * Method to check if there are notes of the user
     */

    public boolean ifNotes(String userName){
        boolean present = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_NOTES;

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                if(cursor.getString(0).equals(userName)){
                    present = true;
                    break;
                }
            }while (cursor.moveToNext());
        }
        return present;
    }

    public List<Notes> getNotes(String userName) {

        List<Notes> notesList = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(0).equals(userName)) {

                    Notes notes = new Notes(userName, cursor.getString(1), cursor.getString(2));
                    notesList.add(notes);
                }
            } while (cursor.moveToNext());
        }
        return notesList;
    }


        public int updateNote(Notes notes , Notes oldNotes,String userName)
        {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put(KEY_UNAME,userName);
            contentValues.put(KEY_TITLE,notes.getTitle());
            contentValues.put(KEY_CONTENT,notes.getContent());

            //updating row
            return db.update(TABLE_NOTES,contentValues,KEY_TITLE+" = ? ",new String[]{oldNotes.getTitle()});

        }

        public void deleteNote(Notes notes)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NOTES,KEY_TITLE+" = ? ",new String[]{notes.getTitle()});
            db.close();
        }



}
