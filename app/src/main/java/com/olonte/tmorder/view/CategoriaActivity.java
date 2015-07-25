package com.olonte.tmorder.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.olonte.tmorder.R;
import com.olonte.tmorder.helper.AlertDialogManager;
import com.olonte.tmorder.src.TMOrderApplication;

public class CategoriaActivity extends Activity {
    private TMOrderApplication tmorderApplication;
    private AlertDialogManager alertDialogManager;
    private Context context;
    private LinearLayout linearlayout;
    private ScrollView scrollView;
    private LinearLayout _linearLayout;
    private TextView textView;
    private static final String TAG_ESPACIO =" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        tmorderApplication = (TMOrderApplication) getApplication();
        setContext(this);
        setAlertDialogManager( new AlertDialogManager() );
        textView = (TextView) findViewById(R.id.txvCtg);
        textView.setTextColor( Color.parseColor(getString( R.string.tag_color ) ) );
        textView.setText( getString( R.string.tag_sel_ped_ms ) + TAG_ESPACIO + tmorderApplication.getIdMesaActual() );

        cargarCategorias();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.categoria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

            switch (item.getItemId()) {
                case R.id.accion_facturar:
                    if ( tmorderApplication.validaOrden( CategoriaActivity.this, tmorderApplication.obtenerIdVtaActual( tmorderApplication.getIdAsignacionActual() ) ) ) {
                       facturarPedido();
                   }
                    break;
                case R.id.accion_confirmar:
                    if ( tmorderApplication.validaOrden( CategoriaActivity.this, tmorderApplication.obtenerIdVtaActual( tmorderApplication.getIdAsignacionActual() ) ) ) {
                        enviarPedido();
                    }
                    break;
                case R.id.accion_modificar:
                    if ( tmorderApplication.validaOrden( CategoriaActivity.this, tmorderApplication.obtenerIdVtaActual( tmorderApplication.getIdAsignacionActual() ) ) ) {
                        modificarPedido();
                    }
                    break;
                /*case R.id.accion_cancelar:
                    //  cancelarPedido();
                    break;*/
                case R.id.accion_volver:
                    onBackPressed();
                    break;
            }


        return false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent asignacionActivity;
        asignacionActivity = new Intent( CategoriaActivity.this, AsignacionActivity.class);
        startActivity( asignacionActivity);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public AlertDialogManager getAlertDialogManager() {
        return alertDialogManager;
    }

    public void setAlertDialogManager(AlertDialogManager alertDialogManager) { this.alertDialogManager = alertDialogManager;  }

    public void cargarCategorias(){

        linearlayout = new LinearLayout( getApplicationContext() );

        linearlayout = (LinearLayout) findViewById( R.id.lyCategoria );

        scrollView = new ScrollView( getApplicationContext() );
        scrollView.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );

        _linearLayout = new LinearLayout( getApplicationContext() );
        _linearLayout.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
        _linearLayout.setOrientation( LinearLayout.VERTICAL );

        scrollView.addView( _linearLayout );

        for( int i = 0; i < tmorderApplication.getArrayListCategorias().size(); i++ ) {

            Button button;

            button = new Button( getApplicationContext() );
            button.setId( Integer.parseInt( tmorderApplication.getArrayListCategorias().get( i ).getIdCategoria() ) );
            button.setText( tmorderApplication.getArrayListCategorias().get( i ).getDescripcion() );
            button.setWidth( Integer.parseInt(  getString(R.string.tag_width_btn ) ) );
            button.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button buttonOnClick = (Button)view;
                    tmorderApplication.setIdCategoriaActual( Integer.toString( buttonOnClick.getId() ) );
                    if(  tmorderApplication.getDbHandler().isConexion()) {
                        Intent subCategoriaActivity = new Intent(CategoriaActivity.this, SubCategoriaActivity.class);
                        startActivity(subCategoriaActivity);
                    }
                }
            });

            _linearLayout.addView(button);
        }
        linearlayout.addView(scrollView);
    }

    public void enviarPedido() {

        if( tmorderApplication.obtenerVenta( tmorderApplication.getIdAsignacionActual() ) != -1 ) {
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle( getString( R.string.tag_tl_env_ped ) );
            alertDialog.setMessage( getString( R.string.tag_tl_msj_ped ) );
            alertDialog.setButton( DialogInterface.BUTTON_POSITIVE, getString( R.string.tag_btn_conf_ped ),
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tmorderApplication.generarPedido( getContext(), CategoriaActivity.this,tmorderApplication.getIdAsignacionActual());
                        }
                    }
            );
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString( R.string.tag_btn_cancl_ped ),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }
            );
            alertDialog.show();
        }else{
            runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    Toast.makeText( getBaseContext(), getString( R.string.tag_msj_ped ), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void modificarPedido(){

        if( tmorderApplication.obtenerVenta( tmorderApplication.getIdAsignacionActual() ) != -1 ) {

            Intent modificarPedidoActivity = new Intent( CategoriaActivity.this, ModificarPedidoActivity.class );
            startActivity(modificarPedidoActivity);
        } else {
            runOnUiThread( new Runnable() {
                @Override
                public void run() {
                   Toast.makeText(getBaseContext(), getString( R.string.tag_msj_ped ), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void facturarPedido(){

        if( tmorderApplication.obtenerVenta( tmorderApplication.getIdAsignacionActual() ) != -1 ) {
            Intent facturaActivity = new Intent(getApplicationContext(), FacturaActivity.class);
            startActivity(facturaActivity);
        }else {
            runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), getString( R.string.tag_msj_fac_ped ), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


}
