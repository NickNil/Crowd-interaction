package hig.no.crowdinteraction;

/**
 * Created by Harnys on 26.10.2014.
 */

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService
{
    public static final String PROPERTY_REG_ID = "registration_id";

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    protected void onMessage(Context context, Intent intent)
    {
        Log.i("On Messasge", "Received message. Extras: " + intent.getExtras());
        String message = (R.string.gcm_message);
        String Response = intent.getStringExtra("param");

        switch (Response.charAt(0))
        {
            case'r':
            {
                String regId = intent.getStringExtra("regid");

                final SharedPreferences prefs = MainActivity.getGCMPreferences(context);
                Log.i("add regID", "Saving regId on app version ");
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(PROPERTY_REG_ID, regId);
                editor.commit();

                break;
            }
            case'x':
            {
                CharSequence text = "User is already rregistered";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
            }
        }
    }

}
