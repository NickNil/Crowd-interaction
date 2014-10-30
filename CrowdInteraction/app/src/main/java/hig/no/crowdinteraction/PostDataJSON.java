package hig.no.crowdinteraction;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PostDataJSON
{
    Context context;

    PostDataJSON(Context appContext)
    {
        context = appContext;
    }

    String SENDER_ID = "914623768180";
    String SERVER_API_KEY = "G4zVKwwpEwsk20WEeLzqMNRt2A8Q3Lze";
    String SERVER_URL ="http://ci.harnys.net";
    Toast toast;

    protected void sendJson(final String firstname, final String lastname, final String nationality,
                            final String phoneNumber, final String passcode)
    {

       final GoogleCloudMessaging  gcm = GoogleCloudMessaging.getInstance(context);


        Thread t = new Thread()
        {

            public void run()
            {


                String regID = null;

                try
                {
                    regID = gcm.register(SENDER_ID);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                Log.i("regID in regthred",regID);

                if (regID !="")
                {
                    HttpClient client = new DefaultHttpClient();

                    HttpResponse response;
                    JSONObject json = new JSONObject();
                    JSONObject register = new JSONObject();
                    try {
                        HttpPost post = new HttpPost(SERVER_URL + "/api/register");
                        Log.i("URL:",SERVER_URL + "/api/register");
                        register.put("api_key", SERVER_API_KEY);
                        register.put("phone_number", phoneNumber);
                        register.put("passcode", passcode);
                        register.put("firstname", firstname);
                        register.put("lastname", lastname);
                        register.put("nationality", nationality);
                        register.put("regid", regID);

                        json.put("REGISTER", register);

                        StringEntity se = new StringEntity(json.toString());
                        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                        post.setEntity(se);
                        response = client.execute(post);

                        if (response != null) {

                            InputStream in = response.getEntity().getContent(); //Get the data in the entity
                            StatusLine statusLine = response.getStatusLine();
                            int statusCode = statusLine.getStatusCode();
                            Log.i("status",Integer.toString(statusCode));
                            in.close();

                            Log.i("xx",in.toString());


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();

    }




   /* void sendRegistrationIdToBackend(String regID)
    {

        Log.i("Start Reg", "registering device (regId = " + regID + ")");
        String serverUrl = SERVER_URL + "api/register";
        //String [] params = {regID};
        Map<String, String> params  = new HashMap<String, String>();

        post(serverUrl,"REGISTER", params);

    }

    protected void post(final String serverUrl,final String type, final Map<String, String> params)
    {
        Thread t = new Thread()
        {

            public void run()
            {

                HttpClient client = new DefaultHttpClient();

                HttpResponse response;
                JSONObject json = new JSONObject();
                JSONObject Parameters = new JSONObject();

                try {
                    HttpPost post = new HttpPost(serverUrl);

                    for (int i=0; i < params.size(); i++)
                    {
                        Parameters = params;
                    }

                   json.put(type, Parameters);

                    StringEntity se = new StringEntity(json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

                    if(response != null)
                    {

                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                        in.close();
                        Log.i(in.toString(), "xx");

                    }

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        };

        t.start();
    }*/

}
