package com.olonte.tmorder.view;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.olonte.tmorder.R;
import com.olonte.tmorder.src.TMOrderApplication;

public class NotificacionOrdenActivity extends Activity {

    private TMOrderApplication tmorderApplication;
    private Context context;
    private TextView textViewNotificacion;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_orden);
        tmorderApplication = (TMOrderApplication) getApplication();
        setContext(  tmorderApplication.getApplicationContext() );
        textViewNotificacion = (TextView)findViewById(R.id.txtVnotf);
        textViewNotificacion.setText( tmorderApplication.getOrdenLista() );
        notificationManager = (NotificationManager) getContext().getSystemService( NOTIFICATION_SERVICE );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notificacion_orden, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Context getContext() { return context;  }

    public void setContext(Context context) { this.context = context;  }

    public void onclickNotificacion(View v) {
        notificationManager.cancel( Integer.parseInt( tmorderApplication.getIdNotificacion() ) );
        finish();
    }
}
