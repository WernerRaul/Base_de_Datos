package com.example.raul.base_de_datos.entidades;

public class Congregaciones {
    private Integer id_congregacion;
    private String nombre;
    private String seccion;
    private String observaciones;

    public Congregaciones() {

        this.id_congregacion = id_congregacion;

        this.nombre = nombre;

        this.seccion = seccion;

        this.observaciones = observaciones;

    }

    public Integer getId_congregacion() {
        return id_congregacion;
    }

    public void setid_congregacion(Integer id_congregacion) {
        this.id_congregacion = id_congregacion;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public String getseccion() {
        return seccion;
    }

    public void setseccion(String seccion) {
        this.seccion = seccion;
    }

    public String getobservaciones() {
        return observaciones;
    }

    public void setobservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}