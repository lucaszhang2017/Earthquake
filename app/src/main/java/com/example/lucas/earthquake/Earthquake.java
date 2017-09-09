package com.example.lucas.earthquake;

import java.util.ArrayList;

/**
 * Created by lucas on 8/25/17.
 */

public class Earthquake extends ArrayList {

    private String rmag;
    private String rplace;
    private String rtime;
    private String rUrl;

    public Earthquake(String mag, String place, String time, String Url){
        rmag = mag;
        rplace = place;
        rtime = time;
        rUrl = Url;
    }

    public String getmag() {return rmag;}
    public String getplace() {return rplace;}

    public String gettime() {return rtime;}

    public String geturl() {return rUrl;}



}
