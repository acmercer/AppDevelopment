package com.example.appdevelopment.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdevelopment.ProfileActivity;
import com.example.appdevelopment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    TextView userEmail, userPas;
    String[] userAccount;
    Button createAccount, signIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_in);
        userEmail = findViewById(R.id.etSignInEmail);
        userPas = findViewById(R.id.etSignInPassword);
        signIn = findViewById(R.id.btnSignIn);
        createAccount = findViewById(R.id.btnCreateAccount2);
        userAccount = new String[]{(userEmail.getText()).toString(), (userPas.getText()).toString()};
        signIn.setOnClickListener(view -> signIn());
        createAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateAccountActivity.class));
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            FirebaseAuth.getInstance().signOut();
        }
        mAuth.addAuthStateListener(firebaseAuth -> {
            // Get signedIn user
            FirebaseUser user = firebaseAuth.getCurrentUser();});
    }

    public void signIn() {
            String email = (userEmail.getText()).toString();
            String password = (userPas.getText()).toString();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(new Intent(SignInActivity.this, ProfileActivity.class));
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
    }
}

