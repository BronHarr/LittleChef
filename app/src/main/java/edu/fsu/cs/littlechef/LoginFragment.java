package edu.fsu.cs.littlechef;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class LoginFragment extends Fragment {
    private EditText email, pass;
    private Button login_button;
    private Button register_button;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


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
        register_button = rootView.findViewById(R.id.button);
        email = rootView.findViewById(R.id.email);
        pass = rootView.findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(email)){
                    email.setError("Must Enter a email");
                }
                if(isEmpty(pass)){
                    pass.setError("Must Enter a password");
                }

                String emailId = email.getText().toString();
                String password = pass.getText().toString();
                LoginUser(emailId, password);
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment mFrag = new RegisterFragment();
                ft.replace(R.id.Frag_container, mFrag);
                ft.commit();
            }
        });
        return rootView;
    }

    private void LoginUser(String mail, String pass) {
        mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getActivity(), RecipeListActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT);

                }
            }
        });
    }

    //function to see if EditText is empty or not.
    private boolean isEmpty(EditText text){
        CharSequence string = text.getText().toString();
        return TextUtils.isEmpty(string);
    }

}
