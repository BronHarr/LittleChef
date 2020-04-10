package edu.fsu.cs.littlechef;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        Ingredient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder prepbuilder = new AlertDialog.Builder(getActivity());
                View prepView = getLayoutInflater().inflate(R.layout.ingredient_prep_dialog, null);
                ImageView ingredImg = prepView.findViewById(R.id.ingredientImg);
                ingredImg.setImageResource(R.drawable.onion_background);

                prepbuilder.setView(prepView);
                AlertDialog dialog = prepbuilder.create();
                dialog.show();
            }
        });


         adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, testlist);
         Ingredient.setAdapter(adapter);


        return view;
    }

}
