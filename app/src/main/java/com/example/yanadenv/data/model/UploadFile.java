package com.example.yanadenv.data.model;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;

import com.example.yanadenv.conexion.ConexionSQLiteHelper;
import com.example.yanadenv.entidades.SongDto;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UploadFile extends AsyncTask<Object, String, String> {

    String readvalues;
    String participantId;
    String file;
    String token;
    ConexionSQLiteHelper conn;
    Context context;

    public UploadFile(String readvalues, String participantId, String file , String token, Context context) {
        this.readvalues = readvalues;
        this.participantId = participantId;
        this.file = file;
        this.token = token;
        this.context = context;
    }

    String file_name = "";

    @Override
    public String doInBackground(Object[] params) {
        return uploadFile();
    }

    private static final int REQUEST_CODE_OPEN_DIRECTORY = 42;  // Código de solicitud para seleccionar un directorio

    public static void solicitarAccesoDirectorio(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivityForResult(intent, REQUEST_CODE_OPEN_DIRECTORY);
    }

    // Método para manejar el resultado de la selección de directorio
    public static void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, Context context, ArrayList<SongDto> musicInfos) {
        if (requestCode == REQUEST_CODE_OPEN_DIRECTORY && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri directoryUri = data.getData();

                // Guardar acceso persistente al directorio seleccionado
                context.getContentResolver().takePersistableUriPermission(directoryUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Recorrer el directorio seleccionado para buscar archivos .txt
                recorrerDirectorio(directoryUri, context, musicInfos);
            }
        }
    }


    public static void recorrerDirectorio(Uri uri, Context context, ArrayList<SongDto> musicInfos) {
        DocumentFile directory = DocumentFile.fromTreeUri(context, uri);

        if (directory != null && directory.isDirectory()) {
            for (DocumentFile file : directory.listFiles()) {
                if (file.isFile() && file.getName() != null && file.getName().endsWith(".txt")) {
                    // Agregar archivos .txt a la lista
                    SongDto song = new SongDto();
                    song.path = file.getUri().toString(); // Guardamos el URI
                    song.songTitle = file.getName();
                    musicInfos.add(song);

                    Log.d(TAG, "Archivo encontrado: " + file.getName());
                } else if (file.isDirectory()) {
                    // Recursivamente buscar en subdirectorios
                    recorrerDirectorio(file.getUri(), context, musicInfos);
                }
            }
        }
    }

    // Método para subir el archivo
    private String uploadFile() {
        try {
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 100000000 * 100000000;

            URL url = new URL("http://161.132.216.3/samaycov-api/readdata/upload");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Authorization", token);
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            // Primer parámetro
            outputStream.writeBytes("Content-Disposition: form-data; name=\"readvalues\"" + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(readvalues);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            // Segundo parámetro
            outputStream.writeBytes("Content-Disposition: form-data; name=\"participantId\"" + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(participantId);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            // Archivo
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + file + "\"" + lineEnd);
            outputStream.writeBytes("Content-Type: audio/wav" + lineEnd);
            outputStream.writeBytes(lineEnd);

            // Acceder a la carpeta Documentos en la memoria interna/external
            File documentsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            if (documentsDir != null && !documentsDir.exists()) {
                documentsDir.mkdir();  // Crear la carpeta si no existe
            }

            // Crear el archivo si no existe
            File fileToUpload = new File(documentsDir, file);
            if (!fileToUpload.exists()) {
                // Crear el archivo si no existe
                fileToUpload.createNewFile();
                // Escribir algún contenido en el archivo (opcional)
                FileOutputStream fos = new FileOutputStream(fileToUpload);
                fos.write("Contenido inicial".getBytes());
                fos.close();
            }

            // Leer el archivo recién creado
            FileInputStream fileInputStream = new FileInputStream(fileToUpload);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            int serverResponseCode = connection.getResponseCode();

            String result = null;
            if (serverResponseCode == 200) {
                StringBuilder s_buffer = new StringBuilder();
                InputStream is = new BufferedInputStream(connection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    s_buffer.append(inputLine);
                }
                result = s_buffer.toString();
            }
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();

            if (result != null) {
                Log.d("result_for upload", result);
                file_name = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file_name;
    }
}
