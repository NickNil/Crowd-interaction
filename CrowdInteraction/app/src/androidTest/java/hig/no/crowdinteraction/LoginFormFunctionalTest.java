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


    String [] phoneNumbers = {"","1568894","41415252" };
    String [] codes = {"","15616", "1337"};
    //public static final String phoneNumber = "4 1 4 1 5 2 5 2 ENTER ";
    //public static final String code = "1 3 3 7 ENTER ";
    private LoginForm activity;
    EditText phoneNumber, code;
    Button login;
    int i;

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
    }
    public void testStartSecondActivity() throws Throwable {


        for(i = 0; i < phoneNumbers.length; i++) {

            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    phoneNumber.setText(phoneNumbers[i]);
                    code.setText(codes[i]);
                }
            });
            TouchUtils.clickView(this, login);
            this.sendKeys(KeyEvent.KEYCODE_BACK);

            // add monitor to check for the second activity
            Instrumentation.ActivityMonitor loginMonitor =
                    getInstrumentation().
                            addMonitor(EventList.class.getName(), null, false);
            // wait 10 seconds for the start of the activity
            EventList startedActivity = (EventList) loginMonitor.waitForActivityWithTimeout(10000);

            //sendKeys(phoneNumbers[i]);
            //sendKeys(codes[i]);
            //sendKeys("ENTER");
            //sendKeys(KeyEvent.KEYCODE_BACK);

            if (phoneNumbers[i].equals("41415252")&& codes[i].equals("1337")){
                    TouchUtils.clickView(this, login);
                    assertNotNull("Activity is null", startedActivity);
            }
            else{
                    TouchUtils.clickView(this, login);
                    assertNull("Activity is not null", startedActivity);
            }

        }


    }

}
