package edu.fsu.cs.littlechef;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AddRecipeFragment extends Fragment {

    private View mRootView;


    public AddRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        // TODO: Format UI Input More And Create Functions To Input Data Into Future Database
        // TODO: Add More Specific Error Messaged To Prompt User i.e. Must Have Times Greater Than 0

        final EditText RecipeName = mRootView.findViewById(R.id.name_input);
        final EditText PrepTime = mRootView.findViewById(R.id.prep_time_input);
        final EditText CookTime = mRootView.findViewById(R.id.cook_time_input);
        final EditText Servings = mRootView.findViewById(R.id.servings_input);
        final EditText Ingredients = mRootView.findViewById(R.id.Ingredients_Input);
        final EditText Steps = mRootView.findViewById(R.id.Steps_Input);


        final Button CancelButton = mRootView.findViewById(R.id.Cancel_Button);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity)getActivity()).returnToMain();
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

                if (!(PrepTime.getText().toString().length() > 0)|| Integer.valueOf(PrepTime.getText().toString()) == 0)
                {
                    if (Integer.valueOf(PrepTime.getText().toString()) == 0) {
                        Toast.makeText(getActivity(), "Please Enter Prep. Time Above 0", Toast.LENGTH_SHORT).show();
                    }

                    Error = true;
                }

                if (!(CookTime.getText().toString().length() > 0)|| Integer.valueOf(CookTime.getText().toString()) == 0)
                {
                    if (Integer.valueOf(PrepTime.getText().toString()) == 0) {
                        Toast.makeText(getActivity(), "Please Enter Cook Time Above 0", Toast.LENGTH_SHORT).show();
                    }

                    Error = true;
                }

                if (!(Servings.getText().toString().length() > 0) || Integer.valueOf(Servings.getText().toString()) == 0)
                {
                    Error = true;
                }

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
                    Toast.makeText(getActivity(), "Form Not Fully Completed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String recipeName = RecipeName.getText().toString();

                    Integer prepTime = Integer.valueOf(PrepTime.getText().toString());

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

                    //String id = RecipeDatabase.push().getKey();

                    // Recipes recipe = new Recipes(id, recipeName, prepTime + cookTime, ingredientsForDatabase, stepsForDatabase);

                    //RecipeDatabase.child(id).setValue(recipe);

                    Toast.makeText(getActivity(), "Recipe Added", Toast.LENGTH_SHORT).show();

                    // TODO Here: *Actually Add Info To FireBase Database

                    //Return To MainActivity
                    //(MainActivity)getActivity()).returnToMain();
                }
            }
        });


        return mRootView;
    }


}
