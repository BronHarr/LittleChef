package edu.fsu.cs.littlechef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    ListView listRecipes;
    TextView listText;
    DatabaseReference recipeDatabase;

    List<String> displayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        listRecipes = (ListView) findViewById(R.id.listRecipes);
        recipeDatabase = FirebaseDatabase.getInstance().getReference("recipes");
        displayList = new ArrayList<>();
        listText = (TextView) findViewById(R.id.listText);

    }

    @Override
    protected void onStart() {
        super.onStart();

        recipeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayList.clear();

                for(DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {

                    Recipes recipe1 = recipeSnapshot.getValue(Recipes.class);
//                    Toast.makeText(RecipeListActivity.this, recipe1.getRecipeName(), Toast.LENGTH_LONG).show();

                    displayList.add(recipe1.getRecipeName());
                }

                Toast.makeText(RecipeListActivity.this, displayList.get(0), Toast.LENGTH_LONG).show();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(RecipeListActivity.this, R.layout.rowlayout, R.id.listText, displayList);
                listRecipes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
