package com.example.yanadenv.conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yanadenv.principal.utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // db.execSQL(Utilidades.CREAR_TABLA_USUARIO);
        //db.execSQL(Utilidades.CREAR_TABLA_CLINICOS);
        db.execSQL(Utilidades. CREAR_TABLA_GENERALES);
        db.execSQL(Utilidades. CREAR_TABLA_PARTICIPANTE_AUDIOS);
        db.execSQL(Utilidades. CREAR_TABLA_READGROUP);
        db.execSQL(Utilidades. CREAR_TABLA_CLINICOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
      //  db.execSQL("DROP TABLE IF EXISTS datos");
       // db.execSQL("DROP TABLE IF EXISTS DATOS_GENERALES");
        //db.execSQL("DROP TABLE IF EXISTS DATOS_PARTICIPANTES_AUDIOS");
        //db.execSQL("DROP TABLE IF EXISTS DATOS_PARTICIPANTES_READGROUP");
        //db.execSQL("DROP TABLE IF EXISTS DATOS_PARTICIPANTES_CLINICOS");

        onCreate(db);
    }
}
