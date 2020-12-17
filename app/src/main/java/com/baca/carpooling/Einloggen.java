package com.baca.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class    Einloggen extends AppCompatActivity {


    String email;
    String password;

    EditText Email;
    EditText Password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einloggen);

        Email = (EditText) findViewById(R.id.mailEdit);
        Password = (EditText) findViewById(R.id.passwordEdit);




        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();


    }



    public void einloggen(View view){




        mAuth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(getApplicationContext(),Fahrten.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });






    }

    private void updateUI(FirebaseUser user) {
    }

    public void registrieren (View view){

        Intent intent = new Intent(this,Registrierung.class);
        startActivity(intent);

    }
}
