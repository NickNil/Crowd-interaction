package hig.no.crowdinteraction;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nicklas on 23.11.2014.
 */
public class LiveEventListItems extends ArrayAdapter<String>{
    private final Activity context;
    private final String [] eventName;
    private final String [] id;
    private final String [] nationality;
    private final String [] number;
    private final String [] athleteName;
    private final Integer[] natIcon;
    private final Integer[] eventIcon;

    public LiveEventListItems(Activity context,String [] id, String [] eventName, String [] nationality, String [] number, String [] athleteName, Integer[] natIcon, Integer[] eventIcon) {
        super(context, R.layout.live_event_list_items, eventName);
        this.context = context;
        this.id = id;
        this.eventName = eventName;
        this.nationality = nationality;
        this.number = number;
        this.athleteName = athleteName;
        this.natIcon = natIcon;
        this.eventIcon = eventIcon;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.live_event_list_items, null, true);
        TextView tvEventName = (TextView) rowView.findViewById(R.id.eventName);
        TextView tvNat = (TextView) rowView.findViewById(R.id.iocNationality);
        TextView tvNum = (TextView) rowView.findViewById(R.id.number);
        TextView tvAthlete = (TextView) rowView.findViewById(R.id.athleteName);

        ImageView ivNat = (ImageView) rowView.findViewById(R.id.iocIcon);
        ImageView ivEvent = (ImageView) rowView.findViewById(R.id.eventIcon);
        tvEventName.setText(eventName[position]);
        tvNat.setText(nationality[position]);
        tvNum.setText(number[position]);
        tvAthlete.setText(athleteName[position]);
        ivNat.setImageResource(natIcon[position]);
        ivEvent.setImageResource(eventIcon[position]);
        rowView.setTag(id);
        return rowView;
    }

}
