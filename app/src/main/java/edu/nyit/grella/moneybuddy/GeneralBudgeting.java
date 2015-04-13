package edu.nyit.grella.moneybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class GeneralBudgeting extends ActionBarActivity {

    static double cMoneyValue;
    static double pSavedValue;
    private EditText CurrentMoney;
    private EditText PercentSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_budgeting);
        CurrentMoney = (EditText) findViewById(R.id.editText);  //Gets instance of editText
        PercentSaved = (EditText) findViewById(R.id.editText2); //Gets instance of editText2
    }

    public void CalculateButton (View v) {
        //Calculates desired % of money to be saved

        String cMoney = CurrentMoney.getText().toString(); //Gets string from editText
        cMoneyValue = Double.parseDouble(cMoney); //Parses string to double

        String pSaved = PercentSaved.getText().toString(); //Gets string from editText2
        pSavedValue = Double.parseDouble(pSaved); //Parses string to double

        Button Calculate = (Button) v;
        startActivity(new Intent(getApplicationContext(), GenBudCalculate.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_general_budgeting, menu);
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
