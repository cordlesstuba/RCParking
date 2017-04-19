package com.rcp.rcparking;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gcarves on 12/04/2017.
 */

public class Addresses {

    static Address addressDepart;
    static Address addressArrivee;


    public static void setAddressDepart(String _id, LatLng _latLng) {
        addressDepart = new Address(_id, _latLng);
    }

    public static Address getAddressDepart() {
        System.out.println(addressDepart.latLng);
        return addressDepart;
    }

    public static void setAddressArrivee(String _id, LatLng _latLng) {
        addressArrivee = new Address(_id, _latLng);
    }

    public static Address getAddressArrivee() {
        System.out.println(addressArrivee.latLng);
        return addressArrivee;
    }


}
