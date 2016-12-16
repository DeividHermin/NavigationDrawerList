package com.example.deivi.navigationdrawerlist;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.toggle;


public class MainActivity extends AppCompatActivity {

    DrawerLayout dw;
    ListView lista;
    Toolbar tb;
    ActionBarDrawerToggle toggle;
    BDContactos bd;
    static Adaptador a;
    static ArrayList<Elemento> arrayList = new ArrayList();

    EditText et;
    Button btAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dw = (DrawerLayout)findViewById(R.id.drawer);
        lista = (ListView)findViewById(R.id.lista);
        tb = (Toolbar)findViewById(R.id.toolbar);
        et = (EditText) findViewById(R.id.editText);
        btAdd = (Button)findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContacto();
            }
        });

        if(tb!=null){
            tb.setTitle("Contactos");
            setSupportActionBar(tb);
        }
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle=new ActionBarDrawerToggle(this, dw, tb, R.string.dw_abierto, R.string.dw_cerrado);
        dw.addDrawerListener(toggle);

        bd = new BDContactos(this);

        actualizaLista();

        a = new Adaptador(this,arrayList, bd);
        a.notifyDataSetChanged();
        lista.setAdapter(a);
    }

    @Override
    protected void onResume(){
        toggle.syncState();
        super.onResume();
    }

    public void actualizaLista(){
        arrayList.clear();
        if(bd.listado().size()>0)
            for(int i=0; i<bd.listado().size(); i++){
                arrayList.add(bd.listado().get(i));
            }
    }

    public void addContacto(){
        String str = et.getText().toString();
        if(str.equals(""))
            str="Cara anchoa";
        bd.insertarContacto(new Elemento(bd.returnId(), str));
        Toast.makeText(getApplicationContext(), "Añadido", Toast.LENGTH_SHORT).show();
        actualizaLista();
    }
}
