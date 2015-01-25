package org.verfallen.bestbusstopintheworld.settings;

import android.app.Activity;

public abstract class BaseSettings {

    public Activity mActivity = null;

    public abstract boolean canExecute();

    public abstract boolean isEnabled();

    public BaseSettings( Activity aActivity ) {
        this.mActivity = aActivity;
    }
}
