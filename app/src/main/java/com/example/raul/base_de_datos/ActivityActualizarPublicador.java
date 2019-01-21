package com.example.raul.base_de_datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.raul.base_de_datos.Utilidades.Utilidades;
import com.example.raul.base_de_datos.entidades.Congregaciones;
import com.example.raul.base_de_datos.entidades.Publicadores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityActualizarPublicador extends AppCompatActivity implements OnItemSelectedListener {

    ArrayList<String> listaCongregaciones;
    ArrayList<Congregaciones> congregacionesList;
    ConexionSQLiteHelper conn;
    Spinner spnCongregacion;
    Spinner spnPublicador;
    Spinner spnSexo;
    EditText edtNombrePublicador;
    EditText edtDireccion;
    EditText edtTelefono;
    EditText edtFechaNacimiento;
    EditText edtFechaBautizo;
    CheckBox chkAnciano;
    CheckBox chkSiervoMinisterial;
    CheckBox chkPrecursorRegular;
    EditText edtObservaciones;
    Button btnIngresarPublicador;
    EditText edtUltimoRegistro;
    ArrayList<String> listaPublicadores;
    ArrayList<Publicadores> publicadoresList;
    Map<String,Integer> nombreMap;
    String ID_Publicador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_publicador);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        nombreMap = new HashMap<String, Integer>();

        //Llenar spinner con nombre de las congregaciones
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

        //Llenar spinner con sexos 'Hombre' y 'Mujer'
        spnSexo = findViewById(R.id.spnSexo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.combo_sexo,android.R.layout.simple_spinner_item);
        spnSexo.setAdapter(adapter);

    }

    private void consultarlistaCongregaciones() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Congregaciones congregacion = null;
        congregacionesList = new ArrayList<Congregaciones>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CONGREGACIONES, null);
        while (cursor.moveToNext()) {
            congregacion = new Congregaciones();
            congregacion.setnombre(cursor.getString(1)); //Ingresar nombre de la congregacion
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
        spnPublicador.setOnItemSelectedListener(this);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //si hay selección de un nombre en el spinner
        if (position != 0) {
             datoseditar();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void datoseditar() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PUBLICADORES + " WHERE Nombre = '" +
                spnPublicador.getSelectedItem() + "'", null);
        cursor.moveToNext();

        //VISUALIZACIÓN DE DATOS QUE OFRECE EL CURSOR EN LOS VIEW
        //Guarda ID_Publicador para hacer la actualización en bd línea 220
        ID_Publicador = cursor.getString(0);
        //establecer valor en campo NombrePublicador
        edtNombrePublicador = findViewById(R.id.edtNombrePublicador);
        edtNombrePublicador.setText(cursor.getString(1));
        //establecer valor en campo Dirección
        edtDireccion = findViewById(R.id.edtDireccion);
        edtDireccion.setText(cursor.getString(2));
        //establecer valor en campo Sexo
        if (cursor.getString(3).equals("Hombre")){
            spnSexo.setSelection(0);
        } else {
            spnSexo.setSelection(1);
        }
        //establecer valor en campo Teléfono
        edtTelefono = findViewById(R.id.edtTelefono);
        edtTelefono.setText(cursor.getString(4));
        //establecer valor en campo FechaNacimiento
        edtFechaNacimiento = findViewById(R.id.edtFechaNacimiento);
        edtFechaNacimiento.setText(cursor.getString(5));
        //establecer valor en campo NombreBautizo
        edtFechaBautizo = findViewById(R.id.edtFechaBautizo);
        edtFechaBautizo.setText(cursor.getString(6));
        //establecer valor en campo Anciano
        chkAnciano = findViewById(R.id.chkAnciano);
        chkAnciano.setChecked(Boolean.parseBoolean(cursor.getString(7)));
        //establecer valor en campo SiervoMinisterial
        chkSiervoMinisterial = findViewById(R.id.chkSiervoMinisterial);
        chkSiervoMinisterial.setChecked(Boolean.parseBoolean(cursor.getString(8)));
        //establecer valor en campo Precursor Regular
        chkPrecursorRegular = findViewById(R.id.chkPrecursorRegular);
        chkPrecursorRegular.setChecked(Boolean.parseBoolean(cursor.getString(9)));
        //establecer valor en campo Observaciones
        edtObservaciones = findViewById(R.id.edtObservaciones);
        edtObservaciones.setText(cursor.getString(11));

        db.close();

    }

    public void onClickActualizar(View view) {
        if (spnCongregacion.getSelectedItemId() == 0 || spnPublicador.getSelectedItemId() == 0 || edtNombrePublicador.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Rellene los campos arriba indicados",Toast.LENGTH_LONG).show();
        } else {
            ejecutar();
        }
    }

    private void ejecutar(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"ICA-04", null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        try {
            //Esta
            ContentValues values=new ContentValues();
            values.put("Nombre", edtNombrePublicador.getText().toString());
            values.put("Direccion", edtDireccion.getText().toString());
            values.put("Sexo", spnSexo.getSelectedItem().toString());
            values.put("Telefono", edtTelefono.getText().toString());
            values.put("FechaNacimiento", edtFechaNacimiento.getText().toString());
            values.put("FechaBautismo", edtFechaBautizo.getText().toString());
            values.put("Anciano", chkAnciano.isChecked() ? "true" : "false");
            values.put("SiervoMinisterial", chkSiervoMinisterial.isChecked() ? "true" : "false");
            values.put("PrecRegular", chkPrecursorRegular.isChecked() ? "true" : "false");
            values.put("Observaciones", edtObservaciones.getText().toString());
            values.put("ID_Congregacion",spnCongregacion.getSelectedItemId() - 1);

            String[] ID = new String[] {String.valueOf(ID_Publicador)};
            int a = db.update("tbl_PUBLICADORES", values,"ID_Publicador=?",ID);
            if (a == 1) {
                Toast.makeText(getApplicationContext(),"Se actualizó en la base de datos. Nombre: " +
                        edtNombrePublicador.getText().toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),"No se actualizó en la base de datos", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(),"No se actualizó en la base de datos. Error: " + e, Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}

