package org.verfallen.bestbusstopintheworld;

import org.verfallen.bestbusstopintheworld.settings.GpsSettings;
import org.verfallen.bestbusstopintheworld.settings.WirelessNetworkSettings;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener {

    private GpsSettings mGpsSettings;
    private WirelessNetworkSettings mWirelessNetworkSettings;

    @Override
    protected void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );

        mGpsSettings = new GpsSettings( this );
        mWirelessNetworkSettings = new WirelessNetworkSettings( this );

        setupSimplePreferencesScreen();
    }

    private void setupSimplePreferencesScreen() {
        // In the simplified UI, fragments are not used at all and we instead
        // use the older PreferenceActivity APIs.

        // Add 'my_location' preferences.
        addPreferencesFromResource( R.xml.pref_my_location );

        // Wireless Networks
        CheckBoxPreference cbxWiressNetworks = (CheckBoxPreference) findPreference( getString( R.string.key_checkbox_wireless_networks ) );
        cbxWiressNetworks.setOnPreferenceChangeListener( this );
        cbxWiressNetworks.setChecked( mWirelessNetworkSettings.isEnabled() );

        // GPS
        CheckBoxPreference cbxGps = (CheckBoxPreference) findPreference( getString( R.string.key_checkbox_gps ) );
        cbxGps.setOnPreferenceChangeListener( this );
        cbxGps.setChecked( mGpsSettings.isEnabled() );
    }

    @Override
    public boolean onPreferenceChange( Preference aPreference, Object aObj ) {
        String key = aPreference.getKey();
        // Wireless Networks CheckBox
        if ( key.equals( getString( R.string.key_checkbox_wireless_networks ) ) ) {
            mWirelessNetworkSettings.showSettings();
            return false;
        }
        // GPS CheckBox
        if ( key.equals( getString( R.string.key_checkbox_gps ) ) ) {
            CheckBoxPreference cbxPref = (CheckBoxPreference) aPreference;
            boolean isCheked = ( (Boolean) aObj ).booleanValue();
            if ( mGpsSettings.canNinja() ) {
                if ( isCheked ) {
                    mGpsSettings.turnOn();
                } else {
                    mGpsSettings.turnOff();
                }
                cbxPref.setChecked( isCheked );
                return isCheked;
            } else {
                mGpsSettings.showSettings();
                return false;
            }
        }
        return false;
    }

    @Override
    public void onRestart() {
        super.onRestart();

        // GPS
        CheckBoxPreference cbxGps = (CheckBoxPreference) findPreference( getString( R.string.key_checkbox_gps ) );
        cbxGps.setChecked( mGpsSettings.isEnabled() );

        // Wireless Networks
        CheckBoxPreference cbxWiressNetworks = (CheckBoxPreference) findPreference( getString( R.string.key_checkbox_wireless_networks ) );
        cbxWiressNetworks.setChecked( mWirelessNetworkSettings.isEnabled() );
    }

}
