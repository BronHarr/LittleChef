package edu.fsu.cs.littlechef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText user, pass;
    private Button login_button;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.login, container, false);
        login_button = rootView.findViewById(R.id.login);
        user = rootView.findViewById(R.id.username);
        pass = rootView.findViewById(R.id.password);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(user)){
                    user.setError("Must Enter a Username");
                }
                if(isEmpty(pass)){
                    pass.setError("Must Enter a password");
                }

                String username = user.getText().toString();
                String password = pass.getText().toString();

            }
        });

        return rootView;
    }

    //function to see if EditText is empty or not.
    private boolean isEmpty(EditText text){
        CharSequence string = text.getText().toString();
        return TextUtils.isEmpty(string);
    }

}
