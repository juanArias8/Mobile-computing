package co.edu.udea.compumovil.gr06_20181.lab2.Adapters;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.compumovil.gr06_20181.lab2.R;

public class RecyclerDrinksAdapter extends RecyclerView.Adapter<RecyclerDrinksAdapter.ViewHolder> {

    private String[] names = {
            "Chapter One",
            "Chapter Two",
            "Chapter Three",
            "Chapter Four"};

    private String[] prices = {
            "Item one details",
            "Item two details",
            "Item three details",
            "Item four details",
            };

    private int[] images = {
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            };

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView ivPhoto;
        public TextView tvName;
        public TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView)itemView.findViewById(R.id.item_image);
            tvName = (TextView)itemView.findViewById(R.id.item_name);
            tvPrice = (TextView)itemView.findViewById(R.id.item_price);

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
                .inflate(R.layout.card_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvName.setText(names[i]);
        viewHolder.tvPrice.setText(prices[i]);
        viewHolder.ivPhoto.setImageResource(images[i]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
}