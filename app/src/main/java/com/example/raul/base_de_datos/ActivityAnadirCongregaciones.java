package com.example.raul.base_de_datos;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityAnadirCongregaciones extends AppCompatActivity {

    EditText campoNuevaCongregacion, campoSeccion, campoObservaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_congregaciones);

        campoNuevaCongregacion = findViewById(R.id.edtNombreCongregacion);
        campoSeccion = findViewById(R.id.edtSeccion);
        campoObservaciones = findViewById(R.id.edtObservaciones);

    }

    public void onClick(android.view.View view) {
        if ((campoNuevaCongregacion.getText().toString().equals("")) || (campoSeccion.getText().toString().equals(""))) {
            Toast.makeText(getApplicationContext(),"Rellene los campos arriba indicados",Toast.LENGTH_LONG).show();
        } else {
            ejecutar();
        }
    }

    private void  ejecutar() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"ICA-04", null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        try {
            ContentValues values=new ContentValues();
            values.put("Nombre", campoNuevaCongregacion.getText().toString());
            values.put("Seccion", campoSeccion.getText().toString());
            values.put("Observaciones", campoObservaciones.getText().toString());
            db.insert("tbl_CONGREGACIONES","Observaciones", values);
            Toast.makeText(getApplicationContext(),"Se registró en la base de datos", Toast.LENGTH_LONG).show();
            limpiar();
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(),"No se registró en la base de datos", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    private void limpiar() {
        campoNuevaCongregacion.setText("");
        campoSeccion.setText("");
        campoObservaciones.setText("");
    }
}
