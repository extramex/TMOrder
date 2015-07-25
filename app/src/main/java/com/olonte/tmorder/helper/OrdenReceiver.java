package com.olonte.tmorder.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OrdenReceiver extends BroadcastReceiver {
    public OrdenReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setClass( context, OrdenService.class );

        context.startService( intent );

    }
}
