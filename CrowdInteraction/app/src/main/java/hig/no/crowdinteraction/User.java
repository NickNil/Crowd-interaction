package hig.no.crowdinteraction;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Harry on 30.10.2014. <p>
 * a class that is called if user information is need, and stores new user information to the shard
 * preferences
 */
public class User
{
    String gmcID;
    String mongoID;
    String[] name;
    String nationality;
    String ioc;
    String iso;
    String phoneNumber;
    String highscore;
    Context context;
    SharedPreferences sharedPref;

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
        highscore = sharedPref.getString("Highscore","");
        ioc = sharedPref.getString("ioc","");
        iso = sharedPref.getString("iso","");
        context = appContext;
    }

    /**
     * Deletes the user information from shard preferences
     */
    public void logout()
    {
        gmcID = "";
        mongoID = "";
        name = new String[] {"", ""};
        nationality = "";
        phoneNumber = "";
        highscore = "";
        ioc = "";
        iso = "";

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("GMC_ID",gmcID);
        editor.putString("MONGO_ID",mongoID);
        editor.putString("firstName","");
        editor.putString("lastName","");
        editor.putString("Nationality",nationality);
        editor.putString("PhoneNumber",phoneNumber);
        editor.putString("Highscore", highscore);
        editor.putString("ioc", ioc);
        editor.putString("iso", iso);
        editor.commit();
    }

    /**
     *
     * @return Users Google cloud messaging ID
     */
    protected String GetGmcId()
    {
        return gmcID;
    }

    /**
     *
     * @return Users database ID
     */
    protected String GetMongoId()
    {
      return mongoID;
    }

    /**
     *
     * @return Users name {first name, sir name}
     */
    protected String[] GetName()
    {
        return name;
    }

    /**
     *
     * @return Users Nationality
     */
    protected String GetNationality()
    {
        return nationality;
    }
    /**
     *
     * @return Users nationality's short name
     */
    protected String GetIoc()
    {
        return ioc;
    }
    /**
     *
     * @return Users nationality's flag
     */
    protected String GetIso()
    {
        return iso;
    }
    /**
     *
     * @return Users phone number
     */
    protected String GetPhoneNumber()
    {
        return phoneNumber;
    }
    /**
     *
     * @return Users highscore
     */
    protected String GetHighscore()
    {
        return highscore;
    }

    ///////////////////////////////////////////////////////////

    /**
     * Stores the Google cloud messaging ID to shard prefaces
     * @param id Google cloud messaging ID
     */
    protected void SetGmcId(String id)
    {
        gmcID = id;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("GMC_ID",gmcID);
        editor.commit();
    }

    /**
     * Stores the database ID to shard prefaces
     * @param id database ID
     */
    protected void SetMongoId(String id)
    {
        mongoID = id;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("MONGO_ID",mongoID);
        editor.commit();
    }

    /**
     * Stores the users name to shard prefaces
     * @param fName Users first name
     * @param lName Users sir name
     */
    protected void SetName(String fName, String lName)
    {
        name = new String[] {fName, lName};
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("firstName",fName);
        editor.putString("lastName",lName);
        editor.commit();
    }

    /**
     * Stores the users nationality to shard prefaces
     * @param newNationality User Nationality
     */
    protected void SetNationality(String newNationality)
    {
        nationality = newNationality;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Nationality",nationality);
        editor.commit();
    }

    /**
     * stores the users national code to shard prefaces
     * @param newIoc national code
     */
    protected void SetIoc(String newIoc)
    {
        ioc = newIoc;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("ioc",ioc);
        editor.commit();
    }

    /**
     * stores the users national code for flag to shard prefaces
     * @param newIso national code for flag
     */
    protected void SetIso(String newIso)
    {
        iso = newIso;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("iso",iso);
        editor.commit();
    }

    /**
     * stores the users  phone number to shard prefaces
     * @param newPhoneNumber Phone number
     */
    protected void SetPhoneNumber(String newPhoneNumber)
    {
        phoneNumber = newPhoneNumber;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("PhoneNumber",phoneNumber);
        editor.commit();
    }

    /**
     * stores the users highscore to shard prefaces
     * @param newHighscore
     */
    protected void SetHighscore(String newHighscore)
    {
        highscore = newHighscore;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Highscore",highscore);
        editor.commit();
    }

}
