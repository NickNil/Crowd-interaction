package hig.no.crowdinteraction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Map;
import java.util.Set;


public class EventList extends Activity
{

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        user = new User(getApplicationContext());

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
        if (id == R.id.LiveEvents)
        {
            Intent i = new Intent(EventList.this, LiveEventList.class);
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
