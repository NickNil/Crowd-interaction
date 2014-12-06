package hig.no.crowdinteraction;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;

/**
 * Created by Mimoza on 11/9/2014.
 */
public class Register_userUnitTest extends ActivityUnitTestCase <Register_user> {

        private int registerButtonID, nationalityButtonID;
        private Register_user activity;

        public Register_userUnitTest(){
            super(Register_user.class);
        }

        protected void setUp() throws Exception {
            super.setUp();
            Intent intent = new Intent(getInstrumentation().getTargetContext(),Register_user.class);
            startActivity(intent, null, null);
            activity = getActivity();
        }

        /**
          * Tests the layout, if the buttons have the correct labels
        */
        public void testLayout(){

            registerButtonID = R.id.registerButton;
            assertNotNull(activity.findViewById(registerButtonID));
            Button register = (Button) activity.findViewById(registerButtonID);
            assertEquals("Incorrect label of the button", "Register", register.getText());

            nationalityButtonID = R.id.nationalityButton;
            assertNotNull(activity.findViewById(nationalityButtonID));
            Button nationality = (Button) activity.findViewById(nationalityButtonID);
            assertEquals("Incorrect label of the button", "Nationality", nationality.getText());
        }

}
