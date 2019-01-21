package com.example.raul.base_de_datos.Utilidades;

public class Utilidades {
    public static final String TABLA_ACTIVIDAD = "tbl_ACTIVIDAD";
    public static final String CAMPO_ID_ACTIVIDAD = "ID_Actividad";
    public static final String CAMPO_Horas = "Horas";
    public static final String CAMPO_Revisitas = "Revisitas";
    public static final String CAMPO_Estudios = "Estudios";
    public static final String CAMPO_PAuxiliar = "PAuxiliar";
    public static final String CAMPO_Observaciones_Actividad = "Observaciones";
    public static final String CAMPO_MesAnio = "Mes/Año";
    public static final String CAMPO_ID_Publicador = "ID_Publicador";

    public static final String CREAR_TABLA_ACTIVIDAD="CREATE TABLE "+TABLA_ACTIVIDAD+" ("
            +CAMPO_ID_ACTIVIDAD+" INTEGER, "+CAMPO_Horas+" INTEGER, "+CAMPO_Revisitas
            +" INTEGER, "+CAMPO_Estudios+" INTEGER, "+CAMPO_PAuxiliar+" TEXT, "
            +CAMPO_Observaciones_Actividad+" TEXT, "+CAMPO_MesAnio+" INTEGER, "+CAMPO_ID_Publicador
            +" INTEGER, PRIMARY KEY "+CAMPO_ID_ACTIVIDAD+")";

    public static final String TABLA_CONGREGACIONES="tbl_CONGREGACIONES";
    public static final String CAMPO_ID_CONGREGACION="Id_CONGREGACION";
    public static final String CAMPO_NOMBRE_CONGREGACION="Nombre";
    public static final String CAMPO_SECCION="Seccion";
    public static final String CAMPO_OBSERVACIONES_CONGREGACION="Observaciones";

    public static final String CREAR_TABLA_CONGREGACIONES="CREATE TABLE "+TABLA_CONGREGACIONES+" ("
            +CAMPO_ID_CONGREGACION+" INTEGER, "+CAMPO_NOMBRE_CONGREGACION+" TEXT, "+CAMPO_SECCION
            +" TEXT, "+CAMPO_OBSERVACIONES_CONGREGACION+" TEXT, PRIMARY KEY "+CAMPO_ID_CONGREGACION+")";

    public static final String TABLA_PUBLICADORES = "tbl_PUBLICADORES";
    public static final String CAMPO_ID_PUBLICADOR = "ID_Publicador";
    public static final String CAMPO_Nombre_Publicador = "Nombre";
    public static final String CAMPO_Direccion = "Direccion";
    public static final String CAMPO_Sexo = "Sexo";
    public static final String CAMPO_Telefono = "Telefono";
    public static final String CAMPO_FechaNacimiento = "FechaNacimiento";
    public static final String CAMPO_FechaBautismo = "FechaBautismo";
    public static final String CAMPO_Anciano = "Anciano";
    public static final String CAMPO_SiervoMinisterial = "SiervoMinisterial";
    public static final String CAMPO_PrecRegular = "PrecRegular";
    public static final String CAMPO_ID_CONGREGACION_PUBLICADOR = "ID_CONGREGACION";
    public static final String CAMPO_Observaciones = "Observaciones";

    public static final String CREAR_TABLA_PUBLICADORES="CREATE TABLE "+TABLA_PUBLICADORES+" ("
            +CAMPO_ID_PUBLICADOR+" INTEGER, "+CAMPO_Nombre_Publicador+" TEXT, "+CAMPO_Direccion
            +" TEXT, "+CAMPO_Sexo+" TEXT, "+CAMPO_Telefono+" INTEGER, "+CAMPO_FechaNacimiento
            +" INTEGER, "+CAMPO_FechaBautismo+" INTEGER, "+CAMPO_Anciano+" TEXT, "
            +CAMPO_SiervoMinisterial+" TEXT, "+CAMPO_PrecRegular+" TEXT, "+CAMPO_ID_CONGREGACION_PUBLICADOR
            +" INTEGER, "+CAMPO_Observaciones+" TEXT, PRIMARY KEY "+CAMPO_ID_PUBLICADOR+")";

    public static final String TABLA_REUNIONES = "tbl_REUNIONES";
    public static final String CAMPO_ID_REUNIONES = "ID_Mes";
    public static final String CAMPO_Mes = "Mes";
    public static final String CAMPO_ReuEntreSemana = "ReuEntreSemana";
    public static final String CAMPO_ReuFinSemana = "ReuFinSemana";
    public static final String CAMPO_ID_CONGREGACION_REUNIONES = "ID_CONGREGACION";
    public static final String CAMPO_Observaciones_Reuniones = "Observaciones";

    public static final String CREAR_TABLA_REUNIONES="CREATE TABLE "+TABLA_REUNIONES+" ("
            +CAMPO_ID_REUNIONES+" INTEGER, "+CAMPO_Mes+" TEXT, "+CAMPO_ReuEntreSemana
            +" INTEGER, "+CAMPO_ReuFinSemana+" INTEGER, "+CAMPO_ID_CONGREGACION_REUNIONES+" INTEGER, "
            +CAMPO_Observaciones_Reuniones+" TEXT, PRIMARY KEY "+CAMPO_ID_REUNIONES+")";

}
