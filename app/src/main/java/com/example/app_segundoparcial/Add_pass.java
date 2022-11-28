package com.example.app_segundoparcial;

import static com.example.app_segundoparcial.Formulario_registro.archivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_segundoparcial.json.MyData;
import com.example.app_segundoparcial.json.MyInfo;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Add_pass extends AppCompatActivity {
    public static String TAG = "mensajito";
    private List<MyData> lista;
    Button regiscontra;
    private EditText newpass, newred;
    //private TextView contrasena, red2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass);

        regiscontra = findViewById(R.id.buttonAdd);
        newred = findViewById(R.id.new_snetwork);
        newpass = findViewById(R.id.new_pass1);
        Intent intent = getIntent();
        Object object = null;
        MyInfo info = null;
        List<MyInfo> list =new ArrayList<MyInfo>();
        regiscontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyData myData = null;
                Object object = null;
                MyInfo info = null;
                object = intent.getExtras().get("MyInfo");
                info = (MyInfo) object;
                lista = info.getPswds();
                myData = new MyData();
                myData.setPassword(String.valueOf(newpass.getText()));
                myData.setRed(String.valueOf(newred.getText()));
                lista.add(myData);
                info.setPswds(lista);
                List2Json(info,list);


                Intent intent = new Intent(Add_pass.this, Bienvenido.class);
                intent.putExtra("MyInfo", info);
                startActivity(intent);
            }
        });

    }
    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        list.add(info);
        json =gson.toJson(list, ArrayList.class);
        if (json == null){
            Log.d(TAG, "Error json");
        }
        else {
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
    }


    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Hola");
            return true;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private File getFile(){
        return new File(getDataDir(), archivo);
    }

}