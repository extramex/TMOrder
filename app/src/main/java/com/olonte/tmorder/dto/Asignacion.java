package com.olonte.tmorder.dto;

/**
 * Created by jsgravenhorst on 9/12/2014.
 */
public class Asignacion {
    private String idAsignacion;
    private String idUsuario;
    private String idMesa;
    private boolean estado;


    public  Asignacion(String idAsignacion, String idUsuario, String idMesa, String idestado ) {
        setIdAsignacion( idAsignacion );
        setIdUsuario( idUsuario );
        setIdMesa( idMesa );
        if ( idestado.equals( "1" ) ) {
            setEstado( false );
        }else{
            setEstado( true );
        }

    }

    public String getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(String idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


}
