package com.olonte.tmorder.dto;

/**
 * Created by jsgravenhorst on 9/9/2014.
 */
public class Categoria {
    private String idCategoria;
    private String descripcion;

    public Categoria( String idCategoria, String descripcion){
        setIdCategoria( idCategoria );
        setDescripcion( descripcion );
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
