package id.co.motion.research.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.Marker;

import id.co.motion.research.table.MarkerTable;

public class MarkerDb extends SQLiteOpenHelper {

    /*============================================================================================*/
    // Member

    private static final String MARKER_DB = "MARKER_DB";
    private static final int MARKER_DB_VER = 1;

    private static final String CREATE_MARKER_TABLE = ""
            + "CREATE TABLE " + MarkerTable.NAME + "("
            + MarkerTable.COL_ID + " INTEGER NOT NULL PRIMARY KEY,"
            + MarkerTable.COL_NAME + " TEXT NOT NULL,"
            + MarkerTable.COL_LAT + " TEXT NOT NULL,"
            + MarkerTable.COL_LNG + " TEXT NOT NULL"
            + ")";

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Constructor

    public MarkerDb(Context context) {
        super(context, MARKER_DB, null, MARKER_DB_VER);
    }

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Override

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MARKER_TABLE);

        db.insert(MarkerTable.NAME, null, new MarkerTable.Builder()
                .name("Kaaba")
                .lat("21.422487")
                .lng("39.826206")
                .build());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*--------------------------------------------------------------------------------------------*/
}
