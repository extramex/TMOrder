package com.olonte.tmorder.dto;

/**
 * Created by jsgravenhorst on 9/8/2014.
 */
public class Usuario {
    private String idUsuario;
    private String usuario;
    private String passw;
    private String nombre;
    private String apellido;

    public Usuario(){}

    public Usuario( String idUsuario,String user, String nombre, String apellido, String passw ){
        setIdUsuario( idUsuario );
        setUsuario( user );
        setNombre( nombre );
        setApellido( apellido );
        setPassw( passw );
    }

    public Usuario (String idUsuario, String nombre, String apellido ){
        setIdUsuario( idUsuario );
        setNombre( nombre );
        setApellido( apellido );
    }

    public Usuario (String idUsuario, String user){
        setIdUsuario( idUsuario );
        setUsuario( user );
    }



    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() { return usuario; }

    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassw() { return passw; }

    public void setPassw(String passw) { this.passw = passw; }

    public String toString() { return getUsuario(); }

}
