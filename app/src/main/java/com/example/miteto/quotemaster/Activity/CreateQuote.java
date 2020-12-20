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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
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
public class CreateQuote extends Activity
{
    QuoteDTO quote;
    CarDTO car;

    EditText fNameView;
    EditText lNameView;
    EditText ageView;
    EditText claimsView;
    EditText licenceView;
    EditText penPointsView;
    EditText carIdView;
    EditText carMakeView;
    EditText carModelView;
    EditText carEngineView;


    private String URL = "http://cirx08.azurewebsites.net/Quote/";
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        progress = new ProgressDialog(this);
        progress.setTitle("Creating Quote");
        progress.setMessage("Creating...");

        fNameView = (EditText) findViewById(R.id.fName_text_create);

        lNameView = (EditText) findViewById(R.id.lName_text_create);

        ageView = (EditText) findViewById(R.id.age_text_create);

        claimsView = (EditText) findViewById(R.id.noClaims_text_create);

        licenceView= (EditText) findViewById(R.id.licence_text_create);

        penPointsView = (EditText) findViewById(R.id.penaltyPoints_text_create);

        carIdView = (EditText) findViewById(R.id.carReg_text_create);

        carMakeView = (EditText) findViewById(R.id.carMake_text_create);

        carModelView = (EditText) findViewById(R.id.carModel_text_create);

        carEngineView = (EditText) findViewById(R.id.carEngine_text_create);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createQuote(View view)
    {
        String fName = fNameView.getText().toString();
        String lName = lNameView.getText().toString();
        String age = ageView.getText().toString();
        int ageInt = Integer.parseInt(age);
        String claims = claimsView.getText().toString();
        int claimsInt = Integer.parseInt(claims);
        String licence = licenceView.getText().toString();
        String penaltyPoints = penPointsView.getText().toString();
        int penaltyInt = Integer.parseInt(penaltyPoints);
        String carReg = carIdView.getText().toString();

        car = new CarDTO(carReg, "", "", 0.0);
        quote = new QuoteDTO(car, fName, lName, ageInt, claimsInt,licence,penaltyInt);



        QuoteDTO newQuote = null;

        HttpEditQuoteTask quoteTask = new HttpEditQuoteTask();
        try {
            newQuote =  quoteTask.execute(quote).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("NEW QUOTE", newQuote.toString());
        Intent i = new Intent(this, QuoteDisplayActivity.class);
        i.putExtra("quote", newQuote);
        progress.dismiss();
        startActivity(i);
    }

    //TODO: Implement AsyncTask for HttpPOST  - create a new quote -
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

            URL += "?format=json";

            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(URL);

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
