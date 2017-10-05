package com.mini.smartroad;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.out.negotiate.FindStationsOutDto;
import com.mini.smartroad.dto.out.negotiate.StationOutDto;
import com.mini.smartroad.events.FailureEvent;
import com.mini.smartroad.events.FoundStationsEvent;

import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

public class SearchActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = SearchActivity.class.getName();
    public static final String USER_TOKEN = "user_token";
    private GoogleApiClient mGoogleApiClient;
    private LatLng currentLongLat;
    private GoogleMap mGoogleMap;
    private Marker currentLocationMarker;
    private EventBus bus = EventBus.getDefault();
    private volatile FindStationsOutDto findStationsOutDto;
    private HashMap<String, StationOutDto> stationsMap;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        if (intent != null) {
            userToken = intent.getStringExtra(USER_TOKEN);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bus.register(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (stationsMap != null) {
                    StationOutDto stationOutDto = stationsMap.get(marker.getId());
                    if (stationOutDto != null) {
                        ActionDialogFragment actionDialogFragment = new ActionDialogFragment();
                        actionDialogFragment.setArguments(new Bundle());
                        actionDialogFragment.getArguments().putSerializable(ActionDialogFragment.STATION, stationOutDto);
                        actionDialogFragment.show(getSupportFragmentManager(), "ACTION_DIALOG_" + stationOutDto.getToken());
                    }
                }
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            currentLongLat = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            addCurrentLocationMarker();
        }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLongLat).zoom(14).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        startAgentSearchStations();
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void addCurrentLocationMarker() {
        mGoogleMap.clear();
        stationsMap = new HashMap<>();
        if (findStationsOutDto != null) {
            List<StationOutDto> stations = findStationsOutDto.getStations();
            if (stations != null) {
                for (StationOutDto station : stations) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(station.getLatitude(), station.getLongitude()));
                    markerOptions.title(station.getName());
                    markerOptions.snippet(station.getEmail());
                    if (station.getActionType() == ActionType.REPRESENT) {
                        setMarkerIcon(station, markerOptions);
                    } else if (station.getActionType() == ActionType.ATTEND) {
                        setMarkerIcon(station, markerOptions);
                        markerOptions.alpha(0.8f);
                    } else {
                        setMarkerIcon(station, markerOptions);
                        markerOptions.alpha(0.5f);
                    }
                    Marker marker = mGoogleMap.addMarker(markerOptions);
                    stationsMap.put(marker.getId(), station);
                }
            }
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLongLat);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.car));
        currentLocationMarker = mGoogleMap.addMarker(markerOptions);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(new LatLng(52.204667, 20.959245));
        markerOptions2.title("Current Position");
        markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mGoogleMap.addMarker(markerOptions2);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(new LatLng(52.196207, 20.964174));
        markerOptions3.title("Current Position");
        markerOptions3.icon(BitmapDescriptorFactory.fromResource(R.mipmap.gas_station));
        mGoogleMap.addMarker(markerOptions3);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(new LatLng(52.195904, 20.962221));
        markerOptions4.title("Current Position");
        markerOptions4.icon(BitmapDescriptorFactory.fromResource(R.mipmap.gas_station));
        mGoogleMap.addMarker(markerOptions4);
    }

    private void setMarkerIcon(StationOutDto station, MarkerOptions markerOptions) {
        if (station.getCounter() >= station.getMinCarsAmount()) {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else if (station.getCounter() == station.getMinCarsAmount() - 1) {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        } else if (station.getCounter() == station.getMinCarsAmount() - 2) {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        } else {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        currentLongLat = new LatLng(location.getLatitude(), location.getLongitude());
        addCurrentLocationMarker();
        startAgentSearchStations();
    }

    public void onEvent(FoundStationsEvent event) {
        Log.i(TAG, event.toString());
        findStationsOutDto = event.getFindStationsOutDto();
    }

    public void onEvent(FailureEvent failureEvent) {
        Toast.makeText(this, failureEvent.getMessage(), Toast.LENGTH_LONG).show();
        Log.e(TAG, failureEvent.getMessage());
    }

    private void startAgentSearchStations() {
//        ConnectionUtils.startAgent(SearchStationsClientAgent.class.getName() + UUID.randomUUID(),
//                SearchStationsClientAgent.class, getApplicationContext(),
//                new Object[]{userToken, currentLongLat.latitude, currentLongLat.longitude, Long.valueOf((long) (1000 / mGoogleMap.getCameraPosition().zoom))});
    }

    public void startAgentAction(String stationToken, ActionType actionType, Boolean value, Boolean redo) {
//        ConnectionUtils.startAgent(ActionClientAgent.class.getName() + UUID.randomUUID(),
//                ActionClientAgent.class, getApplicationContext(), new Object[]{userToken, stationToken, actionType, value, redo});
    }

    @Override
    protected void onDestroy() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        bus.unregister(this);
        super.onDestroy();
    }
}

