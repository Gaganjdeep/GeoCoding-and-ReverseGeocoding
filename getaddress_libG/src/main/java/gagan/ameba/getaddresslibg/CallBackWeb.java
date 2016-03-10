package gagan.ameba.getaddresslibg;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gagandeep on 30/10/15.
 */
public interface CallBackWeb
{
    void getStringAddress(String output);
    void getLatlngAddress(LatLng latlng);
}
