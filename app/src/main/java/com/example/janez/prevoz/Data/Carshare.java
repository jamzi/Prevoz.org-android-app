package com.example.janez.prevoz.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Carshare implements Serializable{

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("from_id")
    @Expose
    public String fromId;
    @SerializedName("from_country")
    @Expose
    public String fromCountry;
    @SerializedName("from_country_name")
    @Expose
    public String fromCountryName;
    @SerializedName("to_id")
    @Expose
    public String toId;
    @SerializedName("to")
    @Expose
    public String to;
    @SerializedName("to_country")
    @Expose
    public String toCountry;
    @SerializedName("to_country_name")
    @Expose
    public String toCountryName;
    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("date_iso8601")
    @Expose
    public String dateIso8601;
    @SerializedName("added")
    @Expose
    public String added;
    @SerializedName("price")
    @Expose
    public Integer price;
    @SerializedName("num_people")
    @Expose
    public Integer numPeople;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("is_author")
    @Expose
    public String isAuthor;
    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("contact")
    @Expose
    public String contact;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("full")
    @Expose
    public String full;
    @SerializedName("insured")
    @Expose
    public String insured;
    @SerializedName("share_type")
    @Expose
    public String shareType;
    @SerializedName("confirmed_contact")
    @Expose
    public String confirmedContact;
    @SerializedName("bookmark")
    @Expose
    public Object bookmark;
    @SerializedName("from")
    @Expose
    public String from;

}