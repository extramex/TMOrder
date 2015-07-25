package com.olonte.tmorder.dto;

/**
 * Created by jsgravenhorst on 9/15/2014.
 */
public class Producto {
    private String idProducto;
    private String descripcion;
    private int    cantidad;
    private int    valor;
    private int    number;

    public Producto( String idProducto, String descripcion, int cantidad, int valor ){
        setIdProducto( idProducto );
        setDescripcion( descripcion );
        setCantidad( cantidad );
        setValor( valor );
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
