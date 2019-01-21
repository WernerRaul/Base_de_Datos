package com.example.raul.base_de_datos.entidades;

public class Actividad {
    private Integer ID_Actividad;
    private Integer Horas;
    private Integer Revisitas;
    private Integer Estudios;
    private String PAuxiliar;
    private String Observaciones;
    private String AñoMes;
    private Integer ID_Publicador;

    public Actividad(int i, int i1, int i2, int i3, String aTrue, String s, String s1, int i4) {
        this.ID_Actividad = i;
        this.Horas = i1;
        this.Revisitas = i2;
        this.Estudios = i3;
        this.PAuxiliar = aTrue;
        this.Observaciones = s;
        this.AñoMes = s1;
        this.ID_Publicador = i4;
    }

    public Integer getID_Actividad() {
        return ID_Actividad;
    }

    public void setID_Actividad(Integer ID_Actividad) {
        this.ID_Actividad = ID_Actividad;
    }

    public Integer getHoras() {
        return Horas;
    }

    public void setHoras(Integer horas) {
        this.Horas = horas;
    }

    public Integer getRevisitas() {
        return Revisitas;
    }

    public void setRevisitas(Integer revisitas) {
        this.Revisitas = revisitas;
    }

    public Integer getEstudios() {
        return Estudios;
    }

    public void setEstudios(Integer estudios) {
        this.Estudios = estudios;
    }

    public String getPAuxiliar() {
        return PAuxiliar;
    }

    public void setPAuxiliar(String PAuxiliar) {
        this.PAuxiliar = PAuxiliar;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.Observaciones = observaciones;
    }

    public String getMesAño() {
        return AñoMes;
    }

    public void setMesAño(String mesAño) {
        this.AñoMes = mesAño;
    }

    public Integer getID_Publicador() {
        return ID_Publicador;
    }

    public void setID_Publicador(Integer ID_Publicador) {
        this.ID_Publicador = ID_Publicador;
    }
}
