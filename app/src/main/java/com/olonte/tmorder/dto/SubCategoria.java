package com.olonte.tmorder.dto;

/**
 * Created by jsgravenhorst on 9/9/2014.
 */
public class SubCategoria {
    private String idSubCategoria;
    private String idCategoria;
    private String descripcion;

    public SubCategoria( String idSubCategoria, String idCategoria, String descripcion ) {
        setIdSubCategoria( idSubCategoria );
        setIdCategoria( idCategoria );
        setDescripcion( descripcion );
    }

    public String getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(String idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
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
