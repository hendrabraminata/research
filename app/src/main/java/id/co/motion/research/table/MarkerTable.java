package id.co.motion.research.table;

import android.content.ContentValues;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MarkerTable implements Parcelable {

    /*============================================================================================*/
    // Member

    public static final String NAME = "MARKER_TABLE";
    public static final String COL_ID = "COL_ID";
    public static final String COL_NAME = "COL_NAME";
    public static final String COL_LAT = "COL_LAT";
    public static final String COL_LNG = "COL_LNG";

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Helper Method

    public static final class Builder {
        private final ContentValues contentValues = new ContentValues();

        public Builder id(long id) {
            contentValues.put(COL_ID, id);
            return this;
        }

        public Builder name(String name) {
            contentValues.put(COL_NAME, name);
            return this;
        }

        public Builder lat(String lat) {
            contentValues.put(COL_LAT, lat);
            return this;
        }

        public Builder lng(String lng) {
            contentValues.put(COL_LNG, lng);
            return this;
        }

        public ContentValues build() {
            return contentValues;
        }
    }

    /*--------------------------------------------------------------------------------------------*/

}
