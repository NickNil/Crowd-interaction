package hig.no.crowdinteraction;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.List;

/**
 * Displayes a screen with information about a athlete and enable the user to vote on that athlete
 * Uses information that is passed along from live event list
 */
public class VoteActivity extends Activity {


    User user;
    PostDataJSON post;

    Button voteButton;// = (Button) findViewById(R.id.Votebutton);
    EditText scorePicker;// = (NumberPicker) findViewById(R.id.scorePicker);
    LinearLayout voteLayout;

    String SERVER_API_KEY = "G4zVKwwpEwsk20WEeLzqMNRt2A8Q3Lze";
    String SERVER_URL = "http://ci.harnys.net";

    String sportName;
    String eventType;
    String EventDate;
    String mongoID;
    String startingNumber;
    String athlete;
    String score;
    int EventIcon;
    int intNatIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        sportName = "ERROR!";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // remove the left caret
        actionBar.setDisplayShowHomeEnabled(true); // remove the icon
        getActionBar().setDisplayShowTitleEnabled(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            sportName = extras.getString("name");
            eventType = extras.getString("eventType");
            EventDate = extras.getString("event_date");
            mongoID = extras.getString("id");
            startingNumber = extras.getString("athleteNR");
            athlete = extras.getString("athlete");
            EventIcon = extras.getInt("EventIcon");
            intNatIcon = extras.getInt("NatIcon");
        }

        float Scoreinterval = 0f;
        int numScorce = 0;
        float curScore = 0;
        int size = 5;

        if (eventType.equals("figure_skating") )
        {
            numScorce = 13; //13
            Scoreinterval = 0.5f;
        }else if(eventType.equals("snowboard") )
        {
            numScorce = 21;
            Scoreinterval = 0.5f;
        }
        else if (eventType.equals("ski_jump") )
        {
            curScore = 15;
            numScorce = 11;
            Scoreinterval = 0.5f;
        }


        voteLayout  = new LinearLayout(this);
        LinearLayout scrollView = (LinearLayout)findViewById(R.id.votepick);

        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale

        //android recommendation
        int buttonSize = 60;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(buttonSize*scale),
                (int)(buttonSize*scale));

        params.setMargins((int)(2*scale), (int)(2*scale), (int)(2*scale), (int)(2*scale));

        for (int i =0; i < numScorce; i++)
        {
            voteButton = new Button(this);
            voteButton.setId(i);
            voteButton.setText(Float.toString(curScore));
            voteButton.setTextColor(Color.parseColor("#ffffff"));
            voteButton.setLayoutParams(params);

            if (i % size == 0)
            {
                voteLayout = new LinearLayout(this);
                voteLayout.setOrientation(LinearLayout.HORIZONTAL);
                scrollView.addView(voteLayout);
            }

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
            {
                voteButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button));
            }
            else {

                voteButton.setBackground(getResources().getDrawable(R.drawable.round_button));
            }

                voteButton.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick (View v)
                    {
                        int scoreid = v.getId();
                        Button temp = (Button)findViewById(scoreid);
                        score = temp.getText().toString();

                       /* Toast toast = Toast.makeText(getApplicationContext(), "Click " +score,
                                Toast.LENGTH_SHORT);
                        toast.show();*/
                        new VoteTask().execute();

                    }
                });
            voteLayout.addView(voteButton);
            curScore +=Scoreinterval;
        }



        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(sportName);

        text = (TextView) findViewById(R.id.lastname);
        text.setText(athlete);

        text = (TextView) findViewById(R.id.points);
        text.setText(startingNumber);

        user = new User(getApplicationContext());
        post = new PostDataJSON (getApplicationContext());

        ImageView flag = (ImageView)findViewById(R.id.flag);
        ImageView eventIco = (ImageView)findViewById(R.id.eventIco);

        flag.setImageResource(intNatIcon);
        eventIco.setImageResource(EventIcon);



        voteLayout.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v)
            {
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


    private class  VoteTask extends AsyncTask<Void, Void, String>
    {
        String jsonString = null;
        JSONObject eventJSON;
        @Override
        protected String doInBackground(Void... params)
        {
            String responsString = "";
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            HttpPost httpPost = new HttpPost(SERVER_URL + "/api/add_user_score");
            // Log.i("URL", SERVER_URL + "/api/events");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            BasicNameValuePair pair = new BasicNameValuePair("id", mongoID);
            nameValuePairs.add(pair);
            pair = new BasicNameValuePair("start_number", startingNumber);
            nameValuePairs.add(pair);
            pair = new BasicNameValuePair("user_mongo_id", user.GetMongoId());
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

                try {

                    JSONArray tempArray = new JSONArray(jsonString);
                    JSONObject tempObject = tempArray.getJSONObject(0);
                    tempObject = tempObject.getJSONObject("data");
                    responsString = tempObject.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "no response from server",
                        Toast.LENGTH_SHORT);
                toast.show();
            }


            return responsString;
        }
        protected void onPostExecute(String responsString)
        {
            if (responsString.equals("1"))
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Thank you for voting",
                        Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("dialog",true);
                startActivity(intent);
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Sorry, we did not get your vote",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

}
