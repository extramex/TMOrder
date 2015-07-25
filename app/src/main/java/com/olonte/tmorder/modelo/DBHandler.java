package com.olonte.tmorder.modelo;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.olonte.tmorder.R;
import com.olonte.tmorder.dto.Anotacion;
import com.olonte.tmorder.helper.AlertDialogManager;
import com.olonte.tmorder.helper.AssetsPropertyReader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by jsgravenhorst on 9/6/2014.
 */
public class DBHandler {

    private static  Activity activity;

    private AssetsPropertyReader assetsPropertyReader;
    private Properties properties;
    private AlertDialogManager alertDialogManager;
    private ProgressDialog progressDialog;

    private boolean conexion;
    private Context context;
    private String respuesta;
    private String errorBD;

    private JSONParser jsonParser;
    private JSONArray  jsonArray;
    private JSONObject jsonObject;
    private JSONObject _jsonObject;

    private String idSession;
    private String idPedido;
    private com.olonte.tmorder.dto.Venta   venta;
    private ArrayList<com.olonte.tmorder.dto.Usuario> arrayListUsuarios;
    private ArrayList<com.olonte.tmorder.dto.Asignacion> arrayListAsignaciones;
    private ArrayList<com.olonte.tmorder.dto.Categoria> arrayListCategorias;
    private ArrayList<com.olonte.tmorder.dto.SubCategoria> arrayListSubCategorias;
    private ArrayList<com.olonte.tmorder.dto.Venta> arrayListVentas;
    private ArrayList<com.olonte.tmorder.dto.Producto> arrayListProductos;
    private ArrayList<com.olonte.tmorder.dto.Anotacion> arrayListAnotaciones;
    private String ordenLista;

    public DBHandler(Context context, Activity activity){
        setContext( context );
        setActivity( activity );
        setAssetsPropertyReader( new AssetsPropertyReader( getContext()) );
        setProperties( getAssetsPropertyReader().getProperties( "SettingFile.properties" ));
        setAlertDialogManager( new AlertDialogManager() );
        setConexion( false );
     }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() { return activity;  }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public JSONArray getJsonArray() { return jsonArray;  }

    public void setJsonArray(JSONArray jsonArray) { this.jsonArray = jsonArray; }

    public JSONObject getJsonObject() { return jsonObject;  }

    public void setJsonObject(JSONObject jsonObject) { this.jsonObject = jsonObject; }

    public JSONParser getJsonParser() {  return jsonParser;  }

    public void setJsonParser(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public JSONObject get_jsonObject() {  return _jsonObject;   }

    public void set_jsonObject(JSONObject _jsonObject) { this._jsonObject = _jsonObject; }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public String getErrorBD() {
        return errorBD;
    }

    public void setErrorBD(String errorBD) {
        this.errorBD = errorBD;
    }

    public boolean isConexion() {
        return conexion;
    }

    public void setConexion(boolean conexion) {
        this.conexion = conexion;
    }

    public ArrayList<com.olonte.tmorder.dto.Usuario> getArrayListUsuarios() { return arrayListUsuarios; }

    public void setArrayListUsuarios(ArrayList<com.olonte.tmorder.dto.Usuario> arrayListUsuarios) { this.arrayListUsuarios = arrayListUsuarios;  }

    public ArrayList<com.olonte.tmorder.dto.Asignacion> getArrayListAsignaciones() { return arrayListAsignaciones;  }

    public void setArrayListAsignaciones(ArrayList<com.olonte.tmorder.dto.Asignacion> arrayListAsignaciones) { this.arrayListAsignaciones = arrayListAsignaciones;  }

    public ArrayList<com.olonte.tmorder.dto.Categoria> getArrayListCategorias() { return arrayListCategorias;  }

    public void setArrayListCategorias(ArrayList<com.olonte.tmorder.dto.Categoria> arrayListCategorias) { this.arrayListCategorias = arrayListCategorias;  }

    public ArrayList<com.olonte.tmorder.dto.SubCategoria> getArrayListSubCategorias() { return arrayListSubCategorias;  }

    public void setArrayListSubCategorias(ArrayList<com.olonte.tmorder.dto.SubCategoria> arrayListSubCategorias) { this.arrayListSubCategorias = arrayListSubCategorias; }

    public ArrayList<com.olonte.tmorder.dto.Producto> getArrayListProductos() { return arrayListProductos;  }

    public ArrayList<com.olonte.tmorder.dto.Venta> getArrayListVentas() { return arrayListVentas;  }

    public void setArrayListVentas(ArrayList<com.olonte.tmorder.dto.Venta> arrayListVentas) { this.arrayListVentas = arrayListVentas; }

    public void setArrayListProductos(ArrayList<com.olonte.tmorder.dto.Producto> arrayListProductos) {  this.arrayListProductos = arrayListProductos; }

    public ArrayList<Anotacion> getArrayListAnotaciones() { return arrayListAnotaciones;  }

    public void setArrayListAnotaciones(ArrayList<Anotacion> arrayListAnotaciones) { this.arrayListAnotaciones = arrayListAnotaciones; }

    public com.olonte.tmorder.dto.Venta getVenta() { return venta;  }

    public void setVenta(com.olonte.tmorder.dto.Venta venta) {
        this.venta = venta;
    }

    public void setVenta( String  idVenta, String idAsignacion ) { this.venta = new com.olonte.tmorder.dto.Venta( idVenta, idAsignacion );  }

    public void setVenta ( String idVenta, String idAsignacion, String idPedido, ArrayList<com.olonte.tmorder.dto.Orden> arrayListOrdenes ) { this.venta = new  com.olonte.tmorder.dto.Venta( idVenta, idAsignacion, idPedido, arrayListOrdenes ); }

    public String getIdPedido() { return idPedido;  }

    public void setIdPedido(String idPedido) { this.idPedido = idPedido;  }

    public String getOrdenLista() { return ordenLista; }

    public void setOrdenLista(String ordenLista) { this.ordenLista = ordenLista;  }

    public Properties getProperties() { return properties;  }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public AlertDialogManager getAlertDialogManager() {
        return alertDialogManager;
    }

    public void setAlertDialogManager(AlertDialogManager alertDialogManager) { this.alertDialogManager = alertDialogManager;  }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) { this.progressDialog = progressDialog; }

    public AssetsPropertyReader getAssetsPropertyReader() {
        return assetsPropertyReader;
    }

    public void setAssetsPropertyReader(AssetsPropertyReader assetsPropertyReader) { this.assetsPropertyReader = assetsPropertyReader;  }

    public void testIntConn(){ testConn(); }

    public void testIntConn(Context context, Activity activity) {
        setContext( context );
        setActivity( activity );
        testConn();
    }

    public void testConn() {
        ConnectionDetector connectionDetector;

        connectionDetector = new ConnectionDetector(getContext());

        if (!connectionDetector.isConnectingToInternet()) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString(R.string.tag_error_con), getActivity().getString(R.string.tag_msj_cont_int) );
                }
            });
            setConexion( false );


        } else {
            setConexion( true );
        }

    }

    public  ArrayList<com.olonte.tmorder.dto.Usuario> cargarUsuarios() {

        try {
            new Usuario().execute().get();
        } catch (ExecutionException exc) {
            exc.printStackTrace();
        } catch (InterruptedException intEx){
            intEx.printStackTrace();
        }

        return getArrayListUsuarios();
    }

    public ArrayList<com.olonte.tmorder.dto.Asignacion> cargarAsignaciones( String idSession, String idUsuario){

        try {
            new Asignacion().execute(idSession,idUsuario).get();
        } catch (ExecutionException exc){
            exc.printStackTrace();
        } catch (InterruptedException intEx){
            intEx.printStackTrace();
        }

        return getArrayListAsignaciones();
    }

    public ArrayList<com.olonte.tmorder.dto.Categoria> cargarCategorias( String idSession ){

        try {
            new Categoria().execute(idSession).get();
        } catch (ExecutionException exc){
            exc.printStackTrace();
        } catch (InterruptedException intEx){
            intEx.printStackTrace();
        }

        return getArrayListCategorias();

    }

    public ArrayList<com.olonte.tmorder.dto.SubCategoria> cargarSubCategorias(String idSession){

        try {
            new SubCategoria().execute(idSession).get();
        } catch (ExecutionException exc){
            exc.printStackTrace();
        } catch (InterruptedException intEx){
            intEx.printStackTrace();
        }

        return getArrayListSubCategorias();

    }

    public com.olonte.tmorder.dto.Venta generaVenta(String idSession, String idAsignacion){

        try {
            new Venta().execute(idSession, idAsignacion).get();
        } catch (ExecutionException exc){
            exc.printStackTrace();
        } catch (InterruptedException intEx){
            intEx.printStackTrace();
        }

        return getVenta();
    }

    public ArrayList<com.olonte.tmorder.dto.Producto> cargarListaProductos(String idSession, String idSubcategoria){
        try {
            new Producto().execute(idSession, idSubcategoria).get();
        } catch (ExecutionException exc){
            exc.printStackTrace();
        } catch (InterruptedException intEx){
            intEx.printStackTrace();
        }

        return getArrayListProductos();
    }

    public void generarPedido(String idSession, String pedido){
        try {
            new Pedido().execute(idSession,pedido).get();
        } catch (ExecutionException exc){
            exc.printStackTrace();
        } catch (InterruptedException intEx){
            intEx.printStackTrace();
        }
    }

    public void generarModPedido(String idSession, String pedido) {
        try {
            new ModificarPedido().execute(idSession,pedido).get();
        } catch (ExecutionException exc){
            exc.printStackTrace();
        } catch (InterruptedException intEx){
            intEx.printStackTrace();
        }
    }

    public String obtenerOrdenLista(String idSession, String idUsuario ){

        try {
            new Orden().execute(idSession, idUsuario).get();
        } catch (ExecutionException exc) {
            exc.printStackTrace();
        } catch (InterruptedException intEx){
            intEx.printStackTrace();
        }

        return getOrdenLista();
    }

    public void generarFactura(String idSession, String idVenta) {
        try {
            new Factura().execute(idSession,idVenta).get();
        } catch (ExecutionException exc){
            exc.printStackTrace();
        } catch (InterruptedException intEx){
            intEx.printStackTrace();
        }
    }

    class Usuario extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage( getActivity().getString( R.string.tag_carga_usu ) );
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            setConexion( false );

            setJsonParser( new JSONParser() );

            set_jsonObject(getJsonParser().makeHttpRequest( getActivity().getString( R.string.tag_servidor ) +   getActivity().getString( R.string.tag_url_usuarios),   getActivity().getString( R.string.tag_method ) ) );

            if ( get_jsonObject() != null ) {
                try {

                    setJsonObject( null );

                    setRespuesta( get_jsonObject().getString(  getActivity().getString( R.string.tag_success ) ) );

                    if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_exito ) ) ) {

                        setConexion( true );

                        setArrayListUsuarios( new ArrayList<com.olonte.tmorder.dto.Usuario>());
                        com.olonte.tmorder.dto.Usuario usuario;
                        setJsonArray( get_jsonObject().getJSONArray( getActivity().getString( R.string.tag_consulta ) ) );
                        setIdSession( get_jsonObject().getString( getActivity().getString( R.string.tag_id_session ) ) );

                        for ( int i = 0; i < getJsonArray().length(); i++ ){

                            if ( i == 0 ){
                                usuario = new com.olonte.tmorder.dto.Usuario(  getActivity().getString( R.string.tag_id_usu_df ),
                                                                               getActivity().getString( R.string.tag_usu_df )  ) ;

                                getArrayListUsuarios().add(usuario );
                            }
                            usuario = new com.olonte.tmorder.dto.Usuario( getJsonArray().getJSONObject(i).getString( getActivity().getString( R.string.tag_id_usuario ) ),
                                                                          getJsonArray().getJSONObject(i).getString( getActivity().getString( R.string.tag_usuario ) ),
                                                                          getJsonArray().getJSONObject(i).getString( getActivity().getString( R.string.tag_nom_usuario ) ),
                                                                          getJsonArray().getJSONObject(i).getString( getActivity().getString( R.string.tag_apell_usuario ) ),
                                                                          getJsonArray().getJSONObject(i).getString( getActivity().getString( R.string.tag_pass_usuario ) ) );
                            getArrayListUsuarios().add(usuario );
                        }
                    } else if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_error ) ) ) {

                        setErrorBD( get_jsonObject().getString( getActivity().getString( R.string.tag_error_bd  ) ) );
                        setConexion( false );
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_msj_bd ), getErrorBD());
                            }
                        });
                        progressDialog.dismiss();
                    }
                } catch (JSONException exj) {
                    exj.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_json ), getActivity().getString( R.string.tag_error_json_usu ) );
                        }
                    });
                    progressDialog.dismiss();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_exc ),  getActivity().getString( R.string.tag_error_exc_usu ) );
                        }
                    });

                    progressDialog.dismiss();
                }
            } else {
                setConexion(false);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_con_serv ), getActivity().getString( R.string.tag_error_serv_log_usu ) );
                    }
                });
                progressDialog.dismiss();
            }
            return null;
        } /// end method doInBackground()

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    } // end class Usuario

    class Asignacion  extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            setConexion(false);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_id_session ), strings[0] ) );
            params.add( new BasicNameValuePair(  getActivity().getString( R.string.tag_id_usuario ), strings[1] ) );

            setJsonParser( new JSONParser() );

            set_jsonObject(getJsonParser().makeHttpRequest( getActivity().getString(R.string.tag_servidor ) + getActivity().getString(R.string.tag_url_asignaciones ), getActivity().getString(R.string.tag_method ), params));

            if ( get_jsonObject() != null ) {

                try {

                    setJsonObject( null );

                    setRespuesta( get_jsonObject().getString( getActivity().getString( R.string.tag_success ) ) );

                    if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_exito ) ) ) {

                        setJsonArray( get_jsonObject().getJSONArray( getActivity().getString( R.string.tag_consulta ) ) );
                        setIdSession( get_jsonObject().getString( getActivity().getString( R.string.tag_id_session ) ) );

                        if ( getJsonArray().length() > 0 ) {

                            setConexion( true );

                            setArrayListAsignaciones( new ArrayList<com.olonte.tmorder.dto.Asignacion>() );
                            JSONObject jsonObjectAsignacion;
                            com.olonte.tmorder.dto.Asignacion asignacion;
                            String anotacionDf;
                            anotacionDf = "";

                            for ( int i = 0; i < getJsonArray().length(); i++ ) { // Inicio for arreglo Asignaciones

                                jsonObjectAsignacion = getJsonArray().getJSONObject( i );

                                asignacion = new com.olonte.tmorder.dto.Asignacion(  jsonObjectAsignacion.getString( getActivity().getString( R.string.tag_id_asignacion ) ),
                                                                                     jsonObjectAsignacion.getString( getActivity().getString( R.string.tag_id_usuario ) ),
                                                                                     jsonObjectAsignacion.getString( getActivity().getString( R.string.tag_id_mesa ) ),
                                                                                     jsonObjectAsignacion.getString( getActivity().getString( R.string.tag_id_est_asig ) ) );

                                getArrayListAsignaciones().add( asignacion );

                                /** Validar si existe una venta previamente creada */
                                if ( !jsonObjectAsignacion.getString( getActivity().getString( R.string.tag_id_vta ) ).equals( getActivity().getString( R.string.tag_id_vta_df ) )  ){ // Inicio if validacion Venta
                                      if ( i == 0 ) {
                                          setArrayListVentas( new ArrayList<com.olonte.tmorder.dto.Venta>() );
                                      }
                                     /** Validar si existe un pedido previamente creado */
                                    if ( !jsonObjectAsignacion.getString( getActivity().getString( R.string.tag_id_pedido ) ).equals( getActivity().getString( R.string.tag_id_pedido_df ) ) ) { // Inicio if validacion Pedido

                                        /** Se crea la orden perteneciente al pedido */
                                        JSONArray jsonArrayOrden;
                                        JSONObject jsonObjectOrden;

                                        jsonArrayOrden = jsonObjectAsignacion.getJSONArray( getActivity().getString( R.string.tag_arreg_ord ) );

                                        ArrayList<com.olonte.tmorder.dto.Orden> arrayListOrdenes;
                                        arrayListOrdenes = new ArrayList<com.olonte.tmorder.dto.Orden>();

                                        /** Se obtienen las ordenes creadas*/
                                        for ( int j = 0; j < jsonArrayOrden.length(); j++ ) { // Inicio for arreglo Ordenes

                                            jsonObjectOrden = jsonArrayOrden.getJSONObject(j);

                                            com.olonte.tmorder.dto.Orden orden;

                                            orden = new com.olonte.tmorder.dto.Orden( jsonObjectOrden.getString( getActivity().getString( R.string.tag_id_pedido ) ),
                                                                                      jsonObjectOrden.getString( getActivity().getString( R.string.tag_id_producto ) ),
                                                                                      Integer.parseInt( jsonObjectOrden.getString( getActivity().getString( R.string.tag_cantidad_ord ) ) ),
                                                                                      jsonObjectOrden.getString( getActivity().getString( R.string.tag_valor_ord ) ),
                                                                                      jsonObjectOrden.getString( getActivity().getString( R.string.tag_desc_ord ) ),
                                                                                      jsonObjectOrden.getString( getActivity().getString( R.string.tag_anotacion_ord ) ) ) ;
                                            arrayListOrdenes.add( orden );
                                            /** Se valida si existe una anotacion para el producto */
                                            if  ( !jsonObjectOrden.getString( getActivity().getString( R.string.tag_anotacion_ord ) ).equals( anotacionDf ) ) {

                                                if ( i == 0 ) {
                                                     setArrayListAnotaciones( new ArrayList<com.olonte.tmorder.dto.Anotacion>() );
                                                 }

                                                 com.olonte.tmorder.dto.Anotacion anotacion;
                                                 anotacion = new com.olonte.tmorder.dto.Anotacion( jsonObjectAsignacion.getString( getActivity().getString( R.string.tag_id_asignacion ) ),
                                                                                                   jsonObjectOrden.getString( getActivity().getString( R.string.tag_id_producto ) ) ,
                                                                                                   jsonObjectOrden.getString(  getActivity().getString( R.string.tag_anotacion_ord ) ) );
                                                 getArrayListAnotaciones().add( anotacion );
                                            }

                                        } // Fin for arreglo Ordenes
                                        /** Se crea la venta junto con el pedido y la orden asociada */
                                        setVenta( getJsonArray().getJSONObject(i).getString( getActivity().getString( R.string.tag_id_vta ) ),
                                                  jsonObjectAsignacion.getString( getActivity().getString( R.string.tag_id_asignacion ) ),
                                                  getJsonArray().getJSONObject(i).getString( getActivity().getString( R.string.tag_id_pedido ) ),
                                                  arrayListOrdenes );

                                     }else { /** Existe una venta previamente creada pero no tiene pedido  */ // Fin if validacion Pedido
                                        setVenta( getJsonArray().getJSONObject(i).getString( getActivity().getString( R.string.tag_id_vta ) ) ,
                                                  jsonObjectAsignacion.getString( getActivity().getString( R.string.tag_id_asignacion ) ) );
                                    }
                                    getArrayListVentas().add( getVenta() );
                                } // Fin if validacion Venta

                             } // Fin for arreglo Asignaciones
                        }

                    } else if (getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_error ) ) ) {
                        setErrorBD( get_jsonObject().getString( getActivity().getString( R.string.tag_error_bd  ) ) );
                        setConexion(false);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_msj_bd ) , getErrorBD());
                            }
                        });
                        progressDialog.dismiss();
                    }

                }catch (JSONException exj) {
                    exj.printStackTrace();
                    setConexion( false );
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_json ), getActivity().getString( R.string.tag_error_jsn_asig ) );
                        }
                    });

                    progressDialog.dismiss();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    setConexion( false );
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(),  getActivity().getString( R.string.tag_error_exc ), getActivity().getString( R.string.tag_error_exc_asig ) );
                        }
                    });
                     progressDialog.dismiss();
                }
            }else {
                setConexion(false);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAlertDialogManager().showAlertDialog( getContext(),   getActivity().getString( R.string.tag_error_con_serv ), getActivity().getString( R.string.tag_error_serv_asig ) );
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    } // end calss Asignacion

    class Categoria extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            setConexion(false);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_id_session ),strings[ 0 ] ) );
            setJsonParser( new JSONParser() );

            set_jsonObject( getJsonParser().makeHttpRequest( getActivity().getString(R.string.tag_servidor ) + getActivity().getString( R.string.tag_url_categorias ), getActivity().getString( R.string.tag_method ), params ) );

            if ( get_jsonObject() != null ) {

                try {

                    setJsonObject( null );

                    setRespuesta( get_jsonObject().getString(  getActivity().getString( R.string.tag_success ) ) );

                    if ( getRespuesta().equalsIgnoreCase(  getActivity().getString( R.string.tag_exito ) ) ) {

                        setJsonArray( get_jsonObject().getJSONArray( getActivity().getString( R.string.tag_consulta ) ) );
                        setIdSession( get_jsonObject().getString( getActivity().getString( R.string.tag_id_session ) ) );

                        if ( getJsonArray().length() > 0 ) {

                            setConexion(true);

                            ArrayList<com.olonte.tmorder.dto.Categoria> arrayListCategorias;

                            arrayListCategorias =  new ArrayList<com.olonte.tmorder.dto.Categoria>();
                            JSONObject jsonObject;
                            com.olonte.tmorder.dto.Categoria categoria;

                            for ( int i = 0; i < getJsonArray().length(); i++ ) {

                                jsonObject = getJsonArray().getJSONObject( i );

                                categoria = new com.olonte.tmorder.dto.Categoria(  jsonObject.getString( getActivity().getString( R.string.tag_id_catg ) ),
                                                                           jsonObject.getString( getActivity().getString( R.string.tag_desc_catg ) ) );
                                arrayListCategorias.add( categoria );

                            }

                            setArrayListCategorias( arrayListCategorias );

                        }

                    }else if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_error ) ) ) {
                        setErrorBD( get_jsonObject().getString( getActivity().getString( R.string.tag_error_bd  ) ) );
                        setConexion( false );
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_msj_bd ), getErrorBD() );
                            }
                        });
                        progressDialog.dismiss();
                    }

                } catch (JSONException exj) {
                    exj.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_json ), getActivity().getString( R.string.tag_error_jsn_cat ) );
                        }
                    });
                    progressDialog.dismiss();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_exc ), getActivity().getString( R.string.tag_error_exc_cat ) );
                        }
                    });
                    progressDialog.dismiss();
                }
            } else {
                setConexion( false );
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAlertDialogManager().showAlertDialog( getContext(),  getActivity().getString( R.string.tag_error_con_serv ), getActivity().getString( R.string.tag_error_serv_cat ) );
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    } // end class Categoria

    class SubCategoria extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            setConexion(false);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_id_session ),strings[ 0 ] ) );

            setJsonParser( new JSONParser() );

            set_jsonObject(getJsonParser().makeHttpRequest( getActivity().getString(R.string.tag_servidor ) + getActivity().getString(R.string.tag_url_subcategorias ),getActivity().getString(R.string.tag_method ), params));

            if ( get_jsonObject() != null ) {

                try {

                    setJsonObject( null );

                    setRespuesta( get_jsonObject().getString( getActivity().getString( R.string.tag_success ) ) );

                    if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_exito ) ) ) {

                        setJsonArray( get_jsonObject().getJSONArray( getActivity().getString( R.string.tag_consulta ) ) );
                        setIdSession( get_jsonObject().getString( getActivity().getString( R.string.tag_id_session ) ) );

                        if ( getJsonArray().length() > 0 ) {

                            setConexion(true);

                            ArrayList<com.olonte.tmorder.dto.SubCategoria> arrayListSubCategorias;

                            arrayListSubCategorias =  new ArrayList<com.olonte.tmorder.dto.SubCategoria>();
                            JSONObject jsonObject;
                            com.olonte.tmorder.dto.SubCategoria subCategoria;

                            for ( int i = 0; i < getJsonArray().length(); i++ ) {

                                jsonObject = getJsonArray().getJSONObject( i );

                                subCategoria = new com.olonte.tmorder.dto.SubCategoria(  jsonObject.getString( getActivity().getString( R.string.tag_id_sub_catg ) ),
                                        jsonObject.getString( getActivity().getString( R.string.tag_id_catg ) ),
                                        jsonObject.getString(  getActivity().getString( R.string.tag_desc_sub_catg ) ) );
                                arrayListSubCategorias.add( subCategoria );

                            }
                            setArrayListSubCategorias( arrayListSubCategorias );
                        }

                    }else if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_error )  ) ) {
                        setErrorBD( get_jsonObject().getString( getActivity().getString( R.string.tag_error_bd  ) ) );
                        setConexion( false );
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_msj_bd ), getErrorBD() );
                            }
                        });
                        progressDialog.dismiss();
                    }
                } catch (JSONException exj) {
                    exj.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_json ), getActivity().getString( R.string.tag_error_jsn_sub_cat ) );
                        }
                    });
                    progressDialog.dismiss();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_exc ), getActivity().getString( R.string.tag_error_exc_sub_cat ) );
                        }
                    });
                    progressDialog.dismiss();
                }
            } else {
                setConexion( false );
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAlertDialogManager().showAlertDialog( getContext(),  getActivity().getString( R.string.tag_error_con_serv ), getActivity().getString( R.string.tag_error_serv_sub_cat ) );
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    } // end class SubCategoria

    class Producto extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage( getActivity().getString( R.string.tag_carga_productos ) );
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            setConexion(false);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(  getActivity().getString( R.string.tag_id_session ), strings[ 0 ] ) );
            params.add(new BasicNameValuePair(  getActivity().getString( R.string.tag_id_sub_catg ), strings [ 1 ] ) ) ;

            setJsonParser( new JSONParser() );

            set_jsonObject(getJsonParser().makeHttpRequest( getActivity().getString(R.string.tag_servidor ) + getActivity().getString(R.string.tag_url_productos ), getActivity().getString(R.string.tag_method ), params ) );

            if ( get_jsonObject() != null ) {

                try {

                    setJsonObject( null );

                    setRespuesta( get_jsonObject().getString( getActivity().getString( R.string.tag_success ) ) );

                    if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_exito ) ) ) {

                        setJsonArray( get_jsonObject().getJSONArray( getActivity().getString( R.string.tag_consulta ) ) );
                        setIdSession( get_jsonObject().getString( getActivity().getString( R.string.tag_id_session ) ) );

                        if ( getJsonArray().length() > 0 ) {

                            setConexion(true);

                            ArrayList<com.olonte.tmorder.dto.Producto> arrayListProductos;

                            arrayListProductos =  new ArrayList<com.olonte.tmorder.dto.Producto>();
                            JSONObject jsonObject;
                            com.olonte.tmorder.dto.Producto producto;

                            for ( int i = 0; i < getJsonArray().length(); i++ ) {

                                jsonObject = getJsonArray().getJSONObject( i );

                                producto = new com.olonte.tmorder.dto.Producto(  jsonObject.getString( getActivity().getString( R.string.tag_id_producto ) ),
                                                                                 jsonObject.getString( getActivity().getString( R.string.tag_desc_prd ) ),
                                                                                 Integer.parseInt(jsonObject.getString( getActivity().getString( R.string.tag_cant_prd ) ) ),
                                                                                 Integer.parseInt(jsonObject.getString( getActivity().getString( R.string.tag_valor_prd ) ) ) );
                                arrayListProductos.add( producto );
                            }
                            setArrayListProductos( arrayListProductos );
                        }
                    }else if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_error ) ) ) {
                        setErrorBD( get_jsonObject().getString( getActivity().getString( R.string.tag_error_bd  ) ) );
                        setConexion( false );
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_msj_bd ), getErrorBD() );
                            }
                        });
                    }

                } catch (JSONException exj) {
                    exj.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_json ), getActivity().getString( R.string.tag_erro_json_prd ) );
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_exc ),  getActivity().getString( R.string.tag_error_exc_prd ) );
                        }
                    });
                }
            }else {
                setConexion( false );
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_con_serv ),  getActivity().getString( R.string.tag_error_serv_prd ) );
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    } // end class Producto

    class Venta extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage( getActivity().getString( R.string.tag_gen_venta ) );
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            setConexion( false );

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_id_session ), strings[0] ) );
            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_id_asignacion ), strings[1] ) );

            setJsonParser( new JSONParser() );

            set_jsonObject(getJsonParser().makeHttpRequest( getActivity().getString(R.string.tag_servidor ) + getActivity().getString(R.string.tag_url_gen_venta ), getActivity().getString(R.string.tag_method ), params));

            if ( get_jsonObject() != null ) {
                try {

                    setJsonObject( null );

                    setRespuesta( get_jsonObject().getString( getActivity().getString( R.string.tag_success ) ) );

                    if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_exito ) ) ) {

                        setConexion( true );

                        setIdSession( get_jsonObject().getString( getActivity().getString( R.string.tag_id_session ) ) );

                        setVenta( get_jsonObject().getString( getActivity().getString( R.string.tag_consulta ) ) ,strings[ 1 ] );

                    } else if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_error ) ) ) {

                        setErrorBD( get_jsonObject().getString( getActivity().getString( R.string.tag_error_bd  ) ) );
                        setConexion( false );
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_msj_bd ), getErrorBD());
                            }
                        });
                    }
                } catch (JSONException exj) {
                    exj.printStackTrace();
                    setConexion( false );
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(),  getActivity().getString( R.string.tag_error_json ), getActivity().getString( R.string.tag_error_jsn_vta ) );
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    setConexion( false );
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(),  getActivity().getString( R.string.tag_error_exc ), getActivity().getString( R.string.tag_error_exc_vta ) );
                        }
                    });
                }
            } else {
                setConexion( false );
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAlertDialogManager().showAlertDialog( getContext(),  getActivity().getString( R.string.tag_error_con_serv ), getActivity().getString( R.string.tag_error_serv_vta ) );
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }// end class Venta

    class Pedido extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage( getActivity().getString( R.string.tag_gen_pedido ) );
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            setConexion( false );

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_id_session ), strings[0] ) );
            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_pedido ), strings[1] ) );

            setJsonParser( new JSONParser() );

            set_jsonObject(getJsonParser().makeHttpRequest( getActivity().getString(R.string.tag_servidor ) + getActivity().getString(R.string.tag_url_gen_pedido ),  getActivity().getString(R.string.tag_method ), params ) );

            if ( get_jsonObject() != null ) {

                try {

                    setRespuesta( get_jsonObject().getString( getActivity().getString( R.string.tag_success ) ) );

                    if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_exito ) ) ) {

                        setConexion( true );

                        setIdSession( get_jsonObject().getString( getActivity().getString( R.string.tag_id_session ) ) );

                        setIdPedido( get_jsonObject().getString( getActivity().getString( R.string.tag_consulta ) ) );

                    } else if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_error ) ) ) {

                        setErrorBD( get_jsonObject().getString( getActivity().getString( R.string.tag_error_bd ) ) );

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_msj_bd ), getErrorBD());
                            }
                        });

                        setConexion( false );

                        progressDialog.dismiss();
                    }
                } catch (JSONException exj) {
                    exj.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_json ), getActivity().getString( R.string.tag_error_jsn_ped ) );
                        }
                    });

                    progressDialog.dismiss();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_exc ), getActivity().getString( R.string.tag_error_exc_ped )  );
                }
            });

                    progressDialog.dismiss();
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_con_serv ), getActivity().getString( R.string.tag_error_serv_ped ) );
                    }
                });
                setConexion( false );
                progressDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    } // end class Pedido

    class ModificarPedido extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage( getActivity().getString( R.string.tag_mod_ped ) );
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            setConexion( false );

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_id_session ), strings[0] ) );
            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_pedido ), strings[1] ) );

            setJsonParser( new JSONParser() );

            set_jsonObject(getJsonParser().makeHttpRequest( getActivity().getString(R.string.tag_servidor ) +  getActivity().getString( R.string.tag_url_gen_mod_ped ) ,  getActivity().getString( R.string.tag_method), params ) );

            if ( get_jsonObject() != null ) {

                try {

                    setRespuesta( get_jsonObject().getString( getActivity().getString( R.string.tag_success ) ) );

                    if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_exito ) ) ) {

                        setConexion( true );
                        setIdSession( get_jsonObject().getString( getActivity().getString( R.string.tag_id_session ) ) );
                        setIdPedido(get_jsonObject().getString( getActivity().getString( R.string.tag_consulta ) ) );

                    } else if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_error ) ) ) {

                        setErrorBD( get_jsonObject().getString( getActivity().getString( R.string.tag_error_bd ) ) );

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_msj_bd ), getErrorBD());
                            }
                        });

                        setConexion( false );

                        progressDialog.dismiss();
                    }
                } catch (JSONException exj) {
                    exj.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_json ), getActivity().getString( R.string.tag_error_jsn_mod_ped ) );
                        }
                    });

                    progressDialog.dismiss();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_exc ), getActivity().getString( R.string.tag_error_exc_mod_ped ) );
                        }
                    });

                    progressDialog.dismiss();
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_con_serv ),getActivity().getString( R.string.tag_error_serv_mod_ped ) );
                    }
                });
                setConexion( false );
                //progressDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    } // end class ModificarPedido

    class Orden extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            setConexion( false );

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add( new BasicNameValuePair(  getActivity().getString( R.string.tag_id_session ), strings[0] ) );
            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_id_usuario), strings[1] ) );

            setJsonParser( new JSONParser() );

            set_jsonObject( getJsonParser().makeHttpRequest( getActivity().getString( R.string.tag_servidor ) + getActivity().getString(R.string.tag_url_orden ), getActivity().getString(R.string.tag_method ), params ) );

            if ( get_jsonObject() != null ) {

                try {

                    setJsonObject( null );

                    setRespuesta( get_jsonObject().getString(  getActivity().getString( R.string.tag_success ) ) );

                    if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_exito ) ) ) {

                        setConexion(true);
                        JSONObject jsonObject;
                        String ordenLista;
                        ordenLista = getActivity().getString( R.string.tag_ord_list );

                        setJsonArray( get_jsonObject().getJSONArray( getActivity().getString( R.string.tag_consulta ) ) );
                        setIdSession( get_jsonObject().getString( getActivity().getString( R.string.tag_id_session ) ) );

                        for ( int i = 0; i < getJsonArray().length(); i++ ) {
                            jsonObject = getJsonArray().getJSONObject(i);
                            ordenLista = ordenLista + " " + jsonObject.getString( getActivity().getString( R.string.tag_ord ) );
                        }
                        setOrdenLista(ordenLista);
                    } else if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_no_ord ) ) ) {
                        setConexion( true );
                        setOrdenLista( getActivity().getString( R.string.tag_no_ord ) );
                    }  else if ( getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_error ) ) ) {
                        setErrorBD( get_jsonObject().getString(getActivity().getString( R.string.tag_error_bd ) ) );
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_msj_bd ), getErrorBD() );
                            }
                        });

                        setConexion( false );
                    }
                } catch (JSONException exj) {
                    exj.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_json ),  getActivity().getString( R.string.tag_error_jsn_ord ) );
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_exc ), getActivity().getString( R.string.tag_error_exc_ord ) );
                        }
                    });
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAlertDialogManager().showAlertDialog( getContext(), getActivity().getString( R.string.tag_error_con_serv ), getActivity().getString( R.string.tag_error_serv_ord ) );
                    }
                });
                setConexion( false );
            }

            return null;
        }
    }

    class Factura extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             progressDialog = new ProgressDialog(getActivity());
             progressDialog.setMessage( getActivity().getString( R.string.tag_gen_fact ) );
             progressDialog.setIndeterminate(false);
             progressDialog.setCancelable(false);
             progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

           	params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_id_session ),  strings[0] ) );
            params.add( new BasicNameValuePair( getActivity().getString( R.string.tag_id_vta ), strings[1] ) );

            setJsonParser( new JSONParser() );

            set_jsonObject(getJsonParser().makeHttpRequest( getActivity().getString(R.string.tag_servidor ) +  getActivity().getString(R.string.tag_url_factura ), getActivity().getString(R.string.tag_method ), params));
            if ( get_jsonObject() != null ) {

                try {

                    setJsonObject(null);
                    setRespuesta(get_jsonObject().getString( getActivity().getString( R.string.tag_success ) ) );

                    if (getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_exito ) ) ) {
                         setIdSession(get_jsonObject().getString( getActivity().getString( R.string.tag_id_session ) ) );
                         setConexion(true);
                   } else if (getRespuesta().equalsIgnoreCase( getActivity().getString( R.string.tag_error ) ) ) {
                        setErrorBD(get_jsonObject().getString(  getActivity().getString( R.string.tag_error_bd ) ) );
                        setConexion(false);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_msj_bd ), getErrorBD());
                            }
                        });
                    }
                }catch (JSONException exj) {
                    exj.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_json ),  getActivity().getString( R.string.tag_error_jsn_fact ));
                        }
                    });
                }catch (Exception ex) {
                    ex.printStackTrace();
                    setConexion(false);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlertDialogManager().showAlertDialog(getContext(), getActivity().getString( R.string.tag_error_exc ), getActivity().getString( R.string.tag_error_exc_fact ) );
                        }
                    });
                }
            }else {
                setConexion( false );
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAlertDialogManager().showAlertDialog( getContext(),  getActivity().getString( R.string.tag_error_con_serv ), getActivity().getString( R.string.tag_error_serv_fact ) );
                    }
                });
            }


             return null;
          }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    } // end class Factura
}
