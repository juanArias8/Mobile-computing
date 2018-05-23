package co.edu.udea.compumovil.gr06_20181.lab3;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import co.edu.udea.compumovil.gr06_20181.lab3.Interfaces.RestInterface;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.Message;

import co.edu.udea.compumovil.gr06_20181.lab3.POJO.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import co.edu.udea.compumovil.gr06_20181.lab3.Models.UserModel;

public class SingupActivity extends AppCompatActivity {

    private final int REQUEST_CODE_GALLERY = 999;

    private ImageView ivPhoto;
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        init();

    }

    public void singUp(View view){
        if(validateFields()){
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String status = "0";
            final String imageEncode = encodeBase64Image(ivPhoto);

            User user = new User(name, email, password, status, imageEncode);

            try {

                Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RestInterface.URL_SINGUP)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

                RestInterface restInterface = retrofit.create(RestInterface.class);

                Call<Message> callSignup = restInterface.singupUser(user);

                callSignup.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if(response.body().getSuccess()){
                            Toast.makeText(getApplicationContext(), response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
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

                Toast.makeText(this, String.format("user %s added successfully", name),
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                e.printStackTrace();
            }

            redirectToLogin();
        }
    }

    private void init(){
        etName = (EditText) findViewById(R.id.sing_et_name);
        etEmail = (EditText) findViewById(R.id.sing_et_email);
        etPassword = (EditText) findViewById(R.id.sing_et_password);
        ivPhoto = (ImageView) findViewById(R.id.sing_iv_user);
    }

    protected void ivPhotoLoadPhoto(View view) {
        ActivityCompat.requestPermissions(
                SingupActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 &&
                    grantResults[0] == getPackageManager().PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(),
                        "You don't have permission to access files", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

           try {
               InputStream inputStream = getContentResolver().openInputStream(uri);
               Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
               ivPhoto.setImageBitmap(bitmap);
           } catch (IOException e){
               e.printStackTrace();
           }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private String encodeBase64Image(ImageView image){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    private Bitmap decodeBase64Image(String image){
        byte[] decodeImage = Base64.decode(image, 0);
        return BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);
    }

    private boolean validateFields(){
        if(etName.getText().toString().equals("")
                || etEmail.getText().toString().equals("")
                || etPassword.getText().toString().equals("")){

            Toast.makeText(this, "All input fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void redirectToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
