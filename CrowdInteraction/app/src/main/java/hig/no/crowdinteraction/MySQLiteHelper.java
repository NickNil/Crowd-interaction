package hig.no.crowdinteraction;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mimoza on 11/14/2014.
 * Class that creates a table CountryCodes, with the ID, country name, ioc and iso codes as columns in the SQLite database
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "CountriesDB";

    private static final String TABLE_NAME = "CountryCodes";

    // IOC Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_IOC = "ioc";
    private static final String KEY_ISO = "iso";
    private static final String KEY_NAME = "name";

    private static final String[] COLUMNS = {KEY_ID,KEY_IOC, KEY_ISO,KEY_NAME};

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create ioc codes table
        String CREATE_COUNTRYCODES_TABLE = "CREATE TABLE "+TABLE_NAME+" ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ioc TEXT, "+
                "iso TEXT, "+
                "name TEXT )";
        db.execSQL(CREATE_COUNTRYCODES_TABLE);

    }

    /**
     * Method to add countries to the table CountryCodes
     */
    public void addCountry(IOCandISOcodes iocCodes){

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.rawQuery("SELECT id FROM "+ TABLE_NAME, null).getCount();

        //Log.i("number of records", String.valueOf(count));

        if (count <226) {
            // 2. create ContentValues to add key "column"/value
            ContentValues values = new ContentValues();
            values.put(KEY_IOC, iocCodes.getIoc());
            values.put(KEY_ISO, iocCodes.getIso());
            values.put(KEY_NAME, iocCodes.getCoutryName());


            // 3. insert
            db.insert(TABLE_NAME, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values
        }

        // 4. close
        db.close();
    }
    /**
     * Method that gets all the countries from the table CountryCodes, used to show the countries in the nationality list
     */
    public List<IOCandISOcodes> getAllCountries() {
        List<IOCandISOcodes> codesList = new LinkedList<IOCandISOcodes>();

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build codes and add to list

        IOCandISOcodes codes = null;

        if (cursor.moveToFirst()) {
            do {
                codes = new IOCandISOcodes();
                codes.setId(Integer.parseInt(cursor.getString(0)));
                codes.setIoc(cursor.getString(1));
                codes.setIso(cursor.getString(2));
                codes.setCoutryName(cursor.getString(3));

                codesList.add(codes);
            } while (cursor.moveToNext());
        }

        db.close();
        // return codes
        return codesList;
    }

    /**
     * Method to get codes based on the id
     */
    public IOCandISOcodes getCodes(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        //db.rawQuery("SELECT * FROM " + TABLE_NAME + "WHERE id = ?", new String[]{String.valueOf(id)});

        // 2. build query
        Cursor cursor =
                db.query(TABLE_NAME, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit


        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build codes object
        IOCandISOcodes codes = new IOCandISOcodes();
        codes.setId(Integer.parseInt(cursor.getString(0)));
        codes.setIoc(cursor.getString(1));
        codes.setIso(cursor.getString(2));
        codes.setCoutryName(cursor.getString(3));

        //log
        Log.d("getCodes("+id+")", codes.toString());

        db.close();
        // 5. return codes
        return codes;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older countrycodes table if it exists
        db.execSQL("DROP TABLE IF EXISTS iocCodes");

        // create new countryCodes table
        this.onCreate(db);
    }
}
