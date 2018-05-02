package co.edu.udea.compumovil.gr06_20181.lab2.Adapters;

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

import co.edu.udea.compumovil.gr06_20181.lab2.Models.PlateModel;
import co.edu.udea.compumovil.gr06_20181.lab2.R;

public class RecyclerPlatesAdapter extends RecyclerView.Adapter<RecyclerPlatesAdapter.ViewHolder>{
    public ArrayList<PlateModel> plates;

    public RecyclerPlatesAdapter(ArrayList<PlateModel> plates){
        this.plates = plates;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivPhoto;
        public TextView tvName;
        public TextView tvPrice;
        public TextView tvKind;
        public TextView tvPreparationTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.plate_item_photo);
            tvName = (TextView) itemView.findViewById(R.id.plate_item_name);
            tvPrice = (TextView) itemView.findViewById(R.id.plate_item_price);
            tvKind = (TextView) itemView.findViewById(R.id.plate_item_kind);
            tvPreparationTime = (TextView) itemView.findViewById(R.id.plate_item_preparation_time);

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
                .inflate(R.layout.card_plate_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        byte[] photo = plates.get(i).getPhoto();

        Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);

        viewHolder.tvName.setText(plates.get(i).getName());
        viewHolder.tvPrice.setText(String.valueOf(plates.get(i).getPrice()));
        viewHolder.tvKind.setText(plates.get(i).getKind());
        viewHolder.tvPreparationTime.setText(plates.get(i).getPreparationTime());
        viewHolder.ivPhoto.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return plates.size();
    }
}
