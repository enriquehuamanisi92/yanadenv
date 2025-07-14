package com.example.yanadenv.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.yanadenv.data.model.ClinicDTO;
import com.example.yanadenv.data.model.Clinicgroup;
import com.example.yanadenv.data.model.Project;
import com.example.yanadenv.data.model.campain.Campain2;
import com.example.yanadenv.data.model.projectFull.Project3;
import com.example.yanadenv.data.model.readGroup.Readgroup2;
import com.example.yanadenv.data.model.readGroupFull.ReadgroupFull;
import com.example.yanadenv.principal.utilidades.Utilidades;
import com.google.android.material.textfield.TextInputLayout;
import com.example.yanadenv.data.model.Token;
import com.example.yanadenv.data.model.CodigoToken;
import com.example.yanadenv.data.remote.APIService;
import com.example.yanadenv.data.remote.ApiUtils;
import com.example.yanadenv.R;
import com.example.yanadenv.principal.Inicio;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private TextInputLayout txtUsuario, txtContrasenia;
    private Button btnIngresar;
    private static final String TAG = "LoginActivity";
    private APIService mAPIService;
    private int counterExit = 0;
    ArrayList<ClinicDTO> ListClinicDto;
    int identificador;
    private ArrayList<Project> ListProjectClinic;
    int iteracion = 0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = findViewById(R.id.textUsuario);
        txtContrasenia = findViewById(R.id.textContrasenia);
        btnIngresar = findViewById(R.id.btnIngresar);

        mAPIService = ApiUtils.getAPIService();

        SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        String storedUsuario = prefs.getString("usuario", null);
        String storedContrasenia = prefs.getString("contrasenia", null);


        // Verificar si el usuario ya está logueado
        if (storedUsuario != null && storedContrasenia != null) {
            redirigirAInicio(); // Si ya hay credenciales almacenadas, redirige a la pantalla de inicio
        }





        btnIngresar.setOnClickListener(v -> {
            String usuario = txtUsuario.getEditText().getText().toString().trim();
            String contrasenia = txtContrasenia.getEditText().getText().toString().trim();

            if (usuario.isEmpty() || contrasenia.isEmpty()) {
                Toast.makeText(Login.this, "Los campos están vacíos", Toast.LENGTH_LONG).show();
            } else {
                if (storedUsuario != null && storedContrasenia != null) {

                        if (usuario.equals(storedUsuario) && contrasenia.equals(storedContrasenia)) {


                        estructuraDatosClinicos(prefs.getString("tokenUnico", null));
                        redirigirAInicio();
                    } else {
                        Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Token token = new Token(usuario, contrasenia, "Yanadenv");
                    urlToken(token);
                }
            }
        });
    }

    private void urlToken(Token token) {
        mAPIService.urlToken(token).enqueue(new Callback<CodigoToken>() {
            @Override
            public void onResponse(Call<CodigoToken> call, Response<CodigoToken> response) {
                if (response.isSuccessful()) {
                    SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("usuario", token.getUsername());
                    editor.putString("contrasenia", token.getPassword());
                    editor.putString("tokenUnico", response.body().getToken());
                    editor.commit();

                    estructuraDatosClinicos(response.body().getToken());
                    redirigirAInicio();
                } else {
                    Toast.makeText(Login.this, "Usuario no identificado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CodigoToken> call, Throwable t) {
                Log.e(TAG, "Error al conectar al servidor.", t);
            }
        });
    }


    private void redirigirAInicio() {
        Intent intent = new Intent(Login.this, Inicio.class);
        startActivity(intent);
        finish(); // Cierra la actividad actual
    }

    public void estructuraDatosClinicos(String token) {

        ListProjectClinic = new ArrayList<Project>();

        Map<String, String> headers = new HashMap<>();
        Call<Project3> call0 = mAPIService.getProject("Bearer " + token, 0, 0, 0);
        call0.enqueue(new Callback<Project3>() {
            @Override
            public void onResponse(Call<Project3> call, retrofit2.Response<Project3> response) {
                if (response.isSuccessful()) {
                    for (int n = 0; n < response.body().getData().size(); n++) {
                        if (Utilidades.NOMBRE_PROYECTO.equals(response.body().getData().get(n).getName())) {
                            String idProyecto = response.body().getData().get(n).getId();
                            Call<Clinicgroup> callGroup = mAPIService.getClicgroupFull("Bearer " + token, 0, 0, 0);
                            callGroup.enqueue(new Callback<Clinicgroup>() {
                                @Override
                                public void onResponse(Call<Clinicgroup> call, retrofit2.Response<Clinicgroup> response2) {
                                    ListClinicDto = new ArrayList<ClinicDTO>();
                                    identificador = 0;
                                    for (int p = 0; p < response2.body().getData().size(); p++) {
                                        if (idProyecto.equals(response2.body().getData().get(p).getProjectId())) {
                                            String idBloques = response2.body().getData().get(p).getId();
                                            Call<Project> call1 = mAPIService.clinicgroup("Bearer " + token, idBloques);
                                            call1.enqueue(new Callback<Project>() {
                                                @Override
                                                public void onResponse(Call<Project> call, Response<Project> response) {
                                                    if (response.isSuccessful()) {
                                                        if (response.body().getName().equals("Datos Clínicos") &&
                                                                response.body().getProjectId().equals("82c7193e-5208-11f0-95cd-e297c4e3c44f")) {
                                                            //fcf82147-31d3-4023-9456-fa2b7cefe8ad
                                                            // response.body().getProjectId().equals("82c7193e-5208-11f0-95cd-e297c4e3c44f")) {
                                                            Gson gson = new Gson();
                                                            Object request = gson.toJson(response.body());

                                                            String datosClinicos1 = request.toString();
                                                            SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = prefs.edit();
                                                            editor.putString("datosClinicos1", datosClinicos1);
                                                            editor.commit();
                                                        }

                                                        if (response.body().getName().equals("Conocimiento") &&
                                                                response.body().getProjectId().equals("82c7193e-5208-11f0-95cd-e297c4e3c44f")) {
                                                            Gson gson = new Gson();
                                                            Object request = gson.toJson(response.body());

                                                            String datosClinicos2 = request.toString();
                                                            SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = prefs.edit();
                                                            editor.putString("datosClinicos2", datosClinicos2);
                                                            editor.commit();
                                                        }

                                                        if (response.body().getName().equals("Socieconómico") &&
                                                                response.body().getProjectId().equals("82c7193e-5208-11f0-95cd-e297c4e3c44f")) {

                                                            Gson gson = new Gson();
                                                            Object request = gson.toJson(response.body());

                                                            String socioEconomico = request.toString();
                                                            SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = prefs.edit();
                                                            editor.putString("SocieconómicoJson", socioEconomico);
                                                            editor.commit();
                                                        }


                                                        iteracion++;
                                                    }
                                                    if (iteracion == 2) {

                                                        Call<ReadgroupFull> call0 = mAPIService.readGroupIdFull("Bearer " + token, 0, 0, 0, "82c7193e-5208-11f0-95cd-e297c4e3c44f");
                                                       // Call<Readgroup2> call0 = mAPIService.readGroupFull("Bearer " + token, response4.body().getData().get(0).getId());


                                                        call0.enqueue(new Callback<ReadgroupFull>() {
                                                            @Override
                                                            public void onResponse(Call<ReadgroupFull> call, Response<ReadgroupFull> response4) {

                                                                if (response4.isSuccessful()) {
                                                                    //Lecturas
                                                                    Call<Readgroup2> call0 = mAPIService.readGroupFull("Bearer " + token, response4.body().getData().get(0).getId());
                                                                    call0.enqueue(new Callback<Readgroup2>() {
                                                                        @Override
                                                                        public void onResponse(Call<Readgroup2> call, Response<Readgroup2> response4) {
                                                                            if (response4.isSuccessful()) {
                                                                                response4.body().getId();
                                                                                Gson gson = new Gson();
                                                                                Object request = gson.toJson(response4.body());
                                                                                String readGroupFull = request.toString();
                                                                                SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                                                                                SharedPreferences.Editor editor = prefs.edit();
                                                                                editor.putString("listReadGroupFull", readGroupFull);
                                                                                editor.commit();
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<Readgroup2> call, Throwable t) {
                                                                            Log.e(TAG, "Unable to submit post to API.");
                                                                        }
                                                                    });
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ReadgroupFull> call, Throwable t) {
                                                                Log.e(TAG, "Unable to submit post to API.");
                                                            }
                                                        });

                                                        Call<Campain2> call1 = mAPIService.getCampaiFull("Bearer " + token, 0, 0, 0 );
                                                      //  Call<ReadgroupFull> call0 = mAPIService.readGroupIdFull("Bearer " + token, 0, 0, 0, "9ac69152-355b-47a9-a5b7-0f6e104c0adc");

                                                        call1.enqueue(new Callback<Campain2>() {
                                                            @Override
                                                            public void onResponse(Call<Campain2> call, Response<Campain2> response) {

                                                                if (response.isSuccessful()) {
                                                                    Gson gson = new Gson();
                                                                    Object request = gson.toJson(response.body());

                                                                    String campain = request.toString();
                                                                    SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                                                                    SharedPreferences.Editor editor = prefs.edit();
                                                                    editor.putString("listCampain", campain);
                                                                    editor.commit();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<Campain2> call, Throwable t) {
                                                                Log.e(TAG, "Unable to submit post to API.");
                                                            }
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Project> call, Throwable t) {
                                                    Log.e(TAG, "Unable to submit post to API.");
                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Clinicgroup> call, Throwable t) {
                                    Log.e(TAG, "Unable to submit post to API.");
                                }
                            });
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<Project3> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}



