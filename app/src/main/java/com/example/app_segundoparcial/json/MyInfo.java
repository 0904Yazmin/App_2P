package com.example.app_segundoparcial.json;

import java.lang.reflect.Array;

public class MyInfo {
    private String usuario;
    private String pass;
    private String correo;
    private Boolean sexo;
    private String fecha;
    private Integer tel;
    private Array TipoUsu;

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public Array getTipoUsu() {
        return TipoUsu;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public void setTipoUsu(Array tipoUsu) {
        TipoUsu = tipoUsu;
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
}
