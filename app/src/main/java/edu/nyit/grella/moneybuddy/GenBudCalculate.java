package edu.nyit.grella.moneybuddy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GenBudCalculate extends ActionBarActivity {

    static double GenBudMoneySaved;
    static double GenBudIfYouSpend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_bud_calculate);

        //Calculates Money that will be saved
        GenBudMoneySaved = GeneralBudgeting.cMoneyValue * GeneralBudgeting.pSavedValue;

        //Calculates If you spend this amount of money
        GenBudIfYouSpend = GeneralBudgeting.cMoneyValue - GenBudMoneySaved;

        TextView IfYouSpendOutput = (TextView) findViewById(R.id.textView);
        IfYouSpendOutput.setText("$" + GenBudIfYouSpend);

        TextView CurrentMoneyOutput = (TextView) findViewById(R.id.textView10);
        CurrentMoneyOutput.setText("$" + GeneralBudgeting.cMoneyValue);

        TextView MoneySavedOutput = (TextView) findViewById(R.id.textView7);
        MoneySavedOutput.setText("$" + GenBudMoneySaved);
    }

    public void HomeButton (View v) {
        //Go to HomeScreen
        Button Home = (Button) v;
        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
    }

    public void RetryCalculateButton (View v) {
        // Enter a new value to calculate desired % of money to be saved
        Button Retry = (Button) v;
        startActivity(new Intent(getApplicationContext(), GeneralBudgeting.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gen_bud_calculate, menu);
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
