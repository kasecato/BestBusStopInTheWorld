package org.verfallen.bestbusstopintheworld.settings;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

public class WirelessNetworkSettings extends BaseSettings {

    public WirelessNetworkSettings( Activity aActivity ) {
        super( aActivity );
    }

    @Override
    public boolean isEnabled() {
        ContentResolver cr = mActivity.getContentResolver();
        boolean wifiEnabled = Settings.Secure.isLocationProviderEnabled( cr, LocationManager.NETWORK_PROVIDER );
        return wifiEnabled;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    public boolean showSettings() {
        Intent intent = new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS );
        mActivity.startActivity( intent );
        return true;
    }

}
