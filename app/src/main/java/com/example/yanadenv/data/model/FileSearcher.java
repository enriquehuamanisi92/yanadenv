package com.example.yanadenv.data.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;

import com.example.yanadenv.entidades.SongDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class FileSearcher {
    public static final int REQUEST_CODE = 42;
    private static final String TAG = "FileSearcher";
    private static final int REQUEST_CODE_OPEN_DIRECTORY = 42;  // Código de solicitud para seleccionar un directorio

    // Método para solicitar acceso a un directorio a través de SAF (como la carpeta de Descargas)
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



    // Recorrer el directorio seleccionado utilizando DocumentFile y buscar archivos .txt
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

    // Método para leer el contenido de un archivo a partir de su URI (opcional si quieres leer el contenido)
    private static String readTextFromUri(Uri uri, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    // Método para obtener el URI persistente del directorio seleccionado en SharedPreferences
    public static Uri obtenerUriPersistente(Context context) {
        String uriString = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE).getString("downloads_uri", null);
        if (uriString != null) {
            return Uri.parse(uriString);
        }
        return null;
    }

    // Método para guardar el URI persistente en SharedPreferences
    private static void guardarUriPersistente(Context context, Uri uri) {
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE).edit().putString("downloads_uri", uri.toString()).apply();
    }

    // Método para buscar archivos con MediaStore (si es necesario)
    public static void buscarArchivosConMediaStore(Context context, ArrayList<SongDto> musicInfos) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Log.e(TAG, "No se tiene el permiso para gestionar todos los archivos en Android 11+");
                return;
            }
        }
        // Aquí continúa el proceso de búsqueda con MediaStore (si aplicara)
    }

    // Método para buscar archivos en el almacenamiento interno
    public static void buscarArchivosInternos(Context context, ArrayList<SongDto> musicInfos) {
        // Aquí puedes buscar archivos en el almacenamiento interno si es necesario
    }
}
