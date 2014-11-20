package hig.no.crowdinteraction;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class VoteActivity extends Activity {

    User user;
    PostDataJSON post;

    Button voteButton;// = (Button) findViewById(R.id.Votebutton);
    EditText scorePicker;// = (NumberPicker) findViewById(R.id.scorePicker);

    String SERVER_API_KEY = "G4zVKwwpEwsk20WEeLzqMNRt2A8Q3Lze";
    String SERVER_URL = "http://ci.harnys.net";

    String sportName;
    String EventDate;
    String mongoID;
    String startingNumber;
    String athlete;
    String score;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        sportName = "ERROR!";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        voteButton = (Button) findViewById(R.id.Votebutton);
        scorePicker = (EditText) findViewById(R.id.scorePicker);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            sportName = extras.getString("name");
            EventDate = extras.getString("event_date");
            mongoID = extras.getString("id");
            startingNumber = extras.getString("athleteNR");
            athlete = extras.getString("athlete");
        }

        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(sportName);

        text = (TextView) findViewById(R.id.textView3);
        text.setText(athlete);

        text = (TextView) findViewById(R.id.textView4);
        text.setText(startingNumber);

        user = new User(getApplicationContext());
        post = new PostDataJSON (getApplicationContext());

        voteButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v)
            {

                score = scorePicker.getText().toString();
                Toast toast = Toast.makeText(getApplicationContext(), "Click",
                        Toast.LENGTH_SHORT);
                toast.show();
                new VoteTask().execute();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class  VoteTask extends AsyncTask<Void, Void, Void>
    {
        JSONObject eventJSON;
        @Override
        protected Void doInBackground(Void... params)
        {
            String jsonString = null;


            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            HttpPost httpPost = new HttpPost(SERVER_URL + "/api/add_user_score");
            // Log.i("URL", SERVER_URL + "/api/events");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            BasicNameValuePair pair = new BasicNameValuePair("id", mongoID);
            nameValuePairs.add(pair);
            pair = new BasicNameValuePair("start_number", "1");
            nameValuePairs.add(pair);
            pair = new BasicNameValuePair("phone_number", user.GetPhoneNumber());
            nameValuePairs.add(pair);
            pair = new BasicNameValuePair("platform", "android");
            nameValuePairs.add(pair);
            pair = new BasicNameValuePair("score", score);
            nameValuePairs.add(pair);
            pair = new BasicNameValuePair("regid", user.GetGmcId());
            nameValuePairs.add(pair);
            pair = new BasicNameValuePair("api_key", SERVER_API_KEY);
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
                    Log.i("LoginResponse", jsonString);

                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "no response from server",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
            return null;
        }
        protected void onPostExecute(final JSONObject event_data)
        {

        }
    }

}
