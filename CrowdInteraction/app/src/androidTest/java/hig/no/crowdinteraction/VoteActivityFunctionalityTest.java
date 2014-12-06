package hig.no.crowdinteraction;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by Mimoza on 11/9/2014.
 */
public class VoteActivityFunctionalityTest extends ActivityInstrumentationTestCase2 <VoteActivity> {

    VoteActivity activity;

    public VoteActivityFunctionalityTest(){
        super(VoteActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //setActivityInitialTouchMode(false);
        activity = getActivity();
    }

    /*public void testLayoutComponents() throws Exception {

        // add monitor to check for the activity
        Instrumentation.ActivityMonitor voteMonitor =
                getInstrumentation().
                        addMonitor(VoteActivity.class.getName(), null, false);

        // wait 2 seconds for the start of the activity
        VoteActivity voteActivity = (VoteActivity) voteMonitor
                .waitForActivityWithTimeout(10000);
        assertNotNull(voteActivity);

        // search for the title
        TextView title = (TextView) voteActivity.findViewById(R.id.Title);

        // check that the TextView is on the screen
        ViewAsserts.assertOnScreen(voteActivity.getWindow().getDecorView(),
                title);
        // validate the text on the TextView
        assertEquals("Text incorrect", "Voting", title.getText().toString());

        // Sport textView
        TextView sport = (TextView) voteActivity.findViewById(R.id.sport);
        ViewAsserts.assertOnScreen(voteActivity.getWindow().getDecorView(), sport);
        assertEquals("Text incorrect", "Sport:", sport.getText().toString());

        // Event textView
        TextView event = (TextView) voteActivity.findViewById(R.id.event);
        ViewAsserts.assertOnScreen(voteActivity.getWindow().getDecorView(), event);
        assertEquals("Text incorrect", "Event:", event.getText().toString());

        // Sport textView
        TextView athlete = (TextView) voteActivity.findViewById(R.id.athlete);
        ViewAsserts.assertOnScreen(voteActivity.getWindow().getDecorView(), athlete);
        assertEquals("Text incorrect", "Athlete:", athlete.getText().toString());

        //scorePicker
        NumberPicker scorePicker = (NumberPicker) voteActivity.findViewById(R.id.scorePicker);
        ViewAsserts.assertOnScreen(voteActivity.getWindow().getDecorView(), scorePicker);

    }*/
}
