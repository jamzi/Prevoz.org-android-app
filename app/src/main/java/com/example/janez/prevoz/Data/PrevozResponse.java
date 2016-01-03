package com.example.janez.prevoz.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PrevozResponse {

    @SerializedName("search_type")
    @Expose
    public String searchType;
    @SerializedName("carshare_list")
    @Expose
    public List<Carshare> carshareList = new ArrayList<Carshare>();

}