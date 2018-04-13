package co.edu.udea.compumovil.gr06_20181.lab2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText et_name, et_email, et_password;
    private ImageButton ib_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = (EditText)findViewById(R.id.txt_user_name);
        et_email = (EditText)findViewById(R.id.txt_email_L);
        et_password = (EditText)findViewById(R.id.txt_password_L);
        //ib_photo = (ImageButton)findViewById(R.id.ib_user_add_photo);
    }

    //Metodo para registrarse

    public void fillRegister(View view){
        AdminDBHelper admin = new AdminDBHelper(this, "administration", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String email = et_email.getText().toString();
        String name = et_name.getText().toString();
        String password = et_password.getText().toString();

        //String photo = ib_photo.get

        if(!email.isEmpty() && !name.isEmpty() && !password.isEmpty()){
            ContentValues register = new ContentValues();
            register.put("email", email);
            register.put("name", name);
            register.put("password", password);

            db.insert("user", null, register);
            db.close();

            et_email.setText("");
            et_password.setText("");
            et_name.setText("");

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(this, "Es necesario llenar todos los campos", Toast.LENGTH_LONG).show();

        }
    }

    //Metodo para ir al login
    public void logIn(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
