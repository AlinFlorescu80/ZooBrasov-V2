package com.example.zoobrasov;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    FirebaseFirestore firestore;


    private Button button;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button signOut;

    //Pentru Sign in:
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button googleBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        button = findViewById(R.id.button1);
        button.setOnClickListener(v -> openActivity2());

        button1 = findViewById(R.id.button2);
        button1.setOnClickListener(v -> openActivity3());


        button2 = findViewById(R.id.button3);
        button2.setOnClickListener(v -> openActivity4());


        button3 = findViewById(R.id.button4);
        button3.setOnClickListener(v -> openActivity5());

        googleBtn = findViewById(R.id.login);

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        signOut = findViewById(R.id.signOut);

        firestore = FirebaseFirestore.getInstance();


//        firestore.collection("Users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_LONG).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "Failure",Toast.LENGTH_LONG).show();
//            }
//        });

        googleBtn.setOnClickListener(view -> signIN());

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null)
        {
            String personName = acct.getDisplayName();
            googleBtn.setText(personName);
        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        if (!googleBtn.getText().toString().equals("Log in"))
            signOut.setVisibility(View.VISIBLE);
        else
            signOut.setVisibility(View.INVISIBLE);

    }

    void signOut() {
    gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            finish();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
    });
    }

    void signIN()
    {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                NavigateBack();


            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Eroare șefule", Toast.LENGTH_SHORT).show();
            }
        }

    }

    void NavigateBack(){
        finish();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void openActivity2(){
        Intent intent = new Intent(this, ProgramSiBilete.class);
        startActivity(intent);
    }

    public void openActivity3(){
        Intent intent = new Intent(this, Animale.class);
        startActivity(intent);
    }


    public void openActivity4(){
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }


    public void openActivity5(){
        Intent intent = new Intent(this, Harta.class);
        startActivity(intent);
    }




}