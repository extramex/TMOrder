package com.olonte.tmorder.view;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.olonte.tmorder.R;
import com.olonte.tmorder.dto.Orden;
import com.olonte.tmorder.dto.Producto;
import com.olonte.tmorder.helper.NumberPicker;
import com.olonte.tmorder.helper.ViewHolderProducto;
import com.olonte.tmorder.src.TMOrderApplication;

import java.util.ArrayList;

public class ProductoActivity extends ListActivity {
    private TMOrderApplication tmorderApplication;
    private Context context;
    private ProductoAdapter productoAdapter;
    private static final int TAG_VALOR_CANCELADO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        tmorderApplication = (TMOrderApplication) getApplication();
        setContext(this);
        cargarProductos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.producto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.accion_confirmar:
                generarPedido();
                if ( tmorderApplication.validaOrden( ProductoActivity.this, tmorderApplication.obtenerIdVtaActual( tmorderApplication.getIdAsignacionActual() ) ) ) {
                   runOnUiThread( new Runnable() {
                       @Override
                       public void run() {
                          Toast.makeText(getBaseContext(), getString(R.string.tag_msj_pedido), Toast.LENGTH_SHORT).show();
                       }
                   });
                }
                onBackPressed();
                break;
            /*case R.id.accion_cancelar:
                //cancelarPedido();
                break;*/
            case R.id.accion_volver:
                eliminarAnotaciones();
                onBackPressed();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void cargarProductos(){

        tmorderApplication.cargaListaProductos(getContext(), ProductoActivity.this, tmorderApplication.getIdSubCategoriaActual() );

        if( tmorderApplication.getDbHandler().isConexion() ) {
            productoAdapter = new ProductoAdapter( getApplicationContext(), tmorderApplication.getArrayListProductos() );
            setListAdapter(productoAdapter);
        }

    }

    public void eliminarAnotaciones(){ tmorderApplication.eliminarAnotaciones(tmorderApplication.getIdAsignacionActual());  }

    public void generarPedido(){

        tmorderApplication.generarOrden( tmorderApplication.obtenerIdVtaActual( tmorderApplication.getIdAsignacionActual() ), obtenerOrden() );
        tmorderApplication.eliminarAnotaciones(tmorderApplication.getIdAsignacionActual());
    }

    public ArrayList<Orden> obtenerOrden() {

        ArrayList<Orden> arrayListOrdenes;
        int contOrden;
        arrayListOrdenes = null;
        contOrden = 0;


        for ( int posicion = 0; posicion < productoAdapter.getCount(); posicion++ ) {

            if ( productoAdapter.getItem(posicion).getNumber() > TAG_VALOR_CANCELADO ) {

                if (contOrden == 0 ) {
                    arrayListOrdenes = new ArrayList<Orden>();
                }

                contOrden++;

                Orden orden;
                orden = new Orden( productoAdapter.getItem(posicion).getIdProducto(),
                                   productoAdapter.getItem(posicion).getNumber(),
                                   Integer.toString( productoAdapter.getItem(posicion).getValor() ),
                                   productoAdapter.getItem(posicion).getDescripcion(),
                                   tmorderApplication.obtenerAnotacion( productoAdapter.getItem(posicion).getIdProducto() ) );

                arrayListOrdenes.add( orden );
            }
        }
        return arrayListOrdenes;
    }



    /*******************************************************************************************************************************/
    private class ProductoAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private ArrayList<Producto> itemsProducto;

        public ProductoAdapter(Context context, ArrayList<Producto> itemsProducto) {
            layoutInflater = LayoutInflater.from(context);
            this.itemsProducto = itemsProducto;
        }

        @Override
        public int getCount() {
            return itemsProducto.size();
        }

        @Override
        public Producto getItem(int posicion) {
            return  itemsProducto.get(posicion);
        }

        @Override
        public long getItemId(int posicion) {
            return posicion;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
           final ViewHolderProducto viewHolderProducto;

            if (view == null) {

                view = layoutInflater.inflate(R.layout.list_item_pedido, null);

                viewHolderProducto = new ViewHolderProducto();
                /**
                 *   Se hace el binding entre el viewHolderProducto.java y el list_item_productos.xml
                 *
                 */
                viewHolderProducto.idProducto     = (TextView) view.findViewById(R.id.idProducto);
                viewHolderProducto.descripcion    = (TextView) view.findViewById(R.id.descripcion);
                viewHolderProducto.valor          = (TextView) view.findViewById(R.id.valor);
                viewHolderProducto.cantidad       = (TextView) view.findViewById(R.id.cantidad);
                viewHolderProducto.numberPicker   = (NumberPicker) view.findViewById(R.id.numberPicker);
                viewHolderProducto.numberPicker.setOnChangeListener(numberPickerOnChangedListener);
                viewHolderProducto.numberPicker.setOnClickListener(numberPickerOnClickListener);


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

            Producto producto = itemsProducto.get(position);

            /**
             * Se asigna los valores de cada item del array itemsProductos al viewHolderProducto
             */
            viewHolderProducto.idProducto.setText( producto.getIdProducto() );
            viewHolderProducto.descripcion.setText( producto.getDescripcion());
            viewHolderProducto.valor.setText( Integer.toString(producto.getValor() ) );
            viewHolderProducto.cantidad.setText(  Integer.toString(producto.getCantidad() ) );
            viewHolderProducto.numberPicker.setCurrent( producto.getNumber() );

            return view;
        }
    } // end class ProductoAdapter

    /**
     * *****************************************************************************************************************************************************************
     */
    NumberPicker.OnChangedListener numberPickerOnChangedListener = new NumberPicker.OnChangedListener() {
        @Override
        public void onChanged(NumberPicker numberPicker, int oldVal, int newVal) {
            int posicion = (Integer) numberPicker.getTag();
            Producto producto = productoAdapter.getItem(posicion);
            producto.setNumber(newVal);
        }


    };

    NumberPicker.OnClickListener numberPickerOnClickListener = new NumberPicker.OnClickListener() {
        @Override
        public void onClick(View view) {
            onClickAnotacion( (Integer) view.getTag() );
        }
    };


    public void onClickAnotacion( int posicion ) {
        if (productoAdapter.getItem(posicion).getNumber() > 0) {
            tmorderApplication.setIdProductoActual(productoAdapter.getItem(posicion).getIdProducto());
            Intent anotacionItemActivity = new Intent(ProductoActivity.this, AnotacionItemActivity.class);
            startActivity(anotacionItemActivity);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), getString(R.string.tag_val_cant ), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    /**
     **********************************************************************************************************************************************************************
     */
}
