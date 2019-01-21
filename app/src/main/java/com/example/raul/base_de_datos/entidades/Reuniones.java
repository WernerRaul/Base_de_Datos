package com.example.raul.base_de_datos.entidades;

public class Reuniones {
    private Integer ID_Mes;
    private String Mes;
    private Integer ReuEntreSemana;
    private Integer ReuFinSemana;
    private Integer ID_CONGREGACION;
    private String Observaciones;

    public Reuniones() {
        this.ID_Mes = ID_Mes;
        this.Mes = Mes;
        this.ReuEntreSemana = ReuEntreSemana;
        this.ReuFinSemana = ReuFinSemana;
        this.ID_CONGREGACION = ID_CONGREGACION;
        this.Observaciones = Observaciones;
    }

    public Integer getID_Mes() {
        return ID_Mes;
    }

    public void setID_Mes(Integer ID_Mes) {
        this.ID_Mes = ID_Mes;
    }

    public String getMes() {
        return Mes;
    }

    public void setMes(String mes) {
        this.Mes = mes;
    }

    public Integer getReuEntreSemana() {
        return ReuEntreSemana;
    }

    public void setReuEntreSemana(Integer reuEntreSemana) {
        this.ReuEntreSemana = reuEntreSemana;
    }

    public Integer getReuFinSemana() {
        return ReuFinSemana;
    }

    public void setReuFinSemana(Integer reuFinSemana) {
        this.ReuFinSemana = reuFinSemana;
    }

    public Integer getID_CONGREGACION() {
        return ID_CONGREGACION;
    }

    public void setID_CONGREGACION(Integer ID_CONGREGACION) {
        this.ID_CONGREGACION = ID_CONGREGACION;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.Observaciones = observaciones;
    }
}
