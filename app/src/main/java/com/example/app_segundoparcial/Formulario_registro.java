package com.example.app_segundoparcial;
import static android.widget.Toast.makeText;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formulario_registro extends AppCompatActivity implements View.OnClickListener{
    //
    private static final String TAG = "MainActivity";
    //public static final String archivo = "Archivo1.json";
    public static final String archivo = "archivo.json";
    String json = null;
    public static List<MyInfo> list =new ArrayList<MyInfo>();

    private EditText usuario = null;
    private EditText pass = null;
    private EditText passConfirm = null;
    private EditText correo = null;
    private RadioButton rFem = null;
    private RadioButton rMas = null;
    private CheckBox alum = null;
    private CheckBox docen = null;
    private CheckBox indep = null;
    private TextView tipos = null;
    private EditText tel = null;
    private Switch Notificar = null;
        // validar genero y notificaciones
    public static boolean gen= true;
    public static boolean Actnotificar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_registro);
        // Componentes del formulario de registro
        usuario = findViewById(R.id.idNombre);
        pass = findViewById(R.id.idPass);
        passConfirm = findViewById(R.id.idPassConfirm);
        correo = findViewById(R.id.idEmail);
        rFem = findViewById(R.id.idFemenino);
        rMas = findViewById(R.id.idMasculino);
        tipos = findViewById(R.id.tiposUsu);
        alum = findViewById(R.id.idAlum);
        indep = findViewById(R.id.idIndep);
        docen = findViewById(R.id.idDocen);
        tel = findViewById(R.id.idTel);
        Notificar = findViewById(R.id.idNotifi);
        String[] tiposUsu = new String[3];
        Button toLogin = findViewById(R.id.toLogin);
        Button enviarInfo = findViewById(R.id.btnRegistrarme);
        readFile();
        json2List(json);

                // Calendario
        EditText date = findViewById(R.id.idFecha);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia= calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Formulario_registro.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mes, int dia) {
                        mes = mes +1;
                        String date1 = dia + "/" + mes + "/" + year;
                        date.setText(date1);
                    }
                },year,mes,dia);
                datePickerDialog.show();
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Formulario_registro.this, Login.class);
                startActivity(intent);
            }
        });

        enviarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()){
                    // Validar checkboxs "Tipo de usuario"
                    if(alum.isChecked()){
                        tiposUsu[0]= "TIPO_ALUMNO";
                    }
                    if(docen.isChecked()){
                        tiposUsu[1] = "TIPO_DOCENTE";
                    }
                    if(indep.isChecked()){
                        tiposUsu[2] = "TIPO_INDEP";
                    }
                    // Validar campo "Sexo"
                    if(rFem.isChecked()){
                        gen = true;
                    }
                    if(rMas.isChecked()){
                        gen=false;
                    }
                    // Validar notificaciones
                    if(Notificar.isChecked()){
                        Actnotificar = true;
                    }

                    // Validar informacion ingresada en el formulario de registro
                    MyInfo info= new MyInfo();
                    info.setNotificaciones(Actnotificar);
                    info.setUsuario(String.valueOf(usuario.getText()));
                    info.setCorreo(String.valueOf(correo.getText()));
                    info.setPass(Digest.bytesToHex(Digest.createSha1(String.valueOf(pass.getText()) + correo.getText() )));
                    info.setPassConfirm(Digest.bytesToHex(Digest.createSha1(String.valueOf(passConfirm.getText()) + correo.getText() )));
                    info.setFecha(String.valueOf(date.getText()));
                    info.setTel(String.valueOf(tel.getText()));
                    info.setTipoUsu(tiposUsu);
                    info.setSexo(gen);
                    List2Json(info,list);
                    Intent intent = new Intent(Formulario_registro.this, CuentaCreada.class);
                    startActivity(intent);

                }
            }
        });

    }
    // METODOS
    private boolean writeFile(String text)
    {
        File file = null;
        FileOutputStream fileOutputStream = null;
        try
        {
            file = getFile();
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Archivo creado");
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
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
        return new File(getDataDir() , archivo );
    }

    public boolean isFileExits( )
    {
        File file = getFile( );
        return file.isFile() && file.exists();
    }

    public void Objet2Json(MyInfo info){
        Gson gson =null;
        String json= null;
        String mensaje = null;
        gson =new Gson();
        json = gson.toJson(info);
        if (json != null) {
            Log.d(TAG, json);
            mensaje = "object2Json OK";
        } else {
            mensaje = "Error object2Json";
        }
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson = null;
        String json= null;
        gson =new Gson();
        list.add(info);
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Los datos son correctos", Toast.LENGTH_LONG).show();
    }
    public void json2List( String json)
    {
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0) {
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            return;
        }
    }


    ////////////////////// VALIDACIONES  ///////////////////////////

    public boolean validarCorreo(){
        String email = correo.getText().toString();
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        if(!mather.find()){
            Toast.makeText(getApplicationContext(), "El correo es incorrecto", Toast.LENGTH_LONG).show();
        }
        return mather.find();
    }
    public boolean validarTel(){
        String numtel = tel.getText().toString();
        boolean correcto = false;
        for(int i=0; i<numtel.length(); i++){
            if(numtel.length()==12){
                correcto = true;
            }
            else{
                tel.setError("Tu número de celular debe de tener 10 cifras");
                correcto = false;
            }
        }
        return correcto;
    }

    public boolean validarCampos(){
        boolean campos=true;
        String usu = usuario.getText().toString();
        String pswd = pass.getText().toString();
        String pswdC = passConfirm.getText().toString();
        String email = correo.getText().toString();
        String numtel = tel.getText().toString();
        if(numtel.isEmpty()){
            tel.setError("Este campo es obligatorio.");
            campos = false;
        }else{
            validarTel();
        }
        if(usu.isEmpty()){
            usuario.setError("Este campo es obligatorio.");
            campos= false;
        }
        if(pswd.isEmpty()){
            pass.setError("Este campo es obligatorio.");
            campos= false;
        }
        if(pswdC.isEmpty()){
            passConfirm.setError("Este campo es obligatorio.");
            campos= false;
        }
        if(!pswd.equals(pswdC)){  //valida si las contraseñas son iguales
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }
        if(email.isEmpty()){
            correo.setError("Este campo es obligatorio.");
            campos= false;
        } else{
            validarCorreo();
        }
        if(!docen.isChecked() && !indep.isChecked() && !alum.isChecked()){
            tipos.setError("Debes seleccionar un tipo de usuario.");
            campos= false;
        }
        else{
            if(docen.isChecked() && indep.isChecked() && alum.isChecked()){
                Toast.makeText(getApplicationContext(), "Debes seleccionar solo un tipo de usuario.", Toast.LENGTH_LONG).show();
                tipos.setError("");
                campos= false;
            }
            else{
                if(docen.isChecked() && indep.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Debes seleccionar solo un tipo de usuario.", Toast.LENGTH_LONG).show();
                    tipos.setError("");
                    campos = false;
                }
                else{
                    if(alum.isChecked() && indep.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Debes seleccionar solo un tipo de usuario.", Toast.LENGTH_LONG).show();
                        tipos.setError("");
                        campos = false;
                    }
                    else{
                        if(docen.isChecked() && alum.isChecked()) {
                            Toast.makeText(getApplicationContext(), "Debes seleccionar solo un tipo de usuario.", Toast.LENGTH_LONG).show();
                            tipos.setError("");
                            campos = false;
                        }
                    }
                }
            }
        }
        return campos;
    }

    @Override
    public void onClick(View view) {

    }
}