package co.edu.udea.compumovil.gr06_20181.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.edu.udea.compumovil.gr06_20181.lab3.Models.UserModel;

public class SingupActivity extends AppCompatActivity {

    private final int REQUEST_CODE_GALLERY = 999;

    private android.widget.ImageView ivPhoto;
    private android.widget.EditText etName;
    private android.widget.EditText etEmail;
    private android.widget.EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        init();

    }

    public void singUp(android.view.View view){
        if(validateFields()){
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String state = "0";
            byte[] image = imageViewToByte(ivPhoto);

            UserModel user = new UserModel(name, email, password, state, image);
            try {
                //TODO: Register User
                android.widget.Toast.makeText(this, String.format("user %s added successfully", name),
                        android.widget.Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                e.printStackTrace();
            }

            redirectToLogin();
        }
    }

    private void init(){
        etName = (android.widget.EditText) findViewById(R.id.sing_et_name);
        etEmail = (android.widget.EditText) findViewById(R.id.sing_et_email);
        etPassword = (android.widget.EditText) findViewById(R.id.sing_et_password);
        ivPhoto = (android.widget.ImageView) findViewById(R.id.sing_iv_user);
    }

    protected void ivPhotoLoadPhoto(android.view.View view) {
        android.support.v4.app.ActivityCompat.requestPermissions(
                SingupActivity.this,
                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions,
                                           @android.support.annotation.NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 &&
                    grantResults[0] == getPackageManager().PERMISSION_GRANTED){
                android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                android.widget.Toast.makeText(getApplicationContext(),
                        "You don't have permission to access files", android.widget.Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            android.net.Uri uri = data.getData();

           try {
               java.io.InputStream inputStream = getContentResolver().openInputStream(uri);
               android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(inputStream);
               ivPhoto.setImageBitmap(bitmap);
           } catch (java.io.IOException e){
               e.printStackTrace();
           }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] imageViewToByte(android.widget.ImageView image){
        android.graphics.Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable)image.getDrawable()).getBitmap();
        java.io.ByteArrayOutputStream stream = new java.io.ByteArrayOutputStream();
        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private boolean validateFields(){
        if(etName.getText().toString().equals("")
                || etEmail.getText().toString().equals("")
                || etPassword.getText().toString().equals("")){

            android.widget.Toast.makeText(this, "All input fields are required", android.widget.Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void redirectToLogin(){
        android.content.Intent intent = new android.content.Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
