package com.olonte.tmorder.modelo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jsgravenhorst on 3/8/14.
 */
public class ConnectionDetector {

    private Context context;

	public ConnectionDetector(Context context){
		setContext( context );
	}

    public boolean isConnectingToInternet() {

        ConnectivityManager connectivityManager;

        NetworkInfo networkInfo;

        connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connectivityManager.getNetworkInfo(0);

        if (networkInfo != null && networkInfo.getState()==NetworkInfo.State.CONNECTED) {

            return true;

        }else {

            networkInfo = connectivityManager.getNetworkInfo(1);

            if(networkInfo!=null && networkInfo.getState()==NetworkInfo.State.CONNECTED)
                return  true;

        }

        return false;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
