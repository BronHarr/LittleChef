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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterFragment extends Fragment {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText confirmPassword;

    private ProgressDialog progressDialog;
    private FirebaseAuth  firebaseAuth;
    private FirebaseException firebaseException;

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
                if (isEmpty(editTextEmail)) { //if email is empty, display error
                    editTextEmail.setError("Must enter an email");
                } else if (isEmpty(editTextPassword)) { //if password is empty, display error
                    editTextPassword.setError("Must enter a password");
                } else if (!editTextPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                    confirmPassword.setError("Passwords must match"); //display error if passwords don't match
                }
                else {
                    String useremail = editTextEmail.getText().toString().trim();
                    String userpass = confirmPassword.getText().toString().trim();
                    progressDialog.setMessage("Registering User..."); //show progress bar while user registers
                    progressDialog.show();

                    RegisterUser(useremail, userpass);
                }
            }
        });

        return rootview;
    }

    // Function to register the user to Firebase
    private void RegisterUser(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){ //if it works, go to login page.
                    Toast.makeText(getActivity(), "User Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else{ // if it doesn't work, display error.
                    firebaseException = (FirebaseAuthException) task.getException();
                    Toast.makeText(getActivity(), "User not registered: "+firebaseException.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isEmpty(EditText text){  //check if edit-texts are empty.
        CharSequence string = text.getText().toString();
        return TextUtils.isEmpty(string);
    }
}
