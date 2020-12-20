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
import android.widget.Toast;

import com.example.miteto.quotemaster.DTO.CarDTO;
import com.example.miteto.quotemaster.DTO.QuoteDTO;
import com.example.miteto.quotemaster.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
    Created by  Dimitar Papazikov,
                Jake Carville,
                Chris Collins.
 */

public class MainActivity extends Activity {

    private QuoteDTO quote;
    private CarDTO car;

    //TODO: URL Insertion + adjustments
        private String URL = "http://cirx08.azurewebsites.net/Quote/";

    private ProgressDialog progress;

    EditText editText1;
    EditText editText2;

    String fName = "";
    String lName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Retrieving quote...");
        progress.hide();

        editText1 = (EditText) findViewById(R.id.fName_text);
        editText2 = (EditText) findViewById(R.id.lName_text);

        /*
        Intent i = getIntent();
        if(i != null)
        {
            fName = i.getStringExtra("fName");
            lName = i.getStringExtra("lName");
        }
        **/
        if(!fName.equals("") && !lName.equals(""))
        {
            editText1.setText(fName);
            editText2.setText(lName);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create) {
            Intent i = new Intent(this, CreateQuote.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }




    public void getQuote(View view)
    {

        fName = String.valueOf(editText1.getText());
        lName = String.valueOf(editText2.getText());

        if(!fName.equals("") && !lName.equals(""))
        {
            HttpGetQuoteTask quoteTask = new HttpGetQuoteTask();
            quoteTask.execute(fName,lName);
            //URL += fName + "/" + lName;

        }
        else
        {
            Toast.makeText(this, "Enter First name AND Last name", Toast.LENGTH_LONG).show();
        }

    }

    public class HttpGetQuoteTask extends AsyncTask<String , Void, QuoteDTO>
    {
        String firstName;
        String lastName;

        @Override
        protected void onPreExecute()
        {
           progress.show();
           super.onPreExecute();
        }


        @Override
        protected QuoteDTO doInBackground(String... params)
        {
            firstName = params[0];
            lastName = params[1];
            ArrayList<QuoteDTO> quoteList = new ArrayList<QuoteDTO>();

            URL += firstName + "%20" + lastName + "?format=json";

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(URL);

            try
            {
                HttpResponse response = client.execute(request);
                int responseCode = response.getStatusLine().getStatusCode();

                if(responseCode != 200)
                {
                    Log.i("RESPONSE CODE:", responseCode + "");
                    Log.i("URL:", URL );
                    return null;
                }
                Log.i("RESPONSE CODE:", responseCode + "");
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

                JSONArray quotes = new JSONArray(data);


            for(int i = 0; i < quotes.length(); i++)
            {
                 JSONObject quoteInfo = quotes.getJSONObject(i);
                 JSONObject carInfo = quoteInfo.getJSONObject("car");

                 car = new CarDTO(carInfo.getString("id"), carInfo.getString("make"), carInfo.getString("model"), carInfo.getDouble("engineSize"));
                 quote = new QuoteDTO(car, quoteInfo.getInt("id"), quoteInfo.getString("firstName"), quoteInfo.getString("lastName"), quoteInfo.getInt("age"),
                         quoteInfo.getInt("noClaimsBonus"), quoteInfo.getString("licenceType"), quoteInfo.getInt("penaltyPoints"),
                         quoteInfo.getDouble("thirdPartyCover"), quoteInfo.getDouble("monthlyThirdPartyCover"), quoteInfo.getDouble("fullyCompCover"),
                         quoteInfo.getDouble("monthlyFullyCompCover"));

                 Log.i("CAR DATA:", car.getId() + car.getMake());
                 Log.i("QUOTE DATA:", quote.toString());


                 quoteList.add(quote);
            }
                Intent intent = new Intent(MainActivity.this,QuoteListActivity.class);
                intent.putExtra("quotes", quoteList);
                startActivity(intent);

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
            return quote;
        }

        @Override
        protected void onPostExecute(QuoteDTO result)
        {

            progress.hide();
            progress.dismiss();
            super.onPreExecute();
        }
    }
}
