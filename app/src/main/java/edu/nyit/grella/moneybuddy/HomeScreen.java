package edu.nyit.grella.moneybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class HomeScreen extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void WishlistButton(View v) {
        Button wishlistbtn = (Button) v;
        startActivity(new Intent(getApplicationContext(), Wishlist.class));
    }

    public void GeneralBudgetingButton (View v) {
        //Go to General Budgeting page
        Button GeneralBudgeting = (Button) v;
        startActivity(new Intent(getApplicationContext(), GeneralBudgeting.class));
    }

    public void SavedSpentButton (View v) {
        //Go to General Budgeting page
        Button SavedSpentbtn = (Button) v;
        startActivity(new Intent(getApplicationContext(), SavedSpent.class));
    }


    public void SavingsHistoryButton (View v) {
        //Go to General Budgeting page
        Button SavingsHistorybtn = (Button) v;
        startActivity(new Intent(getApplicationContext(), SavingsHistory.class));
    }

    public void CalendarButton (View v) {
        //Go to General Budgeting page
        Button Calendarbtn = (Button) v;
        startActivity(new Intent(getApplicationContext(), CalendarBuddy.class));
    }

    public void DailyExpensesButton (View v) {
        //Go to General Budgeting page
        Button DailyExpensesbtn = (Button) v;
        startActivity(new Intent(getApplicationContext(), DailyExpenses.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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
}
