package edu.fsu.cs.littlechef;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterFragment extends Fragment {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText confirmPassword;

    private ProgressDialog progressDialog;
    private FirebaseAuth  firebaseAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_register, container, false);
        buttonRegister = rootview.findViewById(R.id.registerButton);
        editTextEmail = rootview.findViewById(R.id.email1);
        editTextPassword = rootview.findViewById(R.id.password1);
        confirmPassword = rootview.findViewById(R.id.confirm);
        progressDialog = new ProgressDialog(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(editTextEmail)) {
                    editTextEmail.setError("Must enter an email");
                } else if (isEmpty(editTextPassword)) {
                    editTextPassword.setError("Must enter a password");
                } else if (!editTextPassword.toString().equals(confirmPassword.toString())) {
                    confirmPassword.setError("Passwords must match");
                }
                String useremail = editTextEmail.toString().trim();
                String userpass = confirmPassword.toString().trim();
                progressDialog.setMessage("Registering User...");
                progressDialog.show();

                RegisterUser(useremail, userpass);
            }
        });

        return rootview;
    }

    private void RegisterUser(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "User Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), RecipeListActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "User not registered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isEmpty(EditText text){
        CharSequence string = text.getText().toString();
        return TextUtils.isEmpty(string);
    }
}
