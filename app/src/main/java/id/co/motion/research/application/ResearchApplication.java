package id.co.motion.research.application;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

import id.co.motion.research.BuildConfig;
import id.co.motion.research.component.DaggerResearchComponent;
import id.co.motion.research.component.ResearchComponent;
import id.co.motion.research.module.AppModule;
import id.co.motion.research.module.DbModule;
import timber.log.Timber;

public final class ResearchApplication extends Application {

    /*============================================================================================*/
    // Member

    private ResearchComponent mResearchComponent;

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Override Method

    @Override
    public void onCreate() {
        super.onCreate();

        /*========================================================================================*/
        // Leak Canary

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        LeakCanary.install(this);

        /*----------------------------------------------------------------------------------------*/


        /*========================================================================================*/
        // Timber

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        /*----------------------------------------------------------------------------------------*/


        /*========================================================================================*/
        // Dagger2

        mResearchComponent = DaggerResearchComponent.builder()
                .appModule(new AppModule(this))
                .dbModule(new DbModule())
                .build();

        /*----------------------------------------------------------------------------------------*/

    }

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Helper Method

    public static ResearchComponent getComponent(Context context) {
        return ((ResearchApplication) context.getApplicationContext()).mResearchComponent;
    }

    /*--------------------------------------------------------------------------------------------*/

}
