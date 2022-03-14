package com.foxminded.formula1;

import java.time.LocalTime;

public class Abbreviations {

    public String racerAbbreviation = "";
    public String name = "";
    public String car = "";
    public LocalTime startTime;
    public LocalTime endTime;
    public String minutes  = "";
    public String seconds = "";
    public String nanoseсonds  = "";

    public String getRacerAbbreviation() {
        return racerAbbreviation;
    }public String getMinutes() {
        return minutes;
    }public String getSeconds() {
        return seconds;
    }public String getNanoseсonds() {
        return nanoseсonds;
    }

        @Override
    public String toString() {
        return "Abbreviations [ racerAbbreviation= " + racerAbbreviation + "name= " +name +"]";
    }

}
