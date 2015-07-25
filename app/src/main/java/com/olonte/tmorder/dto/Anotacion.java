package com.olonte.tmorder.dto;

/**
 * Created by jsgravenhorst on 9/30/2014.
 */
public class Anotacion {
    private String idAsignacion;
    private String idProducto;
    private String anotacion;

    public Anotacion(){}

    public Anotacion(  String idAsignacion, String idProducto, String anotacion ){
        setIdAsignacion( idAsignacion );
        setIdProducto( idProducto );
        setAnotacion( anotacion );
    }

    public String getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(String idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getAnotacion() {
        return anotacion;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

}
