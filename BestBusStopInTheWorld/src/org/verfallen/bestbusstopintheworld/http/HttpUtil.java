package org.verfallen.bestbusstopintheworld.http;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.verfallen.bestbusstopintheworld.model.CommonConst;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtil {

    public static String doGet( String aUrl, ArrayList<NameValuePair> aParams ) {
        String response = null;
        HttpResponse res = null;
        HttpClient httpClient = new DefaultHttpClient();

        try {
            String query = URLEncodedUtils.format( aParams, CommonConst.HTTP.DEFAULT_ENCODING );
            HttpGet httpGet = new HttpGet( aUrl + query );
            res = httpClient.execute( httpGet );
            HttpEntity entity = res.getEntity();
            response = EntityUtils.toString( entity );
            entity.consumeContent();
            httpClient.getConnectionManager().shutdown();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Checking for all possible internet providers
     * **/
    public static boolean isConnectingToInternet( Activity aActivity ) {
        ConnectivityManager connectivity = (ConnectivityManager) aActivity
                .getSystemService( Context.CONNECTIVITY_SERVICE );
        if ( connectivity != null )
            for ( NetworkInfo info : connectivity.getAllNetworkInfo() )
                if ( info.getState() == NetworkInfo.State.CONNECTED )
                    return true;
        return false;
    }
}
