package com.olonte.tmorder.view;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.olonte.tmorder.R;
import com.olonte.tmorder.helper.OrdenReceiver;
import com.olonte.tmorder.src.TMOrderApplication;

import java.util.Calendar;


public class AsignacionActivity extends Activity {

    private TMOrderApplication tmorderApplication;
    private Context context;
    private LinearLayout linearlayout;
    private ScrollView scrollView;
    private GridLayout gridLayout;

    private static final int ONE_MINUTE  = 1 * 60 * 1000;
    private static final String TAG_ESPACIO = " ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion);
        tmorderApplication = (TMOrderApplication) getApplication();
        setContext(this);
        TextView textView = (TextView) findViewById( R.id.txVtl );
        textView.setTextColor( Color.parseColor( getString( R.string.tag_color ) ) );
        textView.setText( getString( R.string.tag_bienv ) + TAG_ESPACIO + tmorderApplication.getUsuario().getNombre() + TAG_ESPACIO + tmorderApplication.getUsuario().getApellido());

        cargarMesas();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iniciarServicio();
    }

    public void iniciarServicio(){

        Intent intent;

        PendingIntent pendingIntent;

        AlarmManager alarmManager;

        Calendar calendar;

        intent = new Intent( AsignacionActivity.this, OrdenReceiver.class );

        pendingIntent = PendingIntent.getBroadcast( AsignacionActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        calendar = Calendar.getInstance();

        calendar.add(Calendar.SECOND, 60);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), ONE_MINUTE, pendingIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.asignacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.accion_volver:
                onBackPressed();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent logueoActivity = new Intent(getApplicationContext(), LogueoActivity.class);
        startActivity(logueoActivity);
    }

   public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void cargarMesas(){

        linearlayout = new LinearLayout( getApplicationContext() );
        linearlayout = (LinearLayout)findViewById(R.id.lyMesa);

        scrollView   = new ScrollView( getApplicationContext() );
        scrollView.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );

        gridLayout = new GridLayout( getApplicationContext() );

        gridLayout.setColumnCount( Integer.parseInt(getString( R.string.tag_column_count ) ) );
        gridLayout.setMinimumWidth( Integer.parseInt( getString( R.string.tag_min_column_width ) ) );
        gridLayout.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );

        scrollView.addView( gridLayout );

        for( int i = 0; i <  tmorderApplication.getArrayListAsignaciones().size(); i++ ) {

            Button button;
            button = new Button( getApplicationContext() );
            button.setId( Integer.parseInt(  tmorderApplication.getArrayListAsignaciones().get( i ).getIdAsignacion() ) );
            button.setWidth( Integer.parseInt( getString(R.string.tag_width_btn ) ) );

            if(   tmorderApplication.getArrayListAsignaciones().get( i ).isEstado() ){ /* Mesa Ocupada */
                button.setText( getString( R.string.tag_mesa ) + TAG_ESPACIO +  tmorderApplication.getArrayListAsignaciones().get( i ).getIdMesa() + TAG_ESPACIO + getString( R.string.tag_mesa_ocupada ) );
                button.getBackground().setColorFilter(Color.MAGENTA, PorterDuff.Mode.MULTIPLY);
            }else { /* Mesa Libre*/
                button.setText( getString( R.string.tag_mesa ) + TAG_ESPACIO +  tmorderApplication.getArrayListAsignaciones().get( i ).getIdMesa() + TAG_ESPACIO +  getString( R.string.tag_mesa_libre ) );
                button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button buttonOnClick = (Button)view;
                    tmorderApplication.setIdAsignacionActual( Integer.toString( buttonOnClick.getId() ) );
                    tmorderApplication.setIdMesaActual( getIdMesa( Integer.toString( buttonOnClick.getId() ) ) );
                    if(tmorderApplication.getDbHandler().isConexion()) {
                        Intent categoriaActivity = new Intent(AsignacionActivity.this, CategoriaActivity.class);
                        startActivity(categoriaActivity);
                    }
                }

                public String getIdMesa( String idAsignacion ){
                    String idMesa;
                    idMesa = "";
                    for( int i = 0; i <  tmorderApplication.getArrayListAsignaciones().size(); i++ ) {
                        if ( tmorderApplication.getArrayListAsignaciones().get(i).getIdAsignacion().equals( idAsignacion ) ) {
                            idMesa = tmorderApplication.getArrayListAsignaciones().get(i).getIdMesa();
                        }

                    }
                    return idMesa;
                }

            });

            gridLayout.addView(button);

        } // end  For

        linearlayout.addView(scrollView);
    }
}
