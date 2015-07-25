package com.olonte.tmorder.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.olonte.tmorder.R;
import com.olonte.tmorder.src.TMOrderApplication;

public class AnotacionItemActivity extends Activity {
    private TMOrderApplication tmorderApplication;
    private EditText editTextAnotacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacion_item);
        editTextAnotacion = (EditText) findViewById(R.id.etAnotacion);
        tmorderApplication = (TMOrderApplication) getApplication();
        cargarAnotacion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.anotacion_item, menu);
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
        super.onBackPressed();
    }

    public void clearEditText(){
        editTextAnotacion.setText("");
    }

    public void cargarAnotacion(){
        if ( tmorderApplication.getAnotacionActual() != null && tmorderApplication.getAnotacionActual() != "" ) {
             editTextAnotacion.setText( tmorderApplication.getAnotacionActual() );
             tmorderApplication.setAnotacionActual("");
        }else {
            editTextAnotacion.setText(tmorderApplication.cargarAnotacion(tmorderApplication.getIdAsignacionActual(), tmorderApplication.getIdProductoActual()));
        }
    }

    public void guardarAnotacion(View v) {
        tmorderApplication.guardarAnotacion(tmorderApplication.getIdAsignacionActual(),tmorderApplication.getIdProductoActual(),editTextAnotacion.getText().toString());
        onBackPressed();
    }

    public void cancelarAnotacion(View view){
        tmorderApplication.cancelarAnotacion(tmorderApplication.getIdAsignacionActual(),tmorderApplication.getIdProductoActual());
        clearEditText();
        onBackPressed();
    }
}
