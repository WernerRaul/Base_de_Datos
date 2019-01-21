package com.example.raul.base_de_datos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raul.base_de_datos.Utilidades.Utilidades;
import com.example.raul.base_de_datos.entidades.Congregaciones;
import com.example.raul.base_de_datos.entidades.Publicadores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityActividadPublicador extends AppCompatActivity {

    Spinner spnCongregacion;
    Spinner spnPublicador;
    EditText edtFecha;
    EditText edtHoras;
    EditText edtRevisitas;
    EditText edtEstudios;
    CheckBox chkPrecursorAuxiliar;
    EditText edtObservaciones;
    ArrayList<String> listaCongregaciones;
    ArrayList<Congregaciones> congregacionesList;
    ArrayList<String> listaPublicadores;
    ArrayList<Publicadores> publicadoresList;
    Map<String,Integer> nombreMap;
    TextView lblUltimoRegistro;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_publicador);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        nombreMap = new HashMap<String, Integer>();

        //llenar spinner con los nombres de las congregaciones
        spnCongregacion = findViewById(R.id.spnCongregacion);
        consultarlistaCongregaciones(); //llamar método
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);

        //al seleccionar una congregacion
        spnCongregacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incluirlista(); //llamar método
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //método directo de onCreate//////////////////////////////
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

    //método directo de onCreate//////////////////////////////
    private void incluirlista() {
        //método para incluir la lista de publicadores de la congregacion deseada
        spnPublicador = findViewById(R.id.spnPublicador);

        consultarlistaPublicadores(); //llamar método
        ArrayAdapter adapter = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, listaPublicadores);
        spnPublicador.setAdapter(adapter);
    }

    private void consultarlistaPublicadores() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Publicadores publicador = null;
        publicadoresList = new ArrayList<Publicadores>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PUBLICADORES +
                " WHERE ID_Congregacion = " + (spnCongregacion.getSelectedItemId() - 1) +
                " ORDER BY "+ Utilidades.TABLA_PUBLICADORES + "." + Utilidades.CAMPO_Nombre_Publicador +
                " ASC", null);
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


    @SuppressLint("WrongViewCast")
    public void onClickIngresarActividad(View view) {
        spnCongregacion = findViewById(R.id.spnCongregacion);
        spnPublicador = findViewById(R.id.spnPublicador);
        edtFecha = findViewById(R.id.edtFecha);
        edtHoras = findViewById(R.id.edtHoras);
        edtRevisitas = findViewById(R.id.edtRevisitas);
        edtEstudios = findViewById(R.id.edtEstudios);

        if ((spnCongregacion.getSelectedItemId() == 0) || (spnPublicador.getSelectedItemId() == 0) ||
                edtFecha.getText().toString().equals("") || edtHoras.getText().toString().equals("") ||
                edtRevisitas.getText().toString().equals("") || edtEstudios.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_LONG).show();
        } else {
            ejecutar();
        }
    }

    private void ejecutar(){
        edtFecha = findViewById(R.id.edtFecha);
        edtHoras = findViewById(R.id.edtHoras);
        edtRevisitas = findViewById(R.id.edtRevisitas);
        edtEstudios = findViewById(R.id.edtEstudios);
        chkPrecursorAuxiliar = findViewById(R.id.chkPrecursorAuxiliar);
        edtObservaciones = findViewById(R.id.edtObservaciones);

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "ICA-04", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("Horas", edtHoras.getText().toString());
            values.put("Revisitas", edtRevisitas.getText().toString());
            values.put("Estudios", edtEstudios.getText().toString());
            values.put("PAuxiliar", chkPrecursorAuxiliar.isChecked() ? "true" : "false");
            values.put("Observaciones", edtObservaciones.getText().toString());
            values.put("AñoMes", edtFecha.getText().toString());
            values.put("Id_Publicador", nombreMap.get(spnPublicador.getSelectedItem().toString())); //utilizamos el map

            int a = (int) db.insert("tbl_ACTIVIDAD","Observaciones", values);
            Toast.makeText(getApplicationContext(),"Se registró en la base de datos. Número de registro "+ a, Toast.LENGTH_LONG).show();
            lblUltimoRegistro = findViewById(R.id.lblUltimoRegistro);
            String u = "Última fecha registrada: " + edtFecha.getText().toString()
                    + ", Horas: " + edtHoras.getText().toString() + ", Revisitas: " + edtRevisitas.getText().toString()
                    + ", Estudios: " + edtEstudios.getText().toString() + ", ID: " + nombreMap.get(spnPublicador.getSelectedItem().toString());
            lblUltimoRegistro.setText(u);
            limpiar(); //llamar método
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"No se registró en la base de datos. Error: " + e, Toast.LENGTH_LONG).show();
        }

        db.close();

        //Aumentará en un mes la EditView edtFecha
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date mFecha = null;
        try {
            mFecha = format1.parse(String.valueOf(edtFecha.getText()));
            cal.setTime(mFecha);
            cal.add(Calendar.MONTH, 1);
            String formatted = format1.format(cal.getTime());
            edtFecha.setText(formatted);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Foco a horas facilitando el ingreso de datos
        edtHoras.requestFocus();
    }

    //método directo de onClickIngresarActividad //////////////////////////////
    private void limpiar() {
        edtHoras.setText("");
        edtRevisitas.setText("");
        edtEstudios.setText("");
        chkPrecursorAuxiliar.setChecked(false);
        edtObservaciones.setText("");

    }
}