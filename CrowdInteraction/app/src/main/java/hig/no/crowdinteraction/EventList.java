package hig.no.crowdinteraction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;




/*
* the listview is an adaption of this
* http://androidexample.com/Create_A_Simple_Listview_-_Android_Example/index.php?view=article_discription&aid=65&aaid=90
*/

public class EventList extends Activity
{

    User user;
    ListView listView ;
    PostDataJSON post;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);


 //     user = new User(getApplicationContext());
        intent = new Intent(this, VoteActivity.class);
        post = new PostDataJSON (getApplicationContext());

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.eventList);


       JSONObject eventJSON =  post.eventlist();
        JSONObject temp = null;
        // Defined Array values to show in ListView

      /* try {
            temp = eventJSON.getJSONObject("event_date");
       } catch (JSONException e) {
           e.printStackTrace();
       }
        eventJSON.length();
        String[] values = new String[eventJSON.length()];

        try {
            values[0] =  temp.getString("event_type");   //jsonObj.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        String[] values = new String[]
                { "Event 1",
                "Event 2",
                "Event 3",
                "Event 4",
                "Event 5",};

        /* Define a new Adapter
        * First parameter - Context
        * Second parameter - Layout for the row
        * Third parameter - ID of the TextView to which the data is written
        * Forth - the Array of data */

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue = (String)listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
               // startActivity(intent);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.Logout) {
            user.logout();
            Intent intent;
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.Leaderboard)
        {
            Intent i = new Intent(EventList.this, Leaderboards.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    public static void populateEventList(Intent events)
    {
        Map <String, String> eventMap;
        Bundle bundle;
        Set <String> keys;
        String [] eventKeys;


        bundle = events.getExtras();
        keys = bundle.keySet();
        eventKeys = new String[keys.size()];
        eventKeys =  keys.toArray(eventKeys);



    }
}
