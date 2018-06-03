package com.firetodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private final String TAG = getClass().getSimpleName();
    private static final int RC_SIGN_IN = 123;



    @Override
    protected void onStart () {
        super.onStart();
        // Check if user is sign in (non null) and update ui accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d(TAG, "Logged in");
            Log.d(TAG, currentUser.getUid());
            Log.d(TAG + "PhoneNumber", currentUser.getPhoneNumber());
            Log.d(TAG + "Email", currentUser.getEmail().isEmpty() ? "Email not found" : currentUser.getEmail());
            Log.d(TAG + "Display Name",  currentUser.getDisplayName().isEmpty() ? "display name not found" : currentUser.getDisplayName());
//            Log.d( TAG , currentUser.);
        } else {
            Log.d(TAG, "Opening signin activity");
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                                    new AuthUI.IdpConfig.FacebookBuilder().build(),
                                    new AuthUI.IdpConfig.GoogleBuilder().build()
                            ))
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            checkForUserSignIn(resultCode, data);
        }
    }

    private void checkForUserSignIn(int resultCode, Intent data) {
        Log.d(TAG, "resultCode "+ resultCode);
        Log.d(TAG, data.toString());
    }
}
