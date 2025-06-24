package com.example.yanadenv.audio;

import java.text.DecimalFormat;
import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yanadenv.R;
import com.example.yanadenv.conexion.ConexionSQLiteHelper;
import com.example.yanadenv.data.model.Readgroup;
import com.example.yanadenv.entidades.ParticipanteAudios;
import com.example.yanadenv.entidades.upload;
import com.example.yanadenv.principal.utilidades.Utilidades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.text.TextUtils.isEmpty;

public class Control extends Fragment implements ServiceConnection, SerialListener {

    private Object UploadFile;
    String formattedDate = "";
    private String deviceAddress;
    private SerialService service;

    private TextView logText;
    private String numeroDocumento;
    private String nombreArchivoGlobal;

    private boolean initialStart = true;
    String idTablaParticipante;
    String nombreArchivo;
    ByteArrayOutputStream rawAudioStream;
    ArrayList<String> datoLectura;
    private boolean activeAudioStreaming = false;
    ConexionSQLiteHelper conn;
    ArrayList<ParticipanteAudios> listParticipante;
    Spinner lv;
    ArrayAdapter<String> adapter;

    private enum Connected {False, Pending, True}

    private enum DeviceMode {Command, Stream}

    private Connected connected = Connected.False;
    private DeviceMode deviceMode = DeviceMode.Command;
    String uploadAudioName;
    File[] audioFilesStored;
    private String usuario;
    private String contrasenia;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;


    /*
     * Lifecycle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        deviceAddress = getArguments().getString("device");
        rawAudioStream = new ByteArrayOutputStream();
        conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
    }

    @Override
    public void onDestroy() {
        if (connected != Connected.False)
            disconnect();
        getActivity().stopService(new Intent(getActivity(), SerialService.class));
        super.onDestroy();
    }

    @Override
    public void onStart() {
        LocalDateTime now = null;
        formattedDate = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        DateTimeFormatter dateFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedDate = now.format(dateFormatter);
        }

        super.onStart();
        if (service != null)
            service.attach(this);
        else
            getActivity().startService(new Intent(getActivity(), SerialService.class)); // prevents service destroy on unbind from recreated activity caused by orientation change
    }

    @Override
    public void onStop() {
        if (service != null && !getActivity().isChangingConfigurations())
            service.detach();
        super.onStop();
    }

    @SuppressWarnings("deprecation")
    // onAttach(context) was added with API 23. onAttach(activity) works for all API versions
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        getActivity().bindService(new Intent(getActivity(), SerialService.class), this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDetach() {
        try {
            getActivity().unbindService(this);
        } catch (Exception ignored) {
        }
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (initialStart && service != null) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = ((SerialService.SerialBinder) binder).getService();
        service.attach(this);
        if (initialStart && isResumed()) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        service = null;
    }

    /*
     * UI
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        logText = view.findViewById(R.id.receive_text);                          // TextView performance decreases with number of spans
        logText.setTextColor(getResources().getColor(R.color.colorDefaultText)); // set as default color to reduce number of spans
        logText.setMovementMethod(ScrollingMovementMethod.getInstance());
        SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        usuario = prefs.getString("usuario", ""); // prefs.getString("nombre del campo" , "valor por defecto")
        contrasenia = prefs.getString("contrasenia", ""); // prefs.getString("nombre del campo" , "valor por defecto")

        View btnStart = view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> sendStartCommand());

        /////MI CODIGO LO COMENTE UN RATO
        //  View btnSave = view.findViewById(R.id.btnSave);
        //  btnSave.setOnClickListener(v -> saveFile());

        View btnUpload = view.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(v -> uploadAudiosFromDevice());

        ///Sincronizacion con el servidor
        // View btnSincronizar = view.findViewById(R.id.btnSincronizacion);


        // btnSincronizar.setOnClickListener(v -> sendAudioClinicos());


        datoLectura = new ArrayList<>();


        String idParticipante = prefs.getString("idParticipante", "");
        numeroDocumento = prefs.getString("numeroDocumento", "");


        //   Toast toast = Toast.makeText(getActivity(),  idDato +"", Toast.LENGTH_SHORT);


        datoLectura = ObTenerNombreLectura(idParticipante);

        lv = view.findViewById(R.id.spinnerCategoriaLugar);
        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, datoLectura);
        lv.setAdapter(adapter);


        return view;
    }

    /*
     * Commands
     */
    private void sendStartCommand() {

        if (activeAudioStreaming) {
            Toast.makeText(getContext(), "Please wait, audio streaming in progress...", Toast.LENGTH_LONG).show();
            return;
        }

        /// Mi codigo Enrique
        SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        idTablaParticipante = prefs.getString("idParticipante", ""); // prefs.getString("nombre del campo" , "valor por defecto")
        String startCmd = "START";
        String datoSpinner = lv.getSelectedItem().toString();
        int cantidadElemento = lv.getCount();

        //COMENTARIO POR MOMENTO
        //  if (cantidadElemento == 2 && datoSpinner.equals("Archivos Basal")) {

        String dni = numeroDocumento; //Update data here!!!
        String part = nombreGrabacacion(datoSpinner); //Update data here!!!
        String idLecturaArchivo = consulterListaDatos(datoSpinner);

        startCmd += ("&" + dni + "_" + part);
        //startCmd +=  dni;

        //send command via BT
        if (send(startCmd)) {
            activeAudioStreaming = true;
            TimerTask task = new TimerTask() {
                public void run() {
                    activeAudioStreaming = false;
                    //display message
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), "Stopping recording...", Toast.LENGTH_SHORT).show();
                            String datoSpinner = lv.getSelectedItem().toString();

                            //Arsénico = As
                            //Mercurio = Hg
                            // Plomo = Pb
                            // Molibdeno = Mo
                            // Cadmio = Cd

                            String caracteristica = "";
                            if (datoSpinner.equals("Arsenico Basal")) {
                                caracteristica = "basal_As";
                            } else if (datoSpinner.equals("Arsenico Medida")) {
                                caracteristica = "medida_As";
                            }

                            if (datoSpinner.equals("Mercurio Basal")) {
                                caracteristica = "basal_Hg";
                            } else if (datoSpinner.equals("Mercurio Medida")) {
                                caracteristica = "medida_Hg";
                            }

                            if (datoSpinner.equals("Plomo Basal")) {
                                caracteristica = "basal_Pb";
                            } else if (datoSpinner.equals("Plomo Medida")) {
                                caracteristica = "medida_Pb";
                            }

                            if (datoSpinner.equals("Molibdeno Basal")) {
                                caracteristica = "basal_Mo";
                            } else if (datoSpinner.equals("Molibdeno Medida")) {
                                caracteristica = "medida_Mo";
                            }

                            if (datoSpinner.equals("Cadmio Basal")) {
                                caracteristica = "basal_Cd";
                            } else if (datoSpinner.equals("Cadmio Medida")) {
                                caracteristica = "medida_Cd";
                            }
                            nombreArchivoGlobal = numeroDocumento + "_" + formattedDate + "_" + caracteristica + ".txt";
                            actualizarReadGroupEstado(idLecturaArchivo, idTablaParticipante, "2" , nombreArchivoGlobal);
                        }
                    });
                    //send command via BT
                    if (send("STOP") == false) {
                        Log.e("BT", "Failed to send STOP command!");
                    }
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 20000);
        } else {
            Log.e("BT", "Failed to send START command!!!");
        }
        Toast.makeText(getContext(), "Starting recording...", Toast.LENGTH_LONG).show();
    }


    /*
     * Serial + UI
     */
    private void connect() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
            status("connecting...");
            connected = Connected.Pending;
            SerialSocket socket = new SerialSocket(getActivity().getApplicationContext(), device);
            service.connect(socket);
        } catch (Exception e) {
            onSerialConnectError(e);
        }
    }

    private void disconnect() {
        connected = Connected.False;
        service.disconnect();
    }

    private boolean send(String str) {
        if (connected != Connected.True) {
            Toast.makeText(getActivity(), "not connected", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            byte[] data;
            String[] parts = str.split("&");
            String cmd = parts[0];

            data = cmd.getBytes();

            SpannableStringBuilder spn = new SpannableStringBuilder("Command " + cmd + " sent!\n");
            spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorSendText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            logText.append(spn);
            service.write(data);
            return true;
        } catch (Exception e) {
            onSerialIoError(e);
        }

        return false;
    }


    private void receive(byte[] data) {
        try {
            if (deviceMode == DeviceMode.Command) {
                String cmd = new String(data);

                // Verificar si el comando recibido contiene la palabra "CLOSE"
                if (cmd.contains("CLOSE")) {
                    // Enviar el comando STOP
                    send("STOP");
                    activeAudioStreaming = false;
                    Toast.makeText(getContext(), "Reception stopped by CLOSE command", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtener los valores entre corchetes
                int start = cmd.indexOf('[');
                int end = cmd.indexOf(']');
                String values = cmd.substring(start + 1, end);
                // Reemplazar corchetes por comas
                String csv = values.replace("[", "").replace("]", "").replace(", ", ",");

                // Separar los valores en un arreglo
                String[] parts = csv.split(",");
                if (parts.length > 0) {
                    // Formatear el último valor a 6 decimales
                    DecimalFormat df = new DecimalFormat("#.######");
                    parts[parts.length - 1] = df.format(Double.parseDouble(parts[parts.length - 1]));
                }

                // Unir los valores nuevamente en una cadena CSV
                StringBuilder formattedCsv = new StringBuilder();
                for (String part : parts) {
                    formattedCsv.append(part).append(",");
                }
                // Eliminar la última coma
                if (formattedCsv.length() > 0) {
                    formattedCsv.setLength(formattedCsv.length() - 1);
                }

                // Abrir archivo de texto para escritura
                String datoSpinner = lv.getSelectedItem().toString();

                //Arsénico = As
                //Mercurio = Hg
                // Plomo = Pb
                // Molibdeno = Mo
                // Cadmio = Cd

                String caracteristica = "";
                if (datoSpinner.equals("Arsenico Basal")) {
                    caracteristica = "basal_As";
                } else if (datoSpinner.equals("Arsenico Medida")) {
                    caracteristica = "medida_As";
                }

                if (datoSpinner.equals("Mercurio Basal")) {
                    caracteristica = "basal_Hg";
                } else if (datoSpinner.equals("Mercurio Medida")) {
                    caracteristica = "medida_Hg";
                }

                if (datoSpinner.equals("Plomo Basal")) {
                    caracteristica = "basal_Pb";
                } else if (datoSpinner.equals("Plomo Medida")) {
                    caracteristica = "medida_Pb";
                }

                if (datoSpinner.equals("Molibdeno Basal")) {
                    caracteristica = "basal_Mo";
                } else if (datoSpinner.equals("Molibdeno Medida")) {
                    caracteristica = "medida_Mo";
                }

                if (datoSpinner.equals("Cadmio Basal")) {
                    caracteristica = "basal_Cd";
                } else if (datoSpinner.equals("Cadmio Medida")) {
                    caracteristica = "medida_Cd";
                }

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    // Permission already granted
                    // Access the external storage
                }

                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String filePath = dir.getAbsolutePath() + "/" + numeroDocumento + "_" + formattedDate + "_" + caracteristica + ".txt";
                nombreArchivoGlobal = numeroDocumento + "_" + formattedDate + "_" + caracteristica + ".txt";
                FileWriter fileWriter = new FileWriter(filePath, true);

                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                // Escribir datos en el archivo de texto
                bufferedWriter.write(formattedCsv.toString());
                bufferedWriter.newLine();
                // Cerrar archivo de texto
                bufferedWriter.close();
                fileWriter.close();
            } else if (deviceMode == DeviceMode.Stream) {
                // ...
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void status(String str) {
        SpannableStringBuilder spn = new SpannableStringBuilder(str + '\n');
        spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorStatusText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logText.append(spn);
    }

    private void uploadAudiosFromDevice() {
        String cmd = "UPLOAD";

        if (activeAudioStreaming) {
            Toast.makeText(getContext(), "Please wait, audio streaming in progress...", Toast.LENGTH_LONG).show();
            return;
        }

        //get current audio files
        File directory = new File(StorageUtils.getAudioDirectory());
        audioFilesStored = directory.listFiles();

        //send command
        if (send(cmd) == false) {
            Log.e("BT", "Failed to send UPLOAD command!");
        }
    }


    private void saveFile() {

        System.out.println("Save file!");
        System.out.println("Len:" + rawAudioStream.size());

        WavFileBuilder wavFile = new WavFileBuilder();


        // CAMBIAR FORMATO DE WAB A TXT
        wavFile.setAudioFormat(WavFileBuilder.PCM_AUDIO_FORMAT);
        wavFile.setBitsPerSample(WavFileBuilder.BITS_PER_SAMPLE_16);
        wavFile.setNumChannels(WavFileBuilder.CHANNELS_MONO);
        wavFile.setSampleRate(WavFileBuilder.SAMPLE_RATE_44100);
        wavFile.setSubChunk1Size(16);
        StorageUtils.writeToFile(uploadAudioName, wavFile.build(rawAudioStream.toByteArray()));

        Toast.makeText(getContext(),"File saved!",Toast.LENGTH_LONG).show();
        SpannableStringBuilder spn = new SpannableStringBuilder("File saved: " + uploadAudioName + "\n");
        spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorDefaultText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logText.append(spn);


        /// Mi codigo Enrique
        SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        String idDato = prefs.getString("idTablaAudio", ""); // prefs.getString("nombre del campo" , "valor por defecto")
        idTablaParticipante = prefs.getString("idParticipante", ""); // prefs.getString("nombre del campo" , "valor por defecto")
        String valueinString = lv.getSelectedItem().toString();
        ///Actualiza nombre

        String idLecturaArchivo = consulterListaDatos(valueinString);

        String[] parts  = uploadAudioName.split("_");

        actualizarReadGroupEstadoRecepcion(parts[0], parts[1], uploadAudioName);

    }


    private ArrayList<String> ObTenerNombreLectura(String idParticipante) {
        SQLiteDatabase db = conn.getReadableDatabase();
        Readgroup dato = null;
        ArrayList<String> nombresLectura;
        nombresLectura = new ArrayList<String>();


//        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PARTICIPANTES_READGROUP + " where " +
//                Utilidades.CAMPO_idParticipante + " = '" + idParticipante + "'" + " and " + Utilidades.CAMPO_flag + " = 0", null);

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PARTICIPANTES_READGROUP + " where " +
                Utilidades.CAMPO_idParticipante + " = '" + idParticipante + "'" + " and " + Utilidades.CAMPO_flag + " = 0 " + " and " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " +
                Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "'" , null);


        while (cursor.moveToNext()) {
            nombresLectura.add(cursor.getString(3));
        }

        db.close();

        return nombresLectura;
    }

    private void updateDeviceTime() {
        String cmd = "SETTIME";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        cmd += ("&" + sdf.format(new Date()));

        if (send(cmd) == false) {
            Log.e("BT", "Failed to send SETTIME command!");
        }
    }

    /*
     * SerialListener
     */
    @Override
    public void onSerialConnect() {
        status("connected");
        connected = Connected.True;

        //each new connection update device time
        updateDeviceTime();
    }

    @Override
    public void onSerialConnectError(Exception e) {
        status("connection failed: " + e.getMessage());
        disconnect();
    }

    @Override
    public void onSerialRead(byte[] data) {
        receive(data);
    }

    @Override
    public void onSerialIoError(Exception e) {
        status("connection lost: " + e.getMessage());
        disconnect();
    }

    private void actualizarReadGroup(String idLectura, String idParticipante, String ruta) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {idLectura, idParticipante};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ruta, ruta);
        db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_idLectura + "=?" + " and " + Utilidades.CAMPO_idParticipante + "=?", parametros);

        db.close();
    }


    private void actualizarReadGroupEstado(String idLectura, String idParticipante, String estado , String ruta) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {idLectura, idParticipante};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_flag, estado);
        values.put(Utilidades.CAMPO_ruta, ruta);

        db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_idLectura + "=?" + " and " + Utilidades.CAMPO_idParticipante + "=?", parametros);

        db.close();
    }

    private void actualizarReadGroupEstadoRecepcion(String dni, String nameSigla, String nombreArchivo) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {dni, nameSigla};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_flag, 0);
        values.put(Utilidades.CAMPO_ruta, nombreArchivo);
        db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_dni + "=?" + " and " + Utilidades.CAMPO_namesigla + "=?", parametros);
        db.close();
    }


    private String consulterListaDatos(String nombre) {
        String idCodigoLectura = null;
        SQLiteDatabase db = conn.getReadableDatabase();
        ParticipanteAudios dato = null;


        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PARTICIPANTES_READGROUP + " " +
                " where " + Utilidades.CAMPO_name + " = '" + nombre + "'" + " and "+     Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " +
                Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "'" , null);

        while (cursor.moveToNext()) {
            // dato.setIdParticipante(cursor.getString(1));
            idCodigoLectura = cursor.getString(2);
        }
        db.close();
        //      obtenerLista();
        return idCodigoLectura;
    }


    public void sendAudioClinicos() {

        try {
            SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
            idTablaParticipante = prefs.getString("idParticipante", ""); // prefs.getString("nombre del campo" , "valor por defecto")

            ArrayList<upload> listDeAudios = ListadeAudiosClinicos(idTablaParticipante);

            for (int i = 0; i < listDeAudios.size(); i++) {
                nombreArchivo = listDeAudios.get(i).getNombreArchivo();
                if (!nombreArchivo.isEmpty()) {
                    // new UploadFile(listDeAudios.get(i).getIdLectura(), listDeAudios.get(i).getIdParticipante(), listDeAudios.get(i).getNombreArchivo(),).execute();
                    // eliminarAudioRegistrado(listDeAudios.get(i).getIdLectura(), listDeAudios.get(i).getIdParticipante());
                }
            }
            Toast.makeText(getContext(), "SE SUBIERON LOS AUDIOS CON EXITO AL SERVIDOR", Toast.LENGTH_LONG).show();


            //      lv = view.findViewById(R.id.spinnerCategoriaLugar);
            //      ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, datoLectura);
            //        lv.setAdapter(adapter);


//            Spinner spinner = (Spinner)findViewById(R.id.spinnerCategoriaLugar);
//            List<String> list = Arrays.asList(getResources().getStringArray(R.array.S));

            //           ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, Android.R.layout.simple_spinner_item, list);
//            spinnerAdapter.setDropDownViewResource(Android.R.layout.simple_spinner_dropdown_item);
//            spinner.setAdapter(spinnerAdapter);
            lv.setAdapter(null);

            SharedPreferences prefss = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
            String idParticipante = prefss.getString("idParticipante", ""); // prefs.getString("nombre del campo" , "valor por defecto")
            datoLectura = ObTenerNombreLectura(idParticipante);


            adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, datoLectura);
            lv.setAdapter(adapter);


        } catch (Exception e) {

            Toast.makeText(getContext(), "OCURRIO UN ERROR", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<upload> ListadeAudiosClinicos(String idParticipante) {


        SQLiteDatabase db = conn.getReadableDatabase();
        Readgroup dato = null;
        ArrayList<upload> listaAudioCarga;
        listaAudioCarga = new ArrayList<upload>();


        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PARTICIPANTES_READGROUP + " where " +
                " where "+ Utilidades.CAMPO_idParticipante+ " = '"+ idParticipante +"'" + " and " +
                Utilidades.CAMPO_usuario + " = '" + usuario + "'" + " and " +
                Utilidades.CAMPO_contrasenia + " = '" + contrasenia + "'" , null);

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
            objAudioCarga.setNombreArchivo(cursor.getString(4));
            listaAudioCarga.add(objAudioCarga);
        }

        db.close();

        return listaAudioCarga;
    }

    private void eliminarAudioRegistrado(String idLectura, String idParticipante) {

        conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);

        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {idLectura, idParticipante};
        // db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_idLectura + "=?", parametros);
        db.delete(Utilidades.TABLA_PARTICIPANTES_READGROUP, Utilidades.CAMPO_idLectura + "=?" + " and " + Utilidades.CAMPO_idParticipante + "=?", parametros);

        db.close();
    }

    private void actualizarEstadoAudioServidor(String idLectura, String idParticipante) {
        conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {idLectura, idParticipante};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_flag, 1);
        db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_idLectura + "=?" + " and " + Utilidades.CAMPO_idParticipante + "=?", parametros);
        db.close();
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
        return resultado;
    }

}