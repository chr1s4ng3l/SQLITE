package google.tamayo.christopher.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etCodigo, etDescripcion, etPrecio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo=(EditText)findViewById(R.id.txtCodigo);
        etDescripcion=(EditText)findViewById(R.id.txtDescripcion);
        etPrecio=(EditText)findViewById(R.id.txtPrecio);
    }
    public void Registrar(View view){
        AdminSQLiteOpenHelper objAdmin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase BaseDeDatos = objAdmin.getWritableDatabase();

        String codigo = etCodigo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String precio = etPrecio.getText().toString();
        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio",precio);

            BaseDeDatos.insert("articulos", null, registro);
            BaseDeDatos.close();

            etPrecio.setText("");
            etDescripcion.setText("");
            etCodigo.setText("");

        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();

        }
    }

    public void Buscar(View view){

        AdminSQLiteOpenHelper objAdmin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase BaseDeDatos = objAdmin.getWritableDatabase();
        String codigo = etCodigo.getText().toString();

        if (!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select descripcion, precio from articulos where codigo ="+codigo, null);

            if (fila.moveToFirst()){
                etDescripcion.setText(fila.getString(0));
                etPrecio.setText(fila.getString(1));
                BaseDeDatos.close();

            }else{
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }

        }else{
            Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(View view){
        AdminSQLiteOpenHelper objAdmin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = objAdmin.getWritableDatabase();
        String codigo = etCodigo.getText().toString();

        if (!codigo.isEmpty()){

            int cantidad = BaseDeDatos.delete("articulos", "codigo="+codigo, null);

            BaseDeDatos.close();

            etPrecio.setText("");
            etDescripcion.setText("");
            etCodigo.setText("");

            if (cantidad==1){
                Toast.makeText(this, "Articulo eliminado", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();

            }

        }else{
            Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
    }

    public void Modificar(View view){
        AdminSQLiteOpenHelper objAdmin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = objAdmin.getWritableDatabase();
        String codigo = etCodigo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String precio = etPrecio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            int cantidad = BaseDeDatos.update("articulos", registro, "codigo="+codigo, null);
            BaseDeDatos.close();

            if (cantidad==1){
                Toast.makeText(this, "El articulo se modifico correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }



    }
}
