package co.edu.udea.compumovil.gr06_20181.lab3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udea.compumovil.gr06_20181.lab3.Interfaces.RestInterface;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected NavigationView navigationView = null;
    protected Toolbar toolbar = null;

    protected ImageView ivPhoto;
    protected TextView tvName;
    protected TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemFragment fragment = new AddItemFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.fragments_container, fragment);
                fragmentTransaction.commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Configure Navigation drawer header
        View headerView = navigationView.getHeaderView(0);

        ivPhoto = (ImageView) headerView.findViewById(R.id.nav_iv_photo);
        tvName = (TextView) headerView.findViewById(R.id.nav_tv_name);
        tvEmail = (TextView) headerView.findViewById(R.id.nav_tv_email);

        try {
            String email = getIntent().getExtras().getString("email");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RestInterface.URL_USER)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RestInterface restInterface = retrofit.create(RestInterface.class);

            try{
                JSONObject paramObject = new JSONObject();
                paramObject.put("email", email);

                Call<User> callFindUser = restInterface.findUser(paramObject.toString());

                callFindUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        tvName.setText(response.body().getUsername());
                        tvEmail.setText(response.body().getEmail());
                        ivPhoto.setImageBitmap(decodeBase64Image(response.body().getPhoto()));
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (JSONException je){
                je.printStackTrace();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        ProfileFragment fragment = new ProfileFragment();

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragments_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.Fragment fragment = null;

        if (id == R.id.nav_item_plates) {

            fragment = new PlatesFragment();

        } else if (id == R.id.nav_item_drinks) {

            fragment = new DrinksFragment();

        } else if (id == R.id.nav_item_profile) {

            fragment = new ProfileFragment();

        } else if (id == R.id.nav_item_settings) {

        } else if (id == R.id.nav_item_about) {

        } else if (id == R.id.nav_item_logout) {

        }

        if(fragment != null){
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragments_container, fragment);
            fragmentTransaction.commit();
        } else{
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Bitmap decodeBase64Image(String image){
        byte[] decodeImage = Base64.decode(image, 0);
        return BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);
    }
}
