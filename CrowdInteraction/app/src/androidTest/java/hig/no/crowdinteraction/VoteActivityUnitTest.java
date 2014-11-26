package hig.no.crowdinteraction;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.NumberPicker;

/**
 * Created by Mimoza on 11/9/2014.
 */
public class VoteActivityUnitTest extends ActivityUnitTestCase <VoteActivity> {

    private VoteActivity activity;
    private int voteButtonID;
    NumberPicker np;


    public VoteActivityUnitTest(){

        super(VoteActivity.class);
    }

    protected void setUp() throws Exception{
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(),LoginForm.class);
        startActivity(intent, null, null);
        activity = getActivity();
        np = (NumberPicker)activity.findViewById(R.id.scorePicker);
    }

    public void testLayout(){

        voteButtonID = R.id.voteButton;
        assertNotNull(activity.findViewById(voteButtonID));
        Button vote = (Button) activity.findViewById(voteButtonID);
        assertEquals("Incorrect label of the button", "Vote", vote.getText());

        assertNotNull(activity.findViewById(R.id.scorePicker));
    }

    public void testRange(){
        np.setMinValue(15);
        np.setMaxValue(20);
        assertEquals(np.getMinValue(),15);
        assertEquals(np.getMaxValue(),20);
    }

    public void testCurrent(){
        np.setValue(0);
        assertEquals(np.getValue(),0);
    }
}
