package com.example.timemanagement.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.timemanagement.R;
import com.example.timemanagement.views.WeekViewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class signinActivity extends AppCompatActivity {
    EditText edtemail,edtpassword;
    FirebaseAuth auth;
    Button btnsignin;
    ImageView hideandshow;
    private static final String TAG="signinActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        setcontrol();
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textemail = edtemail.getText().toString();
                String textpassword =edtpassword.getText().toString();
                if(TextUtils.isEmpty(textemail)){
                    Toast.makeText(signinActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    edtemail.setError("Email is required");
                    edtemail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textemail).matches()) {
                    Toast.makeText(signinActivity.this,"Please re-enter your password",Toast.LENGTH_SHORT).show();
                    edtemail.setError("Vaild email is required");
                    edtemail.requestFocus();
                }
                else if (TextUtils.isEmpty(textpassword)) {
                    Toast.makeText(signinActivity.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                    edtpassword.setError("Password is required");
                    edtpassword.requestFocus();
                }
                else {
                    loginUser(textemail,textpassword);
                }
           }
        });
        hideandshow.setImageResource(R.drawable.invisible);
        edtpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        hideandshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtpassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()) ){
                    edtpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    hideandshow.setImageResource(R.drawable.visible);
                }else {
                    edtpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    hideandshow.setImageResource(R.drawable.invisible);
                }
            }
        });
   }


    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(signinActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(signinActivity.this,"You login successful", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(signinActivity.this, WeekViewActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        edtemail.setError("User does not exists or is no longer valid. Please check again");
                        edtemail.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        edtemail.setError("invalid credentials ,Please check and re-enter");
                        edtemail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(signinActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(signinActivity.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setcontrol() {
        edtemail =findViewById(R.id.edtemailsignin);
        edtpassword =findViewById(R.id.edtpasswordsignin);
        auth =FirebaseAuth.getInstance();
        btnsignin =findViewById(R.id.btnsignin);
        hideandshow =findViewById(R.id.hideandshowpwd);
    }
}