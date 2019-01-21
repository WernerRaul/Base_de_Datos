package com.example.raul.base_de_datos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.raul.base_de_datos.Utilidades.Utilidades;
import com.example.raul.base_de_datos.entidades.Publicadores;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ActivityTarjetasGraficas extends AppCompatActivity {
    
    Spinner spnPublicador;
    ArrayList<Publicadores> publicadoresList;
    ArrayList<String> listaPublicadores;
    Bundle bolsa;
    Map<String,Integer> nombreMap;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas_graficas);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);

        consultarlistaPublicadores();

        spnPublicador = findViewById(R.id.spnPublicadores);
        ArrayAdapter adapter = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, listaPublicadores);
        spnPublicador.setAdapter(adapter);
        spnPublicador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SQLiteDatabase db = conn.getReadableDatabase();
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                        "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion AND " +
                        "tbl_PUBLICADORES.Nombre = '" + spnPublicador.getSelectedItem() + "') INNER JOIN tbl_ACTIVIDAD ON " +
                        "(tbl_ACTIVIDAD.ID_Publicador = tbl_PUBLICADORES.ID_Publicador AND " +
                        "tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsa.get("miMes").toString()+" months'))");
                String[] sqlSelect = {
                        "tbl_ACTIVIDAD.AñoMes",
                        "tbl_ACTIVIDAD.Horas",
                        "tbl_ACTIVIDAD.Revisitas",
                        "tbl_ACTIVIDAD.Estudios"
                };
                String[] argumentos = {bolsa.getString("miCongregacion")};
                Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, null, null, "tbl_CONGREGACIONES.Nombre ASC");
                String titulo[] = {c.getColumnName(0),c.getColumnName(1),c.getColumnName(2),c.getColumnName(3)};

                //para la tabla
                TableLayout tblTabla = findViewById(R.id.tblTabla);
                TableRow row = new TableRow(getBaseContext());
                tblTabla.removeAllViews();
                TextView textView;
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void consultarlistaPublicadores() {

        SQLiteDatabase db = conn.getReadableDatabase();
        Publicadores publicador = null;
        publicadoresList = new ArrayList<Publicadores>();
        //entrega de dato de anterior activity (congregación)
        bolsa=getIntent().getExtras();
        nombreMap = new HashMap<String, Integer>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PUBLICADORES +
                " WHERE ID_Congregacion = "+ bolsa.getInt("miIdCongregacion")+
                " ORDER BY " + Utilidades.TABLA_PUBLICADORES+"."+ Utilidades.CAMPO_Nombre_Publicador, null);

        while (cursor.moveToNext()) {
            publicador = new Publicadores();
            publicador.setNombre(cursor.getString(1));
            nombreMap.put(cursor.getString(1),cursor.getInt(0)); //map para guardar id de publicador que se usará para ingresar a la bd actividad
            publicadoresList.add(publicador);
        }
        db.close();
        obtenerlistaP(); //llamar método

    }

    private void obtenerlistaP() {
        listaPublicadores = new ArrayList<String>();
        listaPublicadores.add("Seleccione");

        for (int i = 0; i < publicadoresList.size(); i++) {
            listaPublicadores.add(publicadoresList.get(i).getNombre());
        }
    }

    public void onClick(View view) {
        Bundle bolsaE = new Bundle();
        bolsaE.putString("nombre",spnPublicador.getSelectedItem().toString());
        bolsaE.putString("miCongregacion",bolsa.getString("miCongregacion"));
        bolsaE.putString("miMes", String.valueOf(bolsa.getInt("miMes")));
        Intent miIntent = new Intent(ActivityTarjetasGraficas.this, ActivityGraficaPublicador.class);
        miIntent.putExtras(bolsaE);
        startActivity(miIntent);
    }
}
