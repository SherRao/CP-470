package tech.sherrao.wlu.android.toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import com.google.android.material.snackbar.Snackbar;

import tech.sherrao.wlu.android.MainActivity;
import tech.sherrao.wlu.android.R;
import tech.sherrao.wlu.android.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, binding.toolbar.getId());
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(view -> Snackbar.make(view, "This is a string!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, binding.toolbar.getId());
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.toolbar_menu_action_one:
                Log.d("Toolbar", "Option 1 selected");
                Toast.makeText(this, "Option 1 selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.toolbar_menu_action_two:
                Log.d("Toolbar", "Option 2 selected");
                Toast.makeText(this, "Option 2 selected", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.ToolbarDialogTitle);
                builder.setPositiveButton(R.string.ToolbarDialogOk, (dialog, id) -> finish());
                builder.setNegativeButton(R.string.ToolbarDialogCancel, (dialog, id) -> {});
                builder.create().show();
                break;

            case R.id.toolbar_menu_action_three:
                Log.d("Toolbar", "Option 3 selected");
                Toast.makeText(this, "Option 3 selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.toolbar_menu_action_four:
                Log.d("Toolbar", "Option 4 selected");
                Toast.makeText(this, "Version 1.0, by Nausher Rao", Toast.LENGTH_SHORT).show();
                break;

            default:
                Log.d("Toolbar", "Default selected");
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }
}