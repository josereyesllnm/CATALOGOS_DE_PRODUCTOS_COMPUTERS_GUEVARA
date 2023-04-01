package com.ugb.COMPUTERS_GUEVARA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityescritorio extends AppCompatActivity {
    DbEscritorio db_escritorio;
    String accion="nuevo1";
    String id="";
    Button btn;
    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityescritorio);
        btn = findViewById(R.id.btnGuardar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {guardar_escritorio();
            }
        });


        mostrar_datos_escritorio();
    }
    void mostrar_datos_escritorio(){
        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");
        if(accion.equals("modificar2")){
            String compuescritorio[] = parametros.getStringArray("Escritorio");
            id = compuescritorio[0];

            temp = findViewById(R.id.txtcodigo);
            temp.setText(compuescritorio[1]);

            temp = findViewById(R.id.txtdescripcion);
            temp.setText(compuescritorio[2]);

            temp = findViewById(R.id.txtmarca);
            temp.setText(compuescritorio[3]);

            temp = findViewById(R.id.txtpresentacion);
            temp.setText(compuescritorio[4]);

            temp = findViewById(R.id.txtprecio);
            temp.setText(compuescritorio[5]);
        }

    }
    void guardar_escritorio(){
        try {
            temp = (TextView) findViewById(R.id.txtcodigo);
            String codigo = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtdescripcion);
            String descripcion = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtmarca);
            String marca = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtpresentacion);
            String presentacion = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtprecio);
            String precio = temp.getText().toString();

            db_escritorio = new DbEscritorio(MainActivityescritorio.this, "",null,1);
            String result = db_escritorio.administrar_escritorio(id, codigo, descripcion, marca, presentacion,precio, accion);
            String msg = result;
            if( result.equals("ok") ){
                msg = "Registro guardado con exito";
                regresarListaescritorio();
            }
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error en guardar computadora: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    void regresarListaescritorio(){
        Intent ilistaescritorio = new Intent(MainActivityescritorio.this, ESCRITORIO.class);
        startActivity(ilistaescritorio);

    }
}