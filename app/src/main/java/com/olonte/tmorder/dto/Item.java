package com.olonte.tmorder.dto;

/**
 * Created by jsgravenhorst on 11/9/2014.
 */
public class Item {
    private String descripcion;
    private String cantidad;
    private int valor;

    public Item(){
    }

    public Item(String descripcion, String cantidad, int valor) {
        setDescripcion(descripcion);
        setCantidad(cantidad);
        setValor(valor);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
