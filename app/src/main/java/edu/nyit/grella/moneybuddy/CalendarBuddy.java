package edu.nyit.grella.moneybuddy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@SuppressLint("SimpleDateFormat")
public class CalendarBuddy extends FragmentActivity {
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private ArrayAdapter adapter;
    ListView listView;
    protected static ArrayList<String> arrayList = new ArrayList();
    private HashMap<String, Date> colorDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        colorDates = new HashMap<String, Date>(30);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        caldroidFragment = new CaldroidFragment();
        // 4. Access the ListView
        listView = (ListView) findViewById(R.id.calList);

        // Create ArrayAdapters for the ListView
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice,
                arrayList);

        // Set the ListViews to use the ArrayAdapter
        listView.setAdapter(adapter);
        LoadPreferences();

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            caldroidFragment.setArguments(args);
        }

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {
        // On click date
            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
                Calendar cal = Calendar.getInstance();

//                used for changing date cell color
                cal.add(Calendar.DATE, Integer.valueOf(formatter.format(date).substring(0,1)));
                final Date blueDate = date;
                LayoutInflater li = LayoutInflater.from(view.getContext());
                final View promptsView = li.inflate(R.layout.event_prompt, null);
//                Prep event input popup
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);
//                List of events
                ListView result = (ListView) findViewById(R.id.calList);
//                Show popup
                builder.setView(promptsView);
                builder.setPositiveButton("Add Event", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //add to ListView
                        String event;
                        event = formatter.format(blueDate)+" "+userInput.getText().toString();
                        arrayList.toString();
                        arrayList.add(event);
                        SavePreferences();
                        adapter.notifyDataSetChanged();
//                         set color works, but can't undo it
//                        caldroidFragment.setBackgroundResourceForDate(R.color.caldroid_holo_blue_light,
//                                blueDate);
//                        colorDates.put(formatter.format(blueDate),blueDate);
//                        caldroidFragment.refreshView();
                        dialog.dismiss();
                    }

                });

                builder.setNegativeButton("Never Mind", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClickDate(Date date, View view) {
//                Toast.makeText(getApplicationContext(),
//                        "Long click " + formatter.format(date),
//                        Toast.LENGTH_SHORT).show();
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        final TextView textView = (TextView) findViewById(R.id.textview);

        final Button deleteButton = (Button) findViewById(R.id.delete_button);

        // Customize the calendar
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                caldroidFragment.clearSelectedDates();
                SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
                int itemCount = listView.getCount();

                for (int i = itemCount - 1; i >= 0; i--) {
                    if (checkedItemPositions.get(i)) {
                        adapter.remove(arrayList.get(i));
                    }
                }
                checkedItemPositions.clear();
                SavePreferences();
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }
    protected void SavePreferences() {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = data.edit();
        editor.putInt("Status2_size", arrayList.size());

        for (int i=0; i<arrayList.size(); i++){
            arrayList.remove("Status2_" + i);
            editor.putString("Status2_" + i, arrayList.get(i));
        }

        editor.commit();
    }

    protected void LoadPreferences() {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        arrayList.clear();
        int size = data.getInt("Status2_size", 0);

        for (int i=0; i<size; i++) {
            arrayList.add(data.getString("Status2_" + i, null));
        }
        adapter.notifyDataSetChanged();
    }

}
