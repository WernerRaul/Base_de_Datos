package com.example.raul.base_de_datos.entidades;

public class Publicadores {
    private Integer ID_Publicador;
    private String Nombre;
    private String Direccion;
    private String Sexo;
    private Integer Telefono;
    private String FechaNacimiento;
    private String FechaBautismo;
    private String Anciano;
    private String SiervoMinisterial;
    private String PrecRegular;
    private Integer ID_CONGREGACION;
    private String Observaciones;

    public Publicadores(){

        this.ID_Publicador = ID_Publicador;

        this.Nombre = Nombre;

        this.Direccion = Direccion;

        this.Sexo = Sexo;

        this.Telefono = Telefono;

        this.FechaNacimiento = FechaNacimiento;

        this.FechaBautismo = FechaBautismo;

        this.Anciano = Anciano;

        this.SiervoMinisterial = SiervoMinisterial;

        this.PrecRegular = PrecRegular;

        this.ID_CONGREGACION = ID_CONGREGACION;

        this.Observaciones = Observaciones;

    }

    public Integer getID_Publicador() {
        return ID_Publicador;
    }

    public void setID_Publicador(Integer ID_Publicador) {
        this.ID_Publicador = ID_Publicador;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        this.Direccion = direccion;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        this.Sexo = sexo;
    }

    public Integer getTelefono() {
        return Telefono;
    }

    public void setTelefono(Integer telefono) {
        this.Telefono = telefono;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.FechaNacimiento = fechaNacimiento;
    }

    public String getFechaBautismo() {
        return FechaBautismo;
    }

    public void setFechaBautismo(String fechaBautismo) {
        this.FechaBautismo = fechaBautismo;
    }

    public String getAnciano() {
        return Anciano;
    }

    public void setAnciano(String anciano) {
        this.Anciano = anciano;
    }

    public String getSiervoMinisterial() {
        return SiervoMinisterial;
    }

    public void setSiervoMinisterial(String siervoMinisterial) {
        this.SiervoMinisterial = siervoMinisterial;
    }

    public String getPrecRegular() {
        return PrecRegular;
    }

    public void setPrecRegular(String precRegular) {
        this.PrecRegular = precRegular;
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

    public Integer get_id_pub (String nombre_pub){
        if (Nombre == nombre_pub){
            return ID_Publicador;
        }else {
            return null;
        }
    }
}
