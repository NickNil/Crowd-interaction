package hig.no.crowdinteraction;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Created by Mimoza on 11/26/2014.
 */
public class UserScore {
    Context context;
    User user;

    UserScore(Context appContext)
    {
        context = appContext;
        user = new User(context);
    }

    String SERVER_URL = "http://ci.harnys.net";
    Toast toast;

    protected void sendJson(final String id) {

        Thread t = new Thread() {

            public void run() {

                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                try {

                    HttpPost post = new HttpPost(SERVER_URL + "/api/userscore/"+id);
                    Log.i("url", (SERVER_URL + "/api/userscore/"+id).toString());

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

                        JSONObject jsonObj = new JSONObject(jsonString);
                        JSONObject data = jsonObj.getJSONObject("data");
                        Log.i("data", data.toString());
                        String highscore = data.getString("highscore");
                        Log.i("highscore",highscore);
                        user.SetHighscore(highscore);

                        //Log.i("Response2", inputStreamToString(in));*/
                        in.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
            }

        } catch (Exception e) {
        }
        return answer.toString();

    }

}