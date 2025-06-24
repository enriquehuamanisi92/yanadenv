package com.example.yanadenv.principal.utilidades;

public class Utilidades {

    //Declaracion de tablas
    public static final String TABLA_PARTICIPANTE = "PARTICIPANTE";
    public static final String TABLA_GENERALES = "DATOS_GENERALES";

    public static final String TABLA_PARTICIPANTES_AUDIOS = "DATOS_PARTICIPANTES_AUDIOS";

    public static final String TABLA_PARTICIPANTES_READGROUP = "DATOS_PARTICIPANTES_READGROUP";

    public static final String TABLA_PARTICIPANTES_DATOSCLINICOS = "DATOS_PARTICIPANTES_CLINICOS";

    public static final String TABLA_DATO = "datos";

    public static final String NOMBRE_PROYECTO = "qhawawa";

    //////Campos Participante
    public static final String  CAMPO_ID = "id";
    public static final String  CAMPO_enabled = "enabled";
    public static final String  CAMPO_name = "name";
    public static final String  CAMPO_lastname = "lastname";
    public static final String  CAMPO_sex = "sex";
    public static final String  CAMPO_doctype= "doctype";
    public static final String  CAMPO_docnumber= "docnumber";
    public static final String  CAMPO_campainId= "campainId";
    public static final String  CAMPO_countryId= "countryId";
    public static final String  CAMPO_contactnumber= "contactnumber";
    public static final String  CAMPO_civilstatus= "civilstatus";
    public static final String  CAMPO_weight= "weight";
    public static final String  CAMPO_stature= "stature";
    public static final String  CAMPO_imc= "imc";
    public static final String  CAMPO_assigneeName= "assigneeName";
    ///Apoderado
    public static final String  CAMPO_nameApoderado= "nameApoderado";
    public static final String  CAMPO_lastnameApoderado= "lastnameApoderado";
    public static final String  CAMPO_sexApoderado= "sexApoderado";
    public static final String  CAMPO_doctypeApoderado= "doctypeApoderado";
    public static final String  CAMPO_docnumberApoderado= "docnumberApoderado";
    ///SocioEconomico
    public static final String  CAMPO_apartament= "apartament";
    public static final String  CAMPO_material= "material";
    public static final String  CAMPO_electric= "electric";
    public static final String  CAMPO_water= "water";
    public static final String  CAMPO_drain= "drain";
    public static final String  CAMPO_familyNumber= "familyNumber";
    public static final String  CAMPO_ChildrenNumber= "childrenNumber";
    public static final String  CAMPO_adultNumber= "adultNumber";
    public static final String  CAMPO_infantNumber= "infantNumber";
    public static final String  CAMPO_pregnatNumber= "pregnatNumber";

    //////Campos Participante audio
    public static final String  CAMPO_idParticipante= "idParticipante";
    public static final String  CAMPO_nombreParticipante= "nombre";
    public static final String  CAMPO_apellidoParticipante= "apellido";
    public static final String  CAMPO_usuario= "usuario";
    public static final String  CAMPO_contrasenia= "contrasenia";

    public static final String  CAMPO_audio1= "audio1";
    public static final String  CAMPO_audio2= "audio2";
    public static final String  CAMPO_audio3= "audio3";
    public static final String  CAMPO_audio4= "audio4";
    public static final String  CAMPO_edad= "edad";
    public static final String  CAMPO_gestante= "gestante";
    public static final String  CAMPO_educacion= "educacion";



    //////Campos Participante audio
    public static final String  CAMPO_idLectura= "idLectura";
    public static final String  CAMPO_subName= "subName";
    public static final String  CAMPO_ruta= "ruta";
    public static final String  CAMPO_flag= "flag";

    // Campos datos clinicos
    public static final String  CAMPO_dni = "dni";
    public static final String  CAMPO_participanteId = "idParticipante";
    public static final String  CAMPO_clinicId = "idClinic";
    public static final String  CAMPO_clinicValueId = "idClinicValue";
    public static final String  CAMPO_value = "value";
    public static final String  CAMPO_namesigla = "sigla";
    public static final String  CAMPO_idParticipanteServidor = "idParticipanteServidor";
    public static final String  CAMPO_baseDeDatos = "bd_datos5";
    // VERSION SQLITE
    public static final int  CAMPO_version = 4;

    public static final String CREAR_TABLA_GENERALES = "CREATE TABLE "+
            "" +TABLA_GENERALES+" ("
            +CAMPO_ID+" "+ "INTEGER PRIMARY KEY, "
            +CAMPO_name+" TEXT,"
            +CAMPO_lastname+" TEXT,"
            +CAMPO_sex+" TEXT, "
            +CAMPO_doctype+" TEXT, "
            +CAMPO_docnumber+" TEXT,"
            +CAMPO_campainId+" TEXT,"
            +CAMPO_countryId+" TEXT, "
            +CAMPO_contactnumber+" TEXT, "
            +CAMPO_civilstatus+" TEXT, "
            +CAMPO_weight+" INTEGER, "
            +CAMPO_stature+" INTEGER, "
            +CAMPO_imc+" REAL, "
            +CAMPO_nameApoderado+" TEXT, "
            +CAMPO_lastnameApoderado+" TEXT, "
            +CAMPO_sexApoderado+" TEXT, "
            +CAMPO_doctypeApoderado+" TEXT, "
            +CAMPO_docnumberApoderado+" TEXT, "
            +CAMPO_apartament+" TEXT, "
            +CAMPO_material+" TEXT, "
            +CAMPO_electric+" TEXT, "
            +CAMPO_water+" TEXT, "
            +CAMPO_drain+" TEXT, "
            +CAMPO_familyNumber+" TEXT, "
            +CAMPO_ChildrenNumber+" TEXT, "
            +CAMPO_adultNumber+" TEXT, "
            +CAMPO_infantNumber+" TEXT, "
            +CAMPO_pregnatNumber+" TEXT, "
            +CAMPO_edad+" TEXT, "
            +CAMPO_gestante+" TEXT, "
            +CAMPO_educacion+" TEXT, "
            +CAMPO_flag+" INTEGER, "
            +CAMPO_idParticipante+" TEXT, "
            +CAMPO_usuario+" TEXT, "
            +CAMPO_contrasenia+" TEXT, "
            +CAMPO_idParticipanteServidor+" TEXT)";

    public static final String CREAR_TABLA_PARTICIPANTE_AUDIOS = "CREATE TABLE "+
            "" +TABLA_PARTICIPANTES_AUDIOS+" ("
            +CAMPO_ID+" "+ "INTEGER PRIMARY KEY, "
            +CAMPO_idParticipante+" TEXT, "
            +CAMPO_nombreParticipante+" TEXT, "
            +CAMPO_apellidoParticipante+" TEXT, "
            +CAMPO_edad+" TEXT, "
            +CAMPO_usuario+" TEXT, "
            +CAMPO_contrasenia+" TEXT)";

    public static final String CREAR_TABLA_READGROUP = "CREATE TABLE "+
            "" +TABLA_PARTICIPANTES_READGROUP+" ("
            +CAMPO_ID+" "+ "INTEGER PRIMARY KEY, "
            +CAMPO_idParticipante+" TEXT, "
            +CAMPO_idLectura+" TEXT, "
            +CAMPO_name+" TEXT, "
            +CAMPO_ruta+" TEXT, "
            +CAMPO_flag+" INTEGER, "
            +CAMPO_dni+" TEXT, "
            +CAMPO_namesigla+" TEXT, "
            +CAMPO_idParticipanteServidor+" TEXT, "
            +CAMPO_usuario+" TEXT, "
            +CAMPO_contrasenia+" TEXT)";

    public static final String CREAR_TABLA_CLINICOS = "CREATE TABLE "+
            "" +TABLA_PARTICIPANTES_DATOSCLINICOS+" ("
            +CAMPO_ID+" "+ "INTEGER PRIMARY KEY, "
            +CAMPO_dni+" TEXT, "
            +CAMPO_participanteId+" TEXT, "
            +CAMPO_clinicId+" TEXT, "
            +CAMPO_clinicValueId+" TEXT, "
            +CAMPO_value+" TEXT)";
}
