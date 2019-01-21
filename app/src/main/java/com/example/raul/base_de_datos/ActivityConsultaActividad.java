package com.example.raul.base_de_datos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.raul.base_de_datos.Utilidades.Utilidades;
import com.example.raul.base_de_datos.entidades.Congregaciones;

import java.util.ArrayList;

public class ActivityConsultaActividad extends AppCompatActivity {

    Spinner spnCongregacion;
    Spinner spnMes;
    ArrayList<String> milista;

    ArrayList<String> listaCongregaciones;
    ArrayList<Congregaciones> congregacionesList;
    ConexionSQLiteHelper conn;
    String mCongSeleccionada;
    Integer mIdCongregacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_actividad);

                //onCreate(savedInstanceState);
        //setContentView(R.layout.activity_consulta_actividad);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        spnCongregacion = findViewById(R.id.spnCongregacion);

        consultarlistaCongregaciones(); //llamar método

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);
        spnCongregacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position !=0){
                    mCongSeleccionada=congregacionesList.get(position-1).getnombre();
                    //Congregación seleccionada en el spin se busca su id
                    SQLiteDatabase db = conn.getReadableDatabase();
                    Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CONGREGACIONES +
                            " WHERE " + Utilidades.CAMPO_NOMBRE_CONGREGACION + " = '" + mCongSeleccionada +
                            "'", null);
                    cursor.moveToNext();
                    mIdCongregacion=cursor.getInt(0);
                    db.close();

                }else{
                    Toast.makeText(getApplicationContext(),"Seleccione una Congregación a consultar",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnMes = findViewById(R.id.spnMes);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.meses_array));
        spnMes.setAdapter(adapter);
    }

    private void consultarlistaCongregaciones() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Congregaciones congregacion = null;
        congregacionesList = new ArrayList<Congregaciones>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CONGREGACIONES, null);
        while (cursor.moveToNext()) {
            congregacion = new Congregaciones();
            congregacion.setnombre(cursor.getString(1));
            congregacionesList.add(congregacion);
        }
        db.close();
        obtenerlista(); //llamar método
    }

    private void obtenerlista() {
        listaCongregaciones = new ArrayList<String>();
        listaCongregaciones.add("Seleccione");

        for (int i = 0; i < congregacionesList.size(); i++) {
            listaCongregaciones.add(congregacionesList.get(i).getnombre());
        }
    }

    public void onClickConsultaActividadPublicadores(View view) {
        try {
            Intent miIntent = new Intent(ActivityConsultaActividad.this, ActivityConsultaActividadPublicadores.class);
            Bundle bolsa = new Bundle();
            bolsa.putString("miCongregacion",mCongSeleccionada);
            bolsa.putInt("miIdCongregacion",mIdCongregacion);
            bolsa.putInt("miMes", Integer.parseInt(spnMes.getSelectedItem().toString()));
            miIntent.putExtras(bolsa);
            startActivity(miIntent);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Seleccione una Congregación y establezca el número de meses correcto", Toast.LENGTH_LONG).show();
        }

    }

}

