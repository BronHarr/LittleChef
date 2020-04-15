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
    Button listButton;
    DatabaseReference RecipeDatabase;
    DatabaseReference IngredientsDatabase;
    DatabaseReference StepsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addButton);
        listButton = (Button) findViewById(R.id.listButton);

        RecipeDatabase = FirebaseDatabase.getInstance().getReference("recipes");
        IngredientsDatabase = FirebaseDatabase.getInstance().getReference("ingredients");
        StepsDatabase = FirebaseDatabase.getInstance().getReference("steps");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        RecipeViewFragment frag = new RecipeViewFragment();
        ft.add(R.id.Frag_container, frag);
        ft.commit();

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });
    }


    private void addRecipe() {
        String name = "Perfect Pot of Rice";
        int time = 45;
        List<String> ingredients = new ArrayList<String>();
        ingredients.add("1 cup of rice");
        ingredients.add("Kosher Salt");

        List<String> steps = new ArrayList<String>();
        steps.add("Rinse rice thoroughly.");
        steps.add("Combine salt and 2 cups of water in saucepan.");
        steps.add("Add rice.");
        steps.add("Stir and boil.");
        steps.add("Cover and reduce heat to low for 18 mins.");
        steps.add("Remove from heat and let stand for 15 mins.");

        String id = RecipeDatabase.push().getKey();

        Recipes recipe1 = new Recipes(id, name, time, ingredients, steps);

        RecipeDatabase.child(id).setValue(recipe1);

        Toast.makeText(this, "recipe added", Toast.LENGTH_LONG).show();

    }
}
