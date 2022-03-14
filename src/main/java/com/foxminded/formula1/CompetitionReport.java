package com.foxminded.formula1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompetitionReport {

    public static List<Results> startResults = new ArrayList<>();
    public static List<Results> endResults = new ArrayList<>();
    public static List<Abbreviations> abbreviations = new ArrayList<>();

    public void makeReport() throws IOException {

        fillFromFileStartResults();
        fillFromFileEndResults();
        fillAbbreviationsWithNameCarResults();
        fillAbbreviationsWithStartResults();
        fillAbbreviationsWithEndResults();
        fillAbbreviationsWithEndResults();
        fillAbbreviationsWithDiffTime();
        sortAbbreviations();
        printReport();
    }

    private void fillFromFileStartResults() throws IOException {
        Path path = Paths.get("Files\\start.log");
        Stream<String> streamOfStrings = Files.lines(path);
        streamOfStrings
                .forEach(this::parseLineStart);
    }

    private void fillFromFileEndResults() throws IOException {
        Path path = Paths.get("Files\\end.log");
        Stream<String> streamOfStrings = Files.lines(path);
        streamOfStrings
                .forEach(this::parseLineEnd);
    }

    private void fillAbbreviationsWithNameCarResults() throws IOException {
        Path path = Paths.get("Files\\abbreviations.txt");
        Stream<String> streamOfStrings = Files.lines(path);
        streamOfStrings
                .forEach(this::parseAbbreviations);
    }

    private void parseLineStart(String parseLine) {

        Results rs = new Results();
        rs.racerAbbreviation = parseLine.substring(0, 3);
        rs.date = parseLine.substring(3, 13);
        rs.time = LocalTime.parse(parseLine.substring(14, 26));
        startResults.add(rs);

    }

    private void parseLineEnd(String parseLine) {

        Results rs = new Results();
        rs.racerAbbreviation = parseLine.substring(0, 3);
        rs.date = parseLine.substring(3, 13);
        rs.time = LocalTime.parse(parseLine.substring(14, 26));
        endResults.add(rs);

    }

    private void parseAbbreviations(String parseLine) {
        List<String> fieldsOfAbbreviations = Arrays.stream(parseLine.split("_"))
                .collect(Collectors.toList());
        Abbreviations as = new Abbreviations();
        as.racerAbbreviation = fieldsOfAbbreviations.get(0);
        as.name = fieldsOfAbbreviations.get(1);
        as.car = fieldsOfAbbreviations.get(2);
        abbreviations.add(as);
    }

    private void fillAbbreviationsWithStartResults() {
        for (Results startResults : startResults) {
            for (Abbreviations abbreviations : abbreviations) {
                if (startResults.racerAbbreviation.equals(abbreviations.racerAbbreviation)) {
                    abbreviations.startTime = startResults.time;
                }
            }
        }
    }

    private void fillAbbreviationsWithEndResults() {
        for (Results endResults : endResults) {
            for (Abbreviations abbreviations : abbreviations) {
                if (endResults.racerAbbreviation.equals(abbreviations.racerAbbreviation)) {
                    abbreviations.endTime = endResults.time;
                }
            }
        }
    }

    private void fillAbbreviationsWithDiffTime() {
        abbreviations
                .forEach(this::fillDiffTime);
    }

    private void fillDiffTime(Abbreviations abbreviations) {
        long hours = abbreviations.startTime.until(abbreviations.endTime, ChronoUnit.HOURS);
        long minutes = (abbreviations.startTime.until(abbreviations.endTime, ChronoUnit.MINUTES)) % 60;
        abbreviations.minutes = String.format("%02d", minutes);
        long seconds = abbreviations.startTime.until(abbreviations.endTime, ChronoUnit.SECONDS);
        seconds = seconds - hours * 3600 - minutes * 60;
        abbreviations.seconds = String.format("%02d", seconds);
        long nanosecond = abbreviations.startTime.until(abbreviations.endTime, ChronoUnit.NANOS);
        String nanosecondString = Long.toString(nanosecond);
        nanosecondString = nanosecondString.substring(nanosecondString.length() - 9, nanosecondString.length() - 6);
        abbreviations.nanoseсonds = nanosecondString;

    }

    private void sortAbbreviations() {
        Comparator<Abbreviations> dateComparator = Comparator.comparing(Abbreviations::getMinutes)
                .thenComparing(Abbreviations::getSeconds)
                .thenComparing(Abbreviations::getNanoseсonds);
        abbreviations.stream()
                .sorted(dateComparator);
    }

    private void printReport() {
        for (int i = 0; i < abbreviations.size(); i++) {
            System.out.println(String.format("% 4d", i + 1) + ". " +
                    String.format("%-20s", abbreviations.get(i).name) + "   | " +
                    String.format("%-30s", abbreviations.get(i).car) + "   | " +
                    abbreviations.get(i).minutes + ":" +
                    abbreviations.get(i).seconds + "." +
                    abbreviations.get(i).nanoseсonds
            );
            if (i == 14) {
                System.out.println("  ------------------------------------------------------------------------");
            }
        }
    }

}//public class CompetitionReport End
