package hig.no.crowdinteraction;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Before users can use the application they have to log in and this class sets the login screen and when the user has
 * the filled his credentials and tries to log in, this class uses the LoginTask class checks if the credentials are correct
 * and sends the user to the home screen
 * */
public class LoginForm extends Activity {

    User user;
    PostDataJSON post;
    EditText phoneNumber;
    EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        user = new User(getApplicationContext());
        post = new PostDataJSON(getApplicationContext());

        Button login = (Button) findViewById(R.id.loginButton);
        Button register = (Button) findViewById(R.id.registerButton2);

        phoneNumber = (EditText) findViewById(R.id.phoneNumberInput);
        code = (EditText) findViewById(R.id.codeInput);
        

        login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                if(phoneNumber.getText().toString().equals("")
                        || code.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Please fill Phone number and Code to login!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                   new LoginTask().execute();
                    //LoginJSON json = new LoginJSON(getApplicationContext());

                    //json.sendJson(phoneNumber.getText().toString(),
                      //      code.getText().toString());

                    //Intent intent = new Intent(LoginForm.this, Home.class);
                    //startActivity(intent);


                }
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                CountryCodesJSON json = new CountryCodesJSON(getApplicationContext());
                json.sendJson();
                Intent i = new Intent(LoginForm.this, Register_user.class);
                startActivity(i);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * Checks with the server if the data sent from the user complies with the data registered in the server database, in case they do
    * then the user can log in to the app, his data are saved to in the shared preferences using the User class,
    * and if the data doesn't comply with the server then the user get a notification that the credentials he has given don't match
    */
    private class LoginTask extends AsyncTask <Void, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(Void... params) {

            String SERVER_URL = "http://ci.harnys.net";
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            JSONObject data = null;

            try {

                HttpPost httpPost = new HttpPost(SERVER_URL + "/api/login");

                Log.i("URL", SERVER_URL + "/api/login");


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                //BasicNameValuePair pair = new BasicNameValuePair("api_key", SERVER_API_KEY);
                //nameValuePairs.add(pair);
                BasicNameValuePair pair = new BasicNameValuePair("phone_number", phoneNumber.getText().toString());
                nameValuePairs.add(pair);
                pair = new BasicNameValuePair("passcode", code.getText().toString());
                nameValuePairs.add(pair);


                response = post.sendJson(client, httpPost, nameValuePairs);


                if (response != null) {

                    HttpEntity entity;
                    entity = response.getEntity();

                    InputStream in = entity.getContent();//response.getEntity().getContent(); //Get the data in the entity
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    Log.i("HTTP Status", Integer.toString(statusCode));

                    String jsonString = post.inputStreamToString(in);
                    in.close();

                    jsonString = jsonString.replace("[","");
                    jsonString = jsonString.replace("]","");
                    Log.i("LoginResponse", jsonString);



                    JSONObject jsonObj = new JSONObject(jsonString);

                    data = jsonObj;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            JSONObject data = null;
            if(jsonObject != null){

                try {

                    data = jsonObject.getJSONObject("data");
                    Log.i("data", data.toString());
                    String id = data.getString("id");
                    Log.i("id",id);

                    if (id.equals("0")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast toast = Toast.makeText(getApplicationContext(), "The phone number and code don't match. Please double-check and try again.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    }
                    else{

                        JSONObject name = data.getJSONObject("name");
                        user.SetPhoneNumber(phoneNumber.getText().toString());
                        user.SetName(name.getString("firstname"), name.getString("lastname"));

                        JSONObject nationality = data.getJSONObject("nationality");
                        //user.SetNationality(data.getString("nationality"));
                        user.SetIoc(nationality.getString("ioc"));
                        user.SetIso(nationality.getString("iso"));
                        user.SetGmcId(data.getString("regid"));
                        user.SetMongoId(id);
                        user.SetHighscore(data.getString("highscore"));

                        Intent intent = new Intent(getApplicationContext(),Home.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                    if (!user.GetMongoId().equals("") && !user.GetMongoId().equals(null) )
                    {

                        Intent intent2 = new Intent(LoginForm.this, Home.class);
                        startActivity(intent2);
                    }

                    else if (user.GetMongoId().equals("") && user.GetMongoId().equals(null))
                    {
                        Toast.makeText(getApplicationContext(),
                                "You are not a registered user. Please register first!",
                                Toast.LENGTH_SHORT).show();
                    }

                            /*{
                                "param":"l",
                                    "data":{
                                "id":"545219809c76847e198b4570",
                                        "nationality":{
                                    "iso":"AL",
                                            "ioc":"ALB"
                                },
                                "highscore":0,
                                        "name":{
                                    "firstname":"Bjarne",
                                            "lastname":"Betjent"
                                },
                                "regid":"REGIDBJARNE1337"
                            }
                            }*/

                            /*{"param":"l","data":
                                {"id":"545239c29c76842a1e8b4568",
                                "nationality":"Norge",
                                "name":{"firstname":"Harry","lastname":"Nystad"},
                                "regid":"APA91bFv_6fAq_SuyFubPrJcDxfktd8YmlzX4gxEEhOahUlOVmKvIu8Y60PDFBuosZiducUxAQva0JWLGxDFXKHOb2bhRlFCILB1jvCIPC6Vz9E5knOmELJQ5M3sQXqX0HOtc8uVWEbtGLme7E4-W2fBKXzdC1th76HJXOezTd5dJB13rGuzRuQ"}}]
                            */

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Oops! Something went WRONG! Please check your internet connection and try again.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
