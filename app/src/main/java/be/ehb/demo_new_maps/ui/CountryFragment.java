package be.ehb.demo_new_maps.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import be.ehb.demo_new_maps.R;
import be.ehb.demo_new_maps.model.Capital;
import be.ehb.demo_new_maps.model.CapitalDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountryFragment extends Fragment {

    //fields
    private MapView mapView;
    private GoogleMap myMap;
//listeners
//private OnMapReadyCallback onMapReady = new OnMapReadyCallback() {        (indien de code zichzelf verschrijft)
    private OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //field maken om de GoogleMap in andere methoden te krijgen.
            myMap = googleMap;

         //kaart klaar
            //kaart centreren op een coördinaat
            LatLng coordBrussel = new LatLng(50.858712, 4.347446);
            CameraUpdate moveToBrussel = CameraUpdateFactory.newLatLngZoom(coordBrussel, 16);
            googleMap.animateCamera(moveToBrussel);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            //fuctie drawMarker aanroepen

            googleMap.setOnInfoWindowClickListener(infoWindowClickListener);
            setMarkerAdapter();
            drawMarkers();

            drawLine();

        }
    };
    private GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Capital c = (Capital) marker.getTag();
            Toast.makeText(getActivity(), c.getInfo(), Toast.LENGTH_LONG).show();
        }
    };

    //map methods
    //lijn op map tekenen
    private void drawLine(){
        //ARGB  a= alpha, rgb= rood groen blauw
        //alpha => doorzichtigheid, 0 = transparant
        //RGB => waarde rood, groen, blauw
        //waardes tussen 0 en 255 of in hexadecimaal tussen 00 en FF
        myMap.addPolyline(new PolylineOptions()
                .color(0xff990000)
                .width(5)
                .add(new LatLng(51.528308, -0.381789))
                .add(new LatLng(60.1098678, 24.738504))
                .add(new LatLng(-33.87365, 151.20689))
                
        );

    }


    private void setMarkerAdapter(){
        myMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                //trekken we ons niks van aan
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View cardView = getActivity().getLayoutInflater().inflate(R.layout.marker_card, null, false);

                TextView tvTitle = cardView.findViewById(R.id.tv_card_title);
                TextView tvSnippet = cardView.findViewById(R.id.tv_card_snippet);

                tvTitle.setText(marker.getTitle());
                tvSnippet.setText(marker.getSnippet());
                
                return cardView;
            }
        });
    }
    private void drawMarkers() {
        myMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.858712, 4.347446))
                .title("Kaaitheater")
                .snippet("Dit is een theater")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name))
                .rotation(20));

        for (Capital capital : CapitalDAO.INSTANCE.getCapitals()){
            Marker m = myMap.addMarker(new MarkerOptions().position(capital.getCoordinate()));
            m.setTitle(capital.getName());
            m.setSnippet(capital.getInfo());
            m.setTag(capital);

        }

    }
    



    public CountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_country, container, false);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(onMapReady);

        return rootView;
    }

    @Override
    public void onResume() {

        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
