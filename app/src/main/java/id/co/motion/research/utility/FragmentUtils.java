package id.co.motion.research.utility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import id.co.motion.research.R;
import id.co.motion.research.fragment.HomeFragment;

public class FragmentUtils {

    /*============================================================================================*/
    // Constant

    private static final String TAG = "FragmentUtils";

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Private Method

    private static void goFragment(AppCompatActivity appCompatActivity, Fragment fragment,
                                   Bundle bundle, Boolean isBackable,
                                   int enter, int exit, int popEnter, int popExit) {

        if (bundle != null) fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        if (enter != 0 && exit != 0)
            fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit);
        fragmentTransaction.replace(R.id.activity_main_content, fragment);
        if (isBackable) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private static void goFragment(Object object, Fragment fragment,
                                   Bundle bundle, Boolean isBackable,
                                   int enter, int exit, int popEnter, int popExit) {

        if (object.getClass().getSuperclass().equals(AppCompatActivity.class)) {
            AppCompatActivity activity = (AppCompatActivity) object;
            goFragment(activity, fragment, bundle, isBackable, enter, exit, popEnter, popExit);

        } else if (object.getClass().getSuperclass().equals(Fragment.class)) {
            Fragment currentFragment = (Fragment) object;
            AppCompatActivity activity = (AppCompatActivity) currentFragment.getActivity();
            goFragment(activity, fragment, bundle, isBackable, enter, exit, popEnter, popExit);

        } else {
            Log.d(TAG, "Unknown type");
        }
    }

    private static void goFragment(Object object, Fragment fragment) {
        goFragment(object, fragment, null, true, 0, 0, 0, 0);
    }

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Public Method

    public static Fragment openHome(Object object) {
        Fragment fragment = HomeFragment.newInstance();
        goFragment(object, fragment);
        return fragment;
    }

    /*--------------------------------------------------------------------------------------------*/

}
