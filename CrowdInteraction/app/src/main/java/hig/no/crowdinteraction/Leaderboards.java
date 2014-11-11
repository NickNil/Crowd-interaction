package hig.no.crowdinteraction;

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

        List<Integer> scores = new ArrayList<Integer>();
        String[] names = new String[100];
        scores = getRandScores();
        names = getNames(100);

        Collections.sort(scores);
        Collections.reverse(scores);

        ScrollView scrollView = new ScrollView(this);

        TableLayout tableLayout = new TableLayout(this);

        for (int i = -1; i < scores.size(); i++)
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
                score = scores.get(i).toString();
                position = Integer.toString(i+1);
                name = names[i];
                nationality = getNationality();
                textSize = 25;
            }

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

    private List<Integer> getRandScores() {
        List<Integer> ints = new ArrayList<Integer>();
        for(int i = 0;i < 100; i++)
        {
            Random rand = new Random();
            int randomNum = rand.nextInt((10000 - 10) + 1) + 10;
            ints.add(randomNum);
        }
        return ints;
    }

    public static String getNationality()
    {
        String nationality;
        Random rand = new Random();
        int randomNum = rand.nextInt((10 - 1) + 1) + 1;

        switch(randomNum)
        {
            case 1:  nationality = "NOR";
                break;
            case 2:  nationality = "SWE";
                break;
            case 3:  nationality = "GBR";
                break;
            case 4:  nationality = "DEU";
                break;
            case 5:  nationality = "USA";
                break;
            case 6:  nationality = "ISL";
                break;
            case 7:  nationality = "FIN";
                break;
            case 8:  nationality = "DNK";
                break;
            case 9:  nationality = "ATA";
                break;
            case 10: nationality = "AUS";
                break;
            default: nationality = "DMB";
                break;
        }
        return nationality;
    }

    public static String[] getNames(int numberOfWords)
    {
		/*File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard, "names.txt");


	    BufferedReader reader = new BufferedReader(new FileReader(file));*/
        String namesTxt = "Lan " +
                "Katrina " +
                "Raisa " +
                "Devona " +
                "Glenna " +
                "Angelica " +
                "Naida " +
                "Chelsey " +
                "Melani " +
                "Abbey " +
                "Huey " +
                "Mathilda " +
                "Prudence " +
                "Cassi " +
                "Major " +
                "Jacquelin " +
                "Cortez " +
                "Cassondra " +
                "Vella " +
                "Alison " +
                "Courtney " +
                "Kurtis " +
                "Milan " +
                "Chanel " +
                "Kathlyn " +
                "Marquerite " +
                "Afton " +
                "Saturnina " +
                "Marion " +
                "Giovanna " +
                "Leora " +
                "Mira " +
                "Eddie " +
                "Dale " +
                "Dorotha " +
                "Micah " +
                "Kendrick " +
                "Merry " +
                "Rodrick " +
                "Piedad " +
                "Kacey " +
                "Eugenie " +
                "Rolande " +
                "Tatiana " +
                "Kathi " +
                "Darcey " +
                "Allan " +
                "Eboni " +
                "Lachelle " +
                "Alayna " +
                "Mohammed " +
                "Jackelyn " +
                "Dominica " +
                "Candra " +
                "Caridad " +
                "Jeanna " +
                "Nam " +
                "Jeanice " +
                "Sherilyn " +
                "Anja " +
                "Lynsey " +
                "Kami " +
                "Lynette " +
                "Kandi " +
                "Elaine " +
                "Micah " +
                "Adriana " +
                "Carin " +
                "Miki " +
                "Granville " +
                "Felix " +
                "Irwin " +
                "Annabelle " +
                "Cassidy " +
                "Audra " +
                "Jeneva " +
                "Racquel " +
                "Darlene " +
                "Sherril " +
                "Kazuko " +
                "Laurel " +
                "Esther " +
                "Dixie " +
                "Elfreda " +
                "Jerlene " +
                "Ivonne " +
                "Lucas " +
                "Juan " +
                "Tijuana " +
                "Starla " +
                "Chong " +
                "Vince " +
                "Tawnya " +
                "Crissy " +
                "Trula " +
                "Christena " +
                "Hunter " +
                "Fransisca " +
                "Shanell " +
                "Vi";
        String[] nameArray = namesTxt.split(" ");

        return nameArray;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.leaderboard, menu);
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

