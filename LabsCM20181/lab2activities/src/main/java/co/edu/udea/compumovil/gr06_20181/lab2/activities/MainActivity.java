package co.edu.udea.compumovil.gr06_20181.lab2.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.compumovil.gr06_20181.lab2.R;
import co.edu.udea.compumovil.gr06_20181.lab2.sql.AdminDBHelper;

public class MainActivity extends AppCompatActivity {

    //Se crean variables
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    EditText et_email, et_password;
    Button btnlogin;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openHelper = new AdminDBHelper(this);
        db = openHelper.getReadableDatabase();

        btnlogin = (Button) findViewById(R.id.btn_login);
        et_email = (EditText) findViewById(R.id.txt_email_L);
        et_password = (EditText) findViewById(R.id.txt_password_L);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                cursor = db.rawQuery("SELECT * FROM "+ AdminDBHelper.TABLE_USERS + " WHERE " + AdminDBHelper.COL_U3 + "=? AND " + AdminDBHelper.COL_U4 + "=?", new  String[]{email, password});
                if (cursor != null){
                    if (cursor.getCount()>0){
                        cursor.moveToNext();
                        Toast.makeText(getApplicationContext(), "Ingreso exitoso", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }


    public void register (View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void exit(View view){
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

