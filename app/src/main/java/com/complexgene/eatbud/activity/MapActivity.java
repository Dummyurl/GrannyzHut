package com.complexgene.eatbud.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.complexgene.eatbud.R;
import com.complexgene.eatbud.model.LocationResponse;
import com.complexgene.eatbud.network.ApiClient;
import com.complexgene.eatbud.network.ApiInterface;
import com.complexgene.eatbud.util.AppConstant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by satyabrata on 12/12/17.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS_GPS = 2;
    private static final int REQUEST_LOCATION = 3;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker marker;
    private boolean isMapLoaded = false;

    private String intentAddress,tempAddress;
    private Double intentLat,intentLon,tempLat,tempLon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        getIntent().getDoubleExtra("",0.0d);

        intentAddress=getIntent().getStringExtra("intentAddress");
        intentLat = getIntent().getDoubleExtra("intentLat",0.0);
        intentLon = getIntent().getDoubleExtra("intentLon",0.0);

        System.out.println("compare:"+intentLat);
        System.out.println("compare:"+intentLon);

//        if (Double.compare(intentLat,0.0) ==0 || Double.compare(intentLon,0.0)==0) {
//            buildGoogleApiClient();
//        }

//        if (intentLat == null || intentLon == null) {
//            buildGoogleApiClient();
//        }
    }

    private void openPlaceAutocompleteSearch() {

        try {
//            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(MapActivity.this);

            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build();

            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
//                    .setFilter(typeFilter)
                    .build(this);

            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);


        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
            // dialogView.showSingleButtonDialog(getResources().getString(R.string.app_name), e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            // dialogView.showSingleButtonDialog(getResources().getString(R.string.app_name), e.getMessage());
        }

    }


    private void putMyLocationOnMap(Location location){
        if(isMapLoaded && mLastLocation!=null){
            addCameraToMap(new LatLng(location.getLatitude(),location.getLongitude()),
                    "Current Location");

            getAddressFromServer(new LatLng(intentLat, intentLon));
            stopLocationRequest();
        }
    }


    private void addCameraToMap(LatLng latLng, String name) {
        mMap.clear();

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(AppConstant.ZOOM_LEVEL)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

       mMap.addMarker(new MarkerOptions().position(latLng).title(name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_map)));

        intentLat=latLng.latitude;
        intentLon=latLng.longitude;
        intentAddress=name;


//       intentLat=String.valueOf(latLng.latitude);
//       intentLon=String.valueOf(latLng.longitude);


//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, AppConstant.ZOOM_LEVEL));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));

    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private void initLocationRequest() {
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {

            if (mGoogleApiClient != null) {
                if (mGoogleApiClient.isConnected()) {

                    LocationRequest locationRequest = LocationRequest.create();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(1000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    locationRequest.setSmallestDisplacement(5);

                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);


                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                            ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

//                                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                                        System.out.println("mLastLocation:" + mLastLocation);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    try {
                                        status.startResolutionForResult(MapActivity.this, REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        System.out.println("Exception:" + e);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    private void stopLocationRequest() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(this);
        mMap.setBuildingsEnabled(true);

//        System.out.println("onMapReady:"+Double.compare(intentLat,0.0));
//        System.out.println("onMapReady:"+Double.compare(intentLon,0.0));

        if (Double.compare(intentLat,0.0) !=0 || Double.compare(intentLon,0.0)!=0) {
            addCameraToMap(new LatLng(intentLat, intentLon), intentAddress);
        }else {
            buildGoogleApiClient();
        }
    }


    @Override
    public void onMapLoaded() {
        System.out.println("@@@@@@@@@@ onMapLoaded");
        mMap.setOnMapClickListener(this);
        isMapLoaded=true;
//        if (Double.compare(intentLat,0.0) !=0 || Double.compare(intentLon,0.0)!=0) {
//            addCameraToMap(new LatLng(intentLat, intentLon), intentAddress);
//        }else {
//            putMyLocationOnMap(mLastLocation);
//        }


    }

    @Override
    public void onMapClick(LatLng latLng) {
        System.out.println("@@@@@@@@@@@@@@@@@@ onMapClick");
        getAddressFromServer(latLng);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        System.out.println("@@@@@@@@@@@@@@@ onConnected");
        initLocationRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("@@@@@@@@@@ location:"+location);

//        addCameraToMap(new LatLng(location.getLatitude(),location.getLongitude()),"Current Location");

        getAddressFromServer(new LatLng(location.getLatitude(), location.getLongitude()));

        stopLocationRequest();



//        mLastLocation=location;
//        if(isMapLoaded){
//            addCameraToMap(new LatLng(location.getLatitude(),location.getLongitude()),"Current Location");
//        }

//        putMyLocationOnMap(location);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_location, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:

                setResult(Activity.RESULT_OK,new Intent()
                .putExtra("intentLat",intentLat)
                .putExtra("intentLon",intentLon)
                .putExtra("intentAddress",intentAddress));

                finish();
                return true;
            case R.id.menu_delete:

                setResult(Activity.RESULT_OK,new Intent()
                        .putExtra("intentLat",0.0)
                        .putExtra("intentLon",0.0)
                        .putExtra("intentAddress",""));

                finish();
                return true;
            case R.id.menu_search:
                openPlaceAutocompleteSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLocationRequest();
                } else {
                    System.out.println("permission denied!");
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng latLng = place.getLatLng();
                double lat = latLng.latitude;
                double lng = latLng.longitude;


                System.out.println("lat:" + lat);
                System.out.println("lng:" + lng);
                System.out.println("getAddress:" + place.getAddress().toString());
                System.out.println("getName:" + place.getName().toString());


                addCameraToMap(new LatLng(lat, lng), place.getName().toString());


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                System.out.println("getStatusMessage:" + status.getStatusCode());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onBackPressed() {

        setResult(Activity.RESULT_CANCELED);

        super.onBackPressed();
    }



    private void getAddressFromServer(final LatLng latLng) {
//        CustomLoader.getInstance(this).show();
        String GET_ADDRESS="https://maps.googleapis.com/maps/api/geocode/json?latlng=latitude,longitude&key=apiKey";

        ApiClient.getClient(this).create(ApiInterface.class).getAddress(GET_ADDRESS.
                replace("latitude",String.valueOf(latLng.latitude))
                .replace("longitude",String.valueOf(latLng.longitude))
                .replace("apiKey",getString(R.string.map_api_key))).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        CustomLoader.dismissLoader();
                        try {
                            String responseBody=response.body().string();
                            System.out.println("response:"+responseBody);
                            LocationResponse result = new Gson().fromJson(responseBody, LocationResponse.class);

//                            String address="";
//                            for(int i=0;i<result.getResults().get(0).getAddressComponents().size();i++){
//                                address=address+result.getResults().get(0).getAddressComponents().get(i).getShortName()+",";
//                            }

                            System.out.println("getFormattedAddress:" + result.getResults().get(0).getFormattedAddress());
                            if (result.getStatus().equals("OK")) {

                                addCameraToMap(latLng, result.getResults().get(0).getFormattedAddress());
                            }
                        } catch (Exception e) {
                            System.out.println("removeFromFav Exception:" + e);
                        }

                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        CustomLoader.dismissLoader();
                    }
                });
    }

}
