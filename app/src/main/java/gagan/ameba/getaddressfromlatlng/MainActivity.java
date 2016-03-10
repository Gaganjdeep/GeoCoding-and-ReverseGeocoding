package gagan.ameba.getaddressfromlatlng;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import gagan.ameba.getaddresslibg.CallBackWeb;
import gagan.ameba.getaddresslibg.GetStringAddressFromLatLng;

/**
 * Created by GaGandeep on 08 Feb 2016.
 */
public class MainActivity extends AppCompatActivity implements CallBackWeb
{

    EditText edGetloc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edGetloc = (EditText) findViewById(R.id.edGetloc);


    }


    @Override
    public void getStringAddress(String output)
    {
        Toast.makeText(MainActivity.this, output, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getLatlngAddress(LatLng latlng)
    {
        if (latlng != null)
        {
            Toast.makeText(MainActivity.this, latlng.latitude + "," + latlng.longitude + "", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Invalid address..!", Toast.LENGTH_SHORT).show();
        }
    }













    //    on click events
    public void getLatlng(View view)
    {
        if (edGetloc.getText().toString().trim().isEmpty())
        {
            edGetloc.setError("Enter Location");
        }
        else
        {
            new GetStringAddressFromLatLng(this, MainActivity.this, edGetloc.getText().toString().trim()).execute();     //enter address to get it lat lng using lib
        }

    }

    public void getAddress(View view)
    {
        LatLng latLng = new LatLng(30.3619, 76.8481);  //pass lat lng ,for which you need address.
        new GetStringAddressFromLatLng(this, MainActivity.this, latLng).execute();        //enter lat lng object to get it address using lib

    }
}
