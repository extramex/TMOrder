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
import com.olonte.tmorder.src.TMOrderApplication;


public class SubCategoriaActivity extends Activity {
    private TMOrderApplication tmorderApplication;
    private Context context;
    private LinearLayout linearlayout;
    private ScrollView scrollView;
    private LinearLayout _linearLayout;
    private TextView textView;
    private static final String TAG_ESPACIO = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoria);
        tmorderApplication = (TMOrderApplication) getApplication();
        setContext(this);


        textView = (TextView) findViewById( R.id.txvSubCtg );
        textView.setTextColor( Color.parseColor( getString( R.string.tag_color ) ) );
        textView.setText( getString( R.string.tag_sel_ped_ms ) + TAG_ESPACIO + tmorderApplication.getIdMesaActual() );

        cargarSubCategorias();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tmorderApplication.getDbHandler().setConexion(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sub_categoria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.accion_confirmar:
                if ( tmorderApplication.validaOrden(SubCategoriaActivity.this, tmorderApplication.obtenerIdVtaActual( tmorderApplication.getIdAsignacionActual() ) ) ) {
                    enviarPedido();
                }
                break;
            case R.id.accion_volver:
                onBackPressed();
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent categoriaActivity = new Intent(getApplicationContext(), CategoriaActivity.class);
        startActivity(categoriaActivity);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void cargarSubCategorias() {

        linearlayout = new LinearLayout(getApplicationContext());

        linearlayout = (LinearLayout) findViewById(R.id.lySubCategoria);

        scrollView = new ScrollView( getApplicationContext() );
        scrollView.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );

        _linearLayout = new LinearLayout( getApplicationContext() );
        _linearLayout.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
        _linearLayout.setOrientation( LinearLayout.VERTICAL );

        scrollView.addView( _linearLayout );

        for( int i = 0; i < tmorderApplication.getArrayListSubCategorias().size(); i++ ) {

            if( tmorderApplication.getArrayListSubCategorias().get(i).getIdCategoria().equalsIgnoreCase( tmorderApplication.getIdCategoriaActual() ) ) {

                Button button;

                button = new Button( getApplicationContext() );
                button.setId(Integer.parseInt( tmorderApplication.getArrayListSubCategorias().get(i).getIdSubCategoria() ) );
                button.setText( tmorderApplication.getArrayListSubCategorias().get(i).getDescripcion() );
                button.setWidth( Integer.parseInt( getString(R.string.tag_width_btn ) ) );
                button.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button buttonOnClick = (Button) view;
                        tmorderApplication.setIdSubCategoriaActual( Integer.toString(buttonOnClick.getId() ) );
                        tmorderApplication.generarVenta( getContext(), SubCategoriaActivity.this, tmorderApplication.getIdAsignacionActual());
                        if(tmorderApplication.getDbHandler().isConexion()) {
                            Intent productoActivy = new Intent(SubCategoriaActivity.this, ProductoActivity.class);
                            startActivity(productoActivy);
                        }
                    }
                });
                _linearLayout.addView(button);
            }
        } // end loop for

        linearlayout.addView( scrollView );

    }

    public void enviarPedido() {

        if (tmorderApplication.obtenerVenta(tmorderApplication.getIdAsignacionActual()) != -1) {
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle( getString( R.string.tag_tl_env_ped ) );
            alertDialog.setMessage( getString( R.string.tag_tl_msj_ped ) );
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,getString( R.string.tag_btn_conf_ped ),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tmorderApplication.generarPedido(getContext(), SubCategoriaActivity.this, tmorderApplication.getIdAsignacionActual());
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
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), getString( R.string.tag_msj_ped ), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
