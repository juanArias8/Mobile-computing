package co.edu.udea.compumovil.gr06_20181.lab2;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr06_20181.lab2.Adapters.RecyclerDrinksAdapter;
import co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers.DbDrinksHelper;
import co.edu.udea.compumovil.gr06_20181.lab2.Models.DrinkModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrinksFragment extends Fragment {

    public static DbDrinksHelper dbDrinksHelper;
    public ArrayList<DrinkModel> drinks;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;


    public DrinksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drinks, container, false);

        dbDrinksHelper = new DbDrinksHelper(getContext());

        drinks = initializeData();

        recyclerView = (RecyclerView) view.findViewById(R.id.drink_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerDrinksAdapter(drinks);
        recyclerView.setAdapter(adapter);

        return  view;
    }

    private ArrayList<DrinkModel> initializeData(){
        DrinkModel drink;
        ArrayList<DrinkModel> drinks = new ArrayList<>();
        Cursor cursor;

        String name;
        double price;
        byte [] photo;

        cursor = dbDrinksHelper.getData();

       if(cursor != null){
           while(cursor.moveToNext()){
               name = cursor.getString(1);
               price = cursor.getDouble(3);
               photo = cursor.getBlob(4);

               drink = new DrinkModel(photo, name, price);
               drinks.add(drink);
           }
       }

        return drinks;
    }

}
