package hig.no.crowdinteraction;

import android.app.Activity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Nicklas on 20.11.2014.
 */
public class LiveEventList extends Activity{

    ListView liveEventList;
    String[] eventName;
    String[] Mongoid;
    String[] athleteName;
    String[] iocNationality;
    String[] number;
    ArrayList<Integer> eventIcon = new ArrayList<Integer>();
    ArrayList<Integer> natIcon = new ArrayList<Integer>();
    Integer[] intEventIcon;
    Integer[] intNatIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_event_list);

        LiveEventListJSON json = new LiveEventListJSON();
        json.sendJson();

        //creating string arrays for the adapter.
        eventName = new String[json.eventName.size()];
        Mongoid = new String[json.mongoID.size()];
        athleteName = new String[json.athleteName.size()];
        iocNationality = new String[json.iocNationality.size()];
        number = new String[json.number.size()];
        intEventIcon = new Integer[json.eventType.size()];
        intNatIcon = new Integer[json.isoNationality.size()];

        for(int i = 0; i < json.isoNationality.size(); i++)
        {
            natIcon.add(getDrawable(this, json.isoNationality.get(i).toLowerCase()));
            eventIcon.add(getDrawable(this, json.eventType.get(i)));
            eventName[i] = json.eventName.get(i);
            Mongoid[i] = json.mongoID.get(i);
            athleteName[i] = json.athleteName.get(i);
            iocNationality[i] = json.iocNationality.get(i);
            number[i] = json.number.get(i);
            intEventIcon[i] = eventIcon.get(i);
            intNatIcon[i] = natIcon.get(i);
        }
        System.out.println(json.isoNationality.get(0));

        LiveEventListItems adapter = new LiveEventListItems(this, Mongoid, eventName, iocNationality, number,
                athleteName, intNatIcon, intEventIcon);

        liveEventList = (ListView)findViewById(R.id.liveEventList);
        liveEventList.setAdapter(adapter);

        liveEventList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), VoteActivity.class);

                intent.putExtra("name", eventName[position]);
                intent.putExtra("id", Mongoid[position]);
                intent.putExtra("athleteNR", number[position]);
                intent.putExtra("athlete", athleteName[position]);
                intent.putExtra("EventIcon",intEventIcon[position]);
                intent.putExtra("NatIcon",intNatIcon[position]);

                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.live_event_list, menu);
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

    public static int getDrawable(Context context, String name)
    {
        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }

}
