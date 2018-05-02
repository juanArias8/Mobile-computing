package co.edu.udea.compumovil.gr06_20181.lab2;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers.DbUsersHelper;
import co.edu.udea.compumovil.gr06_20181.lab2.Models.UserModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ImageView ivPhoto;
    public TextView tvName;
    public TextView tvEmail;

    public Bundle bundle;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);

        try {
           bundle = getArguments();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        String name = bundle.getString("name");
        String email = bundle.getString("email");
        byte[] photo = bundle.getByteArray("photo");

        Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        RoundedBitmapDrawable roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmap);

        roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());

        ivPhoto.setImageDrawable(roundedBitmapDrawable);
        tvName.setText(name);
        tvEmail.setText(email);

        return  view;
    }

    // Initialize all views
    private void init(View view){
        ivPhoto = (ImageView) view.findViewById(R.id.app_profile_iv_photo);
        tvName = (TextView) view.findViewById(R.id.app_profile_tv_name);
        tvEmail = (TextView) view.findViewById(R.id.app_profile_tv_email);
    }

}
