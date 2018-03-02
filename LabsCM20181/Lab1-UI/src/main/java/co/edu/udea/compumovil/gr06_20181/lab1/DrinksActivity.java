package co.edu.udea.compumovil.gr06_20181.lab1;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinksActivity extends AppCompatActivity {

    private static final String DRINK_NAME = "";
    private static final String DRINK_PRICE = "";
    private static final String DRINK_INGREDIENTS = "";

    private EditText etDrinkName;
    private EditText etDrinkPrice;
    private EditText etDrinkIngredients;
    private TextView tvDrinkName;
    private TextView tvDrinkPrice;
    private TextView tvDrinkIngredients;
    private ImageView ivDrinkImage;

    private Uri path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        etDrinkName = (EditText) findViewById(R.id.et_drink_name);
        etDrinkPrice = (EditText) findViewById(R.id.et_drink_price);
        etDrinkIngredients = (EditText) findViewById(R.id.et_drink_ingredients);

        tvDrinkName = (TextView) findViewById(R.id.tv_drink_name);
        tvDrinkPrice = (TextView) findViewById(R.id.tv_drink_price);
        tvDrinkIngredients = (TextView) findViewById(R.id.tv_drink_ingredients);
        ivDrinkImage = (ImageView) findViewById(R.id.iv_drink_add_image);

        if(savedInstanceState != null){
            etDrinkName.setText(savedInstanceState.getString(DRINK_NAME));
            etDrinkPrice.setText(savedInstanceState.getString(DRINK_PRICE));
            etDrinkIngredients.setText(savedInstanceState.getString(DRINK_INGREDIENTS));
        }
    }

    public void ibLoadImageDrinks(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

    public boolean validateInputsDrinks(){
        boolean valid = true;
        if(path == null){
            Toast.makeText(this, "Photo is necessary", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(etDrinkName.getText().toString().equals("")) {
            Toast.makeText(this, "Name is necessary", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(etDrinkPrice.getText().toString().equals("")){
            Toast.makeText(this, "Price is necessary", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(etDrinkIngredients.getText().toString().equals("")){
            Toast.makeText(this, "Ingredients is necessary", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    public void showDataDrinks(View view) {
        if (validateInputsDrinks()) {
            tvDrinkName.setText(String.format("%s %s", tvDrinkName.getText(),
                    etDrinkName.getText()));
            tvDrinkPrice.setText(String.format("%s %s", tvDrinkPrice.getText(),
                    etDrinkPrice.getText()));
            tvDrinkIngredients.setText(String.format("%s %s", tvDrinkIngredients.getText(),
                    etDrinkIngredients.getText()));
        } else {
            Toast.makeText(this, "Something was wrong!!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetInputsDrinks(View view){
        path = null;
        etDrinkName.setText("");
        etDrinkPrice.setText("");
        etDrinkIngredients.setText("");
        ivDrinkImage.setImageURI(path);
        tvDrinkName.setText(R.string.tv_drink_name);
        tvDrinkPrice.setText(R.string.tv_drink_price);
        tvDrinkIngredients.setText(R.string.tv_drink_ingredients);
    }

    public void exitDrinks(View view){
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString(DRINK_NAME, etDrinkName.getText().toString());
        outState.putString(DRINK_PRICE, etDrinkPrice.getText().toString());
        outState.putString(DRINK_INGREDIENTS, etDrinkIngredients.getText().toString());

        super.onSaveInstanceState(outState);
    }
}
