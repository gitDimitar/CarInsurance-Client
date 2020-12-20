package com.example.miteto.quotemaster.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.miteto.quotemaster.DTO.CarDTO;
import com.example.miteto.quotemaster.DTO.QuoteDTO;
import com.example.miteto.quotemaster.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

/*
    Created by  Dimitar Papazikov,
                Jake Carville,
                Chris Collins.
 */

public class EditActivity extends Activity
{

    QuoteDTO quote;
    CarDTO car;
    private String URL = "http://cirx08.azurewebsites.net/Quote/";
    private ProgressDialog progress;
    EditText fNameView;
    EditText lNameView;
    EditText ageView;
    EditText claimsView;
    EditText licenceView;
    EditText penPointsView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        progress = new ProgressDialog(this);
        progress.setTitle("Editing Quote");
        progress.setMessage("Updating...");

        Intent i = getIntent();
        quote = (QuoteDTO) i.getParcelableExtra("quote");
        car = quote.getCar();

        fNameView = (EditText) findViewById(R.id.fName_text_edit);
        fNameView.setText(quote.getfName());

        lNameView = (EditText) findViewById(R.id.lName_text_edit);
        lNameView.setText(quote.getlName());

        ageView = (EditText) findViewById(R.id.age_text_edit);
        ageView.setText(Integer.toString(quote.getAge()));

        claimsView = (EditText) findViewById(R.id.noClaims_text_edit);
        claimsView.setText(Integer.toString(quote.getNoClaims()));

        licenceView = (EditText) findViewById(R.id.licence_text_edit);
        licenceView.setText(quote.getLicence());

        penPointsView = (EditText) findViewById(R.id.penaltyPoints_text_edit);
        penPointsView.setText(Integer.toString(quote.getPenaltyPoints()));

        EditText carIdView = (EditText) findViewById(R.id.carReg_text_edit);
        carIdView.setText(car.getId());

        EditText carMakeView = (EditText) findViewById(R.id.carMake_text_edit);
        carMakeView.setText(car.getMake());

        EditText carModelView = (EditText) findViewById(R.id.carModel_text_edit);
        carModelView.setText(car.getModel());

        EditText carEngineView = (EditText) findViewById(R.id.carEngine_text_edit);
        carEngineView.setText(car.getEngineSize() + "");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

    public void getQuote(View view)
    {
        String fName = fNameView.getText().toString();
        String lName = lNameView.getText().toString();
        int age = Integer.parseInt(ageView.getText().toString());
        int noClaims = Integer.parseInt(claimsView.getText().toString());
        String licence = licenceView.getText().toString();
        int penPoints = Integer.parseInt(penPointsView.getText().toString());

        progress.show();
        QuoteDTO newQuote = null;
        QuoteDTO toBeEditedQuote = new QuoteDTO(car, fName, lName, age, noClaims, licence, penPoints);
        HttpEditQuoteTask quoteTask = new HttpEditQuoteTask();
        try {
            newQuote =  quoteTask.execute(toBeEditedQuote).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("NEW QUOTE", newQuote.toString());
        Intent i = new Intent(this, QuoteDisplayActivity.class);
        i.putExtra("quote", newQuote);
        startActivity(i);

    }

    //TODO: CHANGE HttpGET to HttpPUT and readers to writers - reformat code bellow

    public class HttpEditQuoteTask extends AsyncTask<QuoteDTO , Void, QuoteDTO>
    {

        @Override
        protected void onPreExecute()
        {
            progress.show();
            super.onPreExecute();
        }

        @Override
        protected QuoteDTO doInBackground(QuoteDTO... params)
        {
            QuoteDTO responseQuote = null;
            CarDTO reponseCar = null;

            //TODO: URL modification

            QuoteDTO parQuote = params[0];
            CarDTO parCar = parQuote.getCar();

            URL += quote.getId() + "?format=json";

            HttpClient client = new DefaultHttpClient();
            HttpPut request = new HttpPut(URL);

            try
            {
                Log.i("QUOTE PARAM", parQuote.toString());
                JSONStringer jsonCar = new JSONStringer()
                        .object()
                        .key("id").value(parCar.getId())
                        .key("make").value(parCar.getMake())
                        .key("model").value(parCar.getModel())
                        .key("engineSize").value(parCar.getEngineSize())
                        .endObject();

                JSONStringer jsonQuote = new JSONStringer()
                        .object()
                        .key("id").value(quote.getId())
                        .key("firstName").value(parQuote.getfName())
                        .key("lastName").value(parQuote.getlName())
                        .key("age").value(parQuote.getAge())
                        .key("licenceType").value(parQuote.getLicence())
                        .key("noClaimsBonus").value(parQuote.getNoClaims())
                        .key("penaltyPoints").value(parQuote.getPenaltyPoints())
                        .key("car").value(parCar.getId())
                        .endObject();



                StringEntity entity = new StringEntity(jsonQuote.toString());
                entity.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
                entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
                request.setEntity(entity);


                HttpResponse response = client.execute(request);
                int responseCode = response.getStatusLine().getStatusCode();

                if(responseCode != 200)
                {
                    Log.i("RESPONSE CODE:", responseCode + "");
                    return null;
                }

                InputStream stream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null)
                {
                    builder.append(line);
                }

                String data = builder.toString();
                Log.i("JSON DATA:", data);

                JSONObject quoteInfo = new JSONObject(data);
                JSONObject carInfo = quoteInfo.getJSONObject("car");

                reponseCar = new CarDTO(carInfo.getString("id"),carInfo.getString("make"),carInfo.getString("model"),carInfo.getDouble("engineSize"));
                responseQuote = new QuoteDTO(reponseCar, quoteInfo.getInt("id"), quoteInfo.getString("firstName"),quoteInfo.getString("lastName"),quoteInfo.getInt("age"),
                        quoteInfo.getInt("noClaimsBonus"),quoteInfo.getString("licenceType"),quoteInfo.getInt("penaltyPoints"),
                        quoteInfo.getDouble("thirdPartyCover"),quoteInfo.getDouble("monthlyThirdPartyCover"),quoteInfo.getDouble("fullyCompCover"),
                        quoteInfo.getDouble("monthlyFullyCompCover"));



            }
            catch(ClientProtocolException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            return responseQuote;
        }

    }
}
