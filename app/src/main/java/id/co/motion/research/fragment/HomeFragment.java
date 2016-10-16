package id.co.motion.research.fragment;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.motion.research.R;
import id.co.motion.research.application.ResearchApplication;
import id.co.motion.research.db.MarkerItem;
import id.co.motion.research.table.MarkerTable;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

@RuntimePermissions
public final class HomeFragment extends Fragment implements
        OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    /*============================================================================================*/
    // Member

    private GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;
    private UiSettings mUiSettings;
    private Subscription mSubscription;
    private Location mLastLocation;
    private Marker mSelectedMarker;
    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Dagger2

    @Inject
    BriteDatabase db;

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Butter Knife

    @BindView(R.id.fragment_home_remove_button)
    Button fragment_home_remove_button;

    @BindView(R.id.fragment_home_edit_button)
    Button fragment_home_edit_button;

    @OnClick(R.id.fragment_home_save_button)
    public void fragment_home_save_button() {
        updateLastLocation();
        if (mLastLocation != null) {
            db.insert(MarkerTable.NAME, new MarkerTable.Builder()
                    .name("Marker")
                    .lat(String.valueOf(mLastLocation.getLatitude()))
                    .lng(String.valueOf(mLastLocation.getLongitude()))
                    .build());
        }
    }

    @OnClick(R.id.fragment_home_remove_button)
    public void fragment_home_remove_button() {
        Timber.d(mSelectedMarker.toString());
        if (mSelectedMarker != null) {
            int id = mHashMap.get(mSelectedMarker);
            db.delete(MarkerTable.NAME, MarkerTable.COL_ID + "=?", String.valueOf(id));
            mHashMap.remove(mSelectedMarker);

            mSelectedMarker = null;
            fragment_home_remove_button.setVisibility(View.GONE);
            fragment_home_edit_button.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.fragment_home_edit_button)
    public void setFragment_home_edit_button() {
    }

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Constructor

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Override Method

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ResearchApplication.getComponent(context).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();

        prepareMember();
        prepareView();
    }

    @Override
    public void onPause() {
        super.onPause();

        mSubscription.unsubscribe();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        HomeFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mUiSettings = mGoogleMap.getUiSettings();

        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);

        HomeFragmentPermissionsDispatcher.setMyLocationWithCheck(HomeFragment.this);

        mGoogleMap.setOnMapLongClickListener(this);
        mGoogleMap.setOnMarkerClickListener(this);

        mSubscription = db.createQuery(MarkerItem.TABLES, MarkerItem.QUERY)
                .mapToList(MarkerItem.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MarkerItem>>() {
                    @Override
                    public void call(List<MarkerItem> markerItems) {
                        mGoogleMap.clear();
                        for (int i = 0; i < markerItems.size(); i++) {
                            MarkerItem markerItem = markerItems.get(i);
                            Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.valueOf(markerItem.lat()), Double.valueOf(markerItem.lng())))
                                    .title(markerItem.name()));
                            mHashMap.put(marker, (int) markerItem.id());
                        }
                    }
                });
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Testing")
                .snippet("Testing snippet")
                .icon(BitmapDescriptorFactory.defaultMarker(0))
        );
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mSelectedMarker = marker;
        fragment_home_remove_button.setVisibility(View.VISIBLE);
        fragment_home_edit_button.setVisibility(View.VISIBLE);

        return false;
    }

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Helper Method

    private void prepareMember() {
        mGoogleMap = null;
        mUiSettings = null;
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_home_map);
        mLastLocation = null;
        mSelectedMarker = null;
    }

    private void prepareView() {
        mSupportMapFragment.getMapAsync(this);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void setMyLocation() {
        if (mUiSettings != null) {
            mUiSettings.setMyLocationButtonEnabled(true);
        }

        if (mGoogleMap != null) {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void updateLastLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
    }

    /*--------------------------------------------------------------------------------------------*/

}
