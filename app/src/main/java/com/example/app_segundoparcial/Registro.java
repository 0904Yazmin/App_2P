package com.example.app_segundoparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import java.util.Calendar;

public class Registro extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //public static final String archivo = "archivo.json";
    private Button buttonToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //List<MyInfo> list =new ArrayList<MyInfo>();
        buttonToLogin = findViewById(R.id.buttonLogin);
        //Button button4 = findViewById(R.id.continuarlogid);
        Button button5 = findViewById(R.id.idRegistrarse); //
        EditText usuario = findViewById(R.id.idNombre);
        EditText pswd = findViewById(R.id.idPass);
        EditText mail = findViewById(R.id.idEmail);
        RadioButton rFem = findViewById(R.id.idFemenino);
        RadioButton rMas = findViewById(R.id.idMasculino);
        EditText date = findViewById(R.id.idFecha);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia= calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Registro.this, new DatePickerDialog.OnDateSetListener() {
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

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
            }
        });


    }
}