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

        //check to see if user has logged in yet
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
        //if onActivityResult successfully ran, show recipe
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