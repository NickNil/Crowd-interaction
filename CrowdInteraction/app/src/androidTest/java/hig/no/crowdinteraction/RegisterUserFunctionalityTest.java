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
        EditText firstname = (EditText) registerActivity.findViewById(R.id.registerFirstname);
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),firstname);
        assertEquals("Text incorrect", "Firstname:", firstname.getText().toString());

        // textView Lastname
        EditText lastname = (EditText) registerActivity.findViewById(R.id.registerLastname);
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),lastname);
        assertEquals("Text incorrect", "Lastname:", lastname.getText().toString());

        // textView Nationality
        Button nationality = (Button) registerActivity.findViewById(R.id.nationalityButton);
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),nationality);
        assertEquals("Text incorrect", "Nationality:", nationality.getText().toString());

        // textView Phone number
        EditText phoneNumber = (EditText) registerActivity.findViewById(R.id.registerPhoneNumber);
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),phoneNumber);
        assertEquals("Text incorrect", "Phone number:", phoneNumber.getText().toString());

        // textView Code
        EditText code = (EditText) registerActivity.findViewById(R.id.registerCode);
        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),code);
        assertEquals("Text incorrect", "Code:", code.getText().toString());

        ViewAsserts.assertOnScreen(registerActivity.getWindow().getDecorView(),register);
        assertEquals("Text incorrect", "Register", register.getText().toString());

        this.sendKeys(KeyEvent.KEYCODE_BACK);
    }

}
