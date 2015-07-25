package com.olonte.tmorder.dto;

import java.util.ArrayList;

/**
 * Created by jsgravenhorst on 9/15/2014.
 */
public class Venta {
    private String  idVenta;
    private String  idAsignacion;
    private Pedido  pedido;
    private boolean estado;
    private float   valor;

    public Venta(){
    }

   public Venta(String idVenta, String idAsignacion){
       setIdVenta( idVenta );
       setIdAsignacion( idAsignacion );
       setEstado( true);
   }

   public Venta(String idVenta, String idAsignacion, String idpedido, ArrayList<Orden> arrayListOrdenes ){
       setIdVenta( idVenta );
       setIdAsignacion( idAsignacion );
       setEstado( true);
       setPedido( idpedido, idVenta, arrayListOrdenes );
   }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(String idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public Pedido getPedido() { return pedido; }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setPedido( ArrayList<Orden> arrayListOrdenes) { this.pedido = new Pedido(  this.getIdVenta(), arrayListOrdenes );  }

    public void setPedido( String idPedido, String idVenta, ArrayList<Orden> arrayListOrdenes ){ this.pedido = new Pedido( idPedido, idVenta, arrayListOrdenes );  }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }


}
