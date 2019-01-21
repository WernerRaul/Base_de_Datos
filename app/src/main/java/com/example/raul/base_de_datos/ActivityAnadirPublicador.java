package com.example.raul.base_de_datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raul.base_de_datos.Utilidades.Utilidades;
import com.example.raul.base_de_datos.entidades.Congregaciones;

import java.util.ArrayList;

public class ActivityAnadirPublicador extends AppCompatActivity {


    ArrayList<String> listaCongregaciones;
    ArrayList<Congregaciones> congregacionesList;
    ConexionSQLiteHelper conn;
    Spinner spnCongregacion;
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
    TextView lblUltimoRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_publicador);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);

        //Llenar spinner con nombre de las congregaciones
        spnCongregacion = findViewById(R.id.spnCongregacion);
        consultarlistaCongregaciones(); //llamar método
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);

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

    public void onClick(View view) {
        spnCongregacion = findViewById(R.id.spnCongregacion);
        edtNombrePublicador = findViewById(R.id.edtNombrePublicador);
        if ((spnCongregacion.getSelectedItemId() == 0) || edtNombrePublicador.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Rellene los campos", Toast.LENGTH_LONG).show();
        } else {
            ejecutar();
        }
    }

    private void ejecutar(){
        edtNombrePublicador = findViewById(R.id.edtNombrePublicador);
        edtDireccion = findViewById(R.id.edtDireccion);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtFechaNacimiento = findViewById(R.id.edtFechaNacimiento);
        edtFechaBautizo = findViewById(R.id.edtFechaBautizo);
        chkAnciano = findViewById(R.id.chkAnciano);
        chkSiervoMinisterial = findViewById(R.id.chkSiervoMinisterial);
        chkPrecursorRegular = findViewById(R.id.chkPrecursorRegular);
        edtObservaciones = findViewById(R.id.edtObservaciones);
        lblUltimoRegistro = findViewById(R.id.lblUltimoRegistro);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"ICA-04", null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        //Para información de último registro
        String UltimoRegistro = edtNombrePublicador.getText().toString();

        try {
            ContentValues values=new ContentValues();
            values.putNull("ID_Publicador");
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

            int a = (int) db.insert("tbl_PUBLICADORES","Observaciones", values);
            Toast.makeText(getApplicationContext(),"Se registró en la base de datos. Número de registro "+ a, Toast.LENGTH_LONG).show();
            limpiar();
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(),"No se registró en la base de datos", Toast.LENGTH_LONG).show();
        }
        db.close();
        //Información de Último registro
        lblUltimoRegistro.setText("Último registro: " +UltimoRegistro);
        edtNombrePublicador.requestFocus();
    }

    private void limpiar() {
            edtNombrePublicador.setText("");
            edtDireccion.setText("");
            edtTelefono.setText("");
            spnSexo.setSelection(0);
            edtFechaNacimiento.setText("");
            edtFechaBautizo.setText("");
            chkAnciano.setChecked(false);
            chkPrecursorRegular.setChecked(false);
            chkSiervoMinisterial.setChecked(false);
            edtObservaciones.setText("");
    }
}


