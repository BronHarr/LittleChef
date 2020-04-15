package com.example.recipe;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.fsu.cs.littlechef.R;


public class AddRecipeFragment extends Fragment{

    private OnRegisterFragmentInteractionListener mListener;

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
                // TODO: Will Return To Main Fragment Once Integrated
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
                    Error = true;
                }

                if (!(CookTime.getText().toString().length() > 0)|| Integer.valueOf(CookTime.getText().toString()) == 0)
                {
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
                    Toast.makeText(getActivity(), "Form Successfully Filled Out", Toast.LENGTH_SHORT).show();

                    // TODO Here: Add Info To A ContentValues (After Database Fields Finalized
                    // TODO 2: Parse Info Since It Probably Will Need To Be Edited For Database Entry
                }
            }
        });


        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterFragmentInteractionListener) {
            mListener = (OnRegisterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddRecipeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRegisterFragmentInteractionListener {
        void onSubmit(ContentValues values);
    }


}
