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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registrierung extends AppCompatActivity {



    EditText Vorname;
    EditText Nachname;
    EditText GSM;
    EditText Mail;
    EditText Password;
    EditText Password2;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrierung);




        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        Vorname = (EditText) findViewById(R.id.Vorname);
        Nachname = (EditText) findViewById(R.id.Nachname);
        GSM = (EditText) findViewById(R.id.GSM);
        Mail = (EditText) findViewById(R.id.Mail);
        Password = (EditText) findViewById(R.id.Password);
        Password2 = (EditText) findViewById(R.id.Passwordnm);

    }


            //Password Überprüfung
    private boolean isPasswordValid (String pass){

        if(Password.getText().toString().equals(Password2.getText().toString())){
            return true;
        }
        else{
            return false;
        }

    }


                //Email Überprüfung
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        if(email.contains("@stud.tau.edu.tr")||email.contains("@tau.edu.tr")){
            return true;
        }
        else{
            return false;
        }
    }



    public void registrieren (View view) {



        boolean cancel = false;
        View focusView = null;

                            //Überprüfung von Informationen
        if (Vorname.getText().toString().length() < 1 || Nachname.getText().toString().length() < 1 || GSM.getText().toString().length() < 5 || Mail.getText().toString().length() < 5 || Password.getText().toString().length() < 6) {
            Toast.makeText(getApplicationContext(), "Bitte Fügen Sie alle Informationen!", Toast.LENGTH_SHORT).show();
        } if (!isEmailValid(Mail.getText().toString())) {
            Mail.setError(getString(R.string.error_invalid_email));
            Toast.makeText(getApplicationContext(), "Bitte geben Sie ihre Uni-Mail", Toast.LENGTH_LONG).show();
            focusView = Mail;
            cancel = true;
        }
        if (!isPasswordValid(Password.getText().toString())) {
            Password.setError(getString(R.string.error_invalid_email));
            Toast.makeText(getApplicationContext(), "Bitte schreiben Sie gleiche Password", Toast.LENGTH_LONG).show();
            focusView = Password;
            focusView = Password2;
            cancel = true;
        }


                        //Wenn alle Informationen richtig sind,wird das User bestehen.
        else {

            mAuth.createUserWithEmailAndPassword(Mail.getText().toString(), Password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                String uid;
                                FirebaseUser user = mAuth.getCurrentUser();
                                uid = user.getUid().toString();
                                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
                                writeNewUser(dbref, uid);
                                updateUI(user);
                                Intent intent = new Intent(getApplicationContext(),Fahrten.class);
                                intent.putExtra("UID",uid);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });

        }
    }

    private void updateUI(FirebaseUser user) {

    }


                        //Aufschreibung von Informationen des User zu RealtimeDatabase
    private void writeNewUser(DatabaseReference databaseReference, String id) {

        User user = new User(Vorname.getText().toString(), Nachname.getText().toString(), GSM.getText().toString(),Mail.getText().toString(),Password.getText().toString(),null);
        databaseReference.child("users").child(id).setValue(user);
    }


}
