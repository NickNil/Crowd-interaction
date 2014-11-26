package hig.no.crowdinteraction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Mimoza on 11/26/2014.
 */
public class Home extends Activity {

    User user;
    UserScore score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        user = new User(getApplicationContext());
        score = new UserScore(getApplicationContext());

        String [] firstnameLastname = user.GetName();
        TextView username = (TextView)findViewById(R.id.username);
        username.setText(firstnameLastname[0] + " " + firstnameLastname[1]);

        TextView scoreView = (TextView) findViewById(R.id.totalScore);
        score.sendJson(user.GetMongoId());
        scoreView.setText(user.GetHighscore());
    }
}