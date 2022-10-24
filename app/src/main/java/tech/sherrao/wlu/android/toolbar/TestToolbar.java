package tech.sherrao.wlu.android.toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
//        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch(id) {
            case R.id.toolbar_menu_action_one:
                Log.d("Toolbar", "Option 1 selected");
                break;

            case R.id.toolbar_menu_action_two:
                Log.d("Toolbar", "Option 2 selected");
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.toolbar_menu_action_three:
                Log.d("Toolbar", "Option 3 selected");
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