package com.olonte.tmorder.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by jsgravenhorst on 3/8/14.
 */
public class AlertDialogManager {

    public void showAlertDialog(final Context context, String title, String message){

        AlertDialog.Builder alertDailogBuilder;
        alertDailogBuilder = new AlertDialog.Builder( context );
        alertDailogBuilder.setTitle( title );
        alertDailogBuilder.setMessage( message );
        alertDailogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                     }
                }
        );

        alertDailogBuilder.create().show(); // show the Dialog

    }

}
