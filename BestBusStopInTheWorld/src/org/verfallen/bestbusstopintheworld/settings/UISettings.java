package org.verfallen.bestbusstopintheworld.settings;

import org.verfallen.bestbusstopintheworld.R;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;

public class UISettings extends BaseSettings implements View.OnClickListener {

    private GoogleMap mMap;
    private UiSettings mUiSettings;

    public UISettings( Activity aActivity, GoogleMap aMap ) {
        super( aActivity );
        mMap = aMap;
        mUiSettings = aMap.getUiSettings();
        mActivity.findViewById(R.id.zoom_buttons_toggle).setOnClickListener(this);
        mActivity.findViewById(R.id.compass_toggle).setOnClickListener(this);
        mActivity.findViewById(R.id.mylocationbutton_toggle).setOnClickListener(this);
        mActivity.findViewById(R.id.mylocationlayer_toggle).setOnClickListener(this);
        mActivity.findViewById(R.id.scroll_toggle).setOnClickListener(this);
        mActivity.findViewById(R.id.zoom_gestures_toggle).setOnClickListener(this);
        mActivity.findViewById(R.id.tilt_toggle).setOnClickListener(this);
        mActivity.findViewById(R.id.rotate_toggle).setOnClickListener(this);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean canExecute() {
        return true;
    }
    
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.zoom_buttons_toggle:
            setZoomButtonsEnabled(v);
            break;
        case R.id.compass_toggle:
            setCompassEnabled(v);
            break;
        case R.id.mylocationbutton_toggle:
            setMyLocationButtonEnabled(v);
            break;
        case R.id.mylocationlayer_toggle:
            break;
        case R.id.scroll_toggle:
            setScrollGesturesEnabled(v);
            break;
        case R.id.zoom_gestures_toggle:
            setZoomGesturesEnabled(v);
            break;
        case R.id.tilt_toggle:
            setTiltGesturesEnabled(v);
            break;
        case R.id.rotate_toggle:
            setRotateGesturesEnabled(v);
            break;
        default:
            throw new IllegalStateException("undefined view id.");
        }
    }

    public void onResume() {
        if ( mMap != null ) {
            // Keep the UI Settings state in sync with the checkboxes.
            mUiSettings.setZoomControlsEnabled( isChecked( R.id.zoom_buttons_toggle ) );
            mUiSettings.setCompassEnabled( isChecked( R.id.compass_toggle ) );
            mUiSettings.setMyLocationButtonEnabled( isChecked( R.id.mylocationbutton_toggle ) );
            mMap.setMyLocationEnabled( isChecked( R.id.mylocationlayer_toggle ) );
            mUiSettings.setScrollGesturesEnabled( isChecked( R.id.scroll_toggle ) );
            mUiSettings.setZoomGesturesEnabled( isChecked( R.id.zoom_gestures_toggle ) );
            mUiSettings.setTiltGesturesEnabled( isChecked( R.id.tilt_toggle ) );
            mUiSettings.setRotateGesturesEnabled( isChecked( R.id.rotate_toggle ) );
        }
    }

    /**
     * Returns whether the checkbox with the given id is checked.
     */
    private boolean isChecked( int id ) {
        return ( (CheckBox) mActivity.findViewById( id ) ).isChecked();
    }

    /**
     * Checks if the map is ready (which depends on whether the Google Play services APK is available. This should be
     * called prior to calling any methods on GoogleMap.
     */
    private boolean checkReady() {
        if ( mMap == null ) {
            Toast.makeText( mActivity, R.string.map_not_ready, Toast.LENGTH_SHORT ).show();
            return false;
        }
        return true;
    }

    public void setZoomButtonsEnabled( View v ) {
        if ( !checkReady() ) {
            return;
        }
        // Enables/disables the zoom controls (+/- buttons in the bottom right of the map).
        mUiSettings.setZoomControlsEnabled( ( (CheckBox) v ).isChecked() );
    }

    public void setCompassEnabled( View v ) {
        if ( !checkReady() ) {
            return;
        }
        // Enables/disables the compass (icon in the top left that indicates the orientation of the
        // map).
        mUiSettings.setCompassEnabled( ( (CheckBox) v ).isChecked() );
    }

    public void setMyLocationButtonEnabled( View v ) {
        if ( !checkReady() ) {
            return;
        }
        // Enables/disables the my location button (this DOES NOT enable/disable the my location
        // dot/chevron on the map). The my location button will never appear if the my location
        // layer is not enabled.
        mUiSettings.setMyLocationButtonEnabled( ( (CheckBox) v ).isChecked() );
    }

    public void setMyLocationLayerEnabled( View v ) {
        if ( !checkReady() ) {
            return;
        }
        // Enables/disables the my location layer (i.e., the dot/chevron on the map). If enabled, it
        // will also cause the my location button to show (if it is enabled); if disabled, the my
        // location button will never show.
        mMap.setMyLocationEnabled( ( (CheckBox) v ).isChecked() );
    }

    public void setScrollGesturesEnabled( View v ) {
        if ( !checkReady() ) {
            return;
        }
        // Enables/disables scroll gestures (i.e. panning the map).
        mUiSettings.setScrollGesturesEnabled( ( (CheckBox) v ).isChecked() );
    }

    public void setZoomGesturesEnabled( View v ) {
        if ( !checkReady() ) {
            return;
        }
        // Enables/disables zoom gestures (i.e., double tap, pinch & stretch).
        mUiSettings.setZoomGesturesEnabled( ( (CheckBox) v ).isChecked() );
    }

    public void setTiltGesturesEnabled( View v ) {
        if ( !checkReady() ) {
            return;
        }
        // Enables/disables tilt gestures.
        mUiSettings.setTiltGesturesEnabled( ( (CheckBox) v ).isChecked() );
    }

    public void setRotateGesturesEnabled( View v ) {
        if ( !checkReady() ) {
            return;
        }
        // Enables/disables rotate gestures.
        mUiSettings.setRotateGesturesEnabled( ( (CheckBox) v ).isChecked() );
    }

}
