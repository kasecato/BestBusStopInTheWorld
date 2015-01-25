package org.verfallen.bestbusstopintheworld.location;

import org.verfallen.bestbusstopintheworld.R;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class LoactionHelper extends BaseLocation implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

    // Global constants
    /*
     * Define a request code to send to Google Play services This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    // Global variable to hold the current location
    private LocationClient mLocationClient = null;
    private TextView mMessageView;

    public LoactionHelper( FragmentActivity aActivity ) {
        super( aActivity );
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        mMessageView = (TextView) mActivity.findViewById( R.id.message_text );
    }

    @Override
    public void onResume() {
        setUpLocationClientIfNeeded();
        connect();
    }

    @Override
    public void onPause() {
        if ( mLocationClient != null ) {
            stopPolling();
            disconnect();
        }
    }

    @Override
    public void onDestroy() {
    }

    public void setUpLocationClientIfNeeded() {
        if ( mLocationClient == null ) {
            mLocationClient = new LocationClient( mActivity, this, this );
        }
    }

    /**
     * Implementation of {@link LocationListener}.
     */
    @Override
    public void onLocationChanged( Location location ) {
        mMessageView.setText( "Location = " + location );

        Intent broadcastIntent = new Intent();
        broadcastIntent.putExtra( mActivity.getString( R.string.latitude ), location.getLatitude() );
        broadcastIntent.putExtra( mActivity.getString( R.string.longitude ), location.getLongitude() );
        broadcastIntent.setAction( mActivity.getString( R.string.broadcast_location_changed ) );
        mActivity.getBaseContext().sendBroadcast( broadcastIntent );
        
        stopPolling();
    }

    /**
     * Callback called when connected to GCore. Implementation of {@link ConnectionCallbacks}.
     */
    @Override
    public void onConnected( Bundle connectionHint ) {
        // Display the connection status
        Toast.makeText( mActivity, "Connected", Toast.LENGTH_SHORT ).show();
        startPolling( 60 );
    }

    /**
     * Callback called when disconnected from GCore. Implementation of {@link ConnectionCallbacks}.
     */
    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText( mActivity, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT ).show();
    }

    /*
     * Implementation of {@link OnConnectionFailedListener}. Called by Location Services if the attempt to Location
     * Services fails.
     */
    @Override
    public void onConnectionFailed( ConnectionResult connectionResult ) {
        /*
         * Google Play services can resolve some errors it detects. If the error has a resolution, try sending an Intent
         * to start a Google Play services activity that can resolve error.
         */
        if ( connectionResult.hasResolution() ) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult( mActivity, CONNECTION_FAILURE_RESOLUTION_REQUEST );
                /*
                 * Thrown if Google Play services canceled the original PendingIntent
                 */
            } catch ( IntentSender.SendIntentException e ) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the user with the error.
             */
        }
    }

    /*
     * Called when the Activity becomes visible.
     */
    private void connect() {
        // Connect the client.
        if ( mLocationClient != null ) {
            boolean hasConnection = mLocationClient.isConnected() || mLocationClient.isConnecting();
            if ( !hasConnection ) {
                mLocationClient.connect();
            }
        }
    }

    /*
     * Called when the Activity is no longer visible.
     */
    private void disconnect() {
        // Disconnecting the client invalidates it.
        if ( mLocationClient != null ) {
            boolean hasConnection = mLocationClient.isConnected() || mLocationClient.isConnecting();
            if ( hasConnection ) {
                mLocationClient.disconnect();
            }
        }
    }

    // These settings are the same as the settings for the map. They will in fact give you updates at
    // the maximal rates currently possible.
    private static LocationRequest REQUEST = LocationRequest.create().setFastestInterval( 16 ) // 16ms = 60fps
            .setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );

    private void startPolling( int aIntervalSec ) {
        if ( mLocationClient != null ) {
            REQUEST.setInterval( aIntervalSec * 1000 );
            mLocationClient.requestLocationUpdates( REQUEST, this ); // this means LocationListener
        }
    }

    private void stopPolling() {
        if ( mLocationClient != null ) {
            mLocationClient.removeLocationUpdates( this );
        }
    }

}
