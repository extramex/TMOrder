package com.olonte.tmorder.view;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.olonte.tmorder.R;
import com.olonte.tmorder.dto.Orden;
import com.olonte.tmorder.helper.AssetsPropertyReader;
import com.olonte.tmorder.helper.NumberPicker;
import com.olonte.tmorder.helper.ViewHolderProducto;
import com.olonte.tmorder.src.TMOrderApplication;

import java.util.ArrayList;
import java.util.Properties;

public class ModificarPedidoActivity extends ListActivity {

    private TMOrderApplication tmorderApplication;
    private Context context;
    private OrdenAdapter ordenAdapter;
    private char[] arrayPosItemsEliminar;
    private static final char TAG_VALOR_CANCELADO = '*';
    private static final char TAG_VALOR_NULL = ' ';
    private static final String TAG_VACIO = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_pedido);
        tmorderApplication = (TMOrderApplication) getApplication();
        setContext(this);
        cargarPedido();
    }

     public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.modificar_pedido, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.accion_confirmar:
                generarPedido();
                break;
            /*case R.id.accion_cancelar:
                //cancelarPedido();
                break;*/
            case R.id.accion_volver:
                onBackPressed();
                break;
        }
        return false;
    }

    public void setArrayPosItemsEliminar( int size){ arrayPosItemsEliminar = new char[ size ]; }

    public void setValorArregloItem( int posicion, char valor ){ arrayPosItemsEliminar[ posicion ] = valor;  }

    public void generarPedido(){

        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle( getString(R.string.tag_tl_mod_ped) );
        alertDialog.setMessage( getString(R.string.tag_msj_mod_ped)  );
        alertDialog.setButton( DialogInterface.BUTTON_POSITIVE, getString(R.string.tag_btn_conf_mod_ped),
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tmorderApplication.generarOrdenMod( tmorderApplication.obtenerIdVtaActual( tmorderApplication.getIdAsignacionActual() ),obtenerOrden() );
                        tmorderApplication.eliminarAnotaciones( tmorderApplication.getIdAsignacionActual() );
                        enviarPedido();
                        onBackPressed();
                    }
                }
        );
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,  getString(R.string.tag_btn_canc_mod_ped),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }
        );
        alertDialog.show();
    }

    public ArrayList<Orden> obtenerOrden(){

        ArrayList<Orden> arrayListOrdenes;
        String idPedido;
        arrayListOrdenes = new ArrayList<Orden>();
        idPedido = tmorderApplication.obtenerIdPedido( tmorderApplication.obtenerIdVtaActual( tmorderApplication.getIdAsignacionActual() ) );

        for ( int posicion = 0; posicion < ordenAdapter.getCount(); posicion++ ) {

            if( !buscarItem( posicion ) ){ /** Se crea un nuevo arreglo de orden con los producto modificados */
                Orden orden;
                orden = new Orden( idPedido,
                                   ordenAdapter.getItem(posicion).getIdProducto(),
                                   ordenAdapter.getItem(posicion).getCantidad(),
                                   ordenAdapter.getItem(posicion).getValor() ,
                                   ordenAdapter.getItem(posicion).getDescripcion(),
                        tmorderApplication.obtenerAnotacion( ordenAdapter.getItem(posicion).getIdProducto() ) );
                arrayListOrdenes.add(orden);
            }
        }
        return arrayListOrdenes;
    }

    public void  cargarPedido(){
        ordenAdapter = new OrdenAdapter( getApplicationContext(),
                                         tmorderApplication.getArrayListVentas().get(
                                            tmorderApplication.obtenerVenta( tmorderApplication.getIdAsignacionActual() ) )
                                                .getPedido().getArrayListOrdenes() );

        setArrayPosItemsEliminar( ordenAdapter.getCount() );

        setListAdapter(ordenAdapter);
    }

    public void enviarPedido() {
        tmorderApplication.generarModPedido(getContext(), ModificarPedidoActivity.this,tmorderApplication.getIdAsignacionActual());
    }

    /*******************************************************************************************************************************/
    private class OrdenAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private ArrayList<Orden> itemsOrden;

        public OrdenAdapter(Context context, ArrayList<Orden> itemsOrden) {
            layoutInflater = LayoutInflater.from(context);
            this.itemsOrden = itemsOrden;
        }

        @Override
        public int getCount() {
            return itemsOrden.size();
        }

        @Override
        public Orden getItem(int posicion) {
            return  itemsOrden.get(posicion);
        }

        @Override
        public long getItemId(int posicion) {
            return posicion;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
           final  ViewHolderProducto viewHolderProducto;

            if (view == null) {

                view = layoutInflater.inflate(R.layout.list_item_productos, null);

                viewHolderProducto = new ViewHolderProducto();
                /**
                 *   Se hace el binding entre el viewHolderProducto.java y el list_item_productos.xml
                 *
                 */
                viewHolderProducto.checkBox       = (CheckBox) view.findViewById( R.id.checkBoxItem );
                viewHolderProducto.idProducto     = (TextView) view.findViewById(R.id.idProducto);
                viewHolderProducto.descripcion    = (TextView) view.findViewById(R.id.descripcion);
                viewHolderProducto.valor          = (TextView) view.findViewById(R.id.valor);
                viewHolderProducto.cantidad       = (TextView) view.findViewById(R.id.cantidad);
                viewHolderProducto.anotacion      = (TextView) view.findViewById(R.id.anotacion);
                viewHolderProducto.numberPicker   = (NumberPicker) view.findViewById(R.id.numberPicker);
                viewHolderProducto.numberPicker.setOnChangeListener(numberPickerOnChangedListener);
                viewHolderProducto.numberPicker.setOnClickListener(numberPickerOnClickListener);

                viewHolderProducto.checkBox.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adicionaEliminaItem((Integer) viewHolderProducto.numberPicker.getTag());
                    }
                });

                viewHolderProducto.descripcion.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickAnotacion( (Integer)viewHolderProducto.numberPicker.getTag() );
                    }
                });

                viewHolderProducto.valor.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickAnotacion( (Integer)viewHolderProducto.numberPicker.getTag() );
                    }
                });

                /**
                 * se adiciona un tag al layaout
                 */
                view.setTag(viewHolderProducto);

            } else {
                /**
                 * Como ya esta hecho el bindig solo se le asigna el Layout al viewHolderProducto
                 */
                viewHolderProducto = (ViewHolderProducto) view.getTag();
            }

            /**
             * Fijar la posición del numberPicker
             */
            viewHolderProducto.numberPicker.setTag(position);

            Orden orden = itemsOrden.get(position);

            /**
             * Se asigna los valores de cada item del array itemsProductos al viewHolderProducto
             */
            viewHolderProducto.idProducto.setText( orden.getIdProducto() );
            viewHolderProducto.descripcion.setText( orden.getDescripcion());
            viewHolderProducto.valor.setText(orden.getValor());
            viewHolderProducto.anotacion.setText(orden.getAnotacion());
            viewHolderProducto.numberPicker.setCurrent(orden.getCantidad());



            return view;
        }
    } // end class OrdenAdapter

    /**
     * *****************************************************************************************************************************************************************
     */
    NumberPicker.OnChangedListener numberPickerOnChangedListener = new NumberPicker.OnChangedListener() {
        @Override
        public void onChanged(NumberPicker numberPicker, int oldVal, int newVal) {
            int posicion = (Integer) numberPicker.getTag();
            Orden orden = ordenAdapter.getItem(posicion);
            orden.setCantidad(newVal);
        }


    };

    NumberPicker.OnClickListener numberPickerOnClickListener = new NumberPicker.OnClickListener() {
        @Override
        public void onClick(View view) {
            onClickAnotacion( (Integer)view.getTag() );
        }
    };


    public boolean buscarItem( int posicion ){
        if( arrayPosItemsEliminar[ posicion ] == TAG_VALOR_CANCELADO ) {
            return true;
        }else {
            return false;
        }
    }

    public void adicionarItem( int posicion ){ setValorArregloItem( posicion, TAG_VALOR_CANCELADO ); }

    public void eliminarItem( int posicion ){ setValorArregloItem( posicion, TAG_VALOR_NULL );  }

    public void adicionaEliminaItem( int posicion ){
        if( !buscarItem( posicion ) ){
            adicionarItem( posicion );
        }else {
            eliminarItem( posicion );
        }
    }

    public void onClickAnotacion( int posicion ) {

        int posAnt;

        if ( ordenAdapter.getItem(posicion).getCantidad() > 0 ) {

            tmorderApplication.setIdProductoActual( ordenAdapter.getItem(posicion).getIdProducto() );

            posAnt = tmorderApplication.buscarAnotacion( tmorderApplication.getIdAsignacionActual(), ordenAdapter.getItem(posicion).getIdProducto() );

            if ( posAnt != -1 ) {
                tmorderApplication.setAnotacionActual( tmorderApplication.getArrayListAnotaciones().get(posAnt).getAnotacion() );
            }else {
                if (ordenAdapter.getItem(posicion).getAnotacion() != null && ordenAdapter.getItem(posicion).getAnotacion() != TAG_VACIO ) {
                    tmorderApplication.setAnotacionActual(ordenAdapter.getItem(posicion).getAnotacion());
                }
            }

            Intent anotacionItemActivity = new Intent(ModificarPedidoActivity.this, AnotacionItemActivity.class);
            startActivity(anotacionItemActivity);

        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), getString( R.string.tag_val_cant ), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
