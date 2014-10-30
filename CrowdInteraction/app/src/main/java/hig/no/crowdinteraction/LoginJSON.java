package hig.no.crowdinteraction;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoginJSON {
    Context context;

    LoginJSON(Context appContext) {
        context = appContext;
    }

    String SENDER_ID = "914623768180";
    String SERVER_API_KEY = "G4zVKwwpEwsk20WEeLzqMNRt2A8Q3Lze";
    String SERVER_URL = "http://ci.harnys.net";

    protected void sendJson(final String phoneNumber, final String passcode) {

        final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);


        Thread t = new Thread() {

            public void run() {


                String regID = null;

                try {
                    regID = gcm.register(SENDER_ID);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i("regID in regthred", regID);

                if (regID != "") {
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response;
                    try {
                        HttpPost post = new HttpPost(SERVER_URL + "/api/login");

                        Log.i("URL", SERVER_URL + "/api/register");


                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                        BasicNameValuePair pair = new BasicNameValuePair("api_key", SERVER_API_KEY);
                        nameValuePairs.add(pair);
                        pair = new BasicNameValuePair("phone_number", phoneNumber);
                        nameValuePairs.add(pair);
                        pair = new BasicNameValuePair("passcode", passcode);
                        nameValuePairs.add(pair);


                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        response = client.execute(post);


                        if (response != null) {

                            InputStream in = response.getEntity().getContent(); //Get the data in the entity
                            StatusLine statusLine = response.getStatusLine();
                            int statusCode = statusLine.getStatusCode();
                            Log.i("HTTP Status", Integer.toString(statusCode));
                            Log.i("Response", inputStreamToString(in));
                            in.close();
                            Log.i("add regID", "Saving regId on app version ");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();

    }

    private String inputStreamToString(InputStream is) {
        String rLine;
        StringBuilder answer = new StringBuilder();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);

            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
                Log.i("ausdbiuasbd", rLine);
            }

        } catch (Exception e) {
        }
        return answer.toString();

    }
}
