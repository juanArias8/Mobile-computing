package co.edu.udea.compumovil.gr06_20181.lab2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import co.edu.udea.compumovil.gr06_20181.lab2.R;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
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
