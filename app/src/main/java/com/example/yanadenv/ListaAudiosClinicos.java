package com.example.yanadenv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yanadenv.conexion.ConexionSQLiteHelper;
import com.example.yanadenv.data.model.Readgroup;
import com.example.yanadenv.entidades.ParticipanteAudios;
import com.example.yanadenv.entidades.upload;
import com.example.yanadenv.principal.ParticipanteActualizacion;
import com.example.yanadenv.principal.utilidades.Utilidades;

import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;

public class ListaAudiosClinicos extends AppCompatActivity {

    String idTablaParticipante;
    int view = R.layout.activity_lista_audios_clinicos;
    private MediaPlayer mp;
    ConexionSQLiteHelper conn;
    LinearLayout layout, contenedorBoton;
    MediaPlayer mpButtonClick1;
    EditText edtiTextNombre;
    EditText ediTextApellido;
    private String usuario;
    private String contrasenia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_audios_clinicos);
        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.llBotonera);
        SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);

        usuario = prefs.getString("usuario", ""); // prefs.getString("nombre del campo" , "valor por defecto")
        contrasenia = prefs.getString("contrasenia", ""); // prefs.getString("nombre del campo" , "valor por defecto")

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        idTablaParticipante = prefs.getString("idParticipante", ""); // prefs.getString("nombre del campo" , "valor por defecto")

        ArrayList<upload> listDeAudios = ListadeAudiosClinicos(idTablaParticipante);
//        edtiTextNombre.setFocusable(false);
        //      ediTextApellido.setFocusable(false);

//        ParticipanteAudios objAudi = new ParticipanteAudios();
//
//        objAudi = consulterListaDatos(idTablaParticipante);
//        ediTextApellido.setText(objAudi.getApellido());
//        edtiTextNombre.setText(objAudi.getNombre());

        //Creamos los botones en bucle
        //for (int i=0; i<listDeAudios.size(); i++) {

        //  String nombreArchivo = listDeAudios.get(i).getNombreArchivo();

        for (int i = 0; i < listDeAudios.size(); i++) {
            String nombreArchivo = listDeAudios.get(i).getNombreArchivo();
            if (!nombreArchivo.isEmpty()) {
                //  new UploadFile(listDeAudios.get(i).getIdLectura(), listDeAudios.get(i).getIdParticipante(), listDeAudios.get(i).getNombreArchivo()).execute();
                // eliminarAudioRegistrado(listDeAudios.get(i).getIdLectura(), listDeAudios.get(i).getIdParticipante());

                // GENERACION DE ETIQUETAS
                final TextView text = new TextView(this);

                if (listDeAudios.get(i).getFlag() == 1) {
                    text.setPadding(0, 0, 1, 0);
                    text.setTextColor(getResources().getColor(R.color.colorEnviadoServidor));
                    text.setText(listDeAudios.get(i).getNombreGrabacion() + " (SINCRONIZADO)");
                    text.setTextSize(20);
                } else if (!isEmpty(listDeAudios.get(i).getNombreArchivo()) && listDeAudios.get(i).getFlag() == 0) {
                    text.setPadding(0, 0, 1, 0);
                    text.setTextColor(getResources().getColor(R.color.colorRegistradoMovil));
                    text.setText(listDeAudios.get(i).getNombreGrabacion() + " (REGISTRADO)");
                    text.setTextSize(20);
                }

                llBotonera.addView(text);


                // GENERACION DE BOTONES REPRODUCIR
                Button button = new Button(this);

                //Asignamos propiedades de layout al boton
                button.setLayoutParams(lp);
                //Asignamos Texto al botón
                button.setText("Reproducir ");
                //Añadimos el botón a la botonera
                LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                        /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                        /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
                );
                parametros.setMargins(5, 20, 5, 5);

                button.setLayoutParams(parametros);
                llBotonera.addView(button);

                Uri myUri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SamayCOVaudios" + "/" + nombreArchivo);
                mpButtonClick1 = MediaPlayer.create(this, myUri);
                //   mpButtonClick2 = MediaPlayer.create(this, R.raw.splashs);

                // Button dugme = (Button) findViewById(R.id.dugme);

                try {
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            //  mpButtonClick1.start();
                            try {


                                if (mpButtonClick1.isPlaying() == true) {
                                    mpButtonClick1.pause();
                                    button.setText("REPRODUCIR");

                                } else {
                                    mpButtonClick1 = MediaPlayer.create(ListaAudiosClinicos.this, myUri);
                                    mpButtonClick1.start();
                                    button.setText("DETENER");
                                }


                            } catch (Exception e) {

                                button.setText("DETENER");
                            }
                        }
                    });

                } catch (Exception exception) {

                }

            } else if (isEmpty(listDeAudios.get(i).getNombreArchivo()) && listDeAudios.get(i).getFlag() == 0) {
                final TextView text = new TextView(this);
                text.setPadding(0, 0, 1, 0);
                text.setTextColor(getResources().getColor(R.color.colorStatusText));
                text.setText(listDeAudios.get(i).getNombreGrabacion() + " (VACIO)");
                text.setTextSize(20);
                llBotonera.addView(text);
            } else if (listDeAudios.get(i).getFlag() == 2) {
                final TextView text = new TextView(this);
                text.setPadding(0, 0, 1, 0);
                text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                text.setText(listDeAudios.get(i).getNombreGrabacion() + "  En proceso de carga ");
                text.setTextSize(20);
                llBotonera.addView(text);
            }
        }
    }
//if (mediaPlayer!= null) {
//        mediaPlayer.stop();
//        mediaPlayer.release();
//        mediaPlayer= null;
//    }


    public ArrayList<upload> ListadeAudiosClinicos(String idParticipante) {

        conn = new ConexionSQLiteHelper(this, Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();
        Readgroup dato = null;
        ArrayList<upload> listaAudioCarga;
        listaAudioCarga = new ArrayList<upload>();


        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PARTICIPANTES_READGROUP +
                " where " + Utilidades.CAMPO_idParticipante + " = '" + idParticipante + "'" + " and " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " +
                Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "'", null);

        while (cursor.moveToNext()) {
            upload objAudioCarga = new upload();
//
//            +CAMPO_ID+" "+ "INTEGER PRIMARY KEY, "
//                    +CAMPO_idParticipante+" TEXT, "
//                    +CAMPO_idLectura+" TEXT, "
//                    +CAMPO_name+" TEXT, "
//                    +CAMPO_ruta+" TEXT)";

            objAudioCarga.setIdParticipante(cursor.getString(1));
            objAudioCarga.setIdLectura(cursor.getString(2));
            objAudioCarga.setNombreGrabacion(cursor.getString(3));
            objAudioCarga.setNombreArchivo(cursor.getString(4));
            objAudioCarga.setFlag(cursor.getInt(5));
            listaAudioCarga.add(objAudioCarga);
        }

        db.close();

        return listaAudioCarga;
    }

    public ParticipanteAudios consulterListaDatos(String idParticipante) {
        conn = new ConexionSQLiteHelper(this, Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();
        ParticipanteAudios objParticipanteAudio = new ParticipanteAudios();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PARTICIPANTES_AUDIOS +
                " where " + Utilidades.CAMPO_idParticipante + " = '" + idParticipante + "'" + " and " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " +
                Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "'", null);

        while (cursor.moveToNext()) {

            objParticipanteAudio.setId(cursor.getInt(0));
            objParticipanteAudio.setIdParticipante(cursor.getString(1));
            objParticipanteAudio.setNombre(cursor.getString(2));
            objParticipanteAudio.setApellido(cursor.getString(3));
        }
        db.close();
        return objParticipanteAudio;
        //  obtenerLista();
    }

    @Override
    public boolean onSupportNavigateUp() {onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        mpButtonClick1.pause();
        Intent intent = new Intent(ListaAudiosClinicos.this, ParticipanteActualizacion.class);
        startActivity(intent);
    }


}

