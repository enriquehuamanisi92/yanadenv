package com.example.yanadenv.editar.fragmentos;

import android.content.ContentValues;
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
import android.widget.Toast;

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
import com.example.yanadenv.data.model.Socioeconomico;
import com.example.yanadenv.data.model.readGroup.Readgroup2;
import com.example.yanadenv.data.remote.APIService;
import com.example.yanadenv.entidades.Participante;
import com.example.yanadenv.principal.Inicio;
import com.example.yanadenv.principal.utilidades.Utilidades;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarSocioeconomico#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarSocioeconomico extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextInputEditText txtApartament;
    private TextInputEditText txtMaterial;
    private TextInputEditText txtElectric;
    private TextInputEditText txtWater;
    private TextInputEditText txtDrain;
    private TextInputEditText txtFamilyNumber;
    private TextInputEditText txtChildrenNumber;
    private TextInputEditText txtAdultNumber;
    private TextInputEditText txtInfantNumber;
    private TextInputEditText txtPregnatNumber;
    private APIService mAPIService;

    //variables participantes Fragment 1
    private String nombre;
    private String apellido;

    private String tipoSexo;
    private String tipoDocumento;
    private String numeroDocumento;
    private String campaniaId;

    int identificador;

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
    private String idSocioeconomico;
    ConexionSQLiteHelper conn;
    private Participante objParticipante;
    ArrayList<Participante> listParticipante;
    String idTablaParticipante;
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
    private String clinicDataResultado;
    private List<ClinicdataDTO> listDataClinic;
    private int posicion;

    // TODO: Rename and change types of parameters

    ViewPager viewPager;

    LinearLayout ll;
    List<TextInputLayout> allEds = new ArrayList<TextInputLayout>();


    public EditarSocioeconomico() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarSocioeconomico.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarSocioeconomico newInstance(String param1, String param2) {
        EditarSocioeconomico fragment = new EditarSocioeconomico();
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



                nombre = bundle.getString("nombre");
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


                conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
                //listViewPersonas = (ListView) findViewById(R.id.listaDatos);

                // Toast.makeText(getActivity(), nombre, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_editar_socioeconomico, container, false);

        View rootView = inflater.inflate(R.layout.fragment_editar_socioeconomico, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        idTablaParticipante = prefs.getString("idParticipante", "");
        datosClinicos1 = prefs.getString("SocieconómicoJson", "");
        readGroupFull = prefs.getString("listReadGroupFull", "");
        usuario = prefs.getString("usuario", "");
        contrasenia = prefs.getString("contrasenia", "");
        clinicDataResultado= prefs.getString(idTablaParticipante+"_socioeconomico" ,"");

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
        final LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.llBotonera);




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
        return rootView;
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
                                    editor.putString(idTablaParticipante+"_socioeconomico", datosClinicos);
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

    private void actualizarSocioEconomico( Socioeconomico obj)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_apartament, obj.getApartment());
        values.put(Utilidades.CAMPO_electric, obj.getElectric());
        values.put(Utilidades.CAMPO_water, obj.getWater());
        values.put(Utilidades.CAMPO_drain, obj.getDrain());
        values.put(Utilidades.CAMPO_familyNumber, obj.getFamilynumber());
        values.put(Utilidades.CAMPO_ChildrenNumber, obj.getChildrennumber());
        values.put(Utilidades.CAMPO_adultNumber, obj.getAdultnumber());
        values.put(Utilidades.CAMPO_infantNumber, obj.getInfantnumber());
        values.put(Utilidades.CAMPO_pregnatNumber, obj.getPregnantnumber());


        //     values.put(Utilidades.CAMPO_lastname, apellido);
        String[] args = new String[]{idTablaParticipante};
        db.update(Utilidades.TABLA_GENERALES, values, Utilidades.CAMPO_idParticipante + "=?", args);
        //   UPDATE table set col = 1 WHERE id = (SELECT MAX(id) FROM table)
        db.close();
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



        posicion = 0;

            for (int x =0 ; x < listDataClinic.size(); x++) {
                if (listDataClinic.get(x).getClinicvalueId()==null) {

                    for (int y =0 ; y < ListClinicDto.getClinicDTOs().size() ; y++) {

                        if (ListClinicDto.getClinicDTOs().get(y).getId().equals(listDataClinic.get(x).getClinicId())){
                            if(listDataClinic.get(y).getValue() != null){

                                if( //!listDataClinic.get(y).getValue().isEmpty()
                                        listDataClinic.get(y).getValue() !=null
                                ){
                                    Campo1.setText(listDataClinic.get(y).getValue());
                                }
                                else {
                                    Campo1.setText(" ");
                                }
                            }
                            else {
                                Campo1.setText(" ");
                            }
                        }
                    }
                }
            }
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


    public String resultadoEspaniol(String entrada) {
        String resultado = null;

        if (entrada.equals("YES")) {
            resultado = "SI";
        } else if (entrada.equals("NOT")) {

            resultado = "NO";
        } else if (entrada.equals("INDETERMINATE")) {

            resultado = "INDETERMINADO";
        }

        return resultado;
    }

    // Tipo Elemento, Titulo , Lista de elementos , tipoDato

    private void generacionSeleccionSimple(LinearLayout ll, String titulo, List<ClinicvalueDTO> elementos, int j) {


        ClinicvalueDTO objClinicValueDto = new ClinicvalueDTO();
        objClinicValueDto.setId("");
        objClinicValueDto.setName("");


        // obtener el id
        //obtener el name
        final String opciones[] = new String[elementos.size()];
        final String idOpciones[] = new String[elementos.size()];
        final String tituloOpciones = titulo;



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
        for (int i =0 ; i< opciones.length; i++ ) {
            for (int x =0 ; x < listDataClinic.size(); x++){

                if (idOpciones[i].equals(listDataClinic.get(x).getClinicvalueId())){
                    posicion = i ;
                    List<ClinicvalueDTO> listObj = new ArrayList<ClinicvalueDTO>();
                    //List<MyType> myList = new ArrayList<MyType>();
                    objClinicValueDto.setName(opciones[i]);
                    objClinicValueDto.setId(idOpciones[i]);
                    listObj.add(objClinicValueDto);

                    Campo1.setText(opciones[i]);
                    ListClinicDto.getClinicDTOs().get(j).setClinicvalueDTOs(listObj);
                }
                else {
                  //  posicion = 0 ;
                    //    List<ClinicvalueDTO> listObj = new ArrayList<ClinicvalueDTO>();
                   // List<MyType> myList = new ArrayList<MyType>();
                   //objClinicValueDto.setName("");
                   //objClinicValueDto.setId(idOpciones[i]);
                    //listObj.add(objClinicValueDto);
                    //ListClinicDto.getClinicDTOs().get(j).setClinicvalueDTOs(listObj);
                }
            }
        }


       // Campo1.setText(opciones[posicion]);

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



                                                                    if (!opciones[selectedIndex].isEmpty()){
                                                                        objClinicValueDto.setName(opciones[selectedIndex]);
                                                                    }
                                                                    else {
                                                                        objClinicValueDto.setName(" ");
                                                                    }

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

                if (idOpciones[i].equals(listDataClinic.get(x).getClinicvalueId())) {
                    activo[i] = true;
                    nombreCampoMultiple += opciones[i] + ",";
                    Campo1.setText(nombreCampoMultiple);

                    ClinicvalueDTO objClinicValueDto = new ClinicvalueDTO();
                    //List<MyType> myList = new ArrayList<MyType>();
                    if ( !opciones[i].isEmpty()){
                        objClinicValueDto.setId(idOpciones[i]);
                        objClinicValueDto.setName(opciones[i]);
                    }
                    else {
                        objClinicValueDto.setId(idOpciones[i]);
                        objClinicValueDto.setName(" ");
                    }

                    //objClinicValueDto.setId(idOpciones[i]);
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
            String apartment,
            String material,
            String electric,
            String water,
            String drain,
            String familynumber,
            String childrennumber,
            String adultnumber,
            String infantnumber,
            String pregnantnumber
    ) {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
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
                + Utilidades.CAMPO_pregnatNumber + ")" +
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
                + apartment + "','"
                + material + "','"
                + electric + "','"
                + water + "','"
                + drain + "','"
                + familynumber + "','"
                + childrennumber + "','"
                + adultnumber + "','"
                + infantnumber + "','"
                + pregnantnumber + "')";
        db.execSQL(insert);
        db.close();
    }


    public boolean Validacion() {
        boolean estado = true;

        String apartament = txtApartament.getText().toString();
        if (apartament.equals("")) {
            Toast.makeText(getActivity(), "Escribir Nombre !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            // txtApartament.requestFocus();
            estado = false;
        }

        String material = txtMaterial.getText().toString();
        if (material.equals("")) {
            Toast.makeText(getActivity(), "Escribir Apellido !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            //  txtMaterial.requestFocus();
            estado = false;
        }


        String electric = txtElectric.getText().toString();
        if (electric.equals("")) {
            Toast.makeText(getActivity(), "Escribir Sexo !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            //  txtSexo.requestFocus();
            estado = false;
        }


        String numberFamily = txtFamilyNumber.getText().toString();
        if (numberFamily.equals("")) {
            Toast.makeText(getActivity(), "Seleccionar tipo documento !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            // txtTipoDocumento.requestFocus();
            estado = false;
        }


        String children = txtChildrenNumber.getText().toString();
        if (children.equals("")) {
            Toast.makeText(getActivity(), "Escribir numero documento !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            //txtDni.requestFocus();
            estado = false;
        }

        String adultnumber = txtAdultNumber.getText().toString();
        if (adultnumber.equals("")) {
            Toast.makeText(getActivity(), "Seleccionar campaña !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            //  txtCampania.requestFocus();
            estado = false;
        }


        String infantnumber = txtInfantNumber.getText().toString();
        if (infantnumber.equals("")) {
            Toast.makeText(getActivity(), "Seleccionar pais !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            //   txtPais.requestFocus();
            estado = false;
        }


        String inftactnumber = txtInfantNumber.getText().toString();
        if (inftactnumber.equals("")) {
            Toast.makeText(getActivity(), "Seleccionar estado civil !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            //  txtEstadoCivil.requestFocus();
            estado = false;
        }

        return estado;
    }

    ArrayList<Post> obtenerListaParticipanteRegistroFinal() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Post dato = null;
        AssigneeDTO assigneeDTO = null;
        SocieconomicDTO socieconomicDTO = null;
        List<ClinicdataDTO> clinicdataDTOs = null;


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
//        if (edadEntera >= 18) {
//            assigneeDTO.setSex(null);
//        }
//
//        Call<Void> call1 = mAPIService.savePost(to,post);
//
//        call1.enqueue(new Callback<Void>() {
//
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//
//                if (response.isSuccessful()) {
//                    String location = response.headers().value(3);
//                    String[] parts = location.split("/participant/");
//                    /// obtener el id cliente en la posicion 1
//                    //  Toast.makeText(Inicio.this, parts[1], Toast.LENGTH_LONG).show();
//                    registradDatosSql(name, lastname, parts[1]);
//
//                    //   elemiarGeneralParticipante(docnumber);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
//                Toast.makeText(getActivity(), "Ocurrio un error al registrar participante", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private Participante consulterListaDatos(String idParticipante) {

        conn = new ConexionSQLiteHelper(getActivity().getApplicationContext(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
        SQLiteDatabase db = conn.getReadableDatabase();
        Participante dato = new Participante();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GENERALES + " where " +
                Utilidades.CAMPO_idParticipante + " = '" + idParticipante +"'" , null);
        while (cursor.moveToNext()) {

            dato = new Participante();
        //    dato.setId(cursor.getInt(0));
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
        //     listParticipante.add(dato);
        return dato;
    }

    private void registradDatosSql(String nombre, String apellido, String idParticipante) {

        mAPIService.readGroupData(idParticipante).enqueue(new Callback<List<Readgroup>>() {
            @Override
            public void onResponse(Call<List<Readgroup>> call, Response<List<Readgroup>> response) {
                if (response.isSuccessful()) {
                    // showResponse(response.body().toString());
                    //String dato = response.body().getToken();
                    Log.i(TAG, "get submitted to API." + response.body().toString());

                    ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
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
                            + nombre + "','"
                            + apellido + "')";
                    db.execSQL(insert);
                    db.close();

                    ConexionSQLiteHelper conn2 = new ConexionSQLiteHelper(getActivity(), Utilidades.CAMPO_baseDeDatos, null,  Utilidades.CAMPO_version);
                    SQLiteDatabase db2 = conn2.getWritableDatabase();

                    Readgroup rg = new Readgroup();
                    rg = response.body().get(0);
                    //   rg.getReadDTOs()
                    for (int i = 0; i < rg.getReadDTOs().size(); i++) {

                        // Nombre Pecho derecho izquierdo etc
                        String nombre = rg.getReadDTOs().get(i).getName();
                        for (int j = 0; j < rg.getReadDTOs().get(i).getReadvalueDTOs().size(); j++) {

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
                                    + idLectura + "','"
                                    + nombre + " " + subNombre + "','"
                                    + "" + "','"
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