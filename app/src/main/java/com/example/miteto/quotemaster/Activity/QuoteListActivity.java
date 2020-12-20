package com.example.miteto.quotemaster.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.miteto.quotemaster.DTO.CarDTO;
import com.example.miteto.quotemaster.DTO.QuoteDTO;
import com.example.miteto.quotemaster.R;

import java.util.List;
/*
    Created by  Dimitar Papazikov,
                Jake Carville,
                Chris Collins.
 */
public class QuoteListActivity extends Activity
{
    private List<QuoteDTO> quoteDTOs;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_list);

        Intent i = getIntent();
        quoteDTOs = i.getParcelableArrayListExtra("quotes");
        Log.i("QUOTE IN LIST",quoteDTOs.get(0).toString());

        populateList();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quote_list, menu);
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

    private void populateList()
    {
        ArrayAdapter<QuoteDTO> adapter = new MyListAdapter();
        list = (ListView) findViewById(R.id.quote_ListView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.i("CLICK REGISTERED:", "Click!");
                //progress.show();
                Intent i = new Intent(QuoteListActivity.this, QuoteDisplayActivity.class);
                i.putExtra("quote", quoteDTOs.get(position));
                startActivity(i);



            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<QuoteDTO>
    {
        public MyListAdapter()
        {
            super(QuoteListActivity.this, R.layout.item_view, quoteDTOs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View itemView = convertView;
            //make sure there is a view to work with
            if(itemView == null)
            {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            //find place to work with
            QuoteDTO cur = quoteDTOs.get(position);
            CarDTO curCar = cur.getCar();

            //fill the view
            TextView idView = (TextView) itemView.findViewById(R.id.id_txt);
            idView.setText(Integer.toString(cur.getId()));

            TextView lNameView = (TextView) itemView.findViewById(R.id.lName_txt);
            lNameView.setText(cur.getlName());

            TextView carRegView = (TextView) itemView.findViewById(R.id.reg_txt);
            carRegView.setText(curCar.getId());

            return itemView;
        }

        @Override
        public boolean isEnabled(int position)
        {
            return true;
        }
    }
}
