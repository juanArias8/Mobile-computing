package co.edu.udea.compumovil.gr06_20181.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showPlates(View view){
        Intent intent = new Intent(this, PlatesActivity.class);
        startActivity(intent);
    }

    public void showDrinks(View view){
        Intent intent = new Intent(this, DrinksActivity.class);
        startActivity(intent);
    }
}
