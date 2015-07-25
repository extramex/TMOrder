package com.olonte.tmorder.src;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.olonte.tmorder.R;
import com.olonte.tmorder.dto.Anotacion;
import com.olonte.tmorder.dto.Asignacion;
import com.olonte.tmorder.dto.Categoria;
import com.olonte.tmorder.dto.Orden;
import com.olonte.tmorder.dto.Pedido;
import com.olonte.tmorder.dto.Producto;
import com.olonte.tmorder.dto.SubCategoria;
import com.olonte.tmorder.dto.Usuario;
import com.olonte.tmorder.dto.Venta;
import com.olonte.tmorder.helper.AlertDialogManager;
import com.olonte.tmorder.modelo.DBHandler;

import java.util.ArrayList;

/**
 * Created by jsgravenhorst on 9/9/2014.
 */
public class TMOrderApplication extends Application {

    private DBHandler dbHandler;
    private Activity activity;
    private Context context;
    private String idSession;
    private Usuario usuario;
    private String idAsignacionActual;
    private String idMesaActual;
    private String idCategoriaActual;
    private String idSubCategoriaActual;
    private String idProductoActual;
    private String idPedidoActual;
    private String anotacionActual;
    private String ordenLista;
    private String idNotificacion;
    private ArrayList<Usuario> arrayListUsuarios;
    private ArrayList<Asignacion> arrayListAsignaciones;
    private ArrayList<Categoria> arrayListCategorias;
    private ArrayList<SubCategoria> arrayListSubCategorias;
    private ArrayList<Producto> arrayListProductos;
    private ArrayList<Venta> arrayListVentas;
    private ArrayList<Anotacion> arrayListAnotaciones;

    @Override
    public void onCreate() { super.onCreate();  }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DBHandler getDbHandler() {
        return dbHandler;
    }

    public void setDbHandler(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void setDbHandler() { setDbHandler(new DBHandler(getContext(), getActivity() )); }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public ArrayList<Usuario> getArrayListUsuarios() { return arrayListUsuarios; }

    public void setArrayListUsuarios(ArrayList<Usuario> arrayListUsuarios) { this.arrayListUsuarios = arrayListUsuarios;  }

    public ArrayList<Asignacion> getArrayListAsignaciones() {
        return arrayListAsignaciones;
    }

    public void setArrayListAsignaciones(ArrayList<Asignacion> arrayListAsignaciones) { this.arrayListAsignaciones = arrayListAsignaciones;  }

    public ArrayList<Categoria> getArrayListCategorias() {
        return arrayListCategorias;
    }

    public void setArrayListCategorias(ArrayList<Categoria> arrayListCategorias) { this.arrayListCategorias = arrayListCategorias; }

    public ArrayList<SubCategoria> getArrayListSubCategorias() {
        return arrayListSubCategorias;
    }

    public void setArrayListSubCategorias(ArrayList<SubCategoria> arrayListSubCategorias) { this.arrayListSubCategorias = arrayListSubCategorias;  }

    public String getIdAsignacionActual() {
        return idAsignacionActual;
    }

    public void setIdAsignacionActual(String idAsignacionActual) { this.idAsignacionActual = idAsignacionActual; }

    public String getIdMesaActual() { return idMesaActual; }

    public void setIdMesaActual(String idMesaActual) { this.idMesaActual = idMesaActual; }

    public String getIdCategoriaActual() {
        return idCategoriaActual;
    }

    public void setIdCategoriaActual(String idCategoriaActual) { this.idCategoriaActual = idCategoriaActual; }

    public String getIdSubCategoriaActual() {
        return idSubCategoriaActual;
    }

    public void setIdSubCategoriaActual(String idSubCategoriaActual) { this.idSubCategoriaActual = idSubCategoriaActual; }

    public String getIdPedidoActual() { return idPedidoActual;  }

    public void setIdPedidoActual(String idPedidoActual) { this.idPedidoActual = idPedidoActual;  }

    public String getIdProductoActual() { return idProductoActual; }

    public String getAnotacionActual() {  return anotacionActual;  }

    public void setAnotacionActual(String anotacionActual) { this.anotacionActual = anotacionActual; }

    public void setIdProductoActual(String idProductoActual) { this.idProductoActual = idProductoActual; }

    public ArrayList<Anotacion> getArrayListAnotaciones() { return arrayListAnotaciones;  }

    public void setArrayListAnotaciones(ArrayList<Anotacion> arrayListAnotaciones) { this.arrayListAnotaciones = arrayListAnotaciones; }

    public ArrayList<Producto> getArrayListProductos() { return arrayListProductos;  }

    public void setArrayListProductos(ArrayList<Producto> arrayListProductos) { this.arrayListProductos = arrayListProductos; }

    public ArrayList<Venta> getArrayListVentas() {  return arrayListVentas;  }

    public void setArrayListVentas(ArrayList<Venta> arrayListVentas) { this.arrayListVentas = arrayListVentas;  }

    public String getOrdenLista() { return ordenLista; }

    public void setOrdenLista(String ordenLista) { this.ordenLista = ordenLista; }

    public String getIdNotificacion() { return idNotificacion;  }

    public void setIdNotificacion(String idNotificacion) { this.idNotificacion = idNotificacion;  }

    public boolean initConn(Context context, Activity activity ) {
        setContext( context );
        setActivity( activity) ;
        setDbHandler();
        return cargarUsuarios();
    }

    public boolean  cargarUsuarios(){
        boolean band;
        band = false;

        getDbHandler().testIntConn();

        if( getDbHandler().isConexion() ) {
            setArrayListUsuarios( getDbHandler().cargarUsuarios() );
            setIdSession( getDbHandler().getIdSession() );
            band = true;
        }

        return band;

    }

    public boolean validarUsuario( String idUsuario, String passw ){

        boolean band;
        band = false;

        for( int i = 0; i < getArrayListUsuarios().size(); i++ ) {
            if ( getArrayListUsuarios().get(i).getIdUsuario().equals( idUsuario ) && getArrayListUsuarios().get(i).getPassw().equals( passw ) )  {
                setUsuario( new Usuario ( getArrayListUsuarios().get(i).getIdUsuario(),
                                          getArrayListUsuarios().get(i).getNombre(),
                                          getArrayListUsuarios().get(i).getApellido() ) );
                band = true;
            }
        }
        return band;
    }


    public boolean cargarInicial(){
        boolean band;
        band = false;
        if ( cargarAsignaciones() ) {
            if (getDbHandler().isConexion()) {
                if ( cargarCategorias()) {
                    if (getDbHandler().isConexion()) {
                        if ( cargarSubCategorias()) {
                            band = true;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(), getActivity().getString(R.string.tag_msjCrgIni), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        }
        return band;
    }

    public boolean cargarAsignaciones() {
        boolean band;
        band = false;
        getDbHandler().testIntConn( );
        if (getDbHandler().isConexion() ) {
            setArrayListAsignaciones( getDbHandler().cargarAsignaciones( getIdSession(), getUsuario().getIdUsuario() ) );
            if( getDbHandler().isConexion()){
                setIdSession( getDbHandler().getIdSession() );
                band = true;
                /** Se valida si existen ventas creadas con anterioridad */
                if ( getDbHandler().getArrayListVentas() != null ) {
                    setArrayListVentas( getDbHandler().getArrayListVentas() );
                    /** Se valida si existen anotaciones creadas con anterioridad */
                    if ( getDbHandler().getArrayListAnotaciones() != null ) {
                        setArrayListAnotaciones( getDbHandler().getArrayListAnotaciones() );
                    }
                }
            }
        }

        return band;
    }

    public boolean cargarCategorias() {
        boolean band;
        band = false;
        getDbHandler().testIntConn( );
        if( getDbHandler().isConexion() ) {
            setArrayListCategorias( getDbHandler().cargarCategorias( getIdSession() ) );
            if (getDbHandler().isConexion()) {
                setIdSession( getDbHandler().getIdSession() );
                band = true;
            }
        }
        return band;
    }

    public boolean cargarSubCategorias() {
        boolean band;
        band = false;
        getDbHandler().testIntConn();
        if ( getDbHandler().isConexion() ) {
            setArrayListSubCategorias( getDbHandler().cargarSubCategorias( getIdSession() ) );
            if(getDbHandler().isConexion()) {
                setIdSession( getDbHandler().getIdSession() );
                band = true;
            }
        }
        return band;
    }

    public void generarVenta(Context context, Activity activity, String idAsignacionActual ){
        boolean estado;
        estado = true;
        if(getArrayListVentas() != null ) { /** Si existen objetos u objeto Venta  en el ArrayListVentas de la idAsignacionActual */
            if(validarVenta( getIdAsignacionActual())){
                getDbHandler().testIntConn(context,activity);
                if(getDbHandler().isConexion()){
                    getArrayListVentas().add(getDbHandler().generaVenta(getIdSession(), idAsignacionActual));
                    if(getDbHandler().isConexion()){
                        setIdSession(getDbHandler().getIdSession());
                        cambiarEstadoAsignacion(idAsignacionActual,estado);
                    }
                }
            } /** Si el objeto venta ya existe en el ArrayListVentas no se hace nada */
        }else{ /** No existen objetos Venta en el ArrayListVentas */
            getDbHandler().testIntConn(context,activity);
            if ( getDbHandler().isConexion() ) {
                 setArrayListVentas( new ArrayList<Venta>() );
                 getArrayListVentas().add( getDbHandler().generaVenta( getIdSession(), idAsignacionActual ) ); /** Retorna una objeto Venta */
                 if (getDbHandler().isConexion()) {
                     setIdSession(getDbHandler().getIdSession());
                     cambiarEstadoAsignacion( idAsignacionActual,estado );
                 }
            }
        }

    }


    /**
     *
     * @param idAsignacionActual
     * @return boolean
     * Se valida si ya existe un objeto venta con el idAsignacionActual y que ademas este activo estado(true)
     */
    public boolean validarVenta( String idAsignacionActual ){
        boolean band;

        band = true;

        for ( int i = 0; i < arrayListVentas.size(); i++ ) {
                if( getArrayListVentas().get( i ).getIdAsignacion().equalsIgnoreCase(idAsignacionActual)
                        && getArrayListVentas().get(i).isEstado()== true ) {
                    band = false;
                }
            }

        return  band;
    }

    /**
     *  Se cambia el estado a la asignacion  a ocupada (true)
     *
     */
    public void  cambiarEstadoAsignacion(String idAsignacionActual, boolean estado){
        for( int i = 0; i < getArrayListAsignaciones().size(); i++ ){
            if( getArrayListAsignaciones().get(i).getIdAsignacion().equalsIgnoreCase(idAsignacionActual)){
                getArrayListAsignaciones().get(i).setEstado(estado);
            }
        }
    }

    public void cargaListaProductos(Context context, Activity activity, String idSubcategoria){

         getDbHandler().testIntConn(context,activity);
         if(getDbHandler().isConexion()){
             setArrayListProductos( getDbHandler().cargarListaProductos(getIdSession(), idSubcategoria) );
             if(getDbHandler().isConexion()){
                 setIdSession(getDbHandler().getIdSession());
             }
         }
   }

    public int obtenerVenta(String idAsignacionActual){
        int pos;
        pos = -1;
        if( getArrayListVentas()!= null){
            for( int i = 0; i < getArrayListVentas().size(); i++){
                if( getArrayListVentas().get(i).getIdAsignacion().equalsIgnoreCase(idAsignacionActual) && getArrayListVentas().get(i).isEstado() ){
                    pos = i;
                }
            }
        }
        return pos;
    }

    public String obtenerIdVtaActual( String idAsignacionActual ){

        String idVentaActual;
        idVentaActual  = null;
        if( getArrayListVentas()!= null ){
            for( int i = 0; i < getArrayListVentas().size(); i++){
                if( getArrayListVentas().get(i).getIdAsignacion().equalsIgnoreCase( idAsignacionActual ) && getArrayListVentas().get(i).isEstado() ){
                    idVentaActual = getArrayListVentas().get(i).getIdVenta();
                }
            }
        }
        return idVentaActual;
    }

    public void generarPedido(Context context, Activity activity , String idAsignacionActual) {
        int pos;
        pos = obtenerVenta( idAsignacionActual );
        if (pos != -1) {
            getDbHandler().testIntConn( context, activity );
            if ( getDbHandler().isConexion() ) {
                getDbHandler().generarPedido( getIdSession(), getArrayListVentas().get(pos).getPedido().toJSON() );
                if ( getDbHandler().isConexion() ) {
                    setIdSession( getDbHandler().getIdSession() );
                    setIdPedido( getArrayListVentas().get(pos).getPedido(),getDbHandler().getIdPedido() );
                    activity.runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            String msgPedido;
                            msgPedido="Pedido enviado con éxito";
                            Toast.makeText(getBaseContext(), msgPedido, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }else {
            activity.runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    String msgPedido;
                    msgPedido="No existen pedidos para la mesa seleccionada";
                    Toast.makeText(getBaseContext(), msgPedido, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void setIdPedido( Pedido pedido, String idPedido ){

        pedido.setIdPedido(idPedido);

        for ( int i = 0; i < pedido.getArrayListOrdenes().size(); i++ ){
            if  ( pedido.getArrayListOrdenes().get( i ).isEstado() ) {
                pedido.getArrayListOrdenes().get( i ).setIdPedido( idPedido );
            }
        }
    }

    public String obtenerIdPedido( String idVentaActual ){
        String idPedido;
        idPedido = null;
        for( int i = 0; i < getArrayListVentas().size(); i++ ) {

            if ( getArrayListVentas().get(i).getIdVenta().equals(idVentaActual) ) {
                getArrayListVentas().get(i).getPedido().getIdPedido();
            }
        }
        return idPedido;
    }

    public void generarModPedido(Context context, Activity activity , String idAsignacionActual) {
        int pos;
        String idPedidoDf;
        idPedidoDf = "0";
        pos = obtenerVenta( idAsignacionActual );

        if (pos != -1) {
            getDbHandler().testIntConn( context, activity );
            if ( getDbHandler().isConexion() ) {
                getDbHandler().generarModPedido(getIdSession(), getArrayListVentas().get(pos).getPedido().toJSON());
                if (getDbHandler().isConexion()) {
                    if ( !getDbHandler().getIdPedido().equals( idPedidoDf ) ) {
                        setIdPedido( getArrayListVentas().get(pos).getPedido(), getDbHandler().getIdPedido() );
                    }
                    setIdSession( getDbHandler().getIdSession() );
                    activity.runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            String msgPedido;
                            msgPedido="Pedido enviado con éxito";
                            Toast.makeText(getBaseContext(), msgPedido, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String msgPedido;
                    msgPedido = "No existen pedidos para la mesa seleccionada";
                    Toast.makeText(getBaseContext(), msgPedido, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public int obtenerVentaOrden( String idVentaActual){
        int pos;
        pos = 0;
        for( int i = 0; i < getArrayListVentas().size(); i++){
            if( getArrayListVentas().get(i).getIdVenta().equalsIgnoreCase(idVentaActual)){
                pos = i;
            }
        }
        return pos;
     }

    public void generarOrden(String idVentaActual, ArrayList<Orden> arrayListOrdenes){

        if ( arrayListOrdenes != null ) {

            int pos;
            int posOrd;

            pos = obtenerVentaOrden(idVentaActual);

            if (getArrayListVentas().get(pos).getPedido() == null) { /** Si existe la venta pero no tiene pedido alguno */
                getArrayListVentas().get(pos).setPedido(arrayListOrdenes); /** Se crea el nuevo pedido y se le asigna la orden */
            } else { /** Si la venta ya existe y tiene pedido */
                for (int i = 0; i < arrayListOrdenes.size(); i++) {
                    posOrd = buscarOrden(arrayListOrdenes.get(i).getIdProducto(), pos); /** Se busca si ya existe el producto */
                    if (posOrd == -1) { /** Si el producto no existe */
                        Orden orden = new Orden(arrayListOrdenes.get(i).getIdProducto(),
                                arrayListOrdenes.get(i).getCantidad(),
                                arrayListOrdenes.get(i).getValor(),
                                arrayListOrdenes.get(i).getDescripcion(),
                                arrayListOrdenes.get(i).getAnotacion());
                        getArrayListVentas().get(pos).getPedido().getArrayListOrdenes().add(orden);
                    } else {  /** Si el producto existe la nueva cnatidad ingresada se le suma a la existente*/
                        getArrayListVentas().get(pos).getPedido().getArrayListOrdenes().get(posOrd).setCantidad(getArrayListVentas().get(pos).getPedido().getArrayListOrdenes().get(posOrd).getCantidad()
                                + arrayListOrdenes.get(i).getCantidad());
                        getArrayListVentas().get(pos).getPedido().getArrayListOrdenes().get(posOrd).setValor(arrayListOrdenes.get(i).getValor());
                        getArrayListVentas().get(pos).getPedido().getArrayListOrdenes().get(posOrd).setDescripcion(arrayListOrdenes.get(i).getDescripcion());
                        getArrayListVentas().get(pos).getPedido().getArrayListOrdenes().get(posOrd).setAnotacion(arrayListOrdenes.get(i).getAnotacion());
                    }
                }
            }
        }
    }

    /** Se genera el nuevo pedido con base en la modificacion hecha  */
    public void generarOrdenMod(String idVentaActual, ArrayList<Orden> arrayListOrdenes){

        int pos;
        pos = obtenerVentaOrden(idVentaActual);
        getArrayListVentas().get(pos).getPedido().setArrayListOrdenes( arrayListOrdenes );

    }

    public int  buscarOrden( String idProducto, int pos) {
        int posc = -1;
        int i = 0;

        while( i <  getArrayListVentas().get(pos).getPedido().getArrayListOrdenes().size() ){
            if (  getArrayListVentas().get(pos).getPedido().getArrayListOrdenes().get(i).getIdProducto().equalsIgnoreCase( idProducto ) ) {
                posc = i;
                i = getArrayListVentas().get(pos).getPedido().getArrayListOrdenes().size();
            }
            i++;
        }

        return  posc;
    }

    public String obtenerAnotacion( String idProducto){
        String anotacion;
        anotacion="";

        if( getArrayListAnotaciones() != null ){
            for( int i = 0; i < getArrayListAnotaciones().size(); i++){
                if( getArrayListAnotaciones().get(i).getIdProducto().equalsIgnoreCase(idProducto)){
                    anotacion = getArrayListAnotaciones().get(i).getAnotacion();
                }
            }
        }
        return anotacion;
    }

    public int  buscarAnotacion(String idAsignacionActual, String idProductoActual){
        int pos;
        pos = -1;

        if (getArrayListAnotaciones() != null) {
            for (int i = 0; i < getArrayListAnotaciones().size(); i++) {
                if (getArrayListAnotaciones().get(i).getIdProducto().equalsIgnoreCase(idProductoActual) &&
                        getArrayListAnotaciones().get(i).getIdAsignacion().equalsIgnoreCase(idAsignacionActual)) {
                    pos = i;
                }
            }
        }
        return pos;
    }

    public String cargarAnotacion( String idAsignacionActual, String idProductoActual ){
        int pos;
        String anotacion;
        anotacion = "";
        if( getArrayListAnotaciones() != null ){
            pos = buscarAnotacion(idAsignacionActual,idProductoActual);
            if (pos != -1) {
                anotacion = getArrayListAnotaciones().get(pos).getAnotacion() ;
            }
        }

        return  anotacion;
    }

    public void crearAnotacion(String idAsignacionActual, String idProductoActual, String anotacion ){
        Anotacion anotacionObj;
        anotacionObj = new Anotacion( idAsignacionActual,idProductoActual,anotacion  );
        getArrayListAnotaciones().add(anotacionObj);
   }

    public void guardarAnotacion( String idAsignacionActual, String idProductoActual, String anotacion) {

        if( getArrayListAnotaciones() == null ) {
            setArrayListAnotaciones(new ArrayList<Anotacion>());
            crearAnotacion( idAsignacionActual,idProductoActual, anotacion);
        }else {
            int pos;
            pos = buscarAnotacion(idAsignacionActual, idProductoActual);
            if ( pos != -1 ) {
                getArrayListAnotaciones().get(pos).setAnotacion(anotacion);
            }else{
                crearAnotacion( idAsignacionActual,idProductoActual, anotacion);
            }
        }
    }

    public void eliminarAnotaciones( String idAsignacionActual ){
        if (getArrayListAnotaciones() != null ) {
            for (int i = 0; i < getArrayListAnotaciones().size(); i++) {
                if (getArrayListAnotaciones().get(i).getIdAsignacion().equalsIgnoreCase(idAsignacionActual)) {
                    getArrayListAnotaciones().remove(i);
                }
            }
        }

    }

    public void cancelarAnotacion(String idAsignacionActual, String idProductoActual) {

        if (getArrayListAnotaciones() != null) {
            int pos;
            pos = buscarAnotacion(idAsignacionActual, idProductoActual);
            if (pos != -1) {
                getArrayListAnotaciones().remove(pos);
            }
        }
    }

    public void obtenerOrdenLista( String idSession, String idUsuario ){
        getDbHandler().testConn();
        if (getDbHandler().isConexion() ) {
            setOrdenLista(getDbHandler().obtenerOrdenLista(idSession, idUsuario));
            if( getDbHandler().isConexion()){
                setIdSession(getDbHandler().getIdSession());
            }
        }
    }

    public boolean generarFactura(Context context, Activity activity, String idVentaActual ){
        boolean band;
        band = false;

        getDbHandler().testIntConn( context, activity );

        if ( getDbHandler().isConexion() ) {
            getDbHandler().generarFactura( getIdSession(),idVentaActual );
            if( getDbHandler().isConexion() ){
                setIdSession( getDbHandler().getIdSession() );
                actualizarEstadoVenta( idVentaActual );
                actualizarEstadoAsignacion ( getIdAsignacionActual() );
                band = true;
            }
        }
        return band;
    }

    public void actualizarEstadoVenta( String idVentaActual ) {
        for ( int i = 0; i < getArrayListVentas().size(); i++ ) {
            if( getArrayListVentas().get(i).getIdVenta().equals( idVentaActual ) ){
                actualizarEstadoOrden( getArrayListVentas().get(i).getPedido() );
                getArrayListVentas().get(i).setEstado( false );
            }
        }
    }

    public void actualizarEstadoOrden( Pedido pedido ){

        for ( int i = 0; i < pedido.getArrayListOrdenes().size(); i++ ) {
            pedido.getArrayListOrdenes().get(i).setEstado( false );
        }
    }

    public void actualizarEstadoAsignacion( String idAsignacionActual ){
        for ( int i = 0; i < getArrayListAsignaciones().size(); i++ ) {
            if ( getArrayListAsignaciones().get(i).getIdAsignacion().equals( idAsignacionActual ) ) {
                getArrayListAsignaciones().get(i).setEstado( false );
            }
        }
    }

    public boolean validaOrden(Activity activity, String idVentaActual){

        boolean band;
        band = false;

        if ( idVentaActual != null ) {
           for ( int i = 0; i < getArrayListVentas().size(); i++ ) {
               if ( getArrayListVentas().get(i).getIdVenta().equals( idVentaActual ) ) {
                   if ( getArrayListVentas().get(i).getPedido() != null) {
                       band = true;
                   }
               }
           }
       }

       if ( !band ) {
           activity.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   String msgPedido;
                   msgPedido="No tiene ningun pedido para enviar";
                   Toast.makeText(getBaseContext(), msgPedido, Toast.LENGTH_SHORT).show();
               }
           });
       }

       return  band;
    }


}
