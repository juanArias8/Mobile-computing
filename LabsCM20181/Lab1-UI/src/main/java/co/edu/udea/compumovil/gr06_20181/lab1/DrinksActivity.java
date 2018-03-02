package co.edu.udea.compumovil.gr06_20181.lab1;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class DrinksActivity extends AppCompatActivity {

    private EditText etDrinkName;
    private EditText etDrinkPrice;
    private EditText etDrinkIngredients;
    private ImageView ivDrinkImage;

    private Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        etDrinkName = (EditText) findViewById(R.id.et_drink_name);
        etDrinkPrice = (EditText) findViewById(R.id.et_drink_price);
        etDrinkIngredients = (EditText) findViewById(R.id.et_drink_ingredients);
        ivDrinkImage = (ImageView) findViewById(R.id.iv_drink_add_image);

    }

    public void ibLoadImage(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Select an application"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            path = data.getData();
            ivDrinkImage.setImageURI(path);
        }
    }
}
