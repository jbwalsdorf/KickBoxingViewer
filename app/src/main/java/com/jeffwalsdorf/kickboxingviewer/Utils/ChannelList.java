package com.jeffwalsdorf.kickboxingviewer.Utils;

import android.text.TextUtils;

/**
 * Created by Jeff on 5/19/2015.
 */
public class ChannelList {

    private String muayDeetSport = "UC136PyHqcUNWl1q5ZHVyR9g";
    private String tkoWorldBoxing = "UCkyx5g1im6Q1FL26XDxJYBg";
    private String muayTies = "UCk3THNGRpNmCsRbCaWNeWSA";
    private String gloryWorldSeries = "UCKj5FIgxeihLRLDpVqKp_aA";
    private String muayThaiTop = "UCwsdMe9N96Bmh-EXnCTQ_ng";
    private String muayThaiDaily = "UCtmsXw9ZtWfFnGZdKVTmowA";

    private String[] channels = {
            "UC136PyHqcUNWl1q5ZHVyR9g", // Muay Deet Sport
            "UCkyx5g1im6Q1FL26XDxJYBg", // TKO World Boxing
            "UCk3THNGRpNmCsRbCaWNeWSA", // Muay Ties
            "UCKj5FIgxeihLRLDpVqKp_aA", // Glory World Series
            "UCwsdMe9N96Bmh-EXnCTQ_ng", // Muay Thai Top
            "UCtmsXw9ZtWfFnGZdKVTmowA"  // Muay Thai Daily
    };


    public String ChannelList(){
        return TextUtils.join(",", channels);
    }

    public String getChannels() {
        return TextUtils.join(",", channels);
    }
}
