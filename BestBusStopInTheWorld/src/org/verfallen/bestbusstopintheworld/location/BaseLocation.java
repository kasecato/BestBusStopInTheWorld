package org.verfallen.bestbusstopintheworld.location;

import org.verfallen.bestbusstopintheworld.BaseActivity;

import android.support.v4.app.FragmentActivity;

public abstract class BaseLocation extends BaseActivity {

    public BaseLocation( FragmentActivity aActivity ) {
        super.mActivity = aActivity;
    }
}
