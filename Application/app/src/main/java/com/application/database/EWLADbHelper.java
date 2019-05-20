package com.application.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// 해당 클래스는 helper로 데이터베이스를 컨넥션 연동 시키기 위해 제작된 클래스입니다.
public class EWLADbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private Context mycontext;
    private static String DB_NAME = "EWLA.db";
    public SQLiteDatabase myDatabase;
    private String DB_PATH = "/data/data/"
            + mycontext.getApplicationContext().getPackageName()
            + "/databases/";

    private static EWLADbHelper sInstance;

    public EWLADbHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        this.mycontext = context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println("Database exists");
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    private boolean checkdatabase() {
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {

        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream("/data/data/com.application/databases/EWLA.db");

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();

    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public static EWLADbHelper getsInstance(Context context) throws IOException {
        if (sInstance == null) {
            sInstance = new EWLADbHelper(context);
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
