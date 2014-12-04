package hig.no.crowdinteraction;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * Created by Mimoza on 11/26/2014.
 * Sets the personalized home screen (with user name, nationality flag and highscore) of the application that the user
 * sees after he/she logs in
 */
public class Home extends Activity {

    User user;
    PostDataJSON post;
    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        user = new User(getApplicationContext());
        post = new PostDataJSON(getApplicationContext());

        String [] firstnameLastname = user.GetName();
        TextView username = (TextView)findViewById(R.id.username);
        username.setText(firstnameLastname[0] + " " + firstnameLastname[1]);


        scoreView = (TextView) findViewById(R.id.totalScore);
        scoreView.setText(user.GetHighscore());


        String uri ="@drawable/"+user.GetIso().toLowerCase();
        Log.i("uri", uri);
        Integer imageResource = getResources().getIdentifier(uri,null,getPackageName());
        ImageView flag = (ImageView)findViewById(R.id.flag);
        flag.setImageResource(imageResource);

        TextView country = (TextView)findViewById(R.id.country);
        country.setText(user.GetIoc());

        //Log.i("mongoID", user.GetMongoId());
        new HomeTask().execute();

        boolean popup = false;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            popup = extras.getBoolean("dialog");
            if (popup)
            {
                 //scorecPopup();
            }

        }

        //score.sendJson(user.GetMongoId());
        //scoreView.setText(user.GetHighscore());
        //Log.i("user score",user.GetHighscore());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
    public boolean onOptionsItemSelected(MenuItem item) {
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
        if (id == R.id.LiveEvents) {
            Intent i = new Intent(Home.this, LiveEventList.class);
            startActivity(i);
        }
        if (id == R.id.Leaderboard)
        {
            Intent i = new Intent(Home.this, Leaderboards.class);
            startActivity(i);
        }
        if (id == R.id.Events)
        {
            Intent i = new Intent(Home.this, EventList.class);
            startActivity(i);
        }
        if (id == R.id.Map) {
            Intent i = new Intent(Home.this, EventMap.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Class that is used to check the score of the user whenever he scores additional points and his highscore changes
     */
   private class HomeTask extends AsyncTask<Void, Void, JSONObject>{

       String SERVER_URL = "http://ci.harnys.net";


       @Override
       protected JSONObject doInBackground(Void... params) {

           HttpClient client = new DefaultHttpClient();
           HttpResponse response;

           JSONObject data = null;

           try {

               Log.i("url", (SERVER_URL + "/api/userscore/"+user.GetMongoId()).toString());
               HttpPost httpPost = new HttpPost(SERVER_URL + "/api/userscore/"+user.GetMongoId());


               //response = client.execute(post);
               response = post.sendJson(client, httpPost);

               if (response != null) {

                   HttpEntity entity;
                   entity = response.getEntity();

                   InputStream in = entity.getContent(); //Get the data in the entity
                   StatusLine statusLine = response.getStatusLine();
                   int statusCode = statusLine.getStatusCode();
                   Log.i("HTTP Status", Integer.toString(statusCode));

                   String jsonString = post.inputStreamToString(in);
                   in.close();

                   jsonString = jsonString.replaceFirst(Pattern.quote("["), "");

                   JSONObject jsonObj = new JSONObject(jsonString);
                   data = jsonObj;

                   //Log.i("Response2", inputStreamToString(in));*/

               }

           } catch (Exception e) {
               e.printStackTrace();
           }
           return data;
       }

       @Override
       protected void onPostExecute(JSONObject jsonObject) {
           super.onPostExecute(jsonObject);

           JSONObject data = null;
           String highscore = user.GetHighscore();
           if (jsonObject != null) {
               try {

                   data = jsonObject.getJSONObject("data");
                   Log.i("data", data.toString());
                   highscore = data.getString("highscore");
                   user.SetHighscore(highscore);

                   scoreView.setText(highscore);
                   Log.i("user score from async task",user.GetHighscore());

               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
           else
           {
               Toast toast = Toast.makeText(getApplicationContext(), "Opsi! Something is WRONG! Please check your internet connection and try again",
                       Toast.LENGTH_SHORT);
               toast.show();
           }
       }

   }
}

