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

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.example.yanadenv.data.model.Readgroup;
import com.example.yanadenv.data.model.SocieconomicDTO;
import com.example.yanadenv.data.model.readGroup.Readgroup2;
import com.example.yanadenv.data.remote.APIService;
import com.example.yanadenv.entidades.Participante;
import com.example.yanadenv.principal.Inicio;
import com.example.yanadenv.principal.utilidades.Utilidades;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SocioeconomicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocioeconomicoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private APIService mAPIService;

    private String mParam1;
    private String mParam2;
    ViewPager viewPager;
    TextInputLayout txtNombre, txtApellido;
    LinearLayout lcontenedor;
    LinearLayout ll;
    List<TextInputLayout> allEds = new ArrayList<TextInputLayout>();

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
    private String gestante;
    private String educacion;

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

    //ArrayList<ClinicDTO> ListClinicDto;

    private Project ListClinicDto;
    ConexionSQLiteHelper conn;
    ArrayList<Participante> listParticipante;


    // TODO: Rename and change types of parameters

    public SocioeconomicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocioeconomicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SocioeconomicoFragment newInstance(String param1, String param2) {
        SocioeconomicoFragment fragment = new SocioeconomicoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                /*
                mAPIService = ApiUtils.getAPIService();
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
                educacion = bundle.getString("educacion");*/



                conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
                //listViewPersonas = (ListView) findViewById(R.id.listaDatos);

               // Toast.makeText(getActivity(), nombre, Toast.LENGTH_SHORT).show();
            }
        });
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
        datosClinicos1 = prefs.getString("SocieconómicoJson", "");

        // Estructura Datos Clinicos 1
        listProjectClinic = new ArrayList<Project>();
        Gson clinic1 = new Gson();
        Project DatosClinicos1 = new Project();
        DatosClinicos1 = clinic1.fromJson(datosClinicos1, Project.class);
        listProjectClinic.add(DatosClinicos1);

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
                    objClic.setInput("SIMPLE");
                    clinicDTOs.add(identificador, objClic);
                    ListClinicDto.setClinicDTOs(clinicDTOs);
                    generacionSeleccionSimple(ll, clinicDto.getName(), clinicDto.getClinicvalueDTOs(), identificador);
                } else if (clinicDto.getInput().equals("MULTIPLE")) {
                    ClinicDTO objClic = new ClinicDTO();
                    objClic.setId(clinicDto.getId());
                    objClic.setInput("MULTIPLE");
                    clinicDTOs.add(identificador, objClic);
                    ListClinicDto.setClinicDTOs(clinicDTOs);
                    generacionSeleccionMultiple(ll, clinicDto.getName(), clinicDto.getClinicvalueDTOs(), identificador);
                }
                else if (clinicDto.getInput().equals("TEXT")) {
                    ClinicDTO objClic = new ClinicDTO();
                    objClic.setId(clinicDto.getId());
                    objClic.setInput("TEXT");
                    clinicDTOs.add(identificador, objClic);
                    ListClinicDto.setClinicDTOs(clinicDTOs);
                    generacionCampoTexto(ll, clinicDto.getName(), clinicDto.getClinicvalueDTOs() , "Entero", identificador);
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

            }
        });
        return rootView;
    }






    List<ClinicdataDTO> listDataDto() {

        List<ClinicdataDTO> listClinicDataDTO = new ArrayList<>();
        Project objProject = new Project();

        List<ClinicDTO> clinicDTOs = new ArrayList<>();
        //List<ClinicDTO> objClinicDto = new ArrayList<ClinicDTO>();
        List<ClinicdataDTO> listClinicValueDto = new ArrayList<>();

        for (int i = 0; i < ListClinicDto.getClinicDTOs().size(); i++) {

            // ClinicDTO objClinicDto = new ClinicDTO();
            //objClinicDto.setId(ListClinicDto.getClinicDTOs().get(i).getId());

            // CAMPO DE TEXTO


            if(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs() != null){
                if(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().size() != 0){

                    for (int j = 0; j < ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().size(); j++) {
                        ClinicdataDTO objClinicData = new ClinicdataDTO();
                        objClinicData.setClinicId(ListClinicDto.getClinicDTOs().get(i).getId());
                        objClinicData.setValue(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getName());
                        objClinicData.setClinicvalueId(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getId());
                        listClinicValueDto.add(objClinicData);
                    }
                }
                else {
                    ClinicdataDTO objClinicData = new ClinicdataDTO();
                    objClinicData.setClinicId(ListClinicDto.getClinicDTOs().get(i).getId());
                    //Nuevo incluido
                    objClinicData.setValue(" ");
                    //objClinicData.setValue(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getName());
                    //objClinicData.setClinicvalueId(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getId());
                    listClinicValueDto.add(objClinicData);
                }

            } else
            if (!ListClinicDto.getClinicDTOs().get(i).getInput().equals("TEXT")) {
                ClinicdataDTO objClinicData = new ClinicdataDTO();
                objClinicData.setClinicId(ListClinicDto.getClinicDTOs().get(i).getId());
                //Nuevo incluido
                objClinicData.setValue(" ");
                // objClinicData.setClinicvalueId(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getId());
                listClinicValueDto.add(objClinicData);
            }


            else {
                if (ListClinicDto.getClinicDTOs().get(i).getInput().equals("TEXT")) {
                    //objClinicDto.setValue(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getName());
                    //   objClinicDto.setClinicvalueId(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getId());

                    //  objClinicData.setValue(ListClinicDto.getClinicDTOs().get(i).getName());
                    ClinicdataDTO objClinicData = new ClinicdataDTO();
                    objClinicData.setClinicId(ListClinicDto.getClinicDTOs().get(i).getId());
                    if (ListClinicDto.getClinicDTOs().get(i).getName()!= null
                        //        || !ListClinicDto.getClinicDTOs().get(i).getName().equals("")
                    ){
                        //   objClinicData.setValue(ListClinicDto.getClinicDTOs().get(i).getId());
                        objClinicData.setValue(ListClinicDto.getClinicDTOs().get(i).getName());
                    }
                    else {
                        //  objClinicData.setValue(ListClinicDto.getClinicDTOs().get(i).getId());
                        objClinicData.setValue(" ");

                    }

                    //objClinicData.setValue(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getName());
                    //objClinicData.setClinicvalueId(ListClinicDto.getClinicDTOs().get(i).getClinicvalueDTOs().get(j).getId());
                    listClinicValueDto.add(objClinicData);
                }
            }



        }


        return listClinicValueDto;
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

    private void generacionCampoTexto(LinearLayout ll, String titulo,List<ClinicvalueDTO> elementos ,String tipo, int j) {
        TextInputLayout texto1 = new TextInputLayout(getActivity(), null);
        final int datoid = j;
        allEds.add(texto1);
        texto1.setId(datoid);
        ClinicDTO objClinicDto = new ClinicDTO();



        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
        );
        parametros.setMargins(5, 20, 5, 5);
        texto1.setLayoutParams(parametros);
        texto1.setHint(titulo);
        TextInputEditText Campo1 = new TextInputEditText(texto1.getContext());
        // Campo1.setTransformationMethod(Decia);
        if (tipo.toString().equals("Cadena")) {
            Campo1.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (tipo.toString().equals("Entero")) {


            Campo1.addTextChangedListener(new TextWatcher() {

                // Antes de que el texto cambie (no debemos modificar nada aquí)
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String elNuevoTexto = s.toString();

                    if (!elNuevoTexto.isEmpty()) {
                        List<ClinicvalueDTO> listObj = new ArrayList<ClinicvalueDTO>();
                        objClinicDto.setName(elNuevoTexto);
                        objClinicDto.setId(ListClinicDto.getClinicDTOs().get(j).getId());


                        ListClinicDto.getClinicDTOs().get(j).setName(elNuevoTexto);
                    }
                    else {
                        objClinicDto.setName(elNuevoTexto);
                        objClinicDto.setId(ListClinicDto.getClinicDTOs().get(j).getId());
                        ListClinicDto.getClinicDTOs().get(j).setName(" ");
                    }


                }
            });

            /*
            ClinicvalueDTO objClinicValueDto = new ClinicvalueDTO();
            List<ClinicvalueDTO> listObj = new ArrayList<ClinicvalueDTO>();
            objClinicValueDto.setName( elementos.get(j).getName());
            objClinicValueDto.setId( elementos.get(j).getId());
            listObj.add(objClinicValueDto);

            ListClinicDto.getClinicDTOs().get(j).setClinicvalueDTOs(listObj);*/


            Campo1.setInputType(InputType.TYPE_CLASS_NUMBER);



        } else if (tipo.toString().equals("Decimal")) {
            Campo1.setInputType(InputType.TYPE_CLASS_PHONE);
        }



        texto1.addView(Campo1);
        ll.addView(texto1);
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

                                                                            if (opciones[i]!= null){
                                                                                objClinicValueDto.setName(opciones[i]);
                                                                            }
                                                                            else {
                                                                                objClinicValueDto.setName("No especificado");
                                                                            }


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
        boton.setText("Siguiente");
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
                viewPager.setCurrentItem(0);
            }
        });


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                try {
                                    List<ClinicdataDTO> objProject = new ArrayList<>();
                                    objProject = listDataDto();
                                    Gson gson = new Gson();
                                    Object request = gson.toJson(objProject);
                                    String datosClinicos = request.toString();
                                    SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(numeroDocumento + "_" + nombreParticipante+"_"+"socioeconomico", datosClinicos);
                                    editor.commit();



                                    Bundle bundle = new Bundle();
                                    bundle.putString("nombre", nombreParticipante);
                                    bundle.putString("apellido", apellido);
                                    bundle.putString("tipoSexo", tipoSexo);
                                    bundle.putString("tipoDocumento",tipoDocumento);
                                    bundle.putString("numeroDocumento", numeroDocumento);
                                    bundle.putString("resultadoidCampania",campaniaId);
                                    //  bundle.putString("idPais", idPais(pais,listPais));
                                    bundle.putString("idPais", idPais);
                                    bundle.putString("numeroContacto",numeroContacto);
                                    bundle.putString("estadoCivil",estadoCivil);
                                    bundle.putString("peso",peso);
                                    bundle.putString("talla",talla);

                                    // bundle.putString("edad", txtEdad.getText().toString());
                                    bundle.putString("gestante", gestante);
                                    bundle.putString("educacion", educacion);

                                    bundle.putString("nombreApoderado", nombreApoderado);
                                    bundle.putString("apellidoApoderado", apellidoApoderado);
                                    bundle.putString("tiposexoApoderado", tiposexoApoderado);
                                    bundle.putString("tipoDocumentoApoderado", tipoDocumentoApoderado);
                                    bundle.putString("numeroDocumentoApoderado", numeroDocumentoApoderado);
                                    bundle.putString("edad", edad + "");
                                    getParentFragmentManager().setFragmentResult("key", bundle);

                                    viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
                                    viewPager.setCurrentItem(2);
                                } catch (Exception exception) {
                                    exception.printStackTrace();}
            }
        });
    }





    ArrayList<Post> obtenerListaParticipanteRegistroFinal() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Post dato = null;
        AssigneeDTO assigneeDTO = null;
        SocieconomicDTO socieconomicDTO = null;
        List<ClinicdataDTO> clinicdataDTOs = null;



        //SELECT * FROM table ORDER BY column DESC LIMIT 1
        ArrayList<Post> objListParticipante = new ArrayList<Post>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES + " ORDER BY " + Utilidades.CAMPO_ID+ " DESC LIMIT 1" , null);

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

//    public void sendPost(
//            String name,
//            String lastname,
//            String sex,
//            String doctype,
//            String docnumber,
//            String campainId,
//            String countryId,
//            String contactnumber,
//            String civilstatus,
//            int weight,
//            int stature,
//            double imc,
//            AssigneeDTO assigneeDTO,
//            List<ClinicdataDTO> clinicdataDTOs,
//            SocieconomicDTO socieconomicDTO
//
//    ) {
//        Post post = new Post(
//                name,
//                lastname,
//                sex,
//                doctype,
//                docnumber,
//                campainId,
//                countryId,
//                contactnumber,
//                civilstatus,
//                weight,
//                stature,
//                imc,
//                assigneeDTO,
//                clinicdataDTOs,
//                socieconomicDTO
//        );
//
//        int edadEntera = Integer.parseInt(edad);
//        if (edadEntera>=18){
//            assigneeDTO.setSex(null);
//        }
//
//        Call<Void> call1 = mAPIService.savePost(post);
//
//        call1.enqueue(new Callback<Void>() {
//
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//
//                if (response.isSuccessful()) {
//                    String location = response.headers().value(3);
//                    String[] parts  = location.split("/participant/");
//                    /// obtener el id cliente en la posicion 1
//                  //  Toast.makeText(Inicio.this, parts[1], Toast.LENGTH_LONG).show();
//                    registradDatosSql(name, lastname,parts[1]);
//
//                 //   elemiarGeneralParticipante(docnumber);
//                }
//            }
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
//                Toast.makeText(getActivity(), "Ocurrio un error al registrar participante", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private void registradDatosSql(String nombre , String apellido , String idParticipante) {

        mAPIService.readGroupData(idParticipante).enqueue(new Callback<List<Readgroup>>() {
            @Override
            public void onResponse(Call<List<Readgroup>> call, retrofit2.Response<List<Readgroup>> response) {
                if(response.isSuccessful()) {
                    // showResponse(response.body().toString());
                    //String dato = response.body().getToken();
                    Log.i(TAG, "get submitted to API." + response.body().toString());

                    ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
                    SQLiteDatabase db = conn.getWritableDatabase();
                    //  Intent intent= new Intent (Inicio.this, Inicio.class);
                    //  startActivity(intent);

                    String insert = "INSERT INTO " + Utilidades.TABLA_PARTICIPANTES_AUDIOS
                            + " ( "
                            + Utilidades.CAMPO_idParticipante + ","
                            + Utilidades.CAMPO_nombreParticipante + ","
                            + Utilidades.CAMPO_apellidoParticipante + ")" +
                            " VALUES ( '"
                            + idParticipante + "','"
                            + nombre+ "','"
                            + apellido + "')";
                    db.execSQL(insert);
                    db.close();

                    ConexionSQLiteHelper conn2 = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null, Utilidades.CAMPO_version);
                    SQLiteDatabase db2 = conn2.getWritableDatabase();

                    Readgroup rg =  new Readgroup();
                    rg = response.body().get(0);
                    //   rg.getReadDTOs()
                    for (int i = 0 ; i < rg.getReadDTOs().size() ; i++ ) {

                        // Nombre Pecho derecho izquierdo etc
                        String nombre = rg.getReadDTOs().get(i).getName();
                        for (int j = 0 ; j <  rg.getReadDTOs().get(i).getReadvalueDTOs().size() ; j++ ) {

                            /// Sub nombre 2 3 4 6
                            String subNombre = rg.getReadDTOs().get(i).getReadvalueDTOs().get(j).getName();
                            String idLectura = rg.getReadDTOs().get(i).getReadvalueDTOs().get(j).getId();
                            String insert2 = "INSERT INTO " + Utilidades.TABLA_PARTICIPANTES_READGROUP
                                    + " ( "
                                    + Utilidades.CAMPO_idParticipante + ","
                                    + Utilidades.CAMPO_idLectura + ","
                                    + Utilidades.CAMPO_name + ","
                                    + Utilidades.CAMPO_ruta + ","
                                    + Utilidades.CAMPO_flag + ")" +
                                    " VALUES ( '"
                                    + idParticipante + "','"
                                    + idLectura+ "','"
                                    + nombre+" "+ subNombre+ "','"
                                    + ""+ "','"
                                    + 0 + "')";
                            db2.execSQL(insert2);
                        }
                    }
                    db2.close();

                    Intent i = new Intent(getActivity(), Inicio.class);
                    startActivity(i);
                }

            }
            @Override
            public void onFailure(Call<List<Readgroup>> call, Throwable t) {
               Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }


}