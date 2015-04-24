package edu.nyit.grella.moneybuddy;


import android.app.ListActivity;
import android.content.Intent;
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

    protected static String cost;
    protected static ArrayList<String> costArray = new ArrayList();

    ListView listView;
    ArrayAdapter adapter;
    protected static ArrayList<String> arrayList = new ArrayList();

    // Creating the date
    protected static String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_expenses);

        // Access the TextViews defined in layout XML
        // and then set its text
        itemTextView = (TextView) findViewById(R.id.item_textView);
        itemTextView.setText("Items");

        // Access the Buttons defined in layout XML
        // and listen for it here
        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(listener);

        deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(listenerDel);

        // Access the EditTexts defined in layout XML
        itemEditText = (EditText) findViewById(R.id.item_edittext);
        costEditText = (EditText) findViewById(R.id.cost_edittext);

        // Access the ListView
        listView = (ListView) findViewById(android.R.id.list);

        // Create ArrayAdapters for the ListView
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice,
                arrayList);

        // LoadCostArray();

        // Set the ListViews to use the ArrayAdapter
        listView.setAdapter(adapter);
        LoadPreferences();
    }

    public void GraphButton (View v) {
        Button ViewGraph = (Button) v;
        startActivity(new Intent(getApplicationContext(), SavedSpent.class));
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

            // putting user input into an array
            cost = costEditText.getText().toString();
            if (cost.length() != 0) {
                costArray.add(cost);
            }

            // setting user inputs to strings
            cost = costEditText.getText().toString();
            String item = itemEditText.getText().toString();

            String row = item + "          $" + cost + "\n" + date;

            // Also add that value to the lists shown in the ListViews
            // arrayList.toString();
            arrayList.add(row);
            costArray.add(cost);
            SaveCostArray();
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
                    //adapter.remove(costArray.get(i));
                }
            }
            checkedItemPositions.clear();               // deletes the data from the listview if it is checked
            SavePreferences();
            adapter.notifyDataSetChanged();
        }
    };

    // saving the values entered into the textviews in sharedprefs
    protected void SavePreferences() {
        // saving data in sharedprefs to be shown in listview
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = data.edit();
        editor.putInt("DE_Status_size", arrayList.size());

        for (int i=0; i<arrayList.size(); i++){
            arrayList.remove("DE_Status_" + i);
            editor.putString("DE_Status_" + i, arrayList.get(i));
        }

        editor.commit();
    }

    // loading the values from sharedprefs into the listview
    protected void LoadPreferences() {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        arrayList.clear();
        int size = data.getInt("DE_Status_size", 0);

        for (int i=0; i<size; i++) {
            arrayList.add(data.getString("DE_Status_" + i, null));
        }
        adapter.notifyDataSetChanged();
    }

    protected void SaveCostArray() {
        // saving the cost in an array using sharedprefs to be used for graphing
        SharedPreferences cost_data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor cost_editor = cost_data.edit();
        cost_editor.putInt("CostArray_Status_size", costArray.size());

        for (int i=0; i<arrayList.size(); i++){
            costArray.remove("CostArray_Status_" + i);
            cost_editor.putString("CostArray_Status_" + i, costArray.get(i));
        }

        cost_editor.commit();
    }

    /*
    protected void LoadCostArray() {
        SharedPreferences cost_data = PreferenceManager.getDefaultSharedPreferences(this);
        costArray.clear();
        int size = cost_data.getInt("CostArray_Status_size", 0);

        for (int i=0; i<size; i++) {
            costArray.add(cost_data.getString("CostArray_Status_" + i, null));
        }
    }
    */
}
