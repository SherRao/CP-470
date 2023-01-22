package tech.sherrao.wlu.android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    @SuppressLint("ApplySharedPref")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getSimpleName(), "In onCreate()");
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = getSharedPreferences("userDetails", MODE_PRIVATE);
        String savedEmail = prefs.getString("email", "email@domain.com");
        EditText emailInputField = super.findViewById(R.id.loginUsernameInputField);
        emailInputField.setText(savedEmail);

        Button loginButton = super.findViewById(R.id.loginButton);
        loginButton.setOnClickListener((view) -> {
            String email = emailInputField.getText().toString();
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                print(super.getString(R.string.InvalidEmailToast), Toast.LENGTH_LONG);
                return;
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.commit();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getSimpleName(), "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getSimpleName(), "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(this.getClass().getSimpleName(), "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(this.getClass().getSimpleName(), "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getSimpleName(), "In onDestroy()");
    }

    private void print(String message, int toastDuration) {
        Toast toast = Toast.makeText(this, message, toastDuration);
        toast.show();
    }
}