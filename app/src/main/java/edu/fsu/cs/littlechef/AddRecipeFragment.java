package edu.fsu.cs.littlechef;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;



public class AddRecipeFragment extends Fragment {

    private View mRootView;
    private EditText url;


    public AddRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        // All 3 Linear Layouts
        final LinearLayout Manual_OR_Url = mRootView.findViewById(R.id.Manual_Or_Url);
        final LinearLayout Manual_Layout = mRootView.findViewById(R.id.Manual_Layout);
        final LinearLayout Url_Layout = mRootView.findViewById(R.id.URL_Layout);


        // Stuff For Which Layout To Make Visible
        final Button Manual_Enter_Button = mRootView.findViewById(R.id.Enter_Recipe_Button);
        Manual_Enter_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manual_OR_Url.setVisibility(View.INVISIBLE);
                Manual_Layout.setVisibility(View.VISIBLE);
            }
        });

        final Button Url_Enter_Button = mRootView.findViewById(R.id.Enter_Url_Button);
        Url_Enter_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manual_OR_Url.setVisibility(View.INVISIBLE);
                Url_Layout.setVisibility(View.VISIBLE);
            }
        });

        // Stuff For Entering Via Url
        url = mRootView.findViewById(R.id.url);

        final Button Url_Submit_Button = mRootView.findViewById(R.id.Url_Submit_Button);
        Url_Submit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url.getText().toString().contains("allrecipes.com") && validURL(url.getText().toString()))
                {
                    getWebsite();
                    Toast.makeText(getActivity(), "Recipe Added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Please Enter A Valid Url", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final  Button Cancel_Button2 = mRootView.findViewById(R.id.Cancel_Button2);
        Cancel_Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manual_OR_Url.setVisibility(View.VISIBLE);
                Url_Layout.setVisibility(View.INVISIBLE);
            }
        });



        // Stuff For Manual Entering Recipe
        final EditText RecipeName = mRootView.findViewById(R.id.name_input);
        final EditText CookTime = mRootView.findViewById(R.id.cook_time_input);
        final EditText Ingredients = mRootView.findViewById(R.id.Ingredients_Input);
        final EditText Steps = mRootView.findViewById(R.id.Steps_Input);


        final Button CancelButton = mRootView.findViewById(R.id.Cancel_Button);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manual_OR_Url.setVisibility(View.VISIBLE);
                Manual_Layout.setVisibility(View.INVISIBLE);
            }
        });

        final Button RegisterButton = mRootView.findViewById(R.id.Submit_Button);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean Error = false;

                if (!(RecipeName.getText().toString().trim().length() > 0)) {
                    Error = true;
                }

                /*
                if (!(PrepTime.getText().toString().length() > 0)|| Integer.valueOf(PrepTime.getText().toString()) == 0)
                {
                    if (Integer.valueOf(PrepTime.getText().toString()) == 0) {
                        Toast.makeText(getActivity(), "Please Enter Prep. Time Above 0", Toast.LENGTH_SHORT).show();
                    }

                    Error = true;
                }
                 */

                if ((CookTime.getText().toString().trim().isEmpty()))
                {
                    Error = true;
                }
                else if (Integer.parseInt(CookTime.getText().toString()) == 0)
                {
                    Error = true;
                }

                /*
                if (!(Servings.getText().toString().length() > 0) || Integer.valueOf(Servings.getText().toString()) == 0)
                {
                    Error = true;
                }
                 */

                if (!(Ingredients.getText().toString().trim().length() > 0))
                {
                    Error = true;
                }

                if (!(Steps.getText().toString().trim().length() > 0))
                {
                    Error = true;
                }

                if (Error)
                {

                    if (!(CookTime.getText().toString().trim().isEmpty()))
                    {
                        if (Integer.parseInt(CookTime.getText().toString()) == 0)
                        {
                            Toast.makeText(getActivity(), "Please Enter Cook Time Above 0", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Form Not Fully Completed", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    String recipeName = RecipeName.getText().toString();

                    //Integer prepTime = Integer.valueOf(PrepTime.getText().toString());

                    Integer cookTime = Integer.valueOf(CookTime.getText().toString());

                    String ingredients[] = Ingredients.getText().toString().split("\\r?\\n");

                    List<String> ingredientsForDatabase = new ArrayList<String>();
                    for (int i = 0; i < ingredients.length; i++)
                    {
                        ingredientsForDatabase.add(ingredients[i]);
                    }

                    String steps[] = Steps.getText().toString().split("\\r?\\n");
                    List<String> stepsForDatabase = new ArrayList<String>();
                    for (int i = 0; i < steps.length; i++)
                    {
                        stepsForDatabase.add(steps[i]);
                    }

                    // Add Recipe To The Database
                    DatabaseReference RecipeDatabase;
                    DatabaseReference IngredientsDatabase;
                    DatabaseReference StepsDatabase;

                    RecipeDatabase = FirebaseDatabase.getInstance().getReference("recipes");
                    IngredientsDatabase = FirebaseDatabase.getInstance().getReference("ingredients");
                    StepsDatabase = FirebaseDatabase.getInstance().getReference("steps");

                    String id = RecipeDatabase.push().getKey();

                    Recipes recipe = new Recipes(id, recipeName, cookTime, ingredientsForDatabase, stepsForDatabase);

                    RecipeDatabase.child(id).setValue(recipe);

                    Toast.makeText(getActivity(), "Recipe Added", Toast.LENGTH_SHORT).show();


                    //Return To MainActivity
                    //(MainActivity)getActivity()).returnToMain();
                }
            }
        });


        return mRootView;
    }

    private static boolean validURL (String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                final StringBuilder builder2 = new StringBuilder();

                String temp;

                String recipeName;

                try {
                    Document doc = Jsoup.connect(url.getText().toString()).get();

                    String title = doc.title();
                    if (title.contains("- Allrecipes.com | Allrecipes"))
                    {
                        title = title.substring(0, title.length() - 36);
                    }
                    else {
                        title = title.substring(0, title.length() - 23);
                    }

                    recipeName = title;

                    Elements time = doc.select(".ready-in-time");
                    String cookTime = "";

                    if (!time.isEmpty())
                    {
                        for (Element itr : time) {
                            cookTime = itr.text();

                            cookTime = cookTime.substring(0, cookTime.length() - 2);

                            if (cookTime.contains("h"))
                            {
                                String[] hoursMinutes = cookTime.split(" h");

                                //hoursMinutes[0] = hoursMinutes[0].substring(0, hoursMinutes[0].length() - 1);

                                int minutes = Integer.parseInt(hoursMinutes[0]);

                                minutes = minutes * 60;

                                if (hoursMinutes.length > 1)
                                {
                                    hoursMinutes[1] = hoursMinutes[1].substring(1, hoursMinutes[1].length() - 1);

                                    minutes = minutes + Integer.parseInt(hoursMinutes[1]);
                                }

                                cookTime = Integer.toString(minutes);
                            }
                        }

                        // Weird Problem If Time is 1 Hour It Only Displays 1
                        // Attempt To Fix It
                        if (cookTime.equals("1"))
                        {
                            cookTime = "60";
                        }
                    }
                    else
                    {
                        time = doc.select(".recipe-meta-item-body");

                        int i = 0;

                        for (Element itr : time)
                        {
                            if (itr.toString().contains("hrs"))
                            {
                                cookTime = itr.text();

                                String[] hoursMinutes = cookTime.split(" hrs");

                                //hoursMinutes[0] = hoursMinutes[0].substring(0, hoursMinutes[0].length() - 1);

                                int minutes = Integer.parseInt(hoursMinutes[0]);

                                minutes = minutes * 60;

                                if (hoursMinutes.length > 1) {
                                    hoursMinutes[1] = hoursMinutes[1].substring(1, hoursMinutes[1].length() - 5);

                                    minutes = minutes + Integer.parseInt(hoursMinutes[1]);
                                }

                                cookTime = Integer.toString(minutes);
                            }
                            else if (itr.toString().contains("hr"))
                            {
                                cookTime = itr.text();

                                String[] hoursMinutes = cookTime.split(" hr");

                                //hoursMinutes[0] = hoursMinutes[0].substring(0, hoursMinutes[0].length() - 1);

                                int minutes = Integer.parseInt(hoursMinutes[0]);

                                minutes = minutes * 60;

                                if (hoursMinutes.length > 1)
                                {
                                    hoursMinutes[1] = hoursMinutes[1].substring(1, hoursMinutes[1].length() - 5);

                                    minutes = minutes + Integer.parseInt(hoursMinutes[1]);
                                }

                                cookTime = Integer.toString(minutes);
                            }
                            else
                            {
                                cookTime = itr.text();

                                cookTime = cookTime.substring(0, cookTime.length() - 5);
                            }

                            i++;

                            if (i == 3)
                            {
                                break;
                            }
                        }
                    }

                    //builder.append("\n").append(cookTime);

                    Elements ingredients = doc.select(".checkList__line");

                    // builder.append(title).append("\n");

                    // There are 2 different HTML layouts for recipes therefore we have to account
                    // for both. This web scraping only works on allrecipes.com
                    if (!ingredients.isEmpty()) {
                        for (Element ingredient : ingredients) {
                            // Added To Remove Unnecessary Element At End of .checkList__line
                            if (ingredient.toString().contains("Add all ingredients to list"))
                            {

                            }
                            else
                            {
                                builder.append("\n").append(ingredient.text());
                            }
                        }
                    }
                    else
                    {
                        ingredients = doc.select(".ingredients-item-name");
                        for (Element ingredient : ingredients) {
                            // Added To Remove Unnecessary Element At End of .checkList__line
                            if (ingredient.toString().contains("Add all ingredients to list"))
                            {

                            }
                            else
                            {
                                builder.append("\n").append(ingredient.text());
                            }
                        }
                    }

                    String ingredientsArray[] = builder.toString().split("\\r?\\n");
                    List<String> ingredientsForDatabase = new ArrayList<String>();
                    for (int i = 0; i < ingredientsArray.length; i++)
                    {
                        ingredientsForDatabase.add(ingredientsArray[i]);
                    }


                    Elements steps = doc.select(".step");

                    if (!steps.isEmpty()) {
                        for (Element step : steps) {
                            // Removes Any Advertisement Text
                            temp = step.text();
                            temp = temp.replace("Advertisement", "");

                            builder2.append(temp).append("\n\n");
                        }
                    }
                    else
                    {
                        steps = doc.select(".instructions-section-item");
                        for (Element step : steps) {
                            // Removes Any Advertisement Text
                            temp = step.text();
                            temp = temp.replace("Advertisement", "");

                            // Remove Step i From Start Of Instruction
                            for (int i = 0; i < 50; i++)
                            {
                                String temp2 = "Step " + i + " ";
                                temp = temp.replace(temp2, "");
                            }

                            builder2.append("\n\n").append(temp);
                        }
                    }

                    String stepsArray[] = builder2.toString().split("\\r?\\n");
                    List<String> stepsForDatabase = new ArrayList<String>();
                    for (int i = 0; i < stepsArray.length; i++)
                    {
                        stepsForDatabase.add(stepsArray[i]);
                    }

                    DatabaseReference RecipeDatabase;
                    RecipeDatabase = FirebaseDatabase.getInstance().getReference("recipes");

                    String id = RecipeDatabase.push().getKey();

                    Recipes recipe = new Recipes(id, recipeName, Integer.parseInt(cookTime), ingredientsForDatabase, stepsForDatabase);

                    RecipeDatabase.child(id).setValue(recipe);

                }

                catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");

                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Recipe Added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }


}
