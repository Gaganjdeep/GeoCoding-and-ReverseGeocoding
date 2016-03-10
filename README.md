# Geocoding and Reverse Geocoding.
By using this lib,you can get Address from latlng OR lat lng from address (Geocoding and Reverse-Geocoding) both can be done in a simple way ....


See an example how to use this lib : 



    //to get lat lng from an address:-
    new GetStringAddressFromLatLng(this, MainActivity.this, "location to search").execute();
 
 
     //to get address from latlng:-
     new GetStringAddressFromLatLng(this, MainActivity.this, latLng).execute();  
  
  
  after calling this class from lib,make your activity to implement callback methods,you will get response there.
