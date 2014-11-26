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

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Nicklas on 11.11.2014.
 */
public class Leaderboards extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        String score, position, name, nationality;
        int textSize;

        ScrollView scrollView = new ScrollView(this);

        TableLayout tableLayout = new TableLayout(this);

        LeaderboardJSON json = new LeaderboardJSON();
        json.sendJson();

        for (int i = -1; i < json.userList.size(); i++)
        {
            if (i == -1)
            {
                score = "Scores";
                position = "Position";
                name = "Name";
                nationality = "Nationality";
                textSize = 15;
            }
            else
            {
                score = Integer.toString(json.userList.get(i).GetScore());
                position = Integer.toString(json.userList.get(i).GetPosition());
                name = json.userList.get(i).GetName()[0];// + " " + json.userList.get(i).GetName()[1]; //lastname
                nationality = json.userList.get(i).GetNationality();
                textSize = 18;
            }

            //System.out.println(score + " " + position + " " + name + " " + nationality);

            TableRow tableRow = new TableRow(this);

            TextView tvScore = new TextView(this);
            TextView tvNames = new TextView(this);
            TextView tvNat = new TextView(this);
            TextView tvPos = new TextView(this);

            tvScore.setTextSize(textSize);
            tvNames.setTextSize(textSize);
            tvNat.setTextSize(textSize);
            tvPos.setTextSize(textSize);
            //tableRow.LayoutParams trParams = new TableRow.LayoutParams();
            //trParams.column = 2;

            tvPos.setPadding(10, 10, 10, 10);
            tvPos.setGravity(Gravity.CENTER);
            tvScore.setPadding(10, 10, 10, 10);
            tvScore.setGravity(Gravity.CENTER);
            tvNat.setPadding(10, 10, 10, 10);
            tvNat.setGravity(Gravity.CENTER);
            tvNames.setPadding(10, 10, 10, 10);
            tvNames.setGravity(Gravity.CENTER);

            tvPos.setText(position);
            tableRow.addView(tvPos, 0);

            tvNames.setText(name);
            tableRow.addView(tvNames, 1);

            tvNat.setText(nationality);
            tableRow.addView(tvNat, 2);

            tvScore.setText(score);
            tableRow.addView(tvScore, 3);

            tableLayout.addView(tableRow);
            View v = new View(this);
            v.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, 1));
            v.setBackgroundColor(Color.rgb(51, 51, 51));
            tableLayout.addView(v);
        }

        scrollView.addView(tableLayout);

        setContentView(scrollView);
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
            //Intent i = new Intent(Leaderboards.this, Home.class);
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
        if (id == R.id.Settings) {
            //Intent i = new Intent(Leaderboards.this, Settings.class);
        }
        return super.onOptionsItemSelected(item);
    }
}

