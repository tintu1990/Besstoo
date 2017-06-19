package com.rplanx.besstoo.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by soumya on 21/4/17.
 */
public class CheckInetConnection {
    public static boolean checkConn(Context ctx)
    {
        ConnectivityManager conMgr =  (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || conMgr .getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING  )
            return true;
        else if ( conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED || conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED   )
            return false;

        return false;

    }
    public static boolean checkAllCon(Context ctx)
    {

        ConnectivityManager mConnectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mTelephony =  (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        // Skip if no connection, or background data disabled
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info == null ||!mConnectivity.getBackgroundDataSetting())
        {
            return false;
        }

        // Only update if WiFi or 3G is connected and not roaming
        int netType = info.getType();
        int netSubtype = info.getSubtype();

        if (netType == ConnectivityManager.TYPE_WIFI || netType == ConnectivityManager.TYPE_MOBILE)
        {
            return info.isConnected();
        } else if (netType == ConnectivityManager.TYPE_MOBILE && netSubtype == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming())
        {
            return info.isConnected();
        } else
        {
            return false;
        }
    }
}
