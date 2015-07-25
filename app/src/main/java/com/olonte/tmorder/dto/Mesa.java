package com.olonte.tmorder.dto;

/**
 * Created by jsgravenhorst on 9/9/2014.
 */
public class Mesa {
    private String idMesa;
    private String descripcion;
    private String estado;

    public Mesa( String idMesa, String descripcion, String estado ){
        setIdMesa( idMesa );
        setDescripcion( descripcion );
        setEstado( estado );
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
