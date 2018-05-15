package co.edu.udea.compumovil.gr06_20181.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    private android.widget.EditText etEmail;
    private android.widget.EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init(){
        etEmail = (android.widget.EditText) findViewById(R.id.login_et_email);
        etPassword = (android.widget.EditText) findViewById(R.id.login_et_password);
    }

    public void login(android.view.View view){
        if(validateFields()){
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

        }
    }

    public void changeToSingup(android.view.View view){
        android.content.Intent intent = new android.content.Intent(this, SingupActivity.class);
        startActivity(intent);
    }

    private boolean validateFields(){
        if(etEmail.getText().toString().equals("") || etPassword.getText().toString().equals("")){

            android.widget.Toast.makeText(this, "All input fields are required", android.widget.Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
