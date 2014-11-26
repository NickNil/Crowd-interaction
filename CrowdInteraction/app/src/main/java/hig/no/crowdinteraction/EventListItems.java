package hig.no.crowdinteraction;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Harry on 26.11.2014.
 */
public class EventListItems extends ArrayAdapter<String>
{
    private final Activity context;
    private final String [] eventName;
    private final String [] time;
    private final String [] id;



    public EventListItems(Activity context,String [] id, String [] eventName, String [] time)
    {
        super(context, R.layout.event_list_items, eventName);
        this.context = context;
        this.id = id;
        this.eventName = eventName;
        this.time = time;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.event_list_items, null, true);
        TextView tvEventName = (TextView) rowView.findViewById(R.id.eventName);
        TextView tvtime = (TextView) rowView.findViewById(R.id.time);



        tvEventName.setText(eventName[position]);
        tvtime.setText(time[position]);
        rowView.setTag(id);
        return rowView;
    }

}
