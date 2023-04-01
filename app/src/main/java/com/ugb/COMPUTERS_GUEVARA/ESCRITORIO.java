package com.ugb.COMPUTERS_GUEVARA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ESCRITORIO extends AppCompatActivity {
    Bundle parametros = new Bundle();

    DbEscritorio db_escritorio;
    ListView lts;
    Cursor cescritorio;
    FloatingActionButton btn;
    final ArrayList<String> alcompescritorio = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escritorio);
        obtenerDatosescritorio();
        btn = findViewById(R.id.btnescritorio);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo1");
                abrirAgregarescritorio(parametros);
            }
        });
    }
    public void abrirAgregarescritorio(Bundle parametros) {
        Intent iAgregarescritorio = new Intent(ESCRITORIO.this, MainActivityescritorio.class);
        iAgregarescritorio.putExtras(parametros);
        startActivity(iAgregarescritorio);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        cescritorio.moveToPosition(info.position);
        menu.setHeaderTitle(cescritorio.getString(2)); //2=> Codigo de escritorio...
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try{
            switch (item.getItemId()){
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo1");
                    abrirAgregarescritorio(parametros);

                    return true;
                case R.id.mnxModificar:
                    String escritorio[] = {
                            cescritorio.getString(0), //id
                            cescritorio.getString(1), //codigo
                            cescritorio.getString(2), //descripcion
                            cescritorio.getString(3), //marca
                            cescritorio.getString(4), //presentacion
                            cescritorio.getString(5), //precio

                    };
                    parametros.putString("accion", "modificar2");
                    parametros.putStringArray("COMPUTADORAS DE ESCRITORIO", escritorio);
                    abrirAgregarescritorio(parametros);
                    return true;
                case R.id.mnxEliminar:
                    eliminarDatosescrtorio();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }catch (Exception e){
            return super.onContextItemSelected(item);
        }
    }
    void eliminarDatosescrtorio(){
        try{
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(ESCRITORIO.this);
            confirmacion.setTitle("Esta seguro de eliminar a: ");
            confirmacion.setMessage(cescritorio.getString(1));
            confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db_escritorio.administrar_escritorio(cescritorio.getString(0), "","", "", "", "","eliminar3" );

                    obtenerDatosescritorio();
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
    public void obtenerDatosescritorio(){
        try {
            alcompescritorio.clear();

            db_escritorio = new DbEscritorio(ESCRITORIO.this, "", null, 2);
            cescritorio = db_escritorio.consultar_escritorio();
            if(cescritorio.moveToFirst()){
                lts = findViewById(R.id.ltsescritorio);
                final ArrayAdapter<String> adescritorio = new ArrayAdapter<String>(ESCRITORIO.this,
                        android.R.layout.simple_expandable_list_item_1, alcompescritorio);
                lts.setAdapter(adescritorio);
                do{
                    alcompescritorio.add(cescritorio.getString(2));
                }while(cescritorio.moveToNext());
                adescritorio.notifyDataSetChanged();
                registerForContextMenu(lts);
            }else{
                Toast.makeText(this, "No hay datos que mostrar", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Error al obtener : "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}