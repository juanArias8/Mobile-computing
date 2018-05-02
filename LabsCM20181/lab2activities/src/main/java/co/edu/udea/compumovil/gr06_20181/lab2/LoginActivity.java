package co.edu.udea.compumovil.gr06_20181.lab2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers.DbUsersHelper;

public class LoginActivity extends AppCompatActivity {

    public static DbUsersHelper dbUsersHelper;

    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        dbUsersHelper = new DbUsersHelper(this);
    }

    private void init(){
        etEmail = (EditText) findViewById(R.id.login_et_email);
        etPassword = (EditText) findViewById(R.id.login_et_password);
    }

    public void login(View view){
        if(validateFields()){
            String email;
            String password;
            int id_response;
            String email_response;
            String password_response;
            String state_response;

            Cursor cursor;

            email = etEmail.getText().toString().trim();
            password = etPassword.getText().toString().trim();

            cursor = dbUsersHelper.searchUser(email);
            if(cursor.moveToNext()){
                email_response = cursor.getString(0);
                password_response = cursor.getString(1);
                state_response = cursor.getString(2);

                if(email.equals(email_response)){
                    if(password.equals(password_response)){
                        if(state_response.equals("0")){
                            state_response = "1";
                            Log.d("email ==> " , email_response);
                            Log.d("password ==> " , password_response);
                            Log.d("State ==> ", state_response);

                            dbUsersHelper.updateState(email_response, state_response);
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

    public boolean validateFields(){
        if(etEmail.getText().toString().equals("") || etPassword.getText().toString().equals("")){

            Toast.makeText(this, "All input fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
