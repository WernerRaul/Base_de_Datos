package com.example.raul.base_de_datos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityConsultaFinal extends AppCompatActivity {


    Bundle bolsa;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_final);

        //entrega de dato de anterior activity (congregación)
        bolsa=getIntent().getExtras();

        //base de datos
        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        //para la consulta (builder.query)
        String [] argumentos = {bolsa.getString("miCongregacion")};

        //para la tabla
        TableLayout tblTabla = findViewById(R.id.tblTabla);
        TableRow row = new TableRow(getBaseContext());
        TextView textView;
        EditText editText;///////////////////////////////////////////////////////////
        //entrega de dato de anterior activity
        //Publicadores y Precursores Regulares
        if (bolsa.getBoolean("rdbPublicyPrecdelaCong")) {
            builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion)");
            String [] sqlSelect = {"tbl_PUBLICADORES.ID_Publicador",
                    "tbl_PUBLICADORES.Nombre",
                    "tbl_PUBLICADORES.PrecRegular",
                    "tbl_PUBLICADORES.Observaciones"};
            Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, null, null, "tbl_PUBLICADORES.Nombre ASC");

            String titulo[] = {c.getColumnName(0),c.getColumnName(1),c.getColumnName(2),c.getColumnName(3)};
            for(int i=0; i<4; i++){
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15,15,15,15);
                textView.setText(titulo[i]);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER); //
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18); //
                textView.setTypeface(Typeface.SERIF, Typeface.BOLD);
                row.addView(textView);
            }
            tblTabla.addView(row);

            while (c.moveToNext()) {
                String cadena[] = {c.getString(0),c.getString(1),c.getString(2),c.getString(3)};
                row = new TableRow(getBaseContext());
                for(int i=0; i<4; i++){
                    textView = new TextView(getBaseContext());
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(15,15,15,15);
                    textView.setText(cadena[i]);
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER); //
                    row.addView(textView);
                }
                tblTabla.addView(row);
            }
            c.close();

        //entrega de dato de anterior activity
        //Precursores Auxiliares Actividad
        }else if (bolsa.getBoolean("rdbPrecAuxAct")) {

            builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                    "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion) " +
                    "INNER JOIN tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador " +
                    "AND tbl_ACTIVIDAD.PAuxiliar = 'true' " +
                    "AND tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsa.get("miMes").toString()+" months'))");
            String [] sqlSelect = {"tbl_PUBLICADORES.Nombre",
                    "tbl_ACTIVIDAD.Horas",
                    "tbl_ACTIVIDAD.Revisitas",
                    "tbl_ACTIVIDAD.Estudios",
                    "tbl_ACTIVIDAD.AñoMes"};
            Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, null, null, null);

            String titulo[] = {c.getColumnName(0),c.getColumnName(1),c.getColumnName(2),
                    c.getColumnName(3), c.getColumnName(4)};
            for(int i=0; i<5; i++){
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15,15,15,15);
                textView.setText(titulo[i]);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER); //
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18); //
                textView.setTypeface(Typeface.SERIF, Typeface.BOLD); //
                row.addView(textView);
            }
            tblTabla.addView(row);

            while (c.moveToNext()) {
                String cadena[] = {c.getString(0),c.getString(1),c.getString(2),c.getString(3),
                        c.getString(4)};
                row = new TableRow(getBaseContext());
                for(int i=0; i<5; i++){
                    textView = new TextView(getBaseContext());
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(15,15,15,15);
                    textView.setText(cadena[i]);
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER); //
                    row.addView(textView);
                }
                tblTabla.addView(row);
            }
            c.close();

        //entrega de dato de anterior activity
        //Precursores Regulares Actividad
        }else if (bolsa.getBoolean("rdbPrecRegAct")) {

            builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                    "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion " +
                    "AND tbl_PUBLICADORES.PrecRegular = 'true') " +
                    "INNER JOIN tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador " +
                    "AND tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsa.get("miMes").toString()+" months'))");
            String [] sqlSelect = {"tbl_PUBLICADORES.Nombre",
                    "round(avg(tbl_ACTIVIDAD.Horas),1) as PromHoras",
                    "round(avg(tbl_ACTIVIDAD.Revisitas),1) as PromRevisitas",
                    "round(avg(tbl_ACTIVIDAD.Estudios),1) as PromEstudios"};
            Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, "tbl_PUBLICADORES.Nombre", null, "avg(tbl_ACTIVIDAD.Horas) ASC");

            String titulo[] = {c.getColumnName(0),c.getColumnName(1),c.getColumnName(2),
                    c.getColumnName(3)};
            for(int i=0; i<4; i++){
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15,15,15,15);
                textView.setText(titulo[i]);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER); //
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18); //
                textView.setTypeface(Typeface.SERIF, Typeface.BOLD); //
                row.addView(textView);
            }
            tblTabla.addView(row);

            while (c.moveToNext()) {
                String cadena[] = {c.getString(0),c.getString(1),c.getString(2),c.getString(3)};
                row = new TableRow(getBaseContext());
                for(int i=0; i<4; i++){
                    textView = new TextView(getBaseContext());
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(15,15,15,15);
                    textView.setText(cadena[i]);
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER); //
                    row.addView(textView);
                }
                tblTabla.addView(row);
            }
            c.close();

        //entrega de dato de anterior activity
        //Baja Actividad
        }else if (bolsa.getBoolean("rdbBajaAct")) {

            builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                    "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion) " +
                    "INNER JOIN tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador " +
                    "AND tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsa.get("miMes").toString()+" months'))");
            String [] sqlSelect = {"tbl_PUBLICADORES.Nombre",
                    "round(AVG(tbl_ACTIVIDAD.Horas),1) as PromHoras"};
            Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, "tbl_PUBLICADORES.Nombre", "AVG(tbl_ACTIVIDAD.Horas) < 8", "AVG(tbl_ACTIVIDAD.Horas) ASC");

            String titulo[] = {c.getColumnName(0),c.getColumnName(1)};
            for(int i=0; i<2; i++){
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15,15,15,15);
                textView.setText(titulo[i]);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER); //
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18); //
                textView.setTypeface(Typeface.SERIF, Typeface.BOLD); //
                row.addView(textView);
            }
            tblTabla.addView(row);

            while (c.moveToNext()) {
                String cadena[] = {c.getString(0),c.getString(1)};
                row = new TableRow(getBaseContext());
                for(int i=0; i<2; i++){
                    textView = new TextView(getBaseContext());
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(15,15,15,15);
                    textView.setText(cadena[i]);
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER); //
                    row.addView(textView);
                }
                tblTabla.addView(row);
            }
            c.close();

        //entrega de dato de anterior activity
        //Irregulares
        }else if (bolsa.getBoolean("rdbIrregulares")) {

            builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                    "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion) " +
                    "INNER JOIN tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador " +
                    "AND tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsa.get("miMes").toString()+" months') " +
                    "AND tbl_ACTIVIDAD.Horas = 0)");
            String [] sqlSelect = {"tbl_PUBLICADORES.Nombre",
                    "tbl_ACTIVIDAD.AñoMes"};
            Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, null, null, "tbl_PUBLICADORES.Nombre ASC");

            String titulo[] = {c.getColumnName(0),c.getColumnName(1)};
            for(int i=0; i<2; i++){
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15,15,15,15);
                textView.setText(titulo[i]);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER); //
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18); //
                textView.setTypeface(Typeface.SERIF, Typeface.BOLD); //
                row.addView(textView);
            }
            tblTabla.addView(row);

            while (c.moveToNext()) {
                String cadena[] = {c.getString(0),c.getString(1)};
                row = new TableRow(getBaseContext());
                for(int i=0; i<2; i++){
                    textView = new TextView(getBaseContext());
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(15,15,15,15);
                    textView.setText(cadena[i]);
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER); //
                    row.addView(textView);
                }
                tblTabla.addView(row);
            }
            c.close();

        //entrega de dato de anterior activity
        //No hacen Revisitas
        }else if (bolsa.getBoolean("rdbNoHacenRevisitas")) {
            //codigo
            builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                    "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion) " +
                    "INNER JOIN tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador " +
                    "AND tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsa.get("miMes").toString()+" months'))");
            String [] sqlSelect = {"tbl_PUBLICADORES.Nombre"};
            Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, "tbl_PUBLICADORES.Nombre", "tbl_ACTIVIDAD.Revisitas = 0", "tbl_PUBLICADORES.Nombre ASC");

            String titulo[] = {c.getColumnName(0)};
            for(int i=0; i<1; i++){
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15,15,15,15);
                textView.setText(titulo[i]);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER); //
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18); //
                textView.setTypeface(Typeface.SERIF, Typeface.BOLD); //
                row.addView(textView);
            }
            tblTabla.addView(row);

            while (c.moveToNext()) {
                String cadena[] = {c.getString(0)};
                row = new TableRow(getBaseContext());
                for(int i=0; i<1; i++){
                    textView = new TextView(getBaseContext());
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(15,15,15,15);
                    textView.setText(cadena[i]);
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER); //
                    row.addView(textView);
                }
                tblTabla.addView(row);
            }
            c.close();

        //entrega de dato de anterior activity
        //No conducen Estudios
        }else if (bolsa.getBoolean("rdbNoCondEstBib")) {
            //codigo
            builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                    "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion) " +
                    "INNER JOIN tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador " +
                    "AND tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsa.get("miMes").toString()+" months'))");
            String [] sqlSelect = {"tbl_PUBLICADORES.Nombre"};
            Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, "tbl_PUBLICADORES.Nombre", "tbl_ACTIVIDAD.Estudios = 0", "tbl_PUBLICADORES.Nombre ASC");

            String titulo[] = {c.getColumnName(0)};
            for(int i=0; i<1; i++){
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15,15,15,15);
                textView.setText(titulo[i]);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER); //
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18); //
                textView.setTypeface(Typeface.SERIF, Typeface.BOLD); //
                row.addView(textView);
            }
            tblTabla.addView(row);

            while (c.moveToNext()) {
                String cadena[] = {c.getString(0)};
                row = new TableRow(getBaseContext());
                for(int i=0; i<1; i++){
                    textView = new TextView(getBaseContext());
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(15,15,15,15);
                    textView.setText(cadena[i]);
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER); //
                    row.addView(textView);
                }
                tblTabla.addView(row);
            }
            c.close();

        //entrega de dato de anterior activity
        //Si conducen Estudios
        }else if (bolsa.getBoolean("rdbSiCondEstBib")) {

            builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                    "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion) " +
                    "INNER JOIN tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador " +
                    "AND tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsa.get("miMes").toString()+" months') " +
                    "AND tbl_ACTIVIDAD.Estudios > 0)");
            String [] sqlSelect = {"tbl_PUBLICADORES.Nombre",
                    "round(AVG(tbl_ACTIVIDAD.Estudios),1) as PromEstudios"};
            Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, "tbl_PUBLICADORES.Nombre", null, "AVG(tbl_ACTIVIDAD.Estudios) ASC");

            String titulo[] = {c.getColumnName(0),c.getColumnName(1)};
            for(int i=0; i<2; i++){
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15,15,15,15);
                textView.setText(titulo[i]);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER); //
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18); //
                textView.setTypeface(Typeface.SERIF, Typeface.BOLD); //
                row.addView(textView);
            }
            tblTabla.addView(row);

            while (c.moveToNext()) {
                String cadena[] = {c.getString(0),c.getString(1)};
                row = new TableRow(getBaseContext());
                for(int i=0; i<2; i++){
                    textView = new TextView(getBaseContext());
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(15,15,15,15);
                    textView.setText(cadena[i]);
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER); //
                    row.addView(textView);
                }
                tblTabla.addView(row);
            }
            c.close();

        //entrega de dato de anterior activity
        //Analisis de varones
        }else if (bolsa.getBoolean("rdbAnalisisDeVarones")) {

            builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                    "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion " +
                    "AND tbl_PUBLICADORES.Sexo = 'Hombre') " +
                    "INNER JOIN tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador " +
                    "AND tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsa.get("miMes").toString()+" months'))");
            String [] sqlSelect = {"tbl_PUBLICADORES.Nombre",
                    "strftime('%Y','now')- strftime('%Y',tbl_PUBLICADORES.FechaNacimiento) as Edad",
                    "strftime('%Y','now')- strftime('%Y',tbl_PUBLICADORES.FechaBautismo) as Bautizado",
                    "tbl_PUBLICADORES.Anciano",
                    "tbl_PUBLICADORES.SiervoMinisterial",
                    "tbl_PUBLICADORES.PrecRegular",
                    "round(AVG(tbl_ACTIVIDAD.Horas),1) as PromHoras",
                    "round(AVG(tbl_ACTIVIDAD.Revisitas),1) as PromRevisitas",
                    "round(AVG(tbl_ACTIVIDAD.Estudios),1) as PromEstudios"};
            String groupBy = "tbl_PUBLICADORES.Nombre, " +
                "tbl_PUBLICADORES.Anciano, " +
                "tbl_PUBLICADORES.SiervoMinisterial,  " +
                "tbl_PUBLICADORES.PrecRegular,  " +
                "tbl_PUBLICADORES.FechaNacimiento, " +
                "tbl_PUBLICADORES.FechaBautismo,  " +
                "tbl_PUBLICADORES.Sexo";
            Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, groupBy, null, "AVG(tbl_ACTIVIDAD.Horas) DESC");

            String titulo[] = {c.getColumnName(0),c.getColumnName(1),c.getColumnName(2),c.getColumnName(3),
                    c.getColumnName(4),c.getColumnName(5),c.getColumnName(6),
                    c.getColumnName(7),c.getColumnName(8)};
            for(int i=0; i<9; i++){
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15,15,15,15);
                textView.setText(titulo[i]);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER); //
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18); //
                textView.setTypeface(Typeface.SERIF, Typeface.BOLD); //
                row.addView(textView);
            }

            tblTabla.addView(row);
            while (c.moveToNext()) {
                String cadena[] = {c.getString(0),c.getString(1),c.getString(2),c.getString(3),
                        c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8)};

                row = new TableRow(getBaseContext());
                for(int i=0; i<9; i++){
                    textView = new TextView(getBaseContext());
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(15,15,15,15);
                    textView.setText(cadena[i]);
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER); //
                    row.addView(textView);
                }
                tblTabla.addView(row);
            }
            c.close();
        }
    }
}
