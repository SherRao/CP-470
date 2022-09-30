package tech.sherrao.wlu.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private String email;

    private Button loginButton;
    private EditText emailInputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getSimpleName(), "In onCreate()");
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("userDetails", MODE_PRIVATE);
        email = prefs.getString("email", "email@domain.com");
        emailInputField = super.findViewById(R.id.loginUsernameInputField);
        emailInputField.setText(email);

        loginButton = super.findViewById(R.id.loginButton);
        loginButton.setOnClickListener((view) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", emailInputField.getText().toString());
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
}