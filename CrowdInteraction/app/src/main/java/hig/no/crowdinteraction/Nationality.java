package hig.no.crowdinteraction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Mimoza on 11/15/2014.
 * Nationality class gets the list of the nationalities with their iso codes and country names from the SQLite database
 * and uses the iso code to set the flags in the nationality list and the country name for each flag so that users can pick
 * their nationalities from the list
 */
public class Nationality extends Activity {

    final ListView[] list = new ListView[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_list);



        MySQLiteHelper db = new MySQLiteHelper(this);
        final List<IOCandISOcodes> countryList = db.getAllCountries();

        final String [] countries = new String[countryList.size()];
        String [] uri = new String[countryList.size()];
        Integer [] imageResource = new Integer[uri.length];

        for (int i=0; i<countryList.size(); i++){
            countries[i] = countryList.get(i).toString();
            uri[i] = "drawable/" + countryList.get(i).getIso().toLowerCase();

            if(uri[i].equals("drawable/do")){
                uri[i] = "drawable/do2";
            }

            if(uri[i].equals("drawable/bq") || uri[i].equals("drawable/gf")|| uri[i].equals("drawable/gp")||uri[i].equals("drawable/re")||uri[i].equals("drawable/pm")){
                uri[i] = "drawable/_olympics";
            }


            imageResource[i] = getResources().getIdentifier(uri[i],null,getPackageName());
            //Log.i("iso",imageResource[i].toString());
        }


        CustomList adapter = new
                CustomList(Nationality.this, countries, imageResource);
        list[0] =(ListView)findViewById(android.R.id.list);
        list[0].setAdapter(adapter);
        list[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(Nationality.this, "You Clicked at " + countries[+position], Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",countryList.get(position).toString());
                returnIntent.putExtra("ioc", countryList.get(position).getIoc());
                returnIntent.putExtra("iso", countryList.get(position).getIso());
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
