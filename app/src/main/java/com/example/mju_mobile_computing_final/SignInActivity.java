package com.example.mju_mobile_computing_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.mju_mobile_computing_final.DTO.UserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SignInActivity extends Activity {
    private static final int RC_SIGN_IN = 200;
    private static final String TAG = SignInActivity.class.getName();

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton sign_in_button;
    private UserInfo user = UserInfo.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sign_in_button = findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso =
            new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) { updateUI(account); }

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            recreate();
            return;
        }
        addUserGlobal(account);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void addUserGlobal(GoogleSignInAccount account) {
        Log.d(TAG, "accountInfo:displayname = " + account.getDisplayName());
        Log.d(TAG, "accountInfo:email = " + account.getEmail());
        Log.d(TAG, "accountInfo:photoUrl = " + account.getPhotoUrl());
        user.setDisplayName(account.getDisplayName());
        user.setEmail(account.getEmail());
        user.setPhotoUrl(account.getPhotoUrl());

//        Log.d(TAG, "accountInfo:FamilyName = " + account.getFamilyName());
//        Log.d(TAG, "accountInfo:GivenName = " + account.getGivenName());
//        Log.d(TAG, "accountInfo:Account:Name = " + account.getAccount().name);
//        Log.d(TAG, "accountInfo:Account:Type = " + account.getAccount().type);
    }
}
