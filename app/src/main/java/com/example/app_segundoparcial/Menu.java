package com.example.app_segundoparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_segundoparcial.MyAdapter.MyAdapter;
import com.example.app_segundoparcial.json.MyData;
import com.example.app_segundoparcial.json.MyInfo;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private TextView usuario;
    private ListView listView;
    private List<MyData> list;
    public static String TAG = "ola";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        String aux = null;
        MyInfo info = null;
        Object object = null;
        MyData myData = null;

        usuario = findViewById(R.id.textNombre);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView2);
        list = new ArrayList<MyData>();
        if( intent != null)
        {
            aux = intent.getStringExtra("Usuario" );
            if( aux != null && aux.length()> 0 )
            {
                usuario.setText(aux);
            }
            if( intent.getExtras() != null ) {
                object = intent.getExtras().get("MyInfo");
                if (object != null) {
                    if (object instanceof MyInfo) {
                        info = (MyInfo) object;
                        usuario.setText(info.getUsuario());
                        list = info.getPswds();
                    }
                }
            }
        }

        MyAdapter myAdapter = new MyAdapter( list , getBaseContext() );
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toast( i );
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,  menu);
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        MyInfo info = null;
        Object object = null;
        Intent intent = getIntent();
        object = intent.getExtras().get("MyInfo");
        info = (MyInfo) object;
        switch (item.getItemId() ) {
            case R.id.add_id:
                Intent olvideContra = new Intent(Menu.this, Add_pass.class);
                olvideContra.putExtra("MyInfo", info);
                startActivity(olvideContra);
                break;
            case R.id.edit_id:
                Intent editaContra = new Intent(Menu.this, Edit_pass.class);
                editaContra.putExtra("MyInfo", info);
                startActivity(editaContra);
                break;
            case R.id.del_id:
                Intent elimContra = new Intent(Menu.this, Delete_pass.class);
                elimContra.putExtra("MyInfo", info);
                startActivity(elimContra);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
    private void toast( int i )
    {
        Toast.makeText(getBaseContext(), list.get(i).getPassword(), Toast.LENGTH_SHORT).show();
    }


}