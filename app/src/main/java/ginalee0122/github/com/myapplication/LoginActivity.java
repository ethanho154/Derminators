package ginalee0122.github.com.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements OnClickListener {
  FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private AutoCompleteTextView mEmailView;
  private EditText mPasswordView;
  private View mProgressView;
  private View mLoginFormView;
  private TextView signUpView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mEmailView = findViewById(R.id.email);
    mPasswordView = findViewById(R.id.password);

    Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
    mEmailSignInButton.setOnClickListener(this);

    mLoginFormView = findViewById(R.id.login_form);
    mProgressView = findViewById(R.id.login_progress);

    signUpView = findViewById(R.id.sign_up_text);
    signUpView.setOnClickListener(this);
  }

  private boolean validateFields(String email, String password) {
    // Reset errors.
    mEmailView.setError(null);
    mPasswordView.setError(null);
    boolean cancel = false;
    View focusView = null;

    // Check for a valid password, if the user entered one.
    if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
      mPasswordView.setError(getString(R.string.error_invalid_password));
      focusView = mPasswordView;
      cancel = true;
    }
    // Check for a valid email address.
    if (TextUtils.isEmpty(email)) {
      mEmailView.setError(getString(R.string.error_field_required));
      focusView = mEmailView;
      cancel = true;
    } else if (!isEmailValid(email)) {
      mEmailView.setError(getString(R.string.error_invalid_email));
      focusView = mEmailView;
      cancel = true;
    }

    if (cancel) {
      focusView.requestFocus();
    }
    return !cancel;
  }

  private boolean isEmailValid(String email) {
    return email.contains("@");
  }

  private boolean isPasswordValid(String password) {
    return password.length() > 4;
  }

  private void signIn() {
    String email = mEmailView.getText().toString();
    String password = mPasswordView.getText().toString();

    // Show a progress spinner, and kick off a background task to
    // perform the user login attempt.
    if (validateFields(email, password)) {
      showProgress(true);
      mAuth.signInWithEmailAndPassword(email, password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login attempt", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.d("SWITCH-ING to:", "menu screen");
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    Log.d("SWITCH-ED to:", "menu screen");
                  } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login attempt", "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                  }
                  showProgress(false);
                }
              });
    }
  }

  private void createAccount() {
    String email = mEmailView.getText().toString();
    String password = mPasswordView.getText().toString();

    if (validateFields(email, password)) {
      showProgress(true);
      mAuth.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Signup attempt", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                  } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Signup attempt", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                  }
                  showProgress(false);
                }
              });
    }
  }

  /**
   * Shows the progress UI and hides the login form.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  private void showProgress(final boolean show) {
    // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
    // for very easy animations. If available, use these APIs to fade-in
    // the progress spinner.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
      mLoginFormView.animate().setDuration(shortAnimTime).alpha(
              show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
      });

      mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
      mProgressView.animate().setDuration(shortAnimTime).alpha(
              show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
      });
    } else {
      // The ViewPropertyAnimator APIs are not available, so simply show
      // and hide the relevant UI components.
      mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
  }

  @Override
  public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.email_sign_in_button) {
      signIn();
    }
    if (i == R.id.sign_up_text) {
      createAccount();
    }
  }



}
