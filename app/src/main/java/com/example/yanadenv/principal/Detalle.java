package com.example.yanadenv.principal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.yanadenv.R;
import com.example.yanadenv.entidades.Participante;

public class Detalle extends AppCompatActivity {
    private Participante Item ;
    private TextView itemNombre , itemApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Item = (Participante) getIntent().getSerializableExtra("objetoParticipante");
        itemNombre  =(TextView) findViewById(R.id.dNombre);
       // itemApellido  =(TextView) findViewById(R.id.dApellido);

        itemNombre.setText(Item.getName() + " " + Item.getLastname());
       // itemApellido.setText(Item.getLastname());
    }
}