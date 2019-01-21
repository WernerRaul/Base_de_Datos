package com.example.raul.base_de_datos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.raul.base_de_datos.Utilidades.Utilidades;

public class ActivityConsultaPromediosCongregacion extends AppCompatActivity {

    ConexionSQLiteHelper conn;
    TextView lblHoras;
    TextView lblRevisitas;
    TextView lblEstudiosBiblicos;
    TextView lblPublicadores;
    TextView lblPrecRegulares;
    TextView lblPrecAuxiliar;
    TextView lblReuEntreSemana;
    TextView lblReuFinSemana;
    TextView lblTerritorios;
    TextView lblCongregacionBolsa;
    Bundle bolsaRe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_promedios_congregacion);

        lblHoras = findViewById(R.id.lblHoras);
        lblRevisitas = findViewById(R.id.lblRevisitas);
        lblEstudiosBiblicos = findViewById(R.id.lblEstudiosBiblicos);
        lblPublicadores = findViewById(R.id.lblPublicadores);
        lblPrecRegulares = findViewById(R.id.lblPrecursoresRegulares);
        lblPrecAuxiliar = findViewById(R.id.lblPrecursoresAuxiliares);
        lblReuEntreSemana = findViewById(R.id.lblReunionEntreSemana);
        lblReuFinSemana = findViewById(R.id.lblReunionFinSemana);
        lblTerritorios = findViewById(R.id.lblTerritorios);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);

        bolsaRe=getIntent().getExtras();

        lblCongregacionBolsa = findViewById(R.id.lblCongregacionBolsa);
        lblCongregacionBolsa.setText(bolsaRe.getString("miCongregacion"));

        SQLiteDatabase db = conn.getReadableDatabase();
        //Horas,Revisitas,Estudios
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion AND " +
                "tbl_PUBLICADORES.PrecRegular = 'false') INNER JOIN tbl_ACTIVIDAD ON " +
                "(tbl_ACTIVIDAD.ID_Publicador = tbl_PUBLICADORES.ID_Publicador AND " +
                "tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsaRe.get("miMes").toString()+" months') AND tbl_ACTIVIDAD.PAuxiliar = 'false')");
        String[] sqlSelect = {
                "round(sum(tbl_ACTIVIDAD.Horas * 1.0) / count(tbl_PUBLICADORES.ID_Publicador * 1.0),1)",
                "round(sum(tbl_ACTIVIDAD.Revisitas * 1.0) / count(tbl_PUBLICADORES.ID_Publicador * 1.0),1)",
                "round(sum(tbl_ACTIVIDAD.Estudios * 1.0) / count(tbl_PUBLICADORES.ID_Publicador * 1.0),1)"
        };
        Cursor c = builder.query(db, sqlSelect, null, null, "tbl_CONGREGACIONES.Nombre",
                "tbl_CONGREGACIONES.Nombre = '" + bolsaRe.getString("miCongregacion") + "'", null);
        c.moveToNext();
        lblHoras.setText(c.getString(0) + "   Horas por Publicador");
        lblRevisitas.setText(c.getString(1) + "   Revisitas por Publicador");
        lblEstudiosBiblicos.setText(c.getString(2) + "   Estudios por Publicador");
        c.close();

        //Publicadores
        SQLiteQueryBuilder builder2 = new SQLiteQueryBuilder();
        builder2.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON" +
                "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion AND " +
                "tbl_CONGREGACIONES.Nombre = '" + bolsaRe.getString("miCongregacion") + "' " +
                "AND tbl_PUBLICADORES.PrecRegular = 'false')");
        String[] sqlSelect2 = {
                "count(tbl_PUBLICADORES.Nombre)"
        };
        Cursor d = builder2.query(db, sqlSelect2, null, null, null, null, null);
        d.moveToNext();
        lblPublicadores.setText(d.getString(0) + " Publicadores");
        d.close();

        //Precursores Regulares
        SQLiteQueryBuilder builder3 = new SQLiteQueryBuilder();
        builder3.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON" +
                "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion AND " +
                "tbl_CONGREGACIONES.Nombre = '" + bolsaRe.getString("miCongregacion") + "' " +
                "AND tbl_PUBLICADORES.PrecRegular = 'true')");
        String[] sqlSelect3 = {
                "count(tbl_PUBLICADORES.Nombre)"
        };
        Cursor e = builder3.query(db, sqlSelect3, null, null, null, null, null);
        e.moveToNext();
        lblPrecRegulares.setText(e.getString(0) + " Precursores Regulares");
        e.close();

        //Precursores Auxiliares
        SQLiteQueryBuilder builder4 = new SQLiteQueryBuilder();
        builder4.setTables("tbl_CONGREGACIONES INNER JOIN tbl_PUBLICADORES ON" +
                "(tbl_CONGREGACIONES.ID_Congregacion = tbl_PUBLICADORES.ID_Congregacion AND " +
                "tbl_CONGREGACIONES.Nombre = '" + bolsaRe.getString("miCongregacion") + "') INNER JOIN " +
                "tbl_ACTIVIDAD ON (tbl_PUBLICADORES.ID_Publicador = tbl_ACTIVIDAD.ID_Publicador AND " +
                "tbl_ACTIVIDAD.PAuxiliar = 'true' AND tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsaRe.get("miMes").toString()+" months'))");
        String[] sqlSelect4 = {
                "tbl_PUBLICADORES.Nombre"
        };
        Cursor f = builder4.query(db, sqlSelect4, null, null, "tbl_PUBLICADORES.Nombre", null, null);
        f.moveToNext();
        lblPrecAuxiliar.setText(f.getCount() + " Precursores Auxiliares");
        f.close();

        //Asistencia
        SQLiteQueryBuilder builder5 = new SQLiteQueryBuilder();
        builder5.setTables("tbl_REUNIONES INNER JOIN tbl_CONGREGACIONES ON" +
                "(tbl_REUNIONES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion AND " +
                "tbl_CONGREGACIONES.Nombre = '" + bolsaRe.getString("miCongregacion") + "' AND " +
                "tbl_REUNIONES.Mes >= date('now','start of month','-"+bolsaRe.get("miMes").toString()+" months'))");
        String[] sqlSelect5 = {
                "round(AVG(tbl_REUNIONES.ReuEntreSemana),1)",
                "round(AVG(tbl_REUNIONES.ReuFinSemana),1)"
        };
        Cursor g = builder5.query(db, sqlSelect5, null, null, "tbl_CONGREGACIONES.Nombre", null, null);
        g.moveToNext();
        lblReuEntreSemana.setText(g.getString(0) + " Reunión entre semana");
        lblReuFinSemana.setText(g.getString(1) + " Reunión fin de semana");
        g.close();

        //Territorios
        SQLiteDatabase builder6 = conn.getReadableDatabase();
        Cursor h = builder6.rawQuery("SELECT Territorios FROM " + Utilidades.TABLA_CONGREGACIONES +
                " WHERE Nombre = '" + bolsaRe.getString("miCongregacion") + "'" , null);
        h.moveToNext();
        lblTerritorios.setText(h.getString(0) + " Territorios");
        h.close();
    }
}
