package co.edu.udea.compumovil.gr06_20181.lab3;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.util.Log;
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

import co.edu.udea.compumovil.gr06_20181.lab3.DbHelpers.DbHelper;
import co.edu.udea.compumovil.gr06_20181.lab3.Interfaces.RestInterface;
import co.edu.udea.compumovil.gr06_20181.lab3.Models.UserModel;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static DbHelper dbHelper;

    protected NavigationView navigationView = null;
    protected Toolbar toolbar = null;

    protected ImageView ivPhoto;
    protected TextView tvName;
    protected TextView tvEmail;

    protected UserModel user;
    protected String email_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);

        try {
            email_in = getIntent().getExtras().getString("email");
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        updateTableUsers();

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

        tvName = (TextView) headerView.findViewById(R.id.nav_tv_name);
        tvEmail = (TextView) headerView.findViewById(R.id.nav_tv_email);
        ivPhoto = (ImageView) headerView.findViewById(R.id.nav_iv_photo);

        user = getUserByEmail(email_in);

        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        ivPhoto.setImageDrawable(imageFromByteToDrawable(user.getPhoto()));

       initProfileFragment();
    }

    private void initProfileFragment(){
        // Set main fragment
        Bundle bundle = new Bundle();
        bundle.putString("name", user.getName());
        bundle.putString("email", user.getEmail());
        bundle.putByteArray("photo", user.getPhoto());

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(bundle);

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragments_container, fragment);
        fragmentTransaction.commit();
    }

    private RoundedBitmapDrawable imageFromByteToDrawable(byte[] photo){
        Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        RoundedBitmapDrawable roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmap);

        roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());

        return roundedBitmapDrawable;
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

            Bundle bundle = new Bundle();
            bundle.putString("name", user.getName());
            bundle.putString("email", user.getEmail());
            bundle.putByteArray("photo", user.getPhoto());
            fragment = new ProfileFragment();
            fragment.setArguments(bundle);

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

    private void updateTableUsers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestInterface.URL_USER)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestInterface restInterface = retrofit.create(RestInterface.class);

        try{
            JSONObject paramObject = new JSONObject();
            paramObject.put("email", email_in);

            Call<User> callFindUser = restInterface.findUser(paramObject.toString());

            callFindUser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User>  response) {

                    String name = response.body().getUsername();
                    String email = response.body().getEmail();
                    String password = response.body().getPassword();
                    String status = response.body().getStatus();
                    String photo_encode = response.body().getPhoto();

                    byte [] photo =  Base64.decode(photo_encode, 0);

                    UserModel user = new UserModel(
                            name, email, password, status, photo
                    );

                    try {
                        DbHelper dbHelper = new DbHelper(getApplicationContext());
                        dbHelper.saveUser(user);
                        Toast.makeText(getApplicationContext(), "user saved into db",
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
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

    }

    private UserModel getUserByEmail(String email){
        UserModel userModel;
        Cursor cursor = dbHelper.getUserByEmail(email);
        if(cursor.moveToNext()){
            String nameResponse = cursor.getString(1);
            String emailResponse = cursor.getString(2);
            String passwordResponse = cursor.getString(3);
            String stateResponse = cursor.getString(4);
            byte[] photoResponse = cursor.getBlob(5);

            userModel =  new UserModel(nameResponse, emailResponse, passwordResponse,
                    stateResponse, photoResponse);
        } else {
            userModel =  new UserModel("null", "null", "null",
                    "null", null);
        }
        Log.e("Username from db =>", userModel.getName());
        return userModel;
    }

    private RoundedBitmapDrawable decodeBase64Image(String image){
        byte[] decodeImage = Base64.decode(image, 0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);
        RoundedBitmapDrawable roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmap);

        roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());

        return roundedBitmapDrawable;
    }
}
