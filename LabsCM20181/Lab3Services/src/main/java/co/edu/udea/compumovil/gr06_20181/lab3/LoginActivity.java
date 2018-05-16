package co.edu.udea.compumovil.gr06_20181.lab3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import co.edu.udea.compumovil.gr06_20181.lab3.Interfaces.RestInterface;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.Message;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init(){
        etEmail = (EditText) findViewById(R.id.login_et_email);
        etPassword = (EditText) findViewById(R.id.login_et_password);
        ivLogo = (ImageView) findViewById(R.id.iv_logo);

        byte[] logo = imageViewToByte(ivLogo);

        Bitmap bitmap = BitmapFactory.decodeByteArray(logo, 0, logo.length);
        RoundedBitmapDrawable roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmap);

        roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());

        ivLogo.setImageDrawable(roundedBitmapDrawable);
    }

    public void login(android.view.View view){
        if(validateFields()){
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RestInterface.URL_LOGIN)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RestInterface restInterface = retrofit.create(RestInterface.class);

            try {
                JSONObject paramObject = new JSONObject();
                paramObject.put("email", email);
                paramObject.put("password", password);

                Call<Message> callLogin = restInterface.loginUser(paramObject.toString());


                callLogin.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if(response.body().getSuccess()){
                            Toast.makeText(getApplicationContext(), response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AppActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "An error has occurred",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (JSONException e){
                e.printStackTrace();
            }
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

    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
