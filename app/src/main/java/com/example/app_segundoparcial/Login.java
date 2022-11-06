package com.example.app_segundoparcial;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_segundoparcial.json.MyInfo;
import com.example.mydigest.Digest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText correoL = null;
    private EditText contrasenaL = null;
    public static List<MyInfo> list;
    public static String json = null;
    private String mailL;
    private String passL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button Regis = findViewById(R.id.idRegistrarse);
        Button olvidoPass = findViewById(R.id.olvidoID);
        Button login = findViewById(R.id.okID);
        Button next = findViewById(R.id.idNext);
        correoL = findViewById(R.id.CorreoID);
        contrasenaL = findViewById(R.id.passID);
        readFile();
        json2List(json);
        if(json == null || json.length() == 0){
            olvidoPass.setEnabled(false);
            login.setEnabled(false);
            contrasenaL.setEnabled(false);
        }
        else
        {
            olvidoPass.setEnabled(true);
            login.setEnabled(false);
            contrasenaL.setEnabled(false);
            olvidoPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Login.this, OlvidoPass.class);
                    startActivity(intent);
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ValidarCampoEmail()){
                        //String email = String.valueOf(email2.getText());
                        contrasenaL.setEnabled(true);
                        login.setEnabled(true);

                        login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(ValidarPass()){

                                    String Password = Digest.bytesToHex(Digest.createSha1(passL + mailL));
                                    for(MyInfo myInfo : list){
                                        if(myInfo.getCorreo().equals(mailL) && myInfo.getPass().equals(Password)){
                                            Intent intent = new Intent(Login.this, Bienvenido.class);
                                            intent.putExtra("Objeto", myInfo);
                                            startActivity(intent);
                                        }else{
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
        Regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Formulario_registro.class);
                startActivity(intent);
            }
        });
    }


    ///////////////////////////////////////7


    public boolean readFile(){
        if(!isFileExits()){
            return  false;
        }
        File file = getFile();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            json=new String(bytes);
            Log.d(TAG,json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private File getFile( )
    {
        return new File(getDataDir() , Formulario_registro.archivo );
    }

    public boolean isFileExits( )
    {
        File file = getFile( );
        return file.isFile() && file.exists();
    }

    public void json2List( String json )
    {
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {

            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }

    // VALIDACIONES

    public boolean validarCorreoLogin(){
        String correoUsu = correoL.getText().toString();
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correoUsu);
        if(!mather.find()){
            Toast.makeText(getApplicationContext(), "El correo es incorrecto", Toast.LENGTH_LONG).show();
        }
        return mather.find();
    }

    public boolean ValidarCampoEmail(){
        boolean campos2 = true;
        mailL = correoL.getText().toString();
        if(mailL.isEmpty()){
            correoL.setError("Ingresa tu correo");
            campos2= false;
        } else{
            validarCorreoLogin();
        }
        return campos2;
    }

    public boolean ValidarPass(){
        boolean campos2 = true;
        passL = contrasenaL.getText().toString();
        if(passL.isEmpty()){
            contrasenaL.setError("Ingresa tu contraseña");
            campos2= false;
        }
        return campos2;
    }


}