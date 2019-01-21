package com.example.raul.base_de_datos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivityAsistenciaReuniones extends AppCompatActivity {
    EditText edtFecha;
    EditText edtReunionEntreSemana;
    EditText edtReunionFinSemana;
    EditText edtObservaciones;
    TextView lblUltimoRegistro;

    ConexionSQLiteHelper conn;


    TextView tituloCong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_reuniones);

        tituloCong= findViewById(R.id.lblCongregacion);
        Bundle bolsaR=getIntent().getExtras();
        tituloCong.setText("Congregación "+bolsaR.getString("nombreKey"));


    }

    public void onClick(View view) {
        edtFecha = findViewById(R.id.edtFecha);
        edtReunionEntreSemana = findViewById(R.id.edtReunionEntreSemana);
        edtReunionFinSemana = findViewById(R.id.edtReunionFinSemana);
        if (edtFecha.getText().toString().equals("") || edtReunionEntreSemana.getText().toString().equals("") || edtReunionFinSemana.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Relene los campos arriba indicados",Toast.LENGTH_LONG).show();
        } else {
            ejecutar();
        }
    }

    private  void ejecutar () {
        edtFecha = findViewById(R.id.edtFecha);
        edtReunionEntreSemana = findViewById(R.id.edtReunionEntreSemana);
        edtReunionFinSemana = findViewById(R.id.edtReunionFinSemana);
        edtObservaciones = findViewById(R.id.edtObservaciones);
        String texto = null;

        Bundle bolsaR=getIntent().getExtras();

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        SQLiteDatabase db=conn.getWritableDatabase();

        try {
            ContentValues values=new ContentValues();
            values.put("Mes", edtFecha.getText().toString());
            values.put("ReuEntreSemana", edtReunionEntreSemana.getText().toString());
            values.put("ReuFinSemana", edtReunionFinSemana.getText().toString());
            values.put("ID_Congregacion", bolsaR.getInt("idKey"));
            values.put("Observaciones", edtObservaciones.getText().toString());

            db.insert("tbl_REUNIONES","Observaciones", values);

            texto = "Fecha: " +edtFecha.getText().toString()+", RES: "+edtReunionEntreSemana.getText().toString()+
                    ", RFS: "+edtReunionFinSemana.getText().toString()+", registrado en la Base de Datos";
            Toast.makeText(getApplicationContext(),texto, Toast.LENGTH_LONG).show();
            limpiar();
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(),"No se registró en la base de datos", Toast.LENGTH_LONG).show();
        }
        db.close();

        lblUltimoRegistro = findViewById(R.id.lblUltimoRegistro);
        lblUltimoRegistro.setText(texto);

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

        edtReunionEntreSemana.requestFocus();
    }

    private void limpiar() {
        edtReunionEntreSemana.setText("");
        edtReunionFinSemana.setText("");
        edtObservaciones.setText("");
    }
}
