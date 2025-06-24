package com.example.yanadenv.registro.fragmentos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.viewpager.widget.ViewPager;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yanadenv.R;
import com.example.yanadenv.conexion.ConexionSQLiteHelper;

import com.example.yanadenv.data.model.AssigneeDTO;
import com.example.yanadenv.data.model.ClinicDTO;
import com.example.yanadenv.data.model.ClinicdataDTO;
import com.example.yanadenv.data.model.ClinicvalueDTO;
import com.example.yanadenv.data.model.Post;
import com.example.yanadenv.data.model.Project;
import com.example.yanadenv.data.model.SocieconomicDTO;
import com.example.yanadenv.data.model.readGroup.Readgroup2;
import com.example.yanadenv.data.remote.APIService;
import com.example.yanadenv.principal.Inicio;
import com.example.yanadenv.principal.utilidades.Utilidades;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClinicosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClinicosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewPager viewPager;
    TextInputLayout txtNombre, txtApellido;
    LinearLayout lcontenedor;
    LinearLayout ll;
    List<TextInputLayout> allEds = new ArrayList<TextInputLayout>();
    private APIService mAPIService;
    String nombreParticipante;
    int identificador;


    int vueltas;
    int cantidadVueltas;


    private String apellido;

    private String tipoSexo;
    private String tipoDocumento;
    private String numeroDocumento;
    private String campaniaId;


    private String idPais;
    private String numeroContacto;
    private String estadoCivil;
    private String peso;
    private String talla;
    private String imc;
    private String nombreApoderado;
    private String apellidoApoderado;
    private String tiposexoApoderado;
    private String tipoDocumentoApoderado;
    private String numeroDocumentoApoderado;
    private String edad;

    //// Varaibles socio economico

    private String apartamento;
    private String material;
    private String electricidad;
    private String agua;
    private String drenaje;
    private String numeroFamiliares;
    private String numerodeNinios;
    private String numerodeAdultos;
    private String numerodeInfantes;
    private String numerodeGestante;
    private String datosClinicos1;
    private String datosClinicos2;
    private String readGroupFull;
    private Readgroup2 ReadGroupObj;
    private List<ClinicDTO> listaPruba;
    //YA NO LO TOQUES ESTA PERFECTO
    private ArrayList<Project> listProjectClinic;
    private String usuario;
    private String contrasenia;
    private String educacion;
    private String gestante;

    //ArrayList<ClinicDTO> ListClinicDto;

    private Project ListClinicDto;


// xD  gracias mannnnn


    //TextInputEditText txtNombre,txtApellido;
    // Button btnIngresar;

    public ClinicosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClinicosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClinicosFragment newInstance(String param1, String param2) {
        ClinicosFragment fragment = new ClinicosFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_clinicos, container, false);
        final LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.llBotonera);
        // mAPIService = ApiUtils.getAPIService();

        //Project ListClinicDto = new Project();

        // ListClinicDto = new Project();

        SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        datosClinicos1 = prefs.getString("datosClinicos1", "");
        datosClinicos2 = prefs.getString("datosClinicos2", "");
        readGroupFull = prefs.getString("listReadGroupFull", "");
        usuario = prefs.getString("usuario", "");
        contrasenia = prefs.getString("contrasenia", "");


        // Estructura Datos Clinicos 1
        listProjectClinic = new ArrayList<Project>();
        Gson clinic1 = new Gson();
        Project DatosClinicos1 = new Project();
        DatosClinicos1 = clinic1.fromJson(datosClinicos1, Project.class);
        listProjectClinic.add(DatosClinicos1);


        // Estructura Datos Clinicos 2
        Gson clinic2 = new Gson();
        Project DatosClinicos2 = new Project();
        DatosClinicos2 = clinic2.fromJson(datosClinicos2, Project.class);
        listProjectClinic.add(DatosClinicos2);

        // Estructura readGroupFull 2
        Gson readGroup = new Gson();
        ReadGroupObj = new Readgroup2();
        ReadGroupObj = readGroup.fromJson(readGroupFull, Readgroup2.class);

        ListClinicDto = new Project();
        List<ClinicDTO> objClinic = new ArrayList<>();
        identificador = 0;
        List<ClinicDTO> clinicDTOs = new ArrayList<>();
        for (int i = 0; i < listProjectClinic.size(); i++) {
            generarTitulosBloques(ll, listProjectClinic.get(i).getName());
            for (int j = 0; j < listProjectClinic.get(i).getClinicDTOs().size(); j++) {
                ClinicDTO clinicDto = new ClinicDTO();
                clinicDto = listProjectClinic.get(i).getClinicDTOs().get(j);

                if (clinicDto.getInput().equals("SIMPLE")) {
                    ClinicDTO objClic = new ClinicDTO();
                    objClic.setId(clinicDto.getId());
                    clinicDTOs.add(identificador, objClic);
                    ListClinicDto.setClinicDTOs(clinicDTOs);
                    generacionSeleccionSimple(ll, clinicDto.getName(), clinicDto.getClinicvalueDTOs(), identificador);
                } else if (clinicDto.getInput().equals("MULTIPLE")) {
                    ClinicDTO objClic = new ClinicDTO();
                    objClic.setId(clinicDto.getId());
                    clinicDTOs.add(identificador, objClic);
                    ListClinicDto.setClinicDTOs(clinicDTOs);
                    generacionSeleccionMultiple(ll, clinicDto.getName(), clinicDto.getClinicvalueDTOs(), identificador);
                }
             else if (clinicDto.getInput().equals("TEXT")) {
                    ClinicDTO objClic = new ClinicDTO();
                    objClic.setId(clinicDto.getId());
                    clinicDTOs.add(identificador, objClic);
                    ListClinicDto.setClinicDTOs(clinicDTOs);
                    generacionCampoTexto(ll, clinicDto.getName(), "Entero", identificador);
            }
                identificador++;
            }
        }
        generarBotones(ll);


        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                // nombre  = bundle.getString("entradaDatoSocio");

                ///PARTICIPANTES
                nombreParticipante = bundle.getString("nombre");
                apellido = bundle.getString("apellido");
                tipoSexo = bundle.getString("tipoSexo");
                tipoDocumento = bundle.getString("tipoDocumento");
                numeroDocumento = bundle.getString("numeroDocumento");
                campaniaId = bundle.getString("resultadoidCampania");
                idPais = bundle.getString("idPais");
                numeroContacto = bundle.getString("numeroContacto");
                estadoCivil = bundle.getString("estadoCivil");
                peso = bundle.getString("peso");
                talla = bundle.getString("talla");
                imc = bundle.getString("imc");
                nombreApoderado = bundle.getString("nombreApoderado");
                apellidoApoderado = bundle.getString("apellidoApoderado");
                tiposexoApoderado = bundle.getString("tiposexoApoderado");
                tipoDocumentoApoderado = bundle.getString("tipoDocumentoApoderado");
                numeroDocumentoApoderado = bundle.getString("numeroDocumentoApoderado");
                edad = bundle.getString("edad");
                gestante = bundle.getString("gestante");
                educacion = bundle.getString("educacion");

                // datos Socio economicos
               /* apartamento = bundle.getString("apartamento");
                material = bundle.getString("material");
                electricidad = bundle.getString("servicioElectrico");
                agua = bundle.getString("servicioAgua");
                drenaje = bundle.getString("serviciodrenaje");
                numeroFamiliares = bundle.getString("numeroFamilia");
                numerodeNinios = bundle.getString("numeroNinios");
                numerodeAdultos = bundle.getString("numeroAdultos");
                numerodeInfantes = bundle.getString("numeroInfantes");
                numerodeGestante = bundle.getString("numerodeGestantes");*/
            }
        });

        return rootView;
    }


    public void generarBotones(LinearLayout ll) {

        //creando un objeto de la clase button
        final Button boton = new Button(getActivity());
        final Button btnAtras = new Button(getActivity());

        //Personalizando botones
        btnAtras.setTextColor(Color.WHITE);
        btnAtras.setText("Atras");
        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
        );
        parametros.setMargins(5, 20, 5, 5);

        btnAtras.setLayoutParams(parametros);
        btnAtras.setBackgroundColor(getResources().getColor(R.color.colorBoton));


        boton.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams parametros2 = new LinearLayout.LayoutParams(
                /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
        );
        parametros2.setMargins(5, 20, 5, 5);
        boton.setLayoutParams(parametros2);
        boton.setText("Procesar");
        boton.setLayoutParams(parametros);
        boton.setBackgroundColor(getResources().getColor(R.color.colorBoton));
        //
        // boton.getDRA
        ll.addView(btnAtras);
        ll.addView(boton);


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
                viewPager.setCurrentItem(1);
            }
        });


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   if (elementosVacios()) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Mensaje")
                            .setMessage("DESEA REGISTRAR LOS DATOS DEL PARTICIPANTE")
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("AlertDialog", "Positive");
                                    // AQUI MANDAR AL WEB SERVICES
                                    // getPosts();
                                    //Intent i = new Intent(Inicio.this,Login.class);
                                    // startActivity(i);
                                    try {
                                        registradDatosSql(
                                                nombreParticipante,
                                                apellido,
                                                tipoSexo,
                                                tipoDocumento,
                                                numeroDocumento,
                                                campaniaId,
                                                idPais,
                                                numeroContacto,
                                                estadoCivil,
                                                peso,
                                                talla,
                                                imc,
                                                nombreApoderado,
                                                apellidoApoderado,
                                                tiposexoApoderado,
                                                tipoDocumentoApoderado,
                                                numeroDocumentoApoderado,
                                                edad,
                                                gestante,
                                                educacion
                                        );
                                        registradDatosSql_ReadGroup();
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
//                } else {
//                    Toast.makeText(getActivity(), "Debe ingresar todos los datos clínicos ", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }


    public void generarTitulosBloques(LinearLayout ll, String titulo) {
        //creando un objeto de la clase button
        final TextView text = new TextView(getActivity());

        LinearLayout.LayoutParams parametros2 = new LinearLayout.LayoutParams(
                /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // LayoutParams params = (LayoutParams) txt_fecha.getLayoutParams();
        //params.width = 20;
        parametros2.setMargins(5, 20, 5, 5);

        text.setLayoutParams(parametros2);
        text.setText(titulo);
        text.setTextSize(25);
        //text.setBackgroundColor(getResources().getColor(R.color.colorBoton));
        //
        // boton.getDRA
        ll.addView(text);
    }

//    private boolean elementosVacios() {
//
//        boolean estado = true;
//
//        for (int i = 0; i < allEds.size(); i++) {
//
//            if (isEmpty(allEds.get(i).getText().toString())) {
//                estado = false;
//                break;
//            }
//
//        }
//
//        return estado;
//    }


    private void generacionCampoTexto(LinearLayout ll, String titulo, String tipo, int index) {
        // Creamos el TextInputLayout
        TextInputLayout textInputLayout = new TextInputLayout(getActivity(), null);
        textInputLayout.setId(index);
        textInputLayout.setHint(titulo);

        // Creamos el TextInputEditText dentro del TextInputLayout
        TextInputEditText textInputEditText = new TextInputEditText(textInputLayout.getContext());

        // Configuramos el tipo de entrada según el tipo
        if (tipo.equals("Cadena")) {
            textInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (tipo.equals("Entero")) {
            textInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (tipo.equals("Decimal")) {
            textInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        // Añadimos el TextInputEditText al TextInputLayout
        textInputLayout.addView(textInputEditText);
        ll.addView(textInputLayout);

        // Asignamos un listener para guardar el valor ingresado al objeto correspondiente
        textInputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Solo guardamos cuando el campo pierde el foco
                String enteredText = textInputEditText.getText().toString();
                ListClinicDto.getClinicDTOs().get(index).setName(enteredText); // Guardar el valor en ClinicDTO
                ListClinicDto.getClinicDTOs().get(index).setInput("TEXT"); // Guardar el valor en ClinicDTO

            }
        });
    }



    // Tipo Elemento, Titulo , Lista de elementos , tipoDato
    private void generacionSeleccionMultiple(LinearLayout ll, String titulo, List<ClinicvalueDTO> elementos, int j) {


        // obtener el id
        //obtener el name
        final String opciones[] = new String[elementos.size()];
        final String idOpciones[] = new String[elementos.size()];
        final boolean activo[] = new boolean[elementos.size()];


        final String tituloOpciones = titulo;
//
//        objClinicValueDto.setId("");
//        objClinicValueDto.setName("");

        // ListClinicDto.add(objClinicValueDto);

        for (int i = 0; i < elementos.size(); i++) {
            if (elementos.get(i).getName()!=null){
                opciones[i] = elementos.get(i).getName();
            }else {
                opciones[i] = "Por identificar";
            }
            idOpciones[i] = elementos.get(i).getId();
        }


        TextInputLayout textGeneracionMul = new TextInputLayout(getActivity(), null);
        final int datoid = j;
        allEds.add(textGeneracionMul);
        textGeneracionMul.setId(datoid);


        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
        );
        parametros.setMargins(5, 20, 5, 5);
        textGeneracionMul.setLayoutParams(parametros);
        //Asignamos propiedades de layout al boton
        //Asignamos Texto al botón
        textGeneracionMul.setHint(titulo);
        final TextInputEditText Campo1 = new TextInputEditText(textGeneracionMul.getContext());

        Campo1.setInputType(InputType.TYPE_NULL);

        //Campo1.setFocusableInTouchMode(false);
        //Añadimos el botón a la botonera
        textGeneracionMul.addView(Campo1);
        ll.addView(textGeneracionMul);

        Campo1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                            //final boolean[] checkedItems = {false, false, false, false};

                                            String resultado = "";
                                            ArrayList<String> datos = new ArrayList<String>(); // Create an ArrayList object

                                            @Override

                                            public void onFocusChange(View v, boolean hasFocus) {

                                                try{
                                                if (hasFocus)
                                                {
                                                    resultado = "";
                                                    new AlertDialog.Builder(getActivity())
                                                            .setTitle(tituloOpciones)
                                                            .setMultiChoiceItems(opciones, activo, new DialogInterface.OnMultiChoiceClickListener() {

                                                                @Override
                                                                public void onClick(DialogInterface dialog, int index, boolean isChecked) {

                                                                    //Log.d("MainActivity", "clicked item index is " + index);
                                                                    activo[index] = isChecked;

                                                                   /* if (isChecked) {
                                                                        if (opciones[index] != null) {
                                                                            datos.add(opciones[index]);
                                                                        }
                                                                    }*/
                                                                }
                                                            })
                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Campo1.setText("");
                                                                    List<ClinicvalueDTO> listObj = null;
                                                                    listObj = new ArrayList<ClinicvalueDTO>();
                                                                    for (int i = 0; i < activo.length; i++) {


                                                                        if (activo[i]) {
                                                                            resultado += opciones[i] + ",";
                                                                            ClinicvalueDTO objClinicValueDto = new ClinicvalueDTO();
                                                                            //List<MyType> myList = new ArrayList<MyType>();

                                                                            objClinicValueDto.setName(opciones[i]);
                                                                            objClinicValueDto.setId(idOpciones[i]);
                                                                            listObj.add(objClinicValueDto);
                                                                            //  mList.add( new MiClase("Dato1",1) );
                                                                            //   ListClinicDto.set(j, )
                                                                            //   ListClinicDto.set(j, objClinicValueDto);
                                                                        }
                                                                    }
                                                                    ListClinicDto.getClinicDTOs().get(j).setClinicvalueDTOs(listObj);

                                                                    resultado = resultado.replaceAll(",$", "");
                                                                    Campo1.setText(resultado);
                                                                    Campo1.clearFocus();
                                                                }
                                                            })
                                                            // .setNegativeButton("Cancel", null)
                                                            .show();
                                                    // Toast.makeText(getActivity(), "Prueba", Toast.LENGTH_SHORT).show();
                                                }
                                                }

                                                catch (Exception exception) {
                                                    exception.printStackTrace();
                                                }

                                                Campo1.clearFocus();
                                            }
                                        }
        );
    }

    private void generacionSeleccionSimple(LinearLayout ll, String titulo, List<ClinicvalueDTO> elementos, int j) {


        ClinicvalueDTO objClinicValueDto = new ClinicvalueDTO();
        // obtener el id
        //obtener el name
        final String opciones[] = new String[elementos.size()];
        final String idOpciones[] = new String[elementos.size()];
        final String tituloOpciones = titulo;

        objClinicValueDto.setId("");
        objClinicValueDto.setName("");

        // ListClinicDto.add(objClinicValueDto);

        for (int i = 0; i < elementos.size(); i++) {
            opciones[i] = elementos.get(i).getName();
            idOpciones[i] = elementos.get(i).getId();
        }


        TextInputLayout textGeneracionUnica = new TextInputLayout(getActivity(), null);
        final int datoid = j;
        allEds.add(textGeneracionUnica);
        textGeneracionUnica.setId(datoid);


        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
        );
        parametros.setMargins(5, 20, 5, 5);
        textGeneracionUnica.setLayoutParams(parametros);
        //Asignamos propiedades de layout al boton
        //Asignamos Texto al botón
        textGeneracionUnica.setHint(tituloOpciones);
        final TextInputEditText Campo1 = new TextInputEditText(textGeneracionUnica.getContext());
        Campo1.setInputType(InputType.TYPE_NULL);
        // Campo1.setFocusableInTouchMode(false);
        //Añadimos el botón a la botonera
        textGeneracionUnica.addView(Campo1);
        ll.addView(textGeneracionUnica);


        Campo1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                            int itemSelected = 0;
                                            ArrayList<String> datos = new ArrayList<String>(); // Create an ArrayList object

                                            @Override
                                            public void onFocusChange(View v, boolean hasFocus) {

                                                if (hasFocus) {
                                                    new AlertDialog.Builder(getActivity())
                                                            .setTitle(tituloOpciones)
                                                            .setSingleChoiceItems(opciones, itemSelected, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                                                    Campo1.setText(opciones[selectedIndex]);
                                                                    itemSelected = selectedIndex;
                                                                    List<ClinicvalueDTO> listObj = new ArrayList<ClinicvalueDTO>();
                                                                    //List<MyType> myList = new ArrayList<MyType>();

                                                                    objClinicValueDto.setName(opciones[selectedIndex]);
                                                                    objClinicValueDto.setId(idOpciones[selectedIndex]);
                                                                    listObj.add(objClinicValueDto);
                                                                    //  mList.add( new MiClase("Dato1",1) );
                                                                    //   ListClinicDto.set(j, )
                                                                    //   ListClinicDto.set(j, objClinicValueDto);
                                                                    ListClinicDto.getClinicDTOs().get(j).setClinicvalueDTOs(listObj);
                                                                    //  clinicDTOs.add(identificador,objClic);
                                                                    //ListClinicDto.setClinicDTOs(clinicDTOs);


                                                                }
                                                            })
                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                }
                                                            })
                                                            .show();
                                                }
                                                Campo1.clearFocus();
                                            }
                                        }
        );
    }


    ////ENTIERRALE !!


    public void agregarBotones(int elementos, LinearLayout contenedor) {
        for (int i = 0; i < elementos; i++) {
            //creando un objeto de la clase button
            Button boton = new Button(getActivity());
            //Personalizando botones
            boton.setText("Boton nr°: " + (i + 1));
            contenedor.addView(boton);
        }
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("elementos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    ArrayList<Post> obtenerListaParticipanteRegistroFinal() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();
        Post dato = null;
        AssigneeDTO assigneeDTO = null;
        SocieconomicDTO socieconomicDTO = null;
        List<ClinicdataDTO> clinicdataDTOs = null;
        Project objProject = new Project();
        //ListClinicDto
        //SELECT * FROM table ORDER BY column DESC LIMIT 1
        ArrayList<Post> objListParticipante = new ArrayList<Post>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES + " ORDER BY " + Utilidades.CAMPO_ID + " DESC LIMIT 1", null);

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
            dato.setSocieconomicDTO(socieconomicDTO);
            ///////////////////////////////////////////////
            ///// POR AGREGAR DESPUES CUANDO FRERLY LO TENGA LISTO
            ///////////
            // clinicdataDTOs = listDataDto();


            // clinicdataDTOs = null;
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

    List<ClinicdataDTO> listDataDto() {

        List<ClinicdataDTO> listClinicDataDTO = new ArrayList<>();
        Project objProject = new Project();

        List<ClinicDTO> clinicDTOs = new ArrayList<>();
        //List<ClinicDTO> objClinicDto = new ArrayList<ClinicDTO>();
        List<ClinicdataDTO> listClinicValueDto = new ArrayList<>();

        for (int i = 0; i < ListClinicDto.getClinicDTOs().size(); i++) {


            ClinicDTO objClinicDto = new ClinicDTO();

            List<Object> object = new ArrayList<Object>();

            objClinicDto.setId(ListClinicDto.getClinicDTOs().get(i).getId());

            if (ListClinicDto != null
                    && ListClinicDto.getClinicDTOs() != null
                    && ListClinicDto.getClinicDTOs().size() > i
                    && ListClinicDto.getClinicDTOs().get(i) != null
                    && ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs() == null) {


                ClinicdataDTO objClinicData = new ClinicdataDTO();
                objClinicData.setClinicId(ListClinicDto.getClinicDTOs().get(i).getId());
                objClinicData.setValue(ListClinicDto.getClinicDTOs().get(i).getName());
                listClinicValueDto.add(objClinicData);
                // Aquí puedes ejecutar tu lógica
            }
            else {



            // CAMPO DE TEXTO
            for (int j = 0; j < ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().size(); j++) {


                ClinicdataDTO objClinicData = new ClinicdataDTO();
                objClinicData.setClinicId(ListClinicDto.getClinicDTOs().get(i).getId());
                objClinicData.setValue(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getName());
                objClinicData.setClinicvalueId(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getId());
                listClinicValueDto.add(objClinicData);

            }

            }

        }


        return listClinicValueDto;
    }


    private void registradDatosSql_ReadGroup() {

        ConexionSQLiteHelper conn2 = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db2 = conn2.getWritableDatabase();
        //   rg.getReadDTOs()
        for (int i = 0; i < ReadGroupObj.getReadDTOs().size(); i++) {

            // Nombre Pecho derecho izquierdo etc
            String nombre = ReadGroupObj.getReadDTOs().get(i).getName();
            String nombreSigla = nombreGrabacacion(nombre);
           // String nombreSigla = nombre;

            for (int j = 0; j < ReadGroupObj.getReadDTOs().get(i).getReadvalueDTOs().size(); j++) {

                /// Sub nombre 2 3 4 6
                String subNombre = ReadGroupObj.getReadDTOs().get(i).getReadvalueDTOs().get(j).getName();
                String idLectura = ReadGroupObj.getReadDTOs().get(i).getReadvalueDTOs().get(j).getId();
                String insert2 = "INSERT INTO " + Utilidades.TABLA_PARTICIPANTES_READGROUP
                        + " ( "
                        + Utilidades.CAMPO_idParticipante + ","
                        + Utilidades.CAMPO_idLectura + ","
                        + Utilidades.CAMPO_name + ","
                        + Utilidades.CAMPO_ruta + ","
                        + Utilidades.CAMPO_flag + ","
                        + Utilidades.CAMPO_dni + ","
                        + Utilidades.CAMPO_namesigla + ","
                        + Utilidades.CAMPO_usuario + ","
                        + Utilidades.CAMPO_contrasenia + ")" +
                        " VALUES ( '"
                        + numeroDocumento + "_" + nombreParticipante + "','"
                        + idLectura + "','"
                        + nombre + " " + subNombre + "','"
                        + "" + "','"
                        + 0 + "','"
                        + numeroDocumento + "','"
                        + subNombre + "','"
                        + usuario + "','"
                        + contrasenia + "')";
                db2.execSQL(insert2);
            }
        }
        db2.close();

        Intent i = new Intent(getActivity(), Inicio.class);
        startActivity(i);
    }


    private void registradDatosSql(
            String nombre,
            String apellido,
            String sexo,
            String tipoDocumento,
            String numeroDocumento,
            String campaniaId,
            String pais,
            String numeroContacto,
            String estadoCivil,
            String peso,
            String talla,
            String imc,
            String nombreApoderado,
            String apellidoApoderado,
            String sexoApoderado,
            String tipoDocumentoApoderado,
            String numeroDocumentoApoderado,
            String edad,
            String gestante,
            String educacion

    ) {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getWritableDatabase();


        String insert = "INSERT INTO " + Utilidades.TABLA_GENERALES
                + " ( "
                + Utilidades.CAMPO_name + ","
                + Utilidades.CAMPO_lastname + ","
                + Utilidades.CAMPO_sex + ","
                + Utilidades.CAMPO_doctype + ","
                + Utilidades.CAMPO_docnumber + ","
                + Utilidades.CAMPO_campainId + ","
                + Utilidades.CAMPO_countryId + ","
                + Utilidades.CAMPO_contactnumber + ","
                + Utilidades.CAMPO_civilstatus + ","
                + Utilidades.CAMPO_weight + ","
                + Utilidades.CAMPO_stature + ","
                + Utilidades.CAMPO_imc + ","
                + Utilidades.CAMPO_nameApoderado + ","
                + Utilidades.CAMPO_lastnameApoderado + ","
                + Utilidades.CAMPO_sexApoderado + ","
                + Utilidades.CAMPO_doctypeApoderado + ","
                + Utilidades.CAMPO_docnumberApoderado + ","
                + Utilidades.CAMPO_apartament + ","
                + Utilidades.CAMPO_material + ","
                + Utilidades.CAMPO_electric + ","
                + Utilidades.CAMPO_water + ","
                + Utilidades.CAMPO_drain + ","
                + Utilidades.CAMPO_familyNumber + ","
                + Utilidades.CAMPO_ChildrenNumber + ","
                + Utilidades.CAMPO_adultNumber + ","
                + Utilidades.CAMPO_infantNumber + ","
                + Utilidades.CAMPO_pregnatNumber + ","
                + Utilidades.CAMPO_edad + ","
                + Utilidades.CAMPO_gestante + ","
                + Utilidades.CAMPO_educacion + ","
                + Utilidades.CAMPO_flag + ","
                + Utilidades.CAMPO_idParticipante + ","
                + Utilidades.CAMPO_usuario + ","
                + Utilidades.CAMPO_contrasenia + ")" +
                " VALUES ( '"
                + nombre + "','"
                + apellido + "','"
                + sexo + "','"
                + tipoDocumento + "','"
                + numeroDocumento + "','"
                + campaniaId + "','"
                + pais + "','"
                + numeroContacto + "','"
                + estadoCivil + "','"
                + peso + "','"
                + talla + "','"
                + imc + "','"
                + nombreApoderado + "','"
                + apellidoApoderado + "','"
                + sexoApoderado + "','"
                + tipoDocumentoApoderado + "','"
                + numeroDocumentoApoderado + "','"
                + " " + "','"
                + " " + "','"
                + " " + "','"
                + " " + "','"
                + " " + "','"
                + " " + "','"
                + " " + "','"
                + " " + "','"
                + " " + "','"
                + " " + "','"
                + edad + "','"
                + gestante + "','"
                + educacion + "','"
                + 0 + "','"
                + numeroDocumento + "_" + nombre + "','"
                + usuario + "','"
                + contrasenia + "')";

        List<ClinicdataDTO> objProject = new ArrayList<>();
        objProject = listDataDto();


//        List<ClinicDTO> listdataDto = new ArrayList<>();
//        listdataDto = objProject.getClinicDTOs();

        Gson gson = new Gson();
        Object request = gson.toJson(objProject);


        String datosClinicos = request.toString();

        SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(numeroDocumento + "_" + nombre, datosClinicos);

        editor.commit();

//        Gson gson = new Gson();
//        Object request = gson.toJson(objProject);
//
//        String datosClinicos2 = request.toString();
//        SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(numeroDocumento+"_"+nombre, datosClinicos2);
//        editor.commit();
        // objProject.setClinicDTOs(listDataDto())

        db.execSQL(insert);
        db.close();
    }


    public String resultadoIngles(String entrada) {
        String resultado = null;

        if (entrada.equals("SI")) {
            resultado = "YES";
        } else if (entrada.equals("NO")) {

            resultado = "NOT";
        } else if (entrada.equals("INDETERMINADO")) {

            resultado = "INDETERMINATE";
        }

        return resultado;
    }


//    private void actualizarIdParticipante(String idParticipante) {
//        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null, 1);
//        SQLiteDatabase db = conn.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Utilidades.CAMPO_idParticipante, idParticipante);
//        db.update(Utilidades.TABLA_GENERALES, values, "(SELECT MAX(id) FROM " + Utilidades.TABLA_GENERALES + ")", null);
//        //   UPDATE table set col = 1 WHERE id = (SELECT MAX(id) FROM table)
//        db.close();
//    }

    String nombreGrabacacion(String entrada) {

        String resultado = "";
        String[] parts = entrada.split(" ");

        for (int i = 0; i < parts.length; i++) {
            if (!isEmpty(parts[i])) {
                char[] caracteres = parts[i].toCharArray();
                caracteres[0] = Character.toUpperCase(caracteres[0]);
                resultado = resultado + caracteres[0];
            }
        }
        return resultado;
    }


}