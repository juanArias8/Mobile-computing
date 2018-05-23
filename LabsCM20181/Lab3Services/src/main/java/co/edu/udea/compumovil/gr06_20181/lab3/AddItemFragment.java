package co.edu.udea.compumovil.gr06_20181.lab3;


import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Calendar;

import co.edu.udea.compumovil.gr06_20181.lab3.Interfaces.RestInterface;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.Drink;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.Plate;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment extends Fragment {

    private static final int PICK_IMAGE = 100;


    private boolean onSelectImagePlate = false;
    private boolean onSelectImageDrink = false;

    private ImageView ivPhotoPlate;
    private EditText etNamePlate;
    private EditText etKindPlate;
    private EditText etPricePlate;
    private Button btnPreparationTime;
    private Button btnAddPlate;

    private ImageView ivPhotoDrink;
    private EditText etNameDrink;
    private EditText etPriceDrink;
    private Button btnAddDrink;


    private String preparation_time;

    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        init(view);

        btnPreparationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        btnAddPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlate();
            }
        });

        btnAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrink();
            }
        });

        ivPhotoPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImagePlate = true;
                openGallery();
            }
        });

        ivPhotoDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageDrink = true;
                openGallery();
            }
        });

        return view;
    }

    private void init(View view){
        ivPhotoPlate = (ImageView) view.findViewById(R.id.add_plate_iv_photo);
        etNamePlate = (EditText) view.findViewById(R.id.add_plate_et_name);
        etKindPlate = (EditText) view.findViewById(R.id.add_plate_et_kind);
        etPricePlate = (EditText) view.findViewById(R.id.add_plate_et_price);
        btnPreparationTime = (Button) view.findViewById(R.id.add_plate_btn_time);
        btnAddPlate = (Button) view.findViewById(R.id.add_plate_btn_add);

        ivPhotoDrink = (ImageView) view.findViewById(R.id.add_drink_iv_photo);
        etNameDrink = (EditText) view.findViewById(R.id.add_drink_et_name);
        etPriceDrink = (EditText) view.findViewById(R.id.add_drink_et_price);
        btnAddDrink = (Button) view.findViewById(R.id.add_drink_btn_add);
    }

    private void addPlate(){
        if(validateFieldsPlate()){
            String name = etNamePlate.getText().toString();
            String kind = etKindPlate.getText().toString();
            String price = etPricePlate.getText().toString();
            String photo = encodeBase64Image(ivPhotoPlate);

            Plate plate = new Plate(name, kind, price, preparation_time,  photo);

            try {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RestInterface.URL_PLATES)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RestInterface restInterface = retrofit.create(RestInterface.class);

                Call<Message> callSavePlate = restInterface.savePlate(plate);

                callSavePlate.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if(response.body().getSuccess()){
                            Toast.makeText(getContext(), response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        cleanPlateFields();
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getContext(), "An error has occurred",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(getContext(), "Plate saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void addDrink(){
        if(validateFieldsDrink()){
            String name = etNameDrink.getText().toString();
            String price = etPriceDrink.getText().toString();
            String photo = encodeBase64Image(ivPhotoDrink);

            Drink drink = new Drink(name, price, photo);

            try {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RestInterface.URL_DRINKS)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RestInterface restInterface = retrofit.create(RestInterface.class);

                Call<Message> callSaveDrink = restInterface.saveDrink(drink);

                callSaveDrink.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if(response.body().getSuccess()){
                            Toast.makeText(getContext(), response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        cleanDrinkFields();
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getContext(), "An error has occurred",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(getContext(), "Drink saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void showTimePicker(){
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(getContext(), onTimeSetListener, c.get(c.HOUR_OF_DAY),
                c.get(c.MINUTE), true).show();
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            preparation_time = String.valueOf(hour) + ":" + String.valueOf(minute);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if(onSelectImagePlate){
                    ivPhotoPlate.setImageBitmap(bitmap);
                    onSelectImagePlate = false;
                } else if(onSelectImageDrink){
                    ivPhotoDrink.setImageBitmap(bitmap);
                    onSelectImageDrink = false;
                } else {
                    Toast.makeText(getContext(), "An error has occurred", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private String encodeBase64Image(ImageView image){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    private boolean validateFieldsPlate(){
        if(etNamePlate.getText().equals("")
                || etKindPlate.getText().equals("")
                || etPricePlate.getText().equals("")
                || preparation_time.equals("")){

            Toast.makeText(getContext(), "All input fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateFieldsDrink(){
        if(etNameDrink.getText().equals("") || etPriceDrink.getText().equals("")){
            Toast.makeText(getContext(), "All input fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void cleanPlateFields(){
        ivPhotoPlate.setImageBitmap(null);
        etNamePlate.setText("");
        etKindPlate.setText("");
        etPricePlate.setText("");
    }

    private void cleanDrinkFields(){
        ivPhotoDrink.setImageBitmap(null);
        etNameDrink.setText("");
        etPriceDrink.setText("");
    }

}
