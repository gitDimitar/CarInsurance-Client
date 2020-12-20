package com.example.miteto.quotemaster.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.miteto.quotemaster.DTO.CarDTO;
import com.example.miteto.quotemaster.DTO.QuoteDTO;
import com.example.miteto.quotemaster.R;

/*
    Created by  Dimitar Papazikov,
                Jake Carville,
                Chris Collins.
 */

public class QuoteDisplayActivity extends Activity {

    QuoteDTO quote;
    CarDTO car;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_display);
        Intent i = getIntent();
        quote = (QuoteDTO) i.getExtras().getParcelable("quote");
        Log.i("QUOTEDISPLAYACTIVITY:",  "Inside Activity!");
        Log.i("QUOTE DATA IN DISPLAY:",  quote.toString());
        car = (CarDTO) quote.getCar();

        TextView idView = (TextView) findViewById(R.id.id_text_display);
        idView.setText(Integer.toString(quote.getId()));

        TextView fNameView = (TextView) findViewById(R.id.fName_text_display);
        fNameView.setText(quote.getfName());

        TextView lNameView = (TextView) findViewById(R.id.lName_text_display);
        lNameView.setText(quote.getlName());

        TextView ageView = (TextView) findViewById(R.id.age_text_display);
        ageView.setText(Integer.toString(quote.getAge()));

        TextView claimsView = (TextView) findViewById(R.id.noClaims_text_display);
        claimsView.setText(Integer.toString(quote.getNoClaims()));

        TextView licenceView = (TextView) findViewById(R.id.licence_text_display);
        licenceView.setText(quote.getLicence());

        TextView penPointsView = (TextView) findViewById(R.id.penaltyPoints_text_display);
        penPointsView.setText(Integer.toString(quote.getPenaltyPoints()));

        TextView carIdView = (TextView) findViewById(R.id.carReg_text_display);
        carIdView.setText(car.getId());

        TextView carMakeView = (TextView) findViewById(R.id.carMake_text_display);
        carMakeView.setText(car.getMake());

        TextView carModelView = (TextView) findViewById(R.id.carModel_text_display);
        carModelView.setText(car.getModel());

        TextView carEngineView = (TextView) findViewById(R.id.carEngine_text_display);
        carEngineView.setText(car.getEngineSize() + "");

        TextView tpPriceView = (TextView) findViewById(R.id.tp_full_text_display);
        tpPriceView.setText(quote.getTpPrice() + "");

        TextView tpMonthlyPriceView = (TextView) findViewById(R.id.tp_monthly_text_display);
        tpMonthlyPriceView.setText(quote.getTpMonthlyPrice() + "");

        TextView fullPriceView = (TextView) findViewById(R.id.full_text_display);
        fullPriceView.setText(quote.getFullPrice() + "");

        TextView fullMonthlyPriceView = (TextView) findViewById(R.id.monthly_text_display);
        fullMonthlyPriceView.setText(quote.getFullMonthlyPrice() + "");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quote_display, menu);
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
        else if(id == R.id.action_edit)
        {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("quote", quote);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
