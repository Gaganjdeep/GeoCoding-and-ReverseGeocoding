package gagan.ameba.getaddresslibg;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

/**
 * Created by GaGandeep on 08 Feb 2016.
 */
public class GetStringAddressFromLatLng extends AsyncTask<Void, Void, String>
{

    CallBackWeb callBackWeb;
    Context con;
    LatLng latLng;

    String latLngString;


    LatLng latLngToReturn;

    public GetStringAddressFromLatLng(CallBackWeb callBackWeb, Context con, LatLng latLng)
    {
        this.callBackWeb = callBackWeb;
        this.con = con;
        this.latLng = latLng;
    }

    public GetStringAddressFromLatLng(CallBackWeb callBackWeb, Context con, String latLng)
    {
        this.callBackWeb = callBackWeb;
        this.con = con;
        this.latLngString = latLng;
        this.latLng = null;
    }


    @Override
    protected String doInBackground(Void... voids)
    {
        if (latLng != null)
        {
            return GetAddressByString(con, latLng);
        }
        else
        {
            return GetAddressinLatlng(con, latLngString);
        }
    }

    @Override
    protected void onPostExecute(String s)
    {
        if (latLng != null)
        {
            callBackWeb.getStringAddress(s);
        }
        else
        {

                callBackWeb.getLatlngAddress(latLngToReturn);
                System.out.println(s);

        }


        super.onPostExecute(s);
    }


    private String GetAddressByString(Context con, LatLng addressssss)
    {

        String addressReturn = "";
        Geocoder geocoder = new Geocoder(con, Locale.getDefault());

        List<Address> addresses;
        try
        {

            addresses = geocoder.getFromLocation(addressssss.latitude, addressssss.longitude, 3);

            if (addresses.isEmpty())   //if geocoder fails ,try with google API..!
            {
                addressReturn = getLocationFromString(addressssss);
            }
            else
            {


                int getMAxAddrss = addresses.get(0).getMaxAddressLineIndex();

                for (int g = 0; g < getMAxAddrss; g++)
                {
                    addressReturn = addressReturn + "," + addresses.get(0).getAddressLine(g);
                }
                addressReturn = addressReturn.substring(1, addressReturn.length());
                // addrss[i] = addressReturn;

                return addressReturn;

            }


        }
        catch (Exception | Error e)
        {
            addressReturn = getLocationFromString(addressssss);

        }


        return addressReturn;

    }


    // Directly access google map for location
    public String getLocationFromString(LatLng addressssss)
    {
        String addrsssssName = "";
        try
        {

            String URL = "http://maps.google.com/maps/api/geocode/json?latlng=" + URLEncoder.encode(addressssss.latitude + "," + addressssss.longitude, "UTF-8") + "&en&sensor=false";

            JSONObject jsonObject = new JSONObject(performGetCall(URL));

            JSONArray results = jsonObject.getJSONArray("results");


            if (results.length() > 0)
            {

//                l = new Latlng_data();
//
//                double lng = results.getJSONObject(j).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
//
//                double lat = results.getJSONObject(j).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                addrsssssName = results.getJSONObject(0).getString("formatted_address");
                return addrsssssName != null ? addrsssssName : "";
//                l.setAddress(addrsssssName != null ? addrsssssName : "");
//
//                l.setLat(lng);
//                l.setLng(lat);
//
//                Ldata.add(l);
            }

        }
        catch (Exception e)
        {
            return addrsssssName;
        }
        catch (Error e)
        {
            return addrsssssName;
        }

        return addrsssssName;
    }


    private String GetAddressinLatlng(Context con, String addressssss)
    {

        String addressReturn = "";
        Geocoder geocoder = new Geocoder(con, Locale.getDefault());

        List<Address> addresses;
        try
        {

            addresses = geocoder.getFromLocationName(addressssss, 3);

            if (addresses.isEmpty())   //if geocoder fails ,try with google API..!
            {
                latLngToReturn=null;
                addressReturn = "geocoder not working try google API";
            }
            else
            {


                int getMAxAddrss = addresses.get(0).getMaxAddressLineIndex();

                for (int g = 0; g < getMAxAddrss; g++)
                {
                    addressReturn = addressReturn + "," + addresses.get(0).getAddressLine(g);
                }
                addressReturn = addressReturn.substring(1, addressReturn.length());
                // addrss[i] = addressReturn;
                addressReturn = addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude() + "";

                latLngToReturn = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                return addressReturn;

            }


        }
        catch (Exception | Error e)
        {
            addressReturn = "geocoder not working try google API";

        }


        return addressReturn;

    }


    public String performGetCall(String url)
    {

        try
        {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept", "application/json");


            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();

            //print result
            return response.toString(); //this is your response
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }

    }


}
