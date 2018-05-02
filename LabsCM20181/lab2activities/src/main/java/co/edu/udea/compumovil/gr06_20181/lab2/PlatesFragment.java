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

import co.edu.udea.compumovil.gr06_20181.lab2.Adapters.RecyclerPlatesAdapter;
import co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers.DbPlatesHelper;
import co.edu.udea.compumovil.gr06_20181.lab2.Models.PlateModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlatesFragment extends Fragment {

    public static DbPlatesHelper dbPlatesHelper;
    public ArrayList<PlateModel> plates;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    public PlatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plates, container, false);

        dbPlatesHelper = new DbPlatesHelper(getContext());

        plates = initializeData();

        recyclerView = (RecyclerView) view.findViewById(R.id.plate_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerPlatesAdapter(plates);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<PlateModel> initializeData(){
        PlateModel plate;
        ArrayList<PlateModel> plates = new ArrayList<>();
        Cursor cursor;

        String name;
        String kind;
        String preparationTime;
        double price;
        byte[] photo;

        cursor = dbPlatesHelper.getData();

        if(cursor != null){
            while(cursor.moveToNext()) {
                name = cursor.getString(1);
                kind = cursor.getString(2);
                price = cursor.getDouble(3);
                preparationTime = cursor.getString(4);
                photo = cursor.getBlob(5);

                plate = new PlateModel(photo, name, kind, preparationTime, price);
                plates.add(plate);
            }
        }

        return  plates;
    }

}
