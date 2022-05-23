package com.example.appdevelopment.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdevelopment.MainActivity;
import com.example.appdevelopment.ProfileActivity;
import com.example.appdevelopment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {
    TextView userEmail, userPas, userCon;
    String userAccount[];
    Button createAccount, signIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_create_account);
        userEmail = findViewById(R.id.etEmail);
        userPas = findViewById(R.id.etPassword);
        userCon = findViewById(R.id.etConfirmPassword);
        createAccount = findViewById(R.id.btnCreateAccount);
        signIn = findViewById(R.id.btnSignIn2);
        userAccount = new String[]{(userEmail.getText()).toString(), (userPas.getText()).toString(), (userCon.getText()).toString()};
        signIn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignInActivity.class));
        });
        createAccount.setOnClickListener(view -> signIn());
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            FirebaseAuth.getInstance().signOut();
        }
    }

    public void signIn() {
        if ((userPas.getText()).toString().equals((userCon.getText()).toString())) {
            String email = (userEmail.getText()).toString();
            String password = (userPas.getText()).toString();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateAccountActivity.this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
