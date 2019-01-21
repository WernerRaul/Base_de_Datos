package com.example.raul.base_de_datos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.raul.base_de_datos.Adaptadores.AdaptadorActividad;
import com.example.raul.base_de_datos.Utilidades.Utilidades;
import com.example.raul.base_de_datos.entidades.Actividad;
import com.example.raul.base_de_datos.entidades.Congregaciones;
import com.example.raul.base_de_datos.entidades.Publicadores;

import java.util.ArrayList;
import java.util.Map;

public class ActivityActualizarActividadPublicador extends AppCompatActivity implements OnItemSelectedListener {

    ConexionSQLiteHelper conn;

    RecyclerView recyclerActividad;
    Spinner spnCongregacion;
    Spinner spnPublicador;

    ArrayList<Congregaciones> congregacionesList;
    ArrayList<String> listaCongregaciones;
    ArrayList<String> listaPublicadores;
    ArrayList<Actividad> listaActividad;
    ArrayList<Publicadores> publicadoresList;
    Map<String, Integer> nombreMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_actividad_publicador);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        spnCongregacion = findViewById(R.id.spnCongregacion);

        //Llenar spinner con nombre de las congregaciones
        consultarlistaCongregaciones(); //llamar método
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, listaCongregaciones);
        spnCongregacion.setAdapter(adaptador);
        spnCongregacion.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //si hay selección de un nombre en el spinner
        if (parent.getId() == R.id.spnCongregacion && position > 0) {
            incluirlista();
//            Toast.makeText(getApplicationContext(),"position: "+String.valueOf(position) +
//                    " ItemId= "+spnCongregacion.getSelectedItem(),Toast.LENGTH_LONG).show();

        } else if (parent.getId() == R.id.spnPublicador) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void consultarlistaCongregaciones() {
        //El método se encarga de gestionar los nombres de las congregaciones
        //primero: el cursor devuelve los nombres de las congregaciones
        //segundo: los deposita en el objeto congregacion de la clase congregaciones por cada nombre
        //tercero: el objeto por cada nombre es añadido al array congregacionesList
        //se llama al método obtenerlista
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
        //El método se encarga de gestionar la puesta de nombres en el array listaCongregaciones
        //para ser usado por el adapter en el método onCreate
        listaCongregaciones = new ArrayList<String>();

        listaCongregaciones.add("Seleccione"); //Añadir Seleccione
        for (int i = 0; i < congregacionesList.size(); i++) {
            listaCongregaciones.add(congregacionesList.get(i).getnombre());
        }
    }

    private void incluirlista() {
        //método para incluir la lista de publicadores de la congregacion deseada
        spnPublicador = findViewById(R.id.spnPublicador);

        consultarlistaPublicadores(); //llamar método
        ArrayAdapter adaptador1 = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, listaPublicadores);
        spnPublicador.setAdapter(adaptador1);
//        spnPublicador.setOnItemSelectedListener(this);
    }

    private void consultarlistaPublicadores() {
        //El método se encarga de abrir la bd y llenar el objeto publicador de la clase publicadores
        //por cada nombre de publicador que existe, luego el cursor alimenta un HashMap con su Id
        //para identificar el id con el nombre del publicador, luego llama al método obtenerlistaP
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
        //Este método se encarga de alimentar el array listaPublicadores
        //para luego incertarlo en el adapter del spinner
        listaPublicadores = new ArrayList<String>();

        listaPublicadores.add("Seleccione");
        for (int i = 0; i < publicadoresList.size(); i++) {
            listaPublicadores.add(publicadoresList.get(i).getNombre());
        }
    }

    private void datosmostrar() {
        SQLiteDatabase db = conn.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();


        builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion AND " +
                "tbl_PUBLICADORES.Nombre = '" + spnPublicador.getSelectedItem() + "') INNER JOIN tbl_ACTIVIDAD ON " +
                "(tbl_ACTIVIDAD.ID_Publicador = tbl_PUBLICADORES.ID_Publicador)");
        String[] sqlSelect = {
                "tbl_ACTIVIDAD.ID_Actividad",
                "tbl_ACTIVIDAD.Horas",
                "tbl_ACTIVIDAD.Revisitas",
                "tbl_ACTIVIDAD.Estudios",
                "tbl_ACTIVIDAD.PAuxiliar",
                "tbl_ACTIVIDAD.Observaciones",
                "tbl_ACTIVIDAD.AñoMes",
                "tbl_ACTIVIDAD.ID_Publicador"
        };
        String[] argumentos = {(String) spnCongregacion.getSelectedItem()};
        Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, null, null, "tbl_CONGREGACIONES.Nombre ASC");
        while (c.moveToNext()) {
            listaActividad.add(new Actividad(
                    Integer.parseInt(c.getString(0)),
                    Integer.parseInt(c.getString(1)),
                    Integer.parseInt(c.getString(2)),
                    Integer.parseInt(c.getString(3)),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    Integer.parseInt(c.getString(7))));
        }
        c.close();


        listaActividad = new ArrayList<>();
        recyclerActividad = findViewById(R.id.myRecyclerView);
        recyclerActividad.setLayoutManager(new LinearLayoutManager(this));
//        recyclerActividad.setLayoutManager(new GridLayoutManager(this,5));

//        llenarActividad();

        AdaptadorActividad adapter2 = new AdaptadorActividad(listaActividad);
        recyclerActividad.setAdapter(adapter2);

    }

}