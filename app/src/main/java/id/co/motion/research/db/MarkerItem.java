package id.co.motion.research.db;

import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.Collection;
import java.util.Collections;

import id.co.motion.research.table.MarkerTable;
import id.co.motion.research.utility.DbUtils;
import rx.functions.Func1;

@AutoValue
public abstract class MarkerItem implements Parcelable {
    private static String ALIAS_MARKER = "marker";

    private static String MARKER_ID = ALIAS_MARKER + "." + MarkerTable.COL_ID;
    private static String MARKER_NAME = ALIAS_MARKER + "." + MarkerTable.COL_NAME;
    private static String MARKER_LAT = ALIAS_MARKER + "." + MarkerTable.COL_LAT;
    private static String MARKER_LNG = ALIAS_MARKER + "." + MarkerTable.COL_LNG;

    public static Collection<String> TABLES = Collections.singletonList(MarkerTable.NAME);
    public static String QUERY = ""
            + "SELECT " + MARKER_ID + ", " + MARKER_NAME + ", " + MARKER_LAT + ", " + MARKER_LNG
            + " FROM " + MarkerTable.NAME + " AS " + ALIAS_MARKER;

    public abstract long id();

    public abstract String name();

    public abstract String lat();

    public abstract String lng();

    public static Func1<Cursor, MarkerItem> MAPPER = new Func1<Cursor, MarkerItem>() {
        @Override
        public MarkerItem call(Cursor cursor) {
            long id = DbUtils.getLong(cursor, MarkerTable.COL_ID);
            String name = DbUtils.getString(cursor, MarkerTable.COL_NAME);
            String lat = DbUtils.getString(cursor, MarkerTable.COL_LAT);
            String lng = DbUtils.getString(cursor, MarkerTable.COL_LNG);
            return new AutoValue_MarkerItem(id, name, lat, lng);
        }
    };
}

