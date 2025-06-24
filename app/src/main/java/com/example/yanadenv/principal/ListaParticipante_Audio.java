package com.example.yanadenv.principal;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.yanadenv.R;

import com.example.yanadenv.audio.DetalleAudio;
import com.example.yanadenv.conexion.ConexionSQLiteHelper;
import com.example.yanadenv.data.model.DatosParticipantes;
import com.example.yanadenv.data.model.ParametrosDatos;

import com.example.yanadenv.data.remote.APIService;
import com.example.yanadenv.data.remote.ApiUtils;
import com.example.yanadenv.entidades.ParticipanteAudios;
import com.example.yanadenv.principal.utilidades.Utilidades;


import java.util.ArrayList;

public class ListaParticipante_Audio extends AppCompatActivity {


    ListView listViewPersonas;
    private APIService mAPIService;
    ArrayList<ParticipanteAudios> listParticipante;
    DatosParticipantes datosParticipante;
    ArrayList<String> listaInformacion;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_participante__audio);
        listViewPersonas = (ListView) findViewById(R.id.listaParticipanteAudio);
        conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);


        mAPIService = ApiUtils.getAPIService();
        ParametrosDatos parametros = new ParametrosDatos(0, 0, 0);

        consulterListaDatos();
       // obtenerLista();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listViewPersonas.setAdapter(adaptador);

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaParticipante_Audio.this, DetalleAudio.class);

               // Toast toast = Toast.makeText(getApplicationContext(),  listParticipante.get(position).getId() +"", Toast.LENGTH_SHORT);
              //  toast.show();

                SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("idTablaAudio", listParticipante.get(position).getId()+"");
                editor.putString("idParticipante", listParticipante.get(position).getIdParticipante()+"");

                editor.commit();

                // intent.putExtra("objetoParticipante", listParticipante.get(position));

                startActivity(intent);
            }
        });
    }

    private void obtenerLista () {
        listaInformacion = new ArrayList<String>();
        for (int i = 0; i < listParticipante.size(); i++) {
            listaInformacion.add(listParticipante.get(i).getApellido() +
                    " " + listParticipante.get(i).getNombre()
            );
        }
    }

    private void consulterListaDatos() {
        SQLiteDatabase db = conn.getReadableDatabase();
        ParticipanteAudios dato = null;
        listParticipante = new ArrayList<ParticipanteAudios>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES, null);

        while (cursor.moveToNext()) {
            dato = new ParticipanteAudios();
            dato.setId(cursor.getInt(0));
            dato.setIdParticipante(cursor.getString(8));
            dato.setNombre(cursor.getString(2));
            dato.setApellido(cursor.getString(3));
            listParticipante.add(dato);

        }

        db.close();
        obtenerLista();
    }

}