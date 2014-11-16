package hig.no.crowdinteraction;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mimoza on 11/9/2014.
 */
public class LoginFormFunctionalTest extends ActivityInstrumentationTestCase2 <LoginForm>{

    public static final String phoneNumber = "4 1 4 1 5 2 5 2 ENTER ";
    public static final String code = "1 3 3 7 ENTER ";
    private LoginForm activity;

    public LoginFormFunctionalTest(){
        super(LoginForm.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
    }
    public void testStartSecondActivity() throws Exception {

        // add monitor to check for the second activity
        Instrumentation.ActivityMonitor loginMonitor =
                getInstrumentation().
                        addMonitor(EventList.class.getName(), null, false);

        sendKeys(phoneNumber);
        sendKeys(code);
        sendKeys("ENTER");

        // wait 10 seconds for the start of the activity
        EventList startedActivity = (EventList) loginMonitor.waitForActivityWithTimeout(10000);
        assertNotNull(startedActivity);

        this.sendKeys(KeyEvent.KEYCODE_BACK);
    }

}
