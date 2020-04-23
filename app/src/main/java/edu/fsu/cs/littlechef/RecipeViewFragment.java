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
import android.widget.TextView;

import java.util.List;


public class RecipeViewFragment extends Fragment {

    private Recipes recipe;

    public RecipeViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_view, container, false);

        TextView recTitle = view.findViewById(R.id.RecipeTitle);
        recTitle.setText(recipe.getRecipeName());

        ArrayAdapter<String> adapter1;
        ArrayAdapter<String> adapter2;

        //populate views using recipe object
        List<String> ingredients = recipe.getIngredients();
        List<String> steps = recipe.getSteps();

        ListView Ingredient = (ListView) view.findViewById(R.id.foodlist);
        ListView Steps = view.findViewById(R.id.stepsList);

        adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ingredients );
        Ingredient.setAdapter(adapter1);

        adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, steps);
        Steps.setAdapter(adapter2);


        //originally envisioned was that ingredient displays a side scrolling image that shows ingredient prep steps
        //there was no easily accessible source of prepping images for ingredients and would have to be custom illustrated
        //so for now every item click is an onion.
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



        return view;
    }
    public void setRecipe(Recipes r){
        this.recipe = r;
    }

}
