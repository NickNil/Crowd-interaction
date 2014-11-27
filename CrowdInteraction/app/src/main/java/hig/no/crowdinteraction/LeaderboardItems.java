package hig.no.crowdinteraction;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nicklas on 27.11.2014.
 */
public class LeaderboardItems extends ArrayAdapter<String> {
    private final Activity context;
    private final Integer[] natIcon;
    private final String[] position;
    private final String[] name;
    private final String[] points;
    private final String[] nationality;

    public LeaderboardItems(Activity context, Integer[] natIcon, String[] position, String[] name,
                            String[] points, String[] nationality) {
        super(context, R.layout.leaderboard_items, name);
        this.context = context;
        this.natIcon = natIcon;
        this.name = name;
        this.points = points;
        this.position = position;
        this.nationality = nationality;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        System.out.println(name.length);
        if (name.length > 0)
            System.out.println(name[0] + " : " + name[1]);
        System.out.println(points.length);
        if (points.length > 0)
            System.out.println(points[0] + " : " + points[1]);
        View rowView= inflater.inflate(R.layout.leaderboard_items, null, true);
        TextView tvPos = (TextView) rowView.findViewById(R.id.position);
        TextView tvNat = (TextView) rowView.findViewById(R.id.nationality);
        TextView tvPoints = (TextView) rowView.findViewById(R.id.score);
        TextView tvName = (TextView) rowView.findViewById(R.id.name);

        ImageView ivNat = (ImageView) rowView.findViewById(R.id.flag);
        tvPos.setText(position[index]);
        tvNat.setText(nationality[index]);
        tvPoints.setText(points[index]);
        tvName.setText(name[index]);
        ivNat.setImageResource(natIcon[index]);
        return rowView;
    }
}
