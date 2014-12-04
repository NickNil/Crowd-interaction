package hig.no.crowdinteraction;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Mimoza on 11/9/2014.
 */
public class LoginFormFunctionalTest extends ActivityInstrumentationTestCase2 <LoginForm>{


    private LoginForm activity;
    EditText phoneNumber, code;
    Button login;
    User user;

    public LoginFormFunctionalTest(){
        super(LoginForm.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
        phoneNumber = (EditText)activity.findViewById(R.id.phoneNumberInput);
        code = (EditText)activity.findViewById(R.id.codeInput);
        login = (Button)activity.findViewById(R.id.loginButton);
        user = new User(getActivity());
    }

    /**
     * Tests if the user can log in with wrong credentials
     * Assure the user can't start the following acitivity
     */
    public void testLoginWrongCreds() throws Throwable{
        final String phoneNr = "2", passcode = "4";

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                phoneNumber.setText(phoneNr);
                code.setText(passcode);
            }
        });

        TouchUtils.clickView(this, login);
        this.sendKeys(KeyEvent.KEYCODE_BACK);

        // add monitor to check for the second activity
        Instrumentation.ActivityMonitor loginMonitor =
                getInstrumentation().
                        addMonitor(EventList.class.getName(), null, false);
        // wait 2 seconds for the start of the activity
        EventList startedActivity = (EventList) loginMonitor.waitForActivityWithTimeout(2000);
        assertNull("Activity is not null", startedActivity);
        sendKeys(KeyEvent.KEYCODE_BACK);

    }

    /**
     * Tests if the user can log in with empty credentials
     * Assure the user can't start the following acitivity
     */
    public void testLoginEmptyCreds() throws Throwable{
        final String phoneNr = "", passcode = "";

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                phoneNumber.setText(phoneNr);
                code.setText(passcode);
            }
        });

        TouchUtils.clickView(this, login);
        this.sendKeys(KeyEvent.KEYCODE_BACK);

        // add monitor to check for the second activity
        Instrumentation.ActivityMonitor loginMonitor =
                getInstrumentation().
                        addMonitor(EventList.class.getName(), null, false);
        // wait 2 seconds for the start of the activity
        EventList startedActivity = (EventList) loginMonitor.waitForActivityWithTimeout(2000);
        assertNull("Activity is not null", startedActivity);
        sendKeys(KeyEvent.KEYCODE_BACK);

    }

    /**
     * Tests if the user can log in with correct credentials
     * Assure that the acitivity following the login starts correctly
     */
    public void testLoginCorrectCreds() throws Throwable{
        final String phoneNr = "12358", passcode = "12358";

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                phoneNumber.setText(phoneNr);
                code.setText(passcode);
            }
        });

        TouchUtils.clickView(this, login);
        this.sendKeys(KeyEvent.KEYCODE_BACK);

        // add monitor to check for the second activity
        Instrumentation.ActivityMonitor loginMonitor =
                getInstrumentation().
                        addMonitor(EventList.class.getName(), null, false);
        // wait 2 seconds for the start of the activity
        EventList startedActivity = (EventList) loginMonitor.waitForActivityWithTimeout(2000);
        assertNull("Activity is null", startedActivity);
        user.logout();
        sendKeys(KeyEvent.KEYCODE_BACK);


    }

}
