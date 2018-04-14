package co.edu.udea.compumovil.gr06_20181.lab2.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import co.edu.udea.compumovil.gr06_20181.lab2.R;
import co.edu.udea.compumovil.gr06_20181.lab2.sql.AdminDBHelper;

public class RegisterActivity extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button btnregister, btnlogin;
    EditText et_name, et_email, et_password;
    private ImageButton ib_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        openHelper = new AdminDBHelper(this);
        btnregister = (Button) findViewById(R.id.btn_register);
        et_name = (EditText)findViewById(R.id.txt_user_name);
        et_email = (EditText)findViewById(R.id.txt_email_L);
        et_password = (EditText)findViewById(R.id.txt_password_L);
        //ib_photo = (ImageButton)findViewById(R.id.ib_user_add_photo);
        btnlogin = (Button) findViewById(R.id.btn_login);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = openHelper.getWritableDatabase();
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                insertData(name, email, password);
                Toast.makeText(getApplicationContext(),"Registro exitoso", Toast.LENGTH_LONG).show();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void insertData(String name, String email, String password){
        ContentValues register = new ContentValues();
        register.put(AdminDBHelper.COL_U2, name);
        register.put(AdminDBHelper.COL_U3, email);
        register.put(AdminDBHelper.COL_U4, password);
        long id = db.insert(AdminDBHelper.TABLE_USERS, null, register);
    }

    //Metodo para registrarse

  /*  public void fillRegister(View view){
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
*/
    //Metodo para ir al login
    public void logIn(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
