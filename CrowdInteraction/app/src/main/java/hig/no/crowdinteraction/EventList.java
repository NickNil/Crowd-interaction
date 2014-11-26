package hig.no.crowdinteraction;

import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




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
    JSONObject eventJSON = new JSONObject();
    JSONArray jsonArray = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);


        user = new User(getApplicationContext());
        post = new PostDataJSON (getApplicationContext());

        new EventListTask().execute();
       // test();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_list, menu);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
            getActionBar().setDisplayShowTitleEnabled(false); //remove title
        }
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
        if (id == R.id.Home) {
            //Intent i = new Intent(EventList.this, Home.class);
        }
        if (id == R.id.Leaderboard)
        {
            Intent i = new Intent(EventList.this, Leaderboards.class);
            startActivity(i);
        }
        if (id == R.id.LiveEvents)
        {
            Intent i = new Intent(EventList.this, LiveEventList.class);
            startActivity(i);
        }
        if (id == R.id.Settings) {
            //Intent i = new Intent(EventList.this, Settings.class);
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
            HttpPost httpPost = new HttpPost(SERVER_URL + "/api/live");
           // HttpPost httpPost = new HttpPost(SERVER_URL + "/api/events/1");
            // Log.i("URL", SERVER_URL + "/api/events");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            BasicNameValuePair pair = new BasicNameValuePair("api_key", SERVER_API_KEY);
            nameValuePairs.add(pair);
            response = post.sendJson(client, httpPost, nameValuePairs);

            if (response != null)
            {

                InputStream in = null; //Get the data in the entity
                try {

                    HttpEntity entity;
                    entity = response.getEntity();

                    in = entity.getContent();//response.getEntity().getContent(); //Get the data in the entity
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    Log.i("HTTP Status", Integer.toString(statusCode));

                    jsonString = post.inputStreamToString(in);
                    //jsonString = jsonString.replace("[", "");
                    //jsonString = jsonString.replace("]", "");
                   // jsonString = jsonString.replace("\\", "");
                    Log.i("LoginResponse", jsonString);

                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject event_data = null;

                if (jsonString != "")
                {
                    try {
                        JSONObject jsonObj = null;// new JSONObject(jsonString);
                        JSONArray jarray = new JSONArray(jsonString);
                        jsonObj = jarray.getJSONObject(0);
                        event_data = jsonObj;
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                return event_data;
            }
            else
            {
                return null;
            }



        }
        protected void onPostExecute(final JSONObject event_data)
        {

            if (event_data != null)
            {
                LinearLayout view = (LinearLayout) findViewById(R.id.topView);
                TextView event = null;

                eventJSON = event_data;

                try {
                    jsonArray = eventJSON.getJSONArray("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject temp = null;
                // Defined Array values to show in ListView

                int numberOfObjects = jsonArray.length();


                for (int i = 0; i < numberOfObjects; i++) {
                    try {
                        temp = jsonArray.getJSONObject(i);

                        event = new TextView(getApplicationContext());
                        String eventID = temp.getString("_id");
                        event.setTag(eventID);


                        event.setText(temp.getString("event_name"));

                        view.addView(event);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (event != null) {
                    event.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), VoteActivity.class);

                            Iterator<String> eventIterator;
                            eventIterator = eventJSON.keys();
                            JSONObject temp = null;

                            try {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    temp = jsonArray.getJSONObject(i);

                                    if (temp.getString("_id") == v.getTag())
                                    {
                                        intent.putExtra("name", temp.getString("event_name"));
                                        intent.putExtra("id", temp.getString("_id"));

                                        intent.putExtra("athleteNR", temp.getJSONObject("current_athlete").getString("number"));
                                        intent.putExtra("athlete", temp.getJSONObject("current_athlete").getString("name"));

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                        }
                    });
                }
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "no response from server",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void test()
    {
        NotificationManager mNotificationManager = null;
        NotificationCompat.Builder NotifyBuilder;

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
        // Sets an ID for the notification, so it can be updated*/

        NotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Test notification")
                .setContentText("Test")
                .setSmallIcon(R.drawable.ic_launcher);

       mNotificationManager.notify(
                1,
                NotifyBuilder.build());
    }
}
