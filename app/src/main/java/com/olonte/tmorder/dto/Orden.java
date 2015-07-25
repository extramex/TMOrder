package com.olonte.tmorder.dto;

/**
 * Created by jsgravenhorst on 10/1/2014.
 */
public class Orden {
    private String idPedido;
    private String idProducto;
    private int    cantidad;
    private String valor;
    private String descripcion;
    private String anotacion;
    private boolean estado;

    public Orden(){
    }

    public Orden(String idProducto, int cantidad, String valor, String descripcion, String anotacion){
        setIdProducto( idProducto );
        setCantidad( cantidad );
        setValor( valor );
        setDescripcion( descripcion );
        setAnotacion( anotacion );
        setEstado( true );
    }

    public Orden( String idPedido, String idProducto, int cantidad, String valor, String descripcion, String anotacion){
        setIdPedido( idPedido );
        setIdProducto( idProducto );
        setCantidad( cantidad );
        setValor( valor );
        setDescripcion( descripcion );
        setAnotacion( anotacion );
        setEstado( true );
    }



    public String getIdPedido() { return idPedido;  }

    public void setIdPedido(String idPedido) {  this.idPedido = idPedido;  }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAnotacion() {
        return anotacion;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

    public boolean isEstado() {  return estado;   }

    public void setEstado(boolean estado) {  this.estado = estado; }

}
