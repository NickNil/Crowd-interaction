package hig.no.crowdinteraction;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;




/*
* the listview is an adaption of this
* http://androidexample.com/Create_A_Simple_Listview_-_Android_Example/index.php?view=article_discription&aid=65&aaid=90
*/

public class EventList extends Activity
{
    String SENDER_ID = "914623768180";
    String SERVER_API_KEY = "G4zVKwwpEwsk20WEeLzqMNRt2A8Q3Lze";
    String SERVER_URL = "http://ci.harnys.net";

    User user;
    ListView listView ;
    PostDataJSON post;
    Intent intent;
    JSONObject eventJSON = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);


        user = new User(getApplicationContext());
        intent = new Intent(this, VoteActivity.class);
        post = new PostDataJSON (getApplicationContext());

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.eventList);


        new EventListTask().execute();
        JSONObject temp = null;
        // Defined Array values to show in ListView
        Iterator<String> i;
        i = eventJSON.keys();
       try
       {
           temp = eventJSON.getJSONObject(i.next());
       } catch (JSONException e)
       {
           e.printStackTrace();
       }
        eventJSON.length();
        String[] values = new String[eventJSON.length()];

        try {
            values[0] =  temp.getString("event_type");   //jsonObj.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

       /* String[] values = new String[]
                { "Event 1",
                "Event 2",
                "Event 3",
                "Event 4",
                "Event 5",};/*

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



    private class  EventListTask extends AsyncTask<Void, Void, JSONObject>
    {

        @Override
        protected JSONObject doInBackground(Void... params)
        {
            String jsonString = null;


            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            HttpPost httpPost = new HttpPost(SERVER_URL + "/api/events");
            // Log.i("URL", SERVER_URL + "/api/events");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            BasicNameValuePair pair = new BasicNameValuePair("api_key", SERVER_API_KEY);
            nameValuePairs.add(pair);
            response = post.sendJson(client, httpPost, nameValuePairs);

            if (response != null)
            {

                InputStream in = null; //Get the data in the entity
                try {
                    in = response.getEntity().getContent();

                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    Log.i("HTTP Status", Integer.toString(statusCode));
                    Log.i("Response", post.inputStreamToString(in));

                    jsonString = post.inputStreamToString(in);
                    jsonString = jsonString.replace("[", "");
                    jsonString = jsonString.replace("]", "");
                    Log.i("LoginResponse", jsonString);

                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject event_data = null;

                try {
                    JSONObject jsonObj = new JSONObject(jsonString);
                    jsonObj = jsonObj.getJSONObject("data");
                    event_data  = jsonObj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return event_data;

            }

            return null;
        }
        protected void onPostExecute(JSONObject event_data)
        {
            eventJSON = event_data;
        }
    }

}
