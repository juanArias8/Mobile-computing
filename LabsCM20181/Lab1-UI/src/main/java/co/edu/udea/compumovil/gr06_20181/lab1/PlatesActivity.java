package co.edu.udea.compumovil.gr06_20181.lab1;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class PlatesActivity extends AppCompatActivity {

    private EditText etPlateName;
    private EditText etPlatePrice;
    private CheckBox cbPlateMorning;
    private CheckBox cbPlateAfternoon;
    private CheckBox cbPlateNight;
    private RadioButton rbPlateEntrance;
    private RadioButton rbPlateMain;
    private EditText etPlateIngredients;
    private ImageView ivPlateImage;

    private TextView tvPlateName;
    private TextView tvPlatePrice;
    private TextView tvPlateTimetable;
    private TextView tvPlateType;
    private TextView tvPlateTime;
    private TextView tvPlateIngredients;

    private String time = "";

    private Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plates);

        etPlateName = (EditText) findViewById(R.id.et_plate_name);
        etPlatePrice = (EditText) findViewById(R.id.et_plate_price);
        cbPlateMorning = (CheckBox) findViewById(R.id.cb_plate_morning);
        cbPlateAfternoon = (CheckBox) findViewById(R.id.cb_plate_afternoon);
        cbPlateNight = (CheckBox) findViewById(R.id.cb_plate_night);
        rbPlateEntrance = (RadioButton) findViewById(R.id.rb_plate_entrance);
        rbPlateMain = (RadioButton) findViewById(R.id.rb_plate_main);
        etPlateIngredients = (EditText) findViewById(R.id.et_plate_ingredients);
        ivPlateImage = (ImageView) findViewById(R.id.iv_plate_add_image);

        tvPlateName = (TextView) findViewById(R.id.tv_plate_name);
        tvPlatePrice = (TextView) findViewById(R.id.tv_plate_price);
        tvPlateTimetable = (TextView) findViewById(R.id.tv_plate_timetable);
        tvPlateType = (TextView) findViewById(R.id.tv_plate_type);
        tvPlateTime = (TextView) findViewById(R.id.tv_plate_time);
        tvPlateIngredients = (TextView) findViewById(R.id.tv_plate_ingredients);
    }

    public void showTimePicker(View view){
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(PlatesActivity.this, onTimeSetListener, c.get(c.HOUR_OF_DAY),
                c.get(c.MINUTE), true).show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            time = String.valueOf(hour) + ":" + String.valueOf(minute);
        }
    };

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
            ivPlateImage.setImageURI(path);
        }
    }

    public boolean validateInputs(){
        boolean valid = true;
        if(path == null){
            Toast.makeText(this, "Photo is necessary", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(etPlateName.getText().toString().equals("")) {
            Toast.makeText(this, "Name is necessary", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(etPlatePrice.getText().toString().equals("")){
            Toast.makeText(this, "Price is necessary", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if((!cbPlateMorning.isChecked()) && (!cbPlateAfternoon.isChecked()) && (!cbPlateNight.isChecked())){
            Toast.makeText(this, "Timetable is necessary", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if((!rbPlateEntrance.isChecked()) && (!rbPlateMain.isChecked())){
            Toast.makeText(this, "Type is necessary", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(time.equals("")){
            valid = false;
        }
        if(etPlateIngredients.getText().toString().equals("")){
            Toast.makeText(this, "Ingredients is necessary", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    public void showData(View view){
        if(validateInputs()){
            tvPlateName.setText(String.format("%s %s", tvPlateName.getText(),
                    etPlateName.getText()));
            tvPlatePrice.setText(String.format("%s %s", tvPlatePrice.getText(),
                    etPlatePrice.getText()));
            if(cbPlateMorning.isChecked()){
                tvPlateTimetable.setText(String.format("%s %s", tvPlateTimetable.getText(),
                        cbPlateMorning.getText()));
            }
            if (cbPlateAfternoon.isChecked()){
                tvPlateTimetable.setText(String.format("%s %s", tvPlateTimetable.getText(),
                        cbPlateAfternoon.getText()));
            }
            if(cbPlateNight.isChecked()){
                tvPlateTimetable.setText(String.format("%s %s", tvPlateTimetable.getText(),
                        cbPlateNight.getText()));
            }
            if(rbPlateEntrance.isChecked()){
                tvPlateType.setText(String.format("%s %s", tvPlateType.getText(),
                        rbPlateEntrance.getText()));
            } else {
                tvPlateType.setText(String.format("%s %s", tvPlateType.getText(),
                        rbPlateMain.getText()));
            }
            tvPlateTime.setText(String.format("%s %s %s", tvPlateTime.getText(), time, "hours"));
            tvPlateIngredients.setText(String.format("%s %s", tvPlateIngredients.getText(),
                    etPlateIngredients.getText()));

        } else {
            Toast.makeText(this, "Something was wrong!!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetInputs(View view){
        path = null;
        etPlateName.setText("");
        etPlatePrice.setText("");
        cbPlateMorning.setChecked(false);
        cbPlateAfternoon.setChecked(false);
        cbPlateNight.setChecked(false);
        rbPlateEntrance.setChecked(false);
        rbPlateMain.setChecked(false);
        etPlateIngredients.setText("");
        ivPlateImage.setImageURI(path);
        tvPlateName.setText(R.string.tv_plate_name);
        tvPlatePrice.setText(R.string.tv_plate_price);
        tvPlateTimetable.setText(R.string.tv_plate_timetable);
        tvPlateType.setText(R.string.tv_plate_time);
        tvPlateTime.setText(R.string.tv_plate_time);
        tvPlateIngredients.setText(R.string.tv_plate_ingredients);
    }

    public void exit(View view){
        finish();
    }
}
