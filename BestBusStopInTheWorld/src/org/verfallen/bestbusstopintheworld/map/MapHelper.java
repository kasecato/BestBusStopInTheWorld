package org.verfallen.bestbusstopintheworld.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.verfallen.bestbusstopintheworld.R;
import org.verfallen.bestbusstopintheworld.googleplaces.GooglePlacesUtil;
import org.verfallen.bestbusstopintheworld.model.GooglePlacesJSON;
import org.verfallen.bestbusstopintheworld.util.CommonUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapHelper extends BaseMap implements OnInfoWindowClickListener {

    private BroadcastReceiver mBroadcastRcvOnLocationChanged;
    private List<Marker> mMarkerList;

    /** List of hues to use for the marker */
    private static final float[] MARKER_HUES = new float[] { BitmapDescriptorFactory.HUE_RED,
            BitmapDescriptorFactory.HUE_ORANGE, BitmapDescriptorFactory.HUE_YELLOW, BitmapDescriptorFactory.HUE_GREEN,
            BitmapDescriptorFactory.HUE_CYAN, BitmapDescriptorFactory.HUE_AZURE, BitmapDescriptorFactory.HUE_BLUE,
            BitmapDescriptorFactory.HUE_VIOLET, BitmapDescriptorFactory.HUE_MAGENTA, BitmapDescriptorFactory.HUE_ROSE, };

    public MapHelper( FragmentActivity aActivity ) {
        super( aActivity );
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        RegistBroadcasetReceiverOnLocationChanged();
    }

    @Override
    public void onResume() {
        setUpMapIfNeeded();
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
        UnregistBroadcasetReceiverOnLocationChanged();
    }

    private void RegistBroadcasetReceiverOnLocationChanged() {
        IntentFilter filter = new IntentFilter( mActivity.getString( R.string.broadcast_location_changed ) );
        mBroadcastRcvOnLocationChanged = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent ) {
                double latitude = intent.getExtras().getDouble( mActivity.getString( R.string.latitude ) );
                double longitude = intent.getExtras().getDouble( mActivity.getString( R.string.longitude ) );

                String lang = mActivity.getResources().getConfiguration().locale.getLanguage();
                lang = "ja";
                new DownloadGooglePlacesTask().execute( String.valueOf( latitude ), String.valueOf( longitude ),
                        GooglePlacesUtil.Types.BUS_STATION, lang );

                Location myLocation = new Location( "My Location" );
                myLocation.setLatitude( latitude );
                myLocation.setLongitude( longitude );

                moveCameraToMyLocation( myLocation, true );
            }
        };
        mActivity.registerReceiver( mBroadcastRcvOnLocationChanged, filter );
    }

    private class DownloadGooglePlacesTask extends AsyncTask<String, Integer, List<GooglePlacesJSON>> {

        @Override
        protected List<GooglePlacesJSON> doInBackground( String... aStrings ) {
            double latitude = Double.parseDouble( aStrings[0] );
            double longitude = Double.parseDouble( aStrings[1] );
            String types = aStrings[2];
            String language = aStrings[3];
            return GooglePlacesUtil.getGooglePlaces( latitude, longitude, types, language );
        }

        protected void onProgressUpdate( Integer... progress ) {
        }

        protected void onPostExecute( List<GooglePlacesJSON> busStationList ) {
            addMarkersToMap( busStationList );
        }

    }

    private void UnregistBroadcasetReceiverOnLocationChanged() {
        mActivity.unregisterReceiver( mBroadcastRcvOnLocationChanged );
    }

    private void initialize() {
        // 現在位置表示の有効化
        mMap.setMyLocationEnabled( true );
        // 設定の取得
        UiSettings settings = mMap.getUiSettings();
        // コンパスの有効化
        settings.setCompassEnabled( true );
        // 現在位置に移動するボタンの有効化
        settings.setMyLocationButtonEnabled( true );
        // ズームイン・アウトボタンの有効化
        settings.setZoomControlsEnabled( true );
        // すべてのジェスチャーの有効化
        settings.setAllGesturesEnabled( true );
        // 回転ジェスチャーの有効化
        settings.setRotateGesturesEnabled( true );
        // スクロールジェスチャーの有効化
        settings.setScrollGesturesEnabled( true );
        // Tlitジェスチャー(立体表示)の有効化
        settings.setTiltGesturesEnabled( false );
        // ズームジェスチャー(ピンチイン・アウト)の有効化
        settings.setZoomGesturesEnabled( true );
    }

    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if ( mMap == null ) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ( (SupportMapFragment) mActivity.getSupportFragmentManager().findFragmentById( R.id.map ) ).getMap();
            // Check if we were successful in obtaining the map.
            if ( mMap != null ) {
                initialize();
                mMap.setMyLocationEnabled( true );

                // Set listeners for marker events. See the bottom of this class for their behavior.
                mMap.setOnInfoWindowClickListener( this );
            }
        }
    }

    public void moveCameraToMyLocation( Location aCurrentLocation, boolean aIsAnimation ) {
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition( new CameraPosition.Builder()
                .target( new LatLng( aCurrentLocation.getLatitude(), aCurrentLocation.getLongitude() ) ).zoom( 14.5f )
                .build() );
        if ( aIsAnimation ) {
            mMap.animateCamera( camera );
        } else {
            mMap.moveCamera( camera );
        }
    }

    private void addMarkersToMap( List<GooglePlacesJSON> aGooglePlaces ) {
        mMap.clear();
        mMarkerList = new ArrayList<Marker>();
        int rank = 1;
        for ( GooglePlacesJSON googlePlaces : aGooglePlaces ) {
            // Uses a colored icon.
            float newHue = MARKER_HUES[new Random().nextInt( MARKER_HUES.length )];
            LatLng latLng = new LatLng( googlePlaces.mLocationLat, googlePlaces.mLocationLng );
            Marker marker = mMap
                    .addMarker( new MarkerOptions()
                            .position( latLng )
                            .title( String.format( "%s", googlePlaces.mName ) )
                            .snippet(
                                    String.format( "#%s - %s", rank,
                                            CommonUtil.conversionDistanse( googlePlaces.mDistance ) ) )
                            .icon( BitmapDescriptorFactory.defaultMarker( newHue ) ) );
            if ( rank == 1 ) {
                marker.showInfoWindow();
            }
            mMarkerList.add( marker );
            rank++;
        }
    }

    //
    // Marker related listeners.
    //
    @Override
    public void onInfoWindowClick( Marker aMarker ) {
        String title = aMarker.getTitle();
        Toast.makeText( mActivity.getBaseContext(), title, Toast.LENGTH_SHORT ).show();
    }

}
