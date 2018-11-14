package ca.bcit.ass2.shin_lee;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView children_list_view;
    SQLiteOpenHelper helper;
    EditText firstName;
    EditText lastName;
    EditText DOB;
    EditText street;
    EditText city;
    EditText province;
    EditText postalCode;
    EditText country;
    EditText lat;
    EditText lng;
    EditText naughty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        children_list_view = findViewById(R.id.children);
        setChildren();
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        helper = new DB(this);

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
                return super.onOptionsItemSelected(item);
        }
    }

    public void setChildren() {
        DB db = new DB(this);
        Child.children = db.get();
        if (Child.children != null) {
            ChildrenAdaptor adapter = new ChildrenAdaptor(MainActivity.this, Child.children);
            children_list_view.setAdapter(adapter);
        }
    }

    public boolean addDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Add a New Person!")
                .setView(initLayout())
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Map<String, String> userInputs = getAddInputs();
                        ((DB) helper).add(
                                userInputs.get("firstName"),
                                userInputs.get("lastName"),
                                userInputs.get("DOB"),
                                userInputs.get("street"),
                                userInputs.get("city"),
                                userInputs.get("province"),
                                userInputs.get("postalCode"),
                                userInputs.get("country"),
                                Double.parseDouble(userInputs.get("lat")),
                                Double.parseDouble(userInputs.get("lng")),
                                userInputs.get("naughty"));
                        setChildren();
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

        firstName = new EditText(this);
        firstName.setHint("First Name");
        firstName.setId(R.id.firstName);

        lastName = new EditText(this);
        lastName.setHint("Last Name");
        lastName.setId(R.id.lastName);

        DOB = new EditText(this);
        DOB.setHint("YYYY-MM-DD");
        DOB.setId(R.id.DOB);

        street = new EditText(this);
        street.setHint("Street");
        street.setId(R.id.street);

        city = new EditText(this);
        city.setHint("city");
        city.setId(R.id.city);

        province = new EditText(this);
        province.setHint("province");
        province.setId(R.id.DOB);

        postalCode = new EditText(this);
        postalCode.setHint("postalCode");
        postalCode.setId(R.id.postalCode);

        country = new EditText(this);
        country.setHint("Country");
        country.setId(R.id.country);

        lat = new EditText(this);
        lat.setHint("Latitude");
        lat.setId(R.id.lat);

        lng = new EditText(this);
        lng.setHint("Longitude");
        lng.setId(R.id.lng);

        naughty = new EditText(this);
        naughty.setHint("Naughty? (Y/N)");
        naughty.setId(R.id.naughty);

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

    Map<String, String> getAddInputs() {
        Map<String, String> userInputs = new HashMap<>();
        userInputs.put("firstName", firstName.getText().toString());
        userInputs.put("lastName", lastName.getText().toString());
        userInputs.put("DOB", DOB.getText().toString());
        userInputs.put("street", street.getText().toString());
        userInputs.put("city", city.getText().toString());
        userInputs.put("province", province.getText().toString());
        userInputs.put("postalCode", postalCode.getText().toString());
        userInputs.put("country", country.getText().toString());
        userInputs.put("lat", lat.getText().toString());
        userInputs.put("lng", lng.getText().toString());
        userInputs.put("naughty", naughty.getText().toString());
        return userInputs;
    }
}

//TODO:
// listview + array adaptor
// think about how to display the info
// search
// think about how to display the info