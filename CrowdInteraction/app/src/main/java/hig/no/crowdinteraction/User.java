package hig.no.crowdinteraction;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Harry on 30.10.2014.
 */
public class User
{
    String gmcID;
    String mongoID;
    String[] name;
    String nationality;
    String phoneNumber;
    Context context;
    SharedPreferences sharedPref;
    int position;
    int score;

    User()
    {

    }

    User( Context appContext)
    {
        String firstName;
        String lastName;
        context = appContext;
        sharedPref = context.getSharedPreferences("hig.no.crowdInteraction.PREFERENCE_FILE_KEY",
                Context.MODE_PRIVATE);
        gmcID = sharedPref.getString("GMC_ID","");
        mongoID = sharedPref.getString("MONGO_ID","");
        nationality = sharedPref.getString("Nationality","");
        phoneNumber = sharedPref.getString("PhoneNumber","");
        firstName = sharedPref.getString("firstName","");
        lastName = sharedPref.getString("lastName","");
        name  = new String[] {firstName, lastName};

        context = appContext;
    }

    protected void logout()
    {
        gmcID = "";
        mongoID = "";
        name = new String[] {"", ""};
        nationality = "";
        phoneNumber = "";

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("GMC_ID",gmcID);
        editor.putString("MONGO_ID",mongoID);
        editor.putString("firstName","");
        editor.putString("lastName","");
        editor.putString("Nationality",nationality);
        editor.putString("PhoneNumber",phoneNumber);
        editor.commit();
    }

    protected String GetGmcId()
    {
        return gmcID;
    }
    protected String GetMongoId()
    {
      return mongoID;
    }
    protected String[] GetName()
    {
        return name;
    }
    protected String GetNationality()
    {
        return nationality;
    }
    protected String GetPhoneNumber()
    {
        return phoneNumber;
    }
    protected int GetPosition()
    {
        return position;
    }
    protected int GetScore()
    {
        return score;
    }

    //

    protected void SetGmcId(String id)
    {
        gmcID = id;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("GMC_ID",gmcID);
        editor.commit();
    }

    protected void SetMongoId(String id)
    {
        mongoID = id;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("MONGO_ID",mongoID);
        editor.commit();
    }

    protected void SetName(String fName, String lName)
    {
        name = new String[] {fName, lName};
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("firstName",fName);
        editor.putString("lastName",lName);
        editor.commit();
    }
    protected void SetNationality(String newNationality)
    {
        nationality = newNationality;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Nationality",nationality);
        editor.commit();
    }
    protected void SetPhoneNumber(String newPhoneNumber)
    {
        phoneNumber = newPhoneNumber;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("PhoneNumber",phoneNumber);
        editor.commit();
    }
    protected void SetPosition(int newPosition)
    {
        position = newPosition;
    }
    protected void SetScore(int newScore)
    {
        position = newScore;
    }

}
