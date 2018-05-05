package co.edu.udea.compumovil.gr06_20181.lab2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers.DbHelper;
import co.edu.udea.compumovil.gr06_20181.lab2.Models.UserModel;

public class LoginActivity extends AppCompatActivity {

    private static DbHelper dbHelper;

    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        dbHelper = new DbHelper(this);
    }

    private void init(){
        etEmail = (EditText) findViewById(R.id.login_et_email);
        etPassword = (EditText) findViewById(R.id.login_et_password);
    }

    public void login(View view){
        if(validateFields()){
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            Cursor cursor = dbHelper.getUserByEmail(email);

            if(cursor.moveToNext()){
                Log.e("Cursor ==> ", "Entra al if");
                String nameResponse = cursor.getString(1);
                String emailResponse = cursor.getString(2);
                String passwordResponse = cursor.getString(3);
                String stateResponse = cursor.getString(4);
                byte[] photoResponse = cursor.getBlob(5);

                if(email.equals(emailResponse)){
                    if(password.equals(passwordResponse)){
                        if(stateResponse.equals("0")){
                            stateResponse = "1";

                            UserModel user = new UserModel(nameResponse, emailResponse,
                                    passwordResponse, stateResponse, photoResponse);

                            Log.d("email ==> " , emailResponse);
                            Log.d("password ==> " , passwordResponse);
                            Log.d("State ==> ", stateResponse);

                            dbHelper.updateUserState(user);
                        }

                        Toast.makeText(this, "User entered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, AppActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);

                    } else {
                        Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Wrong email", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void changeToSingup(View view){
        Intent intent = new Intent(this, SingupActivity.class);
        startActivity(intent);
    }

    private boolean validateFields(){
        if(etEmail.getText().toString().equals("") || etPassword.getText().toString().equals("")){

            Toast.makeText(this, "All input fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
