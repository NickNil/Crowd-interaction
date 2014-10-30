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
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoginJSON {
    Context context;
    User user;

    LoginJSON(Context appContext)
    {
        context = appContext;
        user = new User(context);
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
                            Log.i("LoginResponse", inputStreamToString(in));

                            JSONObject myObject = new JSONObject();
                            myObject.getString(inputStreamToString(in));
                            int a = 1;
                            int b = 1;
                            int c;
                            c = a*b;
                            in.close();

                           /* user.SetPhoneNumber(phoneNumber);
                            user.SetName(firstname,lastname);
                            user.SetNationality(nationality);
                            user.SetGmcId(regID); */



                            /*{"param":"l","data":
                                {"id":"545239c29c76842a1e8b4568",
                                "nationality":"Norge",
                                "name":{"firstname":"Harry","lastname":"Nystad"},
                                "regid":"APA91bFv_6fAq_SuyFubPrJcDxfktd8YmlzX4gxEEhOahUlOVmKvIu8Y60PDFBuosZiducUxAQva0JWLGxDFXKHOb2bhRlFCILB1jvCIPC6Vz9E5knOmELJQ5M3sQXqX0HOtc8uVWEbtGLme7E4-W2fBKXzdC1th76HJXOezTd5dJB13rGuzRuQ"}}]
                            */
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
