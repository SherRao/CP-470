package tech.sherrao.wlu.android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Switch switchToggle;
    private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getSimpleName(), "In onCreate()");
        setContentView(R.layout.activity_list_items);

        imageButton = super.findViewById(R.id.listItemsImageButton);
        imageButton.setOnClickListener((view) -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        });

        switchToggle = super.findViewById(R.id.listItemsSwitch);
        switchToggle.setOnCheckedChangeListener((view, isChecked) -> {
            if(isChecked)
                print("Switch is On", Toast.LENGTH_SHORT);

            else
                print("Switch is Off", Toast.LENGTH_LONG);
        });

        checkbox = super.findViewById(R.id.listItemsCheckbox);
        checkbox.setOnCheckedChangeListener((view, isChecked) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
            builder.setMessage("Do you want to finish this activity?")
                    .setTitle("Finish Activity?")
                    .setPositiveButton("Ok", (dialog, id) -> {
                        Intent resultIntent = new Intent(  );
                        resultIntent.putExtra("Response", "Here is my response");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    })
                    .setNegativeButton("Cancel", (dialog, id) ->  {
                    })
                    .show();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }

    private void print(String message, int toastDuration) {
        Toast toast = Toast.makeText(this, message, toastDuration);
        toast.show();
    }
}