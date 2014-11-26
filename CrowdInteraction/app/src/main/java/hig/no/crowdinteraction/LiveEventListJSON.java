package hig.no.crowdinteraction;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Nicklas on 11.11.2014.
 */
public class LiveEventListJSON {
    Context context;
    Boolean responseError = false;
    ArrayList<String> mongoID = new ArrayList<String>();
    ArrayList<String> eventName = new ArrayList<String>();
    ArrayList<String> eventType = new ArrayList<String>();
    ArrayList<String> athleteName = new ArrayList<String>();
    ArrayList<String> number = new ArrayList<String>();
    ArrayList<String> iocNationality = new ArrayList<String>();
    ArrayList<String> isoNationality = new ArrayList<String>();


    LiveEventListJSON()
    {
    }

    String SENDER_ID = "914623768180";
    String SERVER_API_KEY = "G4zVKwwpEwsk20WEeLzqMNRt2A8Q3Lze";
    String SERVER_URL = "http://ci.harnys.net";

    protected void sendJson() {

        Thread t = new Thread() {

            public void run() {

                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                JSONObject data;
                JSONObject nationality;
                JSONObject athlete;
                String newline = System.getProperty("line.separator");


                try {
                      HttpPost post = new HttpPost(SERVER_URL + "/api/live");

                      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    BasicNameValuePair pair = new BasicNameValuePair("api_key", SERVER_API_KEY);
                    nameValuePairs.add(pair);

                    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    response = client.execute(post);

                    if (response != null) {

                        HttpEntity entity;
                        entity = response.getEntity();

                        InputStream in = entity.getContent();//response.getEntity().getContent(); //Get the data in the entity
                        StatusLine statusLine = response.getStatusLine();
                        int statusCode = statusLine.getStatusCode();
                        Log.i("HTTP Status", Integer.toString(statusCode));

                        String jsonString = inputStreamToString(in);
                        jsonString = jsonString.replaceFirst(Pattern.quote("["), "");

                        Log.i("LoginResponse", jsonString);

                        JSONObject jsonObj = new JSONObject(jsonString);

                        JSONArray jsonArray = jsonObj.getJSONArray("data");
                        Log.i("test", jsonArray.getJSONObject(0).toString());

                        in.close();

                        for (int i=0; i<jsonArray.length(); i++)
                        {
                            data = jsonArray.getJSONObject(i);
                            athlete = data.getJSONObject("current_athlete");
                            nationality = athlete.getJSONObject("nationality");



                            mongoID.add(data.getString("_id"));
                            eventName.add(data.getString("event_name"));
                            eventType.add(data.getString("event_type"));
                            iocNationality.add(nationality.getString("ioc"));
                            isoNationality.add(nationality.getString("iso"));
                            number.add(athlete.getString("number"));
                            athleteName.add(athlete.getString("name"));
                        }

                    }
                    else
                    {
                        responseError = true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String inputStreamToString(InputStream is) {
        String rLine;
        StringBuilder answer = new StringBuilder();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);

            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
                Log.i("LoginResponse2", rLine);
            }

        } catch (Exception e) {
        }
        return answer.toString();
    }

}
