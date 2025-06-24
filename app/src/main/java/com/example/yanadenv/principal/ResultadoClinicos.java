package com.example.yanadenv.principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yanadenv.R;
import com.example.yanadenv.data.remote.APIService;
import com.example.yanadenv.data.remote.ApiUtils;
import com.example.yanadenv.conexion.ConexionSQLiteHelper;

import com.example.yanadenv.principal.utilidades.Utilidades;

public class ResultadoClinicos extends AppCompatActivity {

    private String idParticipanteServidor;
    private APIService mAPIService;
    EditText edtiTextNombreResultado;
    EditText ediTextApellidoResultado;
    EditText edtiTextCalidad;
    EditText ediTextEstado;
    TextView edtiTextDescripcionEstado;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_clinicos);

        SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        idParticipanteServidor = prefs.getString("idParticipanteServidor", "");
        token = prefs.getString("tokenUnico", "");

        mAPIService = ApiUtils.getAPIService();
        edtiTextNombreResultado = findViewById(R.id.txtEditNombreResultado);
        edtiTextNombreResultado.setFocusable(false);

        ediTextApellidoResultado = findViewById(R.id.txtEditApellidoResultado);
        ediTextApellidoResultado.setFocusable(false);

        edtiTextCalidad = findViewById(R.id.txtCalidad);
        edtiTextCalidad.setFocusable(false);

        ediTextEstado = findViewById(R.id.txtEditEstado);
        ediTextEstado.setFocusable(false);

        edtiTextDescripcionEstado = findViewById(R.id.receive_textDescripcion);
        edtiTextDescripcionEstado.setFocusable(false);

        // Cargar nombre y apellido desde la base de datos
        String dni = cargarDatosDesdeBaseDeDatos();

        // Cargar los otros datos desde SharedPreferences
        cargarDatosDesdeSharedPreferences(prefs , dni);
    }

    private String  cargarDatosDesdeBaseDeDatos() {
        String dni = "";
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();

        // Consulta para obtener el nombre y apellido usando el idParticipanteServidor como filtro
        Cursor cursor = db.rawQuery("SELECT " + Utilidades.CAMPO_name + ", " + Utilidades.CAMPO_lastname +", " + Utilidades.CAMPO_docnumber +
                " FROM " + Utilidades.TABLA_GENERALES + " WHERE " + Utilidades.CAMPO_idParticipanteServidor + " = ?", new String[]{idParticipanteServidor});

        if (cursor.moveToFirst()) {
            String nombre = cursor.getString(0);
            String apellido = cursor.getString(1);
            dni = cursor.getString(2);

            // Asignar los valores obtenidos a los TextView
            edtiTextNombreResultado.setText(!isNullOrEmpty(nombre) ? nombre : "N/A");
            ediTextApellidoResultado.setText(!isNullOrEmpty(apellido) ? apellido : "N/A");
        } else {
            // Si no se encuentra, mostrar un mensaje predeterminado
            edtiTextNombreResultado.setText("N/A");
            ediTextApellidoResultado.setText("N/A");
            Toast.makeText(this, "No se encontraron datos para el participante.", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();

        return dni;
    }

    private void cargarDatosDesdeSharedPreferences(SharedPreferences prefs, String dni) {
        // Obtener los datos de SharedPreferences
        String calidad = prefs.getString(dni + "_Calidad", "N/A");
        String estado = prefs.getString(dni + "_Estado", "N/A");
        String descripcion = prefs.getString(dni + "_Descripcion", "N/A");

        String descripcionPlomo = prefs.getString(dni + "_LabPlomo", "N/A");
        String descripcionMercurio = prefs.getString(dni + "_LabMercurio", "N/A");
        String descripcionlabCadmio = prefs.getString(dni + "_LabCadmio", "N/A");
        String descripcionlabArsenico = prefs.getString(dni + "_LabArsenico", "N/A");
        String descripcionlabMolibdeno = prefs.getString(dni + "_LabMolibdeno", "N/A");

        String descripcionCompleta =
                "Resultado de LabPlomo: " + descripcionPlomo + "\n" +
                        "Resultado de LabMercurio: " + descripcionMercurio + "\n" +
                        "Resultado de LabCadmio: " + descripcionlabCadmio + "\n" +
                        "Resultado de LabArsénico: " + descripcionlabArsenico + "\n" +
                        "Resultado de LabMolibdeno: " + descripcionlabMolibdeno + "\n \n " +
                        "Descripción del estado: \n" + descripcion + "\n";



        // Asignar los valores a los TextView
        edtiTextDescripcionEstado.setText(!isNullOrEmpty(descripcionCompleta ) ? descripcionCompleta : "N/A");
        edtiTextCalidad.setText(!isNullOrEmpty(calidad) ? calidad : "N/A");
        ediTextEstado.setText(!isNullOrEmpty(estado) ? estado : "N/A");
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
