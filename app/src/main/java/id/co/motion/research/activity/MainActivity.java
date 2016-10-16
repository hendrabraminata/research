package id.co.motion.research.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import id.co.motion.research.R;
import id.co.motion.research.utility.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    /*============================================================================================*/
    // Member

    private Fragment mFragment;

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Override Method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragment = FragmentUtils.openHome(this);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    /*--------------------------------------------------------------------------------------------*/

}
