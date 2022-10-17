package com.example.app_segundoparcial.json;

import java.io.Serializable;
import java.lang.reflect.Array;

public class MyInfo implements Serializable{
    private String usuario;
    private String pass;
    private String correo;
    private Boolean sexo;
    private String fecha;
    private String tel;
    private String[] TipoUsu;
    private String passConfirm;
    private Boolean notificaciones;

    public MyInfo() {
    }
    public Boolean getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Boolean notificaciones) {
        this.notificaciones = notificaciones;
    }

    public String getPassConfirm() {
        return passConfirm;
    }

    public void setPassConfirm(String passConfirm) {
        this.passConfirm = passConfirm;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String[] getTipoUsu() {
        return TipoUsu;
    }

    public void setTipoUsu(String[] tipoUsu) {
        TipoUsu = tipoUsu;
    }
}
