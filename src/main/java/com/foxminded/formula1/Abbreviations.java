package com.foxminded.formula1;

import java.time.LocalTime;

public class Abbreviations {

    public String racerAbbreviation = "";
    public String name = "";
    public String car = "";
    public LocalTime startTime;
    public LocalTime endTime;
    public String minutes = "";
    public String seconds = "";
    public String nanosecond = "";

    public Integer getMinutes() {
        return Integer.parseInt(minutes);
    }

    public Integer getSeconds() {
        return Integer.parseInt(seconds);
    }

    public Integer getNanosecond() {

        return Integer.parseInt(nanosecond);
    }

    @Override
    public String toString() {
        return "Abbreviations [ racerAbbreviation= " + racerAbbreviation + "name= " + name + "]";
    }

}
