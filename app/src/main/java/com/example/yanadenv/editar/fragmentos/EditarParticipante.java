package com.example.yanadenv.editar.fragmentos;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yanadenv.R;
import com.example.yanadenv.conexion.ConexionSQLiteHelper;
import com.example.yanadenv.data.model.Campain;
import com.example.yanadenv.data.model.campain.Campain2;
import com.example.yanadenv.data.remote.APIService;
import com.example.yanadenv.data.remote.ApiUtils;
import com.example.yanadenv.entidades.Campania;
import com.example.yanadenv.entidades.Pais;
import com.example.yanadenv.entidades.Participante;
import com.example.yanadenv.principal.utilidades.Utilidades;
import com.example.yanadenv.registro.ClassConnection;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarParticipante#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarParticipante extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ViewPager viewPager;
    private TextInputEditText txtDni;
    private TextInputEditText txtNacionalidad;
    private TextInputEditText txtTipoDocumento;
    private TextInputEditText txtNombres;
    //  private TextInputEditText txtPais;
    private TextInputEditText txtCampania;
    private TextInputEditText txtApellido;
    private TextInputEditText txtDniApoderado;
    private TextInputEditText txtNombreApoderado;
    private TextInputEditText txtApellidoApoderado;
    private TextInputEditText txtEdad;
    private TextInputEditText txtSexo;
    private TextInputEditText txtTelefonoContacto;
    private TextInputEditText txtEstadoCivil;
    private TextInputEditText txtSexoApoderado;
    private TextInputEditText txtTipoDocumentoApoderado;
    private TextInputEditText txtPeso;
    private TextInputEditText txtTalla;
    private TextInputEditText txtGestante;
    private TextInputEditText txtEducacion;
    public String prueba;
    public ArrayList<Campain> resultadoNombreCap;
    LinearLayout ll;
    private APIService mAPIService;
    private String resultadoNombreCampania;
    private String valorAssigne;
    private String assigne;
    String idTablaParticipante;
    ConexionSQLiteHelper conn;
    Participante objParticipante;
    private String listCampaña;
    List<TextInputLayout> allEds = new ArrayList<TextInputLayout>();


    TextInputLayout textDni, textNombre, textApellido,
            textDniApoderado, textNombreApoderado,
            textApellidoApoderado, textEdad, textTelefono,
            textPeso, textTalla, textNumeroFamilia, textNumeroNinios,
            textNumeroAdultos, textNumeroInfante, textNumeroGestante;

    AutoCompleteTextView cboSexo, cboEstado, cboVivienda, cboMaterial, cboEnergia, cboRedAgua, cboRedDesague;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarParticipante.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarParticipante newInstance(String param1, String param2) {
        EditarParticipante fragment = new EditarParticipante();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EditarParticipante() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_editar_participante, container, false);

        mAPIService = ApiUtils.getAPIService();


        final Button btnSiguiente = new Button(getActivity());
        //Sexo Dropdown
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.llParticipante);
        txtNombres = (TextInputEditText) rootView.findViewById(R.id.Nombres);
        txtApellido = (TextInputEditText) rootView.findViewById(R.id.Apellidos);
        txtSexo = (TextInputEditText) rootView.findViewById(R.id.SexoP);
        txtTipoDocumento = (TextInputEditText) rootView.findViewById(R.id.tipoDocumento);
        txtDni = (TextInputEditText) rootView.findViewById(R.id.numeroDocumento);
        txtDni.setFocusable(false);
        txtCampania = (TextInputEditText) rootView.findViewById(R.id.campaniaId);
        // txtPais = (TextInputEditText) rootView.findViewById(R.id.paisId);
        txtTelefonoContacto = (TextInputEditText) rootView.findViewById(R.id.numeroContacto);
        txtEstadoCivil = (TextInputEditText) rootView.findViewById(R.id.estadoCivil);
        txtPeso = (TextInputEditText) rootView.findViewById(R.id.Peso);
        txtTalla = (TextInputEditText) rootView.findViewById(R.id.Talla);
        txtNombreApoderado = (TextInputEditText) rootView.findViewById(R.id.nombreApoderado);
        txtApellidoApoderado = (TextInputEditText) rootView.findViewById(R.id.apellidoApoderado);
        txtSexoApoderado = (TextInputEditText) rootView.findViewById(R.id.sexApoderado);
        txtTipoDocumentoApoderado = (TextInputEditText) rootView.findViewById(R.id.tipoDocumentoApoderado);
        txtDniApoderado = (TextInputEditText) rootView.findViewById(R.id.numeroDocumentoApoderado);
        txtGestante = (TextInputEditText) rootView.findViewById(R.id.pregnant);
        txtEducacion = (TextInputEditText) rootView.findViewById(R.id.education);

        txtEdad = (TextInputEditText) rootView.findViewById(R.id.Edad);

        txtEdad.addTextChangedListener(new

                                               TextWatcher() {

                                                   // Antes de que el texto cambie (no debemos modificar nada aquí)
                                                   @Override
                                                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                   }


                                                   //Cuando esté cambiando...(no debemos modificar el texto aquí)
                                                   @Override
                                                   public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                   }

                                                   /*
                                                    * Aquí el texto ya ha cambiado completamente, tenemos el texto actualizado en pocas palabras
                                                    *
                                                    * Por cierto, aquí sí podemos modificar el texto pero debemos tener cuidado para no caer en
                                                    * un ciclo infinito
                                                    * */
                                                   @Override
                                                   public void afterTextChanged(Editable s) {
                                                       String elNuevoTexto = s.toString();
                                                       // Hacer lo que sea con elNuevoTexto
                                                       //  Toast.makeText(getActivity(), "Cambió a " + elNuevoTexto, Toast.LENGTH_SHORT).show();

                                                       if (!elNuevoTexto.isEmpty()) {
                                                           int edad = Integer.parseInt(elNuevoTexto);
                                                           if (edad > 18) {
                                                               txtNombreApoderado.setEnabled(false);
                                                               txtApellidoApoderado.setEnabled(false);
                                                               txtSexoApoderado.setEnabled(false);
                                                               txtTipoDocumentoApoderado.setEnabled(false);
                                                               txtNombreApoderado.setEnabled(false);
                                                               txtDniApoderado.setEnabled(false);
                                                           } else {
                                                               txtNombreApoderado.setEnabled(true);
                                                               txtApellidoApoderado.setEnabled(true);
                                                               txtSexoApoderado.setEnabled(true);
                                                               txtTipoDocumentoApoderado.setEnabled(true);
                                                               txtNombreApoderado.setEnabled(true);
                                                               txtDniApoderado.setEnabled(true);
                                                           }
                                                       }
                                                   }
                                               });


        SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        listCampaña = prefs.getString("listCampain", ""); // prefs.getString("nombre del campo" , "valor por defecto")
        //  SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        String idDato = prefs.getString("idTablaAudio", ""); // prefs.getString("nombre del campo" , "valor por defecto")
        idTablaParticipante = prefs.getString("idParticipante", ""); // prefs.getString("nombre del campo" , "valor por defecto")
        String edadParticipante = prefs.getString("edadParticipante", ""); // prefs.getString("nombre del campo" , "valor por defecto")

        listCampaña = prefs.getString("listCampain", ""); // prefs.getString("nombre del campo" , "valor por defecto")


        Gson jCampania = new Gson();
        Campain2 objCampain = new Campain2();

        objCampain = jCampania.fromJson(listCampaña, Campain2.class);
        final ArrayList<Campania> listCampania = new ArrayList<>();

        for (int i = 0; i < objCampain.getData().size(); i++) {
            Campania camp = new Campania();

            camp.setId(objCampain.getData().get(i).getId());
            camp.setName(objCampain.getData().get(i).getName());
            listCampania.add(camp);

        }


        objParticipante = new Participante();

        objParticipante = consulterListaDatos(idTablaParticipante);


        txtNombres.setText(objParticipante.getName().toString());
        txtApellido.setText(objParticipante.getLastname());
        txtSexo.setText(resultadoSexo(objParticipante.getSex()));
        txtTipoDocumento.setText(objParticipante.getDoctype());
        txtDni.setText(objParticipante.getDocnumber());
        // nombreCampania(response.body().getCampainId().toString());
        // assigne = response.body().getAssigneeId().toString();


        txtCampania.setText(nombreCampania(objParticipante.getCampainId().toString(), listCampania));


        txtTelefonoContacto.setText(objParticipante.getContactnumber().toString());
        txtEstadoCivil.setText(objParticipante.getCivilstatus().toString());
        double peso = Double.parseDouble(objParticipante.getWeight().toString());
        txtPeso.setText(peso / 100 + "");
        double talla = Double.parseDouble(objParticipante.getStature().toString());
        txtTalla.setText(talla / 100 + "");
        txtEdad.setText(objParticipante.getEdad());
        txtEducacion.setText(objParticipante.getEducacion());
        txtGestante.setText(objParticipante.getGestante());
        // valorAssigne = objParticipante.getAssigneeId().toString();

        //    Call<Assignee> call2 = mAPIService.getAssignee(response1.body().getAssigneeId().toString());

        int edad = Integer.parseInt(objParticipante.getEdad());

        if (edad <= 18) {
            txtNombreApoderado.setText(objParticipante.getNameApoderado());
            txtApellidoApoderado.setText(objParticipante.getLastnameApoderado());
            txtSexoApoderado.setText(objParticipante.getSexApoderado());
            txtTipoDocumentoApoderado.setText(objParticipante.getDocnumberApoderado());
            txtDniApoderado.setText(objParticipante.getDocnumberApoderado());

        } else {
            txtNombreApoderado.setText("");
            txtApellidoApoderado.setText("");
            txtSexoApoderado.setText("");
            txtTipoDocumentoApoderado.setText("");
            txtDniApoderado.setText("");
        }




        //  txtCampania.setText(response.body().getName());


//        txtEdad.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                // TODO Auto-generated method stub
//              //  Toast.makeText(getActivity(), "segundo metodo", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //Toast.makeText(getActivity(), "Tercer metodo", Toast.LENGTH_SHORT).show();
//                // TODO Auto-generated method stub
//
//
//             //   Toast.makeText(getActivity(), "primer metodo", Toast.LENGTH_SHORT).show();
//
//                int edad = Integer.parseInt(txtEdad.getText().toString());
//
//                if (edad>=18){
//                    //   editText.setEnabled(false);
//                    txtNombreApoderado.setEnabled(false);
//                    txtApellidoApoderado.setEnabled(false);
//                    txtSexoApoderado.setEnabled(false);
//                    txtTipoDocumentoApoderado.setEnabled(false);
//                    txtDniApoderado.setEnabled(false);
//                }
//                else {
//
//                    txtNombreApoderado.setEnabled(true);
//                    txtApellidoApoderado.setEnabled(true);
//                    txtSexoApoderado.setEnabled(true);
//                    txtTipoDocumentoApoderado.setEnabled(true);
//                    txtDniApoderado.setEnabled(true);
//                }
//
//            }
//        });


        // Toast.makeText(getActivity(), "Cambió a " + idTablaParticipante, Toast.LENGTH_SHORT).show();

        ClassConnection connection = new ClassConnection();
        ClassConnection connection2 = new ClassConnection();
        Pais dato = null;
        Campania dato2 = null;


        txtSexo = (TextInputEditText) rootView.findViewById(R.id.SexoP);

        txtSexo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int itemSelected = 0;
            String[] tipoSexo = {"MASCULINO", "FEMENINO"};

            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (hasFocus) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Seleccionar Sexo")
                            .setSingleChoiceItems(tipoSexo, itemSelected, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                    //  Pais  datos = listPais.get(selectedIndex);
                                    txtSexo.setText(tipoSexo[selectedIndex]);
                                    itemSelected = selectedIndex;
                                }
                            })
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                txtSexo.clearFocus();
            }
        });

        txtTipoDocumento = (TextInputEditText) rootView.findViewById(R.id.tipoDocumento);

        txtTipoDocumento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int itemSelected = 0;
            String[] tipoDocumento = {"DNI", "PASSPORT", "CE"};

            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (hasFocus) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Seleccionar tipo documento")
                            .setSingleChoiceItems(tipoDocumento, itemSelected, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                    //  Pais  datos = listPais.get(selectedIndex);
                                    txtTipoDocumento.setText(tipoDocumento[selectedIndex]);
                                    itemSelected = selectedIndex;
                                }
                            })
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                txtTipoDocumento.clearFocus();
            }
        });


        txtEstadoCivil = (TextInputEditText) rootView.findViewById(R.id.estadoCivil);

        txtEstadoCivil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int itemSelected = 0;
            String[] tipoEstadoCivil = {"SOLTERO", "CASADO", "VIUDO", "DIVORCIADO"};

            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (hasFocus) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Seleccionar estado civil")
                            .setSingleChoiceItems(tipoEstadoCivil, itemSelected, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                    //  Pais  datos = listPais.get(selectedIndex);
                                    txtEstadoCivil.setText(tipoEstadoCivil[selectedIndex]);
                                    itemSelected = selectedIndex;
                                }
                            })
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                txtEstadoCivil.clearFocus();
            }
        });

        txtCampania = (TextInputEditText) rootView.findViewById(R.id.campaniaId);

        txtCampania.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                 int itemSelected = 0;
                                                 String[] nombre = new String[listCampania.size()];
                                                 ArrayList<String> datos = new ArrayList<String>(); // Create an ArrayList object

                                                 @Override
                                                 public void onFocusChange(View v, boolean hasFocus) {

                                                     for (int i = 0; i < listCampania.size(); i++) {
                                                         Campania p = listCampania.get(i);
                                                         nombre[i] = p.getName();
                                                     }

                                                     if (hasFocus) {
                                                         new AlertDialog.Builder(getActivity())
                                                                 .setTitle("Seleccionar Campaña")
                                                                 .setSingleChoiceItems(nombre, itemSelected, new DialogInterface.OnClickListener() {
                                                                     @Override
                                                                     public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                                                         //  Pais  datos = listPais.get(selectedIndex);
                                                                         txtCampania.setText(nombre[selectedIndex]);
                                                                         itemSelected = selectedIndex;
                                                                     }
                                                                 })
                                                                 .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                     @Override
                                                                     public void onClick(DialogInterface dialog, int which) {

                                                                     }
                                                                 })
                                                                 .show();
                                                     }
                                                     txtCampania.clearFocus();
                                                 }
                                             }
        );


//        txtNacionalidad = (TextInputEditText) rootView.findViewById(R.id.paisId);

//        txtNacionalidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                                                     int itemSelected = 0;
//                                                     String[] nombre = new String[listPais.size()];
//                                                     ArrayList<String> datos = new ArrayList<String>(); // Create an ArrayList object
//
//                                                     @Override
//                                                     public void onFocusChange(View v, boolean hasFocus) {
//
//                                                         for (int i = 0; i < listPais.size(); i++) {
//                                                             Pais p = listPais.get(i);
//                                                             nombre[i] = p.getName();
//                                                         }
//
//                                                         if (hasFocus) {
//                                                             new AlertDialog.Builder(getActivity())
//                                                                     .setTitle("Seleccionar Pais")
//                                                                     .setSingleChoiceItems(nombre, itemSelected, new DialogInterface.OnClickListener() {
//                                                                         @Override
//                                                                         public void onClick(DialogInterface dialogInterface, int selectedIndex) {
//                                                                             //  Pais  datos = listPais.get(selectedIndex);
//                                                                             txtNacionalidad.setText(nombre[selectedIndex]);
//                                                                             itemSelected = selectedIndex;
//                                                                         }
//                                                                     })
//                                                                     .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                                                         @Override
//                                                                         public void onClick(DialogInterface dialog, int which) {
//
//                                                                         }
//                                                                     })
//                                                                     .show();
//                                                         }
//                                                         txtNacionalidad.clearFocus();
//                                                     }
//                                                 }
//        );


        txtSexoApoderado = (TextInputEditText) rootView.findViewById(R.id.sexApoderado);

        txtSexoApoderado.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int itemSelected = 0;
            String[] tipoSexo = {"MASCULINO", "FEMENINO"};

            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (hasFocus) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Seleccionar Sexo")
                            .setSingleChoiceItems(tipoSexo, itemSelected, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                    //  Pais  datos = listPais.get(selectedIndex);
                                    txtSexoApoderado.setText(tipoSexo[selectedIndex]);
                                    itemSelected = selectedIndex;
                                }
                            })
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                txtSexoApoderado.clearFocus();
            }
        });

        txtTipoDocumentoApoderado = (TextInputEditText) rootView.findViewById(R.id.tipoDocumentoApoderado);
        txtTipoDocumentoApoderado.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int itemSelected = 0;
            String[] tipoDocumento = {"DNI", "PASSPORT", "CE"};

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Seleccionar tipo documento")
                            .setSingleChoiceItems(tipoDocumento, itemSelected, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                    //  Pais  datos = listPais.get(selectedIndex);
                                    txtTipoDocumentoApoderado.setText(tipoDocumento[selectedIndex]);
                                    itemSelected = selectedIndex;
                                }
                            })
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                txtTipoDocumentoApoderado.clearFocus();
            }
        });

        try {


            //Personalizando botones
            btnSiguiente.setTextColor(Color.WHITE);
            btnSiguiente.setText("ACTUALIZAR");
            LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                    /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                    /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
            );
            parametros.setMargins(5, 20, 5, 5);

            btnSiguiente.setLayoutParams(parametros);
            btnSiguiente.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
            ll.addView(btnSiguiente);

            btnSiguiente.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Mensaje")
                            .setMessage("¿DESEA ACTUALIZAR LOS LOS DATOS DEL PARTICIPANTE?")
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("AlertDialog", "Positive");
                                    // AQUI MANDAR AL WEB SERVICES
                                    // getPosts();
                                    //Intent i = new Intent(Inicio.this,Login.class);
                                    // startActivity(i);
                                    try {

                                        boolean activo = Validacion();
                                        String sexo = null;
                                        String campaniaId = null;
                                        String sexoApoderado = null;
                                        String nombreAparticipante = null;
                                        String apellidoParticipante =null;


                                        String tipoDocumento =null;
                                        String tipoDocumentoApoderado = null;
                                        String numeroDocumento = null;
                                        String resultadoIdCampania = null;
                                        String numeroContacto = null;
                                        String estadoCivil = null;
                                        double t = 0;
                                        double p = 0;
                                        double imc = 0;
                                        String nombreApoderado = null;
                                        String apellidoApoderado = null;
                                        String numeroDocumentoApoderado = null;


                                        String edadParti= null;
                                        int edadEntera = 0;
                                        if (activo) {

                                            campaniaId = idCampania(txtCampania.getEditableText().toString(), listCampania);
                                            nombreAparticipante = txtNombres.getEditableText().toString();
                                            apellidoParticipante = txtApellido.getEditableText().toString();

                                            sexo = tipoSexo(txtSexo.getEditableText().toString());
                                            tipoDocumento = txtTipoDocumento.getEditableText().toString();
                                            numeroDocumento = txtDni.getEditableText().toString();
                                            numeroContacto = txtTelefonoContacto.getEditableText().toString();
                                            estadoCivil = txtEstadoCivil.getEditableText().toString();
                                            t = Double.parseDouble(txtTalla.getText().toString());
                                            p = Double.parseDouble(txtPeso.getText().toString());
                                            imc = p / (t * t);
                                            t = t * 100;
                                            p = p * 100;


                                             edadEntera = Integer.parseInt(txtEdad.getText().toString());
                                            if (edadEntera <= 18) {

                                                nombreApoderado = txtNombreApoderado.getEditableText().toString();
                                                apellidoApoderado = txtApellidoApoderado.getEditableText().toString();
                                                sexoApoderado = tipoSexo(txtSexoApoderado.getEditableText().toString());
                                                tipoDocumentoApoderado = txtTipoDocumentoApoderado.getEditableText().toString();

                                                numeroDocumentoApoderado = txtDniApoderado.getEditableText().toString();
                                                edadParti = edadEntera + "";

                                            } else {
                                                nombreApoderado = "null";
                                                apellidoApoderado = "null";
                                                sexoApoderado = null;
                                                tipoDocumentoApoderado = 0 + "";
                                                numeroDocumentoApoderado = 0 + "";
                                                edadParti = edadEntera + "";
                                            }
                                            Toast.makeText(getActivity(), "Se actualizaron los datos con exito", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(getActivity(), "Ingrese la información faltante", Toast.LENGTH_SHORT).show();
                                        }


                                        actualizarReadGroup(sexo, campaniaId, sexoApoderado, nombreAparticipante, apellidoParticipante, tipoDocumento,
                                                tipoDocumentoApoderado, numeroDocumento, resultadoIdCampania, numeroContacto, estadoCivil, t, p, imc, nombreApoderado,
                                         apellidoApoderado, numeroDocumentoApoderado, edadEntera+"", edadParti);


                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("AlertDialog", "Negative");
                                }
                            })
                            .show();
                }
            });


        } catch (
                Exception e) {
            e.printStackTrace();
        }

        viewPager = (ViewPager)

                getActivity().

                        findViewById(R.id.view_pager);


        return rootView;

    }


    private void actualizarReadGroup( String sexo,
            String campaniaId,
            String sexoApoderado,
            String nombreAparticipante,
            String apellidoParticipante,
            String tipoDocumento,
            String tipoDocumentoApoderado,
            String numeroDocumento,
            String resultadoIdCampania,
            String numeroContacto,
            String estadoCivil,
            double t,
            double p,
            double imc,
            String nombreApoderado,
            String apellidoApoderado,
            String numeroDocumentoApoderado,
            String edadApoderado,
            String edadParti) {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(Utilidades.CAMPO_name, nombreAparticipante);
        values.put(Utilidades.CAMPO_lastname, apellidoParticipante);
        values.put(Utilidades.CAMPO_sex, sexo);
        values.put(Utilidades.CAMPO_doctype, tipoDocumento);
        values.put(Utilidades.CAMPO_docnumber, numeroDocumento);
        values.put(Utilidades.CAMPO_campainId, campaniaId);
        values.put(Utilidades.CAMPO_contactnumber, numeroContacto);
        values.put(Utilidades.CAMPO_civilstatus, estadoCivil);
        values.put(Utilidades.CAMPO_weight, p);
        values.put(Utilidades.CAMPO_stature, t);
        values.put(Utilidades.CAMPO_imc, imc);
        values.put(Utilidades.CAMPO_edad, edadParti);
        values.put(Utilidades.CAMPO_nameApoderado, nombreApoderado);
        values.put(Utilidades.CAMPO_lastnameApoderado, apellidoApoderado);
        values.put(Utilidades.CAMPO_sexApoderado, sexoApoderado);
        values.put(Utilidades.CAMPO_doctypeApoderado,tipoDocumentoApoderado);
        values.put(Utilidades.CAMPO_docnumberApoderado, numeroDocumentoApoderado);
        
        //     values.put(Utilidades.CAMPO_lastname, apellido);
        String[] args = new String[]{idTablaParticipante};
        db.update(Utilidades.TABLA_GENERALES, values, Utilidades.CAMPO_idParticipante + "=?", args);
        //   UPDATE table set col = 1 WHERE id = (SELECT MAX(id) FROM table)
        db.close();
    }


    String tipoSexo(String sexo) {
        String resultado = "";

        if (sexo.equals("FEMENINO")) {
            resultado = "F";
        } else {
            resultado = "M";
        }
        return resultado;
    }

    private void actualizarReadGroupDni(String idParticipante, String dni) {
        conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {idParticipante};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_dni, dni);
        db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_idParticipante + "=? ", parametros);
        db.close();
    }

    public String idCampania(String campania, ArrayList<Campania> listCampania) {
        String index = "";
        int bound = listCampania.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (listCampania.get(userInd).getName().equals(campania)) {
                index = listCampania.get(userInd).getId();
                break;
            }
        }
        return index + "";
    }

    public String nombreCampania(String campania, ArrayList<Campania> listCampania) {
        String index = "";
        int bound = listCampania.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (listCampania.get(userInd).getId().equals(campania)) {
                index = listCampania.get(userInd).getName();
                break;
            }
        }
        return index + "";
    }


    public String idPais(String campania, ArrayList<Pais> listPais) {
        String index = "";
        int bound = listPais.size();
        for (int userInd = 0; userInd < bound; userInd++) {
            if (listPais.get(userInd).getName().equals(campania)) {
                index = listPais.get(userInd).getId();
                break;
            }
        }
        return index + "";
    }


    public boolean Validacion() {
        boolean estado = true;

        String nombre = txtNombres.getText().toString();
        if (nombre.equals("")) {
            Toast.makeText(getActivity(), "Escribir Nombre !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            txtNombres.requestFocus();
            estado = false;
        }

        String apellido = txtApellido.getText().toString();
        if (apellido.equals("")) {
            Toast.makeText(getActivity(), "Escribir Apellido !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            txtApellido.requestFocus();
            estado = false;
        }


        String sexo = txtSexo.getText().toString();
        if (sexo.equals("")) {
            Toast.makeText(getActivity(), "Escribir Sexo !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            //  txtSexo.requestFocus();
            estado = false;
        }


        String tipo_documento = txtTipoDocumento.getText().toString();
        if (tipo_documento.equals("")) {
            Toast.makeText(getActivity(), "Seleccionar tipo documento !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            // txtTipoDocumento.requestFocus();
            estado = false;
        }


        String dni = txtDni.getText().toString();
        if (dni.equals("")) {
            Toast.makeText(getActivity(), "Escribir numero documento !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            txtDni.requestFocus();
            estado = false;
        }

        String campania = txtCampania.getText().toString();
        if (campania.equals("")) {
            Toast.makeText(getActivity(), "Seleccionar campaña !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            //  txtCampania.requestFocus();
            estado = false;
        }


        String estadocivil = txtEstadoCivil.getText().toString();
        if (estadocivil.equals("")) {
            Toast.makeText(getActivity(), "Seleccionar estado civil !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            //  txtEstadoCivil.requestFocus();
            estado = false;
        }


        String peso = txtPeso.getText().toString();
        if (peso.equals("")) {
            Toast.makeText(getActivity(), "Ingresar peso !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            txtPeso.requestFocus();
            estado = false;
        }


        String talla = txtTalla.getText().toString();
        if (talla.equals("")) {
            Toast.makeText(getActivity(), "Ingresar talla !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            txtTalla.requestFocus();
            estado = false;
        }

        String edad = txtEdad.getText().toString();
        if (edad.equals("")) {
            Toast.makeText(getActivity(), "Ingresar Edad !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            txtEdad.requestFocus();
            estado = false;
        }
        int edadEntera = 0;
        if (!txtEdad.getText().toString().isEmpty()) {

            edadEntera = Integer.parseInt(txtEdad.getText().toString());
        }


        if (edadEntera <= 18) {

            String nombreApoderado = txtNombreApoderado.getText().toString();
            if (nombreApoderado.equals("")) {
                Toast.makeText(getActivity(), "Ingresar nombre apoderado !!!", Toast.LENGTH_SHORT).show();
                // Focus en jugar y abrir el Teclado
                txtNombreApoderado.requestFocus();
                estado = false;
            }

            String ApellidoApoderado = txtApellidoApoderado.getText().toString();
            if (ApellidoApoderado.equals("")) {
                Toast.makeText(getActivity(), "Ingresar apellido apoderado !!!", Toast.LENGTH_SHORT).show();
                // Focus en jugar y abrir el Teclado
                txtApellidoApoderado.requestFocus();
                estado = false;
            }

            String sexoApoderado = txtSexoApoderado.getText().toString();
            if (sexoApoderado.equals("")) {
                Toast.makeText(getActivity(), "Ingresar sexo apoderado !!!", Toast.LENGTH_SHORT).show();
                // Focus en jugar y abrir el Teclado
                //    txtSexoApoderado.requestFocus();
                estado = false;
            }

            String docummentoApoderado = txtTipoDocumentoApoderado.getText().toString();
            if (docummentoApoderado.equals("")) {
                Toast.makeText(getActivity(), "Ingresar tipo de documento de apoderado !!!", Toast.LENGTH_SHORT).show();
                // Focus en jugar y abrir el Teclado
                //   txtTipoDocumentoApoderado.requestFocus();
                estado = false;
            }

            String dniApoderado = txtDniApoderado.getText().toString();
            if (dniApoderado.equals("")) {
                Toast.makeText(getActivity(), "Ingresar numero de documento apoderado !!!", Toast.LENGTH_SHORT).show();
                // Focus en jugar y abrir el Teclado
                txtDniApoderado.requestFocus();
                estado = false;
            }
        }

        return estado;
    }


    public String resultadoSexo(String entrada) {

        String resultado = "";
        if (entrada.equals("F")) {
            resultado = "FEMENINO";
        } else if (entrada.equals("M")) {
            resultado = "MASCULINO";
        }
        return resultado;
    }


    private Participante consulterListaDatos(String idParticipante) {

        conn = new ConexionSQLiteHelper(getActivity().getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();
        Participante dato = new Participante();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES + " where " +
                Utilidades.CAMPO_idParticipante + " = '" + idParticipante + "'", null);

        while (cursor.moveToNext()) {

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
        }
        //     listParticipante.add(dato);
        return dato;
    }


    private void actualizarDatosParticipanteAudios(String idParticipante, String nombre, String apellido, String edad) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_nombreParticipante, nombre);
        values.put(Utilidades.CAMPO_apellidoParticipante, apellido);
        values.put(Utilidades.CAMPO_edad, edad);
        //     values.put(Utilidades.CAMPO_lastname, apellido);
        String[] args = new String[]{idParticipante};
        db.update(Utilidades.TABLA_PARTICIPANTES_AUDIOS, values, Utilidades.CAMPO_idParticipante + "=?", args);
        //   UPDATE table set col = 1 WHERE id = (SELECT MAX(id) FROM table)
        db.close();
    }

}