/*
package co.edu.udea.compumovil.gr06_20181.lab3.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr06_20181.lab3.Models.DrinkModel;
import co.edu.udea.compumovil.gr06_20181.lab3.R;

public class RecyclerDrinksAdapter extends RecyclerView.Adapter<RecyclerDrinksAdapter.ViewHolder> {

    public ArrayList<DrinkModel> drinks;

    public RecyclerDrinksAdapter(ArrayList<DrinkModel> drinks){
        this.drinks = drinks;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivPhoto;
        public TextView tvName;
        public TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView)itemView.findViewById(R.id.drink_item_photo);
            tvName = (TextView)itemView.findViewById(R.id.drink_item_name);
            tvPrice = (TextView)itemView.findViewById(R.id.drink_item_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_drink_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        byte[] photo = drinks.get(i).getPhoto();

        Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);

        viewHolder.tvName.setText(drinks.get(i).getName());
        viewHolder.tvPrice.setText(String.valueOf(drinks.get(i).getPrice()));
        viewHolder.ivPhoto.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }
}*/
