package co.edu.udea.compumovil.gr06_20181.lab2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Se crean variables
    EditText et_email, et_password;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_email = (EditText) findViewById(R.id.txt_email_L);
        et_password = (EditText) findViewById(R.id.txt_password_L);
    }

    public void sigIn(View view){
        AdminDBHelper admin = new AdminDBHelper(this, "administration", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        cursor = db.rawQuery("select email,password from user where email='"+email+"' and password='"+password+"'",null);

        if (cursor.moveToFirst()==true){
            String emailS = cursor.getString(0);
            String passwordS = cursor.getString(1);
            if(email.equals(emailS)&&password.equals(passwordS)){
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,"Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                et_email.setText("");
                et_password.setText("");
            }
        }
    }

    public void register (View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void exit(View view){
        finish();
    }
}

