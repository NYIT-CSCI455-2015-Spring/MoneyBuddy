package edu.nyit.grella.moneybuddy;


import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DailyExpenses extends ListActivity {

    TextView itemTextView;

    Button addButton;
    Button deleteButton;

    EditText itemEditText;
    EditText costEditText;

    ListView listView;
    ArrayAdapter adapter;
    protected static ArrayList<String> arrayList = new ArrayList();

    // Creating the date
    String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_expenses);

        // 1. Access the TextViews defined in layout XML
        // and then set its text
        itemTextView = (TextView) findViewById(R.id.item_textView);
        itemTextView.setText("Items");

        // 2. Access the Buttons defined in layout XML
        // and listen for it here
        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(listener);

        deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(listenerDel);

        // 3. Access the EditTexts defined in layout XML
        itemEditText = (EditText) findViewById(R.id.item_edittext);
        costEditText = (EditText) findViewById(R.id.cost_edittext);

        // 4. Access the ListView
        listView = (ListView) findViewById(android.R.id.list);

        // Create ArrayAdapters for the ListView
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice,
                arrayList);

        // Set the ListViews to use the ArrayAdapter
        listView.setAdapter(adapter);
        LoadPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daily_expenses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Defining a click event listener for the button "Add"
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Take what was typed into the EditTexts
            // and use in the TextViews
            itemTextView.setText("Item");

            String row = itemEditText.getText().toString() + "          $" + costEditText.getText().toString() + "\n" + date;

            // Also add that value to the lists shown in the ListViews
            arrayList.toString();
            arrayList.add(row);
            SavePreferences();
            adapter.notifyDataSetChanged();
        }
    };

    // Defining a click event listener for the button "Delete"
    View.OnClickListener listenerDel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Getting the checked items from the listview
            SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
            int itemCount = getListView().getCount();

            for (int i=itemCount-1; i >= 0; i--){
                if (checkedItemPositions.get(i)) {
                    adapter.remove(arrayList.get(i));
                }
            }
            checkedItemPositions.clear();
            SavePreferences();
            adapter.notifyDataSetChanged();
        }
    };

    protected void SavePreferences() {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = data.edit();
        editor.putInt("DE_Status_size", arrayList.size());

        for (int i=0; i<arrayList.size(); i++){
            arrayList.remove("DE_Status_" + i);
            editor.putString("DE_Status_" + i, arrayList.get(i));
        }

        editor.commit();
    }

    protected void LoadPreferences() {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        arrayList.clear();
        int size = data.getInt("DE_Status_size", 0);

        for (int i=0; i<size; i++) {
            arrayList.add(data.getString("DE_Status_" + i, null));
        }
        adapter.notifyDataSetChanged();
    }
}
