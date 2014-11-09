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
public class RegisterUserFunctionalityTest extends ActivityInstrumentationTestCase2<LoginForm> {

    private LoginForm activity;

    public RegisterUserFunctionalityTest(){
        super(LoginForm.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
    }

    public void testStartSecondActivity() throws Exception {

        // add monitor to check for the second activity
        Instrumentation.ActivityMonitor registerMonitor =
                getInstrumentation().
                        addMonitor(Register_user.class.getName(), null, false);

        // find button and click it
        Button register = (Button) activity.findViewById(R.id.registerButton2);

        // TouchUtils handles the sync with the main thread internally
        TouchUtils.clickView(this, register);

        // wait 2 seconds for the start of the activity
        Register_user registerActivity = (Register_user) registerMonitor
                .waitForActivityWithTimeout(10000);
        assertNotNull(registerActivity);

        // search for the textView
        TextView textView = (TextView) registerActivity.findViewById(R.id.textViewRegister);

        // check that the TextView is on the screen
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),
                textView);
        // validate the text on the TextView
        assertEquals("Text incorrect", "Register", textView.getText().toString());

        // textView Firstname
        TextView firstname = (TextView) registerActivity.findViewById(R.id.sport);
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),firstname);
        assertEquals("Text incorrect", "Firstname:", firstname.getText().toString());

        // textView Lastname
        TextView lastname = (TextView) registerActivity.findViewById(R.id.lastname);
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),lastname);
        assertEquals("Text incorrect", "Lastname:", lastname.getText().toString());

        // textView Nationality
        TextView nationality = (TextView) registerActivity.findViewById(R.id.nationality);
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),nationality);
        assertEquals("Text incorrect", "Nationality:", nationality.getText().toString());

        // textView Phone number
        TextView phoneNumber = (TextView) registerActivity.findViewById(R.id.phoneNumber);
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),phoneNumber);
        assertEquals("Text incorrect", "Phone number:", phoneNumber.getText().toString());

        // textView Code
        TextView code = (TextView) registerActivity.findViewById(R.id.code);
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),code);
        assertEquals("Text incorrect", "Code:", code.getText().toString());

        this.sendKeys(KeyEvent.KEYCODE_BACK);
    }

}
