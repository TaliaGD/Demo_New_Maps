package be.ehb.demo_new_maps.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedList;

public class CapitalDAO {

    public static CapitalDAO INSTANCE = new CapitalDAO();
    private ArrayList<Capital> capitals;

    private CapitalDAO(){

    }

    public ArrayList<Capital> getCapitals() {
        if(capitals == null){
            capitals = new ArrayList<>();
            capitals.add(new Capital(new LatLng(51.528308, -0.381789), "Londen", "Dubbeldekker, doctor who, bobby, fish and ships" ));
            capitals.add(new Capital(new LatLng(60.1098678, 24.738504), "Helsinki", "Korpiklaani, perkele, vodka" ));
            capitals.add(new Capital(new LatLng(-33.87365, 151.20689), "Sydney", "It's not the capital, cricket, toilet paper, vegemite sandwich, billabong" ));
        }
        return capitals;
    }


}
