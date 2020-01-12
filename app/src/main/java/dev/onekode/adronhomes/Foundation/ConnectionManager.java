package dev.onekode.adronhomes.Foundation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

public class ConnectionManager {
    private static final String TAG = "connection-tag";

    public ConnectionManager() {
        //
    }

    public static int getConnectionStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return 1;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return 2;
            }
        }
        return 0;
    }

    /**
     * Checks for possible Internet Connections
     * like WiFi, Roaming, WiFi Direct etc.
     */
    public static boolean isConnectionAvailable(@NonNull Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            @SuppressLint("MissingPermission") NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo netInfo : info) {
                    if (netInfo.getState() == NetworkInfo.State.CONNECTED) return true;
                }
            }
        }

        return false;
    }
}
