package id.co.motion.research.module;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import id.co.motion.research.db.MarkerDb;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Module
public class DbModule {
    @Provides
    @Singleton
    SQLiteOpenHelper provideSQLiteOpenHelper(Application application) {
        return new MarkerDb(application);
    }

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return SqlBrite.create(new SqlBrite.Logger() {
            @Override
            public void log(String message) {
                Timber.tag("Database").v(message);
            }
        });
    }

    @Provides
    @Singleton
    BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper sqLiteOpenHelper) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(sqLiteOpenHelper, Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }
}
