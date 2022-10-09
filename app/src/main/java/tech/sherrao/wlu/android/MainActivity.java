package tech.sherrao.wlu.android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getSimpleName(), "In onCreate()");
        setContentView(R.layout.activity_main);

        Button mainButton = super.findViewById(R.id.mainButton);
        mainButton.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
            startActivityForResult(intent, 10);
        });

        Button startChatButton = super.findViewById(R.id.startChatButton);
        startChatButton.setOnClickListener((view) -> {
            Log.i(this.getClass().getSimpleName(), "User clicked Start Chat");

            Intent intent = new Intent(MainActivity.this, ChatWindow.class);
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

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        if(requestCode == 10)
            Log.i(this.getClass().getSimpleName(), "Returned to MainActivity.onActivityResult");

        if(responseCode == Activity.RESULT_OK) {
            String messagePassed = data.getStringExtra("Response");
            print(super.getString(R.string.ListItemsToMainActivityToast) + messagePassed, Toast.LENGTH_LONG);
        }
    }

    private void print(String message, int toastDuration) {
        Toast toast = Toast.makeText(this, message, toastDuration);
        toast.show();
    }
}