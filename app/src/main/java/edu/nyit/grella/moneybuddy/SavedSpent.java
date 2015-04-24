package edu.nyit.grella.moneybuddy;

/**
 * Created by grella on 3/9/15.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class SavedSpent extends ActionBarActivity {

    public static ArrayList yvalue;
    public static ArrayList<Integer> yvalues;
    //public static int Yvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedspent);
        GraphView graph = (GraphView) findViewById(R.id.graph);

        yvalue = DailyExpenses.costArray;
        yvalues = getIntegerArray(yvalue);

        // check to see if cost of item was added during a specified month
        // then add the ones in the same month - use a for loop

        /*
        for (Integer i: yvalues) {
            Yvalue = yvalues.get(i);
        }
        */


        // getting the month
        String date = DailyExpenses.date;
        String[] split = date.split("/");
        String monthstr = split[0];
        int month = Integer.parseInt(monthstr);
        System.out.println(month);

        // DataPoint[] data = new DataPoint[5];    // DailyExpenses.arrayList

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);

        // use static labels for horizontal label to show months
        final StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        //staticLabelsFormatter.setVerticalLabels(new String[]{"$0", "$250", "$500", "$750", "$1000", "$1250", "$1500"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        // LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]
                {
                        new DataPoint(0, yvalues.get(0)),
                        new DataPoint(1, yvalues.get(1)),
                        new DataPoint(2, yvalues.get(2)),
                        new DataPoint(3, yvalues.get(3)),
                        new DataPoint(4, yvalues.get(4))
                });


        series.setTitle("Spending per Month");
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(5);
        series.setThickness(5);
        graph.addSeries(series);
    }


    private ArrayList<Integer> getIntegerArray(ArrayList<String> stringArray) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(String stringValue : stringArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Integer.parseInt(stringValue));
            } catch(NumberFormatException nfe) {
                //System.out.println("Could not parse " + nfe);
                Log.w("NumberFormat", "Parsing failed! " + stringValue + " can not be an integer");
            }
        }
        return result;
    }

    /*
    public Integer getYValue(){
        for (Integer i: yvalues) {
            return i;
        }
        return -1;
    }
    */

    /*
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DailyExpenses.date);
        return cal.get(Calendar.MONTH);
    }
    */


    public void HomeButton (View v) {
        //Go to HomeScreen
        Button Home = (Button) v;
        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_savedspent, menu);
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