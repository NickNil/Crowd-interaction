package hig.no.crowdinteraction;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
}