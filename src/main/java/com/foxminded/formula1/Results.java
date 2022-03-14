package com.foxminded.formula1;

import java.time.LocalTime;

public class Results {
    public  String racerAbbreviation = "";
    public String date = "";
    public LocalTime time;

    public String getRacerAbbreviation() {

        return racerAbbreviation;
    }
    @Override
    public String toString() {
        return "Abbreviations [ racerAbbreviation= " + racerAbbreviation + "date= " +date +"]";
    }

}
