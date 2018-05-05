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
import co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers.DbHelper;
import co.edu.udea.compumovil.gr06_20181.lab2.Models.DrinkModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrinksFragment extends Fragment {

    private static DbHelper dbHelper;

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

        dbHelper = new DbHelper(getActivity());

        drinks = initializeData();

        recyclerView = (RecyclerView) view.findViewById(R.id.drink_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerDrinksAdapter(drinks);
        recyclerView.setAdapter(adapter);

        return  view;
    }

    private ArrayList<DrinkModel> initializeData(){
        ArrayList<DrinkModel> drinks = new ArrayList<>();
        Cursor cursor = dbHelper.getAllDrinks();
        if(cursor != null){
            while(cursor.moveToNext()){
                String nameResponse = cursor.getString(1);
                double priceResponse = cursor.getDouble(2);
                byte[] photoResponse = cursor.getBlob(3);

                DrinkModel drink = new DrinkModel(nameResponse, priceResponse, photoResponse);
                drinks.add(drink);
            }
        }

        return drinks;
    }

}
