package hig.no.crowdinteraction;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;

/**
 * Created by Mimoza on 11/9/2014.
 */
public class LoginFormUnitTest extends ActivityUnitTestCase<LoginForm> {

    private int loginButtonID;
    private int registerButtonID;
    private LoginForm activity;

    public LoginFormUnitTest(){
        super(LoginForm.class);
    }
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(),LoginForm.class);
        startActivity(intent, null, null);
        activity = getActivity();
    }

    public void testLayout() {

        loginButtonID = R.id.loginButton;
        assertNotNull(activity.findViewById(loginButtonID));
        Button login = (Button) activity.findViewById(loginButtonID);
        assertEquals("Incorrect label of the button", "Login", login.getText());

        registerButtonID = R.id.registerButton2;
        assertNotNull(activity.findViewById(registerButtonID));
        Button register = (Button) activity.findViewById(registerButtonID);
        assertEquals("Incorrect label of the button", "Register", register.getText());
    }

    public void testIntentTriggerViaOnClick() {

        loginButtonID = R.id.loginButton;
        Button login = (Button) activity.findViewById(loginButtonID);
        assertNotNull("Button not allowed to be null", login);

        login.performClick();

        // Check the intent which was started
        Intent triggeredIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", triggeredIntent);

        registerButtonID = R.id.registerButton2;
        Button register = (Button) activity.findViewById(registerButtonID);
        assertNotNull("Button not allowed to be null", register);

        register.performClick();

        // Check the intent which was started
        Intent triggeredIntent2 = getStartedActivityIntent();
        assertNotNull("Intent was null", triggeredIntent2);
    }
}
