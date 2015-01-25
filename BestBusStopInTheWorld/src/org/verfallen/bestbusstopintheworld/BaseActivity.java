package org.verfallen.bestbusstopintheworld;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseActivity {

    public FragmentActivity mActivity;

    public abstract void onCreate( Bundle savedInstanceState );

    public abstract void onResume();

    public abstract void onPause();

    public abstract void onDestroy();

}
