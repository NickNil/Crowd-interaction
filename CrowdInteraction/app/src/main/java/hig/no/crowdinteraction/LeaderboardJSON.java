package hig.no.crowdinteraction;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;

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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Nicklas on 11.11.2014.
 */
public class LeaderboardJSON {
    Context context;
    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<String> isoNationality = new ArrayList<String>();
    ArrayList<String> position = new ArrayList<String>();
    ArrayList<String> userName = new ArrayList<String>();
    ArrayList<String> points = new ArrayList<String>();
    ArrayList<String> iocNationality = new ArrayList<String>();
    Boolean responseError = false;

    LeaderboardJSON(Context appContext)
    {
        context = appContext;
    }

    String SENDER_ID = "914623768180";
    String SERVER_API_KEY = "G4zVKwwpEwsk20WEeLzqMNRt2A8Q3Lze";
    String SERVER_URL = "http://ci.harnys.net";

    protected void sendJson() {

        Thread t = new Thread() {

            public void run() {

                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                JSONObject user;
                JSONObject nationality;
                JSONObject name;


                try {
                    HttpPost post = new HttpPost(SERVER_URL + "/api/leaderboard");

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    BasicNameValuePair pair = new BasicNameValuePair("api_key", SERVER_API_KEY);
                    nameValuePairs.add(pair);

                    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    response = client.execute(post);

                    if (response != null)
                    {

                        HttpEntity entity;
                        entity = response.getEntity();

                        InputStream in = entity.getContent();//response.getEntity().getContent(); //Get the data in the entity
                        StatusLine statusLine = response.getStatusLine();
                        int statusCode = statusLine.getStatusCode();
                        Log.i("HTTP Status", Integer.toString(statusCode));

                        String jsonString = inputStreamToString(in);
                        Log.i("jsonstring", jsonString);
                        jsonString = jsonString.replaceFirst(Pattern.quote("["), "");
                        //int lastIndex = jsonString.lastIndexOf("]");
                        //jsonString = new StringBuilder(jsonString).replace(lastIndex-1, lastIndex,"}").toString();

                        Log.i("LoginResponse", jsonString);

                        JSONObject jsonObj = new JSONObject(jsonString);

                        JSONArray jsonArray = jsonObj.getJSONArray("data");
                        Log.i("test", jsonArray.getJSONObject(0).toString());

                        //jsonObj = jsonObj.getJSONObject("data");

                        //JSONObject name = jsonObj.getJSONObject("name");

                        in.close();

                        for (int i=0; i<jsonArray.length(); i++)
                        {
                            user = jsonArray.getJSONObject(i).getJSONObject("user");
                            nationality = user.getJSONObject("nationality");
                            name = user.getJSONObject("name");

                            userName.add(name.getString("firstname") + " " + name.getString("lastname"));
                            iocNationality.add(nationality.getString("ioc"));
                            isoNationality.add(nationality.getString("iso"));
                            position.add(jsonArray.getJSONObject(i).getString("standing"));
                            points.add(user.getString("highscore"));
                        }



                       /* user.SetName(name.getString("firstname"), name.getString("lastname"));
                        user.SetNationality(jsonObj.getString("nationality"));
                        user.SetGmcId(jsonObj.getString("regid"));
                        user.SetMongoId(jsonObj.getString("id"));*/

                        /*{"param":"l","data":
                            {"id":"545239c29c76842a1e8b4568",
                            "nationality":"Norge",
                            "name":{"firstname":"Harry","lastname":"Nystad"},
                            "regid":"APA91bFv_6fAq_SuyFubPrJcDxfktd8YmlzX4gxEEhOahUlOVmKvIu8Y60PDFBuosZiducUxAQva0JWLGxDFXKHOb2bhRlFCILB1jvCIPC6Vz9E5knOmELJQ5M3sQXqX0HOtc8uVWEbtGLme7E4-W2fBKXzdC1th76HJXOezTd5dJB13rGuzRuQ"}}]
                        */
                    }

                } catch (UnknownHostException e){
                    e.printStackTrace();
                }catch (JSONException e) {
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
        System.out.println("test");
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


