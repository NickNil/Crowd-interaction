package hig.no.crowdinteraction;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mimoza on 11/26/2014.
 */
public class Home extends Activity {

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        user = new User(getApplicationContext());

        String [] firstnameLastname = user.GetName();
        TextView username = (TextView)findViewById(R.id.username);
        username.setText(firstnameLastname[0] + " " + firstnameLastname[1]);

        TextView scoreView = (TextView) findViewById(R.id.totalScore);

        scoreView.setText(user.GetHighscore());

        String uri ="@drawable/"+user.GetIso().toLowerCase();
        Log.i("uri", uri);
        Integer imageResource = getResources().getIdentifier(uri,null,getPackageName());
        ImageView flag = (ImageView)findViewById(R.id.flag);
        flag.setImageResource(imageResource);

        TextView country = (TextView)findViewById(R.id.country);
        country.setText(user.GetIoc());
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
}