package com.example.app_segundoparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.app_segundoparcial.json.MyInfo;

public class Bienvenido extends AppCompatActivity {

    String aux;
    MyInfo myInfo= null;
    TextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object object= null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        nombre= findViewById(R.id.idUsuario);
        Intent intent = getIntent();
        if(intent != null){
            if(intent.getExtras() !=null){
                object = intent.getExtras().get("Objeto");
                if (object != null) {
                    if (object instanceof MyInfo) {
                        myInfo = (MyInfo) object;
                        nombre.setText(myInfo.getUsuario());
                    }
                }
            }
        }
    }
}