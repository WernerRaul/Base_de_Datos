package com.example.raul.base_de_datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ConexionSQLiteHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "ICA-04";
    private static final int DATABASE_VERSION = 1;

    public ConexionSQLiteHelper(Context context, String name,
                                SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }
}
/*public class ConexionSQLiteHelper extends SQLiteOpenHelper{

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(Utilidades.CREAR_TABLA_ACTIVIDAD);
//        db.execSQL(Utilidades.CREAR_TABLA_CONGREGACIONES);
//        db.execSQL(Utilidades.CREAR_TABLA_PUBLICADORES);
//        db.execSQL(Utilidades.CREAR_TABLA_REUNIONES);
        db.execSQL("CREATE TABLE tbl_ACTIVIDAD (ID_Actividad INTEGER PRIMARY KEY AutoIncrement, Horas INTEGER, Revisitas INTEGER, Estudios INTEGER, PAuxiliar TEXT, Observaciones TEXT, AñoMes INTEGER, ID_Publicador INTEGER)");
        db.execSQL("CREATE TABLE tbl_CONGREGACIONES (ID_Congregacion INTEGER PRIMARY KEY AutoIncrement, Nombre TEXT, Seccion TEXT, Observaciones TEXT)");
        db.execSQL("CREATE TABLE tbl_PUBLICADORES (ID_Publicador INTEGER PRIMARY KEY AutoIncrement, Nombre TEXT, Direccion TEXT, Sexo TEXT, Telefono INTEGER, FechaNacimiento INTEGER, FechaBautismo INTEGER, Anciano TEXT, SiervoMinisterial TEXT, PrecRegular TEXT, ID_Congregacion INTEGER, Observaciones TEXT)");
        db.execSQL("CREATE TABLE tbl_REUNIONES (ID_Mes INTEGER PRIMARY KEY AutoIncrement, Mes INTEGER, ReuEntreSemana INTEGER, ReuFinSemana INTEGER, ID_Congregacion INTEGER, Observaciones TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}*/
