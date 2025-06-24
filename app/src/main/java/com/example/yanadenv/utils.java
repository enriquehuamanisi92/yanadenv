package com.example.yanadenv;

import android.content.Context;
import android.content.SharedPreferences;

public class utils {





    static String getToken(Context context) {

        SharedPreferences prefs = context.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);

        String token = prefs.getString("tokenUnico", ""); // prefs.getString("nombre del campo" , "valor por defecto")

        return token;

    }
}
