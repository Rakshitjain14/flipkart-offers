package com.flipkart.offers.model;

import java.util.ArrayList;

public class AvailableOffer{
    public String adjustment_type;
    public String adjustment_sub_type;
    public String adjustment_id;
    public String title;
    public String summary;
    public Contributors contributors;
    public boolean is_discover;
    public ArrayList<String> display_tags;

    @Override
    public String toString() {
        return "AvailableOffer{" +
                "adjustment_type='" + adjustment_type + '\'' +
                ", adjustment_sub_type='" + adjustment_sub_type + '\'' +
                ", adjustment_id='" + adjustment_id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", contributors=" + contributors +
                ", is_discover=" + is_discover +
                ", display_tags=" + display_tags +
                '}';
    }

    public String getAdjustment_type() {
        return adjustment_type;
    }

    public void setAdjustment_type(String adjustment_type) {
        this.adjustment_type = adjustment_type;
    }

    public String getAdjustment_sub_type() {
        return adjustment_sub_type;
    }

    public void setAdjustment_sub_type(String adjustment_sub_type) {
        this.adjustment_sub_type = adjustment_sub_type;
    }

    public String getAdjustment_id() {
        return adjustment_id;
    }

    public void setAdjustment_id(String adjustment_id) {
        this.adjustment_id = adjustment_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Contributors getContributors() {
        return contributors;
    }

    public void setContributors(Contributors contributors) {
        this.contributors = contributors;
    }

    public boolean isIs_discover() {
        return is_discover;
    }

    public void setIs_discover(boolean is_discover) {
        this.is_discover = is_discover;
    }

    public ArrayList<String> getDisplay_tags() {
        return display_tags;
    }

    public void setDisplay_tags(ArrayList<String> display_tags) {
        this.display_tags = display_tags;
    }
}
