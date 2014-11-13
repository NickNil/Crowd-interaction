package hig.no.crowdinteraction;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;


public class VoteActivity extends Activity {

    User user;
    PostDataJSON post;

    Button voteButton = (Button) findViewById(R.id.Votebutton);
    NumberPicker scorePicker = (NumberPicker) findViewById(R.id.scorePicker);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);


        user = new User(getApplicationContext());
        post = new PostDataJSON (getApplicationContext());

        voteButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v)
            {
               int score = scorePicker.getValue();
                post.vote(Integer.toString(score));
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

}
