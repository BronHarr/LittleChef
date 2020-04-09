package edu.fsu.cs.littlechef;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class    MainActivity extends FragmentActivity {

    Button addButton;
    DatabaseReference RecipeDatabase;
    DatabaseReference IngredientsDatabase;
    DatabaseReference StepsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addButton);
        RecipeDatabase = FirebaseDatabase.getInstance().getReference("recipes");
        IngredientsDatabase = FirebaseDatabase.getInstance().getReference("ingredients");
        StepsDatabase = FirebaseDatabase.getInstance().getReference("steps");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        RecipeViewFragment frag = new RecipeViewFragment();
        ft.add(R.id.Frag_container, frag);
        ft.commit();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
//                startActivity(intent);
                addRecipe();
            }
        });
    }


    private void addRecipe() {
        String name = "Turkey Sandwhich";
        int time = 15;
        List<String> ingredients = new ArrayList<String>();
        ingredients.add("2 slices of bread.");
        ingredients.add("1 tspn of mayo.");

        List<String> steps = new ArrayList<String>();
        steps.add("Lay down bread.");
        steps.add("Toast bread if desired.");


        String id = RecipeDatabase.push().getKey();

        Recipes recipe1 = new Recipes(id, name, time, ingredients, steps);

        RecipeDatabase.child(id).setValue(recipe1);

        Toast.makeText(this, "recipe added", Toast.LENGTH_LONG).show();

    }
}
