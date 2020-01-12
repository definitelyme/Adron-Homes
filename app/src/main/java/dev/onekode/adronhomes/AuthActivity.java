package dev.onekode.adronhomes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.Scopes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.onekode.adronhomes.Foundation.ConnectionManager;
import dev.onekode.adronhomes.Models.User.Profile;
import dev.onekode.adronhomes.Models.User.User;

public class AuthActivity extends AppCompatActivity implements RootInterface {
    private static final int RC_SIGN_IN = 1822; // Arbitrary Request code value
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button anonymousSignInBtn, signInBtn;
    private ProgressBar progressBar;
    private View container;
    private View.OnClickListener onSignInClicked = v -> {
        configAuthButtons(false);

        if (ConnectionManager.isConnectionAvailable(this)) {
            showSignInMethods();
        } else {
            sendSnackbar(container, getString(R.string.no_internet_connection));
            sendToast(this, getString(R.string.no_internet_connection));
            configAuthButtons(true);
        }
    };
    private View.OnClickListener onAnonymousSigninBtnClicked = v -> {
        configAuthButtons(false);
        anonymousSignIn();
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        anonymousSignInBtn = findViewById(R.id.anonymousSignInBtn);
        signInBtn = findViewById(R.id.authPickerBtn);
        progressBar = findViewById(R.id.auth_progress_bar);
        container = getWindow().getDecorView();

        anonymousSignInBtn.setOnClickListener(onAnonymousSigninBtnClicked);
        signInBtn.setOnClickListener(onSignInClicked);

        progressBar.bringToFront(); // Bring Progress Bar to Fore-front
    }

    @Override
    protected void onStart() {
        super.onStart();

        authStateListener = firebaseAuth -> {
            user = firebaseAuth.getCurrentUser();
            if (user != null) updateUI(user);
            else configAuthButtons(true);
        };
    }

    public void anonymousSignIn() {
        List<AuthUI.IdpConfig> providers = getSelectedProviders();
        AuthUI.getInstance().silentSignIn(this, providers)
                .addOnCompleteListener(this, task -> {
                    if (!task.isSuccessful()) {
                        // Ignore any exceptions since we don't care about credential fetch errors.
                        FirebaseAuth.getInstance().signInAnonymously();
                        return;
                    }
                    // Sign-in successful
                    sendResponse(getString(R.string.auth_activity_verification_email_not_sent_string));
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        auth.removeAuthStateListener(authStateListener);
    }

    public void updateUI(@NonNull FirebaseUser firebaseUser) {
        saveUserInformation(firebaseUser);

        if (!firebaseUser.isEmailVerified())
            sendEmailVerification(firebaseUser); // If Email has not been verified, send verification email

        intentService();
    }

    private void intentService() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        killActivity();
    }

    private void configAuthButtons(boolean active) {
        setEnabled(signInBtn, active);
        setEnabled(anonymousSignInBtn, active);
        setVisibility(progressBar, !active);
    }

    private void killActivity() {
        finish();
    }

    private List<AuthUI.IdpConfig> getSelectedProviders() {
        AuthUI.IdpConfig emailIdp = new AuthUI.IdpConfig.EmailBuilder()
                .setRequireName(true)
                .build();

        AuthUI.IdpConfig googleIdp = new AuthUI.IdpConfig.GoogleBuilder()
                .setScopes(Arrays.asList(Scopes.PROFILE, Scopes.EMAIL))
                .build();

        AuthUI.IdpConfig facebookIdp = new AuthUI.IdpConfig.FacebookBuilder() // Configure Facebook Login Permissions
                .setPermissions(Arrays.asList(USER_EMAIL_PERMISSION, USER_PROFILE_PERMISSION))
                .build();

        AuthUI.IdpConfig guestIdp = new AuthUI.IdpConfig.AnonymousBuilder()
                .build();

        return Arrays.asList(
                guestIdp,
                emailIdp,
                googleIdp,
                facebookIdp
        );
    }

    private void showSignInMethods() {
        // You must provide a custom layout XML resource and configure at least one
        // provider button ID. It's important that that you set the button ID for every provider
        // that you have enabled.
        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout // Customize Sign In Layout
                .Builder(R.layout.fragment_login)
                .setEmailButtonId(R.id.email_signin_btn)
                .setGoogleButtonId(R.id.google_signIn_btn)
                .setFacebookButtonId(R.id.facebook_signIn_btn)
                .setAnonymousButtonId(R.id.guest_signin_btn)
                .setTosAndPrivacyPolicyId(R.id.email_tos_and_pp_text)
                .build();

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.logo)
                        .setTheme(R.style.AuthPickerTheme)
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true)
                        .setAuthMethodPickerLayout(customLayout)
                        .setAvailableProviders(getSelectedProviders())
                        .enableAnonymousUsersAutoUpgrade()
                        .setTosAndPrivacyPolicyUrls(TOS, PP)
                        .build(),
                RC_SIGN_IN
        );
    }

    private void saveUserInformation(@NonNull FirebaseUser firebaseUser) {
        ArrayList<Profile> providersArray = new ArrayList<>();

        for (UserInfo info : firebaseUser.getProviderData()) {
            Profile profile = new Profile();
            profile.setUid(info.getUid());
            profile.setDisplayName(info.getDisplayName());
            profile.setPhone(info.getPhoneNumber());
            profile.setPhotoUri(String.valueOf(info.getPhotoUrl()));
            profile.setProviderName(info.getProviderId());

            providersArray.add(profile);
        }

        User user = new User.Builder()
                .setUid(firebaseUser.getUid())
                .setName(firebaseUser.getDisplayName())
                .setEmail(firebaseUser.getEmail())
                .setPhone(firebaseUser.getPhoneNumber())
                .setPhotoUri(String.valueOf(firebaseUser.getPhotoUrl()))
                .setEmailVerified(firebaseUser.isEmailVerified())
                .setProviders(providersArray)
                .create();

        user.setCreated_at(firebaseUser.getMetadata().getCreationTimestamp());
        user.setUpdated_at(firebaseUser.getMetadata().getLastSignInTimestamp());

        if (firebaseUser.getMetadata().getCreationTimestamp() == firebaseUser.getMetadata().getLastSignInTimestamp()) {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            DocumentReference documentReference = database.collection(User.getNode(User.class)).document(firebaseUser.getUid());
            documentReference.set(user)
                    .addOnSuccessListener(aVoid -> Log.i(TAG, "saveUserInformation: User data added!"));
        }
    }

    private void sendEmailVerification(@NonNull FirebaseUser firebaseUser) {
        firebaseUser.sendEmailVerification()
                .addOnSuccessListener(aVoid -> sendToast(this, getString(R.string.auth_activity_verification_email_string)))
                .addOnFailureListener(e -> {
                    if (!user.isAnonymous())
                        sendToast(this, getString(R.string.auth_activity_verification_email_not_sent_string));
                });
    }

}
