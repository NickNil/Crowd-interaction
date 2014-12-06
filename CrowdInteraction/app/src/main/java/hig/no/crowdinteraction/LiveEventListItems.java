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

    /**
     * Constructor that gets data from the LiveEventList class.
     * @param context       the context of the application
     * @param id            Array containing the mongoId
     * @param eventName     Array containing the name of the events
     * @param nationality   Array containing the nationalities of the athletes
     * @param number        Array containing the numbers of the athletes
     * @param athleteName   Array containing the names of the athletes
     * @param natIcon       Array containing ids of the drawable files containing flag icons
     * @param eventIcon     Array containing ids of the drawable files containing the event icons
     */
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

    /**
     * overrides the getview function of the arrayAdapter in order to return a custom listview
     * row instead of a regular one.
     * @param position  the current row index
     * @param view
     * @param parent
     * @return          The custom listview row
     */
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
