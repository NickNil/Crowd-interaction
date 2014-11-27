package hig.no.crowdinteraction;

import android.app.ActionBar;
import android.app.Activity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nicklas on 11.11.2014.
 */
public class Leaderboards extends Activity{
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

        ScrollView scrollView = new ScrollView(this);

        TableLayout tableLayout = new TableLayout(this);

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

    public static int getDrawable(Context context, String name)
    {
        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
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

