package org.joseperez.appcontactos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class ModificarContacto extends AppCompatActivity {

    DbHelper db;
    EditText nombre_input;
    EditText apellido_input;
    EditText edad_input;
    EditText telefono_input;
    EditText email_input;
    TextView tv;

    int idglobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_modificar_contacto);
            nombre_input = (EditText) findViewById(R.id.nombre_input);
            apellido_input = (EditText) findViewById(R.id.apellido_input);
            edad_input = (EditText) findViewById(R.id.edad_input);
            telefono_input = (EditText) findViewById(R.id.telefono_input);
            email_input = (EditText) findViewById(R.id.email_input);
            db = new DbHelper(this, null, null, 1);
            Contactos contactos = new Contactos();
            Intent i = getIntent(); // gets the previously created intent
            String stringid = i.getStringExtra("id_persona");
            int id = Integer.parseInt(stringid);
            Cursor c = db.personabyid(id);

            //Vuelve a rellenar los inputs con los valores del cursor
            nombre_input.setText(c.getString(c.getColumnIndexOrThrow("nombre")));
            apellido_input.setText(c.getString(c.getColumnIndexOrThrow("apellido")));
            edad_input.setText(c.getString(c.getColumnIndexOrThrow("edad")));
            telefono_input.setText(c.getString(c.getColumnIndexOrThrow("telefono")));
            email_input.setText(c.getString(c.getColumnIndexOrThrow("email")));
            idglobal = c.getInt(c.getColumnIndexOrThrow("_id"));
    }

    public void modificar_clicked(View view){
                Contactos contactos = new Contactos(nombre_input.getText().toString(), apellido_input.getText().toString(), Integer.parseInt(edad_input.getText().toString()), telefono_input.getText().toString(), email_input.getText().toString());
                contactos.set_id(idglobal);
                db.updatepersona(contactos);
                confirmacion();
                limpiarcampos();
                finish(); //Termina la actividad y vuelve al menu principal
    }

    public void confirmacion(){

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Se ha modificado exitosamente!");
        dlgAlert.setTitle("Agregar Persona");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    //Limpia los valores entrados para efectos de estetica
    public void limpiarcampos(){

        nombre_input.setText("");
        apellido_input.setText("");
        edad_input.setText("");
        telefono_input.setText("");
        email_input.setText("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.modificar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
