package edu.fsu.cs.littlechef;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
    Recipes recipe;
    private boolean goodResult = false;
    private boolean loggedIn = false;

    int LIST_ACTIVITY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addButton);
        listButton = (Button) findViewById(R.id.listButton);

//        RecipeDatabase = FirebaseDatabase.getInstance().getReference("recipes");
//        IngredientsDatabase = FirebaseDatabase.getInstance().getReference("ingredients");
//        StepsDatabase = FirebaseDatabase.getInstance().getReference("steps");
        if(!loggedIn) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            LoginFragment frag = new LoginFragment();
            ft.add(R.id.Frag_container, frag);
            loggedIn = true;
            ft.commit();
        }
        else{
            Intent i = new Intent(MainActivity.this, RecipeListActivity.class);
            startActivityForResult(i, LIST_ACTIVITY_RESULT);
        }

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
                startActivityForResult(intent, LIST_ACTIVITY_RESULT);
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
        /*
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
         */

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AddRecipeFragment frag = new AddRecipeFragment();

        ft.replace(R.id.Frag_container, frag);
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //prep boolean and recipe for onPostResume() to change to recipe view fragment
        if (requestCode == LIST_ACTIVITY_RESULT) {
            goodResult = true;
            recipe = (Recipes) data.getExtras().getSerializable("RECIPE");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(goodResult){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            RecipeViewFragment frag = new RecipeViewFragment();
            frag.setRecipe(recipe);
            ft.replace(R.id.Frag_container, frag);
            ft.commit();
        }
    }
}