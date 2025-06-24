package com.example.yanadenv.principal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.yanadenv.ListaAudiosClinicos;
import com.example.yanadenv.audio.DetalleAudio;
import com.example.yanadenv.conexion.ConexionSQLiteHelper;
import com.example.yanadenv.data.model.Readgroup;
import com.example.yanadenv.editar.Editar;
import com.example.yanadenv.entidades.ParticipanteAudios;
import com.example.yanadenv.entidades.upload;
import com.example.yanadenv.principal.utilidades.Utilidades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yanadenv.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.text.TextUtils.isEmpty;

public class ParticipanteActualizacion extends AppCompatActivity {

    Button btnListaAudiosRegistrados;
    Button btnRegistrarAudios;
    Button btnResultadoAudiosClinicos;
    Button btnEditar;
    EditText edtiTextNombre;
    EditText ediTextApellido;
    EditText editTextCampania;
    //EditText txtEdad;
    private TextView logText;
    ArrayList<ParticipanteAudios> listParticipante;
    ArrayList<String> listaInformacion;
    ConexionSQLiteHelper conn;
    ArrayList<upload> up;
    private String usuario;
    private String contrasenia;
    private String idParticipanteServidor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante_actualizacion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);

        usuario = prefs.getString("usuario", "");
        contrasenia = prefs.getString("contrasenia", "");
        idParticipanteServidor = prefs.getString("idParticipanteServidor", "");

        conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);

        edtiTextNombre = (EditText) findViewById(R.id.txtEditNombre);
        edtiTextNombre.setFocusable(false);

        ediTextApellido = (EditText) findViewById(R.id.txtEditApellido);
        ediTextApellido.setFocusable(false);
        logText = findViewById(R.id.receive_text);

//        editTextCampania = (EditText) findViewById(R.id.txtEditCampania);
//        editTextCampania.setFocusable(false);

        ParticipanteAudios objAudi = new ParticipanteAudios();
        SharedPreferences.Editor editor = prefs.edit();
        String idDato = prefs.getString("idTablaAudio", "");
        String idTablaParticipante = prefs.getString("idParticipante", "");
        String edadParticipante = prefs.getString("edadParticipante", "");

        editor.putString("idTablaAudio", idDato+"");
        editor.putString("idParticipante", idTablaParticipante+"");
        editor.putString("edadParticipante", edadParticipante+"");

        objAudi = consulterListaDatos(idTablaParticipante);
        ediTextApellido.setText(objAudi.getApellido());
        edtiTextNombre.setText(objAudi.getNombre());

        btnListaAudiosRegistrados = (Button) findViewById(R.id.btnListaAudios);

        btnRegistrarAudios = (Button) findViewById(R.id.btnIngresarAudios);

        btnRegistrarAudios = (Button) findViewById(R.id.btnIngresarAudios);
         up  = new ArrayList<>();

        up = ListadeAudiosClinicos(idTablaParticipante);


        status(up);


        btnRegistrarAudios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipanteActualizacion.this, DetalleAudio.class);
                // Toast toast = Toast.makeText(getApplicationContext(),  listParticipante.get(position).getId() +"", Toast.LENGTH_SHORT);
                //  toat.show();
                SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
              //  SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                String idDato = prefs.getString("idTablaAudio", ""); // prefs.getString("nombre del campo" , "valor por defecto")
                String idTablaParticipante = prefs.getString("idParticipante", ""); // prefs.getString("nombre del campo" , "valor por defecto")
                editor.putString("idTablaAudio", idDato+"");
                editor.putString("idParticipante", idTablaParticipante+"");

                editor.commit();
                // intent.putExtra("objetoParticipante", listParticipante.get(position));
                startActivity(intent);

            }
        });

        btnListaAudiosRegistrados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipanteActualizacion.this, ListaAudiosClinicos.class);
                startActivity(intent);
            }
        });






        btnResultadoAudiosClinicos = (Button) findViewById(R.id.btnResultadosAudios);


        btnResultadoAudiosClinicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(idParticipanteServidor) || "null".equals(idParticipanteServidor)) {
                    Toast.makeText(getApplication(), "NO EXISTEN RESULTADOS CLINICOS PROCESADOS", Toast.LENGTH_SHORT).show();
                }
                else {

                   boolean conectividad =  isOnline(ParticipanteActualizacion.this);
                    conectividad = true;

                   if(conectividad){
                       Intent i = new Intent(ParticipanteActualizacion.this, ResultadoClinicos.class);
                       startActivity(i);
                   }
                   else {
                       Toast.makeText(getApplication(), "NO TIENE CONEXIÓN A INTERNET", Toast.LENGTH_SHORT).show();
                   }


                }

            }
        });
        btnEditar = (Button) findViewById(R.id.btnEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ParticipanteActualizacion.this, Editar.class);
                startActivity(i);
            }
        });

        ///muestra datos del participante
        //SI SE PUEDE MUESTRA LOS AUDIOS CLINICOS REGISTRADOS
        //// PONER 3 BOTONES   EDITAR  , REGISTRAR AUDIOS CLINICOS  Y VER RESULTADOS



//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }



    public ParticipanteAudios consulterListaDatos(String idParticipante) {
        SQLiteDatabase db = conn.getReadableDatabase();
        ParticipanteAudios objParticipanteAudio = new ParticipanteAudios();

//        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES+
//                " where "+ Utilidades.CAMPO_idParticipante+ " = '"+ idParticipante +"'" , null);



        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES+
                 " where "+ Utilidades.CAMPO_idParticipante+ " = '"+ idParticipante +"'" + " and " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " +
                Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "'" , null);


        while (cursor.moveToNext()) {

            objParticipanteAudio.setId(cursor.getInt(0));
            objParticipanteAudio.setIdParticipante(cursor.getString(30));
            objParticipanteAudio.setNombre(cursor.getString(1));
            objParticipanteAudio.setApellido(cursor.getString(2));
        }
        db.close();
        return objParticipanteAudio;
      //  obtenerLista();
    }

    private void status(ArrayList<upload> str) {

        for (int i = 0; i < str.size();i++){

            if (str.get(i).getFlag() == 1)  {
                //   SpannableStringBuilder spn = new SpannableStringBuilder(str.get(i).getNombreGrabacion() +" "+ "" + '\n');
                SpannableStringBuilder spn = new SpannableStringBuilder(nombreGrabacacion(str.get(i).getNombreGrabacion())
                        + " Sincronizado " + '\n');
                spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorEnviadoServidor)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                logText.append(spn);
            }
              if (str.get(i).getFlag() == 2)  {
                //   SpannableStringBuilder spn = new SpannableStringBuilder(str.get(i).getNombreGrabacion() +" "+ "" + '\n');
                  SpannableStringBuilder spn = new SpannableStringBuilder(nombreGrabacacion(str.get(i).getNombreGrabacion())
                          + " En proceso de carga " + '\n');
                  spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                  logText.append(spn);
            }

            else {
            if( !isEmpty(str.get(i).getNombreArchivo()) && str.get(i).getFlag() == 0 ) {
             //   SpannableStringBuilder spn = new SpannableStringBuilder(str.get(i).getNombreGrabacion() +" "+ "" + '\n');
                SpannableStringBuilder spn = new SpannableStringBuilder(nombreGrabacacion(str.get(i).getNombreGrabacion())
                        + "  Registrado " + '\n');
                spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRegistradoMovil)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                logText.append(spn);
            }else  if( isEmpty(str.get(i).getNombreArchivo()) && str.get(i).getFlag() == 0 )

                {
                SpannableStringBuilder spn = new SpannableStringBuilder(nombreGrabacacion(str.get(i).getNombreGrabacion())
                        + " Vacio" + '\n');
                spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorStatusText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                logText.append(spn);
            }
            }

        }
    }


    public ArrayList<upload> ListadeAudiosClinicos(String idParticipante) {


        SQLiteDatabase db = conn.getReadableDatabase();
        Readgroup dato = null;
        ArrayList<upload> listaAudioCarga;
        listaAudioCarga = new ArrayList<upload>();


        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PARTICIPANTES_READGROUP  +
                " where "+ Utilidades.CAMPO_idParticipante+ " = '"+ idParticipante +"'" + " and " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " +
                Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "'" , null);

        while (cursor.moveToNext()) {
            upload objAudioCarga = new upload();
            objAudioCarga.setIdParticipante(cursor.getString(1));
            objAudioCarga.setIdLectura(cursor.getString(2));
            objAudioCarga.setNombreGrabacion(cursor.getString(3));
            objAudioCarga.setNombreArchivo(cursor.getString(4));
            objAudioCarga.setFlag(cursor.getInt(5));
            objAudioCarga.setDni(cursor.getString(6));
            objAudioCarga.setNombreSigla(cursor.getString(7));
            listaAudioCarga.add(objAudioCarga);
        }
        db.close();

        return listaAudioCarga;
    }

    String nombreGrabacacion (String entrada) {

        String resultado = "";
        String[] parts = entrada.split(" ");

        for (int i=0 ; i < parts.length;i++){
            if (!isEmpty(parts[i])){
                char[] caracteres = parts[i].toCharArray();
                caracteres[0] = Character.toUpperCase(caracteres[0]);
                resultado = resultado + caracteres[0];
            }
        }
        return entrada;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplication(), Inicio.class);
        startActivity(intent);
        //Toast.makeText(getApplication(), "PRUEBA", Toast.LENGTH_SHORT).show();
    }



    private static final String TAG = "NetworkUtil";

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Boolean> futureRun = executor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    try {
                        HttpURLConnection urlc = (HttpURLConnection) (new URL("https://www.google.com/").openConnection());
                        urlc.setRequestProperty("User-Agent", "Test");
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(1500);
                        urlc.connect();
                        return (urlc.getResponseCode() == 200);
                    } catch (IOException e) {
                        Log.e(TAG, "Error checking internet connection", e);
                    }
                    return false;
                }
            });

            try {
                boolean isConnected = futureRun.get();
                executor.shutdown();
                return isConnected;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                executor.shutdown();
                return false;
            }
        } else {
            Log.d(TAG, "No network available!");
            return false;
        }
    }




}