package com.olonte.tmorder.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.olonte.tmorder.R;
import com.olonte.tmorder.dto.Usuario;
import com.olonte.tmorder.helper.AlertDialogManager;
import com.olonte.tmorder.src.TMOrderApplication;

public class LogueoActivity extends Activity {

    private TMOrderApplication tmorderApplication;
    private Context context;
    private AlertDialogManager alertDialogManager;
    private ArrayAdapter<Usuario> arrayAdapterUsuarios;
    private Spinner spinnerUsuario;
    private  String idUsuario;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueo);
        setContext( this );
        setAlertDialogManager( new AlertDialogManager() );
        tmorderApplication = (TMOrderApplication) getApplication();

        if ( tmorderApplication.initConn( getContext(), LogueoActivity.this) ) {
            cargarUsuario();
        }

        editTextPassword = (EditText) findViewById(R.id.etClave);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logueo, menu);
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

    @Override
    public void onBackPressed() {
    }

    public Context getContext() { return context; }

    public void setContext(Context context) {
        this.context = context;
    }

    public AlertDialogManager getAlertDialogManager() { return alertDialogManager;  }

    public void setAlertDialogManager(AlertDialogManager alertDialogManager) { this.alertDialogManager = alertDialogManager; }

    public String getIdUsuario() { return idUsuario;  }

    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public void cargarUsuario(){

        spinnerUsuario = (Spinner) findViewById( R.id.spNombre );
        arrayAdapterUsuarios = new ArrayAdapter<Usuario>( LogueoActivity.this, android.R.layout.simple_spinner_item, tmorderApplication.getArrayListUsuarios() );
        arrayAdapterUsuarios.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerUsuario.setAdapter( arrayAdapterUsuarios );
        spinnerUsuario.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setIdUsuario( tmorderApplication.getArrayListUsuarios().get( i ).getIdUsuario() );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    public void startMesaActivity(View v) {
      if ( !getIdUsuario().equals( getString( R.string.tag_id_usu_df ) ) ) {
          if (tmorderApplication.validarUsuario(getIdUsuario(), editTextPassword.getText().toString())) {
              if (tmorderApplication.cargarInicial()) {
                  Intent asignacionActivity = new Intent(LogueoActivity.this, AsignacionActivity.class);
                  startActivity(asignacionActivity);
              }
          } else {
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      getAlertDialogManager().showAlertDialog(getContext(), getString(R.string.tag_tl_error_usuario), getString(R.string.tag_msj_error_usuario));
                  }
              });
          }
      }else {
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  getAlertDialogManager().showAlertDialog(getContext(), getString(R.string.tag_tl_error_usuario), getString(R.string.tag_msj_error_ing_usu));
              }
          });
      }

    }
}
