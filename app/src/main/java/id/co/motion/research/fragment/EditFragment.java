package id.co.motion.research.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

public class EditFragment extends Fragment {

    /*============================================================================================*/
    // Dagger2

    @Inject
    BriteDatabase db;

    /*--------------------------------------------------------------------------------------------*/

    /*============================================================================================*/
    // Constructor

    public static EditFragment newInstance() {

        Bundle args = new Bundle();

        EditFragment fragment = new EditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*--------------------------------------------------------------------------------------------*/

    /*============================================================================================*/
    // Override Method

    /*--------------------------------------------------------------------------------------------*/
}
