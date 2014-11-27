package hig.no.crowdinteraction;

/**
 Add a comment to this line
 * Created by Harnys on 26.10.2014.
 */


import android.app.Dialog;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;


import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService
{
    public static final String PROPERTY_REG_ID = "registration_id";
    //Context context;

    public static final int NOTIFICATION_ID = 1;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder NotifyBuilder;

    public GcmIntentService()
    {
        super("GcmIntentService");
       // context = getApplicationContext();

    }

    public void onHandleIntent(Intent intent)
    {
        Bundle message = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String Response = intent.getStringExtra("param");
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        switch (Response.charAt(0))
        {

            case 's':
            {
                int notifyID = 1;
                String score = message.getString("score");

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(500);
                // Sets an ID for the notification, so it can be updated
                NotifyBuilder = new NotificationCompat.Builder(this)
                        .setContentTitle("Scores are in")
                        .setContentText(score)
                        .setSmallIcon(R.drawable.ic_launcher);

                break;
            }
            case 'n':
            {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(500);
                // Sets an ID for the notification, so it can be updated
                NotifyBuilder = new NotificationCompat.Builder(this)
                        .setContentTitle("Uppdate events")
                        .setContentText("Uppdate your live events, Sorry")
                        .setSmallIcon(R.drawable.ic_launcher);
                break;
            }
            default:
            {
                int notifyID = 1;

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(500);
                // Sets an ID for the notification, so it can be updated
                NotifyBuilder = new NotificationCompat.Builder(this)
                        .setContentTitle("some msg")
                        .setContentText("You got something fom the server, but we don't know what")
                        .setSmallIcon(R.drawable.ic_launcher);

                break;
            }
        }

        mNotificationManager.notify(
                NOTIFICATION_ID,
                NotifyBuilder.build());
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}