package com.example.yanadenv.principal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yanadenv.data.model.ClinicdataDTO;
import com.example.yanadenv.data.model.FileSearcher;
import com.example.yanadenv.data.model.Participant;
import com.example.yanadenv.data.model.Readgroup;
import com.example.yanadenv.data.model.SocieconomicDTO;
import com.example.yanadenv.data.model.UploadFile;
import com.example.yanadenv.data.remote.APIService;
import com.example.yanadenv.R;
import com.example.yanadenv.conexion.ConexionSQLiteHelper;
import com.example.yanadenv.data.model.AssigneeDTO;
import com.example.yanadenv.data.model.Post;
import com.example.yanadenv.data.remote.ApiUtils;
import com.example.yanadenv.entidades.Participante;
import com.example.yanadenv.entidades.SongDto;
import com.example.yanadenv.entidades.upload;
import com.example.yanadenv.login.Login;
import com.example.yanadenv.registro.MainActivity;
import com.example.yanadenv.principal.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class Inicio extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 123;


    Boolean isFABOpen = false;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    FloatingActionButton fab4;
    FloatingActionButton fab5;
    private String datosClinicos1;
    private String datosSocioEconomico;
    private String usuario;
    private String contrasenia;
    private static final String TAG2 = "FileSearcher";


    String token;

    private static Context appContext;

    // Método para inicializar el contexto de la aplicación
    public static void init(Context context) {
        if (appContext == null) {
            appContext = context.getApplicationContext();
        }
    }




    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
            //,
         //   Manifest.permission.WRITE_EXTERNAL_STORAGE
    };




    ArrayList<String> listaInformacion;
    private APIService mAPIService;
    String TAG = "Inicio";
    ArrayList<Participante> listParticipante;
    ConexionSQLiteHelper conn;
    //  ArrayList<Participante> listParticipante;


    // HashMap para almacenar los nombres de los elementos y sus símbolos
    private static HashMap<String, String> elementSymbols;

    static {
        elementSymbols = new HashMap<>();
        elementSymbols.put("Arsenico", "As");
        elementSymbols.put("Plomo", "Pb");
        elementSymbols.put("Molibdeno", "Mo");
        elementSymbols.put("Cadmio", "Cd");
        elementSymbols.put("Mercurio", "Hg");
    }



    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);

        // Inicializar appContext
        init(this);


        usuario = prefs.getString("usuario", "");
        contrasenia = prefs.getString("contrasenia", ""); // prefs.getString("nombre del campo" , "valor por defecto")

        token   = prefs.getString("tokenUnico", ""); // prefs.getString("nombre del campo" , "valor por defecto")

        setContentView(R.layout.activity_inicio);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                checkMermission();
            }
        }, 4000);

        Post post;
        ListView listViewPersonas;
        listViewPersonas = (ListView) findViewById(R.id.listaDatos);
        listParticipante = new ArrayList<Participante>();
        consulterListaDatos();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listViewPersonas.setAdapter(adaptador);

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Inicio.this, ParticipanteActualizacion.class);

                // Toast toast = Toast.makeText(getApplicationContext(),  listParticipante.get(position).getId() +"", Toast.LENGTH_SHORT);
                //  toast.show();
                SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
            //    editor.putString("idTablaAudio", listParticipante.get(position).ge() + "");
                editor.putString("idParticipante", listParticipante.get(position).getIdParticipante() + "");
                editor.putString("edadParticipante", listParticipante.get(position).getEdad() + "");
                editor.putString("numeroDocumento", listParticipante.get(position).getDocnumber() + "");
                editor.putString("idParticipanteServidor", listParticipante.get(position).getIdParticipanteServidor() + "");
               // listParticipante

                editor.commit();

                // intent.putExtra("objetoParticipante", listParticipante.get(position));

                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab5 = (FloatingActionButton) findViewById(R.id.fab5);


        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       if (!isFABOpen) {
                                           showFABMenu();
                                       } else {
                                           closeFABMenu();
                                       }
                                   }
                               }
        );
        fab1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(Inicio.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                }
        );


        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new AlertDialog.Builder(Inicio.this)
                        .setTitle("Mensaje")
                        .setMessage("¿Desea sincronizar los datos de la aplicación?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                consultarYGuardarDatosParticipantes();
                            }



                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("AlertDialog", "Negative");
                            }
                        })
                        .show();


                //     Toast.makeText(Inicio.this, "NO HAY DATOS NUEVOS A SINCRONIZAR", Toast.LENGTH_LONG).show();

            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new AlertDialog.Builder(Inicio.this)
                                                .setTitle("Mensaje")
                                                .setMessage("¿Desea enviar la información al servidor?")
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Log.d("AlertDialog", "Positive");
                                                        sendAudioClinicos();
                                                    }

                                                })
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Log.d("AlertDialog", "Negative");
                                                    }
                                                })
                                                .show();
                                    }
                                }
        );

        fab4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(Inicio.this)
                        .setTitle("Mensaje")
                        .setMessage("¿Desea subir los audios a la aplicación móvil?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            public void onClick(DialogInterface dialog, int which) {
                                // Solicitar acceso al directorio
                                UploadFile.solicitarAccesoDirectorio(Inicio.this);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("AlertDialog", "Negative");
                            }
                        })
                        .show();
            }
        });



        fab5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new AlertDialog.Builder(Inicio.this)
                        .setTitle("Mensaje")
                        .setMessage("¿Desea cerrar la sesión?")
                        .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("AlertDialog", "Positive");
                                // Eliminar las credenciales de SharedPreferences
                                SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.remove("usuario");  // Eliminar usuario
                                editor.remove("contrasenia");  // Eliminar contraseña
                                editor.apply();  // Aplicar los cambios

                                // Redirigir al usuario a la pantalla de inicio de sesión
                                Intent i = new Intent(Inicio.this, Login.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Para limpiar el backstack
                                startActivity(i);
                                finish();  // Cierra la actividad actual para evitar que el usuario regrese


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("AlertDialog", "Negative");
                            }
                        })
                        .show();

            }
        });

    }

    // Sobrescribir onActivityResult para recibir los datos después de solicitar acceso al directorio
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FileSearcher.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Aquí se procesarán los archivos después de obtener el acceso al directorio
            if (data != null) {
                Uri treeUri = data.getData();  // Obtén el URI del directorio seleccionado
                // Procesar el directorio seleccionado y obtener los archivos .txt
                procesarArchivosTxt(treeUri);
            }
        }
    }

    // Método para procesar los archivos .txt en el directorio seleccionado
    private void procesarArchivosTxt(Uri treeUri) {
        boolean sdDisponible = false;
        boolean sdAccesoEscritura = false;
        int contador = 0;

        try {
            // Usar el Uri (treeUri) para acceder al directorio seleccionado
            DocumentFile pickedDir = DocumentFile.fromTreeUri(this, treeUri);

            if (pickedDir != null && pickedDir.isDirectory()) {
                // Crear la carpeta donde se moverán los archivos
                crearCarpeta();

                // Obtener la lista de archivos del directorio seleccionado
                ArrayList<upload> listReadGroup = ObtenerListaReadGroup();

                // Recorrer los archivos en el directorio seleccionado (usar DocumentFile)
                for (DocumentFile file : pickedDir.listFiles()) {
                    if (file.isFile() && file.getName() != null && file.getName().endsWith(".txt")) {
                        String nombreArchivo = file.getName();

                        ArrayList<SongDto> listSongDto = getMusicInfosFromUri(file); // Método para obtener la info del archivo usando el Uri
                        for (int i = 0; i < listReadGroup.size(); i++) {
                            String dniParticipante = listReadGroup.get(i).getDni();
                            String nombreSiglaParticipante = listReadGroup.get(i).getNombreSigla();
                            String nombreArchivoBd = listReadGroup.get(i).getNombreGrabacion();

                            // Tomar solo la primera palabra del nombre del archivo
                            String primeraPalabra = obtenerPrimeraPalabra(nombreArchivoBd);

                            // Llamar al método que usa condicionales para convertir el nombre en el símbolo
                            String simboloQuimico = getElementSymbol(primeraPalabra);




                            for (SongDto songDto : listSongDto) {
                                String[] parts = nombreArchivo.split("_");

                                // Identificar el formato del archivo
                                if (parts.length >= 3) {
                                    // Compara al participante con los audios
                                    if (dniParticipante.equals(parts[0]) && (nombreSiglaParticipante +"_" + simboloQuimico + ".txt").equals(parts[3] + "_" + parts[4])) {
                                        // Mueve el archivo al directorio adecuado
                                        moveFileFromUri(file, songDto.songTitle, Environment.getExternalStorageDirectory().getAbsolutePath() + "/SamayCOVaudios");

                                        // Actualiza el estado de recepción
                                        actualizarReadGroupEstadoRecepcion(dniParticipante, listReadGroup.get(i).getIdLectura(), songDto.songTitle);
                                        contador++;
                                    }
                                }
                            }
                        }
                    }
                }

                if (contador > 0) {
                    Toast.makeText(Inicio.this, "Se subieron " + contador + " elementos al dispositivo", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Inicio.this, "No hay archivos a subir", Toast.LENGTH_LONG).show();
                }

            } else {
                Log.e("Ficheros", "No se pudo acceder al directorio o no es válido.");
            }
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al procesar archivos en el directorio seleccionado", ex);
        }
    }

    // Método para convertir el nombre del archivo en símbolo químico usando condicionales
    public static String getElementSymbol(String elementName) {
        if (elementName.equalsIgnoreCase("Arsenico")) {
            return "As";
        } else if (elementName.equalsIgnoreCase("Plomo")) {
            return "Pb";
        } else if (elementName.equalsIgnoreCase("Molibdeno")) {
            return "Mo";
        } else if (elementName.equalsIgnoreCase("Cadmio")) {
            return "Cd";
        } else if (elementName.equalsIgnoreCase("Mercurio")) {
            return "Hg";
        } else {
            return "Elemento no encontrado";
        }
    }

    // Método que obtiene la primera palabra de un string
    public static String obtenerPrimeraPalabra(String texto) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }
        // Dividir el texto en palabras y devolver la primera
        String[] palabras = texto.split(" ");
        return palabras[0];  // Retorna la primera palabra
    }

    // Este es un ejemplo simulado del método getMusicInfosFromUri, ya que no tengo el contexto exacto
    private static ArrayList<SongDto> getMusicInfosFromUri(String file) {
        // Aquí debes implementar el método real para obtener la información del archivo
        return new ArrayList<>();
    }



    private ArrayList<SongDto> getMusicInfosFromUri(DocumentFile file) {
        ArrayList<SongDto> songList = new ArrayList<>();

        SongDto song = new SongDto();
        song.songTitle = file.getName();
        song.path = file.getUri().toString(); // Usar Uri para la ruta del archivo
        songList.add(song);

        return songList;
    }

    private void moveFileFromUri(DocumentFile file, String fileName, String outputPath) {
        try {
            InputStream in = getContentResolver().openInputStream(file.getUri());
            File outFile = new File(outputPath, fileName);
            OutputStream out = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            // Eliminar el archivo original si es necesario
            file.delete();

        } catch (IOException e) {
            Log.e("MoverArchivo", "Error al mover el archivo: " + fileName, e);
        }
    }




    private void consultarYGuardarDatosParticipantes() {
        conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();
        SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        List<String> listaIdParticipanteServidor = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES + " WHERE " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "' AND " + Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "'", null);

        while (cursor.moveToNext()) {
            String idParticipanteServidor = cursor.getString(35);
            listaIdParticipanteServidor.add(idParticipanteServidor);
        }

        cursor.close();
        db.close();

        token = prefs.getString("tokenUnico", "");
        mAPIService = ApiUtils.getAPIService();


        for (String id : listaIdParticipanteServidor) {
            Call<Participant> call1 = mAPIService.getParticipante("Bearer " + token, id);

            call1.enqueue(new Callback<Participant>() {
                @Override
                public void onResponse(Call<Participant> call, Response<Participant> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Participant participant = response.body();
                        String LabPlomo = (participant.getLabPlomo() != null) ? participant.getLabPlomo().toString() : "";
                        String LabMercurio = (participant.getLabMercurio() != null) ? participant.getLabMercurio().toString() : "";
                        String LabCadmio = (participant.getLabCadmio() != null) ? participant.getLabCadmio().toString() : "";
                        String LabMolibdeno = (participant.getLabmolibdeno() != null) ? participant.getLabmolibdeno().toString() : "";
                        String Labarsenico = (participant.getLabarsenico() != null) ? participant.getLabarsenico().toString() : "";



                        String calidad = (participant.getQuality() != null) ? participant.getQuality().toString() : "";
                        String estado = (participant.getState() != null) ? participant.getState().toString() : "";
                        String descripcion = (participant.getDescriptionstate() != null) ? participant.getDescriptionstate().toString() : "";

                        // Guardar los datos en SharedPreferences
                        editor.putString(participant.getDocnumber() + "_LabPlomo", LabPlomo);
                        editor.putString(participant.getDocnumber() + "_LabMercurio", LabMercurio);
                        editor.putString(participant.getDocnumber() + "_LabCadmio", LabCadmio);
                        editor.putString(participant.getDocnumber() + "_LabArsenico", Labarsenico);
                        editor.putString(participant.getDocnumber() + "_LabMolibdeno", LabMolibdeno);



                        editor.putString(participant.getDocnumber() + "_Calidad", calidad);
                        editor.putString(participant.getDocnumber() + "_Estado", estado);
                        editor.putString(participant.getDocnumber() + "_Descripcion", descripcion);
                        editor.apply();

                        // Opcional: llamar a un método de actualización de UI si es necesario
                    } else {
                        manejarError(response);
                    }
                }

                @Override
                public void onFailure(Call<Participant> call, Throwable t) {
                    Log.e("API_ERROR", "No se pudo conectar con la API.");
                }
            });
        }
    }



    private void manejarError(Response<Participant> response) {
        int statusCode = response.code();
        String errorMessage = response.message();
        Log.e(TAG, "Response was not successful: " + statusCode + " " + errorMessage);

        switch (statusCode) {
            case 400:
                Toast.makeText(getApplicationContext(), "Solicitud incorrecta", Toast.LENGTH_SHORT).show();
                break;
            case 401:
                Toast.makeText(getApplicationContext(), "No autorizado", Toast.LENGTH_SHORT).show();
                break;
            case 404:
                Toast.makeText(getApplicationContext(), "Participante no encontrado", Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error desconocido: " + statusCode, Toast.LENGTH_SHORT).show();
                break;
        }

        if (response.errorBody() != null) {
            try {
                String errorBody = response.errorBody().string();
                Log.e(TAG, "Error body: " + errorBody);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void crearCarpeta() {

       String outputPath =  (Environment.getExternalStorageDirectory().getAbsolutePath() + "/SamayCOVaudios");
        File dir = new File(outputPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void actualizarReadGroupEstadoRecepcion(String dni, String nameSigla, String nombreArchivo) {
        conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);

        // VERIFICAR CON DETALLE SI FUNCIONA CORRECTO
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {dni, nameSigla};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_flag, 2);
        values.put(Utilidades.CAMPO_ruta, nombreArchivo);
        db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_dni + "=?" + " and " +
                Utilidades.CAMPO_idLectura + "=?" + " and " +    Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " +
                Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "' ", parametros);
        db.close();
    }

    private ArrayList<upload> ObtenerListaReadGroup() {
        conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();
        Readgroup dato = null;
        ArrayList<upload> listReadgroup = new ArrayList<upload>();

      //  Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PARTICIPANTES_READGROUP, null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PARTICIPANTES_READGROUP + " " +
                " where " +
              // CODIGO COMENTE POR MOMENTO HOY 19 07 2024
                //  Utilidades.CAMPO_flag + " = '" + 2 + "'" + " and " +

                Utilidades.CAMPO_flag + " = '" + 0 + "'" + " and " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " +
                Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "' ", null);



        while (cursor.moveToNext()) {
            upload objReadgroup = new upload();
            objReadgroup.setIdParticipante(cursor.getString(1));
            objReadgroup.setIdLectura(cursor.getString(2));
            objReadgroup.setNombreGrabacion(cursor.getString(3));
            objReadgroup.setFile(cursor.getString(4));
            objReadgroup.setFlag(cursor.getInt(5));
            objReadgroup.setDni(cursor.getString(6));
            objReadgroup.setNombreSigla(cursor.getString(7));
            listReadgroup.add(objReadgroup);
        }
        db.close();

        return listReadgroup;
    }

    private void moveFile(File file, File dir) throws IOException {
        File newFile = new File(dir, file.getName());
        FileChannel outputChannel = null;
        FileChannel inputChannel = null;


        try {
            outputChannel = new FileOutputStream(newFile).getChannel();
            inputChannel = new FileInputStream(file).getChannel();
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            inputChannel.close();
            file.delete();
        } finally {
            if (inputChannel != null) inputChannel.close();
            if (outputChannel != null) outputChannel.close();
        }

    }

    private void moveFile(String inputPath, String inputFile, String outputPath) {


        if (!inputPath.equals(outputPath+"/"+inputFile)) {

            InputStream in = null;
            OutputStream out = null;
            try {

                //create output directory if it doesn't exist
                File dir = new File(outputPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                in = new FileInputStream(inputPath);
                out = new FileOutputStream(outputPath + "/" + inputFile);

                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();
                in = null;

                // write the output file
                out.flush();
                out.close();
                out = null;

                // delete the original file
                //  new File(inputPath + inputFile).delete();
            } catch (FileNotFoundException fnfe1) {
                Log.e("tag", fnfe1.getMessage());
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, llamar a getMusicInfos
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    ArrayList<SongDto> musicInfos = getMusicInfos(Inicio.this);
                }
                // Hacer algo con musicInfos, como actualizar la UI
            } else {
                // Permiso denegado, manejar la situación
                //.makeText(this, "Permiso de lectura de almacenamiento externo denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  ArrayList<SongDto> getMusicInfos(Context context) {
        ArrayList<SongDto> musicInfos = new ArrayList<>();

        // Buscar en el almacenamiento externo
       // buscarEnAlmacenamientoExterno(context, musicInfos);

        // Buscar en el almacenamiento interno
    //    buscarEnAlmacenamientoInterno(context, musicInfos);

        buscarEnAlmacenamiento(context, musicInfos);


        return musicInfos;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  void buscarEnAlmacenamiento(Context context, ArrayList<SongDto> musicInfos) {
        // Buscar en almacenamiento interno completo
       // buscarArchivos(context.getFilesDir(), musicInfos);

        // Buscar en el directorio cache interno
        //buscarArchivos(context.getCacheDir(), musicInfos);

        //ArrayList<SongDto> musicInfos = new ArrayList<>();
        buscarArchivosEnQhawawa( musicInfos);


        //buscarArchivosEnQhawawa(musicInfos);

        // Buscar en almacenamiento externo específico de la aplicación
        File[] externalDirs = context.getExternalFilesDirs(null);
        for (File externalDir : externalDirs) {
            if (externalDir != null) {
                buscarArchivos(musicInfos);
            }
        }

        // Buscar en la carpeta de descargas pública
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (downloadsDir != null) {
            buscarArchivos(musicInfos);
        }

        // Buscar en almacenamiento externo raíz
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            File[] externalStorageVolumes = context.getExternalMediaDirs();
            for (File externalVolume : externalStorageVolumes) {
                if (externalVolume != null) {
                    buscarArchivos( musicInfos);
                }
            }
        } else {
            buscarArchivos(musicInfos);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void buscarEnAlmacenamientoExterno(Context context, ArrayList<SongDto> musicInfos) {
        Uri contentUri = MediaStore.Files.getContentUri("external");

        String selection = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt");
        String[] selectionArgs = new String[]{ mimeType };

        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATA
        };

        Cursor cursor = context.getContentResolver().query(contentUri, projection, selection, selectionArgs, null);

        if (cursor != null) {
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
            int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);

            while (cursor.moveToNext()) {
                String fileName = cursor.getString(nameColumn);
                String filePath = cursor.getString(dataColumn);

                if (fileName.endsWith(".txt")) {
                    SongDto music = new SongDto();
                    music.path = filePath;
                    music.songTitle = fileName;
                    musicInfos.add(music);
                }
            }
            cursor.close();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void buscarEnAlmacenamientoInterno(Context context, ArrayList<SongDto> musicInfos) {
        // Buscar en almacenamiento interno
        File internalDir = context.getFilesDir();
       // buscarArchivos(internalDir, musicInfos);

        // Buscar en almacenamiento externo específico de la aplicación
        File externalDir = context.getExternalFilesDir(null);
        if (externalDir != null) {
         //   buscarArchivos(externalDir, musicInfos);
        }

        // Buscar en la carpeta de descargas
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (downloadsDir != null) {
          //  buscarArchivos(downloadsDir, musicInfos);
        }
    }




    // Función principal para buscar archivos .txt en el directorio de descargas

    // Función principal para buscar archivos .txt en el directorio Qhawawa en el almacenamiento interno
    // Función principal para buscar archivos .txt en el directorio Qhawawa en el almacenamiento interno

    // Función principal para buscar archivos .txt en el directorio Qhawawa en el almacenamiento interno
    // Método para crear la carpeta Qhawawa en la raíz del almacenamiento externo y buscar archivos .txt en ella
    public void buscarArchivosEnQhawawa( ArrayList<SongDto> musicInfos) {
        // Verificar si los permisos están otorgados
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }

        // Verificar que el almacenamiento externo esté disponible y en modo de escritura
        if (!isExternalStorageWritable()) {
            Log.e(TAG2, "El almacenamiento externo no está disponible o no es accesible.");
            return;
        }

        // Obtener el directorio de Documentos del almacenamiento externo
        File documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        // Crear o obtener el directorio Qhawawa dentro de Documentos
        File qhawawaDir = new File(documentsDir, "Qhawawa");

        if (!qhawawaDir.exists()) {
            boolean dirCreated = qhawawaDir.mkdir();
            if (!dirCreated) {
                Log.e(TAG2, "No se pudo crear el directorio Qhawawa en Documentos.");
                return;
            } else {
                Log.d(TAG2, "Directorio Qhawawa creado con éxito en Documentos.");
            }
        }

        // Buscar archivos .txt en la carpeta Qhawawa
        buscarArchivos( musicInfos);

      //  buscarArchivos(directorio, musicInfos);

// Ahora musicInfos contendrá todos los archivos .txt encontrados en el directorio
        for (SongDto song : musicInfos) {
            Log.d("ArchivoTXT", "Archivo encontrado: " + song.songTitle + " en " + song.path);
        }

    }



    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    // Función recursiva para buscar archivos .txt en un directorio dado


//    private  void buscarArchivos(File dir, ArrayList<SongDto> musicInfos) {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE);
//
//            return; // Salir de la función hasta que se otorgue el permiso
//        }
//
//
//
//
//        // Verificar que el directorio sea válido
//        if (dir == null || !dir.isDirectory()) {
//            Log.e(TAG2, "El directorio proporcionado no es válido: " + (dir != null ? dir.getAbsolutePath() : "null"));
//            return;
//        }
//
//        Log.d(TAG2, "Buscando en: " + dir.getAbsolutePath());
//
//        // Obtener la lista de archivos
//        File[] files = dir.listFiles();
//
//        // Verificar si hay archivos
//        if (files == null || files.length == 0) {
//            Log.e(TAG2, "No se encontraron archivos en el directorio: " + dir.getAbsolutePath());
//            return;
//        }
//
//        // Iterar sobre cada archivo
//        for (File file : files) {
//            Log.d(TAG2, "Archivo encontrado: " + file.getName());
//
//            // Si es un archivo .txt, agregarlo a la lista
//            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
//                SongDto music = new SongDto();
//                music.path = file.getAbsolutePath();
//                music.songTitle = file.getName();
//                musicInfos.add(music);
//                Log.d(TAG2, "Archivo .txt encontrado: " + file.getAbsolutePath());
//            } else if (file.isDirectory()) {
//                // Buscar recursivamente en subdirectorios
//                buscarArchivos(file, musicInfos);
//            }
//        }
//    }

    public void buscarArchivos(ArrayList<SongDto> musicInfos) {
        // Verificar permisos antes de proceder
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        } else {
            // Permiso concedido, proceder con la búsqueda de archivos
            realizarBusquedaArchivos(musicInfos);
        }
    }



    private void realizarBusquedaArchivos(ArrayList<SongDto> musicInfos) {
        // Primero busca en el almacenamiento externo usando MediaStore
        Log.d(TAG2, "Buscando archivos en almacenamiento externo con MediaStore...");
        FileSearcher.buscarArchivosConMediaStore(getApplicationContext(), musicInfos);

        // Luego busca en el almacenamiento interno de la aplicación
        Log.d(TAG2, "Buscando archivos en almacenamiento interno...");
        FileSearcher.buscarArchivosInternos(getApplicationContext(), musicInfos);

        // Finalmente, busca en la carpeta de documentos, donde también podría haber archivos relevantes
        Log.d(TAG2, "Buscando archivos en la carpeta de Documentos...");
        File documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (documentsDir != null) {
          //  FileSearcher.buscarArchivosEnDirectorio(documentsDir, musicInfos);
        }
    }



    public static ArrayList<SongDto> getMusicInfosInterno(Context context) {

        ArrayList<SongDto> musicInfos = new ArrayList<SongDto>();

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SamayCOVaudios");

        String path = dir.getAbsolutePath();

        File carpeta = new File(path);
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {


        }
        else {
            for (int i=0; i< listado.length; i++) {
                SongDto songDtoObj = new SongDto();

                songDtoObj.songTitle =listado [i];
               // System.out.println(listado[i]);
                musicInfos.add(songDtoObj);
            }
        }
        return musicInfos;
    }


    public static boolean isSDCardAvailable(Context context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return true;
        else
            return false;
    }


    public static HashSet<String> getExternalMounts() {
        final HashSet<String> out = new HashSet<String>();
        String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
        String s = "";
        try {
            final Process process = new ProcessBuilder().command("mount").redirectErrorStream(true).start();
            process.waitFor();
            final InputStream is = process.getInputStream();
            final byte[] buffer = new byte[1024];
            while (is.read(buffer) != -1) {
                s = s + new String(buffer);
            }
            is.close();
        } catch (final Exception e) {
            e.printStackTrace();
        } // parse output
        final String[] lines = s.split("\n");
        for (String line : lines) {
            if (!line.toLowerCase(Locale.US).contains("asec")) {
                if (line.matches(reg)) {
                    String[] parts = line.split(" ");
                    for (String part : parts) {
                        if (part.startsWith("/"))
                            if (!part.toLowerCase(Locale.US).contains("vold")) out.add(part);
                    }
                }
            }
        }
        return out;
    }


    // Checks if a volume containing external storage is available
// for read and write.

    public static boolean testSaveLocationExists() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;

        // If SD card is mounted
        if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
            status = true;
        }
        // If no SD card
        else {
            status = false;
        }
        return status;
    }


    private static final File EXTERNAL_STORAGE_DIRECTORY = getDirectory("EXTERNAL_STORAGE", "/sdcard");

    static File getDirectory(String variableName, String defaultPath) {
        String path = System.getenv(variableName);
        return path == null ? new File(defaultPath) : new File(path);
    }


    private void consulterListaDatos() {
        conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();
        Participante dato = null;
        // listParticipante = null;
        //listParticipante = new ArrayList<ParticipanteAudios>();

     //   Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES, null);

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES + " where " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " + Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "' ", null);


        while (cursor.moveToNext()) {
            dato = new Participante();
            dato.setId(cursor.getInt(0));
            dato.setName(cursor.getString(1));
            dato.setLastname(cursor.getString(2));
            dato.setDocnumber(cursor.getString(5));
            dato.setEdad(cursor.getString(28));
            dato.setIdParticipante(cursor.getString(32));
            dato.setIdParticipanteServidor(cursor.getString(35));

            listParticipante.add(dato);
        }

        db.close();
        obtenerLista();
    }











    ArrayList<Post> obtenerListaParticipante() {
        //  conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();
        Post dato = null;
        AssigneeDTO assigneeDTO = null;
        SocieconomicDTO socieconomicDTO = null;
        List<ClinicdataDTO> clinicdataDTOs = null;


        ArrayList<Post> objListParticipante = new ArrayList<Post>();

       // Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES, null);

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES + " where " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " + Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "' ", null);

        while (cursor.moveToNext()) {
            //Ingreso de List en Participante
            dato = new Post();
            // dato.setId(cursor.getInt(0));
            dato.setName(cursor.getString(1));
            dato.setLastname(cursor.getString(2));
            dato.setSex(cursor.getString(3));
            dato.setDoctype(cursor.getString(4));
            dato.setDocnumber(cursor.getString(5));
            dato.setCampainId(cursor.getString(6));
            dato.setCountryId(cursor.getString(7));
            dato.setContactnumber(cursor.getString(8));
            dato.setCivilstatus(cursor.getString(9));
            dato.setWeight(cursor.getInt(10));
            dato.setStature(cursor.getInt(11));
            dato.setImc(cursor.getDouble(12));

            //Ingreso de List Apoderado
            assigneeDTO = new AssigneeDTO();
            assigneeDTO.setName(cursor.getString(13));
            assigneeDTO.setLastname(cursor.getString(14));
            assigneeDTO.setSex(cursor.getString(15));
            assigneeDTO.setDoctype(cursor.getString(16));
            assigneeDTO.setDocnumber(cursor.getString(17));
            dato.setAssigneeDTO(assigneeDTO);
            ////// Ingreso socio Economico SocioEconomico
            socieconomicDTO = new SocieconomicDTO();
            socieconomicDTO.setApartment(cursor.getString(18));
            socieconomicDTO.setMaterial(cursor.getString(19));
            socieconomicDTO.setElectric(cursor.getString(20));
            socieconomicDTO.setWater(cursor.getString(21));
            socieconomicDTO.setDrain(cursor.getString(22));
            socieconomicDTO.setFamilynumber(cursor.getInt(23));
            socieconomicDTO.setChildrennumber(cursor.getInt(24));
            socieconomicDTO.setAdultnumber(cursor.getInt(25));
            socieconomicDTO.setInfantnumber(cursor.getInt(26));
            socieconomicDTO.setPregnantnumber(cursor.getInt(27));
            ///////////// poner datos socio economico aqui
            //socieconomicDTO.setApartment();
            socieconomicDTO = null;
            dato.setSocieconomicDTO(socieconomicDTO);
            ///////////////////////////////////////////////
            ///// POR AGREGAR DESPUES CUANDO FRERLY LO TENGA LISTO
            ///////////
            dato.setClinicdataDTOs(clinicdataDTOs);

            ///////////////////////////////////////////////
            ///// INSERTA TODOS LOS DATOS AL OBJETO
            ///////////

            objListParticipante.add(dato);
        }
        db.close();
        return objListParticipante;
        // obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();
        SQLiteDatabase db = conn.getWritableDatabase(); // Obtener una instancia de la base de datos en modo escritura
        for (int i = 0; i < listParticipante.size(); i++) {
            String nombre = listParticipante.get(i).getName();
            String apellido = listParticipante.get(i).getLastname();
            String idParticipante = listParticipante.get(i).getIdParticipante(); // Obtener el ID del participante

            // Verificar si el nombre es nulo o vacío
            if (nombre != null && !nombre.trim().isEmpty() && !nombre.equalsIgnoreCase("null")) {
                listaInformacion.add(apellido + " " + nombre);
            } else {
                // Si el nombre es nulo o vacío, eliminar el participante de la base de datos
                eliminarParticipanteDeBaseDeDatos(db, idParticipante);
            }
        }
        db.close(); // Cerrar la base de datos después de que termine el bucle
    }

    // Método para eliminar un participante de la base de datos por su ID
    private void eliminarParticipanteDeBaseDeDatos(SQLiteDatabase db, String idParticipante) {
        String[] whereArgs = { idParticipante };
        int rowsDeleted = db.delete(Utilidades.TABLA_GENERALES, Utilidades.CAMPO_idParticipante + "=?", whereArgs);

        if (rowsDeleted > 0) {
            Log.d("EliminarParticipante", "Participante con ID " + idParticipante + " eliminado correctamente.");
        } else {
            Log.e("EliminarParticipante", "No se pudo eliminar el participante con ID " + idParticipante);
        }
    }


    private void showFABMenu() {
        isFABOpen = true;
        fab1.animate().translationY(getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(getResources().getDimension(R.dimen.standard_155));
        fab4.animate().translationY(getResources().getDimension(R.dimen.standard_205));
        fab5.animate().translationY(getResources().getDimension(R.dimen.standard_255));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
        fab4.animate().translationY(0);
        fab5.animate().translationY(0);

    }


    private ArrayList <Participante> consulterListaDatosFull() {

        conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();

        ArrayList<Participante> listParticipante = new ArrayList<Participante>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES + " where " +
                Utilidades.CAMPO_flag + " = '" + 0 +"'" + " and " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " +
                Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "' ", null);

        while (cursor.moveToNext()) {
            Participante dato = new Participante();
            // dato.setId(cursor.getInt(0));
            dato.setName(cursor.getString(1));
            dato.setLastname(cursor.getString(2));
            dato.setSex(cursor.getString(3));
            dato.setDoctype(cursor.getString(4));
            dato.setDocnumber(cursor.getString(5));
            dato.setCampainId(cursor.getString(6));
            dato.setCountryId(cursor.getString(7));
            dato.setContactnumber(cursor.getString(8));
            dato.setCivilstatus(cursor.getString(9));
            dato.setWeight(cursor.getInt(10));
            dato.setStature(cursor.getInt(11));
            dato.setImc(cursor.getDouble(12));
            dato.setNameApoderado(cursor.getString(13));
            dato.setLastnameApoderado(cursor.getString(14));
            dato.setSexApoderado(cursor.getString(15));
            dato.setDoctypeApoderado(cursor.getString(16));
            dato.setDocnumberApoderado(cursor.getString(17));
            dato.setApartament(cursor.getString(18));
            dato.setMaterial(cursor.getString(19));
            dato.setElectric(cursor.getString(20));
            dato.setWater(cursor.getString(21));
            dato.setDrain(cursor.getString(22));
            dato.setFamilyNumber(cursor.getString(23));
            dato.setChildrenNumber(cursor.getString(24));
            dato.setAdultNumber(cursor.getString(25));
            dato.setInfantNumber(cursor.getString(26));
            dato.setPregnatNumber(cursor.getString(27));
            dato.setEdad(cursor.getString(28));
            dato.setGestante(cursor.getString(29));
            dato.setEducacion(cursor.getString(30));
            dato.setFlag(cursor.getInt(31));
            dato.setIdParticipante(cursor.getString(32));
            listParticipante.add(dato);
        }

        return listParticipante;
    }



    public void sendAudioClinicos() {

        try {

            ArrayList<Participante> listParticipante = new ArrayList<>();
            listParticipante = consulterListaDatosFull();


            for (int i =0 ; i  <listParticipante.size(); i ++){

                String name = listParticipante.get(i).getName().toString();
                String lastname = listParticipante.get(i).getLastname().toString();



                String sex = listParticipante.get(i).getSex().toString();
                String doctype =  listParticipante.get(i).getDoctype().toString();
                String docnumber =  listParticipante.get(i).getDocnumber().toString();
                String campainId =  listParticipante.get(i).getCampainId().toString();
                String contactnumber =  listParticipante.get(i).getContactnumber().toString();
                String civilstatus = listParticipante.get(i).getCivilstatus().toString();

                String edad = listParticipante.get(i).getEdad().toString();
                String gestante = listParticipante.get(i).getGestante().toString();
                String educacion = listParticipante.get(i).getEducacion();

                int weight = listParticipante.get(i).getWeight();
                int stature = listParticipante.get(i).getStature();
                double imc =  listParticipante.get(i).getImc();


                AssigneeDTO assigneeDTO = new AssigneeDTO();
                assigneeDTO.setName(listParticipante.get(i).getNameApoderado());
                assigneeDTO.setLastname(listParticipante.get(i).getLastnameApoderado());
                assigneeDTO.setSex(listParticipante.get(i).getSexApoderado());
                assigneeDTO.setDoctype(listParticipante.get(i).getDoctypeApoderado());
                assigneeDTO.setDocnumber(listParticipante.get(i).getDocnumberApoderado());


                String idTablaGeneral = listParticipante.get(i).getIdParticipante();

                SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);

                /////LISTA CLINICOS
                datosClinicos1 = prefs.getString(idTablaGeneral, "");


                //  datosClinicos1 = prefs.getString(idTablaGeneral+"_"+"socioeconomico", "");
                Gson clinic1 = new Gson();
                Type collectionType = new TypeToken<Collection<ClinicdataDTO>>(){}.getType();
                Collection<ClinicdataDTO> enums = clinic1.fromJson(datosClinicos1, collectionType);



                /////LISTA SOCIOECONOMICO
               datosSocioEconomico =  prefs.getString(idTablaGeneral+"_"+"socioeconomico", "");
                Gson clinic1_1 = new Gson();
                Type collectionType_1 = new TypeToken<Collection<ClinicdataDTO>>(){}.getType();
                Collection<ClinicdataDTO> enums2 = clinic1_1.fromJson(datosSocioEconomico, collectionType_1);

                // Asegurarse de que enums no sea null. Si es null, inicializarlo como una lista vacía.
                if (enums == null) {
                    enums = new ArrayList<>();
                }

// Verificar si enums2 es null antes de iterar
                if (enums2 != null) {
                    for (ClinicdataDTO val : enums2) {
                        enums.add(val); // Agregar los valores de enums2 a enums
                    }
                } else {
                    Log.d("sendAudioClinicos", "enums2 es null, no se agregarán elementos a enums");
                }

// Crear una nueva lista a partir de los datos combinados
                List<ClinicdataDTO> theList = new ArrayList<>(enums);




                SocieconomicDTO objSocioEconomico = new SocieconomicDTO();
                objSocioEconomico.setApartment("PROPIA");
                objSocioEconomico.setPregnantnumber(0);
                objSocioEconomico.setInfantnumber(0);
                objSocioEconomico.setAdultnumber(0);
                objSocioEconomico.setChildrennumber(0);
                objSocioEconomico.setFamilynumber(0);
                objSocioEconomico.setDrain("NOT");
                objSocioEconomico.setWater("NOT");
                objSocioEconomico.setElectric("NOT");
                objSocioEconomico.setMaterial("ADOBE");
                objSocioEconomico = null;


                sendPost(name,lastname,sex,doctype,docnumber, campainId,
                        "173",contactnumber,civilstatus,
                        weight,stature,
                        imc,assigneeDTO,
                        theList,objSocioEconomico,
                        edad,gestante,educacion,
                        idTablaGeneral);
            }

            ArrayList<upload> listDeAudios = ListadeAudiosClinicos();

            int cantidad = 0;

            for (int i = 0; i < listDeAudios.size(); i++) {
                String nombreArchivo = listDeAudios.get(i).getNombreArchivo();
                if (!nombreArchivo.isEmpty() && listDeAudios.get(i).getFlag() == 2) {


                    FileSearcher.solicitarAccesoDirectorio(Inicio.this);
                    new UploadFile(listDeAudios.get(i).getIdLectura(), listDeAudios.get(i).getIdParticipante(), listDeAudios.get(i).getNombreArchivo(),"Bearer "+token,this).execute();

                    // eliminarAudioRegistrado(listDeAudios.get(i).getIdLectura(), listDeAudios.get(i).getIdParticipante());
                    actualizarEstadoAudioServidor(listDeAudios.get(i).getIdLectura(), listDeAudios.get(i).getIdParticipante());
                    cantidad++;
                }
            }

            if (cantidad > 0) {
                Toast.makeText(getApplication(), "SE SUBIERON " + cantidad + " TEXTO CON EXITO AL SERVIDOR", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "NO EXISTEN TEXTO CLINICOS EN EL DISPOSITIVO", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

            Toast.makeText(getApplication(), "OCURRIO UN ERROR", Toast.LENGTH_LONG).show();
        }
    }

    public void sendPost(
            String name,
            String lastname,
            String sex,
            String doctype,
            String docnumber,
            String campainId,
            String countryId,
            String contactnumber,
            String civilstatus,
            int weight,
            int stature,
            double imc,
            AssigneeDTO assigneeDTO,
            List<ClinicdataDTO> clinicdataDTOs,
            SocieconomicDTO socieconomicDTO,
            String edad,
            String gestante,
            String educacion,
            String idTablaGeneral

    ) {

        for (int i =0 ;  i < clinicdataDTOs.size(); i++){

            if (clinicdataDTOs.get(i).getValue() == null){

                clinicdataDTOs.get(i).setValue("");
            }

        }

        Post post = new Post(
                name,
                lastname,
                sex,
                doctype,
                docnumber,
                campainId,
                countryId,
                contactnumber,
                civilstatus,
                weight,
                stature,
                imc,
                Integer.parseInt(edad),
                gestante,
                educacion,
                assigneeDTO,
                clinicdataDTOs,
                socieconomicDTO
        );

        int edadEntera = Integer.parseInt(edad);
        if (edadEntera >= 18) {
            assigneeDTO.setSex(null);
        }



        mAPIService = ApiUtils.getAPIService();

        Call<Void> call1 = mAPIService.savePost("Bearer "+token,post);

        call1.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    String location = response.headers().value(3);
                    String[] parts = location.split("/participant/");

                    actulizarEstadoTablaGenerla( idTablaGeneral );
                    String dato = parts[1];
                    actualizarEstadoAudioConIdParticipante(dato,idTablaGeneral);
                    actualizarIdParticipanteGeneral(dato,idTablaGeneral);

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                Toast.makeText(Inicio.this, "Ocurrio un error al registrar participante", Toast.LENGTH_LONG).show();
            }
        });
    }

    public ArrayList<upload> ListadeAudiosClinicos() {
        //  conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);

        SQLiteDatabase db = conn.getReadableDatabase();
        Readgroup dato = null;
        ArrayList<upload> listaAudioCarga;
        listaAudioCarga = new ArrayList<upload>();


        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PARTICIPANTES_READGROUP + " where "+
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " + Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "' ", null);

        while (cursor.moveToNext()) {
            upload objAudioCarga = new upload();
//
//            +CAMPO_ID+" "+ "INTEGER PRIMARY KEY, "
//                    +CAMPO_idParticipante+" TEXT, "
//                    +CAMPO_idLectura+" TEXT, "
//                    +CAMPO_name+" TEXT, "
//                    +CAMPO_ruta+" TEXT)";

            objAudioCarga.setIdParticipante(cursor.getString(8));
            objAudioCarga.setIdLectura(cursor.getString(2));
            objAudioCarga.setNombreArchivo(cursor.getString(4));
            objAudioCarga.setFlag(cursor.getInt(5));
            listaAudioCarga.add(objAudioCarga);
        }

        db.close();

        return listaAudioCarga;
    }

    private void actualizarEstadoAudioServidor(String idLectura, String idParticipante) {
        // conn = new ConexionSQLiteHelper(getApplication(), Utilidades.CAMPO_baseDeDatos, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {idLectura, idParticipante,usuario,contrasenia};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_flag, 1);
        db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_idLectura + "=?" + " and " +
                Utilidades.CAMPO_idParticipanteServidor + "=?" + " and " +
                Utilidades.CAMPO_usuario + "=?" + " and " +
                Utilidades.CAMPO_contrasenia  + "=?" , parametros);
        db.close();
    }

    private void actulizarEstadoTablaGenerla(String idTablaGenerales) {
        // conn = new ConexionSQLiteHelper(getApplication(), Utilidades.CAMPO_baseDeDatos, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {idTablaGenerales,usuario,contrasenia};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_flag, 1);
        //db.update(Utilidades.TABLA_GENERALES, values, Utilidades.CAMPO_idParticipante + "=?" , parametros);

        db.update(Utilidades.TABLA_GENERALES, values, Utilidades.CAMPO_idParticipante + "=?" + " and " +
                Utilidades.CAMPO_usuario + "=?" + " and " +
                Utilidades.CAMPO_contrasenia  + "=?" , parametros);
        db.close();
    }

    private void actualizarEstadoAudioConIdParticipante(String idParticipanteServidor, String idParticipante) {
        // conn = new ConexionSQLiteHelper(getApplication(), Utilidades.CAMPO_baseDeDatos, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {idParticipante, usuario,contrasenia};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_idParticipanteServidor, idParticipanteServidor);
      //  db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_idParticipante + "=?" , parametros);
        db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_idParticipante + "=?" + " and " +
                Utilidades.CAMPO_usuario + "=?" + " and " +
                Utilidades.CAMPO_contrasenia  + "=?", parametros);
        db.close();
    }

    private void actualizarIdParticipanteGeneral(String idParticipanteServidor, String idParticipante) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {idParticipante, usuario,contrasenia};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_idParticipanteServidor, idParticipanteServidor);
        //  db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_idParticipante + "=?" , parametros);
        db.update(Utilidades.TABLA_GENERALES, values, Utilidades.CAMPO_idParticipante + "=?" + " and " +
                Utilidades.CAMPO_usuario + "=?" + " and " +
                Utilidades.CAMPO_contrasenia  + "=?", parametros);
        db.close();
    }

    // Modifica tu método buscarArchivos para que use la nueva clase FileSearcher






    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

    }


    private void checkMermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.isAnyPermissionPermanentlyDenied()) {
                    checkMermission();
                } else if (report.areAllPermissionsGranted()) {
                    // copy some things
                } else {
                    checkMermission();
                }

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }




}