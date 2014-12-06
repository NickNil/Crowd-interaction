package hig.no.crowdinteraction;

import android.app.ActionBar;
import android.app.Activity;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

/**
 * Created by Nicklas on 11.11.2014.
 */
public class Leaderboards extends Activity{
    /**
     * Calls the LeaderboardJSON class in order to get leaderboard data from the server database
     * then displays this data in a listview using the LeaderboardItems adapter.
     * @param savedInstanceState The state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        ListView leaderboardList;

        String[] score;
        String[] position;
        String[] name;
        String[] nationality;
        ArrayList<Integer> natIcon = new ArrayList<Integer>();
        Integer[] intNatIcon;

        LeaderboardJSON json = new LeaderboardJSON(getApplicationContext());
        json.sendJson();

        System.out.println(json.responseError);
        if (json.responseError == true)
        {
            Toast.makeText(this, "No response from server", Toast.LENGTH_SHORT).show();
        }
        else {
            score = new String[json.points.size()];
            position = new String[json.position.size()];
            name = new String[json.userName.size()];
            nationality = new String[json.iocNationality.size()];
            intNatIcon = new Integer[json.isoNationality.size()];
            for (int i = 0; i < json.isoNationality.size(); i++) {
                score[i] = json.points.get(i);
                position[i] = json.position.get(i);
                name[i] = json.userName.get(i);
                nationality[i] = json.iocNationality.get(i);
                natIcon.add(getDrawable(this, json.isoNationality.get(i).toLowerCase()));
                intNatIcon[i] = natIcon.get(i);
            }

            LeaderboardItems adapter = new LeaderboardItems(this, intNatIcon, position, name, score, nationality);

            leaderboardList = (ListView) findViewById(R.id.leaderboard_list);
            leaderboardList.setAdapter(adapter);
        }
    }

    /**
     * Returns the id of a drawable file in order to dynamically load an image from
     * a drawable file.
     * @param context   the context of the application
     * @param name      the name of the relevant drawable file, without extension.
     * @return          the id of the relevant drawable file.
     */
    public static int getDrawable(Context context, String name)
    {
        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }

    /**
     * Inflates the options menu and adds items to the actionbar then removes everything that is
     * not custom actionbar buttons.
     * @param menu  the menu being added to the actionbar.
     * @return      returns true when the actionbar is completed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.leaderboard, menu);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
            getActionBar().setDisplayShowTitleEnabled(false); //remove title
        }
        return true;
    }

    /**
     * Handles what happens when a specific button on the actionbar is clicked.
     * @param item  the id of a the actionbar button that was clicked
     * @return      the onOptionmenuSelected(item) function of the superclass
     */
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
            User user = new User(getApplicationContext());
            user.logout();
            Intent intent;
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.Home) {
            Intent i = new Intent(Leaderboards.this, Home.class);
            startActivity(i);
        }
        if (id == R.id.Events)
        {
            Intent i = new Intent(Leaderboards.this, EventList.class);
            startActivity(i);
        }
        if (id == R.id.LiveEvents)
        {
            Intent i = new Intent(Leaderboards.this, LiveEventList.class);
            startActivity(i);
        }
        if (id == R.id.Map) {
            Intent i = new Intent(Leaderboards.this, EventMap.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}

