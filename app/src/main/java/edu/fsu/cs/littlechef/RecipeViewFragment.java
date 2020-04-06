package edu.fsu.cs.littlechef;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeViewFragment extends Fragment {
    String[] testlist;

    public RecipeViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_view, container, false);

        ArrayAdapter<String> adapter;
         testlist = getResources().getStringArray(R.array.ingredients);
         ListView Ingredient = (ListView) view.findViewById(R.id.foodlist);


         adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, testlist);
         Ingredient.setAdapter(adapter);


        return view;
    }

}
