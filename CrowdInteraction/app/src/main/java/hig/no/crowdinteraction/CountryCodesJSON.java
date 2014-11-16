package hig.no.crowdinteraction;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Mimoza on 11/14/2014.
 */
public class CountryCodesJSON {

    Context context;
    User user;

    CountryCodesJSON(Context appContext)
    {
        context = appContext;
        user = new User(context);
    }

    String SENDER_ID = "914623768180";
    String SERVER_API_KEY = "G4zVKwwpEwsk20WEeLzqMNRt2A8Q3Lze";
    String SERVER_URL = "http://ci.harnys.net";
    public static final String PROPERTY_REG_ID = "registration_id";
    Toast toast;

    protected void sendJson() {

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
                        HttpPost post = new HttpPost(SERVER_URL + "/api/ioc");

                        Log.i("URL", SERVER_URL + "/api/register");

                        response = client.execute(post);


                        if (response != null) {

                            HttpEntity entity;
                            entity = response.getEntity();

                            InputStream in = entity.getContent(); //Get the data in the entity
                            StatusLine statusLine = response.getStatusLine();
                            int statusCode = statusLine.getStatusCode();
                            Log.i("HTTP Status", Integer.toString(statusCode));

                            String jsonString = inputStreamToString(in);
                            jsonString = jsonString.replaceFirst(Pattern.quote("["), "");
                            Log.i("Response", jsonString);

                            JSONObject jsonObj = new JSONObject(jsonString);
                            JSONArray jsonArray = jsonObj.getJSONArray("data");
                            //Log.i("test", jsonArray.getJSONObject(0).toString());

                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                MySQLiteHelper db = new MySQLiteHelper(context);
                                db.addCountry(new IOCandISOcodes(jsonArray.getJSONObject(i).getString("ioc"),jsonArray.getJSONObject(i).getString("iso"), jsonArray.getJSONObject(i).getString("name")));
                                //get all countries
                                //list = db.getAllCountries();
                            }

                            //Log.i("Response2", inputStreamToString(in));
                            in.close();
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
                //Log.i("ausdbiuasbd", rLine);
            }

        } catch (Exception e) {
        }
        return answer.toString();

    }

}
