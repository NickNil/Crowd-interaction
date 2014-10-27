package hig.no.crowdinteraction;


import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.InputStream;

public class PostDataJSON
{

    String SERVER_URL ="ci.harnys.net";

    protected void sendJson(final String firstname, final String lastname, final String nationality,
                            final String phoneNumber, final String passcode)
    {

        Thread t = new Thread()
        {

            public void run()
            {

                HttpClient client = new DefaultHttpClient();

                HttpResponse response;
                JSONObject json = new JSONObject();
                JSONObject register = new JSONObject();
                try {
                    HttpPost post = new HttpPost("http://213.162.246.215/api/register");

                    register.put("phone_number", phoneNumber);
                    register.put("passcode", passcode);
                    register.put("firstname", firstname);
                    register.put("lastname", lastname);
                    register.put("nationality", nationality);

                    json.put("REGISTER", register);

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
