package com.ugb.COMPUTERS_GUEVARA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LAPTOPS extends AppCompatActivity {
    SearchView searchView;
    Bundle parametros = new Bundle();
    DbLaptops db_laptops;
    ListView lts;
    Cursor claptops;
    FloatingActionButton btn;
    final ArrayList<String> alcompulaptops = new ArrayList<String>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptops);
        obtenerDatoslaptops();
        btn = findViewById(R.id.btnlaptops);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                abrirAgregarlaptops(parametros);
            }
        });
    }
    public void abrirAgregarlaptops(Bundle parametros) {
        Intent iAgregarlaptops = new Intent(LAPTOPS.this, MainActivitylaptops.class);
        iAgregarlaptops.putExtras(parametros);
        startActivity(iAgregarlaptops);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        claptops.moveToPosition(info.position);
        menu.setHeaderTitle(claptops.getString(2)); //2=> Codigo de laptops...


    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try{
            switch (item.getItemId()){
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo");
                    abrirAgregarlaptops(parametros);

                    return true;
                case R.id.mnxModificar:
                    String laptops[] = {
                            claptops.getString(0), //idmujer
                            claptops.getString(1), //codigo
                            claptops.getString(2), //descripcion
                            claptops.getString(3), //marca
                            claptops.getString(4), //presentacion
                            claptops.getString(5), //precio

                    };
                    parametros.putString("accion", "modificar");
                    parametros.putStringArray("Laptops", laptops);
                    abrirAgregarlaptops(parametros);
                    return true;
                case R.id.mnxEliminar:
                    eliminarDatoslaptops();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }catch (Exception e){
            return super.onContextItemSelected(item);
        }
    }
    void eliminarDatoslaptops(){
        try{
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(LAPTOPS.this);
            confirmacion.setTitle("Esta seguro de eliminar a: ");
            confirmacion.setMessage(claptops.getString(2));
            confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db_laptops.administrar_laptops(claptops.getString(0), "","", "", "", "","eliminar" );

                    obtenerDatoslaptops();
                    dialogInterface.dismiss();
                }
            });
            confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmacion.create().show();
        }catch (Exception e){
            Toast.makeText(this, "Error al eliminar: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void obtenerDatoslaptops(){
        try {
            alcompulaptops.clear();
            db_laptops = new DbLaptops(LAPTOPS.this, "", null, 2);
            claptops = db_laptops.consultar_laptops();
            if(claptops.moveToFirst()){
                lts = findViewById(R.id.ltslaptops);
                final ArrayAdapter<String> adlaptops = new ArrayAdapter<String>(LAPTOPS.this,
                        android.R.layout.simple_expandable_list_item_1, alcompulaptops);
                lts.setAdapter(adlaptops);
                do{
                    alcompulaptops.add(claptops.getString(2));

                }while(claptops.moveToNext());
                adlaptops.notifyDataSetChanged();
                registerForContextMenu(lts);
            }else{
                Toast.makeText(this, "No hay datos que mostrar", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Error al obtener datos: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}