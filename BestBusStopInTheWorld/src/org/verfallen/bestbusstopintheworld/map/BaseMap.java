package org.verfallen.bestbusstopintheworld.map;

import org.verfallen.bestbusstopintheworld.BaseActivity;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;

public abstract class BaseMap extends BaseActivity {
    public GoogleMap mMap;

    public BaseMap( FragmentActivity aActivity ) {
        super.mActivity = aActivity;
    }

    public GoogleMap getGoogleMap() {
        return mMap;
    }
}
