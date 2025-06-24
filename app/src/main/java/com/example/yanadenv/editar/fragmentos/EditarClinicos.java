package com.example.yanadenv.editar.fragmentos;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yanadenv.R;
import com.example.yanadenv.conexion.ConexionSQLiteHelper;
import com.example.yanadenv.data.model.ClinicDTO;
import com.example.yanadenv.data.model.ClinicdataDTO;
import com.example.yanadenv.data.model.ClinicvalueDTO;
import com.example.yanadenv.data.model.Project;
import com.example.yanadenv.data.model.readGroup.Readgroup2;
import com.example.yanadenv.data.remote.APIService;
import com.example.yanadenv.entidades.Participante;
import com.example.yanadenv.principal.utilidades.Utilidades;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarClinicos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarClinicos extends Fragment {

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
    private Project ListClinicDto;
    private String idTablaParticipante;
    private String clinicDataResultado;
    private List<ClinicdataDTO> listDataClinic;
    private int posicion;

    int seleccion = 0;

    String resultadoMultiple = "";

    ConexionSQLiteHelper conn;


    //TextInputEditText txtNombre,txtApellido;
    // Button btnIngresar;
    public EditarClinicos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarClinicos.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarClinicos newInstance(String param1, String param2) {
        EditarClinicos fragment = new EditarClinicos();
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
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_editar_clinicos, container, false);
        View rootView = inflater.inflate(R.layout.fragment_editar_clinicos, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        idTablaParticipante = prefs.getString("idParticipante", "");
        datosClinicos1 = prefs.getString("datosClinicos1", "");
        datosClinicos2 = prefs.getString("datosClinicos2", "");
        readGroupFull = prefs.getString("listReadGroupFull", "");
        usuario = prefs.getString("usuario", "");
        contrasenia = prefs.getString("contrasenia", "");
        clinicDataResultado= prefs.getString(idTablaParticipante ,"");




        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
        final LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.llBotonera);




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


        // Clinic data
        Gson clinicDataResul = new Gson();
        Type collectionType = new TypeToken<Collection<ClinicdataDTO>>(){}.getType();
        Collection<ClinicdataDTO> enums = clinicDataResul.fromJson(clinicDataResultado, collectionType);
        listDataClinic = new ArrayList(enums);


        // RECOPILACION DE DATA


        //      ListClinicDto = new ArrayList<ClinicDTO>();

        ListClinicDto = new Project();


        List<ClinicDTO> objClinic = new ArrayList<>();

        identificador = 0;
        List<ClinicDTO> clinicDTOs = new ArrayList<>();

        for (int i = 0; i < listProjectClinic.size(); i++) {

            generarTitulosBloques(ll, listProjectClinic.get(i).getName());


            for (int j = 0; j < listProjectClinic.get(i).getClinicDTOs().size(); j++) {

                ClinicDTO clinicDto = new ClinicDTO();

                //Me centro en el elemento
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
        return rootView;
    }

    public void generarBotones(LinearLayout ll) {

        //creando un objeto de la clase button
        final Button boton = new Button(getActivity());
        //final Button btnAtras = new Button(getActivity());

        //Personalizando botones
        //    btnAtras.setTextColor(Color.WHITE);
        //  btnAtras.setText("Atras");
        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
        );
        parametros.setMargins(5, 20, 5, 5);

        //    btnAtras.setLayoutParams(parametros);
        //   btnAtras.setBackgroundColor(getResources().getColor(R.color.colorBoton));


        boton.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams parametros2 = new LinearLayout.LayoutParams(
                /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
        );
        parametros2.setMargins(5, 20, 5, 5);
        boton.setLayoutParams(parametros2);
        boton.setText("ACTUALIZAR");
        boton.setLayoutParams(parametros);
        boton.setBackgroundColor(getResources().getColor(R.color.colorBoton));
        //
        // boton.getDRA
        //  ll.addView(btnAtras);
        ll.addView(boton);


//        btnAtras.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
//                viewPager.setCurrentItem(1);
//            }
//        });


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Mensaje")
                        .setMessage("¿DESEA ACTUALIZAR LOS DATOS CLINICOS?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("AlertDialog", "Positive");
                                // AQUI MANDAR AL WEB SERVICES
                                // getPosts();
                                //Intent i = new Intent(Inicio.this,Login.class);
                                // startActivity(i);
                                try {


                                    List<ClinicdataDTO> objProject = new ArrayList<>();
                                    objProject = listDataDto();

//        List<ClinicDTO> listdataDto = new ArrayList<>();
//        listdataDto = objProject.getClinicDTOs();

                                    Gson gson = new Gson();
                                    Object request = gson.toJson(objProject);


                                    String datosClinicos = request.toString();

                                    SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(idTablaParticipante, datosClinicos);
                                    editor.commit();

                                    Toast.makeText(getActivity(), "SE ACTUALIZO", Toast.LENGTH_LONG).show();

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



    private void actualizarReadGroupEstadoRecepcion(String dni, String nameSigla, String nombreArchivo) {
        conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {dni, nameSigla};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_flag, 0);
        values.put(Utilidades.CAMPO_ruta, nombreArchivo);
        db.update(Utilidades.TABLA_PARTICIPANTES_READGROUP, values, Utilidades.CAMPO_dni + "=?" + " and " + Utilidades.CAMPO_namesigla + "=?", parametros);
        db.close();
    }

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



    // Tipo Elemento, Titulo , Lista de elementos , tipoDato


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
        posicion = 0;
        for (int i =0 ; i< opciones.length; i++ ){


             for (int x =0 ; x < listDataClinic.size(); x++){

                 if (idOpciones[i].equals(listDataClinic.get(x).getClinicvalueId())){
                     posicion = i ;
                     List<ClinicvalueDTO> listObj = new ArrayList<ClinicvalueDTO>();
                     //List<MyType> myList = new ArrayList<MyType>();
                     objClinicValueDto.setName(opciones[i]);
                     objClinicValueDto.setId(idOpciones[i]);
                     listObj.add(objClinicValueDto);
                     ListClinicDto.getClinicDTOs().get(j).setClinicvalueDTOs(listObj);
                 }
             }
        }


        Campo1.setText(opciones[posicion]);

        Campo1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                            int itemSelected = posicion;
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
            opciones[i] = elementos.get(i).getName();
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
        String nombreCampoMultiple = "";
        List<ClinicvalueDTO> listObj1 = null;
        listObj1 = new ArrayList<ClinicvalueDTO>();
        for (int i =0 ; i < idOpciones.length;i++){


            for (int x=0 ; x < listDataClinic.size(); x++){

                if (idOpciones[i].equals(listDataClinic.get(x).getClinicvalueId())){
                    activo[i] = true;
                    nombreCampoMultiple += opciones[i] + ",";
                    Campo1.setText(nombreCampoMultiple);

                    ClinicvalueDTO objClinicValueDto = new ClinicvalueDTO();
                    //List<MyType> myList = new ArrayList<MyType>();

                    objClinicValueDto.setName(opciones[i]);
                    objClinicValueDto.setId(idOpciones[i]);
                    listObj1.add(objClinicValueDto);
                }
            }
        }
        ListClinicDto.getClinicDTOs().get(j).setClinicvalueDTOs(listObj1);



        Campo1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                            //final boolean[] checkedItems = {false, false, false, false};

                                            String resultado = "";
                                            ArrayList<String> datos = new ArrayList<String>(); // Create an ArrayList object

                                            @Override

                                            public void onFocusChange(View v, boolean hasFocus) {

                                                if (hasFocus) {
                                                    resultado = "";
                                                    new AlertDialog.Builder(getActivity())
                                                            .setTitle(tituloOpciones)
                                                            .setMultiChoiceItems(opciones, activo, new DialogInterface.OnMultiChoiceClickListener() {

                                                                @Override
                                                                public void onClick(DialogInterface dialog, int index, boolean isChecked) {

                                                                    //Log.d("MainActivity", "clicked item index is " + index);
                                                                    activo[index] = isChecked;

                                                                    if (isChecked) {
                                                                        if (opciones[index] != null) {
                                                                            datos.add(opciones[index]);
                                                                        }
                                                                    }
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
                                                Campo1.clearFocus();
                                            }
                                        }
        );
    }


    public String resultadoClinicDataDTO(){

        conn = new ConexionSQLiteHelper(getActivity().getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();
        Participante dato = new Participante();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES + " where " +
                Utilidades.CAMPO_idParticipante + " = '" + idTablaParticipante + "'", null);

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
            dato.setFlag(cursor.getInt(29));
            dato.setIdParticipante(cursor.getString(30));
        }

        return null;
    }



}