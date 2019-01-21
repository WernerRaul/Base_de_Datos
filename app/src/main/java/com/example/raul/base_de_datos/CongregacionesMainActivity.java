package com.example.raul.base_de_datos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.raul.base_de_datos.Utilidades.Utilidades;
import com.example.raul.base_de_datos.entidades.Congregaciones;

import java.util.ArrayList;
//Esta clase ahora va a ser usada para interfaz de ingreso de datos
public class CongregacionesMainActivity extends AppCompatActivity {

    Spinner spnCongregacion;
    ArrayList<String> listaCongregaciones;
    ArrayList<Congregaciones> congregacionesList;
    ConexionSQLiteHelper conn;
    String mCongSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congregaciones_main);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        spnCongregacion = findViewById(R.id.spnAsistencia);

        consultarlistaCongregaciones(); //llamar método

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (this,android.R.layout.simple_spinner_item,listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);
        spnCongregacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position !=0){
                    mCongSeleccionada=congregacionesList.get(position-1).getnombre();
                    Button boton = (Button) findViewById(R.id.btnIngresarAsistencia);
                    boton.setEnabled(true);
                }else{
                    Button boton = (Button) findViewById(R.id.btnIngresarAsistencia);
                    boton.setEnabled(false);
//                    Toast.makeText(getApplicationContext(),"Seleccione una Congregación para ingresar asistencia a las reuniones",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void consultarlistaCongregaciones() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Congregaciones congregacion = null;
        congregacionesList = new ArrayList<Congregaciones>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_CONGREGACIONES, null);
        while (cursor.moveToNext()){
            congregacion = new Congregaciones();
            congregacion.setnombre(cursor.getString(1));
            congregacionesList.add(congregacion);
        }

        db.close();
        obtenerlista(); //llamar método
    }

    private void obtenerlista() {
        listaCongregaciones= new ArrayList<String>();
        listaCongregaciones.add("Seleccione");
        
        for(int i = 0; i < congregacionesList.size(); i++){
            listaCongregaciones.add(congregacionesList.get(i).getnombre());
        }
    }

    public void onClick(View view) {
                Intent miIntent=null;

        switch (view.getId()){
            case R.id.btnAñadirCongregacion:
                miIntent = new Intent(CongregacionesMainActivity.this, ActivityAnadirCongregaciones.class);
                break;
            case R.id.btnIngresarAsistencia:
                miIntent = new Intent(CongregacionesMainActivity.this, ActivityAsistenciaReuniones.class);
                Bundle bolsa = new Bundle();
                bolsa.putString("nombreKey", mCongSeleccionada );
                bolsa.putInt("idKey", spnCongregacion.getSelectedItemPosition()-1);
                miIntent.putExtras(bolsa);
                break;
            case R.id.btnAnadirPublicador:
                miIntent = new Intent(CongregacionesMainActivity.this ,ActivityAnadirPublicador.class);
                break;
            case R.id.btnIngresarActividad:
                miIntent = new Intent(CongregacionesMainActivity.this ,ActivityActividadPublicador.class);
                break;
            case R.id.btnActualizarPublicador:
                miIntent = new Intent(CongregacionesMainActivity.this, ActivityActualizarPublicador.class);
                break;
            case R.id.btnActualizarActividad:
                miIntent = new Intent(CongregacionesMainActivity.this, ActivityActualizarActividadPublicador.class);
                break;

        }
        if (miIntent!=null){
            startActivity(miIntent);
        }

    }

}
