package co.edu.udea.compumovil.gr06_20181.lab3;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


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

        ivPhoto.setImageDrawable(imageFromByteToDrawable(photo));
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

    private RoundedBitmapDrawable imageFromByteToDrawable(byte[] photo){
        Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        RoundedBitmapDrawable roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmap);

        roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());

        return roundedBitmapDrawable;
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
