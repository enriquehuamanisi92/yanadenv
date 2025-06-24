package com.example.yanadenv.registro.fragmentos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yanadenv.R;
import com.example.yanadenv.data.model.campain.Campain2;
import com.example.yanadenv.entidades.Campania;
import com.example.yanadenv.entidades.Pais;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParticipanteFragmen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParticipanteFragmen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
    private TextInputEditText txtPregnat;
    private TextInputEditText txtEducation;

    private String listCampaña;
    public String prueba;

    LinearLayout ll;

    List<TextInputLayout> allEds = new ArrayList<TextInputLayout>();


    TextInputLayout textDni, textNombre, textApellido,
            textDniApoderado, textNombreApoderado,
            textApellidoApoderado, textEdad, textTelefono,
            textPeso, textTalla, textNumeroFamilia, textNumeroNinios,
            textNumeroAdultos, textNumeroInfante, textNumeroGestante;

    AutoCompleteTextView cboSexo, cboEstado, cboVivienda, cboMaterial, cboEnergia, cboRedAgua, cboRedDesague;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParticipanteFragmen.
     */
    // TODO: Rename and change types and number of parameters
    public static ParticipanteFragmen newInstance(String param1, String param2) {
        ParticipanteFragmen fragment = new ParticipanteFragmen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View rootView = inflater.inflate(R.layout.fragment_participante, container, false);
        final Button btnSiguiente = new Button(getActivity());
        //Sexo Dropdown
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.llParticipante);
        txtNombres = (TextInputEditText) rootView.findViewById(R.id.Nombres);
        txtApellido = (TextInputEditText) rootView.findViewById(R.id.Apellidos);
        txtSexo = (TextInputEditText) rootView.findViewById(R.id.SexoP);
        txtTipoDocumento = (TextInputEditText) rootView.findViewById(R.id.tipoDocumento);
        txtDni = (TextInputEditText) rootView.findViewById(R.id.numeroDocumento);
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
        txtPregnat = (TextInputEditText) rootView.findViewById(R.id.pregnant);
        txtEducation = (TextInputEditText) rootView.findViewById(R.id.education);



        SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);

        listCampaña = prefs.getString("listCampain", ""); // prefs.getString("nombre del campo" , "valor por defecto")

        Gson jCampania = new Gson();
        Campain2 objCampain = new Campain2();
        objCampain = jCampania.fromJson(listCampaña, Campain2.class);


        final ArrayList<Pais> listPais = new ArrayList<>();
        final ArrayList<Campania> listCampania = new ArrayList<>();


        for (int i = 0; i < objCampain.getData().size(); i++) {
            Campania camp = new Campania();

            camp.setId(objCampain.getData().get(i).getId());
            camp.setName(objCampain.getData().get(i).getName());
            listCampania.add(camp);

        }



        txtEdad = (TextInputEditText) rootView.findViewById(R.id.Edad);

        txtEdad.addTextChangedListener(new TextWatcher() {

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


        txtPregnat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int itemSelected = 0;
            String[] tipoDocumento = {"SI", "NO"};

            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (hasFocus) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("¿Actualmente es gestante?")
                            .setSingleChoiceItems(tipoDocumento, itemSelected, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                    //  Pais  datos = listPais.get(selectedIndex);
                                    txtPregnat.setText(tipoDocumento[selectedIndex]);
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
                txtPregnat.clearFocus();
            }
        });

        txtEducation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int itemSelected = 0;
            String[] tipoDocumento = {"NINGUNA", "PRIMARIA", "SECUNDARIA", "GRADUADO"};
            //  NONE, PRIMARY, SECONDARY, GRADUATE

            // NINGUNA, PRIMARIA, SECUNDARIA, GRADUADO
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (hasFocus) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Su nivel de educación actual es")
                            .setSingleChoiceItems(tipoDocumento, itemSelected, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                    //  Pais  datos = listPais.get(selectedIndex);
                                    txtEducation.setText(tipoDocumento[selectedIndex]);
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
                txtEducation.clearFocus();
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
            btnSiguiente.setText("SIGUIENTE");
            LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                    /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                    /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
            );
            parametros.setMargins(5, 20, 5, 5);

            btnSiguiente.setLayoutParams(parametros);
            btnSiguiente.setBackgroundColor(getResources().getColor(R.color.colorBoton));
            ll.addView(btnSiguiente);

            btnSiguiente.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {


                    //  viewPager.setCurrentItem(1);
                    boolean activo = Validacion();


                    if (activo) {


                        String sexo = txtSexo.getEditableText().toString();
                        String campaniaId = txtCampania.getEditableText().toString();
                        String sexoApoderado = txtSexoApoderado.getEditableText().toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("nombre", txtNombres.getEditableText().toString());
                        bundle.putString("apellido", txtApellido.getEditableText().toString());
                        bundle.putString("tipoSexo", tipoSexo(sexo));
                        bundle.putString("tipoDocumento", txtTipoDocumento.getEditableText().toString());
                        bundle.putString("numeroDocumento", txtDni.getEditableText().toString());
                        bundle.putString("resultadoidCampania", idCampania(campaniaId, listCampania));
                        //  bundle.putString("idPais", idPais(pais,listPais));
                        bundle.putString("idPais", "173");
                        bundle.putString("numeroContacto", txtTelefonoContacto.getEditableText().toString());
                        bundle.putString("estadoCivil", txtEstadoCivil.getEditableText().toString());
                        // bundle.putString("edad", txtEdad.getText().toString());
                        bundle.putString("gestante", resultadoIngles(txtPregnat.getEditableText().toString()));
                        bundle.putString("educacion", resultadoInglesEducacion( txtEducation.getEditableText().toString()));

                        double t = Double.parseDouble(txtTalla.getText().toString());
                        double p = Double.parseDouble(txtPeso.getText().toString());
                        double imc = p / (t * t);
                        t = t * 100;
                        p = p * 100;
                        bundle.putString("talla", t + "");
                        bundle.putString("peso", p + "");

                        bundle.putString("imc", imc + "");

                        int edadEntera = Integer.parseInt(txtEdad.getText().toString());
                        if (edadEntera <= 18) {


                            bundle.putString("nombreApoderado", txtNombreApoderado.getEditableText().toString());
                            bundle.putString("apellidoApoderado", txtApellidoApoderado.getEditableText().toString());
                            bundle.putString("tiposexoApoderado", tipoSexo(sexoApoderado));
                            bundle.putString("tipoDocumentoApoderado", txtTipoDocumentoApoderado.getEditableText().toString());
                            bundle.putString("numeroDocumentoApoderado", txtDniApoderado.getEditableText().toString());
                            bundle.putString("edad", edadEntera + "");
                            getParentFragmentManager().setFragmentResult("key", bundle);
                            viewPager.setCurrentItem(1);
                        } else {

                            bundle.putString("nombreApoderado", "null");
                            bundle.putString("apellidoApoderado", "null");
                            bundle.putString("tiposexoApoderado", null);
                            bundle.putString("tipoDocumentoApoderado", 0 + "");
                            bundle.putString("numeroDocumentoApoderado", 0 + "");
                            bundle.putString("edad", edadEntera + "");
                            getParentFragmentManager().setFragmentResult("key", bundle);
                            viewPager.setCurrentItem(1);
                        }


                    } else {
                        Toast.makeText(getActivity(), "Ingrese la información faltante", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
        return rootView;
    }

    String tipoSexo (String sexo ){
        String resultado = "";

        if (sexo.equals("FEMENINO")) {
            resultado = "F";
        } else {
            resultado = "M";
        }
        return resultado;
    }


    public String idCampania (String campania, ArrayList < Campania > listCampania ){
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


    public String idPais (String campania, ArrayList < Pais > listPais ){
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


    public boolean Validacion () {
        boolean estado = true;

        String nombre = txtNombres.getText().toString();
        if (nombre.equals("")) {
            Toast.makeText(getActivity(), "Escribir Nombre !!!", Toast.LENGTH_SHORT).show();

            // Focus en txtNombres y abrir el teclado con un retraso
            txtNombres.requestFocus();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtNombres, InputMethodManager.SHOW_IMPLICIT);
                }
            }, 100); // 100 ms delay

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
        if (!txtEdad.getText().toString().isEmpty()){

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


    public String resultadoIngles(String entrada){
        String resultado = null;

        if (entrada.equals("SI")){
            resultado = "YES";
        }

        else if (entrada.equals("NO")){

            resultado = "NOT";
        }

        else if (entrada.equals("INDETERMINADO")){

            resultado = "INDETERMINATE";
        }

        return resultado;
    }


    public String resultadoInglesEducacion(String entrada){
        String resultado = null;

        if (entrada.equals("NINGUNA")){
            resultado = "NONE";
        }

        else if (entrada.equals("PRIMARIA")){

            resultado = "PRIMARY";
        }

        else if (entrada.equals("SECUNDARIA")){

            resultado = "SECONDARY";
        }

        else if (entrada.equals("GRADUADO")){

            resultado = "GRADUATE";
        }

        return resultado;
    }


}