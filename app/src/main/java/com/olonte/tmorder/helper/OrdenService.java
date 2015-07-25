package com.olonte.tmorder.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.olonte.tmorder.R;
import com.olonte.tmorder.src.TMOrderApplication;
import com.olonte.tmorder.view.NotificacionOrdenActivity;

import java.util.Properties;

public class OrdenService extends Service {

    private TMOrderApplication tmorderApplication;
    private AssetsPropertyReader assetsPropertyReader;
    private Context context;
    private Properties properties;


    public OrdenService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public AssetsPropertyReader getAssetsPropertyReader() {
        return assetsPropertyReader;
    }

    public void setAssetsPropertyReader(AssetsPropertyReader assetsPropertyReader) { this.assetsPropertyReader = assetsPropertyReader;  }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        tmorderApplication = (TMOrderApplication) getApplication();
        setContext( this );
        setAssetsPropertyReader( new AssetsPropertyReader( getContext() ) );
        setProperties( getAssetsPropertyReader().getProperties( "SettingFile.properties" ) );

        if ( tmorderApplication.getIdSession() != null &&  tmorderApplication.getUsuario() != null ) {

            tmorderApplication.obtenerOrdenLista(tmorderApplication.getIdSession(), tmorderApplication.getUsuario().getIdUsuario());

            if (!tmorderApplication.getOrdenLista().equals(getProperties().getProperty("TAG_NO_ORD"))) {
                crearNotificacion();
            }
        }
        return Service.START_NOT_STICKY;
    }

    public void crearNotificacion () {

        NotificationManager notificationManager = (NotificationManager) getSystemService(getContext().NOTIFICATION_SERVICE);

        Notification notification;

        Intent notificacionOrdenIntent;
        PendingIntent pendingIntent;

        tmorderApplication.setIdNotificacion( getProperties().getProperty( "TAG_ID_NOTIFICACION"  ) );

        notificacionOrdenIntent = new Intent( OrdenService.this, NotificacionOrdenActivity.class );

        notificacionOrdenIntent.putExtra( getProperties().getProperty( "TAG_NOTIFICACION" ), getProperties().getProperty( "TAG_ID_NOTIFICACION"  ) );

        pendingIntent = PendingIntent.getActivity( OrdenService.this, 0, notificacionOrdenIntent, 0 );

        Notification.Builder builder;

        builder = new Notification.Builder( tmorderApplication.getApplicationContext() );

        builder.setContentIntent( pendingIntent );
        builder.setContentTitle( getProperties().getProperty( "TAG_TITLE_NOT" ) );
        builder.setContentText( getProperties().getProperty( "TAG_MSJ_NOT" ) );
        builder.setSmallIcon( R.drawable.notes );
        builder.setWhen( System.currentTimeMillis() );

        notification = builder.build();

        notificationManager.notify( Integer.parseInt( getProperties().getProperty( "TAG_ID_NOTIFICACION"  ) ) , notification );

    }

}
