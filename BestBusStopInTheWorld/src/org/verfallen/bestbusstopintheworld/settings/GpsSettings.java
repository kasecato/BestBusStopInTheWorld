package org.verfallen.bestbusstopintheworld.settings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class GpsSettings extends BaseSettings {

    private static final int JELLY_BEAN_MR1 = 17;

    public GpsSettings( Activity aActivity ) {
        super( aActivity );
    }

    @Override
    public boolean isEnabled() {
        String provider = Settings.Secure.getString( mActivity.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED );
        return provider.contains( "gps" );
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    public boolean canNinja() {
        return ( ( Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO ) || ( JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT ) );
    }

    public boolean turnOn() {
        if ( !isEnabled() ) { // if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName( "com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider" );
            poke.addCategory( Intent.CATEGORY_ALTERNATIVE );
            poke.setData( Uri.parse( "3" ) );
            mActivity.sendBroadcast( poke );
            return true;
        }
        return false;
    }

    public boolean turnOff() {
        if ( isEnabled() ) { // if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName( "com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider" );
            poke.addCategory( Intent.CATEGORY_ALTERNATIVE );
            poke.setData( Uri.parse( "3" ) );
            mActivity.sendBroadcast( poke );
            return true;
        }
        return false;
    }

    public boolean showSettings() {
        Intent intent = new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS );
        mActivity.startActivity( intent );
        return true;
    }
}
