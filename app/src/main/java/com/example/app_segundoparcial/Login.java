package com.example.app_segundoparcial;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText email = null;
    private EditText pass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button Regis = findViewById(R.id.idRegistrarse);
        Button olvidoPass = findViewById(R.id.olvidoID);
        Button login = findViewById(R.id.okID);
        email = findViewById(R.id.CorreoID);
        pass = findViewById(R.id.passID);

        Regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Formulario_registro.class);
                startActivity(intent);
            }
        });
        olvidoPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, OlvidoPass.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar2();
            }
        });


    }
    public void agregar2(){
        if(ValidarCampos2()){
            Toast.makeText(getApplicationContext(), "Los datos son correctos", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validarCorreoLogin(){
        String correoUsu = email.getText().toString();
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correoUsu);
        if(!mather.find()){
            Toast.makeText(getApplicationContext(), "El correo es incorrecto", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "El correo ta bien", Toast.LENGTH_LONG).show();
        }
        return mather.find();
    }

    public boolean ValidarCampos2(){
        boolean campos2 = true;
        String correoUsu = email.getText().toString();
        String password = pass.getText().toString();
        if(password.isEmpty()){
            pass.setError("Ingresa tu contraseña");
            campos2= false;
        }
        if(correoUsu.isEmpty()){
            email.setError("Ingresa tu correo");
            campos2= false;
        } else{
            validarCorreoLogin();
        }
        return campos2;
    }


}