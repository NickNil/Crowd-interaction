package hig.no.crowdinteraction;

/**
 * Created by Harnys on 26.10.2014.
 */


import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMIntentService extends IntentService
{
    public static final String PROPERTY_REG_ID = "registration_id";
    Context context;

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GCMIntentService()
    {
        super("GcmIntentService");
        context = getApplicationContext();

    }

    protected void onHandleIntent(Intent intent)
    {
        Bundle message = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String Response = intent.getStringExtra("param");

        switch (Response.charAt(0))
        {
            case'r':
            {
                String regId = intent.getStringExtra("regid");

                Activity activity = (Activity) context;
                final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
                        Context.MODE_PRIVATE);


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
            case 'e':
            {
                EventList.populateEventList(intent);
            }

        }
    }

}
