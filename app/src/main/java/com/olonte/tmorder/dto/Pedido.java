package com.olonte.tmorder.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jsgravenhorst on 9/15/2014.
 */
public class Pedido {
    private String idPedido;
    private String idVenta;
    private ArrayList<Orden> arrayListOrdenes;

    public Pedido(){}

    public Pedido( String idVenta, ArrayList<Orden> arrayListOrdenes ){
        setIdPedido( "0" );
        setIdVenta(idVenta);
        setArrayListOrdenes( arrayListOrdenes );
    }

    public Pedido( String idPedido, String idVenta, ArrayList<Orden> arrayListOrdenes ){
        setIdPedido( idPedido );
        setIdVenta(idVenta);
        setArrayListOrdenes( arrayListOrdenes );
    }


    public String getIdPedido() { return idPedido;  }

    public void setIdPedido(String idPedido) { this.idPedido = idPedido;  }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public ArrayList<Orden> getArrayListOrdenes() {
        return arrayListOrdenes;
    }

    public void setArrayListOrdenes(ArrayList<Orden> arrayListOrdenes) {  this.arrayListOrdenes = arrayListOrdenes; }

   public JSONArray toJSONArray(ArrayList<Orden> arrayListOrden) {
        JSONArray jsonArrayOrden;
        JSONObject jsonOrden;

        jsonArrayOrden = new JSONArray();

        for (int i = 0; i < arrayListOrden.size(); i++) {
            jsonOrden = new JSONObject();
            try {
                jsonOrden.put( "idproducto", arrayListOrden.get(i).getIdProducto() );
                jsonOrden.put( "cantidad", arrayListOrden.get(i).getCantidad() );
                jsonOrden.put( "valor", arrayListOrden.get(i).getValor() );
                jsonOrden.put( "anotacion", arrayListOrden.get(i).getAnotacion() );

                jsonArrayOrden.put( jsonOrden );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonArrayOrden;
    }

    public String toJSON() {
        JSONObject jsonOrden;
        jsonOrden = new JSONObject();

        try {
            jsonOrden.put("idpedido", getIdPedido());
            jsonOrden.put( "idventa", getIdVenta()  );
            jsonOrden.put( "orden", toJSONArray(getArrayListOrdenes()) );
            return jsonOrden.toString();
        }   catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }


}
