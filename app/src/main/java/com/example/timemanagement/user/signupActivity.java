package com.example.timemanagement.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.timemanagement.R;
import com.example.timemanagement.database.ReadWriteUserDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signupActivity extends AppCompatActivity {
    EditText edtemail,edtpassword,edtnumber;
    ProgressBar progressBar;
    Button btnsignup;
    private static final String TAG ="signupActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setcontrol();
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textemail =edtemail.getText().toString();
                String textpassword =edtpassword.getText().toString();
                String textnumber =edtnumber.getText().toString();
                if(TextUtils.isEmpty(textemail)){
                    Toast.makeText(signupActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    edtemail.setError("Email is required");
                    edtemail.requestFocus();
                } else if (TextUtils.isEmpty((textpassword))) {
                    Toast.makeText(signupActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    edtemail.setError("Password is required");
                    edtemail.requestFocus();
                } else if (TextUtils.isEmpty((textnumber))) {
                    Toast.makeText(signupActivity.this, "Please enter your number", Toast.LENGTH_SHORT).show();
                    edtnumber.setError("Number is required");
                    edtnumber.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textemail).matches()) {
                    Toast.makeText(signupActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    edtemail.setError("Valid email is required");
                    edtemail.requestFocus();
                }else if (textnumber.length()!=10) {
                    Toast.makeText(signupActivity.this, "Please re-enter your number", Toast.LENGTH_SHORT).show();
                    edtnumber.setError("Your number should be 10 digits");
                    edtnumber.requestFocus();
                }else if (textpassword.length()<6) {
                    Toast.makeText(signupActivity.this, "Password should be at least 6 digits", Toast.LENGTH_SHORT).show();
                    edtnumber.setError("Password too weak");
                    edtnumber.requestFocus();}
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    User(textemail,textpassword,textnumber);
                }
            }
        });
    }

    private void User(String textemail, String textpassword, String textnumber) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textemail,textpassword).addOnCompleteListener(signupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    ReadWriteUserDetail writeUserDetail = new ReadWriteUserDetail(textemail,textnumber);
                    DatabaseReference referenceprofile = FirebaseDatabase.getInstance().getReference("User");
                    referenceprofile.child(firebaseUser.getUid()).setValue(writeUserDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(signupActivity.this,"User registered succcessful, please verify your email.",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(signupActivity.this, signinActivity.class);
                                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP|intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(signupActivity.this,"User registered failed, please try again.",Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });


                }else {
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        edtpassword.setError("Your password is too weak. need use a mix alphabet and number ");
                        edtpassword.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        edtemail.setError("Your email is invalid or already use");
                        edtemail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(signupActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setcontrol() {
        edtemail =findViewById(R.id.edtemailsignup);
        edtpassword =findViewById(R.id.edtpasswordsignup);
        edtnumber =findViewById(R.id.edtnumbersignup);
        progressBar = findViewById(R.id.progressbarsignup);
        btnsignup =findViewById(R.id.btnsignup);

    }
}