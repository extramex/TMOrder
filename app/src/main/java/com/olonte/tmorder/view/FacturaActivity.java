package com.olonte.tmorder.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.olonte.tmorder.R;
import com.olonte.tmorder.dto.Pedido;
import com.olonte.tmorder.helper.AlertDialogManager;
import com.olonte.tmorder.src.TMOrderApplication;

public class FacturaActivity extends Activity {

    private TMOrderApplication tmorderApplication;
    private Context            context;
    private AlertDialogManager alertDialogManager;

    private TableLayout    tableLayout;
    private LayoutInflater layoutInflater;
    private View           newTagView;
    private TextView       textViewCantidad;
    private TextView       textViewDescripcion;
    private TextView       textViewValor_Uni;
    private TextView       textViewValor_Tol;
    private Button         buttonGenFac;

    private static final float TAG_TL_SIZE  = 12;
    private static final float TAG_TXT_SIZE = 11;
    private static final float TAG_VLR_IVA  = (float) 16 / 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        tmorderApplication = (TMOrderApplication) getApplication();
        setContext(this);
        setAlertDialogManager( new AlertDialogManager() );
        tableLayout = (TableLayout) findViewById(R.id.tabletLayout);
        buttonGenFac = (Button) findViewById(R.id.btnGenFac);

        cargarFactura( tmorderApplication.getIdAsignacionActual() );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_factura, menu);
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
        Intent categoriaActivity;
        categoriaActivity = new Intent( FacturaActivity.this, CategoriaActivity.class);
        startActivity( categoriaActivity);

    }

    public Context getContext() { return context; }

    public void setContext(Context context) { this.context = context; }

    public AlertDialogManager getAlertDialogManager() { return alertDialogManager; }

    public void setAlertDialogManager(AlertDialogManager alertDialogManager) { this.alertDialogManager = alertDialogManager; }

    public Pedido obtenerPedido ( String idAsignacionActual){

        Pedido pedido;
        pedido = null;

        if (tmorderApplication.getArrayListVentas() != null ) {

            for (int i = 0; i < tmorderApplication.getArrayListVentas().size(); i++) {
                if (tmorderApplication.getArrayListVentas().get(i).getIdAsignacion().equals(idAsignacionActual)
                        && tmorderApplication.getArrayListVentas().get(i).isEstado()) {
                    pedido = tmorderApplication.getArrayListVentas().get(i).getPedido();
                }
            }
        }
        return pedido;
    }

  public void cargarFactura( String idAsignacionActual) {

      Pedido pedido;
      String descripcion;
      int cantidad;
      int valor_unidad;
      int valor_total;
      int subTotal;
      int length;
      subTotal = 0;

      pedido = obtenerPedido( idAsignacionActual );

      if ( pedido != null ) {

          buttonGenFac.setEnabled( true );

          length = pedido.getArrayListOrdenes().size();

          addTitle();

          for (int i = 0; i < pedido.getArrayListOrdenes().size(); i++) {

              descripcion   = pedido.getArrayListOrdenes().get(i).getDescripcion();
              cantidad      = pedido.getArrayListOrdenes().get(i).getCantidad();
              valor_total   = Integer.parseInt(pedido.getArrayListOrdenes().get(i).getValor()) * cantidad;
              valor_unidad  = valor_total / cantidad;
              subTotal     += valor_total;

              View newView;

              newView = addView();

              textViewCantidad = (TextView) newView.findViewById(R.id.newTxtVcantidad);
              textViewCantidad.setText( Integer.toString( cantidad ) );

              textViewDescripcion = (TextView) newView.findViewById(R.id.newTxtVdescripcion);
              textViewDescripcion.setText( descripcion );

              textViewValor_Uni = (TextView) newView.findViewById(R.id.newTxtVvalorUnidad);
              textViewValor_Uni.setText( Integer.toString( valor_unidad ) );

              textViewValor_Tol = (TextView) newView.findViewById(R.id.newTxtVvalorTotal);
              textViewValor_Tol.setText( Integer.toString( valor_total ) );

              addItem( newView, i + 1 );
          }

          View newView;

          newView = addView();

          textViewValor_Uni = (TextView) newView.findViewById(R.id.newTxtVvalorUnidad);
          textViewValor_Uni.setTextSize( TAG_TL_SIZE );
          textViewValor_Uni.setTypeface(textViewValor_Uni.getTypeface(), Typeface.BOLD);
          textViewValor_Uni.setText( getString( R.string.tag_sub_total_fac ) );

          textViewValor_Tol = (TextView) newView.findViewById(R.id.newTxtVvalorTotal);
          textViewValor_Tol.setTextSize( TAG_TXT_SIZE );
          textViewValor_Tol.setText(Integer.toString(subTotal));

          addItem(newView, (length + 1));

          newView = addView();

          textViewValor_Uni = (TextView) newView.findViewById(R.id.newTxtVvalorUnidad);
          textViewValor_Uni.setTextSize( TAG_TL_SIZE );
          textViewValor_Uni.setTypeface(textViewValor_Uni.getTypeface(), Typeface.BOLD);
          textViewValor_Uni.setText( getString( R.string.tag_iva_fac ) );

          textViewValor_Tol = (TextView) newView.findViewById(R.id.newTxtVvalorTotal);
          textViewValor_Tol.setTextSize( TAG_TXT_SIZE );
          textViewValor_Tol.setText(Float.toString(subTotal * TAG_VLR_IVA));

          addItem(newView, (length + 2));

          newView = addView();

          textViewValor_Uni = (TextView) newView.findViewById(R.id.newTxtVvalorUnidad);
          textViewValor_Uni.setTextSize( TAG_TL_SIZE );
          textViewValor_Uni.setTypeface(textViewValor_Uni.getTypeface(), Typeface.BOLD);
          textViewValor_Uni.setText( getString( R.string.tag_total_fac ) );

          textViewValor_Tol = (TextView) newView.findViewById(R.id.newTxtVvalorTotal);
          textViewValor_Tol.setTextSize(TAG_TXT_SIZE);
          textViewValor_Tol.setText(Float.toString(subTotal + (subTotal * TAG_VLR_IVA)));

          addItem( newView, ( length + 3 ) );

      } else {
          runOnUiThread( new Runnable() {
              @Override
              public void run() {
                  getAlertDialogManager().showAlertDialog( getContext(), getString(R.string.tag_tl_err_fact), getString( R.string.tag_msj_fac_ped ) );
              }
          });
      }
    }

    public void addTitle(){

        View newView;

        int indice;

        newView = addView();

        indice = 0;

        textViewCantidad = (TextView) newView.findViewById( R.id.newTxtVcantidad );
        textViewCantidad.setTypeface(textViewCantidad.getTypeface(), Typeface.BOLD);
        textViewCantidad.setTextSize( TAG_TL_SIZE );
        textViewCantidad.setText( getString( R.string.tag_cantidad_fac ) );


        textViewDescripcion = (TextView) newView.findViewById( R.id.newTxtVdescripcion );
        textViewDescripcion.setTypeface(textViewDescripcion.getTypeface(), Typeface.BOLD);
        textViewDescripcion.setTextSize( TAG_TL_SIZE );
        textViewDescripcion.setText( getString(R.string.tag_desc_fac) );


        textViewValor_Uni = (TextView) newView.findViewById( R.id.newTxtVvalorUnidad );
        textViewValor_Uni.setTypeface(textViewValor_Uni.getTypeface(), Typeface.BOLD);
        textViewValor_Uni.setTextSize( TAG_TL_SIZE );
        textViewValor_Uni.setText( getString( R.string.tag_vlr_und_fac ) );


        textViewValor_Tol = (TextView) newView.findViewById( R.id.newTxtVvalorTotal );
        textViewValor_Tol.setTypeface(textViewValor_Tol.getTypeface(), Typeface.BOLD);
        textViewValor_Tol.setTextSize( TAG_TL_SIZE );
        textViewValor_Tol.setText( getString( R.string.tag_total_fac ) );

        addItem( newView, indice );

    }

    public View addView(){
        // get a reference to the LayoutInflater service
        layoutInflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        // inflate new_tag_view.xml to create new item
        newTagView = layoutInflater.inflate( R.layout.new_row_factura, null );

        return newTagView;
    }

    public void addItem( View newView, int index ) { tableLayout.addView( newView , index );  }

    public void onclickFactura( View view ){

        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle( getString( R.string.tag_tl_fact ) );
        alertDialog.setMessage( getString( R.string.tag_msj_fac ) );
        alertDialog.setButton( DialogInterface.BUTTON_POSITIVE, getString( R.string.tag_btn_conf_fac ) ,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if ( tmorderApplication.generarFactura( getContext(), FacturaActivity.this, tmorderApplication.obtenerIdVtaActual( tmorderApplication.getIdAsignacionActual() ) ) ) {

                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    getAlertDialogManager().showAlertDialog( getContext(), getString( R.string.tag_tl_fac_ord ), getString( R.string.tag_msj_gen_fac ) );

                                }
                            });

                            buttonGenFac.setEnabled( false );

                            Intent asignacionActivity = new Intent(FacturaActivity.this, AsignacionActivity.class);
                            startActivity( asignacionActivity );

                        }
                    }
                }
        );
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString( R.string.tag_btn_cancl_fac ),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }
        );
        alertDialog.show();
    }


}
