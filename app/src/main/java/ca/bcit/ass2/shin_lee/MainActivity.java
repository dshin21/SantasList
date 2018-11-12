package ca.bcit.ass2.shin_lee;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SQLiteOpenHelper helper = new DB(this);
        ((DB) helper).add("John",
                "Doe",
                "2018-11-01",
                "randomStreet",
                "Vancouver",
                "BC",
                "V2Y 111",
                "Canada",
                120.2,
                49.0,
                1,
                "2018-11-01");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                addDialog();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public boolean addDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Add a New Person!")
                .setView(initLayout())
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
        return true;
    }

    LinearLayout initLayout() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText firstName = new EditText(this);
        firstName.setHint("First Name");

        final EditText lastName = new EditText(this);
        lastName.setHint("Last Name");

        final EditText DOB = new EditText(this);
        DOB.setHint("YYYY-MM-DD");

        final EditText street = new EditText(this);
        street.setHint("Street");

        final EditText city = new EditText(this);
        city.setHint("city");

        final EditText province = new EditText(this);
        province.setHint("province");

        final EditText postalCode = new EditText(this);
        postalCode.setHint("postalCode");

        final EditText country = new EditText(this);
        country.setHint("Country");

        final EditText lat = new EditText(this);
        lat.setHint("Latitude");

        final EditText lng = new EditText(this);
        lng.setHint("Longitude");

        final EditText naughty = new EditText(this);
        naughty.setHint("Naughty? (Y/N)");

        layout.addView(firstName);
        layout.addView(lastName);
        layout.addView(DOB);
        layout.addView(street);
        layout.addView(city);
        layout.addView(province);
        layout.addView(postalCode);
        layout.addView(country);
        layout.addView(lat);
        layout.addView(lng);
        layout.addView(naughty);

        return layout;
    }
}
