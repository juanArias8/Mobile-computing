package co.edu.udea.compumovil.gr06_20181.lab2;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

import co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers.DbHelper;
import co.edu.udea.compumovil.gr06_20181.lab2.Models.UserModel;

public class AppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static DbHelper dbHelper;

    private UserModel user = null;

    protected NavigationView navigationView = null;
    protected Toolbar toolbar = null;

    protected ImageView ivPhoto;
    protected TextView tvName;
    protected TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        dbHelper = new DbHelper(this);

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

        View headerView = navigationView.getHeaderView(0);

        ivPhoto = (ImageView) headerView.findViewById(R.id.nav_iv_photo);
        tvName = (TextView) headerView.findViewById(R.id.nav_tv_name);
        tvEmail = (TextView) headerView.findViewById(R.id.nav_tv_email);

        // Recuperate intent data
        try {
            String email = getIntent().getExtras().getString("email");
            Log.d("Email from login ==> ", email);
            Cursor cursor = dbHelper.getUserByEmail(email);
            if(cursor.moveToNext()){
                String nameResponse = cursor.getString(1);
                String emailResponse = cursor.getString(2);
                String passwordResponse = cursor.getString(3);
                String stateResponse = cursor.getString(4);
                byte[] photoResponse = cursor.getBlob(5);

                user = new UserModel(nameResponse, emailResponse, passwordResponse,
                        stateResponse, photoResponse);
            }

            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getPhoto(), 0, user.getPhoto().length);
                RoundedBitmapDrawable roundedBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), bitmap);

                roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());

                ivPhoto.setImageDrawable(roundedBitmapDrawable);
                tvName.setText(user.getName());
                tvEmail.setText(user.getEmail());
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        // Set main fragment
        Bundle bundle = new Bundle();
        bundle.putString("name", user.getName());
        bundle.putString("email",user.getEmail());
        bundle.putByteArray("photo", user.getPhoto());

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(bundle);

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

            Bundle bundle = new Bundle();
            bundle.putString("name", user.getName());
            bundle.putString("email",user.getEmail());
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
            Toast.makeText(getApplicationContext(), "Error sending data", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
