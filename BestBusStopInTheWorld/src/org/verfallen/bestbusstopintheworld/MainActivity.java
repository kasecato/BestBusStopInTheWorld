package org.verfallen.bestbusstopintheworld;

import org.verfallen.bestbusstopintheworld.location.LoactionHelper;
import org.verfallen.bestbusstopintheworld.map.MapHelper;
import org.verfallen.bestbusstopintheworld.settings.UISettings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    // Global variable
    LoactionHelper mLocationHelper = null;
    MapHelper mMapHelper = null;
    UISettings mUISettings = null;

    /*
     * Called by Location Services when the request to connect the client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mLocationHelper = new LoactionHelper( this );
        mMapHelper = new MapHelper( this );
        mMapHelper.setUpMapIfNeeded();
        mUISettings = new UISettings( this, mMapHelper.getGoogleMap() );

        mLocationHelper.onCreate( savedInstanceState );
        mMapHelper.onCreate( savedInstanceState );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapHelper.onResume();
        mLocationHelper.onResume();
        mUISettings.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapHelper.onPause();
        mLocationHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapHelper.onDestroy();
        mLocationHelper.onDestroy();
    }

    /************************************
     * Settings
     ************************************/
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        boolean ret = true;
        switch ( item.getItemId() ) {
        case R.id.action_settings:
            Intent intent = new Intent();
            intent.setClass( this, SettingsActivity.class );
            startActivity( intent );
            break;
        case R.id.action_ui_settings:
            ScrollView uiSettings = (ScrollView) findViewById( R.id.ui_settings );
            if ( uiSettings.getVisibility() == ScrollView.VISIBLE ) {
                uiSettings.setVisibility( ScrollView.INVISIBLE );
            } else {
                uiSettings.setVisibility( ScrollView.VISIBLE );
            }
            break;
        case R.id.action_debug:
            TextView debugInfo = (TextView) findViewById( R.id.message_text );
            if ( debugInfo.getVisibility() == TextView.VISIBLE ) {
                debugInfo.setVisibility( TextView.GONE );
            } else {
                debugInfo.setVisibility( TextView.VISIBLE );
            }
            break;
        default:
            ret = super.onOptionsItemSelected( item );
            break;
        }
        return ret;
    }
}
