package edu.fsu.cs.littlechef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    ListView listRecipes;
    TextView listText;
    DatabaseReference recipeDatabase;

    List<String> displayList;
//    List<Recipes> databaseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        listRecipes = (ListView) findViewById(R.id.listRecipes);
        recipeDatabase = FirebaseDatabase.getInstance().getReference("recipes");
        displayList = new ArrayList<>();
        listText = (TextView) findViewById(R.id.listText);

        // ON LISTVIEW CLICK, CREATE RECIPE OBJECT FROM FIREBASE SNAPSHOT AND PASS IT TO RECIPEVIEW FRAGMENT
        listRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l){
                Toast.makeText(RecipeListActivity.this, displayList.get(i), Toast.LENGTH_LONG).show();

                recipeDatabase.orderByChild("recipeName").equalTo(displayList.get(i)).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        Recipes tempRecipe = new Recipes();
                        tempRecipe.setRecipeName(dataSnapshot.child("recipeName").getValue().toString());

                        tempRecipe.setId(dataSnapshot.child("id").getValue().toString());

                        String tempTime = dataSnapshot.child("timeTaken").getValue().toString();
                        tempRecipe.setTimeTaken(Integer.parseInt(tempTime));

                        // INSERTING INGREDIENTS
                        List<Object> tempArray = Arrays.asList(dataSnapshot.child("ingredients").getValue());
                        List<String> tempStringList = new ArrayList<>();

                        String[] idk = tempArray.get(0).toString().split(", ");

                        for(String elem : idk) {
                            tempStringList.add(elem);
                        }
                        tempRecipe.setIngredients(tempStringList);

                        //CLEARING STUFF UP
                        tempStringList = new ArrayList<>();

                        //INSERTING STEPS
                        tempArray = Arrays.asList(dataSnapshot.child("steps").getValue());
                        idk = tempArray.get(0).toString().split(", ");

                        for(String elem : idk) {
                            tempStringList.add(elem);
                        }
                        tempRecipe.setSteps(tempStringList);

                        Log.i("PLEASE", tempRecipe.getId());
                        Log.i("PLEASE", tempRecipe.getRecipeName());
                        Log.i("PLEASE", Integer.toString(tempRecipe.getTimeTaken()));
                        Log.i("PLEASE", tempRecipe.getIngredients().get(1));
                        Log.i("PLEASE", tempRecipe.getSteps().get(0));

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

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
